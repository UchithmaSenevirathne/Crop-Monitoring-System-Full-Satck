package lk.ijse.crop_monitoring_backend.service;

import lk.ijse.crop_monitoring_backend.customObj.EquipResponse;
import lk.ijse.crop_monitoring_backend.dto.EquipmentDTO;

import java.util.List;

public interface EquipmentService {
    void addEquipment(EquipmentDTO equipmentDTO);

    void updateEquipment(EquipmentDTO equipmentDTO, int equipmentId);

    List<EquipmentDTO> getAllEquip();

    EquipResponse getSelectedEquip(int equipmentId);

    void deleteEquip(int equipmentId);
}
