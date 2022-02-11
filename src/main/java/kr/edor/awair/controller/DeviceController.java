package kr.edor.awair.controller;

import kr.edor.awair.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class DeviceController {

    @Autowired
    DeviceService deviceService;

    @PostMapping("/device-regist")
    public ResponseEntity registDevice(@RequestParam Map params) {
        String uuid = String.valueOf(params.get("uuid"));
        String ip = String.valueOf(params.get("ip"));
        String location = String.valueOf(params.get("location"));

        boolean isSuccess = deviceService.registDevice(location, uuid, ip);
        String result = isSuccess ? "result success" : "fail";

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/device-info")
    public ResponseEntity getDeviceInfo() {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", deviceService.getDeviceInfo());
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
}
