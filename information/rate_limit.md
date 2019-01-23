# 接口限频说明文档及缓存规则

## 频次限制规则

|  名称    |  规则 (次/单位)     |  redis-key(前缀 smim: ) |  config-key   |
|:---------------|:---------------|:---------------|:---------------|
|登录限制|10/分钟|CarryJey:rate_limit:login:per_user_id_{userId}|app.rateLimit.login|