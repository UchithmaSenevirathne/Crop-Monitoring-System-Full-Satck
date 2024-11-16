package lk.ijse.crop_monitoring_backend.dto;

import jakarta.persistence.Column;
import lk.ijse.crop_monitoring_backend.customObj.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO implements UserResponse {
    private String email;
    private String password;
    private String role;
}
