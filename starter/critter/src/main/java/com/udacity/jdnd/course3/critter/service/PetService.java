package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Transactional
@Service
public class PetService {
    private final PetRepository repository;

    public PetService(PetRepository repository) {
        this.repository = repository;
    }

    public Pet saveNewPet(Pet pet) {
        return this.repository.save(pet);
    }

    public Set<Pet> getPetsByCustomer(long customerId) {
        return this.repository.findPetsByOwnerId(customerId);
    }
    public List<Pet> getAllPet() {
        return this.repository.findAll();
    }
    public Optional<Pet> getPetById(long petId) {
        return this.repository.findById(petId);
    }

}
