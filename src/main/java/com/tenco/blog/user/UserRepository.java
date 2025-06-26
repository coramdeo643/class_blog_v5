package com.tenco.blog.user;

import com.tenco.blog._core.errors.exception.Exception400;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Repository
public class UserRepository {
    private final EntityManager em;
    private static final Logger log = LoggerFactory.getLogger(UserRepository.class);

    public User findByUsernameAndPassword
            (String username, String password) {
        log.info("");
        // 필요하다면 직접 예외 처리 설정!
        String jpql = "select u from User u " +
                "where u.username = :username and u.password = :password ";
        try {
            return em.createQuery(jpql, User.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional
    public User save(User user) {
        log.info("Start to save the user info");
        em.persist(user);
        return user;
    }

    public User findByUsername(String username) {
        log.info("Start to find the same user info");
        try {
            String jpql = "select u from User u where u.username = :username ";
            return em.createQuery(jpql, User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public User findById(Long id) {
        log.info("Start to find the user info - ID : {}", id);
        User user = em.find(User.class, id);
        if (user == null) {
            throw new Exception400("User not found with id : " + id);
        }
        return user;
    }

    @Transactional
    public User updateById(Long id, UserRequest.UpdateDTO reqDTO) {
        log.info("Start to update the user info - ID : {}", id);
        User user = findById(id);
        user.setPassword(reqDTO.getPassword());
        return user;
    }
}
