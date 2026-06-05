package kr.ac.tukorea.grade.service;

import kr.ac.tukorea.grade.dto.GradeDTO;
import kr.ac.tukorea.grade.mapper.GradeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GradeServiceImpl implements GradeService {

    private final GradeMapper gradeMapper;

    @Override
    public List<GradeDTO> findByStudentId(Long studentId) {
        return gradeMapper.findByStudentId(studentId);
    }

    @Override
    @Transactional
    public void create(GradeDTO grade) {
        gradeMapper.insert(grade);
    }

    @Override
    @Transactional
    public void update(Long gradeId, GradeDTO grade) {
        grade.setId(gradeId);
        gradeMapper.update(grade);
    }

    @Override
    @Transactional
    public void delete(Long gradeId) {
        gradeMapper.deleteById(gradeId);
    }
}
