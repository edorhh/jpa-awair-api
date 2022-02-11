package kr.edor.awair.specification;

import kr.edor.awair.domain.Device;
import kr.edor.awair.domain.Location;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;

public class DeviceSpec {

    public static Specification<Device> whereLocation(Location location) {
        return (root, query, builder) -> builder.equal(root.get("location"), location);
    }

    public static Specification<Device> whereUuid(String uuid) {
        return (root, query, builder) -> builder.equal(root.get("uuid"), uuid);
    }

    public static Specification selectCondition(String location, String uuid) {
        return (root, query, builder) -> {
            Predicate predicate1 = builder.equal(root.get("location"), location);
            Predicate predicate2 = builder.equal(root.get("uuid"), uuid);
            return builder.and(predicate1, predicate2);
        };
    }
}
