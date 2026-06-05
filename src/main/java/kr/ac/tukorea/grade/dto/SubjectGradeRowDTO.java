package kr.ac.tukorea.grade.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SubjectGradeRowDTO {
    private Long studentId;
    private String studentNumber;
    private String name;
    private String status;
    private Double attendanceScore;
    private Double midtermScore;
    private Double finalScore;
    private Double assignmentScore;
}
