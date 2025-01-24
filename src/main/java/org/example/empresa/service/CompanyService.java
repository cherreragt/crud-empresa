package org.example.empresa.service;

import org.example.empresa.domain.Company;
import org.example.empresa.dto.CompanyDTO;
import org.example.empresa.exception.BadRequestException;
import org.example.empresa.exception.ConflictException;
import org.example.empresa.exception.NoContentException;
import org.example.empresa.interfaces.ICompanyService;
import org.example.empresa.repository.CompanyRepository;
import org.example.empresa.utils.Validator;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
public class CompanyService implements ICompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

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
        var companyExists = companyRepository.findByIssuer(companyDTO.getIssuer());
        if (companyExists.isPresent()) {
            throw new ConflictException("El nit ya esta registrado");
        }
        return this.companyRepository.save(companyDTO.toCompany());
    }

    @Override
    public Company getCompanyById(Long id) {
        if (id == null) {
            throw new BadRequestException("id es requerido");
        }
        return this.companyRepository.findById(id).orElseThrow(() -> new NoContentException("No hay data con ese id"));
    }

    @Override
    public Company deleteCompanyById(Long id) {
        if (id == null) {
            throw new BadRequestException("id es requerido");
        }
        var companyExists = companyRepository.findById(id);
        if (companyExists.isEmpty()) {
            throw new BadRequestException("El nit NO esta registrado");
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
            throw new NoContentException("El nit NO esta registrado");
        }
        var companyExistsByIssuer = companyRepository.findByIssuerAndIdIsNot(companyDTO.getIssuer(), companyDTO.getId());
        if (companyExistsByIssuer.isPresent()) {
            throw new ConflictException("El nit ya esta registrado");
        }
        companyDTO.setId(companyExistsById.get().getId());
        return this.companyRepository.save(companyDTO.toCompany());
    }
}
