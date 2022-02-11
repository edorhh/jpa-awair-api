package kr.edor.awair.domain;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "seq.awair-device", sequenceName = "SEQ_DEVICE", allocationSize = 1)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"ip", "uuid"}))
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq.awair-device")
    private int id;

    private String uuid;

    private String ip;

    private String gateway;

    private String version;

    private String display; // 화면 모드

    private String ledMode; // led 모드

    private int ledBrightness; // led 밝기

    @Column(nullable = true)
    private int battery;

    @Column(columnDefinition = " number(1) default 0 ")
    private boolean plugged = false;

//    @ManyToOne
//    @JoinColumn(nullable = false)
//    Location location;
    private String location;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getGateway() {
        return gateway;
    }
    public void setGateway(String gateway) {
        this.gateway = gateway;
    }
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public String getDisplay() {
        return display;
    }
    public void setDisplay(String display) {
        this.display = display;
    }
    public String getLedMode() {
        return ledMode;
    }
    public void setLedMode(String ledMode) {
        this.ledMode = ledMode;
    }
    public int getLedBrightness() {
        return ledBrightness;
    }
    public void setLedBrightness(int ledBrightness) {
        this.ledBrightness = ledBrightness;
    }
    public int getBattery() {
        return battery;
    }
    public void setBattery(int battery) {
        this.battery = battery;
    }
    public boolean isPlugged() {
        return plugged;
    }
    public void setPlugged(boolean plugged) {
        this.plugged = plugged;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
}
