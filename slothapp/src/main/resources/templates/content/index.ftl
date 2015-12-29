<div class="row">
    <div class="col s12">
        <h3>
            Channels
        </h3>
    </div>
    <div class="col s12">
        <div class="collection">
            <#list channelList as channel>
                <a href="/slothsandra/channel/${channel.channelKey.channelName}" class="collection-item">
                    ${channel.channelKey.channelName}
                    <span class="badge">${channel.countMessage}</span>
                </a>
            </#list>
        </div>
    </div>
</div>


