package pet.store.service;

 
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import pet.store.controller.model.PetStoreCustomer;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreEmployee;
import pet.store.dao.EmployeeDao;
import pet.store.dao.PetStoreDao;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

@Service
public class PetStoreService {

	@Autowired 
	private PetStoreDao petStoreDao;
	
	@Autowired
	private EmployeeDao employeeDao;
	
	
	 
	
	@Transactional
	public PetStoreData savePetStore(PetStoreData petStoreData) {
		
 

 	        PetStore petStore = findOrCreatePetStore(petStoreData.getPetStoreId());
 	        
	        copyPetStoreFields(petStore , petStoreData);
	        
	        PetStore savedPetStore = petStoreDao.save(petStore);
	        
	        return new PetStoreData(savedPetStore);
	    }

	    private PetStore findOrCreatePetStore(Long petStoreId) {
	    	
	        if (petStoreId != null) {
	        	
	            return findPetStoreById(petStoreId);
	            
	        } else {
	        	
	            return new PetStore();
	        }
	    }

	    private PetStore findPetStoreById(Long petStoreId) {
	    	
	        Optional<PetStore> optionalPetStore = petStoreDao.findById(petStoreId);
 	        return optionalPetStore.orElseThrow(() -> new NoSuchElementException("No such pet Store found with the ID: " + petStoreId));
	        
	    }
	    
	private void copyPetStoreFields(PetStore petStore, PetStoreData petStoreData) {
		
		petStore.setPetStoreId		(	petStoreData.getPetStoreId() 		);
		petStore.setPetStoreName	( 	petStoreData.getPetStoreName() 		);
		petStore.setPetStorePhone	( 	petStoreData.getPetStorePhone() 	);
		petStore.setPetStoreCity	(   petStoreData.getPetStoreCity() 		);
		petStore.setPetStoreState	( 	petStoreData.getPetStoreState() 	);
		petStore.setPetStoreAddress	(   petStoreData.getPetStoreAddress() 	);
		petStore.setPetStoreZip		(   petStoreData.getPetStoreZip() 		);
		
	 
		
	}

	@Transactional(readOnly = false)
	public PetStoreEmployee saveEmployee(Long petStoreId, PetStoreEmployee petStoreEmployee) {
		
		PetStore petStore = findPetStoreById(petStoreId);
		Long employeeID = petStoreEmployee.getEmployeeId();
		
		Employee employee = findOrCreateEmployee(petStoreId,employeeID);
		
		copyEmployeeFields(employee, petStoreEmployee);
		
		// add the pet store into the employee
		
		 employee.setPetStore(petStore);
		 
		 // adding employee into the list of employees in the pet store
		 
		 Set<Employee> employees = petStore.getEmployees();
         employees.add(employee);        
		 petStore.setEmployees(employees);
		 
 		 
		 Employee savedEmployee = employeeDao.save(employee);
	        
	     return new PetStoreEmployee(savedEmployee);
 	}

	private Employee findOrCreateEmployee(Long petStoreId , Long employeeId) {
		
		if(employeeId == null) {
			return new Employee();
		}
		if(petStoreId == null) {  
			return new Employee();

		} else {
			return findEmployeeById(petStoreId, employeeId);
		}
 		
		
	}

