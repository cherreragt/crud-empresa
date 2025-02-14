package org.example.empresa.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.example.empresa.domain.Company;
import org.example.empresa.dto.CompanyDTO;
import org.example.empresa.dto.CompanyRoot;
import org.example.empresa.exception.BadRequestException;
import org.example.empresa.exception.ConflictException;
import org.example.empresa.exception.NoContentException;
import org.example.empresa.interfaces.ICompanyService;
import org.example.empresa.repository.BranchRepository;
import org.example.empresa.repository.CollaboratorRepository;
import org.example.empresa.repository.CompanyRepository;
import org.example.empresa.utils.Validator;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CompanyService implements ICompanyService {
    private final CompanyRepository companyRepository;
    private final BranchRepository branchRepository;
    private final CollaboratorRepository collaboratorRepository;

    @Override
    public List<Company> getCompanies() {
        var companies = companyRepository.findAll();

        if (companies.isEmpty()) {
            throw new NoContentException("No hay contenido");
        }
        return companies.stream().toList();
    }

    @Override
    public Company createCompany(CompanyDTO companyDTO, BindingResult result) throws BadRequestException {
        if (result.hasFieldErrors()) {
            Validator.validation(result);
        }
        var getCompany = companyRepository.findByName(companyDTO.getName());
        if (getCompany.isPresent()) {
            throw new ConflictException("El nombre de la compañia ya esta registrado");
        }
        return this.companyRepository.save(companyDTO.toCompany());
    }

    @Override
    public Company getCompanyById(@NotNull Long id) {
        return this.companyRepository.findById(id).orElseThrow(() -> new NoContentException("No hay data con ese id"));
    }

    @Override
    public Company deleteCompanyById(@NotNull Long id) {
        var companyExists = companyRepository.findById(id);
        if (companyExists.isEmpty()) {
            throw new BadRequestException("La compañia NO esta registrada");
        }

        var branch = branchRepository.findFirstByCompanyId(id);
        if (branch.isPresent()) {
            throw new ConflictException("La compañia tiene sucursales registradas");
        }

        this.companyRepository.delete(companyExists.get());
        return companyExists.get();
    }

    @Override
    public Company updateCompany(CompanyDTO companyDTO, BindingResult result) {
        if (result.hasFieldErrors()) {
            Validator.validation(result);
        }

        var companyExistsById = companyRepository.findById(companyDTO.getId());
        if (companyExistsById.isEmpty()) {
            throw new NoContentException("La compañia NO esta registrada");
        }

        companyDTO.setId(companyExistsById.get().getId());
        return this.companyRepository.save(companyDTO.toCompany());
    }

    @Override
    @Transactional
    public void batchCreateCompanies(@NotNull MultipartFile file) throws BadRequestException {
        if (file.isEmpty() || Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            throw new BadRequestException("No hay ningun archivo");
        }
        if (!file.getOriginalFilename().endsWith(".json")) {
            throw new BadRequestException("El archivo debe ser de tipo JSON");
        }

        // Podría ser reemplazado por un proceso batch/cola para hacerlo en segundo plano y el servicio no se vea afectado, pero no lo hago por cuestiones de tiempo
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            var om = new ObjectMapper();
            var root = om.readValue(file.getInputStream(), CompanyRoot.class);
            var companyDTO = root.getCompany();

            var validator = factory.getValidator();
            var companyErrors = validator.validate(companyDTO);
            if (!companyErrors.isEmpty()) {
                throw new BadRequestException(companyErrors.stream().toList().get(0).getMessage());
            }

            var getCompany = companyRepository.findByName(companyDTO.getName());
            if (getCompany.isPresent()) {
                throw new ConflictException("El nombre de la compañia ya esta registrado");
            }
            var company = this.companyRepository.save(companyDTO.toCompany());

            for (var branchDTO : companyDTO.getBranches()) {
                var getBranch = this.branchRepository.findByName(branchDTO.getName());
                if (getBranch.isPresent()) {
                    throw new ConflictException("El nombre de la sucursal ya esta registrado");
                }
                branchDTO.setCompanyId(company.getId());

                var branchErrors = validator.validate(branchDTO);
                if (!branchErrors.isEmpty()) {
                    throw new BadRequestException(branchErrors.stream().toList().get(0).getMessage());
                }

                var branch = branchDTO.toBranch();
                branch = this.branchRepository.save(branch);

                for (var collaboratorDTO: branchDTO.getCollaborators()) {
                    var getCollaborator = this.collaboratorRepository.findByCUI(collaboratorDTO.getCUI());
                    if (getCollaborator.isPresent()) {
                        throw new ConflictException("El CUI del colaborador: ".concat(collaboratorDTO.getCUI()).concat(" ya esta registrado"));
                    }
                    collaboratorDTO.setBranchId(branch.getId());

                    var collaboratorErrors = validator.validate(collaboratorDTO);
                    if (!collaboratorErrors.isEmpty()) {
                        throw new BadRequestException(collaboratorErrors.stream().toList().get(0).getMessage());
                    }

                    var collaborator = collaboratorDTO.toCollaborator();
                    this.collaboratorRepository.save(collaborator);
                }
            }

        } catch (Exception e) {
            throw new BadRequestException("Error al cargar el archivo: ".concat(e.getMessage()));
        }
    }
}
