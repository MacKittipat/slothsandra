package mac.kittipat.slothsandra.core.model;

import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

@Table(value = "message_by_channel")
public class MessageByChannel {

    @PrimaryKey
    private MessageByChannelKey messageByChannelKey;

    private String message;

    public MessageByChannelKey getMessageByChannelKey() {
        return messageByChannelKey;
    }

    public void setMessageByChannelKey(MessageByChannelKey messageByChannelKey) {
        this.messageByChannelKey = messageByChannelKey;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
