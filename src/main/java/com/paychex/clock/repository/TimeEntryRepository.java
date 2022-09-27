package com.paychex.clock.repository;

import java.util.List;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import com.paychex.clock.model.TimeEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@NamedQueries({
        @NamedQuery(name = "TimeEntryRepository.findByEmployeeId", query = "SELECT timeEntry FROM TimeEntry timeEntry WHERE timeEntry.employee.id = :employeeId"),
        @NamedQuery(name="TimeEntryRepository.findTodayByEmployeeId", query="SELECT timeEntry FROM TimeEntry timeEntry WHERE timeEntry.employee.id = :employeeId and timeEntry.date >= CURRENT_DATE"),
        @NamedQuery(name="TimeEntryRepository.findDescendingByEmployeeId", query="SELECT timeEntry FROM TimeEntry timeEntry WHERE timeEntry.employee.id = :employeeId order by timeEntry.date desc")
})
public interface TimeEntryRepository extends JpaRepository<TimeEntry, Long> {

    List<TimeEntry> findDescendingByEmployeeId(@Param("employeeId") final Long employeeId);
    List<TimeEntry> findByEmployeeId(@Param("employeeId") final Long employeeId);
    TimeEntry findTodayByEmployeeId(@Param("employeeId") final Long employeeId);}
