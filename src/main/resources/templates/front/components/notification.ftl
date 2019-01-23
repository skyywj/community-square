<#macro notification userId read limit>
    <@tag_notifications userId=userId read=read limit=limit>
        <#list notifications as notification>
      <div class="media">
          <div class="media-left">

              <img src="${notification.fromUserAvatar!}" class="avatar avatar-sm">
          </div>
          <div class="media-body">
              <div class="gray" <#if !notification.read>style="font-weight:700;"</#if>>
                  <a href="/user/${notification.fromUserName!}">${notification.fromUserName!}</a>
                  <span>${model.formatDate(notification.createdTime)}</span>
            <#if notification.action == 0>
              评论了你的话题 <a href="/topic/${notification.topicId}">${notification.topicTitle!}</a>
            <#elseif notification.action == 1>
              在话题 <a href="/topic/${notification.topicId}">${notification.topicTitle!}</a> 下回复了你
            <#elseif notification.action == 2>
              收藏了你的话题 <a href="/topic/${notification.topicId}">${notification.topicTitle!}</a>
            <#elseif notification.action == 3>
              赞了你的话题 <a href="/topic/${notification.topicId}">${notification.topicTitle!}</a>
            <#elseif notification.action == 4>
              赞了你在话题 <a href="/topic/${notification.topicId}">${notification.topicTitle!}</a>下的评论
            </#if>
              </div>
          <#if notification.content??  && notification.action != 3 && notification.action != 4>
            <div class="payload">
                ${model.formatContent(notification.content)}
            </div>
          </#if>
          </div>
      </div>
            <#if notification_has_next>
        <div class="divide"></div>
            </#if>
        </#list>
    </@tag_notifications>
</#macro>
