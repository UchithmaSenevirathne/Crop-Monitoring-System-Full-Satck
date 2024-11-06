package lk.ijse.crop_monitoring_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "staff")
public class StaffEntity {
    @Id
    @GeneratedValue
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
    @Column(unique = true)
    private String email;
    private String role;
    @OneToMany(mappedBy = "staff")
    private List<FieldStaff> fieldStaffs = new ArrayList<>();
    @OneToMany(mappedBy = "staff")
    private List<VehicleStaff> vehicleStaffs = new ArrayList<>();
    @OneToMany(mappedBy = "staff")
    private List<EquipmentStaff> equipmentStaffs = new ArrayList<>();
    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL)
    private List<CropDetailsEnttiy> cropDetails;
}
