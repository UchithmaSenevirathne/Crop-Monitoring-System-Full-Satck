package lk.ijse.crop_monitoring_backend.dao;

import lk.ijse.crop_monitoring_backend.entity.FieldEntity;
import lk.ijse.crop_monitoring_backend.entity.StaffEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldDAO extends JpaRepository<FieldEntity, Integer>, CustomFieldDAO {

}
