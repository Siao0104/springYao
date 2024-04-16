package com.example.spring_yao.jwt;

import com.example.spring_yao.entity.UserBasicEntity;
import com.example.spring_yao.repository.UserBasicRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserBasicRepository userBasicRepository;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        UserBasicEntity userBasicEntity = userBasicRepository.getByAccount(account);
        if (userBasicEntity == null) {
            throw new UsernameNotFoundException(STR."查無此帳號: \{account}");
        }

        return new UserBasicDetailsServiceImpl(userBasicEntity);
    }
}
