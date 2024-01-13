package cz.daniellinda.jenda.Data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "Log")
public class ValueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 4, nullable = false)
    private String value;

    @Column(length = 30, nullable = false)
    private String ipAddressRemote;

    @Column(length = 30, nullable = false)
    private String ipAddressLocal;

    @Column(nullable = false)
    private LocalDateTime time;
}
