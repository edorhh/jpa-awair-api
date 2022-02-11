package kr.edor.awair.scheduler;

import kr.edor.awair.service.AirDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AirDataCollector {

    @Autowired
    AirDataService airDataService;

    @Scheduled(fixedDelayString = "#{new Integer(60000) * ${interval.local.api}}")
    public void collect() {
        System.out.println("call-api: "+ new Date(System.currentTimeMillis()));
        airDataService.syncAirData();
    }
}
