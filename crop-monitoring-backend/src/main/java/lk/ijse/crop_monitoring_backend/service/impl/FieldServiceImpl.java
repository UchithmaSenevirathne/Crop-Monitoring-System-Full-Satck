package lk.ijse.crop_monitoring_backend.service.impl;

import lk.ijse.crop_monitoring_backend.customObj.FieldResponse;
import lk.ijse.crop_monitoring_backend.dao.FieldDAO;
import lk.ijse.crop_monitoring_backend.dao.StaffDAO;
import lk.ijse.crop_monitoring_backend.dto.FieldDTO;
import lk.ijse.crop_monitoring_backend.entity.CropEntity;
import lk.ijse.crop_monitoring_backend.entity.FieldEntity;
import lk.ijse.crop_monitoring_backend.exception.NotFoundException;
import lk.ijse.crop_monitoring_backend.service.FieldService;
import lk.ijse.crop_monitoring_backend.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FieldServiceImpl implements FieldService {
    @Autowired
    private FieldDAO fieldDAO;

    @Autowired
    private Mapping mapping;

    @Override
    public void saveField(FieldDTO fieldDTO) {
        fieldDAO.save(mapping.convertToFieldEntity(fieldDTO));
    }

    @Override
    public int getFieldID(String fieldName) {
        return fieldDAO.getIdByName(fieldName);
    }

    @Override
    public void updateField(FieldDTO fieldDTO) {
        Optional<FieldEntity> tmpFieldEntity = fieldDAO.findById(fieldDTO.getFieldCode());
        if (!tmpFieldEntity.isPresent()) {
            throw new NotFoundException("Field not found");
        }else {
            tmpFieldEntity.get().setFieldName(fieldDTO.getFieldName());
            tmpFieldEntity.get().setFieldLocation(fieldDTO.getFieldLocation());
            tmpFieldEntity.get().setExtentSize(fieldDTO.getExtentSize());
            tmpFieldEntity.get().setFieldImage1(fieldDTO.getFieldImage1());
            tmpFieldEntity.get().setFieldImage2(fieldDTO.getFieldImage2());
        }
    }

    @Override
    public List<FieldDTO> getAllFields() {
        return mapping.convertToFieldDTOList(fieldDAO.findAll());
    }

    @Override
    public FieldResponse getSelectedField(int fieldCode) {
        if (fieldDAO.existsById(fieldCode)) {
            return mapping.convertToFieldDTO(fieldDAO.getReferenceById(fieldCode));
        }else{
            throw new NotFoundException("Field not found");
        }
    }

    @Override
    public void deleteField(int fieldCode) {
        Optional<FieldEntity> findId = fieldDAO.findById(fieldCode);
        if(!findId.isPresent()){
            throw new NotFoundException("Field not found");
        }else {
            fieldDAO.deleteById(fieldCode);
        }
    }
}
