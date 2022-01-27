package com.blck_rbbit.gbspringlessonschapter1.services;

import com.blck_rbbit.gbspringlessonschapter1.entities.Role;
import com.blck_rbbit.gbspringlessonschapter1.entities.User;
import com.blck_rbbit.gbspringlessonschapter1.exceptions.UserAllReadyExistsException;
import com.blck_rbbit.gbspringlessonschapter1.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles())
        );
    }
    
//    @Transactional
//    public void createNewUser(String name, String email, String password) {
//        if (userRepository.findByUsername(name).isPresent() || userRepository.findByEmail(email).isPresent()) {
//            log.debug("User with this name or email exists");
//            return;
//        }
//        User user = new User();
//        user.setUsername(name);
//        user.setPassword(passwordEncoder.encode(password));
//        user.setEmail(email);
//        user.setRoles(Collections.singleton(new Role("ROLE_USER")));
//        userRepository.saveAndFlush(user);
//        log.debug("New user registered, login {}", name);
//    }
    
    @Transactional
    public UserDetails createNewUser(User newUser) throws UserAllReadyExistsException {
        if (findByUsername(newUser.getUsername()).isPresent() ||
                userRepository.findByEmail(newUser.getEmail()).isPresent()) {
            throw new UserAllReadyExistsException(Collections.singletonList("User with this name or email exists"));
        }
        newUser.setUsername(newUser.getUsername());
        newUser.setEmail(newUser.getEmail());
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.setRoles(Collections.singleton(new Role("ROLE_USER")));
        userRepository.save(newUser);
        log.debug("New user registered, login {}", newUser.getUsername());
        return new org.springframework.security.core.userdetails.User(
                newUser.getUsername(), newUser.getPassword(), mapRolesToAuthorities(newUser.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}