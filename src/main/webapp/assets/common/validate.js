/*!
 * 验证插件
 *
 * 内置class 校验规则
 *       v-req         必需
 *       v-phone       手机
 *       v-email       电子邮箱
 *       v-int         整数
 *       v-double      双精度
 *       v-id-card-num 身份证
 *       v-url         URL地址
 * 自定义 class 校验规则 例子
 *       rules: {
 *           'v-phone-double': {
 *               role: function(){ .... return true|false },
 *               msg: '手机号码已经存在。'
 *           }
 *       }
 * by 胡萝卜
 * Copyright 2015 Youlove, Inc.
 */
(function ($) {
    $.fn.extend({
        uloveValidate: function (options) {

            //检测用户传进来的参数是否合法
            if (!isValid(options)) {
                return this;
            }

            var $this = this;

            //使用jQuery.extend 覆盖插件默认参数
            var opts = $.extend({}, defaults, options);

            var rules = defaults._rules;

            /**
             * 是否标记错误高亮
             * @param element
             * @param errorResult
             */
            var markerError = function (element, errorResult, msg) {
                if (errorResult === true) {
                    element.addClass('validate-error');
                    opts.isFormGroup && element.closest('.form-group').addClass('has-error');
                    opts.isTableGroup && element.closest('td').prev().addClass('has-error');

                    //兼容使用了chosen的情况
                    if (element.css("display") === "none" && element.is('select')) {
                        element = element.next()
                    }

                    var id = element.data('tips-id');
                    layer.close(id);

                    var tipsOpt = {tipsMore: true, time: 6000};
                    if (opts.tips) {
                        tipsOpt.tips = opts.tips;
                    }
                    id = layer.tips(msg, element, tipsOpt);
                    element.data('tips-id', id);
                } else {
                    element.removeClass('validate-error');
                    opts.isFormGroup && element.closest('.form-group').removeClass('has-error');
                    opts.isTableGroup && element.closest('td').prev().removeClass('has-error');

                    //兼容使用了chosen的情况
                    if (element.css("display") === "none" && element.is('select')) {
                        element = element.next()
                    }

                    var id = element.data('tips-id');
                    layer.close(id);
                }
                return errorResult;
            };

            /**
             * 身份证验证
             * @param idNumber
             * @returns {{success: boolean, tip: string}}
             */
            var idNumberValid = function (idNumber) {
                var city = {
                    11: "北京",
                    12: "天津",
                    13: "河北",
                    14: "山西",
                    15: "内蒙古",
                    21: "辽宁",
                    22: "吉林",
                    23: "黑龙江 ",
                    31: "上海",
                    32: "江苏",
                    33: "浙江",
                    34: "安徽",
                    35: "福建",
                    36: "江西",
                    37: "山东",
                    41: "河南",
                    42: "湖北 ",
                    43: "湖南",
                    44: "广东",
                    45: "广西",
                    46: "海南",
                    50: "重庆",
                    51: "四川",
                    52: "贵州",
                    53: "云南",
                    54: "西藏 ",
                    61: "陕西",
                    62: "甘肃",
                    63: "青海",
                    64: "宁夏",
                    65: "新疆",
                    71: "台湾",
                    81: "香港",
                    82: "澳门",
                    91: "国外 "
                };
                var tip = "";
                var success = true;

                if (!idNumber || !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[012])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i.test(idNumber)) {
                    tip = "身份证号格式错误";
                    success = false;
                } else if (!city[idNumber.substr(0, 2)]) {
                    tip = "地址编码错误";
                    success = false;
                } else {
                    //18位身份证需要验证最后一位校验位
                    if (idNumber.length == 18) {
                        idNumber = idNumber.split('');
                        //∑(ai×Wi)(mod 11)
                        //加权因子
                        var factor = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
                        //校验位
                        var parity = [1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2];
                        var sum = 0;
                        var ai = 0;
                        var wi = 0;
                        for (var i = 0; i < 17; i++) {
                            ai = idNumber[i];
                            wi = factor[i];
                            sum += ai * wi;
                        }
                        var last = parity[sum % 11];
                        if (last != idNumber[17].toUpperCase()) {
                            tip = "校验位错误";
                            success = false;
                        }
                    }
                }
                return {success: success, tip: tip};
            };

            /**
             * 数组验证
             */
            var arrayVerify = function (val) {
                var flag = false;
                $.each(val, function (i, v) {
                    if (!(v === null || v === "" || v.length === 0)) {
                        flag = true;
                    }
                })
                return flag;
            }

            /**
             * 验证元素
             * @param element
             * @returns {boolean}
             */
            var validateElement = function (element) {

                var val = element.val(),
                    msg = element.data('message'),
                    placeholder = element.attr('placeholder');

                //必需
                if (element.hasClass('v-req')) {
                    var errorResult = (val === null || val === "" || val.length === 0 || val.indexOf("请选择") >= 0 || ($.isArray(val) && !arrayVerify(val)));
                    msg = msg || placeholder || '请输入必要的值。';
                    if (markerError(element, errorResult, msg)) {
                        return true;
                    }
                }

                //匹配规则
                for (var rule in rules) {
                    if (val && element.hasClass(rule)) {
                        var errorResult;
                        if (rules[rule].regex) {
                            errorResult = !(rules[rule].regex.test(val));
                        } else if (rules[rule].rule) {
                            errorResult = !(rules[rule].rule(element));
                        }
                        if (markerError(element, errorResult, rules[rule].msg)) {
                            return true;
                        }
                    }
                }

                //身份证号码
                if (val && (element.hasClass('v-id-number') || element.hasClass('v-id-card-num'))) {
                    var validateResult = idNumberValid(val);
                    if (markerError(element, !validateResult.success, validateResult.tip)) {
                        return true;
                    }
                }

                return false;
            };

            //遍历
            $this.each(function () {
                $(this).on('blur change', function () {
                    validateElement($(this));
                });
            });

            return {
                verify: function () {
                    var success = true;
                    $this.each(function () {
                        var error = validateElement($(this));
                        if (error == true) {
                            success = false;
                        }
                    });
                    return success;
                },
                clear: function () {
                    $this.each(function () {
                        markerError($(this), false);
                    });
                }
            };
        }
    });

    //默认参数
    var defaults = {
        isFormGroup: true,
        isTableGroup: false,
        _rules: {
            'v-phone': {
                /* regex: /^(((13[0-9])|(14[0-9])|(17[07])|(15[^4])|(18[0-9]))+\d{8})$/,*/
                regex: /^1[3|4|5|7|8][0-9]{9}$/,
                msg: "手机号码格式错误。"
            },
            'v-email': {
                regex: /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/,
                msg: "电子邮箱格式错误。"
            },
            'v-int': {
                regex: /^[0-9]+$/,
                msg: "只允许输入数字。"
            },
            'v-max': {
                regex: /^[0-9]{0,3}$/,
                msg: "不能超过三位数。"
            },
            'v-double': {
                regex: /^[0-9]+[.]?[0-9]*$/,
                msg: "只允许输入数字和小数点。"
            },
            'v-url': {
                regex: /^((https|http|ftp|rtsp|mms):\/\/){1}[a-zA-Z0-9.]+(:[0-9]{1,4})?/,
                msg: "URL格式错误。"
            },
            'v-reward-level': {
                rule: function (element) {
                    var result = false;
                    if ($("#bonusLevel").val() == "5") {
                        if (parseFloat($("#money").val()) >= 2 && parseFloat($("#money").val()) <= 50) {
                            result = true;
                        }
                    }
                    if ($("#bonusLevel").val() == "4") {
                        if (parseFloat($("#money").val()) >= 51 && parseFloat($("#money").val()) <= 500) {
                            result = true;
                        }
                    }
                    if ($("#bonusLevel").val() == "3") {
                        if (parseFloat($("#money").val()) >= 501 && parseFloat($("#money").val()) <= 3000) {
                            result = true;
                        }
                    }
                    if ($("#bonusLevel").val() == "2") {
                        if (parseFloat($("#money").val()) >= 3001 && parseFloat($("#money").val()) <= 5000) {
                            result = true;
                        }
                    }
                    if ($("#bonusLevel").val() == "1") {
                        if (parseFloat($("#money").val()) >= 5001) {
                            result = true;
                        }
                    }
                    return result;
                },
                msg: "金额不在奖励级别内。"
            },
            'v-number': {
                regex: /^[0-9]+$/,
                msg: "只允许输入正整数。"
            },
            'v-number-english': {
                regex: /^[A-Za-z0-9]+$/,
                msg: "只允许输入数字和英文。"
            },
            'v-symbol-debule': {
                rule: function (element) {
                    var result;
                    var callback = function (res) {
                        result = res;
                    };
                    var informer = {};
                    informer.informerId = $("#informerId").val();
                    informer.symbol = element.val();
                    $.ajax({
                        type: 'POST',
                        url: '/informer/informer-serach.json',
                        async: false,
                        data: informer,
                        success: function (result) {
                            var meta = result.meta;
                            if (meta.code == 200) {
                                callback(result.response);
                            } else {
                                callback(false);
                                layer.alert('服务器开小差了!');
                            }
                        },
                        dataType: 'json'
                    });
                    return result;
                },
                msg: "代号重复。"
            },
            'v-phone-debule': {
                rule: function (element) {
                    var result;
                    var callback = function (res) {
                        result = res;
                    };
                    var informer = {};
                    informer.informerId = $("#informerId").val();
                    informer.phone = $("#phone").val();
                    $.ajax({
                        type: 'POST',
                        url: '/informer/informer-serach.json',
                        async: false,
                        data: informer,
                        success: function (result) {
                            var meta = result.meta;
                            if (meta.code == 200) {
                                callback(result.response);
                            } else {
                                callback(false);
                                layer.alert('服务器开小差了!');
                            }
                        },
                        dataType: 'json'
                    });
                    return result;
                },
                msg: "电话号码重复。"
            },
            'v-id-card-debule': {
                rule: function (element) {
                    var result;
                    var callback = function (res) {
                        result = res;
                    };
                    var informer = {};
                    informer.informerId = $("#informerId").val();
                    informer.idCardNum = $("#idCardNum").val();
                    $.ajax({
                        type: 'POST',
                        url: '/informer/informer-serach.json',
                        async: false,
                        data: informer,
                        success: function (result) {
                            var meta = result.meta;
                            if (meta.code == 200) {
                                callback(result.response);
                            } else {
                                callback(false);
                                layer.alert('服务器开小差了!');
                            }
                        },
                        dataType: 'json'
                    });
                    return result;
                },
                msg: "身份证号码重复。"
            },
            //验证用户帐号是否重复
            'v-account-double': {
                rule: function (element) {
                    var result;
                    var callback = function (res) {
                        result = res;
                    };
                    var user = {};
                    user.userId = $(".user-id").val();
                    user.account = $("#account").val();
                    kirin.ajax({
                        //type   : 'POST',
                        url: '/user/verify.json',
                        async: false,
                        data: user
                    }).done(function (data) {
                        result = data.exist;
                    });
                    return result;
                }, msg: '用户帐号已经存在。'
            },
            //验证手机号是否重复
            'v-phone-double': {
                rule: function (element) {
                    var result;
                    var callback = function (res) {
                        result = res;
                    };
                    var user = {};
                    user.userId = $(".user-id").val();
                    user.phone = $("#phone").val();
                    kirin.ajax({
                        //type   : 'POST',
                        url: '/user/verify.json',
                        async: false,
                        data: user
                    }).done(function (data) {
                        result = data.exist;
                    });
                    return result;
                }, msg: '手机号码已经存在。'
            },
            //验证身份证是否重复
            'v-id-card-num-double': {
                rule: function (element) {
                    var result;
                    var callback = function (res) {
                        result = res;
                    };
                    var user = {};
                    user.userId = $(".user-id").val();
                    user.idCardNum = $("#idCardNum").val();
                    kirin.ajax({
                        //type   : 'POST',
                        url: '/user/verify.json',
                        async: false,
                        data: user
                    }).done(function (data) {
                        result = data.exist;
                    });
                    return result;
                }, msg: '身份证号码已经存在。'
            }
        }
    };

    //私有方法，检测参数是否合法
    function isValid(options) {
        return !options || (options && typeof options === "object") ? true : false;
    };

})(window.jQuery);