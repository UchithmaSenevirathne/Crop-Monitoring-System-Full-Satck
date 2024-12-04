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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int logCode;
    private String logDate;
    private String logDetails;
    @Column(columnDefinition = "LONGTEXT")
    private String observedImage;
    @ManyToOne
    @JoinColumn(name = "staff_id", referencedColumnName = "staffId")
    private StaffEntity staff;
    @ManyToOne
    @JoinColumn(name = "field_code", referencedColumnName = "fieldCode")
    private FieldEntity field;
    @ManyToOne
    @JoinColumn(name = "crop_code", referencedColumnName = "cropCode")
    private CropEntity crop;
}
