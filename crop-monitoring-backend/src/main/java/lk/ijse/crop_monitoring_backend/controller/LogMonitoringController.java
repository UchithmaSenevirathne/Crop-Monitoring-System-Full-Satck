package lk.ijse.crop_monitoring_backend.controller;

import lk.ijse.crop_monitoring_backend.customObj.CropDetailsResponse;
import lk.ijse.crop_monitoring_backend.customObj.CropResponse;
import lk.ijse.crop_monitoring_backend.dto.CropDTO;
import lk.ijse.crop_monitoring_backend.dto.CropDetailsDTO;
import lk.ijse.crop_monitoring_backend.dto.FieldDTO;
import lk.ijse.crop_monitoring_backend.exception.DataPersistFailedException;
import lk.ijse.crop_monitoring_backend.exception.NotFoundException;
import lk.ijse.crop_monitoring_backend.service.FieldService;
import lk.ijse.crop_monitoring_backend.service.LogService;
import lk.ijse.crop_monitoring_backend.util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/log")
@CrossOrigin
@RequiredArgsConstructor
public class LogMonitoringController {

    @Autowired
    private final LogService logService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addLog(
            @RequestPart("logDate") String logDate,
            @RequestPart("logDetails") String logDetails,
            @RequestPart("cropStatus") String cropStatus,
            @RequestPart("observedImage") MultipartFile observedImage,
            @RequestPart("staffId") String staffId,
            @RequestPart("fieldCode") String fieldCode,
            @RequestPart("cropCode") String cropCode
    ){
        try {
            byte[] bytesImg = observedImage.getBytes();
            String base64Img = AppUtil.toBase64Img(bytesImg);

            int staffIdInt = Integer.parseInt(staffId);
            int fieldCodeInt = Integer.parseInt(fieldCode);
            int cropCodeInt = Integer.parseInt(cropCode);

            CropDetailsDTO cropDetailsDTO = new CropDetailsDTO();
            cropDetailsDTO.setLogDate(logDate);
            cropDetailsDTO.setLogDetails(logDetails);
            cropDetailsDTO.setCropStatus(cropStatus);
            cropDetailsDTO.setObservedImage(base64Img);
            cropDetailsDTO.setStaffId(staffIdInt);
            cropDetailsDTO.setFieldCode(fieldCodeInt);
            cropDetailsDTO.setCropCode(cropCodeInt);

            logService.saveLog(cropDetailsDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistFailedException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/update/{logCode}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateLog(
            @PathVariable ("logCode") int logCode,
            @RequestPart("logDate") String logDate,
            @RequestPart("logDetails") String logDetails,
            @RequestPart("cropStatus") String cropStatus,
            @RequestPart("observedImage") MultipartFile observedImage,
            @RequestPart("staffId") String staffId,
            @RequestPart("fieldCode") String fieldCode,
            @RequestPart("cropCode") String cropCode
    ){
        try {
            byte[] bytesImg = observedImage.getBytes();
            String base64Img = AppUtil.toBase64Img(bytesImg);

            int staffIdInt = Integer.parseInt(staffId);
            int fieldCodeInt = Integer.parseInt(fieldCode);
            int cropCodeInt = Integer.parseInt(cropCode);

            CropDetailsDTO cropDetailsDTO = new CropDetailsDTO();
            cropDetailsDTO.setLogCode(logCode);
            cropDetailsDTO.setLogDate(logDate);
            cropDetailsDTO.setLogDetails(logDetails);
            cropDetailsDTO.setCropStatus(cropStatus);
            cropDetailsDTO.setObservedImage(base64Img);
            cropDetailsDTO.setStaffId(staffIdInt);
            cropDetailsDTO.setFieldCode(fieldCodeInt);
            cropDetailsDTO.setCropCode(cropCodeInt);

            logService.updateLog(cropDetailsDTO);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (NotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/all_logs", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CropDetailsDTO> getAllLogs() {
        return logService.getAllLogs();
    }

    @GetMapping(value = "/get/{logCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CropDetailsResponse getLogById(@PathVariable("logCode") int logCode) {
        return logService.getSelectedLog(logCode);
    }

    @DeleteMapping(value = "/delete/{logCode}")
    public ResponseEntity<Void> deleteLog(@PathVariable("logCode") int logCode) {
        try {
            logService.deleteLog(logCode);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (NotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
