package kr.ac.tukorea.grade.mapper;

import kr.ac.tukorea.grade.dto.SubjectDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SubjectMapper {
    List<SubjectDTO> findAll();
    SubjectDTO findById(Long id);
    void insert(SubjectDTO subject);
    void update(SubjectDTO subject);
    void deleteById(Long id);
}
