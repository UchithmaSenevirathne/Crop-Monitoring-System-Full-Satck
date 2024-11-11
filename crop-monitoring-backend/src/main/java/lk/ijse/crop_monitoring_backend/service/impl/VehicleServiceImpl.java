package lk.ijse.crop_monitoring_backend.service.impl;

import lk.ijse.crop_monitoring_backend.dao.EquipmentDAO;
import lk.ijse.crop_monitoring_backend.dao.VehicleDAO;
import lk.ijse.crop_monitoring_backend.dto.VehicleDTO;
import lk.ijse.crop_monitoring_backend.service.VehicleService;
import lk.ijse.crop_monitoring_backend.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleDAO vehicleDAO;

    @Autowired
    private Mapping mapping;

    @Override
    public void addVehicle(VehicleDTO vehicleDTO) {
        vehicleDAO.save(mapping.convertToVehicleEntity(vehicleDTO));
    }
}
