package ru.miaat.Ecommerce.Entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;
import ru.miaat.Ecommerce.Enum.UserRole;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @Column(unique = true)
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @Column(name="phone_number")
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    private UserRole role;

    @OneToMany(mappedBy="user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderItem> orderItemList;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Address address;

    @Column(name="created_at")
    private final LocalDateTime createdAt = LocalDateTime.now();

}
