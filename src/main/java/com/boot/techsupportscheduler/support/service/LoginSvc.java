package com.boot.techsupportscheduler.support.service;

import com.boot.techsupportscheduler.support.dao.LoginDao;
import com.boot.techsupportscheduler.support.vo.SessionUser;
import com.boot.techsupportscheduler.support.vo.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class LoginSvc {

    private final LoginDao loginDao;

    public SessionUser login(String loginId, String rawPw) {

        log.info("[LOGIN] try loginId={}", loginId);

        User u = loginDao.findByLoginId(loginId);
        log.info("[LOGIN] user found? {}", (u != null));

        if (u == null) return null;

        log.info("[LOGIN] db role={}", u.getRole());

        boolean ok = BCrypt.checkpw(rawPw, u.getPasswordHash());
        log.info("[LOGIN] password match? {}", ok);

        if (!ok) return null;

        log.info("[LOGIN] SUCCESS loginId={}", loginId);
        return new SessionUser(u.getUserId(), u.getLoginId(), u.getRole());
    }
}
