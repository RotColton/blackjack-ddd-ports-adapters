package game;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class HomeController {

    @Value("${app.testProp: Default value}")
    private String testProp;

    @GetMapping("/prop")
    public String hello(){
        return testProp;
    }
}
