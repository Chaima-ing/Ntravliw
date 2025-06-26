package travalagency.com.demo;

import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TripRepository tripRepository;
    private final ClientRepository clientRepository;

    public ReservationService(ReservationRepository reservationRepository, TripRepository tripRepository, ClientRepository clientRepository) {
        this.reservationRepository = reservationRepository;
        this.tripRepository = tripRepository;
        this.clientRepository = clientRepository;
    }

    public Reservation saveReservation(ReservationRequest request) {
        Trip trip = tripRepository.findById(request.getTripId())
                .orElseThrow(() -> new IllegalArgumentException("Trip not found"));

        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));

        double totalPrice = trip.getPricePerPerson() * request.getNumberOfPersons();

        Reservation reservation = new Reservation();
        reservation.setTrip(trip);
        reservation.setClient(client);
        reservation.setReservedAt(java.time.LocalDateTime.now());
        reservation.setNumberOfPersons(request.getNumberOfPersons());
        reservation.setTotalPrice(totalPrice);
        reservation.setStatus("PENDING");

        return reservationRepository.save(reservation);
    }
}
