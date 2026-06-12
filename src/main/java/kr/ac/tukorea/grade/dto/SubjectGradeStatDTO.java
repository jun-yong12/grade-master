package kr.ac.tukorea.grade.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SubjectGradeStatDTO {
    private Long id;
    private String subject;
    private String department;
    private String professor;
    private int gradedCount;
    private Double avgScore;
    private Double maxScore;
    private Double minScore;
}
