package com.flightapp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.flightapp.dto.request.BookingRequest;
import com.flightapp.dto.request.PassengerRequest;
import com.flightapp.dto.response.BookingResponse;
import com.flightapp.exceptions.ResourceNotFoundException;
import com.flightapp.model.Booking;
import com.flightapp.model.Flight;
import com.flightapp.model.Passenger;
import com.flightapp.repository.BookingRepository;
import com.flightapp.repository.FlightRepository;
import com.flightapp.repository.PassengerRepository;
import com.flightapp.service.BookingServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private PassengerRepository passengerRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;

    private Flight sampleFlight;

    @BeforeEach
    void setUp() {
        // sample flight
        sampleFlight = new Flight();
        sampleFlight.setFlightid(10L);
        sampleFlight.setPrice(200.0);
        sampleFlight.setTotal_seats(100);
        sampleFlight.setAvailable_seats(50);
    }

    @Test
    void createBooking_success_shouldReturnBookingResponse() {
        Long flightId = 10L;

        // Prepare booking request with two passengers
        PassengerRequest p1 = new PassengerRequest();
        p1.setName("Alice");
        p1.setAge(30);
        p1.setGender("F");
        p1.setSeat_no("1A");
        p1.setMeal_type("VEG");

        PassengerRequest p2 = new PassengerRequest();
        p2.setName("Bob");
        p2.setAge(35);
        p2.setGender("M");
        p2.setSeat_no("1B");
        p2.setMeal_type("NON_VEG");

        BookingRequest req = new BookingRequest();
        req.setBooker_emailid("booker@example.com");
        req.setPassengers(Arrays.asList(p1, p2));

        // stubbing
        when(flightRepository.findById(flightId)).thenReturn(Optional.of(sampleFlight));
        when(bookingRepository.existsByPnr(anyString())).thenReturn(false);

        Booking savedBooking = new Booking();
        savedBooking.setPnr("PNR123");
        savedBooking.setFlight(sampleFlight);
        savedBooking.setStatus("BOOKED");
        savedBooking.setBookingDateTime(LocalDateTime.now());
        savedBooking.setTotal_amount(400.0);

        when(bookingRepository.saveAndFlush(any(Booking.class))).thenReturn(savedBooking);
        // use doReturn to avoid strict-stubbing issues
        doReturn(Optional.of(savedBooking)).when(bookingRepository).findByPnr(savedBooking.getPnr());

        // passengers saved
        Passenger savedP1 = new Passenger();
        savedP1.setName("Alice");
        savedP1.setSeat_no("1A");

        Passenger savedP2 = new Passenger();
        savedP2.setName("Bob");
        savedP2.setSeat_no("1B");

        when(passengerRepository.save(any(Passenger.class)))
                .thenReturn(savedP1)
                .thenReturn(savedP2);

        // call
        BookingResponse response = bookingService.createBooking(flightId, req);

        // verify & asserts
        assertNotNull(response);
        assertEquals("PNR123", response.getPnr());
        assertEquals("BOOKED", response.getStatus());
        assertEquals(10L, response.getFlightid());
        assertEquals(2, response.getPassengers().size());
        assertEquals(400.0, response.getTotalprice());
        verify(bookingRepository, times(1)).saveAndFlush(any(Booking.class));
        verify(passengerRepository, times(2)).save(any(Passenger.class));
    }

    @Test
    void getBookingByPnr_notFound_shouldThrow() {
        // use doReturn to avoid strict-stub false positives
        doReturn(Optional.empty()).when(bookingRepository).findByPnr("NOPNR");
        assertThrows(ResourceNotFoundException.class, () -> bookingService.getBookingByPnr("NOPNR"));
    }


    @Test
    void cancelBooking_success_shouldReleaseSeatsAndReturnCancelled() {
        Booking b = new Booking();
        b.setPnr("PNR_CANCEL");
        b.setStatus("BOOKED");

        Passenger p1 = new Passenger();
        Passenger p2 = new Passenger();
        b.setPassengers(Arrays.asList(p1, p2));

        Flight f = new Flight();
        f.setFlightid(100L);
        f.setAvailable_seats(5);

        b.setFlight(f);

        doReturn(Optional.of(b)).when(bookingRepository).findByPnr("PNR_CANCEL");
        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(flightRepository.save(any(Flight.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BookingResponse resp = bookingService.cancelBooking("PNR_CANCEL");

        assertEquals("CANCELLED", resp.getStatus());
        // seats increased by 2
        assertEquals(7, f.getAvailable_seats());
        verify(flightRepository, times(1)).save(f);
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void getBookingHistoryByEmail_returnsMappedList() {
        Booking b1 = new Booking();
        b1.setPnr("HIST1");
        b1.setBookingDateTime(LocalDateTime.now());
        b1.setStatus("BOOKED");
        b1.setTotal_amount(100.0);
        b1.setFlight(sampleFlight);

        when(bookingRepository.findByBookerEmailIdOrderByBookingDateTimeDesc("me@a.com"))
                .thenReturn(Collections.singletonList(b1));

        List<com.flightapp.dto.response.BookingResponse> list = bookingService.getBookingHistoryByEmail("me@a.com");
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("HIST1", list.get(0).getPnr());
    }
}
