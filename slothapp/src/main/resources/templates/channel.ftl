<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Slothsandra</title>
</head>
<body>
    <h1>
        Slothsandra : ${channelName}
    </h1>
    <div style="float: left; width: 30%;">
        <ul>
            <#list userByChannelList as userByChannel>
                <li>${userByChannel.userByChannelKey.username}</li>
            </#list>
        </ul>
    </div>
    <div style="float: left; width: 70%;">
        <ul id="messages-list">
        </ul>
    </div>
    <script type="text/javascript" src="/slothsandra/assets/jquery/jquery-2.1.4.min.js"></script>
    <script type="text/javascript">

        var latestCreatedTime = 0;

        var cb = function(data) {
            for(var i=0; i<data.length; i++) {
                $('#messages-list').append('<li>(' + data[i].readableCreatedTime + ') ' + data[i].username + ' : ' + data[i].escapedMessage + '</li>')
            }
            if(data.length > 0) {
                latestCreatedTime =  data[data.length - 1].createdTime;
            } else {
                latestCreatedTime = 0;
            }
        }

        $(document).ready(function() {
            $.get('http://localhost:9000/slothsandra/api/channels/${channelName}/messages', cb);
        });

        $(window).scroll(function() {
            if($(window).scrollTop() + $(window).height() == $(document).height() && latestCreatedTime != 0) {
                $.get('http://localhost:9000/slothsandra/api/channels/${channelName}/messages?createdTime=' + latestCreatedTime, cb);
            }
        });

    </script>
</body>
</html>