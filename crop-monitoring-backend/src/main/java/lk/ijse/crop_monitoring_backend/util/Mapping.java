package lk.ijse.crop_monitoring_backend.util;

import lk.ijse.crop_monitoring_backend.dto.*;
import lk.ijse.crop_monitoring_backend.entity.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Mapping {

    @Autowired
    private ModelMapper modelMapper;

    public StaffDTO convertToStaffDTO(StaffEntity staffEntity) {
        return modelMapper.map(staffEntity, StaffDTO.class);
    }

    public StaffEntity convertToStaffEntity(StaffDTO staffDTO) {
        return modelMapper.map(staffDTO, StaffEntity.class);
    }

    public FieldDTO convertToFieldDTO(FieldEntity fieldEntity) {
        return modelMapper.map(fieldEntity, FieldDTO.class);
    }

    public FieldEntity convertToFieldEntity(FieldDTO fieldDTO) {
        return modelMapper.map(fieldDTO, FieldEntity.class);
    }

    public EquipmentDTO convertToEquipmentDTO(EquipmentEntity equipmentEntity) {
        return modelMapper.map(equipmentEntity, EquipmentDTO.class);
    }

    public EquipmentEntity convertToEquipmentEntity(EquipmentDTO equipmentDTO) {
        return modelMapper.map(equipmentDTO, EquipmentEntity.class);
    }

    public VehicleDTO convertToVehicleDTO(VehicleEntity vehicleEntity) {
        return modelMapper.map(vehicleEntity, VehicleDTO.class);
    }

    public VehicleEntity convertToVehicleEntity(VehicleDTO vehicleDTO) {
        return modelMapper.map(vehicleDTO, VehicleEntity.class);
    }

    public CropDTO convertToCropDTO(CropEntity cropEntity) {
        return modelMapper.map(cropEntity, CropDTO.class);
    }

    public CropEntity convertToCropEntity(CropDTO cropDTO) {
        return modelMapper.map(cropDTO, CropEntity.class);
    }

    public UserDTO convertToUserDTO(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDTO.class);
    }

    public UserEntity convertToUserEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, UserEntity.class);
    }

    public CropDetailsDTO convertToCropDetailsDTO(CropDetailsEnttiy cropDetailsEnttiy) {
        return modelMapper.map(cropDetailsEnttiy, CropDetailsDTO.class);
    }

    public CropDetailsEnttiy convertToCropDetailsEnttiy(CropDetailsDTO cropDetailsDTO) {
        return modelMapper.map(cropDetailsDTO, CropDetailsEnttiy.class);
    }


    public FieldStaffDTO convertToFieldStaffDTO(FieldStaff fieldStaff) {
        return modelMapper.map(fieldStaff, FieldStaffDTO.class);
    }

    public FieldStaff convertToFieldStaff(FieldStaffDTO fieldStaffDTO) {
        return modelMapper.map(fieldStaffDTO, FieldStaff.class);
    }

}
