package lk.ijse.crop_monitoring_backend.service.impl;

import lk.ijse.crop_monitoring_backend.dao.EquipmentStaffDAO;
import lk.ijse.crop_monitoring_backend.dao.FieldStaffDAO;
import lk.ijse.crop_monitoring_backend.dao.StaffDAO;
import lk.ijse.crop_monitoring_backend.dao.VehicleStaffDAO;
import lk.ijse.crop_monitoring_backend.dto.EquipmentStaffDTO;
import lk.ijse.crop_monitoring_backend.dto.FieldStaffDTO;
import lk.ijse.crop_monitoring_backend.dto.StaffDTO;
import lk.ijse.crop_monitoring_backend.dto.VehicleStaffDTO;
import lk.ijse.crop_monitoring_backend.entity.FieldStaff;
import lk.ijse.crop_monitoring_backend.entity.StaffEntity;
import lk.ijse.crop_monitoring_backend.service.StaffService;
import lk.ijse.crop_monitoring_backend.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class StaffServiceImpl implements StaffService {

    @Autowired
    private StaffDAO staffDAO;

    @Autowired
    private FieldStaffDAO fieldStaffDAO;

    @Autowired
    private EquipmentStaffDAO equipmentStaffDAO;

    @Autowired
    private VehicleStaffDAO vehicleStaffDAO;
    @Autowired
    private Mapping mapping;

    @Override
    public void addStaff(StaffDTO staffDTO) {

        staffDAO.save(mapping.convertToStaffEntity(staffDTO));

        if(staffDTO.getFieldStaffs() != null || !staffDTO.getFieldStaffs().isEmpty()){
            for(FieldStaffDTO fieldStaffDTO : staffDTO.getFieldStaffs()){
                fieldStaffDAO.save(mapping.convertToFieldStaff(fieldStaffDTO));
            }
        }

        if(staffDTO.getEquipmentStaffs() != null || !staffDTO.getEquipmentStaffs().isEmpty()){
            for(EquipmentStaffDTO equipmentStaffDTO : staffDTO.getEquipmentStaffs()){
                equipmentStaffDAO.save(mapping.convertToEquipmentStaff(equipmentStaffDTO));
            }
        }

        if(staffDTO.getVehicleStaffs() != null || !staffDTO.getVehicleStaffs().isEmpty()){
            for(VehicleStaffDTO vehicleStaffDTO : staffDTO.getVehicleStaffs()){
                vehicleStaffDAO.save(mapping.convertToVehicleStaff(vehicleStaffDTO));
            }
        }
    }
}
