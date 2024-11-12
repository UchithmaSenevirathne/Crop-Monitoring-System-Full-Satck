package lk.ijse.crop_monitoring_backend.service.impl;

import lk.ijse.crop_monitoring_backend.dao.FieldStaffDAO;
import lk.ijse.crop_monitoring_backend.dao.UserDAO;
import lk.ijse.crop_monitoring_backend.dto.FieldStaffDTO;
import lk.ijse.crop_monitoring_backend.service.FieldStaffService;
import lk.ijse.crop_monitoring_backend.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
