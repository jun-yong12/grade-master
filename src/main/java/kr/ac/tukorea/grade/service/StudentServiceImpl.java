package kr.ac.tukorea.grade.service;

import kr.ac.tukorea.grade.dto.GradeDTO;
import kr.ac.tukorea.grade.dto.PageDTO;
import kr.ac.tukorea.grade.dto.ReportCardDTO;
import kr.ac.tukorea.grade.dto.StudentDTO;
import kr.ac.tukorea.grade.mapper.GradeMapper;
import kr.ac.tukorea.grade.mapper.StudentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentServiceImpl implements StudentService {

    private final StudentMapper studentMapper;
    private final GradeMapper gradeMapper;

    @Override
    public List<StudentDTO> findAll(PageDTO page) {
        return studentMapper.findAll(page);
    }

    @Override
    public long countAll(PageDTO page) {
        return studentMapper.countAll(page);
    }

    @Override
    public StudentDTO findById(Long id) {
        StudentDTO student = studentMapper.findById(id);
        if (student == null) throw new IllegalArgumentException("학생을 찾을 수 없습니다. ID: " + id);
        return student;
    }

    @Override
    @Transactional
    public void create(StudentDTO student) {
        if (studentMapper.existsByStudentNumber(student.getStudentNumber())) {
            throw new IllegalArgumentException("이미 존재하는 학번입니다: " + student.getStudentNumber());
        }
        studentMapper.insert(student);
    }

    @Override
    @Transactional
    public void update(StudentDTO student) {
        StudentDTO existing = findById(student.getId());
        // 학번 중복 체크 (자신 제외)
        StudentDTO byNum = studentMapper.findByStudentNumber(student.getStudentNumber());
        if (byNum != null && !byNum.getId().equals(student.getId())) {
            throw new IllegalArgumentException("이미 존재하는 학번입니다: " + student.getStudentNumber());
        }
        studentMapper.update(student);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        findById(id); // 존재 확인
        studentMapper.deleteById(id);
    }

    @Override
    public ReportCardDTO getReportCard(Long studentId, String semester) {
        StudentDTO student = findById(studentId);
        List<GradeDTO> grades = gradeMapper.findByStudentIdAndSemester(studentId, semester);
        Double avg = gradeMapper.findAverageByStudentIdAndSemester(studentId, semester);
        int rank = studentMapper.countRank(studentId, semester);

        return ReportCardDTO.builder()
                .studentId(student.getId())
                .studentNumber(student.getStudentNumber())
                .name(student.getName())
                .grade(student.getGrade())
                .department(student.getDepartment())
                .semester(semester)
                .grades(grades)
                .average(avg != null ? Math.round(avg * 10) / 10.0 : 0.0)
                .rank(rank)
                .build();
    }
}
