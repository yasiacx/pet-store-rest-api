package pet.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;  

import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreData;
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
	    
	    @PutMapping("/{storeId}")
	    @ResponseStatus(HttpStatus.OK)
	    public PetStoreData updatePetStore( @PathVariable Long storeId, @RequestBody PetStoreData petStoreData) {
	    	
	    	
	        log.info("Received PUT request for /pet_store with the ID: {}", storeId );
	        
	        petStoreData.setPetStoreId(storeId); 
	        
			return petStoreService.savePetStore(petStoreData);
	    	
	    }
}
