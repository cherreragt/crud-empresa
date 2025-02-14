package org.example.empresa.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.empresa.domain.Branch;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BranchDTO {
    private Long id;

    @Size(max = 255, min = 10, message = "Name debe tener entre 10 y 255 caracteres")
    @NotEmpty(message = "Name es requerido")
    @NotBlank(message = "Name no puede estar vacio")
    private String name;

    @Size(max = 255, min = 10, message = "Address debe tener entre 10 y 255 caracteres")
    @NotEmpty(message = "Address es requerido")
    @NotBlank(message = "Address no puede estar vacio")
    private String address;

    @Size(max = 20, min = 8, message = "Phone debe tener entre 8 y 20 caracteres")
    @NotEmpty(message = "Phone es requerido")
    @NotBlank(message = "Phone no puede estar vacio")
    private String phone;

    @NotNull(message = "CompanyId es requerido")
    private Long companyId;

    public Branch toBranch() {
        var branch = new Branch();
        BeanUtils.copyProperties(this, branch, "collaborators");
        return branch;
    }

    private List<CollaboratorDTO> collaborators;
}
