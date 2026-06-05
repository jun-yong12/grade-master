package kr.ac.tukorea.grade.mapper;

import kr.ac.tukorea.grade.dto.GradeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GradeMapper {

    List<GradeDTO> findByStudentId(@Param("studentId") Long studentId);

    List<GradeDTO> findByStudentIdAndSemester(@Param("studentId") Long studentId,
                                               @Param("semester") String semester);

    GradeDTO findById(@Param("id") Long id);

    void insert(GradeDTO grade);

    void update(GradeDTO grade);

    void deleteById(@Param("id") Long id);

    Double findAverageByStudentIdAndSemester(@Param("studentId") Long studentId,
                                              @Param("semester") String semester);
}
