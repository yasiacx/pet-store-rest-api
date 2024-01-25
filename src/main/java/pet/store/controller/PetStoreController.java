package pet.store.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreCustomer;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreEmployee;
import pet.store.entity.PetStore;
import pet.store.service.*;
 

@RestController
@RequestMapping("/pet_store")
@Slf4j
public class PetStoreController {

	@Autowired
	private PetStoreService petStoreService;
	
    
    
	    @PostMapping
	    @ResponseStatus(HttpStatus.CREATED)
	    public PetStoreData createPetStore(@RequestBody PetStoreData petStoreData) {
	        log.info("Received POST request for /pet_store: {}", petStoreData);
	        return petStoreService.savePetStore(petStoreData);
	    }
	    
	    @PutMapping("/{petStoreId}")
	    @ResponseStatus(HttpStatus.OK)
	    public PetStoreData updatePetStore( @PathVariable Long petStoreId, @RequestBody PetStoreData petStoreData) {
	    	
	    	
	        log.info("Received PUT request for /pet_store with the ID: {}", petStoreId );
	        
	        petStoreData.setPetStoreId(petStoreId); 
	        
			return petStoreService.savePetStore(petStoreData);
	    	
	    }
	    
	    @PostMapping("/{petStoreId}/employee")
	    @ResponseStatus(HttpStatus.CREATED)
	    public PetStoreEmployee addEmployeeToPetStore(
	            @PathVariable Long petStoreId,
	            @RequestBody PetStoreEmployee employee) {

	        // Log the request
	        log.info("Adding employee to pet store with ID {}: {}", petStoreId, employee);

 	       
	        PetStoreEmployee savedEmployee = petStoreService.saveEmployee(petStoreId, employee);

	        return savedEmployee;
	    }
	    
	    
	    @PostMapping("/{petStoreId}/customer")
	    @ResponseStatus(HttpStatus.CREATED)
	    public PetStoreCustomer addCustomerToPetStore(
	            @PathVariable Long petStoreId,
	            @RequestBody PetStoreCustomer customer) {

 	        log.info("Adding customer to pet store with ID {}: {}", petStoreId, customer);

	        PetStoreCustomer savedCustomer = petStoreService.saveCustomer(petStoreId, customer);

	        return savedCustomer;
	    }
	    
	    
	    @GetMapping("")
	    @ResponseStatus(HttpStatus.OK)
	    public List<PetStoreData> getAllpetStores() {
	    	
			return petStoreService.retrieveAllPetStores();
	    	
	    }
	    
	    @GetMapping("/{petStoreId}")
	    public ResponseEntity<PetStoreData> getPetStoreById(@PathVariable Long petStoreId) {
	        log.info("Received GET request for /pet_store with the ID: {}", petStoreId);

	        PetStoreData petStoreData = petStoreService.getPetStoreById(petStoreId);

	        return ResponseEntity.ok(petStoreData);
	    }
	    
	    @DeleteMapping("/{petStoreId}")
	    public ResponseEntity<Map<String, String>> deletePetStoreById(@PathVariable Long petStoreId) {
	    	
	        log.info("Received DELETE request for /pet_store with the ID: {}", petStoreId);

	        petStoreService.deletePetStoreById(petStoreId);

	        Map<String, String> response = new HashMap<>();
	        response.put("message", "Deletion successful");

	        return ResponseEntity.ok(response);
	    }
	    
	  
	    
	    
	    
}
