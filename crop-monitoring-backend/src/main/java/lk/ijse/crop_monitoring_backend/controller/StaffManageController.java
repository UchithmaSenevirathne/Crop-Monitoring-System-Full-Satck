package lk.ijse.crop_monitoring_backend.controller;

import lk.ijse.crop_monitoring_backend.dto.StaffDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/staff")
@CrossOrigin
@RequiredArgsConstructor
public class StaffManageController {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveItem(@RequestBody StaffDTO staffDTO) {
        try {
//            String base64Image = Base64.getEncoder().encodeToString(itemImage.getBytes());

            double parsedUnitPrice = Double.parseDouble(unitPrice);
            int parsedItemQty = Integer.parseInt(itemQty);
            int parsedCategoryId = Integer.parseInt(categoryId);

            ItemDTO itemDTO = new ItemDTO();

            itemDTO.setItemName(itemName);
            itemDTO.setItemPrice(parsedUnitPrice);
            itemDTO.setItemQuantity(parsedItemQty);
            itemDTO.setItemImage(itemImage);
            itemDTO.setCategoryId(parsedCategoryId);

            itemService.saveItem(itemDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistFailedException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
