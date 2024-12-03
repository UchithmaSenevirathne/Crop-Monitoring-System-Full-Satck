package lk.ijse.crop_monitoring_backend.service;

import lk.ijse.crop_monitoring_backend.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    int saveUser(UserDTO userDTO);

    UserDTO loadUserDetailsByUsername(String email);

    UserDetails loadUserByUsername(String email);

    void updateUser(UserDTO userDTO);

    void deleteUser(int userId);

    UserDTO getSelectedUser(int userId);

    List<UserDTO> getAllUsers();

    void updateUserEmailAndRole(String email, String role);

    void updateUserPassword(int id, String updatePassword);
}
