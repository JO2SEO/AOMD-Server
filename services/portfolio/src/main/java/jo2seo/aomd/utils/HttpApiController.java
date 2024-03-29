package jo2seo.aomd.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpMethod.POST;

@RestController
public class HttpApiController {
    RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<String> getRequest(String url) {
        return restTemplate.getForEntity(url, String.class);
    }

    public ResponseEntity<String> postRequest(String url, Object object) {
        HttpEntity<Object> request = new HttpEntity<>(object);
        ResponseEntity<String> response = restTemplate.exchange(url, POST, request, String.class);
        return response;
    }
}
