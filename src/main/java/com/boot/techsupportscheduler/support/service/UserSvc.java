package com.boot.techsupportscheduler.support.service;

import com.boot.techsupportscheduler.support.dao.UserDao;
import com.boot.techsupportscheduler.support.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * ✅ UserSvc: "회원정보 수정/비밀번호 변경" 비즈니스 로직 담당
 * - Controller는 요청/응답만 처리
 * - Service는 검증 + DAO 호출(DB 반영)을 담당
 */
@Service
@RequiredArgsConstructor
public class UserSvc {

    private final UserDao userDao;

    /**
     * ✅ 내 정보 조회(화면 출력용)
     * - userId로 users 테이블 조회
     */
    public User getMyInfo(Long userId) {
        User u = userDao.findByUserId(userId);
        if (u == null) throw new IllegalArgumentException("사용자 정보를 찾을 수 없어.");
        return u;
    }

    /**
     * ✅ 내 이름 수정
     * - name 검증 후 UPDATE 수행
     */
    public void updateMyName(Long userId, String name) {
        if (!StringUtils.hasText(name)) throw new IllegalArgumentException("이름을 입력해.");
        String v = name.trim();
        if (v.length() < 2) throw new IllegalArgumentException("이름은 2자 이상으로 입력해.");

        int updated = userDao.updateName(userId, v);
        if (updated != 1) throw new IllegalArgumentException("이름 수정 실패(DB 문제 또는 삭제된 사용자).");
    }

    /**
     * ✅ 내 비밀번호 변경
     * - 현재 비번 확인(BCrypt) → 새 비번 해시 생성 → password_hash 업데이트
     * - 비번은 절대 평문 저장 금지(해시만 저장)
     */
    public void changeMyPassword(Long userId, String currentPw, String newPw, String newPwConfirm) {
        if (!StringUtils.hasText(currentPw)) throw new IllegalArgumentException("현재 비밀번호를 입력해.");
        if (!StringUtils.hasText(newPw)) throw new IllegalArgumentException("새 비밀번호를 입력해.");
        if (newPw.length() < 4) throw new IllegalArgumentException("새 비밀번호는 4자 이상 입력해.");
        if (!newPw.equals(newPwConfirm)) throw new IllegalArgumentException("새 비밀번호 확인이 일치하지 않아.");
        if (newPw.equals(currentPw)) throw new IllegalArgumentException("현재 비밀번호와 다른 비밀번호로 바꿔.");

        // ✅ DB에서 현재 password_hash 가져오기
        User u = userDao.findByUserId(userId);
        if (u == null) throw new IllegalArgumentException("사용자 정보를 찾을 수 없어.");

        // ✅ 현재 비밀번호 검증
        if (!BCrypt.checkpw(currentPw, u.getPasswordHash())) {
            throw new IllegalArgumentException("현재 비밀번호가 틀렸어.");
        }

        // ✅ 새 비밀번호는 해시로 변환해서 저장
        String newHash = BCrypt.hashpw(newPw, BCrypt.gensalt());

        int updated = userDao.updatePasswordHash(userId, newHash);
        if (updated != 1) throw new IllegalArgumentException("비밀번호 변경 실패(DB 문제 또는 삭제된 사용자).");
    }
}
