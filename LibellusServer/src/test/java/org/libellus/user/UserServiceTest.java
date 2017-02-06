package org.libellus.user;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.libellus.Application;
import org.libellus.domain.UserVO;
import org.libellus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class UserServiceTest {
	
	@Autowired UserService userService;
	
	private UserVO user;
	
	@Before
	public void setup() {
		user = new UserVO();
		user.setUsername("user1");
		user.setPassword("pass1");
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setName("USER1");
		user.setCredentialsNonExpired(true);
		user.setEnabled(true);
		user.setAuthorities(AuthorityUtils.createAuthorityList("USER"));
	}
	
	@Test
	public void createUserTest() {
		userService.deleteUser(user.getUsername());
		userService.createUser(user);
		UserVO user1 = userService.readUser(user.getUsername());
		assertThat(user1.getUsername(), is(user.getUsername()));
		
		PasswordEncoder passwordEncoder = userService.passwordEncoder();
		assertThat(passwordEncoder.matches("pass1", user1.getPassword()), is(true));
	
		Collection<? extends GrantedAuthority> authorities1 = user.getAuthorities();
		Iterator<? extends GrantedAuthority> it = authorities1.iterator();
		Collection<GrantedAuthority> authorities = (Collection< GrantedAuthority >) user.getAuthorities();
		while(it.hasNext()) {
			GrantedAuthority authority = it.next();
			assertThat(authorities, hasItem(new SimpleGrantedAuthority(authority.getAuthority())));
		}
	}
	
//	@After
//	public void deleteUser() {
//		userService.deleteUser(user.getUsername());
//	}
}
