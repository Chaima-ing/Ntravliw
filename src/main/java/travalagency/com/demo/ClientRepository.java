package travalagency.com.demo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClientRepository extends JpaRepository<Client, Long>{
    @Query("SELECT c FROM Client c WHERE c.id = :id")
    Optional<Client>  findById(@Param("id") Long id);
}
