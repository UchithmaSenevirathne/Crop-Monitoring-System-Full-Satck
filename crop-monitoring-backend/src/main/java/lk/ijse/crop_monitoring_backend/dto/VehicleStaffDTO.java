package lk.ijse.crop_monitoring_backend.dto;

import jakarta.persistence.*;
import lk.ijse.crop_monitoring_backend.entity.StaffEntity;
import lk.ijse.crop_monitoring_backend.entity.VehicleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehicleStaffDTO {
    private int vehicle_staff_id;
    private int vehicleId;
    private int staffId;
    private String allocatedDate;
}
