package kr.ac.tukorea.grade.service;

import kr.ac.tukorea.grade.dto.PageDTO;
import kr.ac.tukorea.grade.dto.ReportCardDTO;
import kr.ac.tukorea.grade.dto.StudentDTO;

import java.util.List;
import java.util.Map;

public interface StudentService {
    List<StudentDTO> findAll(PageDTO page);
    long countAll(PageDTO page);
    StudentDTO findById(Long id);
    void create(StudentDTO student);
    void update(StudentDTO student);
    void delete(Long id);
    ReportCardDTO getReportCard(Long studentId, String semester);
}
