package org.example.empresa.interfaces;

import jakarta.validation.constraints.NotNull;
import org.example.empresa.domain.Collaborator;
import org.example.empresa.dto.CollaboratorDTO;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface ICollaboratorService {
    Collaborator createCollaborator(CollaboratorDTO collaboratorDTO, BindingResult bindingResult);
    Collaborator updateCollaborator(CollaboratorDTO collaboratorDTO, BindingResult bindingResult);
    Collaborator deleteCollaborator(@NotNull Long id);
    Collaborator getCollaborator(@NotNull Long id);
    List<Collaborator> getCollaboratorsByBranchId(@NotNull Long branchId);
}
