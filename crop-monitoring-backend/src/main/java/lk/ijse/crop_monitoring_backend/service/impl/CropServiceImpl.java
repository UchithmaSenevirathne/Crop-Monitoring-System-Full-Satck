package lk.ijse.crop_monitoring_backend.service.impl;

import lk.ijse.crop_monitoring_backend.dao.CropDAO;
import lk.ijse.crop_monitoring_backend.dao.FieldDAO;
import lk.ijse.crop_monitoring_backend.dto.CropDTO;
import lk.ijse.crop_monitoring_backend.service.CropService;
import lk.ijse.crop_monitoring_backend.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
