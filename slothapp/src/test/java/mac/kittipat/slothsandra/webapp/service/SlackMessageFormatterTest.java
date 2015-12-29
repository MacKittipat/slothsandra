package mac.kittipat.slothsandra.webapp.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class SlackMessageFormatterTest {

    @Spy
    private SlackMessageFormatter slackMessageFormatter;

    @Test
    public void testToPlainText() throws Exception {

        Map<String, String> channelIdMap = new HashMap<>();
        channelIdMap.put("C0H1YRPRT", "general");
        channelIdMap.put("C0486BL04", "random");

        Map<String, String> userIdMap = new HashMap<>();
        userIdMap.put("U10UTDN0J", "Aaa");
        userIdMap.put("U159ECBPJ", "Bbb");
        userIdMap.put("U15AV4Y23", "Ccc");
        String plainText = slackMessageFormatter.toPlainText(
                "Hello <@U10UTDN0J> and <@U159ECBPJ> and <@U15AV4Y23>. How are you ?", channelIdMap, userIdMap);
        Assert.assertEquals("Hello Aaa and Bbb and Ccc. How are you ?", plainText);

        plainText = slackMessageFormatter.toPlainText("<@U10UTDN0J>", channelIdMap, userIdMap);
        Assert.assertEquals("Aaa", plainText);

        plainText = slackMessageFormatter.toPlainText("(<@U10UTDN0J> )", channelIdMap, userIdMap);
        Assert.assertEquals("(Aaa )", plainText);

        plainText = slackMessageFormatter.toPlainText("(<@U10UTDN0J>& )", channelIdMap, userIdMap);
        Assert.assertEquals("(Aaa& )", plainText);

        plainText = slackMessageFormatter.toPlainText("@U10UTDN0J>", channelIdMap, userIdMap);
        Assert.assertEquals("@U10UTDN0J>", plainText);

        plainText = slackMessageFormatter.toPlainText("@U10UTDN0J", channelIdMap, userIdMap);
        Assert.assertEquals("@U10UTDN0J", plainText);

        plainText = slackMessageFormatter.toPlainText("<@U09UTDN0J|Aaa>", channelIdMap, userIdMap);
        Assert.assertEquals("Aaa", plainText);

        plainText = slackMessageFormatter.toPlainText("test<@U09UTDN0J|Aaa> test", channelIdMap, userIdMap);
        Assert.assertEquals("testAaa test", plainText);

        plainText = slackMessageFormatter.toPlainText("Hello <@U10UTDN0J> are you in <#C0H1YRPRT>", channelIdMap, userIdMap);
        Assert.assertEquals("Hello Aaa are you in general", plainText);

        plainText = slackMessageFormatter.toPlainText("<#C0H1YRPRT>", channelIdMap, userIdMap);
        Assert.assertEquals("general", plainText);

        plainText = slackMessageFormatter.toPlainText("<#C0H1YRPRT", channelIdMap, userIdMap);
        Assert.assertEquals("<#C0H1YRPRT", plainText);

        plainText = slackMessageFormatter.toPlainText("#C0H1YRPRT", channelIdMap, userIdMap);
        Assert.assertEquals("#C0H1YRPRT", plainText);
    }
}