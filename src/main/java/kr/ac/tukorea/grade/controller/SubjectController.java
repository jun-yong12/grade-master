package kr.ac.tukorea.grade.controller;

import kr.ac.tukorea.grade.dto.SubjectGradeEntryDTO;
import kr.ac.tukorea.grade.dto.SubjectGradeWeightDTO;
import kr.ac.tukorea.grade.mapper.SubjectGradeMapper;
import kr.ac.tukorea.grade.mapper.SubjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/subjects")
public class SubjectController {

    private final SubjectMapper subjectMapper;
    private final SubjectGradeMapper subjectGradeMapper;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("subjects", subjectMapper.findAll());
        return "subject/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        SubjectGradeWeightDTO weights = subjectGradeMapper.findWeightsBySubject(id);
        if (weights == null) {
            weights = SubjectGradeWeightDTO.builder().subjectId(id).build();
        }
        model.addAttribute("subject", subjectMapper.findById(id));
        model.addAttribute("weights", weights);
        model.addAttribute("grades", subjectGradeMapper.findGradesBySubject(id));
        return "subject/detail";
    }

    @PostMapping("/{id}/weights")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> saveWeights(
            @PathVariable Long id,
            @RequestBody SubjectGradeWeightDTO weights) {
        int total = weights.getAttendanceWeight() + weights.getMidtermWeight()
                  + weights.getFinalWeight() + weights.getAssignmentWeight();
        if (total != 100) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "배점 합계가 100%여야 합니다. (현재 " + total + "%)"));
        }
        weights.setSubjectId(id);
        subjectGradeMapper.saveOrUpdateWeights(weights);
        return ResponseEntity.ok(Map.of("success", true));
    }

    @PostMapping("/{id}/grades")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> saveGrades(
            @PathVariable Long id,
            @RequestBody List<SubjectGradeEntryDTO> entries) {
        entries.forEach(e -> subjectGradeMapper.saveOrUpdateGrade(id, e));
        return ResponseEntity.ok(Map.of("success", true));
    }
}
