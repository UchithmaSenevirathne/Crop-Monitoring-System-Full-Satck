package lk.ijse.crop_monitoring_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "equipment_staff")
public class EquipmentStaff {
    @Id
    @GeneratedValue
    private Long equipment_staff_id;
    @ManyToOne
    @JoinColumn(name = "equipmentId")
    private EquipmentEntity equipment;
    @ManyToOne
    @JoinColumn(name = "staffId")
    private StaffEntity staff;
    private String assignedDate;
    private String handOverDate;
}
