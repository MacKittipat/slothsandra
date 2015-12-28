package mac.kittipat.slothsandra.webapp.controller;

import mac.kittipat.slothsandra.core.dao.ChannelDao;
import mac.kittipat.slothsandra.core.dao.MessageByChannelDao;
import mac.kittipat.slothsandra.core.dao.UserByChannelDao;
import mac.kittipat.slothsandra.core.dao.YearByChannelDao;
import mac.kittipat.slothsandra.core.model.Channel;
import mac.kittipat.slothsandra.core.model.MessageByChannel;
import mac.kittipat.slothsandra.core.model.UserByChannel;
import mac.kittipat.slothsandra.core.model.YearByChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    private static final int DEFAULT_LIMIT = 50;

    @Autowired
    private ChannelDao channelDao;

    @Autowired
    private UserByChannelDao userByChannelDao;

    @Autowired
    private MessageByChannelDao messageByChannelDao;

    @Autowired
    private YearByChannelDao yearByChannelDao;

    @RequestMapping(value = "/")
    public String index(Model model) {
        List<Channel> channelList = channelDao.findAll();
        model.addAttribute("channelList", channelList);
        return "index";
    }

    @RequestMapping(value = "/channel/{channelName}")
    public String channel(Model model,
                          @PathVariable String channelName,
                          @RequestParam(required = false) Long createdTime,
                          @RequestParam(required = false) Integer limit) {
        List<UserByChannel> userByChannelList = userByChannelDao.findUserByChannel(channelName);
        model.addAttribute("userByChannelList", userByChannelList);

        if(createdTime == null) {
            createdTime = Instant.now().toEpochMilli();
        }
        if(limit == null) {
            limit = DEFAULT_LIMIT;
        }

        int yearIndex = 0;
        List<YearByChannel> yearByChannelList = yearByChannelDao.findByChannel(channelName);
        List<MessageByChannel> messageByChannelList = new ArrayList<>();
        while (messageByChannelList.size() < limit && yearIndex < yearByChannelList.size()) {
            List<MessageByChannel> messageByChannelListTemp =
                    messageByChannelDao.findMessageByChannel(
                            channelName,
                            yearByChannelList.get(yearIndex).getYearByChannelKey().getYear(),
                            createdTime,
                            limit - messageByChannelList.size());
            messageByChannelList.addAll(messageByChannelListTemp);
            yearIndex++;
        }
        model.addAttribute("messageByChannelList", messageByChannelList);
        model.addAttribute("latestCreatedTime", messageByChannelList.get(messageByChannelList.size() - 1)
                .getMessageByChannelKey().getCreatedTime().getTime());
        return "channel";
    }

}
