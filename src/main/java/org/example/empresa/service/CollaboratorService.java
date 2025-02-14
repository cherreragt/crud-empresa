package org.example.empresa.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.example.empresa.domain.Collaborator;
import org.example.empresa.dto.CollaboratorDTO;
import org.example.empresa.exception.BadRequestException;
import org.example.empresa.exception.ConflictException;
import org.example.empresa.exception.NoContentException;
import org.example.empresa.interfaces.ICollaboratorService;
import org.example.empresa.repository.BranchRepository;
import org.example.empresa.repository.CollaboratorRepository;
import org.example.empresa.utils.Validator;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CollaboratorService implements ICollaboratorService {
    private final CollaboratorRepository collaboratorRepository;
    private final BranchRepository branchRepository;

    @Override
    public Collaborator createCollaborator(CollaboratorDTO collaboratorDTO, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            Validator.validation(bindingResult);
        }
        var getCollaborator = collaboratorRepository.findByCUI(collaboratorDTO.getCUI());
        if (getCollaborator.isPresent()) {
            throw new ConflictException("Colaborador ya existe");
        }

        var getBranch = branchRepository.findById(collaboratorDTO.getBranchId());
        if (getBranch.isEmpty()) {
            throw new BadRequestException("Sucursal no existe");
        }

        return this.collaboratorRepository.save(collaboratorDTO.toCollaborator());
    }

    @Override
    public Collaborator updateCollaborator(CollaboratorDTO collaboratorDTO, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            Validator.validation(bindingResult);
        }

        var getCollaborator = collaboratorRepository.findById(collaboratorDTO.getId());
        if (getCollaborator.isEmpty()) {
            throw new BadRequestException("Colaborador NO existe");
        }

        var getBranch = branchRepository.findById(collaboratorDTO.getBranchId());
        if (getBranch.isEmpty()) {
            throw new BadRequestException("Sucursal no existe");
        }

        return this.collaboratorRepository.save(collaboratorDTO.toCollaborator());
    }

    @Override
    public Collaborator deleteCollaborator(@NotNull Long id) {
        var getCollaborator = collaboratorRepository.findById(id);
        if (getCollaborator.isEmpty()) {
            throw new NoContentException("Colaborador NO existe");
        }
        var collaborator = getCollaborator.get();
        this.collaboratorRepository.delete(collaborator);
        return collaborator;
    }

    @Override
    public Collaborator getCollaborator(@NotNull Long id) {
        var getCollaborator = collaboratorRepository.findById(id);
        if (getCollaborator.isEmpty()) {
            throw new NoContentException("Colaborador NO existe");
        }
        return getCollaborator.get();
    }

    @Override
    public List<Collaborator> getCollaboratorsByBranchId(Long branchId) {
        var collaborators = collaboratorRepository.findAllByBranchId(branchId);
        if (collaborators.isEmpty()) {
            throw new NoContentException("No hay colaboradores en la sucursal");
        }
        return collaborators;
    }
}
