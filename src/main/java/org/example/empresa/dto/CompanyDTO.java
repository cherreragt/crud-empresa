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

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
}
