package kr.edor.awair.repository;

import kr.edor.awair.domain.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DeviceRepo extends JpaRepository<Device, Integer>, JpaSpecificationExecutor<Device> {
    Device findByUuidAndAndLocation(String uuid, String location);
}
