<%--
  Created by IntelliJ IDEA.
  User: lai
  Date: 2019/4/23
  Time: 15:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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

        #container {
            float: top;
            margin-left: 0%;
            margin-top: 0%;
            width: 100%;
            height:59.5%;
            border:2px solid #678b99;
            border-left:1px solid #678b99;
            border-right:1px solid #678b99;
        }
    </style>
</head>
<body>
<style>
    .input-card {
        margin-top: 70%;
        width: 6.5%;
        bottom: auto;
    }
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
    menu{
        margin-top: 0%;
        float: left;
        width: 5%;
        height: 3%;
        padding-left: 0px;
    }
    .dataselectbox{
        margin-right: 0.1%;
        margin-top: 0.1%;
        float: right;
        background-size: cover;
        width: 100%;
        height: 96%;
        border:1px solid #929799;
    }
    .dataselect-list-box{
        margin-left:0.3%;
        margin-top: 0.1%;
        float: top;
        /*background-size: cover;*/
        width: 99%;
        height: 40%;
        /*border:2px solid #929799;*/
    }
    .selecttools{
        margin-top: 0.1%;
        float: left;
        width: 59.5%;
        height:8%;
        border:1px solid #678b99;
    }
</style>
<div id="majorbox" class="majorbox";>
    <div class="menubox">
        <button class="menubtn" onclick="turnto_up()">数据上传</button>
        <button class="menubtn" onclick="turnto_sec()">数据检索</button>
        <%--<button class="menubtn" onclick="turnto_down()">数据下载</button>--%>
        <button class="menubtn" onclick="turnto_down()">任务界面</button>
    </div>


    <div id="dataselectbox" class="dataselectbox">
        <div class="panel-body" style="height: 40%; overflow-y:scroll">
            <div style="border: 1px  #000000; width: 90%; margin: 0 auto;">
                <div id="dataselect-list-box" class="dataselect-list-box" >
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
                            <th>上传人</th>
                            <th>操作</th>
                            <th>操作</th>
                        </tr>
                        <c:forEach var="U" items="${userAll}"  >
                            <form action="DownServlet" method="get" enctype="multipart/form-data">
                                <tr> <td><input type="text" value="${U.sj_id}"  name="sj_id" ></td>
                                    <td><input type="text" value="${U.sj_name}" id="sj_name" name="sj_name"></td>
                                    <td><input type="text" value="${U.locality}" name="locality"></td>
                                    <td><input type="text" value="${U.size}" name="size" ></td>
                                    <td><input type="text" value="${U.type}" name="type"></td>
                                    <td><input type="text" value="${U.cxtime}" name="cxtime" ></td>
                                    <td><input type="text" value="${U.wn_coord}" name="type"></td>
                                    <td><input type="text" value="${U.es_coord}" name="cxtime" ></td>
                                    <td><input type="text" value="${U.scuser}" name="scuser" ></td>
                                    <td><input type="submit" value="下载" size="5"></td>
                                    <td><input type="button" value="预览" size="5" onclick="showrec('${U.wn_coord}','${U.es_coord}')"></td>
                                </tr>
                            </form>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </div>

        <div id="container">

        </div>

    </div>
</div>


<!-- ���ص�ͼJSAPI�ű� -->
<script type="text/javascript" src="https://cache.amap.com/lbs/static/addToolbar.js"></script>
<script src="https://cache.amap.com/lbs/static/es5.min.js"></script>
<script src="https://webapi.amap.com/maps?v=1.4.14&key=32dfb8b4fc1783fc8635134beedc72c8&&plugin=AMap.Scale,AMap.OverView,AMap.ToolBar,AMap.Autocomplete,AMap.Geocoder,AMap.MouseTool,AMap.MouseToolAMap.PolyEditor,AMap.CircleEditor,AMap.DistrictSearch,AMap.MapType"></script>
<script type="text/javascript" src="https://a.amap.com/jsapi_demos/static/demo-center/js/underscore-min.js" ></script>
<script type="text/javascript" src="https://a.amap.com/jsapi_demos/static/demo-center/js/backbone-min.js" ></script>
<script type="text/javascript" src='https://a.amap.com/jsapi_demos/static/demo-center/js/prety-json.js'></script>
<script type="text/javascript" src="https://a.amap.com/jsapi_demos/static/demo-center/js/jquery-1.11.1.min.js" ></script>

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
    var polygons=[];
    function showrec(wn_coord,es_coord){
        map.remove(polygons);//清除上次结果
        polygons=[];

        var polygonArr = new Array();//多边形覆盖物节点坐标数组

        var wn_long = wn_coord.substring(0,wn_coord.lastIndexOf(","));
        var wn_lat = wn_coord.substring(wn_coord.lastIndexOf(",")+1);
        var es_long = es_coord.substring(0,es_coord.lastIndexOf(","));
        var es_lat = es_coord.substring(es_coord.lastIndexOf(",")+1);
        polygonArr.push([wn_long,wn_lat]);
        polygonArr.push([wn_long,es_lat]);
        polygonArr.push([es_long,es_lat]);
        polygonArr.push([es_long,wn_lat]);
        var  polygon = new AMap.Polygon({
            path: polygonArr,//设置多边形边界路径
            strokeColor: "#0091ea", //线颜色
            strokeWeight: 1,    //线宽
            fillColor: "#1791fc", //填充色
            fillOpacity: 0.1//填充透明度
        });
        polygons.push(polygon);
        //polygon.setMap(map);
        map.add(polygons);
        map.setFitView(polygons);//视口自适应
    }

    var scale = new AMap.Scale({
                visible: true
            }),
            toolBar = new AMap.ToolBar({
                visible: true
            }),
            overView = new AMap.OverView({
                visible: true
            }),
            map = new AMap.Map("container", {
                resizeEnable: true,
                zoom:5
            }),
            mapType = new AMap.MapType({
                visible:true
            });
    map.addControl(scale);
    map.addControl(toolBar);
    map.addControl(overView);
    map.addControl(mapType);




    var auto = new AMap.Autocomplete({
        input: "tipinput"
    });
    AMap.event.addListener(auto, "select", select);//注册监听，当选中某条记录时会触发
    function select(e) {
        if (e.poi && e.poi.location) {
            map.setZoom(15);
            map.setCenter(e.poi.location);
        }
    }
</script>
</body>
</html>