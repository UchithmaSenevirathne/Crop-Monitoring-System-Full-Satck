package lk.ijse.crop_monitoring_backend.service.impl;

import lk.ijse.crop_monitoring_backend.dao.StaffDAO;
import lk.ijse.crop_monitoring_backend.dto.StaffDTO;
import lk.ijse.crop_monitoring_backend.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StaffServiceImpl implements StaffService {

    @Autowired
    private StaffDAO staffDAO;

    @Override
    public void addStaff(StaffDTO staffDTO) {

    }
}
