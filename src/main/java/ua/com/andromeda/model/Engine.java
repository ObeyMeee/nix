package ua.com.andromeda.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Engine {
    @Id
    @Column
    private String id;
    @Column
    private String brand;
    @Column
    private int volume;

    public Engine() {
        id = UUID.randomUUID().toString();
    }
}
