package lk.ijse.crop_monitoring_backend.dto;

import lk.ijse.crop_monitoring_backend.customObj.FieldResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FieldDTO implements FieldResponse {
    private int fieldCode;
    private String fieldName;
    private String fieldLocation;
    private Double extentSize;
    private String fieldImage1;
    private String fieldImage2;
}
