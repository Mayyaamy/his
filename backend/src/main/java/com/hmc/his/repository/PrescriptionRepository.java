package com.hmc.his.repository;

import com.hmc.his.model.Prescription;
import com.hmc.his.model.PrescriptionItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PrescriptionRepository {

    @Select("SELECT * FROM prescription WHERE visit_id = #{visitId} ORDER BY id ASC")
    List<Prescription> selectByVisitId(Long visitId);

    @Insert("INSERT INTO prescription(visit_id, prescribed_at, total_amount) " +
            "VALUES(#{visitId}, #{prescribedAt}, #{totalAmount})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Prescription p);

    @Update("UPDATE prescription SET total_amount = #{totalAmount} WHERE id = #{id}")
    int updateTotal(@Param("id") Long id, @Param("totalAmount") java.math.BigDecimal totalAmount);

    @Select("SELECT pi.*, dr.name AS drug_name, dr.spec AS drug_spec, dr.unit AS drug_unit " +
            "FROM prescription_item pi LEFT JOIN drug dr ON dr.id = pi.drug_id " +
            "WHERE pi.prescription_id = #{prescriptionId} ORDER BY pi.id ASC")
    List<PrescriptionItem> selectItems(Long prescriptionId);

    @Insert("INSERT INTO prescription_item(prescription_id, drug_id, dosage, frequency, days, qty, unit_price, subtotal) " +
            "VALUES(#{prescriptionId}, #{drugId}, #{dosage}, #{frequency}, #{days}, #{qty}, #{unitPrice}, #{subtotal})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertItem(PrescriptionItem item);

    @Select("SELECT p.*, b.bill_no, b.status AS bill_status, " +
            "       pt.name AS patient_name, pt.gender AS patient_gender, " +
            "       dct.name AS doctor_name, r.reg_no " +
            "FROM prescription p " +
            "LEFT JOIN bill b ON b.prescription_id = p.id " +
            "LEFT JOIN visit v ON v.id = p.visit_id " +
            "LEFT JOIN registration r ON r.id = v.registration_id " +
            "LEFT JOIN patient pt ON pt.id = r.patient_id " +
            "LEFT JOIN doctor dct ON dct.id = r.doctor_id " +
            "WHERE b.status = 'PAID' AND p.dispense_status = 'PENDING' " +
            "ORDER BY p.id ASC")
    List<Prescription> selectDispenseQueue();

    @Update("UPDATE prescription SET dispense_status = #{status} WHERE id = #{id}")
    int updateDispenseStatus(@Param("id") Long id, @Param("status") String status);
}
