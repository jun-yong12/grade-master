package kr.ac.tukorea.grade.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.ac.tukorea.grade.dto.GradeDTO;
import kr.ac.tukorea.grade.dto.PageDTO;
import kr.ac.tukorea.grade.dto.StudentDTO;
import kr.ac.tukorea.grade.service.GradeService;
import kr.ac.tukorea.grade.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.validation.FieldError;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Tag(name = "학생 관리", description = "학생 CRUD 및 성적표 API")
@Controller
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private final GradeService gradeService;

    // ── 학생 목록 (페이징 + 검색) ──────────────────────
    @Operation(summary = "학생 목록 조회")
    @GetMapping
    public String list(@ModelAttribute PageDTO page, Model model) {
        List<StudentDTO> students = studentService.findAll(page);
        long totalCount = studentService.countAll(page);
        int totalPages = page.getTotalPages(totalCount);

        model.addAttribute("students", students);
        model.addAttribute("page", page);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("totalPages", totalPages);
        return "board/list";
    }

    // ── 학생 등록 폼 ───────────────────────────────────
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("studentForm", new StudentDTO());
        return "board/form";
    }

    // ── 학생 등록 처리 ─────────────────────────────────
    @PostMapping
    public String create(@Valid @ModelAttribute("studentForm") StudentDTO student,
                         BindingResult result, RedirectAttributes ra) {
        if (result.hasErrors()) return "board/form";
        try {
            studentService.create(student);
            ra.addFlashAttribute("success", "학생이 등록되었습니다.");
        } catch (IllegalArgumentException e) {
            result.rejectValue("studentNumber", "duplicate", e.getMessage());
            return "board/form";
        }
        return "redirect:/students";
    }

    // ── 학생 등록 (모달 AJAX) ──────────────────────────
    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> createAjax(
            @Valid @ModelAttribute StudentDTO student, BindingResult result) {
        Map<String, Object> resp = new LinkedHashMap<>();
        if (result.hasErrors()) {
            resp.put("success", false);
            resp.put("errors", result.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage, (a, b) -> a)));
            return ResponseEntity.badRequest().body(resp);
        }
        try {
            studentService.create(student);
            resp.put("success", true);
            return ResponseEntity.ok(resp);
        } catch (IllegalArgumentException e) {
            resp.put("success", false);
            resp.put("errors", Map.of("studentNumber", e.getMessage()));
            return ResponseEntity.badRequest().body(resp);
        }
    }

    // ── 학생 상세 + 성적 목록 ──────────────────────────
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("student", studentService.findById(id));
        model.addAttribute("grades", gradeService.findByStudentId(id));
        model.addAttribute("gradeForm", GradeDTO.builder().studentId(id).build());
        return "board/detail";
    }

    // ── 학생 수정 폼 ───────────────────────────────────
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("studentForm", studentService.findById(id));
        model.addAttribute("studentId", id);
        return "board/form";
    }

    // ── 학생 수정 처리 ─────────────────────────────────
    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("studentForm") StudentDTO student,
                         BindingResult result, RedirectAttributes ra) {
        if (result.hasErrors()) return "board/form";
        student.setId(id);
        studentService.update(student);
        ra.addFlashAttribute("success", "학생 정보가 수정되었습니다.");
        return "redirect:/students";
    }

    // ── 학생 삭제 ──────────────────────────────────────
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        studentService.delete(id);
        ra.addFlashAttribute("success", "학생이 삭제되었습니다.");
        return "redirect:/students";
    }

    // ── 성적표 조회 ────────────────────────────────────
    @GetMapping("/{id}/report")
    public String report(@PathVariable Long id,
                          @RequestParam(defaultValue = "2024-1") String semester,
                          Model model) {
        model.addAttribute("report", studentService.getReportCard(id, semester));
        model.addAttribute("semester", semester);
        return "board/report";
    }
}
