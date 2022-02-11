package kr.edor.awair.specification;

import kr.edor.awair.domain.AirData;
import kr.edor.awair.domain.Device;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.*;
import java.util.Collection;

public class AirDataSpec {

    public static Specification<AirData> leftJoinQuery() {
        return (root, query, builder) -> {
//            Root device = query.from(Device.class);
//            Join<AirData, Device> device = root.join("device", JoinType.LEFT);
//            return query.multiselect(root, device).getRestriction();
            root.join("device", JoinType.LEFT);
            return null;
        };
    }


    // TODO 수정필요
    public static Specification<AirData> whereConditionQuery(String condition, Object obj) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if ("location".equals(condition)) {
                Subquery subQuery = query.subquery(Integer.class);
                Root<Device> subRoot = subQuery.from(Device.class);
                subQuery.select(subRoot.get("id"));
                predicate = builder.in(root.get("device")).value(subQuery);
            } else if (obj instanceof Integer) {
                // TODO 단일
                predicate = builder.equal(root.get(condition), obj);
            } else if (obj instanceof Collection || obj instanceof Object[]) {
                // TODO 리스트
                predicate = builder.in(root.get(condition)).value(obj);
            } else {
                // TODO ETC
                predicate = null;
            }
            return predicate;
        };
    }

    public static Specification<AirData> whereLatestDataQuery() {
        return (root, query, builder) -> {
            Subquery<Integer> subQuery = query.subquery(Integer.class);
            Root<AirData> subRoot = subQuery.from(AirData.class);
            subQuery.select(builder.max(subRoot.get("id")));
            subQuery.groupBy(subRoot.get("device"));
            return builder.in(root.get("id")).value(subQuery);
        };
    }
}
