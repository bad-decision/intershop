package ru.azmeev.intershop.showcase.integration.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.azmeev.intershop.showcase.integration.IntegrationTestBase;
import ru.azmeev.intershop.showcase.model.entity.UserEntity;
import ru.azmeev.intershop.showcase.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserRepositoryIT extends IntegrationTestBase {

    @Autowired
    private UserRepository userRepository;
    private static final String username = "user01";

    @BeforeEach
    public void setUp() {
        executeSqlBlocking(initDataSql);
    }

    @Test
    void findByUsername_mustReturnCorrectUser() {
        UserEntity user = userRepository.findByUsername(username).block();
        assertEquals(username, user.getUsername());
    }

    @Test
    void findByUsername_mustReturnNull() {
        UserEntity user = userRepository.findByUsername("not-exist-user").block();
        assertNull(user);
    }
}