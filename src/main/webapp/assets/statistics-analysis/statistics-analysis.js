/*!
 * 统计分析模块
 * by 杨康
 * Copyright 2015 Youlove, Inc.
 */
(function ($) {

    "use strict";

    /** 命名空间 */
    var statistics_utils = {};

    /**渲染折线统计图表*/
    statistics_utils.renderLineECharts = function (element, title, legendArray, xAxisArray, seriesArray, a, callback) {

        var myChart = echarts.init(document.getElementById(element), 'macarons');
        if (!a) {
            a = {};
        }
        var option = {
            title: {
                text: title
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data: legendArray
            },

            grid: a,

            toolbox: {
                show: true,
                feature: {
                    mark: {show: true},
                    dataView: {show: true, readOnly: false},
                    magicType: {show: true, type: ['line', 'bar']},
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            calculable: false,
            xAxis: [
                {
                    type: 'category',
                    data: xAxisArray,
                    axisLabel: {
                        interval: 0,
                        rotate: 55,
                        formatter: function (val) {
                            return val.split("").join("\n");
                        }
                    }
                }
            ],
            yAxis: [
                {
                    type: 'value'
                }
            ],
            series: seriesArray
        };
        myChart.setOption(option);
        window.onresize = myChart.resize;

        var ecConfig = echarts.config;
        myChart.on(ecConfig.EVENT.CLICK, function (param) {
            callback(param);
        });
    };

    /**渲染柱状统计图表*/
    statistics_utils.renderBarECharts = function (element, title, legendArray, xAxisArray, seriesArray, a, callback) {

        var myChart = echarts.init(document.getElementById(element), 'macarons');
        if (!a) {
            a = {};
        }
        var option = {
            title: {
                text: title
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data: legendArray
            },

            grid: a,

            toolbox: {
                show: true,
                feature: {
                    mark: {show: true},
                    dataView: {show: true, readOnly: false},
                    magicType: {show: true, type: ['line', 'bar']},
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            calculable: false,
            xAxis: [
                {
                    type: 'category',
                    data: xAxisArray,
                    axisLabel: {
                        interval: 0,
                        rotate: 55,
                        formatter: function (val) {
                            return val.split("").join("\n");
                        }
                    }
                }
            ],
            yAxis: [
                {
                    type: 'value'
                }
            ],
            series: seriesArray
        };
        myChart.setOption(option);
        window.onresize = myChart.resize;

        var ecConfig = echarts.config;
        myChart.on(ecConfig.EVENT.CLICK, function (param) {
            callback(param);
        });
    };

    /**渲染饼状统计图表*/
    statistics_utils.renderPieECharts = function (element, title, depts, datas, callback) {

        var myChart = echarts.init(document.getElementById(element));
        window.onresize = myChart.resize;
        var option = {
            title: {
                text: title,
                x: 'center'
            },
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                x: 'left',
                data: depts
            },
            toolbox: {
                show: true
            },
            calculable: true,
            series: [
                {
                    name: '访问来源',
                    type: 'pie',
                    radius: '55%',
                    center: ['50%', '60%'],
                    data: datas
                }
            ]
        };
        myChart.setOption(option);
        var ecConfig = echarts.config;
        myChart.on(ecConfig.EVENT.CLICK, function (param) {
            callback(param);
        });

        /*myChart.on('click', function (param) {
         callback(param);
         });*/
    };

    /**渲染地图*/
    statistics_utils.renderMapECharts = function (element, path, data) {

        echarts.util.mapData.params.params.HZ = {
            getGeoJson: function (callback) {
                $.getJSON(path, callback);
            }
        };
        //杭州市地图
        var myChart = echarts.init(document.getElementById(element));
        myChart.setOption({
            tooltip: {
                trigger: 'item',
                formatter: function (data) {

                    var name = data.name;
                    var intelligenceCount = data.data.intelligenceCount;

                    var informerCount = data.data.informerCount;
                    console.log(data);
                    return name + '<br/>情报采集量：' + intelligenceCount + '<br/>信息员人数: ' + informerCount + '<br/>';
                }
            },
            dataRange: {
                min: 0,
                max: 20,
                color: ['red', 'orange', 'yellow'],
                text: ['高', '低'],           // 文本，默认为数值文本
                calculable: true
            },
            series: [
                {
                    name: '',
                    type: 'map',
                    mapType: 'HZ',
                    selectedMode: 'single',
                    itemStyle: {
                        normal: {label: {show: true}},
                        emphasis: {label: {show: true}}
                    },
                    data: data
                }
            ]
        });
    };

    /**渲染时间柱状图表*/
    statistics_utils.renderTimeBarECharts = function (element, dateArray, dataArray) {

        var myChart = echarts.init(document.getElementById(element));
        var seriesList = [];
        var legendDataList = [];

        seriesList.push({
            name: '情报总量',
            type: 'line',
            data: dataArray
        });
        legendDataList.push("情报总量");

        var option = {
            tooltip: {
                trigger: 'axis'
            },
            toolbox: {
                y: 50,
                show: true,
                feature: {
                    mark: {show: true},
                    dataView: {show: true, readOnly: false},
                    magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            calculable: true,
            dataZoom: {
                show: true,
                realtime: true,
                start: 40,
                end: 60
            },
            legend: {
                data: legendDataList,
                x: 'center',
                y: 0
            },
            xAxis: [
                {
                    type: 'category',
                    splitLine: {show: false},
                    data: dateArray
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    position: 'right'
                }
            ],
            grid: {
                y: 100
            },
            series: seriesList
        };
        myChart.setOption(option);
    };

    /**时间控件初始化*/
    statistics_utils.initDatePlugin = function (startTimeElement, endTimeElement) {

        var start = {
            elem: '#' + startTimeElement,
            format: 'YYYY/MM/DD', //设定最小日期为当前日期
            max: '2099-06-16', //最大日期
            istoday: false,
            choose: function (datas) {
                end.min = datas; //开始日选好后，重置结束日的最小日期
                end.start = datas //将结束日的初始值设定为开始日
            },
            event: 'focus'
        };
        var end = {
            elem: '#' + endTimeElement,
            format: 'YYYY/MM/DD',
            min: laydate.now(),
            max: '2099-06-16',
            istoday: false,
            event: 'focus'
        };
        laydate(start);
        laydate(end);
    };

    /**将单位id转换成单位名称*/
    statistics_utils.switchDeptIdToName = function (ids, callBack) {

        var allIds = [];
        if (!(ids instanceof Array)) {
            allIds.push(ids)
        } else {
            allIds.concat(ids)
        }

        $.ajax({
            type: 'GET',
            url: '/statistics/switch-ids-to-names.json',
            dataType: 'json',
            traditional: true,
            async: false,
            data: {
                ids: allIds
            },
            success: function (data) {

                var meta = data.meta;
                if (meta.code == 200) {

                    callBack(data.response.list);

                } else {
                    layer.alert(data.message, 0);
                }
            },
            error: function () {

                layer.alert("添加失败", 0);
            }
        });


    }

    //地图区域
    statistics_utils.mapAreas = function () {
        return {
            "上城": "上城区",
            "下城": "下城区",
            "江干": "江干区",
            "拱墅": "拱墅区",
            "西湖": "西湖区",
            "滨江": "滨江区",
            "萧山": "萧山区",
            "余杭": "余杭区",
            "富阳": "富阳区",
            "桐庐": "桐庐县",
            "淳安": "淳安县",
            "建德": "建德市",
            "临安": "临安市"
        };
    }

    statistics_utils.ajax = function () {
    };

    /**
     * 渲染一个line或者bar的线条
     * @param propName  显示名称
     * @param propType bar or line
     * @param showFlag 是否展示数字
     * @param addGroup 是否叠加显示
     * @param seriesIntelligenceArray   添加到的数组
     * @param allArr   //数据
     */
    statistics_utils.renderLineOrBar = function (propName, propType, showFlag, addGroup, seriesIntelligenceArray, allArr) {

        var itemStyle = '{ normal: {label : {show: true },barBorderRadius:[10, 10, 10, 10]}}';
        var json = eval('(' + itemStyle + ')');

        var sum = new Array();
        for (var i = 0; i < allArr[0].length; i++) {
            sum[i] = 0;
        }
        for (var count1 = 0; count1 < sum.length; count1++) {
            for (var count2 = 0; count2 < allArr.length; count2++) {
                sum[count1] += allArr[count2][count1];
            }
        }
        var prop = {};
        prop.name = propName;
        prop.type = propType;
        if (showFlag == true) {
            prop.itemStyle = json;
        }
        if (addGroup == true) {

            prop.stack = 'group';
        }
        if (addGroup == false) {
            prop.stack = 'elseGroup';
        }
        prop.data = sum;
        seriesIntelligenceArray.push(prop);
    };

    //公开
    window.statistics_utils = statistics_utils;

})(window.jQuery);