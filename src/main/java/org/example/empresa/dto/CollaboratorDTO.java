package org.example.empresa.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.empresa.domain.Collaborator;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CollaboratorDTO {
    private Long id;

    @Size(min = 3, max = 255, message = "Name debe tener entre 3 y 255 caracteres")
    @NotEmpty(message = "Name es requerido")
    @NotBlank(message = "Name no puede estar vacio")
    private String name;

    @Size(min = 13, max = 15, message = "Phone debe tener entre 13 y 15 caracteres")
    @NotEmpty(message = "CUI es requerido")
    @NotBlank(message = "CUI no puede estar vacio")
    @JsonProperty("CUI")
    private String CUI;

    @NotNull(message = "BranchId es requerido")
    private Long branchId;

    public Collaborator toCollaborator() {
        var collaborator = new Collaborator();
        BeanUtils.copyProperties(this, collaborator);
        return collaborator;
    }
}
