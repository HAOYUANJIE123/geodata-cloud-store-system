<%--
  Created by IntelliJ IDEA.
  User: lai
  Date: 2019/5/11
  Time: 0:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="static test.vo.FriendClass.*" %>
<%@ page import="static test.vo.User.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no, width=device-width">
    <link rel="stylesheet" href="https://a.amap.com/jsapi_demos/static/demo-center/css/demo-center.css" />
    <link rel="stylesheet" type="text/css" href="https://a.amap.com/jsapi_demos/static/demo-center/css/prety-json.css">
    <title>数据检索</title>
    <style>

    </style>
</head>
<body>
<style>
    html, body {
        height: 100%;
    }
    .majorbox{
        background-size: cover;
        margin: 0 auto;
        position: relative;
        width: 99%;
        height: 99%;
        border:2px solid #929799;
        border-top:50px solid #929799;
        border-left: 5px solid #929799;
        border-right: 5px solid #929799;

    }
    .menubox{
        margin-top: 0%;
        background-size: cover;
        margin: 0 auto;
        position: relative;
        width: 100%;
        height: 5%;
        border:2px solid #929799;
    }

    .user-box{
        float: left;
        background-size: cover;
        width: 60%;
        height: 95%;
        border:1px solid #929799;
    }
    .friend-box{
        float: left;
        width: 40%;
        height:95%;
        border:1px solid #929799;
    }
    .friendinfo{
        float: top;
        width: 100%;
        height:50%;
        border:1px solid #678b99;
    }

    .findfriend-box{
        float: top;
        background-size: cover;
        width: 100%;
        height: 20%;
        border:1px solid #678b99;
    }
    .addfriend-box{
        float: top;
        background-size: cover;
        width: 100%;
        height: 30%;
        border:1px solid #678b99;
    }

    .upload-title {
        margin-top: 1%;
        margin-left: 30%;
    }
    .upload-data-info{
        margin-top: 3%;
        margin-left: 30%;
    }
    .upload-data-info2{
        margin-top: 2%;
        margin-left: 1%;
    }

</style>
<div id="majorbox" class="majorbox";>
    <div class="menubox">
        <button class="menubtn" onclick="turnto_up()">数据上传</button>
        <button class="menubtn" onclick="turnto_sec()">数据检索</button>
        <%--<button class="menubtn" onclick="turnto_down()">数据下载</button>--%>
        <button class="menubtn" onclick="turnto_down()">任务界面</button>
    </div>


    <div id="user-box" class="user-box">
        <header  class="upload-title" style=" font-size:x-large ;">用户信息</header>
        <div >
            <div name="upload-data-info" class="upload-data-info" >
                <label >用户账号:</label>
                <input type='text' id='datatext1' name='userid' disabled="true"  style="width: 25%" value=<%=getUser_id() %>>
            </div>
            <div name="upload-data-info" class="upload-data-info">
                <label >用户姓名:</label>
                <input type='text' id='datatext2' name='username' style="width: 25%" value= <%=getUser_name() %>>
            </div>
            <div id="datatext" name="upload-data-info" class="upload-data-info">
                <label >用户类型:</label>
                <input type='text' id='datatext7' name='usertype' disabled="true" style="width: 25%" value= <%=getUsertype() %>>
            </div>
            <div name="upload-data-info" class="upload-data-info">
                <label >登录密码:</label>
                <input type='text' id='datatext3' name='password' style="width: 25%" value= <%=getPassword() %>>
            </div>
            <div name="upload-data-info" class="upload-data-info">
                <label >注册时间:</label>
                <input type='text' id='datatext4' name='zctime' disabled="true" style="width: 25%" value= <%=getZctime() %>>
            </div>
            <div name="upload-data-info" class="upload-data-info">
                <label >上传文件数量:</label>
                <input type='text' id='datatext5' name='upload' disabled="true" style="width: 25%" value= <%=getScl() %>>
            </div>
            <div name="upload-data-info" class="upload-data-info">
                <label >下载文件数量:</label>
                <input type='text' id='datatext6' name='download' disabled="true" style="width: 25%" value= <%=getXzl() %>>
            </div>

            <div name="upload-data-info" class="upload-data-info">
                <label >任务报酬</label>
                <input type='text' id='rwpqy' name='rwpqy' style="width: 25%" value= <%=getRwpay() %>>
            </div>
            <div name="upload-data-info" class="upload-data-info">
                <button type="submit" id="databianji'" onclick="showbox()">
                    重新编辑
                </button>
            </div>
        </div>
    </div>

    <div id="friend-box" class="friend-box">
        <div id="friendinfo" class="friendinfo">
            <div class="panel-body" style="height: 40%; overflow-y:scroll">
                <div style="border: 1px  #000000; width: 90%; margin: 0 auto;">
                    <header style="margin-left: 0.5%;font-size:larger">好友列表</header>
                    <table>
                        <title>数据检索列表</title>
                        <tr>
                            <th>ID</th>
                            <th>数据名</th>
                        </tr>
                        <c:forEach var="U" items="${userAll}"  >
                            <tr> <td><input type="text" value="${U.friendid}"  name="friend_id" ></td>
                                <td><input type="text" value="${U.friendname}"  name="friend_name"></td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </div>

        <div id="findfriend-box" class="findfriend-box">
            <header style="margin-left: 0.5%;font-size:larger">用户查询</header>
            <form action="FriendSearchServlet" method="get" enctype="multipart/form-data">
                <div name="upload-data-info" class="upload-data-info2" >
                    <label for="friendid" >账号:</label>
                    <input type='text' id='friendid' name='friendid' style="width: 25%" value=<%=getFriendid()%>>
                    <button type="submit" id="btnchaxun'">
                        查询用户
                    </button>
                </div>
            </form>
            <form action="FriendSearchServlet" method="post" enctype="multipart/form-data">
                <div name="upload-data-info" class="upload-data-info2">
                    <label for="friendname">用户名:</label>
                    <input type='text' id='friendname' name='friendname' style="width: 25%" value=<%=getFriendname()%>>
                    <button type="submit" id="btnqingqiu'">
                        添加好友
                    </button>
                </div>
            </form>

        </div>

        <div id="addfriend-box" class="addfriend-box">
            <div class="panel-body" style="height: 40%; overflow-y:scroll">
                <div style="border: 1px  #000000; width: 90%; margin: 0 auto;">
                    <header style="margin-left: 0.5%;font-size:larger">添加好友</header>
                    <table>
                        <title>数据检索列表</title>
                        <tr>
                            <th>ID</th>
                            <th>数据名</th>
                            <th>操作</th>
                        </tr>
                        <c:forEach var="U" items="${userNew}"  >
                            <form action="FriendAddServlet" method="get" enctype="multipart/form-data">
                                <tr> <td><input type="text" value="${U.friendid}"  name="friendid" ></td>
                                    <td><input type="text" value="${U.friendname}" name="friendname"></td>
                                    <td><input type="submit" value="同意" size="5"></td>
                                </tr>
                            </form>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </div>
    </div>


</div>


<script>
    function turnto_up() {

        window.location.href="major.jsp";

    }
    function turnto_sec() {
        window.location.href="search.jsp";
    }

    function turnto_down() {

        window.location.href="GIS.jsp";

    }

    function turnto_gis() {
        window.location.href="test.html";
    }

    function showbox() {
        document.getElementById("datatext").style.display="block";
    }

</script>
</body>
</html>
