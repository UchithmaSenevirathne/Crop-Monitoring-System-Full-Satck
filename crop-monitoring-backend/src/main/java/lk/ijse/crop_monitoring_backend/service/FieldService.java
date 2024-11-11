package lk.ijse.crop_monitoring_backend.service;

import lk.ijse.crop_monitoring_backend.dto.FieldDTO;

public interface FieldService {
    void saveField(FieldDTO fieldDTO);

    int getFieldID(String fieldName);
}
