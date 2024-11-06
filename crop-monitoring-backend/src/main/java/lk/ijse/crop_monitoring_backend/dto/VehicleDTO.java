package lk.ijse.crop_monitoring_backend.dto;

import jakarta.persistence.*;
import lk.ijse.crop_monitoring_backend.entity.VehicleStaff;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehicleDTO {
    private int vehicleCode;
    private String licensePlateNumber;
    private String vehicleCategory;
    private String fuelType;
    private String status;
    private String remarks;
    private List<VehicleStaff> vehicleStaffs;
}
