package kr.ac.tukorea.grade.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DeptOverviewDTO {
    private String department;
    private long studentCount;
    private long activeCount;
    private long inactiveCount;
    private long subjectCount;
}
