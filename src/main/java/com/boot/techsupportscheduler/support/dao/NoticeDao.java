package com.boot.techsupportscheduler.support.dao;

import com.boot.techsupportscheduler.support.vo.Notice;
import com.boot.techsupportscheduler.support.vo.NoticeComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;   // ✅ 추가

import java.util.List;
import java.util.Map;

@Mapper
public interface NoticeDao {

    List<Notice> doList();

    int doDelete(@Param("noticeId") Long noticeId);      // ✅ 수정

    int doInsert(Notice notice);

    Notice doDetail(@Param("noticeId") Long noticeId);   // ✅ 수정

    void increaseViews(@Param("noticeId") Long noticeId);// ✅ 수정

    Notice doPrev(@Param("noticeId") Long noticeId);     // ✅ 수정

    Notice doNext(@Param("noticeId") Long noticeId);     // ✅ 수정

    int doUpdate(Notice notice);

    List<Notice> doSearch(Map<String, Object> params);
    int doSearchCount(Map<String, Object> params);

    int countSearch(Map<String, Object> p);
    List<Notice> searchPaged(Map<String, Object> p);

    List<NoticeComment> selectCommentList(Long noticeId);

    void insertComment(NoticeComment comment);

    void increaseLikeCount(Long commentId);

    int selectLikeCount(Long commentId);

    // [1] 좋아요 이력 확인 (누가 눌렀나?)
    int checkLikeHistory(@Param("commentId") Long commentId, @Param("loginId") String loginId);

    // [2] 좋아요 이력 추가
    void insertLikeHistory(@Param("commentId") Long commentId, @Param("loginId") String loginId);

    // [3] 좋아요 이력 삭제
    void deleteLikeHistory(@Param("commentId") Long commentId, @Param("loginId") String loginId);

    // [4] 좋아요 수 감소 (기존 increaseLikeCount 반대)
    void decreaseLikeCount(@Param("commentId") Long commentId);
}
