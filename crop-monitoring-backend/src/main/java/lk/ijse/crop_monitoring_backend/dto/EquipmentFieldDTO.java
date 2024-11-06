package lk.ijse.crop_monitoring_backend.dto;

import jakarta.persistence.*;
import lk.ijse.crop_monitoring_backend.entity.EquipmentEntity;
import lk.ijse.crop_monitoring_backend.entity.FieldEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EquipmentFieldDTO {
    private int equipment_field_id;
    private int equipmentId;
    private int fieldId;
}
