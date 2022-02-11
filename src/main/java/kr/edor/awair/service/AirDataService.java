package kr.edor.awair.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.edor.awair.domain.AirData;
import kr.edor.awair.repository.AirDataRepo;
import kr.edor.awair.repository.DeviceRepo;
import kr.edor.awair.specification.AirDataSpec;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AirDataService {
    @Autowired
    DeviceRepo deviceRepo;

    @Autowired
    AirDataRepo airDataRepo;

    public void syncAirData() {
        deviceRepo.findAll().forEach(device -> {
            String url = "http://" + device.getIp() + "/air-data/latest";
            Map<String, Object> map = getLocalApiAirData(url);
            if (map.isEmpty()) return;
            AirData airData = new AirData();
            airData.setDevice(device);
//            airData.setTimestamp();
            airData.setScore((Integer)map.get("score"));
            airData.setTemp((Double)map.get("temp"));
            airData.setHumid((Double)map.get("humid"));
            airData.setCo2((Integer)map.get("co2"));
            airData.setVoc((Integer)map.get("voc"));
            airData.setPm25((Integer)map.get("pm25"));
            airData.setLux((Double)map.get("lux"));
            airData.setSplA((Double)map.get("spl_a"));
            airDataRepo.save(airData);
        });
    }

    public Map<String, Object> getLocalApiAirData(String url) {
        Map<String, Object> map = new HashMap<>();
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try {
            client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);
            response = client.execute(request);
            if (response.getStatusLine().getStatusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                map = mapper.readValue(new BufferedReader(new InputStreamReader(response.getEntity().getContent())), Map.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) response.close();
                if (client != null) client.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return map;
    }

    public List getLatestAirData() {
        return airDataRepo.findAll(
                Specification
                        .where(AirDataSpec.leftJoinQuery())
                        .and(AirDataSpec.whereLatestDataQuery())
        );
    }

    public List getAirDataByDevice(String condition, Object obj) {
        return airDataRepo.findAll(
                AirDataSpec
                        .leftJoinQuery()
                        .and(AirDataSpec.whereConditionQuery(condition, obj)));
    }
}
