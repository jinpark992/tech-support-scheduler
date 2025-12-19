package com.boot.techsupportscheduler.support.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/support")
public class HomeCon {

    @GetMapping("/home")
    public String doHome() {
        return "redirect:/support/notice"; // ✅ 공지 목록으로 보내버림
    }


}
