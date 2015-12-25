package mac.kittipat.slothsandra.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    @RequestMapping(value = "/")
    public String index() {
        log.info("Slothsandra!");
        return "Slothsandra!";
    }
}
