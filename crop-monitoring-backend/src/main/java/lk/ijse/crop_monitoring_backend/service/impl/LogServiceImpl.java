package lk.ijse.crop_monitoring_backend.service.impl;

import lk.ijse.crop_monitoring_backend.dao.FieldDAO;
import lk.ijse.crop_monitoring_backend.dao.LogDAO;
import lk.ijse.crop_monitoring_backend.service.LogService;
import lk.ijse.crop_monitoring_backend.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LogServiceImpl implements LogService {

    @Autowired
    private LogDAO logDAO;

    @Autowired
    private Mapping mapping;
}
