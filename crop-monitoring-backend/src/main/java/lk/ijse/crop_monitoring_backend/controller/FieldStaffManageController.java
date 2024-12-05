package lk.ijse.crop_monitoring_backend.controller;

import lk.ijse.crop_monitoring_backend.customObj.FieldStaffResponse;
import lk.ijse.crop_monitoring_backend.customObj.StaffResponse;
import lk.ijse.crop_monitoring_backend.dto.FieldStaffDTO;
import lk.ijse.crop_monitoring_backend.dto.StaffDTO;
import lk.ijse.crop_monitoring_backend.exception.DataPersistFailedException;
import lk.ijse.crop_monitoring_backend.exception.NotFoundException;
import lk.ijse.crop_monitoring_backend.service.FieldService;
import lk.ijse.crop_monitoring_backend.service.FieldStaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/field_staff")
@CrossOrigin
@RequiredArgsConstructor
public class FieldStaffManageController {

    @Autowired
    private final FieldStaffService fieldStaffService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> assignFieldToStaff(@RequestBody FieldStaffDTO fieldStaffDTO) {
        try {
            fieldStaffService.assignFieldToStaff(fieldStaffDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistFailedException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/all_assigns", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FieldStaffDTO> getAllAssigns() {
        return fieldStaffService.getAllAssigns();
    }

    @GetMapping(value = "/get/{field_staff_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FieldStaffResponse getAssignById(@PathVariable("field_staff_id") int field_staff_id) {
        return fieldStaffService.getSelectedAssign(field_staff_id);
    }

    @DeleteMapping(value = "/delete/{field_staff_id}")
    public ResponseEntity<Void> cancelAssign(@PathVariable("field_staff_id") int field_staff_id) {
        try {
            fieldStaffService.deleteAssign(field_staff_id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (NotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
