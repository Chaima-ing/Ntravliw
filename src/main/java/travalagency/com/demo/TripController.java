package travalagency.com.demo;

import java.time.LocalDateTime;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
public class TripController {

    private final TripService tripService;

    @Autowired
    public TripController(TripService tripService){
        this.tripService = tripService;
    }

    @PostMapping("/publish-trip")
    public ResponseEntity<Map<String, Long>> publishTrip(
        @RequestPart("tripRequest") TripRequest tripRequest,
        @RequestPart(value = "images", required = false) List<MultipartFile> files
    ) {
        try {
            System.out.println("Trip publish request received: " + tripRequest);

            // Build the Trip entity from request
            Trip trip = new Trip();
            trip.setTitle(tripRequest.getTitle());
            trip.setDescription(tripRequest.getDescription());
            trip.setDestination(tripRequest.getDestination());
            trip.setDepartureLocation(tripRequest.getDepartureLocation());
            trip.setDepartureDate(tripRequest.getDepartureDate());
            trip.setReturnDate(tripRequest.getReturnDate());
            trip.setDurationDays(tripRequest.getDurationDays());
            trip.setPricePerPerson(tripRequest.getPricePerPerson());
            trip.setAvailablePlaces(tripRequest.getAvailablePlaces());
            trip.setTransportType(tripRequest.getTransportType());
            trip.setHotelName(tripRequest.getHotelName());
            trip.setHotelStars(tripRequest.getHotelStars());
            trip.setIncludesVisa(tripRequest.isIncludesVisa());
            trip.setStatus("PUBLISHED");
            trip.setCreatedAt(LocalDateTime.now());
            trip.setUpdatedAt(LocalDateTime.now());

            // Save trip
            tripService.saveTrip(trip);

            // Save images if any
            if (files != null && !files.isEmpty()) {
                System.out.println("Saving trip images...");
            } else {
                System.out.println("No images provided.");
            }

            // Return response with trip ID
            Map<String, Long> response = new HashMap<>();
            response.put("tripId", trip.getId());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Long> errorResponse = new HashMap<>();
            errorResponse.put("error", -1L);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/getTrips")
    public List<Trip> getAllTrips() {
        return tripService.findAllTrips();
    }

}
