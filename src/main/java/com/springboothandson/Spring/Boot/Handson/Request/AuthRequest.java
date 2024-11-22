package com.springboothandson.Spring.Boot.Handson.Request;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class AuthRequest {
    private String username;
    private String password;
}
