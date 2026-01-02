package com.boot.techsupportscheduler.support.dao;

import com.boot.techsupportscheduler.support.vo.Support;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;   // ✅ 추가

import java.util.List;

@Mapper
public interface SupportDao {

    List<Support> doList();

    Support doDetail(@Param("id") Long id);   // ✅ 수정 (mapper가 #{id}라서 id로 맞춤)

    void doInsert(Support support);

    void doDelete(@Param("id") Long id);     // ✅ 수정

    void doUpdate(Support support);
}
