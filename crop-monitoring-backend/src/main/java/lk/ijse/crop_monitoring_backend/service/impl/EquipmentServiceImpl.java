package lk.ijse.crop_monitoring_backend.service.impl;

import lk.ijse.crop_monitoring_backend.customObj.EquipResponse;
import lk.ijse.crop_monitoring_backend.dao.EquipmentDAO;
import lk.ijse.crop_monitoring_backend.dao.FieldDAO;
import lk.ijse.crop_monitoring_backend.dao.StaffDAO;
import lk.ijse.crop_monitoring_backend.dto.EquipmentDTO;
import lk.ijse.crop_monitoring_backend.entity.CropEntity;
import lk.ijse.crop_monitoring_backend.entity.EquipmentEntity;
import lk.ijse.crop_monitoring_backend.entity.FieldEntity;
import lk.ijse.crop_monitoring_backend.entity.StaffEntity;
import lk.ijse.crop_monitoring_backend.exception.DataPersistFailedException;
import lk.ijse.crop_monitoring_backend.exception.NotFoundException;
import lk.ijse.crop_monitoring_backend.service.EquipmentService;
import lk.ijse.crop_monitoring_backend.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    private EquipmentDAO equipmentDAO;

    @Autowired
    private FieldDAO fieldDAO;

    @Autowired
    private StaffDAO staffDAO;

    @Autowired
    private Mapping mapping;

    @Override
    public void addEquipment(EquipmentDTO equipmentDTO) {
        equipmentDAO.save(mapping.convertToEquipmentEntity(equipmentDTO));
    }

    @Override
    public void updateEquipment(EquipmentDTO equipmentDTO, int equipmentId) {
        Optional<EquipmentEntity> tmpEntity = equipmentDAO.findById(equipmentId);
        if(!tmpEntity.isPresent()){
            throw new NotFoundException("Equipment Not Found");
        }else {
            FieldEntity field = fieldDAO.findById(equipmentDTO.getField_code())
                    .orElseThrow(() -> new DataPersistFailedException("Field not found"));
            StaffEntity staff = staffDAO.findById(equipmentDTO.getStaffId())
                    .orElseThrow(() -> new DataPersistFailedException("Staff not found"));

            tmpEntity.get().setName(equipmentDTO.getName());
            tmpEntity.get().setType(equipmentDTO.getType());
            tmpEntity.get().setStatus(equipmentDTO.getStatus());
            tmpEntity.get().setField(field);
            tmpEntity.get().setStaff(staff);
        }
    }

    @Override
    public List<EquipmentDTO> getAllEquip() {
        return mapping.convertToEquipDTOList(equipmentDAO.findAll());
    }

    @Override
    public EquipResponse getSelectedEquip(int equipmentId) {
        if (equipmentDAO.existsById(equipmentId)) {
            return mapping.convertToEquipmentDTO(equipmentDAO.getReferenceById(equipmentId));
        }else{
            throw new NotFoundException("Equipment not found");
        }
    }

    @Override
    public void deleteEquip(int equipmentId) {
        Optional<EquipmentEntity> findId = equipmentDAO.findById(equipmentId);
        if(!findId.isPresent()){
            throw new NotFoundException("Equipment not found");
        }else {
            equipmentDAO.deleteById(equipmentId);
        }
    }
}
