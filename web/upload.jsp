<%@ page import="static test.vo.User.*" %><%--
  Created by IntelliJ IDEA.
  User: lai
  Date: 2019/4/22
  Time: 19:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="test.service.GetFriendInfo" %>
<%@ page import="test.vo.DataClass" %>
<%@ page import="test.service.Upload" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>地理大数据共享云平台</title>
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
    .
    }
    .menubox{
        margin-top: 0%;
        background-size: cover;
        margin: 0 auto;
        position: relative;
        width: 100%;
        height: 3%;
        border:2px solid #929799;
    }
    .menuitm{
        margin-top: 0%;
        float: left;
        width: 5%;
        height: 3%;
        padding-left: 0px;
    }
    .uploadbox{
        margin-top: 0.1%;
        float: left;
        background-size: cover;
        width: 30%;
        height: 88%;
        border:1px solid #929799;
    }
    .upload-title {
        margin-top: 1%;
        margin-left: 10%;
    }
    .upload-data-info{
        margin-top: 2%;
        margin-left: 10%;
    }

    .data-box{
        margin-top: 0.1%;
        float: right;
        width: 69.5%;
        height:88%;
        border:1px solid #929799;
    }
</style>
<%!int len=0;DataClass a; %>
<div id="majorbox" class="majorbox";>
    <div class="menubox">
        <button class="menubtn" onclick="turnto_up()">数据上传</button>
        <button class="menubtn" onclick="turnto_sec()">数据检索</button>
        <button class="menubtn" onclick="turnto_gis()">任务界面</button>
    </div>
    <div id="datauploadbox" class="uploadbox" style="display: block">
            <header  class="upload-title" style=" font-size:x-large ;">上传数据信息表</header>
            <div >
                <div name="upload-data-info" class="upload-data-info" >
                    <label for="datatype" >数据类型:</label>
                    <input type='text' id='datatype' name='locality' disabled="true" value= <%=getType() %> >
                </div>
                <div name="upload-data-info" class="upload-data-info">
                    <label >区域名称:</label>
                    <input type='text' id='datatext2' name='locality' disabled="true" value= <%=getLocality() %>>
                </div>
                <div name="upload-data-info" class="upload-data-info">
                    <label >采集时间:</label>
                    <input type='text' id='datatext3' name='time' disabled="true" value= <%=getCxtime()%>>
                </div>
                <div name="upload-data-info" class="upload-data-info">
                    <label >西北点经纬:</label>
                    <input type='text' id='dataWN' name='westnorth' disabled="true" value= <%=getWn_coord()%>>
                </div>
                <div name="upload-data-info" class="upload-data-info">
                    <label >东南点经纬:</label>
                    <input type='text' id='dataES' name='eastsouth'disabled="true"  value= <%=getEs_coord()%>>
                </div>
            </div>

            <div name="upload-data-info" class="upload-data-info">
                <button type="submit" id="databianji'" onclick="turnto_up()">
                    重新编辑
                </button>
            </div>
        <form action="UploadServlet"  method="post" enctype="multipart/form-data" >
            <div name="upload-data-info"  class="upload-data-info" id="btnupload" style="display: block">
                <input type="file" id="datatext5" name="file" size="10">
                <input type="submit" id="datatext6"  value="上传文件" onclick="btnClose()">
            </div>
        </form>

    </div>

    <div id="data-box" class="data-box">
        <div class="panel-body" style="height: 100%; overflow-y:scroll">
            <div style="border: 1px  #000000; width: 90%; margin: 0 auto;">
                <header style="margin-left: 30%;font-size:larger">已上传数据列表</header>
                <table>
                    <title>数据检索列表</title>
                    <tr>
                        <th>ID</th>
                        <th>数据名</th>
                        <th>区域</th>
                        <th>大小</th>
                        <th>类型</th>
                        <th>时间</th>
                        <th>西北点</th>
                        <th>东南点</th>
                        <th>操作</th>
                        <th>操作</th>
                    </tr>
                    <tr> <%
                        len= Upload.Uploadinfo().size();
                        for(int i=0;i<len;i++){
                            a=new DataClass();a= (DataClass) Upload.Uploadinfo().get(i);
                            out.print("<form action=\"DelectServlet\" method=\"get\" enctype=\"multipart/form-data\">");
                            out.print(" <tr>");
                            out.print("<td><input type=\"text\" value="+a.getSj_id()+" id=\"sj_id\"  name=\"sj_id\" ></td>");
                            out.print(" <td><input type=\"text\" value="+a.getSj_name()+"  name=\"sj_name\"></td>");
                            out.print("  <td><input type=\"text\" value="+a.getLocality()+" name=\"locality\"></td>");
                            out.print("<td><input type=\"text\" value="+a.getSize()+"  name=\"size\" ></td>");
                            out.print(" <td><input type=\"text\" value="+a.getType()+"  name=\"type\"></td>");
                            out.print("  <td><input type=\"text\" value="+a.getCxtime()+" name=\"cxtime\"></td>");
                            out.print(" <td><input type=\"text\" value="+a.getWn_coord()+"  name=\"wn_coord\"></td>");
                            out.print("  <td><input type=\"text\" value="+a.getEs_coord()+" name=\"es_coord\"></td>");
                            out.print("<td><input type=\"submit\" value=\"删除\" size=\"5\" ></td>");
                            out.print("<td><input type=\"button\" value=\"预览\" size=\"5\" onclick=\"showrec('${U.wn_coord}','${U.es_coord}')\"></td>");
                            out.print(" </tr>");
                            out.print(" </form>");
                        }
                    %>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</div>

</div>
<script type="text/javascript">
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


    function btnup() {
        document.getElementById("btnupload").style.display="block";
    }
    function btnClose() {
        document.getElementById("btnupload").style.display="none";
    }

</script>
</body>
</html>
