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
@Table(name = "equipment")
public class EquipmentEntity {
    @Id
    @GeneratedValue
    private int equipmentId;
    private String name;
    private String type;
    private String status;
    @OneToMany(mappedBy = "equipment")
    private List<EquipmentStaff> equipmentStaffs = new ArrayList<>();
    @OneToMany(mappedBy = "equipment")
    private List<EquipmentField> equipmentFields = new ArrayList<>();
}
