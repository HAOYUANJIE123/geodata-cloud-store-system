<%@ page import="test.service.GetNewFriendsCount" %>
<%@ page import="test.dao.agentCenter" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.GregorianCalendar" %><%--
  Created by IntelliJ IDEA.
  User: hao123
  Date: 2019/9/16
  Time: 16:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<style type="text/css">
    html{height: 5%;width:10%;border: 2px solid red;margin-right: 1%}

</style>
<%
    // 设置每隔1秒刷新一次
    response.setIntHeader("Refresh", 5);
%>
<div>
<label id="agent1" style="border:2px solid black;">a1</label>
<label id="agent2" style="border:2px solid  black">a2</label>
<label id="agent3" style="border:2px solid  black">a3</label>
<label id="sum" style="border:2px solid  black;"></label>
<label id="time" style="border:2px solid  black;"></label>
</div>
<script type="text/javascript">

    //信息更新部分
    var t;
    var a1=0;   var a2=0;   var a3=0;
   var i=0;
    <%agentCenter.Alert(request,response);%>
    function timedCount()
    {

        i++;
        document.getElementById('time').innerText=i+"s";

        document.getElementById('sum').innerText=a1.toString()+"  "+a2.toString()+"  "+a3.toString()+"  ";
        //alert(a1.toString()+"  "+a2.toString()+"  "+a3.toString()+"  ")
        if(a1.toString()=="1")
          document.getElementById('agent1').style.backgroundColor="red"
        else
            document.getElementById('agent1').style.backgroundColor="black"
        if(a2.toString()=="1")
            document.getElementById('agent2').style.backgroundColor="red"
        else
            document.getElementById('agent2').style.backgroundColor="black"
        if(a3.toString()=="1")
            document.getElementById('agent3').style.backgroundColor="red"
        else
            document.getElementById('agent3').style.backgroundColor="black"

         t=setTimeout("timedCount()",1000)//不断循环时间，数字为设置更新间隔
    }
    timedCount();
    </script>
</body>
</html>
