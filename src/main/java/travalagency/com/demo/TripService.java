package travalagency.com.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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

    public Trip findTripById (Long id){
        Optional<Trip> optionalTrip = tripRepository.findTripById(id);
        Trip trip = optionalTrip.get();
        return trip;
    }

}
