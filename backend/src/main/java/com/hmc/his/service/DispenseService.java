package com.hmc.his.service;

import com.hmc.his.model.Prescription;

import java.util.List;

public interface DispenseService {
    List<Prescription> list();

    void dispense(Long id);
}
