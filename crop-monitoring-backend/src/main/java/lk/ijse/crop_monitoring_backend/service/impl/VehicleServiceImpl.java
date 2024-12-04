package lk.ijse.crop_monitoring_backend.service.impl;

import lk.ijse.crop_monitoring_backend.customObj.VehicleResponse;
import lk.ijse.crop_monitoring_backend.dao.EquipmentDAO;
import lk.ijse.crop_monitoring_backend.dao.StaffDAO;
import lk.ijse.crop_monitoring_backend.dao.VehicleDAO;
import lk.ijse.crop_monitoring_backend.dto.VehicleDTO;
import lk.ijse.crop_monitoring_backend.entity.EquipmentEntity;
import lk.ijse.crop_monitoring_backend.entity.FieldEntity;
import lk.ijse.crop_monitoring_backend.entity.StaffEntity;
import lk.ijse.crop_monitoring_backend.entity.VehicleEntity;
import lk.ijse.crop_monitoring_backend.exception.DataPersistFailedException;
import lk.ijse.crop_monitoring_backend.exception.NotFoundException;
import lk.ijse.crop_monitoring_backend.service.VehicleService;
import lk.ijse.crop_monitoring_backend.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleDAO vehicleDAO;

    @Autowired
    private StaffDAO staffDAO;

    @Autowired
    private Mapping mapping;

    @Override
    public void addVehicle(VehicleDTO vehicleDTO) {
        vehicleDAO.save(mapping.convertToVehicleEntity(vehicleDTO));
    }

    @Override
    public void updateVehicle(VehicleDTO vehicleDTO, int vehicleCode) {
        Optional<VehicleEntity> tmpEntity = vehicleDAO.findById(vehicleCode);
        if(!tmpEntity.isPresent()){
            throw new NotFoundException("Vehicle Not Found");
        }else {
            StaffEntity staff = staffDAO.findById(vehicleDTO.getStaffId())
                    .orElseThrow(() -> new DataPersistFailedException("Staff not found"));

            tmpEntity.get().setLicensePlateNumber(vehicleDTO.getLicensePlateNumber());
            tmpEntity.get().setVehicleCategory(vehicleDTO.getVehicleCategory());
            tmpEntity.get().setFuelType(vehicleDTO.getFuelType());
            tmpEntity.get().setStatus(vehicleDTO.getStatus());
            tmpEntity.get().setRemarks(vehicleDTO.getRemarks());
            tmpEntity.get().setStaff(staff);
        }
    }

    @Override
    public List<VehicleDTO> getAllVehicle() {
        return mapping.convertToVehicleDTOList(vehicleDAO.findAll());
    }

    @Override
    public VehicleResponse getSelectedVehicle(int vehicleCode) {
        if (vehicleDAO.existsById(vehicleCode)) {
            return mapping.convertToVehicleDTO(vehicleDAO.getReferenceById(vehicleCode));
        }else{
            throw new NotFoundException("Vehicle not found");
        }
    }

    @Override
    public void deleteVehi(int vehicleCode) {
        Optional<VehicleEntity> findId = vehicleDAO.findById(vehicleCode);
        if(!findId.isPresent()){
            throw new NotFoundException("Vehicle not found");
        }else {
            vehicleDAO.deleteById(vehicleCode);
        }
    }
}
