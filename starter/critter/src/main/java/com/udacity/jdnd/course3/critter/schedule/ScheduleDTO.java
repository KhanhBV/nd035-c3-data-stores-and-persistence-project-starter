package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents the form that schedule request and response data takes. Does not map
 * to the database directly.
 */
public class ScheduleDTO {
    private long id;
    private List<Long> employeeIds;
    private List<Long> petIds;
    private LocalDate date;
    private Set<EmployeeSkill> activities;

    public long getId(){
        return id;
    }
    
    public void setId(long id){
        this.id = id;
    }
    
    public List<Long> getEmployeeIds() {
        return employeeIds;
    }

    public void setEmployeeIds(List<Long> employeeIds) {
        this.employeeIds = employeeIds;
    }

    public List<Long> getPetIds() {
        return petIds;
    }

    public void setPetIds(List<Long> petIds) {
        this.petIds = petIds;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<EmployeeSkill> getActivities() {
        return activities;
    }

    public void setActivities(Set<EmployeeSkill> activities) {
        this.activities = activities;
    }

    public static ScheduleDTO mappingDataFromEntity(Schedule entity) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(entity, scheduleDTO);
        if (entity.getPets() == null) {
            scheduleDTO.setPetIds(Collections.emptyList());
        } else {
            scheduleDTO.setPetIds(entity.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
        }
        if (entity.getEmployees() == null) {
            scheduleDTO.setEmployeeIds(Collections.emptyList());
        } else {
            scheduleDTO.setEmployeeIds(entity.getEmployees().stream().map(Employee::getId).collect(Collectors.toList()));
        }
        return scheduleDTO;
    }

    public Schedule mappingDataToEntity() {
        Schedule schedule = new Schedule();
        schedule.setPets(new HashSet<>());
        schedule.setEmployees(new HashSet<>());
        BeanUtils.copyProperties(this, schedule);
        this.getPetIds().stream().filter(petId -> petId != 0).forEach(petId -> {
            Pet newPet = new Pet();
            newPet.setId(petId);
            schedule.getPets().add(newPet);
        });
        this.getEmployeeIds().stream().filter(employeeId -> employeeId != 0).forEach(employeeId -> {
            Employee newEmp = new Employee();
            newEmp.setId(employeeId);
            schedule.getEmployees().add(newEmp);
        });
        return schedule;
    }
}
