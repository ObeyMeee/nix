package ua.com.andromeda.homework10.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class Invoice {
    private String id;
    private List<Vehicle> vehicles;
    private LocalDateTime created;
}
