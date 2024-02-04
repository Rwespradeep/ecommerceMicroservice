package com.rvs.InventoryService.repository;

import com.rvs.InventoryService.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    //make sure you follow camel case convention when writing findBy custom methods
    Optional<Inventory> findByskuCode(String skuCode);

    List<Inventory> findByskuCodeIn(List<String> skuCode);
}
