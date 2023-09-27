package com.dmdevmvn.integration.repository;

import com.dmdevmvn.dao.SparePartRepository;
import com.dmdevmvn.entity.SparePart;
import com.dmdevmvn.util.EntityUtil;
import com.dmdevmvn.util.TestBase;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SparePartRepositoryIT extends TestBase {

    private final SparePartRepository sparePartRepository = context.getBean(SparePartRepository.class);

    @Test
    void save() {
        SparePart expectedSparePart = EntityUtil.buildRandomSparePart();
        sparePartRepository.save(expectedSparePart);

        entityManager.clear();

        var optionalSparePart = sparePartRepository.findById(expectedSparePart.getId());
        assertNotNull(optionalSparePart);
    }

    @Test
    void delete() {
        SparePart expectedSparePart = EntityUtil.buildRandomSparePart();
        sparePartRepository.save(expectedSparePart);

        sparePartRepository.delete(expectedSparePart);

        entityManager.flush();
        entityManager.clear();

        assertTrue(sparePartRepository.findById(expectedSparePart.getId()).isEmpty());
    }

    @Test
    void update() {
        SparePart expectedSparePart = EntityUtil.buildRandomSparePart();
        sparePartRepository.save(expectedSparePart);

        expectedSparePart.setTitle("Update");
        sparePartRepository.update(expectedSparePart);

        entityManager.flush();
        entityManager.clear();
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