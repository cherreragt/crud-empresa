package org.example.empresa.interfaces;

import org.example.empresa.domain.Company;
import org.example.empresa.dto.CompanyDTO;
import org.example.empresa.exception.BadRequestException;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface ICompanyService {
    List<Company> getCompanies();

    Company createCompany(CompanyDTO companyDTO, BindingResult result) throws BadRequestException;

    Company getCompanyById(Long id);

    Company deleteCompanyById(Long id);

    Company updateCompany(CompanyDTO companyDTO, BindingResult result);
}