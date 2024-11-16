package lk.ijse.crop_monitoring_backend.service.impl;

import lk.ijse.crop_monitoring_backend.customObj.CropResponse;
import lk.ijse.crop_monitoring_backend.dao.CropDAO;
import lk.ijse.crop_monitoring_backend.dao.FieldDAO;
import lk.ijse.crop_monitoring_backend.dto.CropDTO;
import lk.ijse.crop_monitoring_backend.entity.CropEntity;
import lk.ijse.crop_monitoring_backend.exception.NotFoundException;
import lk.ijse.crop_monitoring_backend.service.CropService;
import lk.ijse.crop_monitoring_backend.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CropServiceImpl implements CropService {

    @Autowired
    private CropDAO cropDAO;

    @Autowired
    private Mapping mapping;

    @Override
    public void saveCrop(CropDTO cropDTO) {
        cropDAO.save(mapping.convertToCropEntity(cropDTO));
    }

    @Override
    public void updateCrop(CropDTO cropDTO) {
        Optional<CropEntity> tmpCropEntity = cropDAO.findById(cropDTO.getCropCode());
        if (!tmpCropEntity.isPresent()) {
            throw new NotFoundException("Crop not found");
        }else {
            tmpCropEntity.get().setCropCommonName(cropDTO.getCropCommonName());
            tmpCropEntity.get().setCropScientificName(cropDTO.getCropScientificName());
            tmpCropEntity.get().setCropImage(cropDTO.getCropImage());
            tmpCropEntity.get().setCategory(cropDTO.getCategory());
            tmpCropEntity.get().setCropSeason(cropDTO.getCropSeason());
        }
    }

    @Override
    public List<CropDTO> getAllCrops() {
        return mapping.convertToCropDTOList(cropDAO.findAll());
    }

    @Override
    public CropResponse getSelectedCrop(int cropCode) {
        if (cropDAO.existsById(cropCode)) {
            return mapping.convertToCropDTO(cropDAO.getReferenceById(cropCode));
        }else{
            throw new NotFoundException("Crop not found");
        }
    }
}
