package com.flightapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flightapp.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {


    // PNR lookup stays the same
    Optional<Booking> findByPnr(String pnr);

    boolean existsByPnr(String pnr);
    // match the exact Booking property names: 'booker_emailid' and 'bookingDatetime'
    List<Booking> findByBookerEmailIdOrderByBookingDateTimeDesc(String bookerEmailId);
}
