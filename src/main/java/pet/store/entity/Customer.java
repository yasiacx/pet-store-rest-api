package pet.store.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "customer_first_name")
    private String customerFirstName;

    @Column(name = "customer_last_name")
    private String customerLastName;

    @Column(name = "customer_email")
    private String customerEmail;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "customer_pet_store",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "pet_store_id")
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<PetStore> petStores;
}
