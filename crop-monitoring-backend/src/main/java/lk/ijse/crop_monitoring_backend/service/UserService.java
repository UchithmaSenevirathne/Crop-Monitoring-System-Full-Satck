package lk.ijse.crop_monitoring_backend.service;

import lk.ijse.crop_monitoring_backend.dto.UserDTO;

public interface UserService {
    int saveUser(UserDTO userDTO);
}
