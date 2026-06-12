package kr.ac.tukorea.grade.controller;

import kr.ac.tukorea.grade.mapper.DashboardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardMapper dashboardMapper;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("summary",       dashboardMapper.getSummary());
        model.addAttribute("deptList",      dashboardMapper.getDeptOverview());
        model.addAttribute("subjectStats",  dashboardMapper.getSubjectGradeStats());
        model.addAttribute("gradeStatus",   dashboardMapper.getGradeInputStatus());
        return "dashboard/index";
    }
}
