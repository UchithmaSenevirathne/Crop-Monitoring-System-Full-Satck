package lk.ijse.crop_monitoring_backend.dto;

import lk.ijse.crop_monitoring_backend.entity.VehicleStaff;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StaffDTO {
    private int staffId;
    private String firstName;
    private String lastName;
    private String designation;
    private String gender;
    private String joinedDate;
    private String DOB;
    private String buildingNo;
    private String lane;
    private String mainCity;
    private String mainState;
    private String postalCode;
    private String contactNo;
    private String email;
    private String role;
    private List<FieldStaffDTO> fieldStaffs;
    private List<VehicleStaffDTO> vehicleStaffs;
    private List<EquipmentStaffDTO> equipmentStaffs;
    private List<CropDetailsDTO> cropdetails;
}
