package com.tenco.blog.user;

import com.tenco.blog._core.errors.exception.Exception400;
import com.tenco.blog._core.errors.exception.Exception404;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserJPARepository ur;

    /*
    회원가입처리
     */
    @Transactional // Method level, writing transaction activation
    public User join(UserRequest.JoinDTO joinDTO) {
        // 1 username 중복 check
        ur.findByUsername(joinDTO.getUsername()).ifPresent(user1 -> {
            throw new Exception400("Username already exists");
        });
        return ur.save(joinDTO.toEntity());
    }

    /*
    login
     */
    public User login(UserRequest.LoginDTO loginDTO) {
        return ur.findByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword())
                .orElseThrow(() -> {
                    return new Exception400("Invalid username or password");
                });
    }

    /*
    회원 정보 조회
     */
    public User findById(Long id) {
        return ur.findById(id).orElseThrow(() -> {
           log.warn("User not found with id : {}", id);
           return new Exception404("User not found");
        });
    }

    /*
    회원 정보 수정(dirty checking)
     */
    @Transactional
    public User updateById(Long userId, UserRequest.UpdateDTO updateDTO) {
        // 1 log
        // 2 user select
        User user = findById(userId);
        // TODO user.update(updateDTO);
        user.setPassword(updateDTO.getPassword());
        // 3. 수정된 User 반환 > 세션 동기화
        return user;
    }



}
