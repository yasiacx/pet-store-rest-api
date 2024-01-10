package pet.store.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pet.store.controller.model.PetStoreData;
import pet.store.dao.PetStoreDao;
import pet.store.entity.PetStore;

@Service
public class PetStoreService {

	@Autowired 
	private PetStoreDao petStoreDao;

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
}
