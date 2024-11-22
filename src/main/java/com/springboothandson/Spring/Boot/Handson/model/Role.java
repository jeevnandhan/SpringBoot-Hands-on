package com.springboothandson.Spring.Boot.Handson.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role {
    CONSUMER,
    SELLER
}

//class RoleGrantedAuthority implements GrantedAuthority{
//
//    String role;
//    public RoleGrantedAuthority(String role){
//        this.role = role;
//    }
//    @Override
//    public String getAuthority() {
//        return this.role;
//    }
//}

