package com.boot.techsupportscheduler.support.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NoticeComment {
    private Long commentId;
    private Long noticeId;
    private String writer;
    private String content;
    private int likeCount;
    private LocalDateTime createdAt;
}
