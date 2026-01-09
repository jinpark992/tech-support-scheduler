package com.boot.techsupportscheduler.support.vo;

import lombok.Data;

@Data
public class NoticeComment {
    private Long commentId;
    private Long noticeId;
    private String writer;
    private String content;
    private int likeCount;      // ❤️ 좋아요 수
    private String createdAt;
}