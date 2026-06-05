package kr.ac.tukorea.grade.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SubjectDTO {
    private Long id;
    private String subject;
    private String department;
    private String professor;
    private int studentCount;
}
