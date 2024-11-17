package lk.ijse.crop_monitoring_backend.controller;

import lk.ijse.crop_monitoring_backend.customObj.CropResponse;
import lk.ijse.crop_monitoring_backend.customObj.FieldResponse;
import lk.ijse.crop_monitoring_backend.dto.CropDTO;
import lk.ijse.crop_monitoring_backend.dto.FieldDTO;
import lk.ijse.crop_monitoring_backend.exception.DataPersistFailedException;
import lk.ijse.crop_monitoring_backend.exception.NotFoundException;
import lk.ijse.crop_monitoring_backend.service.FieldService;
import lk.ijse.crop_monitoring_backend.util.AppUtil;
import lk.ijse.crop_monitoring_backend.util.Enums.FieldName;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/field")
@CrossOrigin
@RequiredArgsConstructor
public class FieldManageController {

    @Autowired
    private final FieldService fieldService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addField(
            @RequestPart("fieldName") String fieldName,
            @RequestPart("fieldLocation") String fieldLocation,
            @RequestPart("extentSize") String extentSize,
            @RequestPart("fieldImage1") MultipartFile fieldImage1,
            @RequestPart("fieldImage2") MultipartFile fieldImage2
    ){
        try {
            byte[] bytesImg1 = fieldImage1.getBytes();
            byte[] bytesImg2 = fieldImage2.getBytes();
            String base64Img1 = AppUtil.toBase64Img(bytesImg1);
            String base64Img2 = AppUtil.toBase64Img(bytesImg2);

            Double parsedExtentSize = Double.parseDouble(extentSize);

            FieldDTO fieldDTO = new FieldDTO();
            fieldDTO.setFieldName(fieldName);
            fieldDTO.setFieldLocation(fieldLocation);
            fieldDTO.setExtentSize(parsedExtentSize);
            fieldDTO.setFieldImage1(base64Img1);
            fieldDTO.setFieldImage2(base64Img2);

            fieldService.saveField(fieldDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistFailedException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{fieldCode}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateField(
            @PathVariable ("fieldCode") int fieldCode,
            @RequestPart("fieldName") String fieldName,
            @RequestPart("fieldLocation") String fieldLocation,
            @RequestPart("extentSize") String extentSize,
            @RequestPart("fieldImage1") MultipartFile fieldImage1,
            @RequestPart("fieldImage2") MultipartFile fieldImage2
    ){
        try {
            byte[] bytesImg1 = fieldImage1.getBytes();
            byte[] bytesImg2 = fieldImage2.getBytes();
            String base64Img1 = AppUtil.toBase64Img(bytesImg1);
            String base64Img2 = AppUtil.toBase64Img(bytesImg2);

            Double parsedExtentSize = Double.parseDouble(extentSize);

            FieldDTO fieldDTO = new FieldDTO();
            fieldDTO.setFieldCode(fieldCode);
            fieldDTO.setFieldName(fieldName);
            fieldDTO.setFieldLocation(fieldLocation);
            fieldDTO.setExtentSize(parsedExtentSize);
            fieldDTO.setFieldImage1(base64Img1);
            fieldDTO.setFieldImage2(base64Img2);

            fieldService.updateField(fieldDTO);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (NotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/all_fields", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FieldDTO> getAllFields() {
        return fieldService.getAllFields();
    }

    @GetMapping(value = "/{fieldCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FieldResponse getFieldById(@PathVariable("fieldCode") int fieldCode) {
        return fieldService.getSelectedField(fieldCode);
    }

    @DeleteMapping(value = "/{fieldCode}")
    public ResponseEntity<Void> deleteField(@PathVariable("fieldCode") int fieldCode) {
        try {
            fieldService.deleteField(fieldCode);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (NotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{fieldName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public int getFieldID(@PathVariable("fieldName") String fieldName) {
        return fieldService.getFieldID(fieldName);
    }

    @GetMapping(value = "/names", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getFieldNames() {
        List<String> fieldNames = Arrays.stream(FieldName.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        return ResponseEntity.ok(fieldNames);
    }
}
