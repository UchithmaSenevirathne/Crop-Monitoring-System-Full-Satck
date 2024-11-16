package lk.ijse.crop_monitoring_backend.service;

import lk.ijse.crop_monitoring_backend.customObj.CropResponse;
import lk.ijse.crop_monitoring_backend.dto.CropDTO;

import java.util.List;

public interface CropService {
    void saveCrop(CropDTO cropDTO);

    void updateCrop(CropDTO cropDTO);

    List<CropDTO> getAllCrops();

    CropResponse getSelectedCrop(int cropCode);

    void deleteCrop(int cropCode);
}
