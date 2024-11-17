package lk.ijse.crop_monitoring_backend.service;

import lk.ijse.crop_monitoring_backend.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    int saveUser(UserDTO userDTO);

    UserDTO loadUserDetailsByUsername(String email);

    UserDetails loadUserByUsername(String email);

    void updateUser(UserDTO userDTO);

    void deleteUser(String staffEmail);
}
