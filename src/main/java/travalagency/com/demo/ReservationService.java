package travalagency.com.demo;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TripRepository tripRepository;
    private final ClientRepository clientRepository;

    public ReservationService(ReservationRepository reservationRepository,
                             TripRepository tripRepository,
                             ClientRepository clientRepository) {
        this.reservationRepository = reservationRepository;
        this.tripRepository = tripRepository;
        this.clientRepository = clientRepository;
    }

    @Transactional
    public Reservation saveReservation(ReservationRequest request) {
        Trip trip = tripRepository.findById(request.getTripId())
                .orElseThrow(() -> new IllegalArgumentException("Trip not found"));

        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));

        // Check if there are enough available places
        if (trip.getAvailablePlaces() < request.getNumberOfPersons()) {
            throw new IllegalArgumentException("Not enough available places for this trip");
        }

        double totalPrice = trip.getPricePerPerson() * request.getNumberOfPersons();

        Reservation reservation = new Reservation();
        reservation.setTrip(trip);
        reservation.setClient(client);
        reservation.setReservedAt(java.time.LocalDateTime.now());
        reservation.setNumberOfPersons(request.getNumberOfPersons());
        reservation.setTotalPrice(totalPrice);
        reservation.setStatus("PENDING");

        // Save the reservation
        reservation = reservationRepository.save(reservation);

        // Update the available places in the trip
        trip.setAvailablePlaces(trip.getAvailablePlaces() - request.getNumberOfPersons());
        tripRepository.save(trip);

        return reservation;
    }

    @Transactional
    public Reservation updateReservation(Long reservationId, ReservationRequest request) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        Trip trip = tripRepository.findById(request.getTripId())
                .orElseThrow(() -> new IllegalArgumentException("Trip not found"));

        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));

        double totalPrice = trip.getPricePerPerson() * request.getNumberOfPersons();

        reservation.setTrip(trip);
        reservation.setClient(client);
        reservation.setNumberOfPersons(request.getNumberOfPersons());
        reservation.setTotalPrice(totalPrice);
        // Optionally update the status if needed
        // reservation.setStatus("UPDATED");

        return reservationRepository.save(reservation);
    }

    public void deleteReservation(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }
}
