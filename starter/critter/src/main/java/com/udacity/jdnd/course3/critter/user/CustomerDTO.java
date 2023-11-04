package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import org.springframework.beans.BeanUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents the form that customer request and response data takes. Does not map
 * to the database directly.
 */
public class CustomerDTO {
    private long id;
    private String name;
    private String phoneNumber;
    private String notes;
    private List<Long> petIds;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Long> getPetIds() {
        return petIds;
    }

    public void setPetIds(List<Long> petIds) {
        this.petIds = petIds;
    }

    public static CustomerDTO mappingDataFromEntity(Customer entity) {
        CustomerDTO customerDTO = new CustomerDTO();
        if (entity != null) {
            BeanUtils.copyProperties(entity, customerDTO);
            if (entity.getPets() == null) {
                customerDTO.setPetIds(Collections.emptyList());
            } else {
                customerDTO.setPetIds(entity.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
            }
        }
        return customerDTO;
    }

    public Customer mappingDataToEntity() {
        Customer entity = new Customer();
        BeanUtils.copyProperties(this, entity);
        return entity;
    }
}
