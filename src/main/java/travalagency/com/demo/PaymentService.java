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

    public String initiatePayment(double amount) {
        String url = "https://epay.guiddini.dz/api/payment/initiate";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", "application/json");
        headers.set("x-app-key", appKey);
        headers.set("x-app-secret", appSecret);

        String requestBody = "{\"amount\": " + amount + "}";

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        return response.getBody();
    }

    public String showTransaction(String orderNumber) {
        String url = "https://epay.guiddini.dz/api/payment/show?order_number=" + orderNumber;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("x-app-key", appKey);
        headers.set("x-app-secret", appSecret);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return response.getBody();
    }

    public String getReceipt(String orderNumber) {
        String url = "https://epay.guiddini.dz/api/payment/receipt?order_number=" + orderNumber;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("x-app-key", appKey);
        headers.set("x-app-secret", appSecret);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return response.getBody();
    }

    public String sendEmailReceipt(String orderNumber, String email) {
        String url = "https://epay.guiddini.dz/api/payment/email";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", "application/json");
        headers.set("x-app-key", appKey);
        headers.set("x-app-secret", appSecret);

        String requestBody = "{\"order_number\": \"" + orderNumber + "\", \"email\": \"" + email + "\"}";

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        return response.getBody();
    }
}
