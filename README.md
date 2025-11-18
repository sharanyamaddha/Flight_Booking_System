**Project Overview**

The Flight Booking System is a Spring Boot REST API application that allows users to search for flights, book tickets, manage passenger information, view booking history, and cancel booked tickets.
The system also supports admin operations such as adding flight inventory and managing airline schedules.
It provides a clean, modular backend architecture using Spring Boot, JPA, DTOs, and proper service–controller layers.


 **API Endpoints Overview**

Below is a brief explanation of all APIs implemented in this project:

🔹 1. Add Flight Inventory / Schedule (Admin)

POST /api/v1.0/flight/airline/inventory/add

Allows admin to add new flight schedules or airline inventory.

Includes airline name, source, destination, timings, price, and total seats.

🔹 2. Search for Flights

POST /api/v1.0/flight/search
Users can search flights based on:

Source

Destination

Airline name

Returns a list of flights that match the criteria.

🔹 3. Book a Flight Ticket

POST /api/v1.0/flight/booking/{flightid}
Allows a user to book seats for a specific flight.
Users provide:

Email ID

Number of passengers

Each passenger’s details (name, age, gender, seat number, meal type)

System generates a PNR and stores booking + passenger details.

🔹 4. View Ticket Details (Using PNR)

GET /api/v1.0/flight/ticket/{pnr}

Fetches a booking based on PNR.

Returns:

Flight details

Passenger details

Total amount

Booking status

🔹 5. View Booking History (Email-based)

GET /api/v1.0/flight/booking/history/{emailId}

Allows users to see all their previous bookings based on email ID.

Useful for tracking past flights.

🔹 6. Cancel a Ticket

DELETE /api/v1.0/flight/booking/cancel/{pnr}

Cancels a booked ticket if it’s within allowed time.

Restores the seats back to the flight.

Updates booking status to CANCELLED.
