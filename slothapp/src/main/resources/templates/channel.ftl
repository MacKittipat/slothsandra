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
        <ul>
        <#list messageByChannelList as messageByChannel>
            <li>${messageByChannel.message}</li>
        </#list>
        </ul>
    </div>
    <script type="text/javascript" src="/slothsandra/assets/jquery/jquery-2.1.4.min.js"></script>
    <script type="text/javascript">
        $(window).scroll(function() {
            if($(window).scrollTop() + $(window).height() == $(document).height()) {
                console.log("Scroll !!!")
            }
        });
    </script>
</body>
</html>