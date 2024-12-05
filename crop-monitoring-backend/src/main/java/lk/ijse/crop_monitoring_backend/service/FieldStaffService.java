package lk.ijse.crop_monitoring_backend.service;

import lk.ijse.crop_monitoring_backend.customObj.FieldStaffResponse;
import lk.ijse.crop_monitoring_backend.dto.FieldStaffDTO;

import java.util.List;

public interface FieldStaffService {
    void assignFieldToStaff(FieldStaffDTO fieldStaffDTO);

    List<FieldStaffDTO> getAllAssigns();

    FieldStaffResponse getSelectedAssign(int fieldStaffId);

    void deleteAssign(int fieldStaffId);
}
