<%@ page import="test.vo.User" %>
<%@ page import="static test.vo.User.*" %><%--
  Created by IntelliJ IDEA.
  User: lai
  Date: 2019/4/22
  Time: 19:16
  To change this template use File | Settings | File Templates.

--%>

<%@ page import=" java.io.IOException" %>
<%@ page import=" java.util.Timer " %>
<%@ page import=" test.dao.Search" %>
<%@ page import="test.vo.DataClass" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.io.IOException" %>
<%@ page import="test.dao.Task" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>GIS</title>
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

    .uploadbox1{
        margin-top: 0.1%;
        float: left;
        background-size: cover;
        width: 50%;
        height: 86%;
        border:1px solid #99416a;
    }
    .already_send_table{

        margin-top: 1%;
        float: left;
        background-size: cover;
        width: 100%;
        height: 93%;
        border:1px solid #929799;
        overflow-x: scroll;
    }
    .sendbox{
        margin-top: 0.5%;
        float: right;
        width: 49%;
        height: 20%;
        border:1px solid #929799;

    }

    .menubtn{
     background-color: #cccccc;

    }

    .taskSend-input{
        float: left;
        margin-left: 0.5%;
        margin-top: 1%;
    }
</style>
<%!int len=0;DataClass a; %>
<div id="majorbox" class="majorbox";>
    <div class="menubox">
        <button class="menubtn" onclick="turnto_up()">数据上传</button>
        <button class="menubtn" onclick="turnto_sec()">数据检索</button>
        <%--<button class="menubtn" onclick="turnto_down()">数据下载</button>--%>
        <button class="menubtn" onclick="turnto_down()">任务界面</button>
    </div>
    <div class="menubox">
        <button class="menubtn" id="turnto_per" onclick="turnto_per()">个人发布</button>
        <button class="menubtn" id="turnto_all" onclick="turnto_all()">个人申请</button>
        <button class="menubtn" id="turnto_auto" onclick="turnto_auto()">自动调配</button>
        <label>用户：</label><label><%=getUser_id()%></label>
    </div>
    <div id="sendTaskcenter" class="uploadbox1" style="display: block">

        <header style="margin-left: 30%;font-size:x-large;" >个人发布任务中心</header>
        <div >
            <button class="menubtn" id="turnto_already_send" onclick="turnto_already_send()">已发布</button>
            <button class="menubtn" id="turnto_wait_anpai"  onclick="turnto_wait_anpai()">待审核</button>
            <button class="menubtn" id="turnto_ing"  onclick="turnto_ing()">进行中</button>
            <button class="menubtn" id="turnto_wait_recive"  onclick="turnto_wait_recive()">待查收</button>
            <button class="menubtn" id="turnto_wait_pingJia"  onclick="turnto_wait_pingJia()">待评价</button>
            <button class="menubtn" id="turnto_already_finish"  onclick="turnto_already_finish()">已完成</button>
        </div>

        <div id="already_send" class="already_send_table" style="display:none" >
            <table align="center" id="already_send_table">
                <title>个人发布任务列表</title>
                <tr>
                    <th>ID</th>
                    <th>任务名称</th>
                    <th>技能要求</th>
                    <th>报酬</th>
                    <th>任务描述</th>
                    <th>Url</th>
                </tr>
                <tr>

                        <tr>

                <%
               len= Task.putValue_0().size();
                for(int i=0;i<len;i++){
                a=new DataClass();a= (DataClass) Task.putValue_0().get(i);
                    out.print(" <tr>");
                    out.print("<td><input type=\"text\" value="+a.getSj_id()+"  id=\"sj_id\"  name=\"sj_id\" ></td>");
                    out.print(" <td><input type=\"text\" value="+a.getRw_name()+"  name=\"rw_name\"></td>");
                    out.print("  <td><input type=\"text\" value="+a.getRwskill()+" name=\"rwskill\"></td>");
                    out.print("<td><input type=\"text\" value="+a.getPay()+"  name=\"pay\" ></td>");
                    out.print(" <td><input type=\"text\" value="+a.getDescri()+" name=\"descri\"></td>");
                    out.print("  <td><input type=\"text\" value="+a.getUrl()+" name=\"url\"></td>");
                    out.print("<td><input type=\"button\" value=\"预览\" size=\"5\" onclick=\"showrec('${U.wn_coord}','${U.es_coord}')\"></td>");
                    out.print(" </tr>");
                }
                %>
                        </tr>
            </table>
        </div>
        <div id="waitZhipai" class="already_send_table" style="display:none">
            <table align="center">
                <title>待指派任务列表</title>
                <tr>
                    <th>ID</th>
                    <th>任务名称</th>
                    <th>技能要求</th>
                    <th>报酬</th>
                    <th>任务描述</th>
                    <th>Url</th>
                </tr>
                <tr>

                <tr>

                    <%
                        len= Task.putValue_1().size();
                        for(int i=0;i<len;i++){
                            a=new DataClass();a= (DataClass) Task.putValue_1().get(i);
                            out.print(" <form action=\"UserInfoServlet\" method=\"get\" enctype=\"multipart/form-data\">");
                            out.print(" <tr>");
                            out.print("<td><input type=\"text\" value="+a.getSj_id()+"  id=\"sj_id\"  name=\"sj_id\" ></td>");
                            out.print(" <td><input type=\"text\" value="+a.getRw_name()+"  name=\"rw_name\"></td>");
                            out.print("  <td><input type=\"text\" value="+a.getRwskill()+" name=\"rwskill\"></td>");
                            out.print("<td><input type=\"text\" value="+a.getPay()+"  name=\"pay\" ></td>");
                            out.print(" <td><input type=\"text\" value="+a.getDescri()+" name=\"descri\"></td>");
                            out.print("  <td><input type=\"text\" value="+a.getUrl()+" name=\"url\"></td>");
                            out.print("<td><input type=\"submit\" value=\"指派\" size=\"5\" ></td>");
                            out.print(" </tr>");
                            out.print(" </form>");
                        }
                    %>
                </tr>
            </table>
        </div>
        <div id="ingTask" class="already_send_table" style="display:none">
            <table align="center">
                <title>进行中任务列表</title>
                <tr>
                    <th>ID</th>
                    <th>任务名称</th>
                    <th>技能要求</th>
                    <th>报酬</th>
                    <th>任务描述</th>
                    <th>Url</th>
                    <th>负责人</th>
                </tr>
                <tr>

                <tr>

                    <%
                        len= Task.putValue_2().size();
                        for(int i=0;i<len;i++){
                            a=new DataClass();a= (DataClass) Task.putValue_2().get(i);
                            out.print(" <tr>");
                            out.print("<td><input type=\"text\" value="+a.getSj_id()+"  id=\"sj_id\"  name=\"sj_id\" ></td>");
                            out.print(" <td><input type=\"text\" value="+a.getRw_name()+"  name=\"rw_name\"></td>");
                            out.print("  <td><input type=\"text\" value="+a.getRwskill()+" name=\"rwskill\"></td>");
                            out.print("<td><input type=\"text\" value="+a.getPay()+"  name=\"pay\" ></td>");
                            out.print(" <td><input type=\"text\" value="+a.getDescri()+" name=\"descri\"></td>");
                            out.print("  <td><input type=\"text\" value="+a.getUrl()+" name=\"url\"></td>");
                            out.print("  <td><input type=\"text\" value="+a.getReceiver()+" name=\"receiver\"></td>");
                            out.print(" </tr>");
                        }
                    %>
                </tr>
            </table>
        </div>
        <div id="waitRecive" class="already_send_table" style="display:none">
            <table align="center">
                <title>待查收任务列表</title>
                <tr>
                    <th>ID</th>
                    <th>任务名称</th>
                    <th>技能要求</th>
                    <th>报酬</th>
                    <th>任务描述</th>
                    <th>Url</th>
                    <th>负责人</th>
                </tr>
                <tr>
                <tr>
                    <%
                        len= Task.putValue_3().size();
                        for(int i=0;i<len;i++){
                            a=new DataClass();a= (DataClass) Task.putValue_3().get(i);
                            out.print(" <form action=\"DownServlet\" method=\"get\" enctype=\"multipart/form-data\">");
                            out.print(" <tr>");
                            out.print("<td><input type=\"text\" value="+a.getSj_id()+"  id=\"sj_id\"  name=\"sj_id\" ></td>");
                            out.print(" <td><input type=\"text\" value="+a.getRw_name()+"  name=\"rw_name\"></td>");
                            out.print("  <td><input type=\"text\" value="+a.getRwskill()+" name=\"rwskill\"></td>");
                            out.print("<td><input type=\"text\" value="+a.getPay()+"  name=\"pay\" ></td>");
                            out.print(" <td><input type=\"text\" value="+a.getDescri()+" name=\"descri\"></td>");
                            out.print("  <td><input type=\"text\" value="+a.getUrl()+" name=\"url\"></td>");
                            out.print("  <td><input type=\"text\" value="+a.getReceiver()+" name=\"receiver\"></td>");
                            out.print("<td><input type=\"submit\" value=\"接收\" size=\"5\" ></td>");
                            out.print(" </tr>");
                            out.print(" </form>");
                        }
                    %>
                </tr>
            </table>
        </div>
        <div id="waitPingjia" class="already_send_table" style="display:none">
            <table align="center">
                <title>待评价任务列表</title>
                <tr>
                    <th>ID</th>
                    <th>任务名称</th>
                    <th>技能要求</th>
                    <th>报酬</th>
                    <th>任务描述</th>
                    <th>Url</th>
                    <th>负责人</th>
                </tr>
                <tr>

                <tr>

                    <%
                        len= Task.putValue_4().size();
                        for(int i=0;i<len;i++){
                            a=new DataClass();a= (DataClass) Task.putValue_4().get(i);
                            out.print(" <form action=\"TaskPingjiaServlet\" method=\"get\" enctype=\"multipart/form-data\">");
                            out.print(" <tr>");
                            out.print("<td><input type=\"text\" value="+a.getSj_id()+"  id=\"sj_id\"  name=\"sj_id\" ></td>");
                            out.print(" <td><input type=\"text\" value="+a.getRw_name()+"  name=\"rw_name\"></td>");
                            out.print("  <td><input type=\"text\" value="+a.getRwskill()+" name=\"rwskill\"></td>");
                            out.print("<td><input type=\"text\" value="+a.getPay()+"  name=\"pay\" ></td>");
                            out.print(" <td><input type=\"text\" value="+a.getDescri()+" name=\"descri\"></td>");
                            out.print("  <td><input type=\"text\" value="+a.getUrl()+" name=\"url\"></td>");
                            out.print("  <td><input type=\"text\" value="+a.getReceiver()+" name=\"receiver\"></td>");
                            out.print("  <td> <select name=\"dafen\"> <option>选择得分</option>  <option>5</option>   <option>4</option>   <option>3</option>   <option>2</option>   <option>1</option></select></td>");
                            out.print("<td><input type=\"submit\" value=\"评价\" size=\"5\" ></td>");
                            out.print(" </tr>");
                            out.print(" </form >");
                        }
                    %>
                </tr>
            </table>
        </div>
        <div id="finishTask" class="already_send_table" style="display:none">
            <table align="center">
                <title>已完成任务列表</title>
                <tr>
                    <th>ID</th>
                    <th>任务名称</th>
                    <th>技能要求</th>
                    <th>报酬</th>
                    <th>任务描述</th>
                    <th>Url</th>
                    <th>负责人</th>
                    <th>评分</th>
                </tr>
                <tr>

                <tr>

                    <%
                        len= Task.putValue_5().size();
                        for(int i=0;i<len;i++){
                            a=new DataClass();a= (DataClass) Task.putValue_5().get(i);
                            out.print(" <tr>");
                            out.print("<td><input type=\"text\" value="+a.getSj_id()+"  id=\"sj_id\"  name=\"sj_id\" ></td>");
                            out.print(" <td><input type=\"text\" value="+a.getRw_name()+"  name=\"rw_name\"></td>");
                            out.print("  <td><input type=\"text\" value="+a.getRwskill()+" name=\"rwskill\"></td>");
                            out.print("<td><input type=\"text\" value="+a.getPay()+"  name=\"pay\" ></td>");
                            out.print(" <td><input type=\"text\" value="+a.getDescri()+" name=\"descri\"></td>");
                            out.print("  <td><input type=\"text\" value="+a.getUrl()+" name=\"url\"></td>");
                            out.print("  <td><input type=\"text\" value="+a.getReceiver()+" name=\"receiver\"></td>");
                            out.print("  <td><input type=\"text\" value="+a.getDafen()+" name=\"dafen\"></td>");
                            out.print(" </tr>");
                        }
                    %>
                </tr>
            </table>
        </div>

    </div>
    <div id="applyTaskcenter" class="uploadbox1" style="display: none">

        <header style="margin-left: 30%;font-size:x-large;" >个人申请任务中心</header>
        <div >
            <button class="menubtn" id="turnto_taskStore" onclick="turnto_taskStore()">任务商店</button>
            <button class="menubtn" id="turnto_wait_anpai1"  onclick="turnto_wait_anpai1()">待审核</button>
            <button class="menubtn" id="turnto_ing1"  onclick="turnto_ing1()">进行中</button>
            <%--<button class="menubtn" onclick="turnto_wait_sub()">待提交</button>--%>
            <button class="menubtn" id="turnto_already_finish1"  onclick="turnto_already_finish1()">已完成</button>
        </div>
        <div id="taskStore" class="already_send_table" style="display: none">
            <table align="center">
                <title>商店任务列表</title>
                <tr>
                    <th>ID</th>
                    <th>任务名称</th>
                    <th>技能要求</th>
                    <th>报酬</th>
                    <th>任务描述</th>
                    <th>Url</th>
                    <th>发布人</th>
                </tr>
                <tr>
                <tr>
                    <%
                        len= Task.getValue_0().size();
                        for(int i=0;i<len;i++){
                            a=new DataClass();a= (DataClass) Task.getValue_0().get(i);
                            out.print(" <form action=\"TaskShenqingServlet\" method=\"get\" enctype=\"multipart/form-data\">");
                            out.print(" <tr>");
                            out.print("<td><input type=\"text\" value="+a.getSj_id()+"  id=\"sj_id\"  name=\"sj_id\" ></td>");
                            out.print(" <td><input type=\"text\" value="+a.getRw_name()+"  name=\"rw_name\"></td>");
                            out.print("  <td><input type=\"text\" value="+a.getRwskill()+" name=\"rwskill\"></td>");
                            out.print("<td><input type=\"text\" value="+a.getPay()+"  name=\"pay\" ></td>");
                            out.print(" <td><input type=\"text\" value="+a.getDescri()+" name=\"descri\"></td>");
                            out.print("  <td><input type=\"text\" value="+a.getUrl()+" name=\"url\"></td>");
                            out.print("  <td><input type=\"text\" value="+a.getFaburen()+" name=\"faburen\"></td>");
                            out.print("<td><input type=\"button\" value=\"预览\" size=\"5\" onclick=\"showrec('${U.wn_coord}','${U.es_coord}')\"></td>");
                            out.print("<td><input type=\"submit\" value=\"申请\" size=\"5\" ></td>");
                            out.print(" </tr>");
                            out.print(" </form>");
                        }
                    %>
                </tr>
            </table>
        </div>
        <div id="waitZhipai1" class="already_send_table" style="display:none">
            <table align="center">
                <title>待审核任务列表</title>
                <tr>
                    <th>ID</th>
                    <th>任务名称</th>
                    <th>技能要求</th>
                    <th>报酬</th>
                    <th>任务描述</th>
                    <th>Url</th>
                    <th>发布人</th>
                </tr>
                <tr>

                <tr>

                    <%
                        len= Task.getValue_1().size();
                        for(int i=0;i<len;i++){
                            a=new DataClass();a= (DataClass) Task.getValue_1().get(i);
                            out.print(" <tr>");
                            out.print("<td><input type=\"text\" value="+a.getSj_id()+"  id=\"sj_id\"  name=\"sj_id\" ></td>");
                            out.print(" <td><input type=\"text\" value="+a.getRw_name()+"  name=\"rw_name\"></td>");
                            out.print("  <td><input type=\"text\" value="+a.getRwskill()+" name=\"rwskill\"></td>");
                            out.print("<td><input type=\"text\" value="+a.getPay()+"  name=\"pay\" ></td>");
                            out.print(" <td><input type=\"text\" value="+a.getDescri()+" name=\"descri\"></td>");
                            out.print("  <td><input type=\"text\" value="+a.getUrl()+" name=\"url\"></td>");
                            out.print("  <td><input type=\"text\" value="+a.getFaburen()+" name=\"faburen\"></td>");
                            out.print(" </tr>");
                        }
                    %>
                </tr>
            </table>
        </div>
        <div id="ingTask1" class="already_send_table" style="display: none">
            <table align="center">
                <title>进行中任务列表</title>
                <tr>
                    <th>ID</th>
                    <th>任务名称</th>
                    <th>技能要求</th>
                    <th>报酬</th>
                    <th>任务描述</th>
                    <th>Url</th>
                    <th>发布人</th>
                </tr>
                <tr>

                <tr>

                    <%
                        len= Task.getValue_2().size();
                        for(int i=0;i<len;i++){
                            a=new DataClass();a= (DataClass) Task.getValue_2().get(i);
                            out.print(" <form action=\"DownServlet\" method=\"get\" enctype=\"multipart/form-data\">");
                            out.print(" <tr>");
                            out.print("<td><input type=\"text\" value="+a.getSj_id()+"  id=\"sj_id\"  name=\"sj_id\" ></td>");
                            out.print(" <td><input type=\"text\" value="+a.getRw_name()+"  name=\"rw_name\"></td>");
                            out.print("  <td><input type=\"text\" value="+a.getRwskill()+" name=\"rwskill\"></td>");
                            out.print("<td><input type=\"text\" value="+a.getPay()+"  name=\"pay\" ></td>");
                            out.print(" <td><input type=\"text\" value="+a.getDescri()+" name=\"descri\"></td>");
                            out.print("  <td><input type=\"text\" value="+a.getUrl()+" name=\"url\"></td>");
                            out.print("  <td><input type=\"text\" value="+a.getFaburen()+" name=\"faburen\"></td>");
                            out.print("<td><input type=\"submit\" value=\"下载\" size=\"5\" ></td>");
                            out.print("<td><input type=\"button\" value=\"提交\" size=\"5\" onclick=\"tijiao('"+a.getSj_id()+"','"+a.getRw_name()+"')\"></td>");
                            out.print(" </tr>");
                            out.print(" </form>");
                        }
                    %>
                </tr>
            </table>
        </div>
        <div id="finishTask1" class="already_send_table" style="display: none">
            <table align="center">
                <title>已完成任务列表</title>
                <tr>
                    <th>ID</th>
                    <th>任务名称</th>
                    <th>技能要求</th>
                    <th>报酬</th>
                    <th>任务描述</th>
                    <th>Url</th>
                    <th>发布人</th>
                    <th>评分</th>
                </tr>
                <tr>

                <tr>

                    <%
                        len= Task.getValue_3().size();
                        for(int i=0;i<len;i++){
                            a=new DataClass();a= (DataClass) Task.getValue_3().get(i);
                            out.print(" <tr>");
                            out.print("<td><input type=\"text\" value="+a.getSj_id()+"  id=\"sj_id\"  name=\"sj_id\" ></td>");
                            out.print(" <td><input type=\"text\" value="+a.getRw_name()+"  name=\"rw_name\"></td>");
                            out.print("  <td><input type=\"text\" value="+a.getRwskill()+" name=\"rwskill\"></td>");
                            out.print("<td><input type=\"text\" value="+a.getPay()+"  name=\"pay\" ></td>");
                            out.print(" <td><input type=\"text\" value="+a.getDescri()+" name=\"descri\"></td>");
                            out.print("  <td><input type=\"text\" value="+a.getUrl()+" name=\"url\"></td>");
                            out.print("  <td><input type=\"text\" value="+a.getFaburen()+" name=\"faburen\"></td>");
                            out.print("  <td><input type=\"text\" value="+a.getDafen()+" name=\"dafen\"></td>");
                            out.print(" </tr>");
                        }
                    %>
                </tr>
            </table>
        </div>

    </div>
    <div id="autoTaskcenter" class="uploadbox1" style="display: none">

        <header style="margin-left: 30%;font-size:x-large;" >自动调配任务中心</header>
        <div >
            <button class="menubtn" id="turnto_waitLingqu"  onclick="turnto_waitLingqu()">待领取</button>
            <button class="menubtn" id="turnto_ing2"  onclick="turnto_ing2()">进行中</button>
            <button class="menubtn" id="turnto_already_finish2"  onclick="turnto_already_finish2()">已完成</button>
        </div>
        <div id="waitLingqu" class="already_send_table" style="display:none">
            <table align="center">
                <title>待领取任务列表</title>
                <tr>
                    <th>ID</th>
                    <th>任务名称</th>
                    <th>技能要求</th>
                    <th>报酬</th>
                    <th>任务描述</th>
                    <th>Url</th>
                    <th>发布人</th>
                </tr>
                <tr>
                <tr>
                    <%
                        len= Task.autoValue_1().size();
                        for(int i=0;i<len;i++){
                            a=new DataClass();a= (DataClass) Task.autoValue_1().get(i);
                            out.print(" <form action=\"TaskLingquServlet\" method=\"get\" enctype=\"multipart/form-data\">");
                            out.print(" <tr>");
                            out.print("<td><input type=\"text\" value="+a.getSj_id()+"  id=\"sj_id\"  name=\"sj_id\" ></td>");
                            out.print(" <td><input type=\"text\" value="+a.getRw_name()+"  name=\"rw_name\"></td>");
                            out.print("  <td><input type=\"text\" value="+a.getRwskill()+" name=\"rwskill\"></td>");
                            out.print("<td><input type=\"text\" value="+a.getPay()+"  name=\"pay\" ></td>");
                            out.print(" <td><input type=\"text\" value="+a.getDescri()+" name=\"descri\"></td>");
                            out.print("  <td><input type=\"text\" value="+a.getUrl()+" name=\"url\"></td>");
                            out.print("  <td><input type=\"text\" value="+a.getFaburen()+" name=\"faburen\"></td>");
                            out.print("<td><input type=\"submit\" value=\"领取\" size=\"5\" ></td>");
                            out.print(" </tr>");
                        }
                    %>
                </tr>
            </table>
        </div>
        <div id="ingTask2" class="already_send_table" style="display: none">
            <table align="center">
                <title>进行中任务列表</title>
                <tr>
                    <th>ID</th>
                    <th>任务名称</th>
                    <th>技能要求</th>
                    <th>报酬</th>
                    <th>任务描述</th>
                    <th>Url</th>
                    <th>发布人</th>
                </tr>
                <tr>

                <tr>

                    <%
                        len= Task.autoValue_2().size();
                        for(int i=0;i<len;i++){
                            a=new DataClass();a= (DataClass) Task.autoValue_2().get(i);
                            out.print(" <form action=\"DownServlet\" method=\"get\" enctype=\"multipart/form-data\">");
                            out.print(" <tr>");
                            out.print("<td><input type=\"text\" value="+a.getSj_id()+"  id=\"sj_id\"  name=\"sj_id\" ></td>");
                            out.print(" <td><input type=\"text\" value="+a.getRw_name()+"  name=\"rw_name\"></td>");
                            out.print("  <td><input type=\"text\" value="+a.getRwskill()+" name=\"rwskill\"></td>");
                            out.print("<td><input type=\"text\" value="+a.getPay()+"  name=\"pay\" ></td>");
                            out.print(" <td><input type=\"text\" value="+a.getDescri()+" name=\"descri\"></td>");
                            out.print("  <td><input type=\"text\" value="+a.getUrl()+" name=\"url\"></td>");
                            out.print("  <td><input type=\"text\" value="+a.getFaburen()+" name=\"faburen\"></td>");
                            out.print("<td><input type=\"submit\" value=\"下载\" size=\"5\" ></td>");
                            out.print("<td><input type=\"button\" value=\"提交\" size=\"5\" onclick=\"tijiao('"+a.getSj_id()+"','"+a.getRw_name()+"')\"></td>");
                            out.print(" </tr>");
                            out.print(" </form>");
                        }
                    %>
                </tr>
            </table>
        </div>
        <div id="finishTask2" class="already_send_table" style="display: none">
            <table align="center">
                <title>已完成任务列表</title>
                <tr>
                    <th>ID</th>
                    <th>任务名称</th>
                    <th>技能要求</th>
                    <th>报酬</th>
                    <th>任务描述</th>
                    <th>Url</th>
                    <th>发布人</th>
                    <th>评分</th>
                </tr>
                <tr>

                <tr>

                    <%
                        len= Task.autoValue_3().size();
                        for(int i=0;i<len;i++){
                            a=new DataClass();a= (DataClass) Task.autoValue_3().get(i);
                            out.print(" <tr>");
                            out.print("<td><input type=\"text\" value="+a.getSj_id()+"  id=\"sj_id\"  name=\"sj_id\" ></td>");
                            out.print(" <td><input type=\"text\" value="+a.getRw_name()+"  name=\"rw_name\"></td>");
                            out.print("  <td><input type=\"text\" value="+a.getRwskill()+" name=\"rwskill\"></td>");
                            out.print("<td><input type=\"text\" value="+a.getPay()+"  name=\"pay\" ></td>");
                            out.print(" <td><input type=\"text\" value="+a.getDescri()+" name=\"descri\"></td>");
                            out.print("  <td><input type=\"text\" value="+a.getUrl()+" name=\"url\"></td>");
                            out.print("  <td><input type=\"text\" value="+a.getFaburen()+" name=\"faburen\"></td>");
                            out.print("  <td><input type=\"text\" value="+a.getDafen()+" name=\"dafen\"></td>");
                            out.print(" </tr>");
                        }
                    %>
                </tr>
            </table>
        </div>
    </div>

    <div id="sendtaskbox" class="sendbox" style="height: 30%">
        <header style="margin-left:40%;font-size:large;" >任务发布</header>
        <form action="TaskFabuServlet"  method="get" enctype="multipart/form-data" >
            <div class="taskSend-input">
                <label>任务名称</label><input type="text" name="taskname" value=<%=getRw_name()%> >
            </div>

            <div class="taskSend-input">
                <label>  数据所在区域</label><input type="text" name="locality" value=<%=getLocality()%>>
            </div>
            <div  class="taskSend-input" >
                <label>数据类型</label>
                <select name="datatype">
                    <option></option>
                    <option>栅格数据</option>
                    <option>矢量数据</option>
                </select>
            </div>
            <br>
            <div class="taskSend-input" >
                <label >采集时间</label><input type="text" name="time" value=<%=getCxtime()%>>
            </div>
            <div class="taskSend-input" >
                <label >西北经纬</label><input type="text" name="westnorth" value=<%=getWn_coord()%>>
            </div>
            <div class="taskSend-input" >
                <label >东南经纬</label><input type="text" name="eastsouth" value=<%=getEs_coord()%>>
            </div>
            <div class="taskSend-input" >
                 <label >URL</label><input type="text" name="in-dataUrl" value=<%=getUrl()%>>
            </div>
            <div class="taskSend-input" >
                <label >报酬</label><input type="text" name="money" value=<%=getPay()%>>
            </div>
            <div  class="taskSend-input" style="float: right;margin-right: 10%">
                <label>技能要求</label>
                <select name="skill">
                    <option>矢量分析</option>
                    <option>栅格分析</option>
                    <option>地形分析</option>
                    <option>网络分析</option>
                    <option>遥感处理</option>
                </select>
            </div>
            <div class="taskSend-input" style="float: left;margin-left: 1%;margin-top:0% ">
            <label >任务描述:</label>
                <textarea name="taskDescribe" style=" float: right;width: 100%;height: 20%" value=<%=getDescri()%>></textarea>
            </div>

            <div class="taskSend-input" style="margin-top:3%;">
                <input type="submit" name="tasksubmit" value="保存信息">
            </div>
        </form>
        <form action="TaskFabuServlet"  method="post" enctype="multipart/form-data" style="margin-top: 10%;margin-left: 50%">
            <div class="taskSend-input"  >
                <input type="file" id="datatext5" name="file" size="1">
            </div>
            <div class="taskSend-input" >
                <input type="submit" id="datatext6"  value="发布任务" onclick="btnClose()">
            </div>
        </form>
    </div>
    <div id="anpaiBox" class="sendbox" style="height: 35% ;overflow:scroll">
        <header style="margin-left:40%;font-size:large;" >任务指派</header>
        <div class="taskSend-input">
            <label>竞选任务ID：</label><input type="text" id="jinxuanID" name="taskname"  disabled="true" value=<%=getSj_id()%> >
        </div>
        <div class="taskSend-input" style="margin-left: 5%">
            <label>选择评价指标</label>
            <select>
                <option></option>
                <option>综合</option>
                <option>以往任务完成数量</option>
                <option>平均得分</option>
            </select>
            <input type="button" name="paixu" value="按指标排序">
        </div>
        <div style="float: left;margin-left: 0%;margin-top: 3%;">
        <table>
            <title>竞选列表</title>
            <tr>
                <th>竞选者ID</th>
                <th>个人能力</th>
                <th>进行中任务数</th>
                <th>历史完成数量</th>
                <th>个人评分</th>
                <th>综合得分</th>
            <tr>
            <tr>
            <c:forEach var="U" items="${userAll}" >
            <form action="TaskZhipaiServlet" method="get" enctype="multipart/form-data">
            <tr> <td><input type="text" value="${U.user_id}"  name="user_id" ></td>
                <td><input type="text" value="${U.skill}" id="skill" name="skill"></td>
                <td><input type="text" value="${U.rwwcl}" name="rwwcl"></td>
                <td><input type="text" value="${U.rwdql}" name="rwdql" ></td>
                <td><input type="text" value="${U.score}" name="score"></td>
                <td><input type="text" value="${U.zhscore}" name="zhscore" ></td>
                <td><input type="submit" value="指派" size="5"></td>
            </tr>
            </form>
            </c:forEach>
            </tr>
        </table>
        </div>
    </div>

    <div id="sunmittaskbox" class="sendbox" style="height: 15%">
        <header style="margin-left:40%;font-size:large;" >提交结果</header>
        <form action="TaskTijiaoServlet"  method="get" enctype="multipart/form-data" >
            <div class="taskSend-input">
                <label>ID</label><input type="text" id="resultid" name="dataID" value=<%=getSj_id()%>>
            </div>
            <div class="taskSend-input">
                <label>任务名称</label><input id="resultname" type="text" name="taskname"  value=<%=getRw_name()%>>
            </div>
            <div class="taskSend-input" >
                <label >结果URL</label><input type="text" name="in-dataUrl" value=<%=getUrl()%>>
            </div>
            <br>  <br>
            <div class="taskSend-input" >
                <input type="submit" name="tasksubmit" value="保存信息">
            </div>
        </form>
        <form action="TaskTijiaoServlet"  method="post" enctype="multipart/form-data" style="margin-top: -3%;margin-left: 50%">
            <div  class="taskSend-input">
                <input type="file" id="datatext7" name="file" size="5">
            </div>
            <div  class="taskSend-input">
                <input type="submit" id="datatext8"  value="提交结果" onclick="btnClose()">
            </div>
        </form>

    </div>

