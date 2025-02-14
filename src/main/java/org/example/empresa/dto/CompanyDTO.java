package org.example.empresa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.empresa.domain.Company;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO{
    private Long id;
    @NotBlank(message = "name no debe ser nulo")
    @NotEmpty(message = "name no debe ser nulo")
    @Size(max = 255)
    private String name;

    @NotBlank(message = "country no debe ser nulo")
    @NotEmpty(message = "country no debe ser nulo")
    @Size(max = 100)
    private String country;

    private List<BranchDTO> branches;

    public Company toCompany() {
        var company = new Company();
        BeanUtils.copyProperties(this, company, "branches");
        return company;
    }
}
