package org.example.empresa.repository;

import org.example.empresa.domain.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
    Optional<Branch> findByName(String name);
    Optional<Branch> findFirstByCompanyId(Long companyId);
    List<Branch> findAllByCompanyId(Long companyId);
}
