package lk.ijse.crop_monitoring_backend.controller;

import lk.ijse.crop_monitoring_backend.dto.EquipmentDTO;
import lk.ijse.crop_monitoring_backend.dto.VehicleDTO;
import lk.ijse.crop_monitoring_backend.exception.DataPersistFailedException;
import lk.ijse.crop_monitoring_backend.service.EquipmentService;
import lk.ijse.crop_monitoring_backend.service.VehicleService;
import lk.ijse.crop_monitoring_backend.util.Enums.Availability;
import lk.ijse.crop_monitoring_backend.util.Enums.FieldName;
import lk.ijse.crop_monitoring_backend.util.Enums.FuelType;
import lk.ijse.crop_monitoring_backend.util.Enums.VehicleCategory;
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
@RequestMapping("/vehicle")
@CrossOrigin
@RequiredArgsConstructor
public class VehicleManageController {

    @Autowired
    private final VehicleService vehicleService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addVehicle(@RequestBody VehicleDTO vehicleDTO) {
        try {
            vehicleService.addVehicle(vehicleDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistFailedException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getVehicleCategories() {
        List<String> categories = Arrays.stream(VehicleCategory.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categories);
    }

    @GetMapping(value = "/fuel", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getFuelTypes() {
        List<String> fuel = Arrays.stream(FuelType.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        return ResponseEntity.ok(fuel);
    }

    @GetMapping(value = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getStatus() {
        List<String> status = Arrays.stream(Availability.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        return ResponseEntity.ok(status);
    }
}
