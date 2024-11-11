package lk.ijse.crop_monitoring_backend.service.impl;

import lk.ijse.crop_monitoring_backend.dao.FieldStaffDAO;
import lk.ijse.crop_monitoring_backend.dao.StaffDAO;
import lk.ijse.crop_monitoring_backend.dao.UserDAO;
import lk.ijse.crop_monitoring_backend.dto.FieldStaffDTO;
import lk.ijse.crop_monitoring_backend.dto.StaffDTO;
import lk.ijse.crop_monitoring_backend.dto.UserDTO;
import lk.ijse.crop_monitoring_backend.service.StaffService;
import lk.ijse.crop_monitoring_backend.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StaffServiceImpl implements StaffService {

    @Autowired
    private StaffDAO staffDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private Mapping mapping;

    @Override
    public void addStaff(StaffDTO staffDTO) {
        staffDAO.save(mapping.convertToStaffEntity(staffDTO));

        if (staffDTO.getRole().equals("MANAGER") || staffDTO.getRole().equals("ADMINISTRATIVE") || staffDTO.getRole().equals("SCIENTIST")){
            userDAO.save(mapping.convertToUserEntity(new UserDTO(staffDTO.getEmail(), staffDTO.getPassword(), staffDTO.getRole())));
        }
    }
}
