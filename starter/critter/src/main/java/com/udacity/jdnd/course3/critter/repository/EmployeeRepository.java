package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.user.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query(value = "SELECT * FROM employee INNER JOIN Employee_daysAvailable ON employee.id = Employee_daysAvailable.employee_id WHERE Employee_daysAvailable.daysAvailable = :availableDate", nativeQuery = true)
    List<Employee> getAllEmployeeByDaysAvail(@Param("availableDate") String date);
}
