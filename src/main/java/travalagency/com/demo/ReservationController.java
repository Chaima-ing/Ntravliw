package travalagency.com.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/reserve")
    public ResponseEntity<Map<String, Long>> reserveTrip(@RequestPart("reservationRequest") ReservationRequest reservationRequest) {
        try {
            Reservation reservation = reservationService.saveReservation(reservationRequest);
            Map<String, Long> response = new HashMap<>();
            response.put("reservationId", reservation.getId());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, Long> error = new HashMap<>();
            error.put("error", -1L);
            return ResponseEntity.badRequest().body(error);
        } catch (Exception e) {
            Map<String, Long> error = new HashMap<>();
            error.put("error", -2L);
            return ResponseEntity.internalServerError().body(error);
        }
    }

    @PutMapping("/update-reservation/{reservationId}")
    public ResponseEntity<Map<String, Long>> updateReservation(
            @PathVariable Long reservationId,
            @RequestPart("reservationRequest") ReservationRequest reservationRequest) {
        try {
            Reservation reservation = reservationService.updateReservation(reservationId, reservationRequest);
            Map<String, Long> response = new HashMap<>();
            response.put("reservationId", reservation.getId());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, Long> error = new HashMap<>();
            error.put("error", -1L);
            return ResponseEntity.badRequest().body(error);
        } catch (Exception e) {
            Map<String, Long> error = new HashMap<>();
            error.put("error", -2L);
            return ResponseEntity.internalServerError().body(error);
        }
    }

    @DeleteMapping("/delete-reservation/{reservationId}")
    public ResponseEntity<Map<String, Long>> deleteReservation(@PathVariable Long reservationId) {
        try {
            reservationService.deleteReservation(reservationId);
            Map<String, Long> response = new HashMap<>();
            response.put("reservationId", reservationId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Long> error = new HashMap<>();
            error.put("error", -1L);
            return ResponseEntity.internalServerError().body(error);
        }
    }
}
