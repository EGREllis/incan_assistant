package net.assistant.view;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.util.Date;
import java.util.Map;

/*
Compare difference with this:
https://github.com/spring-projects/spring-boot/blob/v2.0.8.RELEASE/spring-boot-samples/spring-boot-sample-web-jsp

Currently when run:
2021-01-21 20:35:13.391  WARN 34717 --- [nio-8080-exec-2] o.s.w.s.r.ResourceHttpRequestHandler     : Path with "WEB-INF" or "META-INF": [WEB-INF/jsp/welcome.jsp]
2021-01-21 20:35:13.391 DEBUG 34717 --- [nio-8080-exec-2] o.s.w.s.r.ResourceHttpRequestHandler     : Resource not found
 */

@Controller
public class WebMapping {
    @Value("${application.message:Hello World}")
    private String message = "Hello World";

    @GetMapping("/")
    public String hello(Map<String,Object> model) {
        model.put("time", new Date());
        model.put("name", this.message);
        return "welcome";
    }

    @GetMapping("/card")
    public String card(Map<String,Object> model) {
        return "card";
    }

    @PostMapping("/new_assist")
    public String startAssistant(@RequestParam("players") String players, Map<String,Object> model) {
        model.put("players", players);
        return "assist";
    }

    @GetMapping("/main.css")
    public String getMainCss(Map<String, Object> model) {
        return "css/main_css";
    }

    @GetMapping("/main.js")
    public String getMainJs(Map<String,Object> model) {
        return "js/main_js";
    }
}
