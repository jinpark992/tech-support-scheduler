package com.boot.techsupportscheduler.support.dao;

import com.boot.techsupportscheduler.support.vo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {

    /**
     * ✅ login_id로 1건 조회
     * - 회원가입: 아이디 중복 체크
     * - 로그인: 사용자 조회(원하면 LoginDao 대신 이걸로 통일 가능)
     */
    User findByLoginId(@Param("loginId") String loginId);

    /**
     * ✅ 회원가입 INSERT
     * - password_hash는 BCrypt 해시
     * - role은 ROLE_* 형태로 저장
     */
    int insertUser(User user);
}
