package kr.ac.tukorea.grade.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GradeInputStatusDTO {
    private Long id;
    private String subject;
    private String department;
    private String professor;
    private int totalStudents;
    private int gradedStudents;
    private int ungradedStudents;
}
