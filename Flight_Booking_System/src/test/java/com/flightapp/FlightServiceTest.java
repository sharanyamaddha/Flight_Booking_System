package com.flightapp;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.flightapp.dto.request.FlightRequest;
import com.flightapp.dto.response.FlightResponse;
import com.flightapp.model.Airline;
import com.flightapp.model.Flight;
import com.flightapp.repository.AirlineRepository;
import com.flightapp.repository.FlightRepository;
import com.flightapp.service.FlightServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

@ExtendWith(MockitoExtension.class)
class FlightServiceTest {

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private AirlineRepository airlineRepository;

    @InjectMocks
    private FlightServiceImpl flightService;

    private FlightRequest sampleRequest;

    @BeforeEach
    void setUp() {
        sampleRequest = new FlightRequest();
        sampleRequest.setName("Indigo");
        sampleRequest.setSource("BOM");
        sampleRequest.setDestination("DEL");
        sampleRequest.setTotal_seats(150);
        sampleRequest.setPrice(2500.0);
        sampleRequest.setDepartureDatetime(LocalDateTime.of(2025,1,1,10,0));
        sampleRequest.setArrivalDatetime(LocalDateTime.of(2025,1,1,12,0));
    }

    @Test
    void createFlight_existingAirline_shouldUseExistingAirline() {
        Airline existing = new Airline();
        existing.setAirlineid(5L);
        existing.setName("Indigo");

        when(airlineRepository.findByNameIgnoreCase("Indigo")).thenReturn(Optional.of(existing));

        Flight saved = new Flight();
        saved.setFlightid(100L);
        saved.setAirline(existing);
        saved.setSource("BOM");
        saved.setDestination("DEL");
        saved.setDepartureDatetime(sampleRequest.getDepartureDatetime());
        saved.setArrivalDatetime(sampleRequest.getArrivalDatetime());
        saved.setTotal_seats(150);
        saved.setAvailable_seats(150);
        saved.setPrice(2500.0);

        when(flightRepository.save(any(Flight.class))).thenReturn(saved);

        FlightResponse resp = flightService.createFlight(sampleRequest);

        assertNotNull(resp);
        assertEquals(100L, resp.getFlightid());
        assertEquals(5L, resp.getAirlineid());
        assertEquals("BOM", resp.getSource());
        assertEquals(150, resp.getAvailable_seats());
        verify(airlineRepository, times(1)).findByNameIgnoreCase("Indigo");
        // ensure we didn't attempt to save a new airline because found existing
        verify(airlineRepository, never()).save(any(Airline.class));
    }

    @Test
    void createFlight_newAirline_shouldCreateAirlineAndFlight() {
        when(airlineRepository.findByNameIgnoreCase("Indigo")).thenReturn(Optional.empty());

        Airline createdAirline = new Airline();
        createdAirline.setAirlineid(77L);
        createdAirline.setName("Indigo");

        when(airlineRepository.save(any(Airline.class))).thenReturn(createdAirline);

        Flight saved = new Flight();
        saved.setFlightid(200L);
        saved.setAirline(createdAirline);
        saved.setSource("BOM");
        saved.setDestination("DEL");
        saved.setTotal_seats(150);
        saved.setAvailable_seats(150);
        saved.setPrice(2500.0);
        saved.setDepartureDatetime(sampleRequest.getDepartureDatetime());
        saved.setArrivalDatetime(sampleRequest.getArrivalDatetime());

        when(flightRepository.save(any(Flight.class))).thenReturn(saved);

        FlightResponse resp = flightService.createFlight(sampleRequest);

        assertNotNull(resp);
        assertEquals(200L, resp.getFlightid());
        assertEquals(77L, resp.getAirlineid());
        verify(airlineRepository, times(1)).save(any(Airline.class));
    }

    @Test
    void searchFlights_withSourceAndDestinationAndAirline_shouldReturnMatches() {
        Flight f = new Flight();
        f.setFlightid(999L);
        Airline a = new Airline();
        a.setAirlineid(2L);
        a.setName("Indigo");
        f.setAirline(a);
        f.setSource("BOM");
        f.setDestination("DEL");
        f.setDepartureDatetime(sampleRequest.getDepartureDatetime());
        f.setArrivalDatetime(sampleRequest.getArrivalDatetime());
        f.setTotal_seats(120);
        f.setAvailable_seats(50);
        f.setPrice(2000.0);

        // stub repository for case with all 3 fields
        when(flightRepository.findBySourceAndDestinationAndAirline_NameIgnoreCase("BOM", "DEL", "Indigo"))
                .thenReturn(Collections.singletonList(f));

        FlightRequest req = new FlightRequest();
        req.setSource("BOM");
        req.setDestination("DEL");
        req.setName("Indigo");

        List<FlightResponse> res = flightService.searchFlights(req);

        assertNotNull(res);
        assertEquals(1, res.size());
        assertEquals(999L, res.get(0).getFlightid());
    }


}
