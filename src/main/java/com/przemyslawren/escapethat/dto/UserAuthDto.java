package com.przemyslawren.escapethat.dto;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

public record UserAuthDto(String username, Collection<? extends GrantedAuthority> authorities) {
}
