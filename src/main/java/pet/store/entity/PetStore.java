package pet.store.entity;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class PetStore {


	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="pet_store_id")
	private Long petStoreId;
	
	@Column(name ="pet_store_name")
	private String petStoreName;
	
	
	@Column(name ="pet_store_address")
	private String petStoreAddress;
	
	
	@Column(name ="pet_store_city")
	private String petStoreCity;
	
	@Column(name ="pet_store_state")
	private String petStoreState;
	
	@Column(name ="pet_store_zip")
	private String petStoreZip;
	
	
	@Column(name ="pet_store_phone")
	private String petStorePhone;
	
	
	@ManyToMany(cascade = CascadeType.ALL )
	@JoinTable(name = "pet_store_customer", joinColumns = @JoinColumn(name = "pet_store_id"), inverseJoinColumns = @JoinColumn(name = "customer_id"))
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Set<Customer> customers;
	
	@OneToMany(mappedBy = "petStore", cascade = CascadeType.ALL, orphanRemoval = true)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private	Set<Employee> employees;
}
