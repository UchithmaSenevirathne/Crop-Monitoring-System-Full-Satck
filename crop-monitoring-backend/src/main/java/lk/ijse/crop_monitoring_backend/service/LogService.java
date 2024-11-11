package lk.ijse.crop_monitoring_backend.service;

import lk.ijse.crop_monitoring_backend.dto.CropDetailsDTO;

public interface LogService {
    void saveLog(CropDetailsDTO cropDetailsDTO);
}
