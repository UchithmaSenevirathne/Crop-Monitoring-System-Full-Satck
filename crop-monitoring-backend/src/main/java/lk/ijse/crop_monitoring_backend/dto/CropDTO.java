package lk.ijse.crop_monitoring_backend.dto;

import lk.ijse.crop_monitoring_backend.customObj.CropResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CropDTO implements CropResponse {
    private int cropCode;
    private String cropCommonName;
    private String cropScientificName;
    private String cropImage;
    private String category;
    private String cropSeason;
    private int fieldCode;
}
