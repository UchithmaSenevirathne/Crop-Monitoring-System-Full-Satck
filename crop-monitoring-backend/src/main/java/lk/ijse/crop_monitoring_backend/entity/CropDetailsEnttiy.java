package lk.ijse.crop_monitoring_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "crop_details")
public class CropDetailsEnttiy {
    @Id
    @GeneratedValue
    private String logCode;
    private String logDate;
    private String logDetails;
    private String observedImage;
    @ManyToOne
    @JoinColumn(name = "staff_id", referencedColumnName = "staffId")
    private StaffEntity staff;
    @ManyToOne
    @JoinColumn(name = "field_id", referencedColumnName = "fieldCode")
    private FieldEntity field;
    @ManyToOne
    @JoinColumn(name = "crop_id", referencedColumnName = "cropCode")
    private CropEntity crop;
}
