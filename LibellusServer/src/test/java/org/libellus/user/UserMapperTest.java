package org.libellus.user;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.libellus.Application;
import org.libellus.domain.UserVO;
import org.libellus.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class UserMapperTest {
	@Autowired UserMapper userMapper;
	
	@Test
	public void readUserTest() {
		UserVO user = userMapper.readUser("cusonar");
		assertThat("cusonar", is(user.getUsername()));
		assertThat("YCU", is(user.getName()));
		assertThat("1234", is(user.getPassword()));
	}
	
	@Test
	public void readAuthorityTest() {
		List <GrantedAuthority> authorities = userMapper.readAuthority("cusonar");
		Iterator<GrantedAuthority> it = authorities.iterator();
		
		while(it.hasNext()) {
			GrantedAuthority authority = it.next();
			assertThat(authorities, hasItem(new SimpleGrantedAuthority(authority.getAuthority())));
		}
	}
}
