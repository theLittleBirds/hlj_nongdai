
document.write('<script src="http://api.map.baidu.com/api?v=1.3"></script>');


jQuery.extend({
    pickLocation: function (input_lng, input_lat, address) {
        var ts = new Date().getTime();

        $(address).on('blur', function () {
            var val = $(this).val();

            var myGeo = new BMap.Geocoder();
            myGeo.getPoint(val, function (point) {
                if (point) {
                    input_lng.val(point.lng);
                    input_lat.val(point.lat);
                } else {
                    alert("您选择地址没有解析到结果!");
                }
            }, "成都市");
        });

        var btn = '<a href="#modal-map_' + ts + '"data-toggle="modal" class="pink btn btn-sm btn-success" style="margin:0 10px 0;"> 手动选取经纬度 </a>';
        var modal = '<div id="modal-map_' + ts + '" class="modal fade "><div class="modal-dialog" style="width:80%;height:80%"><div class="modal-content">' +
            '<div id="modal-wizard-container"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>' +
            '<h1 class="modal-title">选取经纬度<small class="blue"><i class="ace-icon fa fa-angle-double-right black" style="padding:0 10px;color: black;"></i>搜索，或者点击地图来选取坐标</small></h1>' +
            '</div><div class="modal-body step-content"><div class="form-inline col-lg-12 no-padding" style="margin-bottom: 15px;"><div class="input-group col-lg-6 col-sm-12 no-padding"><input id="map_search_input_' + ts + '" type="text" class="form-control" placeholder="输入地名搜索..."> <span class="input-group-btn">' +
            '<button id="map_search_btn_' + ts + '" type="button" class="btn btn-purple btn-sm"><span class="ace-icon fa fa-search icon-on-right bigger-110"></span> 搜索</button></span></div>' +
            '<div class="pull-right col-lg-6 col-sm-12 no-padding" style="margin-top: 20px;margin-bottom: 20px;"><div class="input-group"><span class="input-group-addon">经度</span><input type="text" id="map_point_lan_' + ts + '" readonly class="form-control input-small" style="float: left;"></div><div class="input-group">' +
            '<span class="input-group-addon">纬度</span><input type="text" id="map_point_lat_' + ts + '" readonly class="form-control input-small" style="float: left;"> <span class="input-group-btn" style="margin-left: 15px;"><button type="button" class="btn btn-success btn-sm" data-dismiss="modal">' +
            '<span class="ace-icon fa fa-check icon-on-right bigger-110"></span> 确认使用</button></span></div></div></div>' +
            '<div id="map_container_' + ts + '" style="border: 1px solid #DCD2B9;margin-top: 15px;width:100%;height:80%;min-height:400px;overflow:hidden;position:relative;z-index:0;color:#000;text-align:left;background-color:#f3f1ec"></div></div></div>' +
            '</div></div></div>';

        if (!input_lat && !input_lng) return;

        input_lat.after(btn, modal);

        var setVal = function (point) {
            $('#map_point_lan_' + ts).val(point.lng);
            $('#map_point_lat_' + ts).val(point.lat);
            input_lng.val(point.lng);
            input_lat.val(point.lat);
        };

        var map = null;


        $('#modal-map_' + ts).on('shown.bs.modal', function (e) {
            var oPoint = new BMap.Point(input_lng.val() ? input_lng.val() : 104.072227, input_lat.val() ? input_lat.val() : 30.663456);
            var marker = new BMap.Marker(oPoint);
            setVal(oPoint);

            map = new BMap.Map("map_container_" + ts);
            map.centerAndZoom(oPoint, 17);

            map.enableScrollWheelZoom();    //启用滚轮放大缩小，默认禁用
            map.enableContinuousZoom();    //启用地图惯性拖拽，默认禁用

            map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件
            map.addControl(new BMap.OverviewMapControl()); //添加默认缩略地图控件
            map.addControl(new BMap.OverviewMapControl({isOpen: true, anchor: BMAP_ANCHOR_BOTTOM_RIGHT}));   //右下角，打开

            //标记点上屏，并绑定拖拽事件
            map.addOverlay(marker);
            marker.enableDragging();
            marker.addEventListener("dragend", function () {
                setVal(marker.getPosition());
            });

            //点击监听
            map.addEventListener("click", function (e) {
                map.clearOverlays();//清空原来的标注
                marker = new BMap.Marker(new BMap.Point(e.point.lng, e.point.lat));  // 创建标注，为要查询的地方对应的经纬度

                //标记点上屏，并绑定拖拽事件
                map.addOverlay(marker);
                marker.enableDragging();
                marker.addEventListener("dragend", function () {
                    setVal(marker.getPosition());
                });

                setVal(e.point);
            });

            var local = new BMap.LocalSearch(map, {
                pageCapacity: 1,
                onSearchComplete: function (results) {
                    if (!results || !results.getPoi(0) || !results.getPoi(0).point) {
                        alert({message: "没有结果！"});
                        return;
                    }
                    var point = results.getPoi(0).point;

                    map.centerAndZoom(point, 17);

                    map.clearOverlays();//清空原来的标注
                    marker.setPosition(point);
                    map.addOverlay(marker);

                    setVal(point);
                }
            });

            var ac = new BMap.Autocomplete({
                "input": 'map_search_input_' + ts,
                "location": map
            });

            ac.addEventListener("onconfirm", function (e) {    //鼠标点击下拉列表后的事件
                $('#map_search_btn_' + ts).click();
            });

            $('#modal-map_' + ts).on('click', '#map_search_btn_' + ts, function () {
                var keyWord = $('#map_search_input_' + ts).val();
                if (!keyWord) {
                    alert({message: "关键字为空！"});
                    return;
                }
                local.search(keyWord);
            });

            if (address) {
                var add = address.val();
                if (add) {
                    $('#map_search_input_' + ts).val(add);
                    local.search(add);
                }
            }
        });
    }
});