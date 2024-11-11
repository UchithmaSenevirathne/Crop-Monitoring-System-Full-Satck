package lk.ijse.crop_monitoring_backend.dao;

import lk.ijse.crop_monitoring_backend.entity.CropEntity;
import lk.ijse.crop_monitoring_backend.entity.FieldEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CropDAO extends JpaRepository<CropEntity, Integer> {
}
