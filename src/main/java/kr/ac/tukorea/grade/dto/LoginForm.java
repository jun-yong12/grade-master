package kr.ac.tukorea.grade.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LoginForm {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
