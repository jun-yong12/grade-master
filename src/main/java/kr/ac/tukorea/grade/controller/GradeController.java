package kr.ac.tukorea.grade.controller;

import jakarta.validation.Valid;
import kr.ac.tukorea.grade.dto.GradeDTO;
import kr.ac.tukorea.grade.service.GradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/grades")
public class GradeController {

    private final GradeService gradeService;

    @PostMapping
    public String create(@Valid @ModelAttribute GradeDTO grade,
                         BindingResult result, RedirectAttributes ra) {
        if (result.hasErrors()) {
            ra.addFlashAttribute("error", "입력값을 확인해 주세요.");
        } else {
            gradeService.create(grade);
            ra.addFlashAttribute("success", "성적이 등록되었습니다.");
        }
        return "redirect:/students/" + grade.getStudentId();
    }

    @PostMapping("/{gradeId}/delete")
    public String delete(@PathVariable Long gradeId,
                          @RequestParam Long studentId,
                          RedirectAttributes ra) {
        gradeService.delete(gradeId);
        ra.addFlashAttribute("success", "성적이 삭제되었습니다.");
        return "redirect:/students/" + studentId;
    }
}
