package com.boot.techsupportscheduler.support.dao;

import com.boot.techsupportscheduler.support.vo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LoginDao {
    User findByLoginId(@Param("loginId") String loginId);
}
