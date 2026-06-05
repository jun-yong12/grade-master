package kr.ac.tukorea.grade.mapper;

import kr.ac.tukorea.grade.dto.SubjectGradeEntryDTO;
import kr.ac.tukorea.grade.dto.SubjectGradeRowDTO;
import kr.ac.tukorea.grade.dto.SubjectGradeWeightDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SubjectGradeMapper {
    SubjectGradeWeightDTO findWeightsBySubject(@Param("subjectId") Long subjectId);
    void saveOrUpdateWeights(SubjectGradeWeightDTO weights);

    List<SubjectGradeRowDTO> findGradesBySubject(@Param("subjectId") Long subjectId);
    void saveOrUpdateGrade(@Param("subjectId") Long subjectId, @Param("entry") SubjectGradeEntryDTO entry);
}
