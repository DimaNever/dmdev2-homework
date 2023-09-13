package com.dmdevmvn.service.dao;

import com.dmdevmvn.service.entity.SparePart;
import com.dmdevmvn.service.util.EntityUtil;
import com.dmdevmvn.util.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SparePartRepositoryTest extends TestBase {

    private final SparePartRepository sparePartRepository = new SparePartRepository(session);

    @Test
    void save() {
        SparePart expectedSparePart = EntityUtil.buildRandomSparePart();
        sparePartRepository.save(expectedSparePart);

        session.clear();

        var optionalSparePart = sparePartRepository.findById(expectedSparePart.getId());
        Assertions.assertNotNull(optionalSparePart);
    }

    @Test
    void delete() {
        SparePart expectedSparePart = EntityUtil.buildRandomSparePart();
        sparePartRepository.save(expectedSparePart);

        sparePartRepository.delete(expectedSparePart);

        session.flush();
        session.clear();

        assertTrue(sparePartRepository.findById(expectedSparePart.getId()).isEmpty());
    }

    @Test
    void update() {
        SparePart expectedSparePart = EntityUtil.buildRandomSparePart();
        sparePartRepository.save(expectedSparePart);

        expectedSparePart.setTitle("Update");
        sparePartRepository.update(expectedSparePart);

        session.flush();
        session.clear();
        var optionalSparePart = sparePartRepository.findById(expectedSparePart.getId());

        assertTrue(optionalSparePart.isPresent());
        assertEquals("Update", optionalSparePart.get().getTitle());
    }

    @Test
    void findById() {
        var optionalSparePart = sparePartRepository.findById(1L);

        assertNotNull(optionalSparePart);
    }

    @Test
    void findAll() {
        var sparePartRepositoryAll = sparePartRepository.findAll();

        assertThat(sparePartRepositoryAll).hasSize(7);
    }

    @Test
    void findAllSparePartsByOrderId() {
        var allSparePartsByOrderId = sparePartRepository.findAllSparePartsByOrderId(2L);

        var actualSparePartIds = allSparePartsByOrderId.stream()
                .map(SparePart::getId)
                .toList();

        assertThat(allSparePartsByOrderId).hasSize(2);
        assertThat(actualSparePartIds).containsExactlyInAnyOrder(6L, 1L);
    }
}