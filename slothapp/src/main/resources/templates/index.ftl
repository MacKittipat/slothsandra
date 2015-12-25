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
        Slothsandra
    </h1>
    <div>
        <ul>
            <#list channelList as channel>
                <li><a href="/slothsandra/channel/${channel.channelKey.channelName}">${channel.channelKey.channelName}</a></li>
            </#list>
        </ul>
    </div>
</body>
</html>