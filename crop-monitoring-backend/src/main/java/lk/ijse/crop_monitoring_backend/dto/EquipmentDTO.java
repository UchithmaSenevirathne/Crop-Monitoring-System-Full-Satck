package lk.ijse.crop_monitoring_backend.dto;

import jakarta.persistence.*;
import lk.ijse.crop_monitoring_backend.entity.EquipmentField;
import lk.ijse.crop_monitoring_backend.entity.EquipmentStaff;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EquipmentDTO {
    private int equipmentId;
    private String name;
    private String type;
    private String status;
}
