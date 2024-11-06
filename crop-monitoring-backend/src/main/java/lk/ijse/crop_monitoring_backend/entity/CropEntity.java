package lk.ijse.crop_monitoring_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "crop")
public class CropEntity {
    @Id
    @GeneratedValue
    private String cropCode;
    private String cropCommonName;
    private String cropScientificName;
    private String cropImage;
    private String category;
    private String cropSeason;
    @ManyToOne
    @JoinColumn(name = "field_id", referencedColumnName = "fieldCode")
    private FieldEntity field;
    @OneToMany(mappedBy = "crop", cascade = CascadeType.ALL)
    private List<CropDetailsEnttiy> cropDetails;
}
