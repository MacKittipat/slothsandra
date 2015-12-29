package mac.kittipat.slothsandra.core.bean;

import java.util.Date;

public class MessageBean {

    private String channelName;
    private String username;
    private String message;
    private String escapedMessage;
    private Date createdTime;
    private String readableCreatedTime;

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEscapedMessage() {
        return escapedMessage;
    }

    public void setEscapedMessage(String escapedMessage) {
        this.escapedMessage = escapedMessage;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getReadableCreatedTime() {
        return readableCreatedTime;
    }

    public void setReadableCreatedTime(String readableCreatedTime) {
        this.readableCreatedTime = readableCreatedTime;
    }
}
