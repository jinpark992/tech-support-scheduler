package com.boot.techsupportscheduler.support.dao;

import com.boot.techsupportscheduler.support.vo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {

    User findByLoginId(@Param("loginId") String loginId);

    int insertUser(User user);

    // ✅ 내정보 조회
    User findByUserId(@Param("userId") Long userId);

    // ✅ 이름 수정
    int updateName(@Param("userId") Long userId,
                   @Param("name") String name);

    // ✅ 비밀번호(해시) 수정
    int updatePasswordHash(@Param("userId") Long userId,
                           @Param("passwordHash") String passwordHash);
}
