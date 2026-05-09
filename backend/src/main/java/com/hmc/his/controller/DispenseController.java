package com.hmc.his.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import com.hmc.his.common.R;
import com.hmc.his.model.Prescription;
import com.hmc.his.service.DispenseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "药房")
@RestController
@RequestMapping("/api/dispenses")
@RequiredArgsConstructor
@SaCheckRole(value = {"ADMIN", "PHARMACIST"}, mode = SaMode.OR)
public class DispenseController {

    private final DispenseService dispenseService;

    @GetMapping
    public R<List<Prescription>> list() {
        return R.ok(dispenseService.list());
    }

    @PostMapping("/{id}/dispense")
    public R<Void> dispense(@PathVariable Long id) {
        dispenseService.dispense(id);
        return R.ok();
    }
}
