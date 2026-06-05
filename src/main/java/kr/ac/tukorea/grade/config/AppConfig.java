package kr.ac.tukorea.grade.config;

import kr.ac.tukorea.grade.repository.UserRepository;
import kr.ac.tukorea.grade.util.PasswordUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * AppConfig — @Bean 수동 등록 (IoC/DI 시연)
 */
@Configuration
public class AppConfig {

    /**
     * UserRepository 초기화 (BCrypt 기본 계정 세팅)
     */
    @Bean
    public UserRepository userRepository(PasswordUtil passwordUtil) {
        UserRepository repo = new UserRepository(passwordUtil);
        repo.init();
        return repo;
    }
}
