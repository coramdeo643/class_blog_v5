package com.tenco.blog.User;

import com.tenco.blog.user.User;
import com.tenco.blog.user.UserJPARepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

// @Import(UserJPARepositoryTest.class) // 인터페이스이기 때문에 @impost 로 직접 빈으로 등록할수없다
@DataJpaTest // JPA 관련 컴포넌트만 로드하여 테스트(much lighter)
public class UserJPARepositoryTest {

    @Autowired
    private UserJPARepository userJPARepository;

    @Test
    public void save_test() {
        User testUser = User.builder()
                .username("testUser")
                .password("1234")
                .email("a@naver.com")
                .build();
        User savedUser = userJPARepository.save(testUser);
        Assertions.assertThat(savedUser.getId()).isNotNull();
    }

    @Test
    public void findByUsername() {
        String username = "ssar";
        Optional<User> selectedUser = userJPARepository.findByUsername(username);
        System.out.println(selectedUser);
        System.out.println(selectedUser.get().getUsername());
    }

}
