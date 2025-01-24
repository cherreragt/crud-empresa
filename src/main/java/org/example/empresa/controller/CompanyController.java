package org.example.empresa.controller;

import jakarta.validation.Valid;
import org.example.empresa.domain.Company;
import org.example.empresa.dto.CompanyDTO;
import org.example.empresa.exception.BadRequestException;
import org.example.empresa.interfaces.ICompanyService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/company")
public class CompanyController {
    private final ICompanyService companyService;

    public CompanyController(ICompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<List<Company>> getCompanies() {
        return ResponseEntity.ok(companyService.getCompanies());
    }

    @PostMapping
    public ResponseEntity<Company> createCompany(@Valid @RequestBody CompanyDTO companyDTO, BindingResult result) throws BadRequestException {
        companyDTO.setId(null);
        var company = companyService.createCompany(companyDTO, result);
        return ResponseEntity.status(HttpStatus.CREATED).body(company);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.companyService.getCompanyById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Company> deleteCompanyById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.companyService.deleteCompanyById(id));
    }

    @PutMapping
    public ResponseEntity<Company> updateCompany(@Valid @RequestBody CompanyDTO companyDTO, BindingResult result) {
        var company = companyService.updateCompany(companyDTO, result);
        return ResponseEntity.status(HttpStatus.CREATED).body(company);
    }
}
