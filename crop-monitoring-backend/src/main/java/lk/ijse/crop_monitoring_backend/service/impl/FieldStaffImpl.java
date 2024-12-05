package lk.ijse.crop_monitoring_backend.service.impl;

import lk.ijse.crop_monitoring_backend.customObj.FieldStaffResponse;
import lk.ijse.crop_monitoring_backend.dao.FieldStaffDAO;
import lk.ijse.crop_monitoring_backend.dao.UserDAO;
import lk.ijse.crop_monitoring_backend.dto.FieldStaffDTO;
import lk.ijse.crop_monitoring_backend.entity.EquipmentEntity;
import lk.ijse.crop_monitoring_backend.entity.FieldStaff;
import lk.ijse.crop_monitoring_backend.exception.NotFoundException;
import lk.ijse.crop_monitoring_backend.service.FieldStaffService;
import lk.ijse.crop_monitoring_backend.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FieldStaffImpl implements FieldStaffService {

    @Autowired
    private FieldStaffDAO fieldStaffDAO;

    @Autowired
    private Mapping mapping;

    @Override
    public void assignFieldToStaff(FieldStaffDTO fieldStaffDTO) {
        fieldStaffDAO.save(mapping.convertToFieldStaff(fieldStaffDTO));
    }

    @Override
    public List<FieldStaffDTO> getAllAssigns() {
        return mapping.convertToFieldStaffDTOList(fieldStaffDAO.findAll());
    }

    @Override
    public FieldStaffResponse getSelectedAssign(int fieldStaffId) {
        if (fieldStaffDAO.existsById(fieldStaffId)) {
            return mapping.convertToFieldStaffDTO(fieldStaffDAO.getReferenceById(fieldStaffId));
        }else{
            throw new NotFoundException("Assign not found");
        }
    }

    @Override
    public void deleteAssign(int fieldStaffId) {
        Optional<FieldStaff> findId = fieldStaffDAO.findById(fieldStaffId);
        if(!findId.isPresent()){
            throw new NotFoundException("Assign not found");
        }else {
            fieldStaffDAO.deleteById(fieldStaffId);
        }
    }
}
