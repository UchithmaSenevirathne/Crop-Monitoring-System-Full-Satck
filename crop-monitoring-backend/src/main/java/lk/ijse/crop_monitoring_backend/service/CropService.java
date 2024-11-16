package lk.ijse.crop_monitoring_backend.service;

import lk.ijse.crop_monitoring_backend.dto.CropDTO;

public interface CropService {
    void saveCrop(CropDTO cropDTO);

    void updateCrop(CropDTO cropDTO);
}
