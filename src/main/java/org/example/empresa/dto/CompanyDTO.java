package org.example.empresa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.example.empresa.domain.Company;
import org.springframework.beans.BeanUtils;

public class CompanyDTO{
    private Long id;

    @NotBlank(message = "name no debe ser nulo")
    @NotEmpty(message = "name no debe ser nulo")
    @Size(max = 80)
    private String name;

    @NotBlank(message = "issuer no debe ser nulo")
    @NotEmpty(message = "issuer no debe ser nulo")
    @Size(max = 32)
    private String issuer;

    @NotBlank(message = "address no debe ser nulo")
    @NotEmpty(message = "address no debe ser nulo")
    @Size(max = 150)
    private String  address;

    public Company toCompany() {
        var company = new Company();
        BeanUtils.copyProperties(this, company);
        return company;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
