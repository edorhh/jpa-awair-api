package kr.edor.awair.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.edor.awair.service.AirDataService;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


@RestController
public class MainController {

    @Autowired
    AirDataService airDataService;

//    @Autowired
//    AwairConfig awairConfig;

    @Value("${awair.base.url}")
    private String baseUrl;

    @Value("${awair.access.token}")
    private String accessToken;

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public ResponseEntity callGetAwair(@RequestParam(defaultValue = "false", name = "isAirData", required = false) boolean isAirData) throws IOException {
        Map<String, Object> result = new HashMap<>();
        result.put("result", "fail");
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        if (isAirData) baseUrl += "/awair-omni/13385/air-data/latest?fahrenheit=false";
        HttpGet request = new HttpGet(baseUrl);
        request.setHeader("Content-Type", "application/json");
        request.setHeader("authorization", "Bearer " + accessToken);
        CloseableHttpResponse response = httpClient.execute(request);
        if (response.getStatusLine().getStatusCode() == 200) {
            ObjectMapper mapper = new ObjectMapper();
            result = mapper.readValue(new InputStreamReader(response.getEntity().getContent()), Map.class);
            result.put("result", "success");
            httpClient.close();
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/main", method = RequestMethod.POST)
    public ResponseEntity callPostAwair() throws IOException {
        Map<String, Object> result = new HashMap<>();
        result.put("result", "fail");
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("http://localhost:8080/member");
        Map map = new HashMap();
        map.put("name", "awair_테스트");
        map.put("user_id", "awair_test");
        String param = new JSONObject().toJSONString(map);
        request.setEntity(new StringEntity(param, "UTF-8"));
        CloseableHttpResponse response = httpClient.execute(request);
        if (response.getStatusLine().getStatusCode() == 200) {
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            ObjectMapper mapper = new ObjectMapper();
            result = mapper.readValue(br.readLine() , Map.class);
            result.put("result", "success");
            httpClient.close();
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/manual")
    public void manualSyncAirData() {
        airDataService.syncAirData();
    }
}
