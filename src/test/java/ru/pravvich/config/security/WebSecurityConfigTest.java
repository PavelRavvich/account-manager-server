package ru.pravvich.config.security;

import org.junit.Test;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

public class WebSecurityConfigTest {
    @Test
    public void encodePassword() {
        System.out.println(new ShaPasswordEncoder().encodePassword("12345", ""));
    }
}