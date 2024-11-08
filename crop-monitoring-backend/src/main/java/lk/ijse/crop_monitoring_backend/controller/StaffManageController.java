package lk.ijse.crop_monitoring_backend.controller;

import lk.ijse.crop_monitoring_backend.dto.StaffDTO;
import lk.ijse.crop_monitoring_backend.exception.DataPersistFailedException;
import lk.ijse.crop_monitoring_backend.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
