package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.schedule.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query(value = "SELECT * FROM schedule INNER JOIN schedule_pet schedule_pet ON schedule.id = schedule_pet.schedule_id WHERE schedule_pet.pet_id = :petId", nativeQuery = true)
    public List<Schedule> findAllByPetId(long petId);

    @Query(value = "SELECT * FROM schedule INNER JOIN schedule_employee ON schedule.id = schedule_employee.schedule_id WHERE schedule_employee.employee_id = :employeeId", nativeQuery = true)
    List<Schedule> findAllByEmployeeId(long employeeId);

    @Query(value =
            "SELECT * FROM schedule INNER JOIN schedule_pet schedule_pet ON schedule.id = schedule_pet.schedule_id INNER JOIN pet p ON schedule_pet.pet_id = p.id WHERE p.customer_id = :customerId",
            nativeQuery = true)
    public List<Schedule> findAllByCustomerId(long customerId);
}
