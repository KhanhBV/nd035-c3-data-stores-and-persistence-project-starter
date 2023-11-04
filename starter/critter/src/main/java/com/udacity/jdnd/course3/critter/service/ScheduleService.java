package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final PetRepository petRepository;
    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, PetRepository petRepository, EmployeeRepository employeeRepository, CustomerRepository customerRepository) {
        this.scheduleRepository = scheduleRepository;
        this.petRepository = petRepository;
        this.employeeRepository = employeeRepository;
        this.customerRepository = customerRepository;
    }

    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) {
        Schedule sc = scheduleDTO.mappingDataToEntity();
        Schedule schedule = this.scheduleRepository.save(sc);
        return ScheduleDTO.mappingDataFromEntity(schedule);
    }

    public List<ScheduleDTO> getAllSchedules() {
        return this.scheduleRepository.findAll().stream().map(ScheduleDTO::mappingDataFromEntity).collect(Collectors.toList());
    }

    public List<ScheduleDTO> getSchedulesForPet(long petId) {
        List<Schedule> schedules = this.petRepository
                .findById(petId)
                .map(p -> this.scheduleRepository.findAllByPetId(p.getId()))
                .orElseThrow(() -> new EntityNotFoundException(String.format("A pet with id '%d' not found", petId)));

        return schedules.stream().map(ScheduleDTO::mappingDataFromEntity).collect(Collectors.toList());
    }

    public List<ScheduleDTO> getSchedulesForEmployee(long employeeId) {
        List<Schedule> schedules = this.employeeRepository
                .findById(employeeId)
                .map(e -> this.scheduleRepository.findAllByEmployeeId(e.getId()))
                .orElseThrow(() -> new EntityNotFoundException(String.format("An employee with id '%d' not found", employeeId)));

        return schedules.stream().map(ScheduleDTO::mappingDataFromEntity).collect(Collectors.toList());
    }

    public List<ScheduleDTO> getSchedulesForCustomer(long customerId) {
        List<Schedule> schedules = this.customerRepository
                .findById(customerId)
                .map(c -> this.scheduleRepository.findAllByCustomerId(c.getId()))
                .orElseThrow(() -> new EntityNotFoundException(String.format("An employee with id '%d' not found", customerId)));

        return schedules.stream().map(ScheduleDTO::mappingDataFromEntity).collect(Collectors.toList());
    }
}
