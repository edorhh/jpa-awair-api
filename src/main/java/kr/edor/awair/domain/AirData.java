package kr.edor.awair.domain;

import javax.persistence.*;
import java.sql.Date;

@Entity
@SequenceGenerator(name = "seq.awair-air-data", sequenceName = "SEQ_AIR_DATA", allocationSize = 1)
public class AirData {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq.awair-air-data")
    private int id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Device device;

    private Date timestamp;

    @Column(nullable = true)
    private int score; // 총점

    @Column(nullable = true, scale = 2)
    private Double temp; // 온도

    @Column(nullable = true, scale = 2)
    private Double humid; // 습도

    @Column(nullable = true)
    private int co2; // 이산화탄소

    @Column(nullable = true)
    private int voc;

    @Column(nullable = true)
    private int pm25;

    @Column(nullable = true, scale = 2)
    private Double lux; // 조도

    @Column(nullable = true, scale = 2)
    private Double splA;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Device getDevice() {
        return device;
    }
    public void setDevice(Device device) {
        this.device = device;
    }
    public Date getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public double getTemp() {
        return temp;
    }
    public void setTemp(double temp) {
        this.temp = temp;
    }
    public double getHumid() {
        return humid;
    }
    public void setHumid(double humid) {
        this.humid = humid;
    }
    public int getCo2() {
        return co2;
    }
    public void setCo2(int co2) {
        this.co2 = co2;
    }
    public int getVoc() {
        return voc;
    }
    public void setVoc(int voc) {
        this.voc = voc;
    }
    public int getPm25() {
        return pm25;
    }
    public void setPm25(int pm25) {
        this.pm25 = pm25;
    }
    public double getLux() {
        return lux;
    }
    public void setLux(double lux) {
        this.lux = lux;
    }
    public double getSplA() {
        return splA;
    }
    public void setSplA(double splA) {
        this.splA = splA;
    }
}