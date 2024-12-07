package lk.ijse.crop_monitoring_backend.service;

import lk.ijse.crop_monitoring_backend.customObj.StaffResponse;
import lk.ijse.crop_monitoring_backend.dto.StaffDTO;

import java.util.List;

public interface StaffService {
    void addStaff(StaffDTO staffDTO);

    void updateSatff(StaffDTO staffDTO, int staffId);

    StaffDTO getSelectedStaff(int staffId);

    List<StaffDTO> getAllStaff();

    void deleteStaff(int staffId);

    String getEmailById(int staffId);

    StaffResponse getStaffByEmail(String email);
}
