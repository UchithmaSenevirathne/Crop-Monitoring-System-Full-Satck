package lk.ijse.crop_monitoring_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "field_staff")
public class FieldStaff {
    @Id
    @GeneratedValue
    private int field_staff_id;
    @ManyToOne
    @JoinColumn(name = "fieldCode")
    private FieldEntity field;
    @ManyToOne
    @JoinColumn(name = "staffId")
    private StaffEntity staff;
    private String assignedDate;
    private String dueDate;
}
