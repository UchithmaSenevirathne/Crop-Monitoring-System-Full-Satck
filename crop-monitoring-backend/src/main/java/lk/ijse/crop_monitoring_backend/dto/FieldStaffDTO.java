package lk.ijse.crop_monitoring_backend.dto;

import lk.ijse.crop_monitoring_backend.customObj.FieldStaffResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FieldStaffDTO implements FieldStaffResponse {
    private int field_staff_id;
    private int fieldCode;
    private int staffId;
    private String assignedDate;
    private String dueDate;
}
