package com.example.springsecuritybasic.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 유저 등록
     *
     * @param username username
     * @param password password
     * @return 유저 권한을 가지고 있는 유저
     */
    public User signup(
            String username,
            String password
    ) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new AlreadyRegisteredUserException();
        }
        return userRepository.save(new User(username, passwordEncoder.encode(password), "ROLE_USER"));
    }

    /**
     * 관리자 등록
     *
     * @param username username
     * @param password password
     * @return 관리자 권한을 가지고 있는 유저
     */
    public User signupAdmin(
            String username,
            String password
    ) {
        userRepository.findByUsername(username)
                .ifPresent(user -> {
                    throw new AlreadyRegisteredUserException();
                });

//        if (userRepository.findByUsername(username).isPresent()) {
//            throw new AlreadyRegisteredUserException();
//        }
        return userRepository.save(new User(username, passwordEncoder.encode(password), "ROLE_ADMIN"));
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User with username " + username + " not found."));
    }
}