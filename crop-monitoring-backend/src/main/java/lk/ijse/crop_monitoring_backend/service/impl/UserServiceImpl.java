package lk.ijse.crop_monitoring_backend.service.impl;

import lk.ijse.crop_monitoring_backend.dao.UserDAO;
import lk.ijse.crop_monitoring_backend.dto.UserDTO;
import lk.ijse.crop_monitoring_backend.entity.UserEntity;
import lk.ijse.crop_monitoring_backend.exception.NotFoundException;
import lk.ijse.crop_monitoring_backend.service.UserService;
import lk.ijse.crop_monitoring_backend.util.Mapping;
import lk.ijse.crop_monitoring_backend.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private Mapping mapping;

    @Override
    public int saveUser(UserDTO userDTO) {
        if (userDAO.existsByEmail(userDTO.getEmail())) {
            return VarList.Not_Acceptable;
        }else{
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
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
    public UserDetails loadUserByUsername(String email) {
        UserEntity userEntity = userDAO.findByEmail(email);
        return new org.springframework.security.core.userdetails.User(userEntity.getEmail(), userEntity.getPassword(), getAuthority(userEntity));
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        UserEntity tmpUserEntity = userDAO.findByEmail(userDTO.getEmail());
        if(tmpUserEntity == null){
            throw new NotFoundException("User Not Found");
        }else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            tmpUserEntity.setEmail(userDTO.getEmail());
            tmpUserEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            tmpUserEntity.setRole(userDTO.getRole());
        }
    }

    @Override
    public void deleteUser(String staffEmail) {
        UserEntity tmpUserEntity = userDAO.findByEmail(staffEmail);
        if(tmpUserEntity == null){
            throw new NotFoundException("User Not Found");
        }else {
            userDAO.deleteByEmail(staffEmail);
        }
    }

    private Set<SimpleGrantedAuthority> getAuthority(UserEntity userEntity) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(userEntity.getRole()));
        return authorities;
    }
}
