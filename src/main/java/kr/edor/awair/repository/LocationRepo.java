package kr.edor.awair.repository;

import kr.edor.awair.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepo extends JpaRepository<Location, Integer> {
    public Location findByName(String name);
}
