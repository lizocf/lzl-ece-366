package webservice;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class ResponseController {


    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Response response(Message message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Response(HtmlUtils.htmlEscape(message.getMsg()));
    }

}
