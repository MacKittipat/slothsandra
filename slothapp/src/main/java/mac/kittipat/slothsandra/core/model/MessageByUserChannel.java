package mac.kittipat.slothsandra.core.model;

import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

@Table(value = "message_by_user_channel")
public class MessageByUserChannel {

    @PrimaryKey
    private MessageByUserChannelKey messageByUserChannelKey;

    private String message;

    public MessageByUserChannelKey getMessageByUserChannelKey() {
        return messageByUserChannelKey;
    }

    public void setMessageByUserChannelKey(MessageByUserChannelKey messageByUserChannelKey) {
        this.messageByUserChannelKey = messageByUserChannelKey;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
