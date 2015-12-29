package mac.kittipat.slothsandra.webapp.controller.api;

import mac.kittipat.slothsandra.core.bean.MessageBean;
import mac.kittipat.slothsandra.core.dao.MessageByChannelDao;
import mac.kittipat.slothsandra.core.dao.MessageByUserChannelDao;
import mac.kittipat.slothsandra.core.dao.MessageDao;
import mac.kittipat.slothsandra.core.dao.YearByChannelDao;
import mac.kittipat.slothsandra.core.model.MessageByUserChannel;
import mac.kittipat.slothsandra.core.model.YearByChannel;
import mac.kittipat.slothsandra.webapp.service.SlackMessageFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api")
public class MessageRestController {

    private static final int DEFAULT_LIMIT = 50;

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private MessageByChannelDao messageByChannelDao;

    @Autowired
    private MessageByUserChannelDao messageByUserChannelDao;

    @Autowired
    private YearByChannelDao yearByChannelDao;

    @Autowired
    private SlackMessageFormatter slackMessageFormatter;

    @Resource
    private Map<String, String> channelIdMap;

    @Resource
    private Map<String, String> userIdMap;

    /**
     * createdTime from Slack is not millisecond. Convert to millisecond by multiply by 1000.
     */
    @RequestMapping(value = "/messages", method = RequestMethod.POST)
    public ResponseEntity create(@RequestParam String channelName,
                                 @RequestParam String username,
                                 @RequestParam String message,
                                 @RequestParam Double createdTime) {

        messageDao.insert(
                channelName,
                username,
                slackMessageFormatter.toPlainText(message, channelIdMap, userIdMap),
                createdTime.longValue()*1000);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/channels/{channelName}/messages", method = RequestMethod.GET)
    public ResponseEntity<List<MessageBean>> findByChannel(@PathVariable String channelName,
                                                           @RequestParam(required = false) Long createdTime,
                                                           @RequestParam(required = false) Integer limit) {

        if(createdTime == null) {
            createdTime = Instant.now().toEpochMilli();
        }
        if(limit == null) {
            limit = DEFAULT_LIMIT;
        }

        int yearIndex = 0;
        List<YearByChannel> yearByChannelList = yearByChannelDao.findByChannel(channelName);
        List<MessageBean> messageByChannelList = new ArrayList<>();
        while (messageByChannelList.size() < limit && yearIndex < yearByChannelList.size()) {
            List<MessageBean> messageByChannelListTemp =
                    messageByChannelDao.findMessageByChannel(
                            channelName,
                            yearByChannelList.get(yearIndex).getYearByChannelKey().getYear(),
                            createdTime,
                            limit - messageByChannelList.size());
            messageByChannelList.addAll(messageByChannelListTemp);
            yearIndex++;
        }

        return new ResponseEntity<>(messageByChannelList, HttpStatus.OK);
    }

    @RequestMapping(value = "/channels/{channelName}/users/{username}/messages", method = RequestMethod.GET)
    public ResponseEntity<List<MessageByUserChannel>> findByUserChannel(@PathVariable String channelName,
                                                                        @PathVariable String username) {

        return new ResponseEntity<>(messageByUserChannelDao.findMessageByUserChannel(username, channelName), HttpStatus.OK);
    }
}
