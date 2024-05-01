package webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class WebSocketTextController {

    @Autowired
    SimpMessagingTemplate template;

    @PostMapping("/send")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Void> sendMessage(@RequestBody TextMessageDTO textMessageDTO) {
        template.convertAndSend("/topic/message", textMessageDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @MessageMapping("/sendMessage")
    @CrossOrigin(origins = "*")
    public void receiveMessage(@Payload TextMessageDTO textMessageDTO) {
        // receive message from client
    }


    @SendTo("/topic/message")
    @CrossOrigin(origins = "*")
    public TextMessageDTO broadcastMessage(@Payload TextMessageDTO textMessageDTO) {
        return textMessageDTO;
    }

}