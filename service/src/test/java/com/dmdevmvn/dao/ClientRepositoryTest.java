package com.dmdevmvn.dao;

import com.dmdevmvn.entity.Client;
import com.dmdevmvn.util.EntityUtil;
import com.dmdevmvn.util.TestBase;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClientRepositoryTest extends TestBase {

    private final ClientRepository clientRepository = context.getBean(ClientRepository.class);

    @Test
    void save() {
        Client expectedClient = EntityUtil.buildRandomClient("Ivan", "Ivanov");
        clientRepository.save(expectedClient);

        session.clear();

        var actualClient = clientRepository.findById(expectedClient.getId());
        assertNotNull(actualClient);
    }

    @Test
    void delete() {
        Client expectedClient = EntityUtil.buildRandomClient("Ivan", "Ivanov");
        clientRepository.save(expectedClient);

        clientRepository.delete(expectedClient);

        session.flush();
        session.clear();

        assertTrue(clientRepository.findById(expectedClient.getId()).isEmpty());
    }

    @Test
    void update() {
        Client expectedClient = EntityUtil.buildRandomClient("Ivan", "Ivanov");
        clientRepository.save(expectedClient);

        expectedClient.setFirstName("Update");
        clientRepository.update(expectedClient);

        session.flush();
        session.clear();

        var optionalClient = clientRepository.findById(expectedClient.getId());

        assertTrue(optionalClient.isPresent());
        assertEquals("Update", optionalClient.get().getFirstName());
    }

    @Test
    void findById() {
        var optionalClient = clientRepository.findById(1L);

        assertNotNull(optionalClient);
    }

    @Test
    void findAll() {
        var clientRepositoryAll = clientRepository.findAll();

        assertThat(clientRepositoryAll).hasSize(3);
    }
}