package travalagency.com.demo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/initiate")
    public ResponseEntity<String> initiatePayment() {
        String response = paymentService.initiatePayment();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/show")
    public ResponseEntity<String> showTransaction(@RequestParam String orderNumber) {
        String response = paymentService.showTransaction(orderNumber);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/receipt")
    public ResponseEntity<String> getReceipt(@RequestParam String orderNumber) {
        String response = paymentService.getReceipt(orderNumber);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/initiate-and-show")
    public ResponseEntity<String> initiateAndShowPayment() {
        String initiateResponse = paymentService.initiatePayment();

        try {
            JsonNode rootNode = objectMapper.readTree(initiateResponse);
            String orderNumber = rootNode.path("data").path("id").asText();

            String showResponse = paymentService.showTransaction(orderNumber);
            return ResponseEntity.ok(showResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error parsing the response: " + e.getMessage());
        }
    }

    @PostMapping("/initiate-show-receipt")
    public ResponseEntity<String> initiateShowAndGetReceipt() {
        String initiateResponse = paymentService.initiatePayment();

        try {
            JsonNode rootNode = objectMapper.readTree(initiateResponse);
            String orderNumber = rootNode.path("data").path("id").asText();

            String showResponse = paymentService.showTransaction(orderNumber);
            String receiptResponse = paymentService.getReceipt(orderNumber);

            return ResponseEntity.ok("Show Response: " + showResponse + "\nReceipt: " + receiptResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error processing the request: " + e.getMessage());
        }
    }
}

