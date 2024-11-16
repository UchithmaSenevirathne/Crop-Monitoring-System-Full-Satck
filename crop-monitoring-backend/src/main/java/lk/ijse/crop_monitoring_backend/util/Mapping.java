package lk.ijse.crop_monitoring_backend.util;

import lk.ijse.crop_monitoring_backend.dto.*;
import lk.ijse.crop_monitoring_backend.entity.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public List<StaffDTO> convertToStaffDTOList(List<StaffEntity> staffEntityList) {
        return modelMapper.map(staffEntityList, new TypeToken<List<StaffDTO>>() {}.getType());
    }


    public FieldDTO convertToFieldDTO(FieldEntity fieldEntity) {
        return modelMapper.map(fieldEntity, FieldDTO.class);
    }

    public FieldEntity convertToFieldEntity(FieldDTO fieldDTO) {
        return modelMapper.map(fieldDTO, FieldEntity.class);
    }

    public List<FieldDTO> convertToFieldDTOList(List<FieldEntity> fieldEntityList) {
        return modelMapper.map(fieldEntityList, new TypeToken<List<FieldDTO>>() {}.getType());
    }


    public EquipmentDTO convertToEquipmentDTO(EquipmentEntity equipmentEntity) {
        return modelMapper.map(equipmentEntity, EquipmentDTO.class);
    }

    public EquipmentEntity convertToEquipmentEntity(EquipmentDTO equipmentDTO) {
        return modelMapper.map(equipmentDTO, EquipmentEntity.class);
    }

    public List<EquipmentDTO> convertToEquipDTOList(List<EquipmentEntity> equipmentEntityList) {
        return modelMapper.map(equipmentEntityList, new TypeToken<List<EquipmentDTO>>() {}.getType());
    }


    public VehicleDTO convertToVehicleDTO(VehicleEntity vehicleEntity) {
        return modelMapper.map(vehicleEntity, VehicleDTO.class);
    }

    public VehicleEntity convertToVehicleEntity(VehicleDTO vehicleDTO) {
        return modelMapper.map(vehicleDTO, VehicleEntity.class);
    }

    public List<VehicleDTO> convertToVehicleDTOList(List<VehicleEntity> vehicleEntityList) {
        return modelMapper.map(vehicleEntityList, new TypeToken<List<VehicleDTO>>() {}.getType());
    }



    public CropDTO convertToCropDTO(CropEntity cropEntity) {
        return modelMapper.map(cropEntity, CropDTO.class);
    }

    public CropEntity convertToCropEntity(CropDTO cropDTO) {
        return modelMapper.map(cropDTO, CropEntity.class);
    }

    public List<CropDTO> convertToCropDTOList(List<CropEntity> cropEntityList) {
        return modelMapper.map(cropEntityList, new TypeToken<List<CropDTO>>() {}.getType());
    }



    public UserDTO convertToUserDTO(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDTO.class);
    }

    public UserEntity convertToUserEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, UserEntity.class);
    }

    public List<UserDTO> convertToUserDTOList(List<UserEntity> userEntityList) {
        return modelMapper.map(userEntityList, new TypeToken<List<UserDTO>>() {}.getType());
    }


    public CropDetailsDTO convertToCropDetailsDTO(CropDetailsEnttiy cropDetailsEnttiy) {
        return modelMapper.map(cropDetailsEnttiy, CropDetailsDTO.class);
    }

    public CropDetailsEnttiy convertToCropDetailsEnttiy(CropDetailsDTO cropDetailsDTO) {
        return modelMapper.map(cropDetailsDTO, CropDetailsEnttiy.class);
    }
    public List<CropDetailsDTO> convertToLogDTOList(List<CropDetailsEnttiy> cropDetailsEnttiyList) {
        return modelMapper.map(cropDetailsEnttiyList, new TypeToken<List<CropDetailsDTO>>() {}.getType());
    }

    public FieldStaffDTO convertToFieldStaffDTO(FieldStaff fieldStaff) {
        return modelMapper.map(fieldStaff, FieldStaffDTO.class);
    }

    public FieldStaff convertToFieldStaff(FieldStaffDTO fieldStaffDTO) {
        return modelMapper.map(fieldStaffDTO, FieldStaff.class);
    }

    public List<FieldStaffDTO> convertToFieldStaffDTOList(List<FieldStaff> fieldStaffList) {
        return modelMapper.map(fieldStaffList, new TypeToken<List<FieldStaffDTO>>() {}.getType());
    }

}
