package kr.edor.awair.service;

import kr.edor.awair.domain.Device;
import kr.edor.awair.repository.DeviceRepo;
import kr.edor.awair.repository.LocationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Service
public class DeviceService {

    @Autowired
    DeviceRepo deviceRepo;

    @Autowired
    LocationRepo locationRepo;

    // awair장비 등록
    public boolean registDevice(String locationStr, String uuidStr, String ipStr) {
//        Location location = locationRepo.findByName(locationStr);
//        if (location == null) {
//            location = new Location(locationStr);
//            locationRepo.save(location);
//        }

//        // 장비의 uuid(ex. awair-omni_13385)와 위치로 조회
//        // select * from device where uuid = 'awair-omni_13385' and device.location.name = '도서관 1층';
//        List<Device> deviceList = deviceRepo.findAll(DeviceSpec.whereUuid(uuidStr).and(DeviceSpec.whereLocation(location)));
//        Device device = null;
//        if (deviceList.size() <= 0) device = new Device();
//        else device = deviceList.get(0);

        Device device = deviceRepo.findByUuidAndAndLocation(uuidStr, locationStr) ;
        if (device == null) device = new Device();
        device.setLocation(locationStr);
        device.setUuid(uuidStr);
        device.setIp(ipStr);

        deviceRepo.save(device);

        return true;
    }

    public List<Device> getDeviceInfo() {
        // awair 장비 데이터 최신화
        syncDeviceInfo();
        // return 동기화된 장비 리스트
        return deviceRepo.findAll();
    }

    // awair 장비 데이터 동기화
    public void syncDeviceInfo() {
        String line, deviceApiUrl = "";
        HttpURLConnection conn = null;
        BufferedReader br = null;
        StringBuffer buff = new StringBuffer();
        try {
            for (Device device : deviceRepo.findAll()) {
                deviceApiUrl = "http://" + device.getIp() + "/settings/config/data";
                URL url = new URL(deviceApiUrl);
                conn = (HttpURLConnection) url.openConnection();
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) buff.append(line);
                JSONObject dataObj = new JSONObject(buff.toString());

                device.setUuid(dataObj.getString("device_uuid"));
                device.setIp(dataObj.getString("ip"));
                device.setGateway(dataObj.getString("gateway"));
                device.setVersion(dataObj.getString("fw_version"));
                device.setDisplay(dataObj.getString("display"));
                device.setLedMode(((JSONObject)dataObj.get("led")).getString("mode"));
                device.setLedBrightness(((JSONObject)dataObj.get("led")).getInt("brightness"));
                device.setBattery(((JSONObject)dataObj.get("power-status")).getInt("battery"));
                device.setPlugged(((JSONObject)dataObj.get("power-status")).getBoolean("plugged"));
                deviceRepo.save(device);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
                if (conn != null) conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
