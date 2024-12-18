package lk.ijse.crop_monitoring_backend.dao;

import lk.ijse.crop_monitoring_backend.entity.FieldStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldStaffDAO extends JpaRepository<FieldStaff, Integer> {
}
