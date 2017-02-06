package org.libellus.controller;

import javax.servlet.http.HttpSession;

import org.libellus.domain.AuthenticationRequest;
import org.libellus.domain.AuthenticationToken;
import org.libellus.domain.UserVO;
import org.libellus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired AuthenticationManager authenticationManager;
    @Autowired UserService userService;
    
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public AuthenticationToken login(
              @RequestBody AuthenticationRequest authenticationRequest,
              HttpSession session
              ) {
         String username = authenticationRequest.getUsername();
         String password = authenticationRequest.getPassword();
         
         UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
         Authentication authentication = authenticationManager.authenticate(token);
         SecurityContextHolder.getContext().setAuthentication(authentication);
         session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                   SecurityContextHolder.getContext());
         
         UserVO user = userService.readUser(username);
         return new AuthenticationToken(user.getName(), user.getAuthorities(), session.getId());
    }
}
