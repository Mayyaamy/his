package com.hmc.his.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Prescription {
    private Long id;
    private Long visitId;
    private LocalDateTime prescribedAt;
    private BigDecimal totalAmount;
    private String dispenseStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /** 关联明细（非数据库列） */
    private List<PrescriptionItem> items;

    /** 发药队列展示字段（非数据库列） */
    private String billNo;
    private String billStatus;
    private String patientName;
    private String patientGender;
    private String doctorName;
    private String regNo;
}
