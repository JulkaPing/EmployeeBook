package model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@EqualsAndHashCode
@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Entity
@Table(name = "EMPLOYEES")
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "CITY")
    private String city;
    @Column(name = "CREATED", nullable = false)
    private LocalDateTime created;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Employee(@NonNull String name, String city) {
        this.name = name;
        this.city = city;
        this.created = LocalDateTime.now();
    }

    public enum Role {
        USER_ROLE,
        ADMIN_ROLE
    }
}
