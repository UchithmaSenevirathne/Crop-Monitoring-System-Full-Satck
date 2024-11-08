package lk.ijse.crop_monitoring_backend.dao;

import lk.ijse.crop_monitoring_backend.entity.FieldStaff;
import lk.ijse.crop_monitoring_backend.entity.VehicleStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleStaffDAO extends JpaRepository<VehicleStaff, Integer> {
}
