package org.example.empresa.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.example.empresa.domain.Branch;
import org.example.empresa.dto.BranchDTO;
import org.example.empresa.interfaces.IBranchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branch")
@RequiredArgsConstructor
public class BranchController {
    private final IBranchService branchService;

    @PostMapping
    public ResponseEntity<Branch> createBranch(@Valid @RequestBody BranchDTO branchDTO, BindingResult bindingResult) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                this.branchService.createBranch(branchDTO, bindingResult)
        );
    }

    @PutMapping
    public ResponseEntity<Branch> updateBranch(@Valid @RequestBody BranchDTO branchDTO, BindingResult bindingResult) {
        return ResponseEntity.ok(this.branchService.updateBranch(branchDTO, bindingResult));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Branch> deleteBranch(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.branchService.deleteBranch(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Branch> getBranch(@PathVariable Long id) {
        return ResponseEntity.ok(this.branchService.getBranch(id));
    }

    @GetMapping
    public ResponseEntity<List<Branch>> getBranches(@PathParam("companyId") @NotNull Long companyId) {
        return ResponseEntity.ok(this.branchService.getBranchesByCompanyId(companyId));
    }
}
