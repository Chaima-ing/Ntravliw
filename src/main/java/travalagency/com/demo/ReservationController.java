package travalagency.com.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/reserve")
public ResponseEntity<Map<String, Long>> reserveTrip(
    @RequestPart("reservationRequest") ReservationRequest reservationRequest
) {
    try {
        Reservation reservation = reservationService.saveReservation(reservationRequest);

        Map<String, Long> response = new HashMap<>();
        response.put("reservationId", reservation.getId());
        return ResponseEntity.ok(response);

    } catch (IllegalArgumentException e) {
        Map<String, Long> error = new HashMap<>();
        error.put("error"+e.getMessage(), -1L);
        return ResponseEntity.badRequest().body(error);
    } catch (Exception e) {
        e.printStackTrace();
        Map<String, Long> error = new HashMap<>();
        error.put("error", -2L);
        return ResponseEntity.internalServerError().body(error);
    }
}

}
