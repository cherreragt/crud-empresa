package org.example.empresa.interfaces;

import jakarta.validation.constraints.NotNull;
import org.example.empresa.domain.Branch;
import org.example.empresa.dto.BranchDTO;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface IBranchService {
    Branch createBranch(BranchDTO branchDTO, BindingResult bindingResult);
    Branch updateBranch(BranchDTO branchDTO, BindingResult bindingResult);
    Branch deleteBranch(@NotNull Long id);
    Branch getBranch(@NotNull Long id);
    List<Branch> getBranchesByCompanyId(@NotNull Long companyId);
}
