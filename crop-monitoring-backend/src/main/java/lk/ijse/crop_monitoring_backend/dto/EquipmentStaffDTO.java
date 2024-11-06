package lk.ijse.crop_monitoring_backend.dto;

import jakarta.persistence.*;
import lk.ijse.crop_monitoring_backend.entity.EquipmentEntity;
import lk.ijse.crop_monitoring_backend.entity.StaffEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EquipmentStaffDTO {
    private int equipment_staff_id;
    private int equipmentId;
    private int staffId;
    private String assignedDate;
    private String handOverDate;
}
