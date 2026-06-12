package kr.ac.tukorea.grade.mapper;

import kr.ac.tukorea.grade.dto.DashboardSummaryDTO;
import kr.ac.tukorea.grade.dto.DeptOverviewDTO;
import kr.ac.tukorea.grade.dto.GradeInputStatusDTO;
import kr.ac.tukorea.grade.dto.SubjectGradeStatDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DashboardMapper {
    DashboardSummaryDTO getSummary();
    List<DeptOverviewDTO> getDeptOverview();
    List<SubjectGradeStatDTO> getSubjectGradeStats();
    List<GradeInputStatusDTO> getGradeInputStatus();
}
