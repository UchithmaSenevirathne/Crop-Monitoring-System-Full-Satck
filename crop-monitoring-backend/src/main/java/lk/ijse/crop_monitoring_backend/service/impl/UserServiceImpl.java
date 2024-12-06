package lk.ijse.crop_monitoring_backend.service.impl;

import lk.ijse.crop_monitoring_backend.dao.StaffDAO;
import lk.ijse.crop_monitoring_backend.dao.UserDAO;
import lk.ijse.crop_monitoring_backend.dto.UserDTO;
import lk.ijse.crop_monitoring_backend.entity.StaffEntity;
import lk.ijse.crop_monitoring_backend.entity.UserEntity;
import lk.ijse.crop_monitoring_backend.exception.NotFoundException;
import lk.ijse.crop_monitoring_backend.service.UserService;
import lk.ijse.crop_monitoring_backend.util.Mapping;
import lk.ijse.crop_monitoring_backend.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private StaffDAO staffDAO;

    @Autowired
    private Mapping mapping;

    @Override
    public int saveUser(UserDTO userDTO) {
        System.out.println("save service");
        if (userDAO.existsByEmail(userDTO.getEmail())) {
            return VarList.Not_Acceptable;
        } else if (!staffDAO.existsByEmail(userDTO.getEmail())) {
            return VarList.Forbidden;
        } else{
            StaffEntity staffEntity = staffDAO.findByEmail(userDTO.getEmail());
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userDTO.setRole(staffEntity.getRole());
            userDAO.save(mapping.convertToUserEntity(userDTO));
            return VarList.Created;
        }
    }

    @Override
    public UserDTO loadUserDetailsByUsername(String email) {
        UserEntity user = userDAO.findByEmail(email);
        return mapping.convertToUserDTO(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userDAO.findByEmail(email);
        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new org.springframework.security.core.userdetails.User(
                userEntity.getEmail(),
                userEntity.getPassword(),
                getAuthority(userEntity)
        );
    }

    @Override
    public void updateUser(UserDTO updateBuidUserDto) {
        Optional<UserEntity> tmpUserEntity = userDAO.findById(updateBuidUserDto.getUserId());
        if(!tmpUserEntity.isPresent()){
            throw new NotFoundException("User Not Found");
        }else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            tmpUserEntity.get().setEmail(updateBuidUserDto.getEmail());
            tmpUserEntity.get().setPassword(passwordEncoder.encode(updateBuidUserDto.getPassword()));
            tmpUserEntity.get().setRole(updateBuidUserDto.getRole());
        }
    }

    @Override
    public void deleteUser(int userId) {
        Optional<UserEntity> tmpUserEntity = userDAO.findById(userId);
        if(!tmpUserEntity.isPresent()){
            throw new NotFoundException("User Not Found");
        }else {
            userDAO.deleteById(userId);
        }
    }

    @Override
    public UserDTO getSelectedUser(int userId) {
        return mapping.convertToUserDTO(userDAO.getUserEntitiesByUserId(userId));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return mapping.convertToUserDTOList(userDAO.findAll());
    }

    @Override
    public void updateUserEmailAndRole(String email, String role) {
        UserEntity tmpUserEntity = userDAO.findByEmail(email);
        if(tmpUserEntity == null){
            throw new NotFoundException("User Not Found");
        }else {
            tmpUserEntity.setEmail(email);
            tmpUserEntity.setRole(role);
        }
    }

    @Override
    public void updateUserPassword(int id, String updatePassword) {
        Optional<UserEntity> tmpUserEntity = userDAO.findById(id);
        if(!tmpUserEntity.isPresent()){
            throw new NotFoundException("User Not Found");
        }else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            tmpUserEntity.get().setPassword(passwordEncoder.encode(updatePassword));
        }
    }

    private Set<SimpleGrantedAuthority> getAuthority(UserEntity userEntity) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + userEntity.getRole())); // Prefix roles with 'ROLE_'
        return authorities;
    }
}
