package com.example.spring_yao.jwt.useridentity;

import com.example.spring_yao.entity.UserBasicEntity;
import com.example.spring_yao.jwt.UserBasicDetailsServiceImpl;
import com.example.spring_yao.jwt.userauthority.UserAuthority;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserIdentity {

    private UserBasicDetailsServiceImpl getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        return "anonymousUser".equals(principal)
                ? new UserBasicDetailsServiceImpl(new UserBasicEntity())
                : (UserBasicDetailsServiceImpl) principal;
    }

    public String getAccount() { return null; }

    public String getUsername() { return null; }

    public UserAuthority getUserAuthority() { return null; }
}
