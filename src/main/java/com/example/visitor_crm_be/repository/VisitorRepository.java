package com.example.visitor_crm_be.repository;

import com.example.visitor_crm_be.model.Vehicle;
import com.example.visitor_crm_be.model.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
public interface VisitorRepository extends JpaRepository<Visitor, Long> {
    Optional<Visitor> findVisitorById(Long id);
    @Query("SELECT v FROM Visitor v WHERE v.departureDateTime BETWEEN :start AND :end")
    List<Visitor> findByDepartureDateTimeRange(@Param("start") OffsetDateTime start,
                                               @Param("end") OffsetDateTime end);
    List<Visitor> findByHotelId(Long hotelId);

}
