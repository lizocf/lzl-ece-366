package webservice;

import org.springframework.web.bind.annotation.CrossOrigin;

// transfers post request to the websocket\
@CrossOrigin
public class TextMessageDTO {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
