package lk.ijse.crop_monitoring_backend.service.impl;

import lk.ijse.crop_monitoring_backend.dao.EquipmentDAO;
import lk.ijse.crop_monitoring_backend.dao.FieldDAO;
import lk.ijse.crop_monitoring_backend.dto.EquipmentDTO;
import lk.ijse.crop_monitoring_backend.service.EquipmentService;
import lk.ijse.crop_monitoring_backend.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    private EquipmentDAO equipmentDAO;

    @Autowired
    private Mapping mapping;

    @Override
    public void addEquipment(EquipmentDTO equipmentDTO) {
        equipmentDAO.save(mapping.convertToEquipmentEntity(equipmentDTO));
    }
}
