package kr.ac.tukorea.grade.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class GradeDTO {
    private Long id;

    @NotNull(message = "학생 ID는 필수입니다")
    private Long studentId;

    @NotBlank(message = "과목명은 필수입니다")
    private String subject;

    @Min(0) @Max(100)
    private int score;

    @NotBlank(message = "학기는 필수입니다")
    private String semester;

    // 조회 시 학생 정보 포함
    private String studentName;
    private String studentNumber;

    public String getLetterGrade() {
        if (score >= 90) return "A";
        else if (score >= 80) return "B";
        else if (score >= 70) return "C";
        else if (score >= 60) return "D";
        else return "F";
    }
}
