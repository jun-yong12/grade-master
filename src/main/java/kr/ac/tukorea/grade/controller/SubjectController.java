package kr.ac.tukorea.grade.controller;

import kr.ac.tukorea.grade.dto.SubjectDTO;
import kr.ac.tukorea.grade.dto.SubjectGradeEntryDTO;
import kr.ac.tukorea.grade.dto.SubjectGradeRatioDTO;
import kr.ac.tukorea.grade.dto.SubjectGradeWeightDTO;
import kr.ac.tukorea.grade.mapper.SubjectGradeMapper;
import kr.ac.tukorea.grade.mapper.SubjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @PostMapping
    @ResponseBody
    public ResponseEntity<Map<String, Object>> create(@RequestBody SubjectDTO subject) {
        subjectMapper.insert(subject);
        return ResponseEntity.ok(Map.of("success", true, "id", subject.getId()));
    }

    @PostMapping("/{id}/update")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> update(@PathVariable Long id, @RequestBody SubjectDTO subject) {
        subject.setId(id);
        subjectMapper.update(subject);
        return ResponseEntity.ok(Map.of("success", true));
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        subjectGradeMapper.deleteBySubjectId(id);
        subjectGradeMapper.deleteWeightsBySubjectId(id);
        subjectGradeMapper.deleteRatiosBySubjectId(id);
        subjectMapper.deleteById(id);
        ra.addFlashAttribute("success", "과목이 삭제되었습니다.");
        return "redirect:/subjects";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        SubjectGradeWeightDTO weights = subjectGradeMapper.findWeightsBySubject(id);
        if (weights == null) {
            weights = SubjectGradeWeightDTO.builder().subjectId(id).build();
        }
        SubjectGradeRatioDTO ratios = subjectGradeMapper.findRatiosBySubject(id);
        if (ratios == null) {
            ratios = SubjectGradeRatioDTO.builder().subjectId(id).build();
        }
        model.addAttribute("subject", subjectMapper.findById(id));
        model.addAttribute("weights", weights);
        model.addAttribute("ratios", ratios);
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
        if (total > 100) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "배점 합계가 100%를 초과할 수 없습니다. (현재 " + total + "%)"));
        }
        weights.setSubjectId(id);
        subjectGradeMapper.saveOrUpdateWeights(weights);
        return ResponseEntity.ok(Map.of("success", true));
    }

    @PostMapping("/{id}/ratios")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> saveRatios(
            @PathVariable Long id,
            @RequestBody SubjectGradeRatioDTO ratios) {
        int total = ratios.getARatio() + ratios.getBRatio() + ratios.getCRatio()
                  + ratios.getDRatio() + ratios.getFRatio();
        if (total > 100) {
            return ResponseEntity.badRequest().body(Map.of("success", false,
                "message", "학점 비율 합계가 100%를 초과할 수 없습니다. (현재 " + total + "%)"));
        }
        ratios.setSubjectId(id);
        subjectGradeMapper.saveOrUpdateRatios(ratios);
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
