/*!
 * 工具集
 * by 胡萝卜
 * Copyright 2015 Youlove, Inc.
 */
(function ($) {

    "use strict";

    /**
     * 页面缓存
     * @type {{}}
     */
    top.window.cacheMap = top.window.cacheMap || {};

    /**
     * 业务参数
     * @type {{}}
     */
    var propertyMap = {};
    /**
     * 部门
     */
    var deptMap;

    /**
     * 命名空间
     * @type {{}}
     */
    var kirin = {};

    /**
     * 文本参数填充
     * PS：kirin.format('我喜欢吃{0}，{1}也可以接收，如果有{0}最好，实在没有{2}也可以。','萝卜','青菜'，'土豆');
     * 结构：我喜欢吃萝卜，青菜也可以接收，如果有萝卜最好，实在没有土豆也可以。
     * @param value 代替换文本
     * @param args 参数列表
     */
    kirin.format = function () {
        if (arguments.length === 0) {
            return null;
        }
        var str = arguments[0];
        for (var i = 1; i < arguments.length; i++) {
            var re = new RegExp('\\{' + (i - 1) + '\\}', 'gm');
            str    = str.replace(re, arguments[i]);
        }
        return str;
    };

    /**
     * 字符工具类
     *
     * @type {{displayPart: kirin.string.displayPart}}
     */
    kirin.string = {

        /**
         * 根据参数截取指定长度字符串    ，超长后添加指定后缀。
         *
         * 例：
         * kirin.string.displayPart("一二三四五六七八九十", 5);             // 一二三四五...
         * kirin.string.displayPart("一二三四五六七八九十", 5, '。。。');    // 一二三四五。。。
         * kirin.string.displayPart("一二三四五六七八九十", 100);           // 一二三四五六七八九十
         * kirin.string.displayPart("一二三四五六七八九十", 100, '。。。');  // 一二三四五六七八九十
         *
         * @param val 字符串
         * @param limit 限制长度
         * @param suffix 后缀
         * @returns {*}
         */
        displayPart: function (val, limit, suffix) {
            limit || alert('请输入限制长度');
            suffix = suffix || '...';
            val && val.length >= limit && (val = val.slice(0, limit) + suffix);
            return val;
        }
    };

    /**
     * 获取URL参数
     * @param name
     * @returns {*}
     */
    kirin.getRequsetParam = function (name, defValue) {
        //构造一个含有目标参数的正则表达式对象
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        //匹配目标参数
        var r   = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        //返回参数值
        return defValue;
    };

    /**
     * 获取指定名称的cookie的值
     * @param objName
     * @returns {*}
     */
    kirin.getCookie = function (objName) {
        var arrStr = document.cookie.split("; ");
        for (var i = 0; i < arrStr.length; i++) {
            var temp = arrStr[i].split("=");
            if (temp[0] == objName) return decodeURI(temp[1]);
        }
    };

    /**
     * 页面历史前进后退
     * @param idx 索引
     */
    kirin.historyGo = function (idx) {
        window.history.go(idx);
    };

    /**
     * 初始化开始结束时间选择框的设置
     * PS:
     * {
     *      start : 选择器
     *      end   : 选择器
     *      format: 'YYYY-MM-DD hh:mm:ss' --默认
     *      min   : 最小日期
     *      max   : 最大日期
     *      istoday :false
     *      event : 'focus'
     *      startChooseCallback: callback
     *      endChooseCallback : callback
     *  }
     * @param options {}
     */
    kirin.initRangeDateTime = function (options) {

        //日期控件
        if (!window.laydate) {
            alert("请安装[laydate]日期控件。");
        }

        //默认参数
        var defaults = {
            format             : 'YYYY-MM-DD hh:mm:ss',
            min                : '2000-01-01',
            max                : laydate.now(),
            istoday            : false,
            event              : 'focus',
            startChooseCallback: function (start, end, chooseDate) {
                end.min   = chooseDate;
                end.start = chooseDate;
            },
            endChooseCallback  : function (start, end, chooseDate) {
                //start.min = chooseDate;
                //start.start = chooseDate;
            }
        };

        //使用jQuery.extend 覆盖默认参数
        var opts = $.extend({}, defaults, options);

        //开始
        var start = {
            elem   : opts.start,
            format : opts.format,
            max    : opts.max,
            istoday: opts.istoday,
            choose : function (chooseDate) {
                opts.startChooseCallback && opts.startChooseCallback(start, end, chooseDate);
            },
            event  : opts.event
        };

        //结束
        var end = {
            elem   : opts.end,
            format : opts.format,
            min    : opts.min,
            istoday: opts.istoday,
            choose : function (chooseDate) {
                opts.endChooseCallback && opts.endChooseCallback(start, end, chooseDate);
            },
            event  : opts.event
        };

        laydate(start);
        laydate(end);
    };

    /**
     * 时间格式化
     * @param timestamp
     * @param format
     * @returns {*}
     */
    kirin.dataFormat = function (timestamp, format) {
        return moment(timestamp).format(format);
    };


    /**
     * 初始化[Simditor]编辑器
     * @param select | Array 选择器 | 选择器数组
     * @param options 参数
     */
    kirin.initSimditor = function (selectArray, options) {

        //Simditor控件
        if (!window.Simditor) {
            alert("请安装[Simditor]编辑器控件。");
        }

        //默认参数
        var defaults = {
            toolbar   : [
                'title',
                'bold',
                'italic',
                'underline',
                'strikethrough',
                'color',
                'ol',
                'ul',
                'blockquote',
                'table',
                'link',
                'hr',
                'indent',
                'outdent'
            ],
            pasteImage: true,
            cleanPaste: true
        };

        //包装成数组
        selectArray = $.isArray(selectArray) ? selectArray : [selectArray];

        //使用jQuery.extend 覆盖默认参数
        var opts = $.extend({}, defaults, options);

        //初始化编辑器
        $.each(selectArray, function (idx, select) {
            opts.textarea = $(select);
            new Simditor(opts);
        });
    };

    /**
     * 初始化文件上传控件
     * @param select | Array 选择器 | 选择器数组
     * @param options 参数
     */
    kirin.initFileUploader = function (selectArray, options) {

    };

    /**
     * 初始化[TagsInput]编辑器
     * @param select | Array 选择器 | 选择器数组
     * @param options 参数
     */
    kirin.initTagsInput = function (selectArray, options) {
        //日期控件
        if (!$.fn.tagsinput) {
            alert("请安装[TagsInput]标签控件。");
        }

        //默认参数
        var defaults = {
            tagClass: 'label label-warning',
            //focusClass: 'my-focus-class',
            maxTags : 48,
            maxChars: 48,
            readonly: false
        };

        //包装成数组
        selectArray = $.isArray(selectArray) ? selectArray : [selectArray];

        //使用jQuery.extend 覆盖默认参数
        var opts = $.extend({}, defaults, options);

        //初始化编辑器
        $.each(selectArray, function (idx, select) {
            var obj = $(select);
            obj.tagsinput(opts);
            if (opts.readonly || typeof(obj.attr("readonly")) !== "undefined") {
                var tags = obj.prev('div.bootstrap-tagsinput');
                tags.addClass('readonly');
            }
        });
    };

    /**
     * Ajax下载
     * @param url 请求URL
     * @param params 参数对象
     * @param method 提交方式[get、post]
     */
    kirin.download = function (url, params, method) {
        var inputs = '';
        $.each(params, function (key, value) {
            if (key && value) {
                inputs += kirin.format("<input type='hidden' name='{0}' value='{1}'>", key, value);
            }
        });
        var html   = kirin.format("<form action='{0}' method='{1}'>{2}</form>", url, (method || 'post'), inputs);
        $(html).appendTo('body').submit().remove();
    };

    /**
     * 导出Excel文件
     * @param fileName 文件名称
     * @param dataJson 导出的数据
     * @param sheetName 表格名称
     * @param method 提交方式[get、post]
     */
    kirin.exportExcel = function (fileName, dataJson, sheetName, method) {
        kirin.download("/Ajax/ExcelExport.ashx",
            {
                fileName : fileName,
                dataJson : JSON.stringify(dataJson),
                sheetName: sheetName
            },
            method);
    };


    kirin.exportEvent = function (tableId, tableTempId, igCol, exportUrl, exportParams) {
        var opsCol = "";
        if (typeof(igCol) != "undefined" && igCol != null && "" != igCol) {
            opsCol = "[" + igCol + "]";
        }
        // 获取所有数据
        $.getJSON(exportUrl, exportParams, function (resdata) {
            var meta = resdata.meta;
            if (meta && meta.code === 200) {
                //渲染
                var griddata = resdata.response.list;
                var gettpl   = document.getElementById(tableTempId).innerHTML;
                laytpl(gettpl).render(griddata, function (html) {
                    var theadHtml      = $("#" + tableId).find("thead").html();
                    var allHtml        = "<table><thead>" + theadHtml + "</thead><tbody>" + html + "</tbody></table>";
                    var exportFileName = $('.custom-font-localmenu').text();
                    $(allHtml).tableExport({type: 'excel', escape: 'false', ignoreColumn: opsCol, fileName: exportFileName});
                });
            } else {
                layer.alert(meta.message, 0);
            }
        });
    }
    /**
     * JQuery的封装版本(判断返回参数http与meta的值如果不为200则提示错误信息)
     * serialize 参数用来序列化页面输入等。如需要以set方式把选择的加起来 可以在元素上加 data-set="true"
     * @param options
     * @returns {*}
     */
    kirin.ajax = function (options) {

        // 默认参数
        var defaults = {
            traditional: true,
            dataType   : 'json',
            type       : 'GET',
            serialize  : null,
            debug      : false,
            async      : true
        };

        // 覆盖插件默认参数
        var opts = $.extend({}, defaults, options);

        var debug = opts.debug;

        //序列化
        if (opts.serialize) {
            var data      = {},
                serialize = $(opts.serialize);

            // 直接提取
            $(serialize).filter('input[type!=checkbox],select,textarea').filter('input[type!=radio],select,textarea').each(function () {
                var that      = $(this),
                    name      = that.attr('name'),
                    nowValues = that.val();

                name || alert('序列化未设置元素[name]属性：' + that.html());

                if ($.isArray(nowValues)) {
                    $.each(nowValues, function (idx, value) {
                        nowValues[idx] = $.trim(value);
                    });
                } else {
                    nowValues = $.trim(nowValues);
                }

                if (!nowValues) {
                    return;
                }

                var oldValues = data[name];
                if (oldValues) {
                    data[name] = [].concat(oldValues, nowValues);
                } else {
                    data[name] = nowValues;
                }
            });

            //checkbox 如果加了data-set 则使值相加
            $(serialize).filter('input[type=checkbox]:checked').each(function () {
                var that      = $(this),
                    name      = that.attr('name'),
                    bitSet    = (that.data('set') == true),
                    nowValues = $.trim(that.val());

                if (!nowValues) {
                    return;
                }

                name || alert('序列化未设置元素[name]属性：' + that.html());

                //Set位运算
                if (bitSet) {
                    isNaN(nowValues) && alert('序列化元素[value]属性不为数字：' + that.html());

                    var oldValues = data[name];
                    if (oldValues) {
                        data[name] = oldValues + parseInt(nowValues);
                    } else {
                        data[name] = parseInt(nowValues);
                    }
                } else {
                    var oldValues = data[name];
                    if (oldValues) {
                        data[name] = [].concat(oldValues, nowValues);
                    } else {
                        data[name] = nowValues;
                    }
                }
            });

            //radio
            $(serialize).filter('input[type=radio]:checked').each(function () {
                var that      = $(this),
                    name      = that.attr('name'),
                    nowValues = $.trim(that.val());

                if (!nowValues) {
                    return;
                }

                name || alert('序列化未设置元素[name]属性：' + that.html());

                var oldValues = data[name];
                if (oldValues) {
                    data[name] = [].concat(oldValues, nowValues);
                } else {
                    data[name] = nowValues;
                }
            });

            opts.data = $.extend({}, opts.data, data);

            debug && console.log(opts.data);
        }

        //服务端不支持 DELETE、PUT等操作
        var type = opts.type.toUpperCase();
        if ($.inArray(opts.type.toUpperCase(), ['GET', 'POST']) === -1) {
            opts.type       = 'POST';
            opts.data       = opts.data || {};
            opts.data[type] = true;
        }

        // 使用promise的方式
        var defer = $.Deferred();

        $.ajax(opts).done(function (data) {

            var meta = data.meta, response = data.response;

            // 服务器成功返回
            if (meta.code === 200) {
                defer.resolve(response);
            } else {
                switch (meta.code) {
                    case 400:
                    case 401:
                    case 403:
                        layer.alert(meta.message, {icon: 0});
                        break;
                    case 500:
                        layer.alert(meta.message, {icon: 5});
                        break;
                    default:
                        layer.alert(meta.message, {icon: 5});
                }
                defer.reject(meta);
            }

        }).fail(function (data) {
            switch (data.status) {
                case 400:
                    layer.alert("请求参数存在错误！", {icon: 0});
                    break;
                case 401:
                    layer.alert("未登录或此帐号在别处被登录！", {icon: 0});
                    break;
                case 403:
                    layer.alert("没有操作权限！", {icon: 0});
                    break;
                case 404:
                    layer.alert("请求的地址不存在！", {icon: 5});
                    break;
                case 405:
                    layer.alert("请求提交方式错误！", {icon: 0});
                    break;
                case 500:
                default:
                    layer.alert("服务器开小差了，请稍后重试！", {icon: 5});
            }
            defer.reject(data);
        });

        return defer.promise();
    };

    /**
     * 根据模版绑定分页数据
     * @param options tpl:模版ID | view：html填充选择器 | pageSize：每页条数 | transform：服务端返回的数据是否需要转换一下 | complete：渲染完成后的回调
     * @param pageing 获取服务端分页数据Ajax（请直接返回：promise）
     */
    kirin.pageing = function (options, pageing) {

        /**
         * 构造器
         * @param opts 参数
         * @param pageing 获取服务端分页数据Ajax（请直接返回：promise）
         * @constructor
         */
        var PageingConstructor = function (options, pageing) {

            // 默认参数
            var defaults = {
                pageInfo  : '#page-info',
                pageBar   : '#page-bar',
                pageNum   : 1,
                pageSize  : 10,
                transform : null,
                noDataText: '暂无数据'
            };

            // 覆盖插件默认参数
            this._opts = $.extend({}, defaults, options);

            // 获取数据的回调
            this._pageing = pageing;

            //初始化
            this.jumpPageing();

        };

        /**
         * 给原型对象填充方法
         * @type {{init: Function, reload: Function, jumpPageing: Function}}
         */
        PageingConstructor.prototype = {

            /**
             * 初始化到第一页
             */
            init: function () {
                this._opts.pageNum = 1;
                this.jumpPageing();
            },

            /**
             * 跳转到指定页
             */
            go: function (pageNum) {
                this._opts.pageNum = pageNum;
                this.jumpPageing();
            },

            /**
             * 刷新当前页
             */
            reload: function () {
                this.jumpPageing();
            },

            /**
             * 获取数据并填充分页
             */
            jumpPageing: function () {

                var that = this, opts = this._opts, pageing = this._pageing;

                pageing(opts).done(function (data) {
                    //是否存在转换程序
                    opts.transform && (data = opts.transform(data));

                    if (!data.list || data.list.length === 0) {

                        //如果数据为空的情况下，暂无数据
                        var count = $('thead tr th', opts.view).length;
                        var html  = kirin.format('<tr class="u-tr-no-data"><th colspan="{0}">{1}</th></tr>', count, opts.noDataText);
                        $('tbody', opts.view).html(html);
                    } else {

                        //渲染模版
                        laytpl($(opts.tpl).html()).render(data.list, function (html) {
                            $('tbody', opts.view).html(html);
                            opts.complete && opts.complete(data);
                        });
                    }

                    //分页
                    laypage({
                        cont : $(opts.pageBar),
                        pages: data.pages,
                        curr : data.pageNum,
                        jump : function (e, first) {
                            if (!first) {
                                opts.pageNum  = e.curr;
                                opts.pageSize = data.pageSize;
                                that.jumpPageing(opts);
                            }
                            //渲染分页导航条
                            var pageInfo = $(opts.pageInfo);
                            pageInfo.html('第' + e.curr + '页 &nbsp; 共' + e.pages + '页 &nbsp; &nbsp; 每页<select class="page-size" style="width: 65px;"><option value="10">10</option><option value="20">20</option><option value="50">50</option><option value="100">100</option></select>条 &nbsp;共' + data.total + '条记录');
                            var pageSize = $('.page-size', pageInfo);
                            pageSize.val(data.pageSize);
                            pageSize.on('change', function () {
                                opts.pageNum  = 1;
                                opts.pageSize = $('.page-size', pageInfo).val();
                                that.jumpPageing(opts);
                            });
                        }
                    });
                });
            }
        };

        return new PageingConstructor(options, pageing);
    };


    /**
     * 渲染表格
     * @param options tpl:模版ID | view：html填充选择器 | transform：服务端返回的数据是否需要转换一下 | complete：渲染完成后的回调
     * @param data Array或服务端获取的数据（请直接返回：promise）
     * @returns {RenderConstructor}
     */
    kirin.tableRender = function (options, data) {

        /**
         * 构造器
         * @param opts 参数
         * @param callback 数据（支持单个对象与数组）
         * @constructor
         */
        var RenderConstructor = function (options, callback) {

            // 默认参数
            var defaults = {
                noDataClass : 'u-tr-no-data-min',
                noDataText  : '暂无数据'
            };

            // 覆盖插件默认参数
            this._opts = $.extend({}, defaults, options);

            var that = this;

            if ($.isFunction(callback)) {

                var opts = this._opts;

                callback(opts).done(function (data) {

                    //是否存在转换程序
                    opts.transform && (data = opts.transform(data));

                    // 渲染数据转换成数组
                    that._dataList = (data && ($.isArray(data) ? data : [data])) || [];

                    // 初始化
                    that.init();

                });

            } else {

                // 渲染数据转换成数组
                this._dataList = (callback && ($.isArray(callback) ? callback : [callback])) || [];

                // 初始化
                this.init();
            }

        };

        /**
         * 给原型对象填充方法
         * @type {{init: Function, pushData: Function, dataList: Function, remove: Function, clear: Function}}
         */
        RenderConstructor.prototype = {

            /**
             * 初始化
             */
            init: function () {

                var opts = this._opts, dataList = this._dataList;

                if (dataList.length === 0) {

                    //如果数据为空的情况下，暂无数据
                    var count = $('thead tr th', opts.view).length;
                    var html = kirin.format('<tr class="{0}"><th colspan="{1}">{2}</th></tr>', opts.noDataClass, count, opts.noDataText);

                    $('tbody', opts.view).html(html);

                } else {

                    //渲染模版
                    laytpl($(opts.tpl).html()).render(dataList, function (html) {
                        $('tbody', opts.view).html(html);
                        opts.complete && opts.complete(dataList);
                    });

                }
            },

            /**
             * 添加数据
             * @param data
             */
            pushData: function (data) {

                if (data) {
                    this._dataList = this._dataList.concat(data);
                }

                this.init();
            },

            /**
             * 添加数据到第一行
             * 
             * @param data
             */
            pushDataToFirst:function (data) {
                if(data){
                    this._dataList.unshift(data);
                }
                this.init();
            },

            /**
             * 获取所有数据
             */
            dataList: function () {
                return this._dataList;
            },

            /**
             * 删除指定行（从0开始）
             * @param i
             */
            remove: function (i) {
                var opts = this._opts, dataList = this._dataList, row = $('tbody tr:eq(' + i + ')', opts.view);
                row.addClass('animated hinge');
                dataList.splice(i, 1);
                row.remove();
                this.init();
            },

            /**
             * 清空列表数据
             */
            clear: function () {
                this._dataList = [];
                this.init();
            }

        }

        return new RenderConstructor(options, data);
    };

    /**
     * 弹出窗口
     * @type {{open: Function, done: Function, notify: Function, fail: Function}}
     */
    kirin.popup = {
        open     : function (options) {
            // 默认参数
            var defaults = {
                type      : 2,
                title     : false,
                shadeClose: false,
                shade     : [0.5, '#000'],
                maxmin    : true,
                scrollbar : false
            };

            // 覆盖插件默认参数
            var opts = $.extend({}, defaults, options);

            // 设置宽高，为空则用默认
            var width = (opts.width || 800), height = (opts.height || ($(window).height() - 50));
            opts.area = [width + 'px', height + 'px'];

            // 使用promise的方式
            var defer = $.Deferred();

            // 弹出layer
            var i = top.layer.open(opts);

            top.window.cacheMap['kirin.popup.open.' + i] = defer;

            //子页面的共享数据
            top.window.cacheMap['kirin.popup.data.' + i] = opts.shareData;

            return defer.promise();
        },
        shareData: function () {
            var i   = top.layer.getFrameIndex(window.name);
            var key = 'kirin.popup.data.' + i;
            return top.window.cacheMap[key];
        },
        success  : function (data) {
            var i     = top.layer.getFrameIndex(window.name);
            var key   = 'kirin.popup.open.' + i;
            var defer = top.window.cacheMap[key];
            defer.resolve(data);
            top.window.layer.close(i);
        },
        notify   : function (data) {
            var i     = top.layer.getFrameIndex(window.name);
            var key   = 'kirin.popup.open.' + i;
            var defer = top.window.cacheMap[key];
            defer.notify(data);
        },
        close    : function (data) {
            var i     = top.layer.getFrameIndex(window.name);
            var key   = 'kirin.popup.open.' + i;
            var defer = top.window.cacheMap[key];

            delete top.window.cacheMap[key];
            delete top.window.cacheMap['kirin.popup.data.' + i];

            defer.reject(data);
            top.window.layer.close(i);
        }

    };

    /**
     * 业务共通
     * @type {{deptSelect: Function}}
     */
    kirin.widget = {

        /**
         * 单位选择
         *
         * 例：
         * var deptList = [
         *        {deptId: 5, editFlag: true}, //editFlag true时不可取消选择
         *        {deptId: 6, editFlag: true},
         *        {deptId: 7, editFlag: true},
         *        {deptId: 8, editFlag: true}
         *     ];
         * kirin.widget.deptSelect(deptList, function (data) {
         *    //data 选择的数据对象
         *    deptList = data;
         * });
         *
         * @param data
         * @param callback
         */
        deptSelect: function (data, callback) {
            kirin.popup.open({
                title    : '单位选择',
                width    : 750,
                height   : 450,
                maxmin   : false,
                shareData: data,
                content  : '/widget/dept-select.html'
            }).done(function (data) {
                callback(data);
            });
        },


        /**
         * 人员选择
         *
         * 例：
         * var userList = [
         *        {userId: 5, editFlag: true}, //editFlag true时不可取消选择
         *        {userId: 6, editFlag: true},
         *        {userId: 7, editFlag: true},
         *        {userId: 8, editFlag: true}
         *     ];
         * kirin.widget.userSelect(userList,deptCode, function (data) {
         *    //data 选择的数据对象
         *    //deptCode 部门编号
         *    userList = data;
         * });
         *
         * @param data
         * @param callback
         */
        userSelect: function (data, deptCode , callback) {
            kirin.popup.open({
                title    : '人员选择',
                width    : 750,
                height   : 450,
                maxmin   : false,
                shareData: data,
                content  : '/widget/user-select.html?deptCode='+deptCode
            }).done(function (data) {
                callback(data);
            });
        },

        /**
         * 去掉下拉框重复元素
         */
        Removesimple:function(){
            $("select").each(function(i,n){
                var options = "";
                $(n).find("option").each(function(j,m){
                    if(options.indexOf($(m)[0].outerHTML) == -1)
                    {
                        options += $(m)[0].outerHTML;
                    }
                });
                $(n).html(options);
            });
        },

        /**
         * 发起合成作战案件
         *
         * 例：
         * var caseModel = {
         *         "baseCaseIdArray"      : [10,12,13,15], //关联案件
         *         "baseGoodIdArray"      : [12,15,18],    //关联物品
         *         "baseImageIdArray"     : [49,46,55,22], //关联图像
         *         "basePeopleIdArray"    : [45,48,46],    //关联人员
         *         "baseVideoIdArray"     : [45,48,45,66], //关联视频
         *         "caseApplyType"        : "NOT_DECLARE",
         *         "caseArrestPeopleCount": 2,
         *         "caseClearUpCount"     : 1,
         *         "caseClueContent"      : "线索情况",
         *         "caseJudgeConclusion"  : "案件研判结论",
         *         "caseKeyWords"         : "关键字",
         *         "caseNum"              : "案件编号",
         *         "caseSimpleContent"    : "简要案情",
         *         "caseTitle"            : "案件标题",
         *         "orginType"            : "SYN_CASE_BASE"
         *     };
         * @param data
         * @param callback
         */
        sponsorSyntheticCase: function (data, callback) {
            kirin.popup.open({
                title    : '添加案件信息',
                width    : 950,
                height   : 520,
                maxmin   : true,
                shareData: data,
                content  : '/synthetic/cases/case-launch-apply'
            }).done(function (data) {
                callback(data);
            });
        },

        /**
         * 发起合成作战人员
         *
         * 例：
         * var peopleModel={
         *         "baseCaseIdArray"         : [10,12,13,15], //关联案件
         *         "baseGoodIdArray"         : [12,15,18],    //关联物品
         *         "baseImageIdArray"        : [49,46,55,22], //关联图像
         *         "basePeopleIdArray"       : [45,48,46],    //关联人员
         *         "baseVideoIdArray"        : [45,48,45,66], //关联视频
         *         "peopleBaseIdentification": "身份证号",
         *         "peopleBaseName"          : "姓名",
         *         "peopleBaseSex"           : "MALE",        //性别
         *         "peopleBaseDomicile"      : "户籍地",
         *         "maritalStatus"           : "SINGLE_DOG",  //婚姻状况
         *         "birthDate"               : "2015-05-04",  //出生日期
         *         "peopleBaseAlias"         : "绰号",
         *         "peopleBasePhone"         : "电话",
         *         "peopleBaseVirtual"       : "虚拟身份",
         *         "featuresCrime"           : "作案特点",
         *         "peopleSimpleContent"     : "简要案情",
         *         "orginType"               : "SYN_CRIMINAL_PEOPLE", //来源类型
         *         "orginId"                 : 15  //来源id
         *     }
         * @param data
         * @param callback
         */
        sponsorSyntheticPeople: function (data, callback) {
            kirin.popup.open({
                title    : '添加案件信息',
                width    : 950,
                height   : 520,
                maxmin   : true,
                shareData: data,
                content  : '/synthetic/people/people-launch-apply'
            }).done(function (data) {
                callback(data);
            });
        }

    };

    /**
     * 时间格式化
     * @param timestamp
     * @param format
     * @returns {*}
     */
    kirin.dataFormat = function (timestamp, format) {
        return moment(timestamp).format(format);
    };

    /**
     * 创建选择框项
     */
    kirin.buildSelectOptions = function (select, data, defaultItem) {
        var value = $(select).data('value');
        $(select).empty();
        $(select).append("<option value=''>" + defaultItem + "</option>");
        $.each(data, function (key, value) {
            var selected = (key == value ? 'selected' : '');
            $(select).append("<option value='" + value.id + "' " + selected + " >" + value.name + "</option>");
        });
    };

    kirin.simple = function(){
        $("select").each(function(i,n){
            var options = "";
            $(n).find("option").each(function(j,m){
                if(options.indexOf($(m)[0].outerHTML) == -1)
                {
                    options += $(m)[0].outerHTML;
                }
            });
            $(n).html(options);
        });
    }
    /**
     * 关闭所有弹出的窗口
     */
    kirin.closeAllLayerWindow = function () {
        try {
            window.parent.layer.closeAll();
        } catch (e) {
            // Nothing
        }
    };

    /**
     * 根据单位ID获取单位名称，多个以逗号隔开
     * @param propId
     */
    kirin.getDeptNameById = function (id) {
        if (id == null || "" == id || id == 0) {
            return "";
        }
        var id     = id + "";
        var result = "";
        if (!deptMap) {
            $.ajax({
                url     : '/sys/dept_get_list_json.json',
                data    : {},
                cache   : false,
                async   : false,
                type    : "GET",
                dataType: 'json',
                success : function (data) {
                    var meta = data.meta;
                    if (meta && meta.code === 200) {
                        deptMap = data.response;
                    } else {
                        alert(meta.message);
                    }
                }, error: function () {
                    alert("获取业务参数失败");
                }
            });
        }
        var ids = id.split(",");
        for (var i = 0; i < ids.length; i++) {
            var num = ids[i];
            if (deptMap[num] != undefined) {
                result += deptMap[num];
                if ((i + 1) < ids.length) {
                    result += ",";
                }
            }
        }
        return result;
    };

    /**
     * 根据业务参数节点ID获取参数值
     * @param propId
     */
    kirin.getPropById = function (id) {
        if (!id) {
            return "";
        }
        var result;
        if (propertyMap) {
            result = propertyMap[id];
        }
        if (!result) {
            result = "";
            $.ajax({
                url     : '/system/property-get-info.json',
                data    : {id: id},
                cache   : false,
                async   : false,
                type    : "GET",
                dataType: 'json',
                success : function (data) {
                    var meta = data.meta;
                    if (meta && meta.code === 200) {
                        propertyMap = data.response;
                    } else {
                        alert(meta.message);
                    }
                }, error: function () {
                    alert("获取业务参数失败");
                }
            });
            id += "";
            var ids = id.split(",");
            for (var i = 0; i < ids.length; i++) {
                var num = ids[i];
                if (propertyMap[num] != undefined) {
                    result += propertyMap[num];
                    if ((i + 1) < ids.length) {
                        result += ",";
                    }
                }
            }
        }
        return result;
    };

    /**
     * 根据父节点获取业务参数列表json格式
     *   {id:id,parentId:parentId,name:name}
     * @param parentId
     */
    kirin.getPropJsonByParentId = function (parentId) {
        $.ajax({
            url     : '/system/property-get-sub.json',
            data    : {parentId: parentId},
            cache   : false,
            async   : false,
            type    : "GET",
            dataType: 'json',
            success : function (data) {
                var meta = data.meta;
                if (meta && meta.code === 200) {
                    propertyMap = data.response;
                } else {
                    alert(meta.message);
                }
            }, error: function () {
                alert("获取业务参数失败");
            }
        });
        return propertyMap;
    };

    //公开
    window.kirin = kirin;

    // 研判上下级关系 上报必须采用
    window.kirin.REPORT_PRE_YES = "2";

})(window.jQuery);