package travalagency.com.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentService {

    @Value("${guiddini.app-key}")
    private String appKey;

    @Value("${guiddini.app-secret}")
    private String appSecret;

    public String initiatePayment() {
        String url = "https://epay.guiddini.dz/api/payment/initiate";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", "application/json");
        headers.set("x-app-key", appKey);
        headers.set("x-app-secret", appSecret);

        // Create the request body as a JSON string
        String requestBody = "{\"amount\": 1000}";

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        return response.getBody();
    }
}
