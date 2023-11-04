package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        return CustomerDTO.mappingDataFromEntity(this.userService.createUser(customerDTO.mappingDataToEntity()));
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        return this.userService.getAllUsers().stream().map(CustomerDTO::mappingDataFromEntity).collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        return CustomerDTO.mappingDataFromEntity(this.userService.getUserByPetId(petId));
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return EmployeeDTO.mappingDataFromEntity(this.userService.saveEmployee(employeeDTO.mappingDataToEntity()));
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return EmployeeDTO.mappingDataFromEntity(this.userService.getEmployeeById(employeeId));
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        this.userService.updateEmployee(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        return this.userService.getEmployeesBySkills(employeeDTO.getSkills(), employeeDTO.getDate()).stream().map(EmployeeDTO::mappingDataFromEntity).collect(Collectors.toList());
    }

}
