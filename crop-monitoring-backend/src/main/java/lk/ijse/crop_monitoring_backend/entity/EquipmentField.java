package lk.ijse.crop_monitoring_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "equipment_field")
public class EquipmentField {
    @Id
    @GeneratedValue
    private int equipment_field_id;
    @ManyToOne
    @JoinColumn(name = "equipmentId")
    private EquipmentEntity equipment;
    @ManyToOne
    @JoinColumn(name = "fieldCode")
    private FieldEntity field;
}
