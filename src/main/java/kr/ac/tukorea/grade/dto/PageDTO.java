package kr.ac.tukorea.grade.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PageDTO {
    private int page = 1;
    private int size = 10;
    private String keyword;    // 검색어
    private String searchType; // name, studentNumber
    private String sortField = "student_number";
    private String sortDir   = "ASC";

    public int getOffset() {
        return (page - 1) * size;
    }

    public int getTotalPages(long totalCount) {
        return (int) Math.ceil((double) totalCount / size);
    }
}
