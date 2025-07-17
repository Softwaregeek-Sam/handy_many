package com.sumit.handymany.user.service;

import com.sumit.handymany.user.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private  final UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        return userRepo.findByPhone(phone)
                .map(user-> User.withUsername(user.getPhone())
                        .password("")
                        .authorities("CLIENT")
                        .build())
                .orElseThrow( () -> new UsernameNotFoundException("user not found"));


    }

    public com.sumit.handymany.user.model.User findUserByPhone(String phone) {
        return userRepo.findByPhone(phone)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
