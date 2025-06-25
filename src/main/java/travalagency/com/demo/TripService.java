package travalagency.com.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TripService {
    @Autowired
    private TripRepository tripRepository;

    public void saveTrip(Trip trip) {
        tripRepository.save(trip);
    }

}
