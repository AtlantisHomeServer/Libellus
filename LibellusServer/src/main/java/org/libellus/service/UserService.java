package org.libellus.service;

import java.util.Collection;

import org.libellus.domain.UserVO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface UserService extends UserDetailsService {
	Collection<GrantedAuthority> getAuthorities(String username);
    public UserVO readUser(String username);
    public void createUser(UserVO user);
    public void deleteUser(String username);
    public PasswordEncoder passwordEncoder();
}