	private Employee findEmployeeById(Long petStoreId, Long employeeId) {

  	        Employee employee = employeeDao.findById(employeeId)
 	    		   .orElseThrow(() -> new NoSuchElementException("Employee with ID " + employeeId + " not found"));

 	       if(employee.getPetStore().getPetStoreId() == petStoreId) {
 	    	   
 	    	   return employee;
 	       }else {
 	    	   throw new IllegalArgumentException("No such employee exist in this pet Store");
 	       }
	    }		
	
	
	private void copyEmployeeFields(Employee employee, PetStoreEmployee petStoreEmployee) {


		employee.setEmployeeId(			petStoreEmployee.getEmployeeId()		);
		employee.setEmployeeFirstName(	petStoreEmployee.getEmployeeFirstName()	);
		employee.setEmployeeLastName(	petStoreEmployee.getEmployeeLastName()	);
		
		employee.setEmployeeJobTitle( 	petStoreEmployee.getEmployeeJobTitle()	);
		employee.setEmployeePhone(		petStoreEmployee.getEmployeePhone()		 ); 

	}

	
	
	
	  @Transactional()
	    public PetStoreCustomer saveCustomer(Long petStoreId, PetStoreCustomer petStoreCustomer) {
		  
		  PetStore petStore = petStoreDao.findById(petStoreId)
		            .orElseThrow(() -> new NoSuchElementException("PetStore not found with ID: " + petStoreId));
	        

	        Long customerId = petStoreCustomer.getCustomerId();

	        Customer customer = findOrCreateCustomer(petStoreId, customerId);

	        
	        copyCustomerFields(customer, petStoreCustomer);

	        
	        
	        // Add the customer to the pet store and vice versa
	        petStore.getCustomers().add(customer);
	        
	        if (customer.getPetStores() == null) {
	            customer.setPetStores(new HashSet<>());
	            customer.getPetStores().add(petStore);
	        }
	        
	        Set<Customer> customers = petStore.getCustomers();

	        customers.add(customer);
	        petStore.setCustomers(customers);

	   
	        return new PetStoreCustomer(customer);

	        
	        
 	    }

	    private Customer findOrCreateCustomer(Long petStoreId, Long customerId) {
	    	
	        if (customerId == null) {
 	            return new Customer();
	        }
	        if (petStoreId == null) {
	            return new Customer();
	        } else {
	            return findCustomerById(petStoreId, customerId);
	        }
	    }

	    private Customer findCustomerById(Long petStoreId, Long customerId) {
	        
	    	List<PetStore> petStores = petStoreDao.findAll();
	    	
 	    	

	    	for( PetStore petStore : petStores) {
	    		
		    	if(petStore.getCustomers() != null) {
		    		
			    	for( Customer customer : petStore.getCustomers() ) {
			    		
			    		if(customer.getCustomerId() == customerId) {
 			
			    			return customer;
			    		}
			    		
			    	}
		    	}

	    	}
	    	
	    	throw new IllegalArgumentException("Customer with ID " + customerId + " does not have PetStore with ID " + petStoreId);
	    

	      

 	    }

	    private void copyCustomerFields(Customer customer, PetStoreCustomer petStoreCustomer) {

	        customer.setCustomerId(petStoreCustomer.getCustomerId());
	        customer.setCustomerFirstName(petStoreCustomer.getCustomerFirstName());
	        customer.setCustomerLastName(petStoreCustomer.getCustomerLastName());
	        customer.setCustomerEmail(petStoreCustomer.getCustomerEmail());
	        
 
	    }

	    @Transactional(readOnly = true)
	    public List<PetStoreData> retrieveAllPetStores() {
	    	
	    	List<PetStore> AllpetStores = petStoreDao.findAll();
	    	
	    	
			return convertPetStoresIntoDto(AllpetStores);
		}	

		private List<PetStoreData> convertPetStoresIntoDto(List<PetStore> allpetStores) {
			
			List<PetStoreData> result = new LinkedList<>();
			
			for ( PetStore petStore : allpetStores) {
				
				PetStoreData psd = new PetStoreData(petStore);
				 
				
				psd.getCustomers().clear();
				psd.getEmployees().clear();
				
				 

				result.add(psd);
			}
			return result;
			
		}

		 @Transactional(readOnly = true)
		    public PetStoreData getPetStoreById(Long petStoreId) {
		        PetStore petStore = findPetStoreById(petStoreId);
		        return new PetStoreData(petStore);  
		    }

		 
		 @Transactional
		    public void deletePetStoreById(Long petStoreId) {
		        PetStore petStore = findPetStoreById(petStoreId);
		        petStoreDao.delete(petStore);
		    }


	
	
}

