package com.hmc.his.service.impl;

import com.hmc.his.common.BusinessException;
import com.hmc.his.model.Prescription;
import com.hmc.his.model.PrescriptionItem;
import com.hmc.his.repository.DrugRepository;
import com.hmc.his.repository.PrescriptionRepository;
import com.hmc.his.service.DispenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DispenseServiceImpl implements DispenseService {

    private final PrescriptionRepository prescriptionRepository;
    private final DrugRepository drugRepository;

    @Override
    public List<Prescription> list() {
        List<Prescription> list = prescriptionRepository.selectDispenseQueue();
        for (Prescription p : list) {
            p.setItems(prescriptionRepository.selectItems(p.getId()));
        }
        return list;
    }

    @Override
    @Transactional
    public void dispense(Long id) {
        Prescription p = prescriptionRepository.selectDispenseQueue().stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new BusinessException("处方不存在或不在待发药队列"));

        for (PrescriptionItem item : prescriptionRepository.selectItems(id)) {
            int affected = drugRepository.deductStock(item.getDrugId(), item.getQty());
            if (affected == 0) {
                throw new BusinessException("药品 " + item.getDrugName() + " 库存不足，无法发药");
            }
        }
        prescriptionRepository.updateDispenseStatus(id, "DISPENSED");
    }
}
