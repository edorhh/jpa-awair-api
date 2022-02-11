package kr.edor.awair.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@SequenceGenerator(name = "seq.location", sequenceName = "SEQ_LOCATION", allocationSize = 1)
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq.location")
    private int id;

    private String name;

    @OneToMany(mappedBy = "location")
    private Set<Device> diveces;

    public Location() {}
    public Location(String name) {
        this.name = name;
    }
    public void add(Device device) {
        diveces.add(device);
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
