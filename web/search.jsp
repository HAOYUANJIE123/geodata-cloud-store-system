<%--
  Created by IntelliJ IDEA.
  User: lai
  Date: 2019/4/23
  Time: 15:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="test.service.GetTaskCount" %>
<%@ page import="test.service.GetNewFriendsCount" %>
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
            float: left;
            margin-left: 0%;
            margin-top: -4%;
            width: 100%;
            height:89%;
            border:2px solid #678b99;
            border-left:1px solid #678b99;
            border-right:1px solid #678b99;
        }
    </style>
</head>
<body>
<style>
    .input-card {
        margin-top: 10.5%;
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
        border-top:40px solid #929799;
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
        height: 5%;
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
        float: left;
        background-size: cover;
        width: 20%;
        height: 99%;
        border:2px solid #929799;
    }
    .selecttools{
        margin-top: 0.1%;
        float: left;
        width: 100%;
        height:16%;
        border:1px solid #678b99;}
        .supersec{
            float: left ;
           margin-left:0.1%;
            margin-top: -0.1%
        }
    .menubtn{
float: left;
        margin-left: 2%;
    }

</style>
<div id="majorbox" class="majorbox";>
    <div class="menubox">
        <button class="menubtn" onclick="turnto_up()">数据上传</button>
        <button class="menubtn" onclick="turnto_sec()">数据检索</button>
        <button class="menubtn" onclick="turnto_down()">任务界面</button>
        <form action="FriendAddServlet" method="post" enctype="multipart/form-data">
            <input type="submit" id="userinfo" name="userinfo"    style="float: right;width: 4%;height:100%;display: block" value="">
        </form>
    </div>
        <div id="selecttools" class="selecttools">
            <div style="float: left ;margin-top:0.5%" >
                <input type="text" id="jinwei" placeholder="经纬"style="width:85% ">
            </div>
            <div style="float: left ;margin-left:-1%;margin-top:0.5%" >
                <form action="SearchServlet" method="get" enctype="multipart/form-data">
                    <div style="float: left ;margin-top: 1.5%" >
                        <label for="dataname" style="float: left ;margin-left: 0%;">数据名称:</label>
                    </div>
                    <div style="float: left ;margin-left: 0.1%;margin-top: 1%" >
                        <input type="text" id="dataname" name="dataname" size="5" >
                    </div>
                        <input type="submit" id="datanameselect"  style="float:right ;margin-top: -15%;" value="检索">
                </form>
            </div>

            <div>
                <form action="SearchAdvServlet" method="get" enctype="multipart/form-data">
                    <div class="supersec" style="margin-left:2% " >
                        <input type="button" id="superselect" style="font-size: small" value="开启高级检索:"onclick="supersec()">
                    </div>
                    <div>
                        <div class="supersec"  >
                            <label for="area" style="float: left">区域名称:</label>
                        </div>
                        <div class="supersec" >
                            <select id="areaclass" >
                                <option>区域尺度</option>
                                <option>省</option>
                                <option>市</option>
                                <option>区/县</option>
                                <option>乡镇</option>
                                <option>详细地址</option>
                            </select>
                        </div>
                        <div class="supersec"  >
                            <input type="text" id="area" name="area" placeholder="点击地图获取"width="120%" style="font-size: x-small">
                        </div>
                    </div>
                    <div>
                        <div class="supersec"  >
                            <label for="year" style="float: left">数据年份:</label>
                        </div>
                        <div class="supersec" >
                            <input type="text" id="year" name="year" size="2" >
                        </div>
                    </div>

                    <div class="supersec"  >
                        <label for="datatype" style="float: left">数据类型:</label>
                    </div>
                    <div class="supersec" >
                        <select id="datatype" name="datatype" size="1" >
                            <option>栅格数据</option>
                            <option>矢量数据</option>
                        </select>
                    </div>

                    <div class="supersec"  >
                        <input type="submit" value="高级检索">
                    </div>
                </form>
                <form action="SearchRangeServlet" method="get" enctype="multipart/form-data">
                    <div class="supersec" style="margin-left:2% ">
                        <input type="button" value="矩形区域" onclick="draw_rect()">
                    </div>
                    <div class="supersec"  >
                        <input type="text" id="westnorth" name="westnorth" value=""placeholder="矩形西北点经纬度" size="6" style="font-size: x-small">
                    </div>
                    <div class="supersec"  >
                        <input type="text" id="eastsouth" name="eastsouth" value=""placeholder="矩形东南点经纬度" size="6" style="font-size: x-small">
                    </div>
                    <div class="supersec"  >
                        <input type="submit" value="范围检索">
                    </div>
                </form>
            </div>
        </div>


        <div id="container">

        </div>

        <div class='input-card'>
            <div class="input-item">
                <input type="checkbox" onclick="toggleScale(this)"/>比例尺
            </div>

            <div class="input-item">
                <input type="checkbox" id="toolbar" onclick="toggleToolBar(this)"/>工具条
            </div>

            <div class="input-item">
                <input type="checkbox" id="toolbarDirection" disabled onclick="toggleToolBarDirection(this)"/>方向盘
            </div>

            <div class="input-item">
                <input type="checkbox" id="toolbarRuler" disabled onclick="toggleToolBarRuler(this)"/>工具条标尺
            </div>

            <div class="input-item">
                <input type="checkbox" id="overview" onclick="toggleOverViewShow(this)"/>显示鹰眼
            </div>

            <div class="input-item">
                <input type="checkbox" id="overviewOpen" disabled onclick="toggleOverViewOpen(this)"/>展开鹰眼
            </div>
        </div>



    </div>
</div>


<!-- ���ص�ͼJSAPI�ű� -->
<script type="text/javascript" src="https://cache.amap.com/lbs/static/addToolbar.js"></script>
<script src="https://cache.amap.com/lbs/static/es5.min.js"></script>
<script src="https://webapi.amap.com/maps?v=1.4.14&key=32dfb8b4fc1783fc8635134beedc72c8&&plugin=AMap.Scale,Map3D,AMap.OverView,AMap.TileLayer.Satellite,AMap.ToolBar,AMap.Autocomplete,AMap.Geocoder,AMap.MouseTool,AMap.MouseToolAMap.PolyEditor,AMap.CircleEditor,AMap.DistrictSearch,AMap.MapType"></script>
<script type="text/javascript" src="https://a.amap.com/jsapi_demos/static/demo-center/js/underscore-min.js" ></script>
<script type="text/javascript" src="https://a.amap.com/jsapi_demos/static/demo-center/js/backbone-min.js" ></script>
<script type="text/javascript" src='https://a.amap.com/jsapi_demos/static/demo-center/js/prety-json.js'></script>
<script type="text/javascript" src="https://a.amap.com/jsapi_demos/static/demo-center/js/jquery-1.11.1.min.js" ></script>

<script type="text/javascript">

    //信息更新部分
    var t;
    function timedCount()
    {
        <%--var info="任务:"+ <%=new GetTaskCount().getValue()%>--%>

        var a=<%=new GetNewFriendsCount().getValue()%>;
        var userinfo="用户:"+ a
        if(a!=0){
            document.getElementById('userinfo').value=userinfo;
            t=setTimeout("timedCount()",1000)//不断循环时间，数字为设置更新间隔
        }
        else{
            document.getElementById('userinfo').value="用户";
       }
    }
    timedCount();

    function turnto_up() {

       window.location.href="major.jsp";

    }
    function turnto_sec() {
        window.location.href="search.jsp";
    }

    function turnto_gis() {
        window.location.href="test.html";
    }

    function turnto_down() {

        window.location.href="GIS.jsp";

    }







    var scale = new AMap.Scale({
                visible: false
            }),
            toolBar = new AMap.ToolBar({
                visible: false
            }),
            overView = new AMap.OverView({
                visible: false
            }),
            map = new AMap.Map("container", {
                resizeEnable: true,
                zoom:5,
            }),
            mapType = new AMap.MapType({
                visible:true
            });



    var i=0;
    var mouseTool = new AMap.MouseTool(map); //在地图中添加MouseTool插件;
    function draw_rect() {
        i++;
        if(i%2==1)
        {
            alert("启动绘图")
            var drawRectangle = mouseTool.rectangle(); //用鼠标工具画矩形

          AMap.event.addListener(mouseTool, 'draw', function (e) { //添加事件
              document.getElementById("westnorth").value = e.obj.getPath()[0];
              document.getElementById("eastsouth").value = e.obj.getPath()[2];
         //  alert(e.obj.getPath().length)
              //  alert("第一"+e.obj.getPath()[0]+"第二"+e.obj.getPath()[1]+"第三"+e.obj.getPath()[2]+"第四"+e.obj.getPath()[3])
              /* var mask=[];
                  for(var i=0;i<4;i++)
                {
                    mask.push([e.obj.getPath()[i]]);
                    alert(mask.length)
                    alert(mask[i])
                    marker = new AMap.Marker({
                        position: mask[i],
                        offset: new AMap.Pixel(-13, -30)
                    });
                    marker.setMap(map);

                }

                map= new AMap.Map("container", {
                    mask:mask,
                    resizeEnable: true,
                    zoom:5,
                    viewMode:'3D',
                    layers:[
                        new  AMap.TileLayer({   zIndex:0,rejectMapMask:true}),
                        /*    new AMap.TileLayer.Satellite({
                         zIndex:1,
                         rejectMapMask:true}),
                        new AMap.TileLayer.Traffic({
                            zIndex:1,
                            rejectMapMask:false})
                    ]

                });*/

            });



        } else
        {
            document.getElementById("westnorth").value = "";
            document.getElementById("eastsouth").value = "";
            alert("关闭绘图");
            mouseTool.close(true);}
    }

    /* var mask=[];
     for(var i =0;i<bounds.length;i+=1){
     mask.push([bounds[i]])
     }

     map= new AMap.Map("container", {
     mask:mask,
     resizeEnable: true,
     zoom:5,
     viewMode:'3D',
     layers:[
     new  AMap.TileLayer({rejectMapMask:true}),
     new AMap.TileLayer.Satellite({rejectMapMask:false})
     ]

     });*/








    map.on('click', function(e) {
      //  map.remove(marker);
        document.getElementById("jinwei").value = e.lnglat.getLng() + ',' + e.lnglat.getLat();//将经纬度写入对话框
      /*  marker = new AMap.Marker({
            position:[e.lnglat.getLng(),e.lnglat.getLat()],
           offset: new AMap.Pixel(-13, -30)
        });
        marker.setMap(map);*/

    });

    //高级检索按钮的方法
    var j=0;//计数器单数启动，双数关闭
    function supersec() {
        j++;
        if(j%2==1){
            alert("高级检索启动，再次点击关闭");
            //为地图注册click事件获取鼠标点击出的经纬度坐标
            map.on('click', function(e) {
                map.remove(polygons)//清除上次结果
                //以下将经纬度转为详细地址
                AMap.plugin('AMap.Geocoder', function() {
                    var geocoder = new AMap.Geocoder({
                        // city 指定进行编码查询的城市，支持传入城市名、adcode 和 citycode
                        city: ""
                    })

                    var lnglat = [e.lnglat.getLng(), e.lnglat.getLat()];
                    geocoder.getAddress(lnglat, function(status, result) {
                        if (status === 'complete' && result.info === 'OK') {
                            var pro= result.regeocode.addressComponent.province;//省
                            var city = result.regeocode.addressComponent.city;//市
                            var dis = result.regeocode.addressComponent.district;//区/县township
                            var town = result.regeocode.addressComponent.township;//乡镇
                            var cominfo = result.regeocode. formattedAddress;//详细地址
                            if(document.getElementById("areaclass").value=="区域尺度")
                            {
                                alert("选定区域尺度");
                            }else {
                                if(document.getElementById("areaclass").value=="省")
                                {
                                    var address=pro;
                                    map.setZoomAndCenter(5, lnglat);
                                    var lev='province';
                                    drawBounds(lev,pro);
                                }
                                if(document.getElementById("areaclass").value=="市")
                                { var address=pro+city;
                                    map.setZoomAndCenter(7, lnglat);
                                    var lev='city';
                                    drawBounds(lev,city);
                                }

                                if(document.getElementById("areaclass").value=="区/县")
                                {  var address=pro+city+dis;
                                    map.setZoomAndCenter(9, lnglat);
                                    var lev='district';
                                    drawBounds(lev,dis);
                                }
                                //绘制行政边框到县级
                                if(document.getElementById("areaclass").value=="乡镇")
                                {  var address=pro+city+dis+town;
                                    map.setZoomAndCenter(11, lnglat);
                                }
                                if(document.getElementById("areaclass").value=="详细地址")
                                {  var address=cominfo;
                                    map.setZoomAndCenter(16, lnglat);
                                }
                                document.getElementById("area").value= address;
                                // result为对应的地理位置详细信息
                            }
                        }
                    })
                })





            });}

    }



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



    //以下为绘制行政边界的方法
    var district = null;
    var polygons=[];
    //行政边界的划分

    function drawBounds( lev,add) {

        //加载行政区划插件
        if(!district){
            //实例化DistrictSearch
            var opts = {
                subdistrict: 0,   //获取边界不需要返回下级行政区
                extensions: 'all',  //返回行政区边界坐标组等具体信息
                level: 'province'   //查询行政级别为 市
            };
            district = new AMap.DistrictSearch(opts);
        }
        //行政区查询
        district.setLevel(lev)
        district.search(add, function(status, result) {
           //   map.remove(polygons)//清除上次结果
            polygons = [];
            var bounds = result.districtList[0].boundaries;
            /* var mask=[];
             for(var i =0;i<bounds.length;i+=1){
             mask.push([bounds[i]])
             }

             map= new AMap.Map("container", {
             mask:mask,
             resizeEnable: true,
             zoom:5,
             viewMode:'3D',
             layers:[
             new  AMap.TileLayer({rejectMapMask:true}),
             new AMap.TileLayer.Satellite({rejectMapMask:false})
             ]

             });*/

            if (bounds) {
                for (var i = 0, l = bounds.length; i < l; i++) {
                    //生成行政区划polygon
                    var polygon = new AMap.Polygon({
                        strokeWeight: 1,
                        path: bounds[i],
                        fillOpacity: 0.4,
                        fillColor: '#80d8ff',
                        strokeColor: '#0091ea'
                    });
                    polygons.push(polygon);
                }
            }
            map.add(polygons)
            map.setFitView(polygons);//视口自适应
        });
    }












    document.
    map.addControl(scale);
    map.addControl(toolBar);
    map.addControl(overView);
    map.addControl(mapType);
    function toggleScale(checkbox) {
        if (checkbox.checked) {
            scale.show();
        } else {
            scale.hide();
        }
    }
    function toggleToolBar(checkbox) {
        if (checkbox.checked) {
            showToolBar();
            showToolBarDirection();
            showToolBarRuler();
        } else {
            hideToolBar();
            hideToolBarDirection();
            hideToolBarRuler();
        }
    }
    function toggleToolBarDirection(checkbox) {
        if (checkbox.checked) {
            toolBar.showDirection()
        } else {
            toolBar.hideDirection()
        }
    }
    function toggleToolBarRuler(checkbox) {
        if (checkbox.checked) {
            toolBar.showRuler();
        } else {
            toolBar.hideRuler();
        }
    }
    function toggleOverViewShow(checkbox) {
        if (checkbox.checked) {
            overView.show();
            document.getElementById('overviewOpen').disabled = false;
        } else {
            overView.hide();
            document.getElementById('overviewOpen').disabled = true;
        }
    }
    function toggleOverViewOpen(checkbox) {
        if (checkbox.checked) {
            overView.open();
        }
        else {
            overView.close();
        }
    }
    function showToolBar() {
        document.getElementById('toolbar').checked = true;
        document.getElementById('toolbarDirection').disabled = false;
        document.getElementById('toolbarRuler').disabled = false;
        toolBar.show();
    }
    function hideToolBar() {
        document.getElementById('toolbar').checked = false;
        document.getElementById('toolbarDirection').disabled = true;
        document.getElementById('toolbarRuler').disabled = true;
        toolBar.hide();
    }
    function showToolBarDirection() {
        document.getElementById('toolbarDirection').checked = true;
        toolBar.showDirection();
    }
    function hideToolBarDirection() {
        document.getElementById('toolbarDirection').checked = false;
        toolBar.hideDirection();
    }
    function showToolBarRuler() {
        document.getElementById('toolbarRuler').checked = true;
        toolBar.showRuler();
    }
    function hideToolBarRuler() {
        document.getElementById('toolbarRuler').checked = false;
        toolBar.hideRuler();
    }
</script>
</body>
</html>