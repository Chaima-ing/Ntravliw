package travalagency.com.demo;

import java.util.Optional;

import org.aspectj.weaver.loadtime.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationRepository extends JpaRepository<Account, Long> {
    @Query("SELECT a FROM Account a WHERE a.id = :id")
    Optional<Account>  findById(@Param("id") String id);
    @Query("SELECT a FROM Account a WHERE a.id = :id AND a.password = :password")
    Optional<Account> findAccountByIdAndPassword(String id, String password); 
}
