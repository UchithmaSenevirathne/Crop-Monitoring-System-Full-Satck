package lk.ijse.crop_monitoring_backend.service;

import lk.ijse.crop_monitoring_backend.customObj.CropDetailsResponse;
import lk.ijse.crop_monitoring_backend.dto.CropDetailsDTO;

import java.util.List;

public interface LogService {
    void saveLog(CropDetailsDTO cropDetailsDTO);

    void updateLog(CropDetailsDTO cropDetailsDTO);

    List<CropDetailsDTO> getAllLogs();

    CropDetailsResponse getSelectedLog(int logCode);

    void deleteLog(int logCode);
}
