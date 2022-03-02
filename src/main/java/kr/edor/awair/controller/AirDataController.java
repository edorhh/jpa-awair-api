package kr.edor.awair.controller;

import kr.edor.awair.service.AirDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AirDataController {

    @Autowired
    AirDataService airDataService;

    @GetMapping("/air-data")
    public ResponseEntity getAirData() {
        /*
            select *
              from device left outer join air_data
                on air_data.device_id = device.id
             where air_data.id in (select max(id) from air_data group by device_id);
        */
        Map<String, Object> result = new HashMap<>();
        result.put("message", "success");
        result.put("list", airDataService.getLatestAirData());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/air-data/{id}")
    public ResponseEntity getAirDataByDevice(@PathVariable int id) {
        String condition = "location";
        Map<String, Object> result = new HashMap<>();
        result.put("message", "success");

        List test1 = new ArrayList<>();
        Integer[] test2 = {1, 2, 3};
        result.put("list", airDataService.getAirDataByDevice(condition, id));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
