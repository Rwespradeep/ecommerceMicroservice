package com.rvs.InventoryService.service;

import com.rvs.InventoryService.Inventorydto.InventoryResponse;
import com.rvs.InventoryService.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    @Transactional(readOnly = true)
    public List<InventoryResponse> isInstock(List<String> skuCode){
       return inventoryRepository.findByskuCodeIn(skuCode).stream().map(inventory -> InventoryResponse.builder()
               .skuCode(inventory.getSkuCode())
               .isInstock(inventory.getQuantity() > 0).build()).toList();
    }
}
