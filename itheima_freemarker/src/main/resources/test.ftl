<html>
<head>
    <meta charset="utf-8">
    <title>Freemarker入门小DEMO </title>
</head>
<body>
<#--我只是一个注释，我不会有任何输出  -->
${name},你好。${message}
<br/>
<#assign user={"name":"张无忌","age":"300"} >
姓名：${user.name},年龄：${user.age}
<br/>
<#-- 在页面中可以引入其他页面-->
<#include "head.ftl">
<br/>
<#if success == true>
    传递的success是true
<#else>
    否则success是false
</#if>
<br/>
<#list goodsList as goods>
   索引:${goods_index+1} --- 商品名称：${goods.name},价格：${goods.price}<br/>
</#list>
商品总数：${goodsList?size}
<br/>
<#--把String字符串转JSON对象的 -->
<#assign userInfo="{'username':'白眉鹰王','age':'350'}">
信息：${userInfo}
<br/>
<#assign u=userInfo?eval>
姓名：${u.username};年龄：${u.age}

<br/>
当前时间：${date?date}<br/>
当前时间：${date?time}<br/>
当前时间：${date?datetime}<br/>
当前时间：${date?string("yyyy-MM-dd HH:mm:ss SSS")}<br/>

${goodsId?c}
<br/>
<#if aaa??>
    这个值有值${aaa}
<#else >
    这个值没有
</#if>

<br/>
<#-- 叹号：作为变量值的判断；如果有值输出；如果妹纸输出后面的数据 -->
${usernames!}

</body>
</html>
