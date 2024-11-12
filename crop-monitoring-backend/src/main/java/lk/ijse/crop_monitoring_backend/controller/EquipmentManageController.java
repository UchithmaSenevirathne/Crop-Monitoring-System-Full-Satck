package lk.ijse.crop_monitoring_backend.controller;

import lk.ijse.crop_monitoring_backend.dto.EquipmentDTO;
import lk.ijse.crop_monitoring_backend.dto.StaffDTO;
import lk.ijse.crop_monitoring_backend.exception.DataPersistFailedException;
import lk.ijse.crop_monitoring_backend.service.EquipmentService;
import lk.ijse.crop_monitoring_backend.service.StaffService;
import lk.ijse.crop_monitoring_backend.util.Enums.Availability;
import lk.ijse.crop_monitoring_backend.util.Enums.EquipmentName;
import lk.ijse.crop_monitoring_backend.util.Enums.EquipmentType;
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
@RequestMapping("/equipment")
@CrossOrigin
@RequiredArgsConstructor
public class EquipmentManageController {

    @Autowired
    private final EquipmentService equipmentService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addStaff(@RequestBody EquipmentDTO equipmentDTO) {
        try {
//            int fieldCode = equipmentDTO.getFieldCode();
//            int staffId = equipmentDTO.getStaffId();
//
//            EquipmentDTO newDTO = new EquipmentDTO();
//            newDTO.setName(equipmentDTO.getName());
//            newDTO.setType(equipmentDTO.getType());
//            newDTO.setStatus(equipmentDTO.getStatus());
//            newDTO.setFieldCode(fieldCode);
//            newDTO.setStaffId(staffId);

            equipmentService.addEquipment(equipmentDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistFailedException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/names", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getEquipmentNames() {
        List<String> names = Arrays.stream(EquipmentName.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        return ResponseEntity.ok(names);
    }

    @GetMapping(value = "/types", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getEquipmentTypes() {
        List<String> types = Arrays.stream(EquipmentType.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        return ResponseEntity.ok(types);
    }

    @GetMapping(value = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getStatus() {
        List<String> status = Arrays.stream(Availability.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        return ResponseEntity.ok(status);
    }
}
