package com.example.visitor_crm_be.repository;

import com.example.visitor_crm_be.model.Trip;
import com.example.visitor_crm_be.model.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
public interface VisitorRepository extends JpaRepository<Visitor, Long> {
    @Query("SELECT DISTINCT v FROM Visitor v JOIN v.trips t WHERE t.datetime BETWEEN :start AND :end")
    List<Visitor> findVisitorsWithTripsBetween(@Param("start") OffsetDateTime start, @Param("end") OffsetDateTime end);

    List<Visitor> findAll();

    @Query("SELECT v FROM Visitor v LEFT JOIN FETCH v.trips")
    List<Visitor> findAllWithTrips();

    Optional<Visitor> findByFullName(String fullName);

    List<Visitor> findByHotelId(Long hotelId);

}
