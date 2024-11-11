package lk.ijse.crop_monitoring_backend.service.impl;

import lk.ijse.crop_monitoring_backend.dao.FieldDAO;
import lk.ijse.crop_monitoring_backend.dao.StaffDAO;
import lk.ijse.crop_monitoring_backend.dto.FieldDTO;
import lk.ijse.crop_monitoring_backend.service.FieldService;
import lk.ijse.crop_monitoring_backend.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
