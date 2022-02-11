package kr.edor.awair.repository;

import kr.edor.awair.domain.AirData;
import kr.edor.awair.domain.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface AirDataRepo extends JpaRepository<AirData, Integer>, JpaSpecificationExecutor<AirData> {
    List<AirData> findByDevice(Device device);
}
