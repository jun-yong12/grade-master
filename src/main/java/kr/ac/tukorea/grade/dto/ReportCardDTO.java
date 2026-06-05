package kr.ac.tukorea.grade.dto;

import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ReportCardDTO {
    private Long studentId;
    private String studentNumber;
    private String name;
    private String grade;
    private String department;
    private String semester;
    private List<GradeDTO> grades;
    private double average;
    private int rank;

    public String getLetterGrade() {
        if (average >= 90) return "A";
        else if (average >= 80) return "B";
        else if (average >= 70) return "C";
        else if (average >= 60) return "D";
        else return "F";
    }
}
