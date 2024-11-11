package lk.ijse.crop_monitoring_backend.dao;

import lk.ijse.crop_monitoring_backend.entity.CropDetailsEnttiy;
import lk.ijse.crop_monitoring_backend.entity.EquipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogDAO extends JpaRepository<CropDetailsEnttiy, Integer>{

}
