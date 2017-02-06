package org.libellus.service;

import java.util.Collection;

import org.libellus.domain.UserVO;
import org.libellus.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	@Autowired UserMapper userMapper;
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserVO user = userMapper.readUser(username);
		user.setAuthorities(getAuthorities(username));
		return user;
	}

	public Collection<GrantedAuthority> getAuthorities(String username) {
		Collection<GrantedAuthority> authorities = userMapper.readAuthority(username);
		return authorities;
	}

	public UserVO readUser(String username) {
		// TODO Auto-generated method stub
		UserVO user = userMapper.readUser(username);
		user.setAuthorities(userMapper.readAuthority(username));
		return user;
	}

	public void createUser(UserVO user) {
		String rawPassword = user.getPassword();
		String encodedPasword = new BCryptPasswordEncoder().encode(rawPassword);
		user.setPassword(encodedPasword);
		userMapper.createUser(user);
		userMapper.createAuthority(user);
	}

	public void deleteUser(String username) {
		// TODO Auto-generated method stub
		userMapper.deleteUser(username);
		userMapper.deleteAuthority(username);
	}

	public PasswordEncoder passwordEncoder() {
		// TODO Auto-generated method stub
		return this.passwordEncoder;
	}
}
