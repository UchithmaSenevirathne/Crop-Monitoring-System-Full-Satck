package lk.ijse.crop_monitoring_backend.dao;

import lk.ijse.crop_monitoring_backend.entity.EquipmentEntity;
import lk.ijse.crop_monitoring_backend.entity.FieldEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentDAO extends JpaRepository<EquipmentEntity, Integer>{

}
