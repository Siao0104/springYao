package com.example.spring_yao.jwt;

import com.example.spring_yao.entity.UserBasicEntity;
import com.example.spring_yao.jwt.userauthority.UserAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class UserBasicDetailsServiceImpl implements UserDetails {

    private final UserBasicEntity userBasicEntity;

    public UserBasicDetailsServiceImpl(UserBasicEntity user) {
        this.userBasicEntity = user;
    }

    // 必須覆寫的方法
    public String getUsername() { return userBasicEntity.getAccount(); }
    public String getPassword() { return userBasicEntity.getPassword(); }

    public boolean isEnabled() { return userBasicEntity.isEnabled(); }
    public boolean isAccountNonLocked() { return true; }
    public boolean isCredentialsNonExpired() { return true; }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(userBasicEntity.getAuthority());
    }

    public boolean isAccountNonExpired() {
        if (userBasicEntity.getExpiryDate() == null) {
            return true;
        }

        return new Date().compareTo(userBasicEntity.getExpiryDate())<0;
    }

    // 自定義的 public 方法
    public Integer getId() { return userBasicEntity.getId(); }
    public UserAuthority getUserAuthority() { return userBasicEntity.getAuthority(); }
    public Date getExpiryDate() { return userBasicEntity.getExpiryDate(); }

    public String getRealUserName() { return userBasicEntity.getUserName(); }
}
