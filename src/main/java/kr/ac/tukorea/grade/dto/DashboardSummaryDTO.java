package kr.ac.tukorea.grade.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DashboardSummaryDTO {
    private long totalStudents;
    private long activeStudents;
    private long inactiveStudents;
    private long totalSubjects;
    private long totalDepartments;
}
