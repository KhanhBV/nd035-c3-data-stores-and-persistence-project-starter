package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = this.petService.saveNewPet(petDTO.mappingDataToEntity());
        return PetDTO.mappingDataFromEntity(pet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        boolean isFind = this.petService.getPetById(petId).isPresent();

        if (isFind) {
            return PetDTO.mappingDataFromEntity(this.petService.getPetById(petId).get());
        }

        throw new EntityNotFoundException("Can not find pet!!");
    }

    @GetMapping
    public List<PetDTO> getPets(){
        return this.petService.getAllPet().stream().map(PetDTO::mappingDataFromEntity).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {

        return this.petService.getPetsByCustomer(ownerId).stream().map(PetDTO::mappingDataFromEntity).collect(Collectors.toList());
    }
}
