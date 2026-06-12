package kr.ac.tukorea.grade.mapper;

import kr.ac.tukorea.grade.dto.PageDTO;
import kr.ac.tukorea.grade.dto.StudentDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudentMapper {

    List<StudentDTO> findAll(@Param("page") PageDTO page);
    List<StudentDTO> findAllNoLimit(@Param("page") PageDTO page);
    List<String> findDistinctDepartments();

    long countAll(@Param("page") PageDTO page);

    StudentDTO findById(@Param("id") Long id);

    StudentDTO findByStudentNumber(@Param("studentNumber") String studentNumber);

    boolean existsByStudentNumber(@Param("studentNumber") String studentNumber);

    List<StudentDTO> findByDepartment(@Param("department") String department);

    void insert(StudentDTO student);

    void update(StudentDTO student);

    void deleteById(@Param("id") Long id);

    // 반 내 평균 기준 등수 계산
    int countRank(@Param("studentId") Long studentId, @Param("semester") String semester);
}
