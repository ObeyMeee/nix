package ua.com.andromeda.model.cars;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import ua.com.andromeda.model.Detail;
import ua.com.andromeda.model.Invoice;
import ua.com.andromeda.model.Manufacturer;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public abstract class Vehicle {
    @Id
    @Column
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    protected String id;

    @Column
    protected String model;

    @Column
    protected BigDecimal price;

    @Column
    @Enumerated(EnumType.STRING)
    protected Manufacturer manufacturer;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "invoice_id")
    @ToString.Exclude
    protected Invoice invoice;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    protected List<Detail> details;
}