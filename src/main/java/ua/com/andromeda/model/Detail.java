package ua.com.andromeda.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ua.com.andromeda.model.cars.Vehicle;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Detail {
    @Id
    @Column
    private String id;
    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    public Detail(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }
}
