package lk.ijse.crop_monitoring_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EquipmentDTO {
    private int equipmentId;
    private String name;
    private String type;
    private String status;
    private int fieldCode;
    private int staffId;
}
