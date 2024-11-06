package lk.ijse.crop_monitoring_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "field")
public class FieldEntity {
    @Id
    @GeneratedValue
    private int fieldCode;
    private String fieldName;
    @Column(columnDefinition = "Geometry(Point, 4326)") // Assuming Point is a geometry type.
    private Point fieldLocation;
    private Double extentSize;
    private String fieldImage1;
    private String fieldImage2;
    @OneToMany(mappedBy = "field")
    private List<FieldStaff> fieldStaffs = new ArrayList<>();
    @OneToMany(mappedBy = "field")
    private List<EquipmentField> equipmentFields = new ArrayList<>();
    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL)
    private List<CropEntity> crops;
    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL)
    private List<CropDetailsEnttiy> cropDetails;
}
