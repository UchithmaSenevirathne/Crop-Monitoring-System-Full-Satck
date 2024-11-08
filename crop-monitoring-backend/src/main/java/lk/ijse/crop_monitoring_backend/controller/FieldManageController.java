package lk.ijse.crop_monitoring_backend.controller;

import lk.ijse.crop_monitoring_backend.dto.FieldDTO;
import lk.ijse.crop_monitoring_backend.exception.DataPersistFailedException;
import lk.ijse.crop_monitoring_backend.service.FieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @RequestPart("fieldImage1") String fieldImage1,
            @RequestPart("fieldImage2") String fieldImage2
    ){
        try {
            Double parsedExtentSize = Double.parseDouble(extentSize);

            FieldDTO fieldDTO = new FieldDTO();
            fieldDTO.setFieldName(fieldName);
            fieldDTO.setFieldLocation(fieldLocation);
            fieldDTO.setExtentSize(parsedExtentSize);
            fieldDTO.setFieldImage1(fieldImage1);
            fieldDTO.setFieldImage2(fieldImage2);

            fieldService.saveField(fieldDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistFailedException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
