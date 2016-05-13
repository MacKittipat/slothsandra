# Slothsandra
Slack (Sloth) + Cassandra : Experiment with Slack API and Apache Cassandra

Written in Java and Node.js

https://slack.com/ is a great chat application but it's free version has limitation. 
Free version keep 10,000 most recent message so we cannot look up for very old message.

Slothsandra solve that limitation. It has slack bot that will store all public chat message into Apache cassandra. It also provide UI for lookup all message from Apache cassadra.

### Data model

![Chebotko Diagram]
(https://github.com/MacKittipat/slothsandra/blob/master/dia/chebotko.png)

- Q1 : Find all channel, Count all message in channel
- Q2 : Find all user in channel, Count all message of user in channel
- Q3 : Find all message in channel
- Q4 : Find all message of user in channel
- Q5 : Find all user
- Q6 : Find year of channel. Channel might have message from many years such as 2015, 2016.
