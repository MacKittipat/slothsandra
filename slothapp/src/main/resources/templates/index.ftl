<div class="row">
    <div class="col s12">
        <h1>
            Slothsandra
        </h1>
    </div>
    <div class="col s12">
        <ul>
            <#list channelList as channel>
                <li><a href="/slothsandra/channel/${channel.channelKey.channelName}">${channel.channelKey.channelName}</a></li>
            </#list>
        </ul>
    </div>
</div>


