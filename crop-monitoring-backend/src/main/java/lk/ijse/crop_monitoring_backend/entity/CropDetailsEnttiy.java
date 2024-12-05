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
    private String  cropStatus;
    @Column(columnDefinition = "LONGTEXT")
    private String observedImage;
    @ManyToOne
    @JoinColumn(name = "staffId")
    private StaffEntity staff;
    @ManyToOne
    @JoinColumn(name = "fieldCode")
    private FieldEntity field;
    @ManyToOne
    @JoinColumn(name = "cropCode")
    private CropEntity crop;
}
