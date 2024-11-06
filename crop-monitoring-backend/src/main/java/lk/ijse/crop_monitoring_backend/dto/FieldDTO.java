package lk.ijse.crop_monitoring_backend.dto;

import jakarta.persistence.*;
import lk.ijse.crop_monitoring_backend.entity.CropDetailsEnttiy;
import lk.ijse.crop_monitoring_backend.entity.CropEntity;
import lk.ijse.crop_monitoring_backend.entity.EquipmentField;
import lk.ijse.crop_monitoring_backend.entity.FieldStaff;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FieldDTO {
    private int fieldCode;
    private String fieldName;
    private String fieldLocation;
    private Double extentSize;
    private String fieldImage1;
    private String fieldImage2;
    private List<FieldStaffDTO> fieldStaffs;
    private List<EquipmentFieldDTO> equipmentFields;
    private List<CropDTO> crops;
    private List<CropDetailsDTO> cropDetails;
}
