package mac.kittipat.slothsandra.webapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SlackMessageFormatter {

    private static final Logger log = LoggerFactory.getLogger(SlackMessageFormatter.class);

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
            try {
                if('@' == id.charAt(0)) {
                    // This is user id
                    String username;
                    if(id.substring(1).contains("|")) {
                        // id.substring(1) might be <@id|name> format
                        username = id.substring(1).split("\\|")[1];
                    } else {
                        username = userIdMap.get(id.substring(1));
                    }
                    message = StringUtils.replace(message, escapeId, username);
                } else if ('#' == id.charAt(0)) {
                    // This is channel id
                    message = StringUtils.replace(message, escapeId, channelIdMap.get(id.substring(1)));
                }
            } catch (NullPointerException e) {
                log.error("Error with message={}, channelIdMap={}, userIdMap={}",
                        message, channelIdMap.toString(), userIdMap.toString());
            }
        }
        return message;
    }
}
