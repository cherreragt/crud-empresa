package org.example.empresa.repository;

import org.example.empresa.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByIssuer(String issuer);
    Optional<Company> findByIssuerAndIdIsNot(String issuer, Long id);
}
