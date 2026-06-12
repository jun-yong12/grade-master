package kr.ac.tukorea.grade.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SubjectGradeRatioDTO {
    private Long subjectId;
    @Builder.Default private int aRatio = 30;
    @Builder.Default private int bRatio = 30;
    @Builder.Default private int cRatio = 25;
    @Builder.Default private int dRatio = 10;
    @Builder.Default private int fRatio = 5;
}
