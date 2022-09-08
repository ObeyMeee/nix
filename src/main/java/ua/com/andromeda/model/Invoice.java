package ua.com.andromeda.model;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import ua.com.andromeda.model.cars.Vehicle;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
public class Invoice {
    @Id
    @Column
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @SerializedName("_id")
    private String id;
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private transient Set<Vehicle> vehicles;
    private BigDecimal totalPrice;
    @Column
    @CreationTimestamp
    private LocalDateTime created;

    public Invoice() {
        id = UUID.randomUUID().toString();
        created = LocalDateTime.now();
    }

    public void add(Vehicle vehicle) {
        if (vehicles == null) {
            vehicles = new HashSet<>();
        }
        vehicle.setInvoice(this);
        vehicles.add(vehicle);
    }
}
