package lk.ijse.crop_monitoring_backend.dto;

import lk.ijse.crop_monitoring_backend.customObj.CropDetailsResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CropDetailsDTO implements CropDetailsResponse {
    private int logCode;
    private String logDate;
    private String logDetails;
    private String  cropStatus;
    private String observedImage;
    private int staffId;
    private int fieldCode;
    private int cropCode;
}
