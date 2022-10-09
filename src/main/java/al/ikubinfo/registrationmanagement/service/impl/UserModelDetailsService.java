package al.ikubinfo.registrationmanagement.service.impl;

import al.ikubinfo.registrationmanagement.entity.UserEntity;
import al.ikubinfo.registrationmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component("userDetailsService")
public class UserModelDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) {
        Optional<UserEntity> optional = userRepository.findByEmail(email);
        if (optional.isPresent()) {
            UserEntity user = optional.get();
            List<GrantedAuthority> role = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getName()));
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), role);
        }
        return null;
    }
}
