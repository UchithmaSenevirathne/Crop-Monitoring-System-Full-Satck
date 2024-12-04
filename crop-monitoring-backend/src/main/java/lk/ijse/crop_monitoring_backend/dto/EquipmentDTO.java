package lk.ijse.crop_monitoring_backend.dto;

import lk.ijse.crop_monitoring_backend.customObj.EquipResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EquipmentDTO implements EquipResponse {
    private int equipmentId;
    private String name;
    private String type;
    private String status;
    private int field_code;
    private int staffId;
}
