package lk.ijse.crop_monitoring_backend.controller;

import lk.ijse.crop_monitoring_backend.customObj.EquipResponse;
import lk.ijse.crop_monitoring_backend.customObj.StaffResponse;
import lk.ijse.crop_monitoring_backend.dto.*;
import lk.ijse.crop_monitoring_backend.exception.DataPersistFailedException;
import lk.ijse.crop_monitoring_backend.exception.NotFoundException;
import lk.ijse.crop_monitoring_backend.service.StaffService;
import lk.ijse.crop_monitoring_backend.service.UserService;
import lk.ijse.crop_monitoring_backend.util.Enums.Designation;
import lk.ijse.crop_monitoring_backend.util.Enums.FieldName;
import lk.ijse.crop_monitoring_backend.util.Enums.Gender;
import lk.ijse.crop_monitoring_backend.util.Enums.Role;
import lk.ijse.crop_monitoring_backend.util.JwtUtil;
import lk.ijse.crop_monitoring_backend.util.VarList;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/staff")
@CrossOrigin
@RequiredArgsConstructor
public class StaffManageController {
    @Autowired
    private final StaffService staffService;

    @Autowired
    private final UserService userService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addStaff(@RequestBody StaffDTO staffDTO) {
        try {
            staffService.addStaff(staffDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistFailedException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/update/{staffId}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateStaff(@RequestBody StaffDTO staffDTO, @PathVariable ("staffId") int staffId) {
        try {
            staffService.updateSatff(staffDTO, staffId);
            UserDTO isUser = userService.loadUserDetailsByUsername(staffDTO.getEmail());

            if (isUser != null) {
                userService.updateUserEmailAndRole(staffDTO.getEmail(), staffDTO.getRole());
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (NotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/all_staff", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StaffDTO> getAllStaff() {
        return staffService.getAllStaff();
    }

    @GetMapping(value = "/get/{staffId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public StaffResponse getStaffById(@PathVariable("staffId") int staffId) {
        return staffService.getSelectedStaff(staffId);
    }

    @DeleteMapping(value = "/delete/{staffId}")
    public ResponseEntity<Void> deleteStaff(@PathVariable("staffId") int staffId) {
        try {
            staffService.deleteStaff(staffId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (NotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/designations", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getDesignations() {
        List<String> designations = Arrays.stream(Designation.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        return ResponseEntity.ok(designations);
    }

    @GetMapping(value = "/genders", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getGenders() {
        List<String> genders = Arrays.stream(Gender.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        return ResponseEntity.ok(genders);
    }

    @GetMapping(value = "/roles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getRoles() {
        List<String> roles = Arrays.stream(Role.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        return ResponseEntity.ok(roles);
    }
}
