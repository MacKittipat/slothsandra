package mac.kittipat.slothsandra.webapp.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SlackMessageFormatter {

    private static final String PATTERN = "<(.*?)>";

    /**
     * Convert slack user id and channel id to username and channel respectively.
     * Ref : https://api.slack.com/docs/formatting
     */
    public String toPlainText(String message, Map<String, String> channelIdMap, Map<String, String> userIdMap) {
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            // group(0) is <@U09UTDN0J> and group(1) is @U09UTDN0J
            String escapeId = matcher.group(0);
            String id = matcher.group(1);
            if('@' == id.charAt(0)) {
                // This is user id
                message = message.replaceAll(escapeId, userIdMap.get(id.substring(1)));
            } else if ('#' == id.charAt(0)) {
                // This is channel id
                message = message.replaceAll(escapeId, channelIdMap.get(id.substring(1)));
            }
        }
        return message;
    }
}
