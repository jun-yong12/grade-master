package kr.ac.tukorea.grade.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SubjectGradeWeightDTO {
    private Long subjectId;
    @Builder.Default private int attendanceWeight  = 10;
    @Builder.Default private int midtermWeight     = 30;
    @Builder.Default private int finalWeight       = 40;
    @Builder.Default private int assignmentWeight  = 20;
}
