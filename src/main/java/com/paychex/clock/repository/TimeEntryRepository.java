package com.paychex.clock.repository;
import java.util.List;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import com.paychex.clock.model.TimeEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@NamedQueries({
        @NamedQuery(name = "TimeEntryRepository.findByEmployeeId", query = "SELECT timeEntry FROM TimeEntry timeEntry WHERE timeEntry.employee.id = :employeeId") })
public interface TimeEntryRepository extends JpaRepository<TimeEntry, Long> {

    List<TimeEntry> findByEmployeeId(@Param("employeeId") final Long employeeId);

    Page<TimeEntry> findByEmployeeId(@Param("employeeId") final Long employeeId, final Pageable pageable);

}
