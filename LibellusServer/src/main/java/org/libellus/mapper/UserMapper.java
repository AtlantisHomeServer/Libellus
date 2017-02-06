package org.libellus.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.libellus.domain.UserVO;
import org.springframework.security.core.GrantedAuthority;

@Mapper
public interface UserMapper {
    public UserVO readUser(String username);
    public List<GrantedAuthority> readAuthority(String username);
    public void createUser(UserVO user);
    public void createAuthority(UserVO user);
    public void deleteUser(String username);
    public void deleteAuthority(String username);
}
