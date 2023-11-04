package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;

/**
 * Represents the form that pet request and response data takes. Does not map
 * to the database directly.
 */
public class PetDTO {
    private long id;
    private PetType type;
    private String name;
    private long ownerId;
    private LocalDate birthDate;
    private String notes;

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public static PetDTO mappingDataFromEntity(Pet entity) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(entity, petDTO);
        if (entity.getOwner() == null) {
            petDTO.setOwnerId(0);
        } else {
            petDTO.setOwnerId(entity.getOwner().getId());
        }
        return petDTO;
    }

    public Pet mappingDataToEntity() {
        Pet newPet = new Pet();
        BeanUtils.copyProperties(this, newPet);
        if (this.ownerId != 0) {
            Customer newCustomer = new Customer();
            newCustomer.setId(this.ownerId);
            newPet.setOwner(newCustomer);
        }
        return newPet;
    }
}
