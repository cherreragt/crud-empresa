package org.example.empresa.repository;

import org.example.empresa.domain.Collaborator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CollaboratorRepository extends JpaRepository<Collaborator, Long> {
    Optional<Collaborator> findByCUI(String cui);
    Optional<Collaborator> findFirstByBranchId(Long branchId);
    List<Collaborator> findAllByBranchId(Long branchId);
}
