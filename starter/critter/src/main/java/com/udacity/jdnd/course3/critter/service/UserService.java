package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.assertj.core.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
public class UserService {
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final PetRepository petRepository;

    public UserService(CustomerRepository customerRepository, EmployeeRepository employeeRepository, PetRepository petRepository) {
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
        this.petRepository = petRepository;
    }

    public Customer createUser(Customer customer) {
        return this.customerRepository.save(customer);
    }

    public Employee saveEmployee(Employee employee) {
        return this.employeeRepository.save(employee);
    }

    public List<Customer> getAllUsers() {
        final List<Pet> listPet = this.petRepository.findAll();
        final List<Customer> listCustomer = this.customerRepository.findAll();
        listCustomer.forEach(customer -> customer.setPets(listPet.stream().filter(pet -> pet.getOwner().getId().equals(customer.getId())).collect(Collectors.toSet())));

        return listCustomer;
    }

    public Customer getUserByPetId(long petId) {
        Customer customer = this.customerRepository.findCustomerByPetId(petId);
        Set<Pet> listPet = this.petRepository.findPetsByOwnerId(customer.getId());
        customer.setPets(listPet);

        return customer;
    }

    public Employee getEmployeeById(long employeeId) throws EntityNotFoundException {
        boolean isEmployee = this.employeeRepository.findById(employeeId).isPresent();
        if (isEmployee) {
            return this.employeeRepository.findById(employeeId).get();
        }
        throw new EntityNotFoundException("Can not find Employee");
    }

    public List<Employee> getEmployeesBySkills(Set<EmployeeSkill> employeeSkills, LocalDate localDate) {
        String stringDate = localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH).toUpperCase();
        return
                this.employeeRepository.
                        getAllEmployeeByDaysAvail(stringDate).stream().
                        filter(employee -> employee.getSkills().containsAll(employeeSkills)).
                        collect(Collectors.toList());
    }

    public void updateEmployee(Set<DayOfWeek> daysAvailable, long employeeId) throws EntityNotFoundException {
        final Employee employee = getEmployeeById(employeeId);
        employee.setDaysAvailable(daysAvailable);
        this.employeeRepository.save(employee);
    }
}
