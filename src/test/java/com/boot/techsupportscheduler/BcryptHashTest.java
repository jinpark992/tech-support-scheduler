package com.boot.techsupportscheduler;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;

class BcryptHashTest {

    @Test
    void generate_hash_for_1234() {
        String hash = BCrypt.hashpw("1234", BCrypt.gensalt());
        System.out.println("BCrypt hash = " + hash);
        System.out.println("matches? " + BCrypt.checkpw("1234", hash));
    }
}
