package lk.ijse.crop_monitoring_backend.controller;

import lk.ijse.crop_monitoring_backend.dto.StaffDTO;
import lk.ijse.crop_monitoring_backend.exception.DataPersistFailedException;
import lk.ijse.crop_monitoring_backend.service.StaffService;
import lk.ijse.crop_monitoring_backend.util.Enums.Designation;
import lk.ijse.crop_monitoring_backend.util.Enums.FieldName;
import lk.ijse.crop_monitoring_backend.util.Enums.Gender;
import lk.ijse.crop_monitoring_backend.util.Enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
