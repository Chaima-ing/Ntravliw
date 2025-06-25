package travalagency.com.demo;
import jakarta.persistence.*;

@Entity
public class Account {

    @Id
    @Column(name="id", unique = true)
    private String id;

    @Column(name="password", columnDefinition = "TEXT")
    private String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
