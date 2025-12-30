package com.boot.techsupportscheduler.support.dao;

import com.boot.techsupportscheduler.support.vo.Notice;
import com.boot.techsupportscheduler.support.vo.Support;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SupportDao {

    List<Support> doList();

    Support doDetail(Long id);

    void doInsert(Support support);

    void doDelete(Long id);
}
