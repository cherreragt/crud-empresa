package org.example.empresa.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.example.empresa.domain.Collaborator;
import org.example.empresa.dto.CollaboratorDTO;
import org.example.empresa.interfaces.ICollaboratorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/collaborator")
@RequiredArgsConstructor
public class CollaboratorController {
    private final ICollaboratorService collaboratorService;

    @PostMapping
    public ResponseEntity<Collaborator> createCollaborator(@Valid @RequestBody CollaboratorDTO collaboratorDTO, BindingResult bindingResult) {
        collaboratorDTO.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.collaboratorService.createCollaborator(collaboratorDTO, bindingResult));
    }

    @PutMapping
    public ResponseEntity<Collaborator> updateCollaborator(@Valid @RequestBody CollaboratorDTO collaboratorDTO, BindingResult bindingResult) {
        return ResponseEntity.status(HttpStatus.OK).body(this.collaboratorService.updateCollaborator(collaboratorDTO, bindingResult));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Collaborator> deleteCollaborator(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.collaboratorService.deleteCollaborator(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Collaborator> getCollaborator(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.collaboratorService.getCollaborator(id));
    }

    @GetMapping
    public ResponseEntity<List<Collaborator>> getCollaboratorsByBranchId(@PathParam("branchId") @NotNull Long branchId) {
        return ResponseEntity.status(HttpStatus.OK).body(this.collaboratorService.getCollaboratorsByBranchId(branchId));
    }
}
