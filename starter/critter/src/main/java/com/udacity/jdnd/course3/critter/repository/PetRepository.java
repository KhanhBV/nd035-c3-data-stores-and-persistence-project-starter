package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.pet.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface PetRepository extends JpaRepository<Pet, Long> {
    Set<Pet> findPetsByOwnerId(long ownerId);
}
