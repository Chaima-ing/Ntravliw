package travalagency.com.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TripService {
    @Autowired
    private TripRepository tripRepository;

    public void saveTrip(Trip trip) {
        tripRepository.save(trip);
    }

    public  List<Trip> findAllTrips (){
        List<Trip> listeTrips =	tripRepository.findAllTripsOrderedById_REDesc();
        return listeTrips;
    }

}