</div>






<script type="text/javascript">

    function tijiao(rw_id,rw_name){
        document.getElementById("resultid").value=rw_id
        document.getElementById("resultname").value=rw_name

    }

    function turnto_taskStore(){
        document.getElementById("waitZhipai1").style.display="none";
        document.getElementById("ingTask1").style.display="none";
        document.getElementById("finishTask1").style.display="none";
        document.getElementById("taskStore").style.display="block";
        document.getElementById("turnto_wait_anpai1").style.backgroundColor=" #cccccc";
        document.getElementById("turnto_ing1").style.backgroundColor=" #cccccc";
        document.getElementById("turnto_already_finish1").style.backgroundColor=" #cccccc";
        document.getElementById("turnto_taskStore").style.backgroundColor=" crimson";

    }
    function turnto_already_finish1(){
        document.getElementById("waitZhipai1").style.display="none";
        document.getElementById("ingTask1").style.display="none";
        document.getElementById("finishTask1").style.display="block";
        document.getElementById("taskStore").style.display="none";
        document.getElementById("turnto_wait_anpai1").style.backgroundColor=" #cccccc";
        document.getElementById("turnto_ing1").style.backgroundColor=" #cccccc";
        document.getElementById("turnto_already_finish1").style.backgroundColor=" crimson";
        document.getElementById("turnto_taskStore").style.backgroundColor=" #cccccc";

    }
    function turnto_ing1(){
        document.getElementById("waitZhipai1").style.display="none";
        document.getElementById("ingTask1").style.display="block";
        document.getElementById("finishTask1").style.display="none";
        document.getElementById("taskStore").style.display="none";
        document.getElementById("turnto_wait_anpai1").style.backgroundColor=" #cccccc";
        document.getElementById("turnto_ing1").style.backgroundColor=" crimson";
        document.getElementById("turnto_already_finish1").style.backgroundColor=" #cccccc";
        document.getElementById("turnto_taskStore").style.backgroundColor=" #cccccc";

    }
    function turnto_waitLingqu(){
        document.getElementById("waitLingqu").style.display="block";
        document.getElementById("ingTask2").style.display="none";
        document.getElementById("finishTask2").style.display="none";
        document.getElementById("turnto_waitLingqu").style.backgroundColor=" crimson";
        document.getElementById("turnto_ing2").style.backgroundColor=" #cccccc";
        document.getElementById("turnto_already_finish2").style.backgroundColor=" #cccccc";
    }
    function turnto_ing2(){
        document.getElementById("waitLingqu").style.display="none";
        document.getElementById("ingTask2").style.display="block";
        document.getElementById("finishTask2").style.display="none";
        document.getElementById("turnto_waitLingqu").style.backgroundColor=" #cccccc";
        document.getElementById("turnto_ing2").style.backgroundColor=" crimson";
        document.getElementById("turnto_already_finish2").style.backgroundColor=" #cccccc";
    }
    function turnto_already_finish2(){
        document.getElementById("waitLingqu").style.display="none";
        document.getElementById("ingTask2").style.display="none";
        document.getElementById("finishTask2").style.display="block";
        document.getElementById("turnto_waitLingqu").style.backgroundColor=" #cccccc";
        document.getElementById("turnto_ing2").style.backgroundColor=" #cccccc";
        document.getElementById("turnto_already_finish2").style.backgroundColor=" crimson";
    }

    function turnto_wait_anpai1(){
        document.getElementById("waitZhipai1").style.display="block";
        document.getElementById("ingTask1").style.display="none";
        document.getElementById("finishTask1").style.display="none";
        document.getElementById("taskStore").style.display="none";
        document.getElementById("turnto_wait_anpai1").style.backgroundColor=" crimson";
        document.getElementById("turnto_ing1").style.backgroundColor=" #cccccc";
        document.getElementById("turnto_already_finish1").style.backgroundColor=" #cccccc";
        document.getElementById("turnto_taskStore").style.backgroundColor=" #cccccc";

    }

    function turnto_already_send(){
        document.getElementById("waitZhipai").style.display="none";
        document.getElementById("ingTask").style.display="none";
        document.getElementById("waitRecive").style.display="none";
        document.getElementById("waitPingjia").style.display="none";
        document.getElementById("finishTask").style.display="none";
        document.getElementById("already_send").style.display="block";
        document.getElementById("turnto_wait_anpai").style.backgroundColor=" #cccccc";
        document.getElementById("turnto_ing").style.backgroundColor=" #cccccc";
        document.getElementById("turnto_wait_recive").style.backgroundColor=" #cccccc";
        document.getElementById("turnto_wait_pingJia").style.backgroundColor=" #cccccc";
        document.getElementById("turnto_already_finish").style.backgroundColor=" #cccccc";
        document.getElementById("turnto_already_send").style.backgroundColor=" crimson";
        //一下为从后端动态添加到前端表

    }




    function turnto_already_finish(){
        document.getElementById("waitZhipai").style.display="none";
        document.getElementById("ingTask").style.display="none";
        document.getElementById("waitRecive").style.display="none";
        document.getElementById("waitPingjia").style.display="none";
        document.getElementById("finishTask").style.display="block";
        document.getElementById("already_send").style.display="none";
        document.getElementById("turnto_wait_anpai").style.backgroundColor=" #cccccc";
        document.getElementById("turnto_ing").style.backgroundColor=" #cccccc";
        document.getElementById("turnto_wait_recive").style.backgroundColor=" #cccccc";
        document.getElementById("turnto_wait_pingJia").style.backgroundColor=" #cccccc";
        document.getElementById("turnto_already_finish").style.backgroundColor=" crimson";
        document.getElementById("turnto_already_send").style.backgroundColor=" #cccccc";

    }
    function turnto_wait_pingJia(){
        document.getElementById("waitZhipai").style.display="none";
        document.getElementById("ingTask").style.display="none";
        document.getElementById("waitRecive").style.display="none";
        document.getElementById("waitPingjia").style.display="block";
        document.getElementById("finishTask").style.display="none";
        document.getElementById("already_send").style.display="none";
        document.getElementById("turnto_wait_anpai").style.backgroundColor=" #cccccc";
        document.getElementById("turnto_ing").style.backgroundColor=" #cccccc";
        document.getElementById("turnto_wait_recive").style.backgroundColor=" #cccccc";
        document.getElementById("turnto_wait_pingJia").style.backgroundColor=" crimson";
        document.getElementById("turnto_already_finish").style.backgroundColor=" #cccccc";
        document.getElementById("turnto_already_send").style.backgroundColor=" #cccccc";

    }
    function turnto_wait_recive(){
        document.getElementById("waitZhipai").style.display="none";
        document.getElementById("ingTask").style.display="none";
        document.getElementById("waitRecive").style.display="block";
        document.getElementById("waitPingjia").style.display="none";
        document.getElementById("finishTask").style.display="none";
        document.getElementById("already_send").style.display="none";
        document.getElementById("turnto_wait_anpai").style.backgroundColor=" #cccccc";
        document.getElementById("turnto_ing").style.backgroundColor=" #cccccc";
        document.getElementById("turnto_wait_recive").style.backgroundColor=" crimson";
        document.getElementById("turnto_wait_pingJia").style.backgroundColor=" #cccccc";
        document.getElementById("turnto_already_finish").style.backgroundColor=" #cccccc";
        document.getElementById("turnto_already_send").style.backgroundColor=" #cccccc";

    }
    function turnto_ing(){
        document.getElementById("waitZhipai").style.display="none";
        document.getElementById("ingTask").style.display="block";
        document.getElementById("waitRecive").style.display="none";
        document.getElementById("waitPingjia").style.display="none";
        document.getElementById("finishTask").style.display="none";
        document.getElementById("already_send").style.display="none";
        document.getElementById("turnto_wait_anpai").style.backgroundColor=" #cccccc";
        document.getElementById("turnto_ing").style.backgroundColor=" crimson";
        document.getElementById("turnto_wait_recive").style.backgroundColor=" #cccccc";
        document.getElementById("turnto_wait_pingJia").style.backgroundColor=" #cccccc";
        document.getElementById("turnto_already_finish").style.backgroundColor=" #cccccc";
        document.getElementById("turnto_already_send").style.backgroundColor=" #cccccc";

    }
    function turnto_wait_anpai(){
        document.getElementById("waitZhipai").style.display="block";
        document.getElementById("ingTask").style.display="none";
        document.getElementById("waitRecive").style.display="none";
        document.getElementById("waitPingjia").style.display="none";
        document.getElementById("finishTask").style.display="none";
        document.getElementById("already_send").style.display="none";
        document.getElementById("turnto_wait_anpai").style.backgroundColor=" crimson";
        document.getElementById("turnto_ing").style.backgroundColor=" #cccccc";
        document.getElementById("turnto_wait_recive").style.backgroundColor=" #cccccc";
        document.getElementById("turnto_wait_pingJia").style.backgroundColor=" #cccccc";
        document.getElementById("turnto_already_finish").style.backgroundColor=" #cccccc";
        document.getElementById("turnto_already_send").style.backgroundColor=" #cccccc";

    }



    function turnto_per() {
        document.getElementById("applyTaskcenter").style.display="none";
        document.getElementById("turnto_all").style.backgroundColor=" #cccccc";
      document.getElementById("sendTaskcenter").style.display="block";
        document.getElementById("turnto_per").style.backgroundColor="crimson";
        document.getElementById("autoTaskcenter").style.display="none";
        document.getElementById("turnto_auto").style.backgroundColor="#cccccc";
    }
    function turnto_all(){
        document.getElementById("applyTaskcenter").style.display="block";
        document.getElementById("turnto_all").style.backgroundColor=" crimson";
        document.getElementById("sendTaskcenter").style.display="none";
        document.getElementById("turnto_per").style.backgroundColor="#cccccc";
        document.getElementById("autoTaskcenter").style.display="none";
        document.getElementById("turnto_auto").style.backgroundColor="#cccccc";
    }

    function turnto_auto() {
        document.getElementById("applyTaskcenter").style.display="none";
        document.getElementById("turnto_all").style.backgroundColor=" #cccccc";
        document.getElementById("sendTaskcenter").style.display="none";
        document.getElementById("turnto_per").style.backgroundColor="#cccccc";
        document.getElementById("autoTaskcenter").style.display="block";
        document.getElementById("turnto_auto").style.backgroundColor="crimson";

    }


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

    function datasecmenu() {
        //alert("123");
        window.location.href="高德api.html";
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