package lk.ijse.crop_monitoring_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CropDetailsDTO {
    private int logCode;
    private String logDate;
    private String logDetails;
    private String observedImage;
    private int staffId;
    private int fieldId;
    private int cropCode;
}
