package kr.ac.tukorea.grade.repository;

import kr.ac.tukorea.grade.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 인메모리 사용자 저장소 (BCrypt 비밀번호 해시)
 * 실제 운영 환경에서는 DB로 교체 권장
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final PasswordUtil passwordUtil;

    // username → bcrypt 해시 저장소
    private final Map<String, String> users = new HashMap<>();
    private final Map<String, String> roles = new HashMap<>();

    // 애플리케이션 시작 시 기본 계정 등록
    public void init() {
        String adminHash = passwordUtil.encode("1234");
        String guestHash = passwordUtil.encode("1234");
        users.put("admin", adminHash);
        users.put("guest", guestHash);
        roles.put("admin", "ADMIN");
        roles.put("guest", "USER");
        log.info("기본 계정 초기화 완료 (admin/1234, guest/1234)");
    }

    public Optional<String> findPasswordByUsername(String username) {
        return Optional.ofNullable(users.get(username));
    }

    public String getRoleByUsername(String username) {
        return roles.getOrDefault(username, "USER");
    }

    public boolean authenticate(String username, String rawPassword) {
        return findPasswordByUsername(username)
                .map(hash -> passwordUtil.matches(rawPassword, hash))
                .orElse(false);
    }
}
