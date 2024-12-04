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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int equipmentId;
    private String name;
    private String type;
    private String status;
    @ManyToOne
    @JoinColumn(name = "field_code", referencedColumnName = "fieldCode")
    private FieldEntity field;
    @ManyToOne
    @JoinColumn(name = "staff_id", referencedColumnName = "staffId")
    private StaffEntity staff;
}
