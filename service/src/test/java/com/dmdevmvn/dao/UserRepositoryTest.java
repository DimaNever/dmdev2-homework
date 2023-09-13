package com.dmdevmvn.dao;

import com.dmdevmvn.entity.Role;
import com.dmdevmvn.entity.User;
import com.dmdevmvn.util.EntityUtil;
import com.dmdevmvn.util.TestBase;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserRepositoryTest extends TestBase {

    private final UserRepository userRepository = context.getBean(UserRepository.class);

    @Test
    void save() {
        User expectedUser = EntityUtil.buildRandomUser("Save", "John", Role.ADMIN);
        userRepository.save(expectedUser);

        session.clear();

        var optionalUser = userRepository.findById(expectedUser.getId());
        assertNotNull(optionalUser);
    }

    @Test
    void delete() {
        User expectedUser = EntityUtil.buildRandomUser("Save", "John", Role.ADMIN);
        userRepository.save(expectedUser);

        userRepository.delete(expectedUser);

        session.flush();
        session.clear();

        assertTrue(userRepository.findById(expectedUser.getId()).isEmpty());
    }

    @Test
    void update() {
        User expectedUser = EntityUtil.buildRandomUser("Save", "John", Role.ADMIN);
        userRepository.save(expectedUser);

        expectedUser.setFirstName("Update");
        userRepository.update(expectedUser);

        session.flush();
        session.clear();

        var optionalUser = userRepository.findById(expectedUser.getId());

        assertTrue(optionalUser.isPresent());
        assertEquals("Update", optionalUser.get().getFirstName());
    }

    @Test
    void findById() {
        var optionalUser = userRepository.findById(1L);

        assertNotNull(optionalUser);
    }

    @Test
    void findAll() {
        var userRepositoryAll = userRepository.findAll();

        assertThat(userRepositoryAll).hasSize(3);
    }

    @Test
    void findAllByRole() {
        var userRepositoryAllByRole = userRepository.findAllByRole(Role.WORKER);

        assertThat(userRepositoryAllByRole).hasSize(2);
    }
}