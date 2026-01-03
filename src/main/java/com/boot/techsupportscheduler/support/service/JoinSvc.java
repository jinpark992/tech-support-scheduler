package com.boot.techsupportscheduler.support.service;

import com.boot.techsupportscheduler.support.dao.UserDao;
import com.boot.techsupportscheduler.support.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCrypt;  // ✅ 너 프로젝트 로그인과 동일
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JoinSvc {

    private final UserDao userDao;

    /**
     * ✅ 회원가입 핵심 로직
     *
     * 흐름:
     * 1) 입력값 검증
     * 2) role 값 검증(화이트리스트)
     * 3) loginId 중복 체크(사용자 친화적인 에러용)
     * 4) 비밀번호 BCrypt 해시 생성 (password_hash에 저장할 값)
     * 5) users 테이블 INSERT
     *
     * 주의:
     * - password_hash에 평문 저장하면 로그인 검증(BCrypt.checkpw)에서 100% 실패함
     * - role은 ROLE_ADMIN/ROLE_SALES/ROLE_SUPPORT 형태로 저장해야 인터셉터 권한이 맞음
     */
    public void join(String loginId,
                     String password,
                     String passwordConfirm,
                     String name,
                     String role) {

        // 1) 입력 검증
        if (!StringUtils.hasText(loginId) || loginId.length() < 3) {
            throw new IllegalArgumentException("아이디는 3자 이상 입력해.");
        }
        if (!StringUtils.hasText(password) || password.length() < 4) {
            throw new IllegalArgumentException("비밀번호는 4자 이상 입력해.");
        }
        if (!password.equals(passwordConfirm)) {
            throw new IllegalArgumentException("비밀번호 확인이 일치하지 않아.");
        }
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("이름을 입력해.");
        }

        // 2) role 값 검증 (프론트 조작 방어 + 인터셉터와 값 통일)
        List<String> allowRoles = List.of("ROLE_ADMIN", "ROLE_SALES", "ROLE_SUPPORT");
        if (!allowRoles.contains(role)) {
            throw new IllegalArgumentException("권한 값이 올바르지 않아.");
        }

        // 3) 중복 체크(친절한 메시지용)
        // - 동시 요청에서는 뚫릴 수 있으니 DB UNIQUE(login_id)가 최종 방어
        if (userDao.findByLoginId(loginId) != null) {
            throw new IllegalArgumentException("이미 사용 중인 아이디야.");
        }

        // 4) 비밀번호 BCrypt 해시 생성
        // - DB users.password_hash에 저장되는 값은 $2a$... 형태
        // - 로그인할 때 BCrypt.checkpw(입력비번, 저장해시)로 비교
        String hash = BCrypt.hashpw(password, BCrypt.gensalt());

        // 5) INSERT 데이터 구성
        User u = new User();
        u.setLoginId(loginId);
        u.setPasswordHash(hash);
        u.setName(name);
        u.setRole(role);

        // 6) 저장
        try {
            userDao.insertUser(u);
        } catch (DuplicateKeyException e) {
            // DB에 UNIQUE(login_id)가 걸려있으면 여기로 올 수 있음
            throw new IllegalArgumentException("이미 사용 중인 아이디야.");
        }
    }
}
