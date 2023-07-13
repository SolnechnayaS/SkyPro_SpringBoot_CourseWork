package org.ru.skypro.coursework.AuctionSystem.security;

import org.springframework.security.core.GrantedAuthority;

public class SecurityGrantedAuthorities implements GrantedAuthority {

    private final String authority;

    public SecurityGrantedAuthorities(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
