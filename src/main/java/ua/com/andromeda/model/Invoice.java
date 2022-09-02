package ua.com.andromeda.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import ua.com.andromeda.model.cars.Vehicle;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
public class Invoice {
    @Id
    @Column
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<Vehicle> vehicles;
    @Column
    @CreationTimestamp
    private LocalDateTime created;

    public void add(Vehicle vehicle) {
        if (vehicles == null) {
            vehicles = new HashSet<>();
        }
        vehicle.setInvoice(this);
        vehicles.add(vehicle);
    }
}
