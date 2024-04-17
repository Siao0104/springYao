package com.example.spring_yao.jwt.useridentity;

import com.example.spring_yao.entity.UserBasicEntity;
import com.example.spring_yao.jwt.UserBasicDetails;
import com.example.spring_yao.jwt.userauthority.UserAuthority;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserIdentity {

    private UserBasicDetails getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        return "anonymousUser".equals(principal)
                ? new UserBasicDetails(new UserBasicEntity())
                : (UserBasicDetails) principal;
    }

    public String getAccount() { return getUserDetails().getUsername(); }

    public String getUsername() { return getUserDetails().getRealUserName(); }

    public UserAuthority getUserAuthority() { return getUserDetails().getUserAuthority(); }
}
