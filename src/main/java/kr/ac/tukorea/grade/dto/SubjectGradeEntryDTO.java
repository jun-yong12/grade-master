package kr.ac.tukorea.grade.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SubjectGradeEntryDTO {
    private Long studentId;
    private Double attendanceScore;
    private Double midtermScore;
    private Double finalScore;
    private Double assignmentScore;
}
