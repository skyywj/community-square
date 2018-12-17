<#macro user_topics isPaginate=false isFooter=false>
  <div class="panel panel-default">
    <div class="panel-heading">${user.username}创建的话题</div>
    <#if topics.total == 0>
      <div class="panel-body">
        暂无话题
      </div>
    <#else>
      <div class="panel-body paginate-bot">
        <#list topics.records as topic>
          <div class="media">
            <div class="media-body">
              <div class="title">
                <a href="/topic/${topic.id}">
                  ${topic.title!?html}
                </a>
              </div>
              <p>
                <span><a href="/user/${topic.username}">${topic.username}</a></span>
                <span class="hidden-sm hidden-xs">•</span>
                <span class="hidden-sm hidden-xs"><a href="/topic/${topic.id}">${topic.commentCount}个评论</a></span>
                <span class="hidden-sm hidden-xs">•</span>
                <span class="hidden-sm hidden-xs">${topic.view}次浏览</span>
                <span>•</span>
                <span>${model.formatDate(topic.inTime)}</span>
              </p>
            </div>
          </div>
          <#if topic_has_next>
            <div class="divide mar-top-5"></div>
          </#if>
        </#list>
        <#if isPaginate>
          <#include "./paginate.ftl"/>
          <@paginate currentPage=topics.current totalPage=topics.pages actionUrl="/user/${user.username}/topics" urlParas=""/>
        </#if>
      </div>
      <#if isFooter>
        <div class="panel-footer">
          <a href="/user/${user.username}/topics">${user.username}更多话题&gt;&gt;</a>
        </div>
      </#if>
    </#if>
  </div>
</#macro>
