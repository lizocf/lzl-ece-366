package webservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;


@SpringBootApplication
@CrossOrigin
@Controller
public class MessagingStompWebsocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessagingStompWebsocketApplication.class, args);
    }
}