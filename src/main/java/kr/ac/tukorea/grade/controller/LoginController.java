package kr.ac.tukorea.grade.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import kr.ac.tukorea.grade.dto.LoginForm;
import kr.ac.tukorea.grade.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserRepository userRepository;

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginForm form,
                        BindingResult result,
                        HttpSession session,
                        Model model) {
        if (result.hasErrors()) return "login";

        if (!userRepository.authenticate(form.getUsername(), form.getPassword())) {
            log.warn("[Login] 인증 실패: {}", form.getUsername());
            model.addAttribute("loginError", true);
            return "login";
        }

        log.info("[Login] 로그인 성공: {}", form.getUsername());
        session.setAttribute("loginUser", form.getUsername());
        session.setAttribute("loginRole", userRepository.getRoleByUsername(form.getUsername()));
        return "redirect:/students";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
