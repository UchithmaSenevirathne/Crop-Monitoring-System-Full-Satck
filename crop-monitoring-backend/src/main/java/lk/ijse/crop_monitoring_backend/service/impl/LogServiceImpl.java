package lk.ijse.crop_monitoring_backend.service.impl;

import lk.ijse.crop_monitoring_backend.customObj.CropDetailsResponse;
import lk.ijse.crop_monitoring_backend.dao.CropDAO;
import lk.ijse.crop_monitoring_backend.dao.FieldDAO;
import lk.ijse.crop_monitoring_backend.dao.LogDAO;
import lk.ijse.crop_monitoring_backend.dao.StaffDAO;
import lk.ijse.crop_monitoring_backend.dto.CropDetailsDTO;
import lk.ijse.crop_monitoring_backend.entity.*;
import lk.ijse.crop_monitoring_backend.exception.DataPersistFailedException;
import lk.ijse.crop_monitoring_backend.exception.NotFoundException;
import lk.ijse.crop_monitoring_backend.service.LogService;
import lk.ijse.crop_monitoring_backend.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LogServiceImpl implements LogService {

    @Autowired
    private LogDAO logDAO;

    @Autowired
    private StaffDAO staffDAO;

    @Autowired
    private FieldDAO fieldDAO;

    @Autowired
    private CropDAO cropDAO;

    @Autowired
    private Mapping mapping;

    @Override
    public void saveLog(CropDetailsDTO cropDetailsDTO) {
        logDAO.save(mapping.convertToCropDetailsEnttiy(cropDetailsDTO));
    }

    @Override
    public void updateLog(CropDetailsDTO cropDetailsDTO) {
        Optional<CropDetailsEnttiy> tmpEntity = logDAO.findById(cropDetailsDTO.getLogCode());
        if(!tmpEntity.isPresent()){
            throw new NotFoundException("Log Not Found");
        }else {
            StaffEntity staff = staffDAO.findById(cropDetailsDTO.getStaffId())
                    .orElseThrow(() -> new DataPersistFailedException("Staff not found"));
            FieldEntity field = fieldDAO.findById(cropDetailsDTO.getFieldCode())
                    .orElseThrow(() -> new DataPersistFailedException("Field not found"));
            CropEntity crop = cropDAO.findById(cropDetailsDTO.getCropCode())
                    .orElseThrow(() -> new DataPersistFailedException("Crop not found"));

            tmpEntity.get().setLogDate(cropDetailsDTO.getLogDate());
            tmpEntity.get().setLogDetails(cropDetailsDTO.getLogDetails());
            tmpEntity.get().setCropStatus(cropDetailsDTO.getCropStatus());
            tmpEntity.get().setObservedImage(cropDetailsDTO.getObservedImage());
            tmpEntity.get().setStaff(staff);
            tmpEntity.get().setField(field);
            tmpEntity.get().setCrop(crop);
        }
    }

    @Override
    public List<CropDetailsDTO> getAllLogs() {
        return mapping.convertToLogDTOList(logDAO.findAll());
    }

    @Override
    public CropDetailsResponse getSelectedLog(int logCode) {
        if (logDAO.existsById(logCode)) {
            return mapping.convertToCropDetailsDTO(logDAO.getReferenceById(logCode));
        }else{
            throw new NotFoundException("Log not found");
        }
    }

    @Override
    public void deleteLog(int logCode) {
        Optional<CropDetailsEnttiy> findId = logDAO.findById(logCode);
        if(!findId.isPresent()){
            throw new NotFoundException("Log not found");
        }else {
            logDAO.deleteById(logCode);
        }
    }
}
