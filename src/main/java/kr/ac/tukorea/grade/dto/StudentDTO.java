package kr.ac.tukorea.grade.dto;

import jakarta.validation.constraints.*;
import lombok.*;

// ── 학생 DTO ────────────────────────────────────────────
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class StudentDTO {
    private Long id;

    @NotBlank(message = "학번은 필수입니다")
    private String studentNumber;

    @NotBlank(message = "이름은 필수입니다")
    private String name;

    private String grade;
    private String department;
    private String phone;
    private String email;
    private String status;
    private Double average;   // 평균 (MyBatis 쿼리에서 계산)
    private Integer rank;     // 등수
}
