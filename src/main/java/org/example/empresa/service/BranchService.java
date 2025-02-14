package org.example.empresa.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.example.empresa.domain.Branch;
import org.example.empresa.dto.BranchDTO;
import org.example.empresa.exception.ConflictException;
import org.example.empresa.interfaces.IBranchService;
import org.example.empresa.repository.BranchRepository;
import org.example.empresa.repository.CollaboratorRepository;
import org.example.empresa.utils.Validator;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchService implements IBranchService {
    private final BranchRepository branchRepository;
    private final CollaboratorRepository collaboratorRepository;

    @Override
    public Branch createBranch(BranchDTO branchDTO, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            Validator.validation(bindingResult);
        }

        var getBranch = this.branchRepository.findByName(branchDTO.getName());
        if (getBranch.isPresent()) {
            throw new ConflictException("El nombre de la sucursal ya esta registrado");
        }
        return this.branchRepository.save(branchDTO.toBranch());
    }

    @Override
    public Branch updateBranch(BranchDTO branchDTO, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            Validator.validation(bindingResult);
        }

        var getBranch = this.branchRepository.findById(branchDTO.getId());
        if (getBranch.isEmpty()) {
            throw new ConflictException("La sucursal no existe");
        }
        return this.branchRepository.save(branchDTO.toBranch());
    }

    @Override
    public Branch deleteBranch(@NotNull Long id) {
        var getBranch = this.branchRepository.findById(id);
        if (getBranch.isEmpty()) {
            throw new ConflictException("La sucursal no existe");
        }

        var getCollaborator = this.collaboratorRepository.findFirstByBranchId(id);
        if (getCollaborator.isPresent()) {
            throw new ConflictException("La sucursal tiene colaboradores registrados");
        }

        this.branchRepository.deleteById(id);
        return getBranch.get();
    }

    @Override
    public Branch getBranch(@NotNull Long id) {
        var getBranch = this.branchRepository.findById(id);
        if (getBranch.isEmpty()) {
            throw new ConflictException("La sucursal no existe");
        }
        return getBranch.get();
    }

    @Override
    public List<Branch> getBranchesByCompanyId(Long companyId) {
        var branches = this.branchRepository.findAllByCompanyId(companyId);
        if (branches.isEmpty()) {
            throw new ConflictException("No hay sucursales registradas");
        }
        return branches;
    }
}
