package travalagency.com.demo;

import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long>{
    @Query("SELECT t FROM Trip t WHERE t.id = :id")
    Optional<Trip>  findTripById(@Param("id") String id);

    @Query("SELECT t FROM Trip t ORDER BY t.id DESC")  // ترتيب العقارات حسب id_RE بشكل تنازلي
	List<Trip> findAllTripsOrderedById_REDesc();
}
