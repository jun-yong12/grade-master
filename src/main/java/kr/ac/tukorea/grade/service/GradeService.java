package kr.ac.tukorea.grade.service;

import kr.ac.tukorea.grade.dto.GradeDTO;
import java.util.List;

public interface GradeService {
    List<GradeDTO> findByStudentId(Long studentId);
    void create(GradeDTO grade);
    void update(Long gradeId, GradeDTO grade);
    void delete(Long gradeId);
}
