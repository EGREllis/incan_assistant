package net.assistant.view;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebMapping {
    @GetMapping("/")
    public String index() {
        return "<html><head><title>Index is working</title></head><body><p>Index is working</p></body></html>";
    }
}
