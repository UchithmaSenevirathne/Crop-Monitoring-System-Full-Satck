package lk.ijse.crop_monitoring_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "vehicle_staff")
public class VehicleStaff {
    @Id
    @GeneratedValue
    private int vehicle_staff_id;
    @ManyToOne
    @JoinColumn(name = "vehicleCode")
    private VehicleEntity vehicle;
    @ManyToOne
    @JoinColumn(name = "staffId")
    private StaffEntity staff;
    private String allocatedDate;
}
