package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.schedule.Schedule;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @CollectionTable
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = EmployeeSkill.class)
    private Collection<EmployeeSkill> skills;

    @CollectionTable
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = DayOfWeek.class)
    private Collection<DayOfWeek> daysAvailable;

    @ManyToMany(mappedBy = "employees")
    private Set<Schedule> schedules;

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

    public Set<EmployeeSkill> getSkills() {
        return this.skills == null ?
                Collections.emptySet() :
                new HashSet<>(skills);
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public Set<DayOfWeek> getDaysAvailable() {
        return this.daysAvailable == null ?
                Collections.emptySet() :
                new HashSet<>(daysAvailable);
    }

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }

    public Set<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedule(Set<Schedule> schedules) {
        this.schedules = schedules;
    }
}
