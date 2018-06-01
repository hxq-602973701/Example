/*!
 * 文件上传插件
 *
 * //只允许图片类型
 * $('#file-upload').uploader({
 *     accept: {
 *         title: 'image',
 *         extensions: 'gif,jpg,jpeg,bmp,png',
 *         mimeTypes: 'image/*'
 *     }
 * });
 *
 * //只允许类型
 * $('#file-upload').uploader({
 *     accept: {
 *         title : 'Excel',
 *         extensions : 'xls',
 *         mimeTypes : 'application/vnd.ms-excel'
 *     }
 * });
 *
 * by 胡萝卜
 * Copyright 2016 Youlove, Inc.
 */
(function ($) {
    $.fn.extend({
        uploader: function (options) {

            //检测用户传进来的参数是否合法
            if (!isValid(options)) {
                return this;
            }

            var $this = this;

            //使用jQuery.extend 覆盖插件默认参数
            var opts = $.extend({}, defaults, options);

            var iconMap = {
                ppt: 'fa fa-file-powerpoint-o',
                pptx: 'fa fa-file-powerpoint-o',
                xls: 'fa fa-file-excel-o',
                xlsx: 'fa fa-file-excel-o',
                doc: 'fa fa-file-word-o',
                docx: 'fa fa-file-word-o',
                mp3: 'fa fa-file-audio-o',
                ogg: 'fa fa-file-audio-o',
                mp4: 'fa fa-file-video-o',
            };

            /**z
             * 初始化图片框
             */
            var initItem = function (file) {
                var name = file.name || file.fileName || file.originalFilename,
                    ext = /\.([^.]+)$/.exec(name) ? RegExp.$1 : '';
                if (/(gif|jpg|jpeg|png|GIF|JPG|JPEG|PNG)/.test(ext)) {
                    if (file.url) {
                        return '<img width="60" height="60" src="' + file.url + '" />';
                    }
                    return '<img width="60" height="60"/>';
                } else {
                    var iconClass = iconMap[ext] || 'fa fa-file-text';
                    return '<i class="' + iconClass + '"></i>';
                }
            }

            /**
             * 初始化上传控件
             * @param element
             * @returns {boolean}
             */
            var initWebUploader = function (element) {

                var $element = $(element),
                    attachments = $element.val(),
                    readonly = (opts.readonly || ($element.attr("readonly") === 'readonly')),
                    $uploaderWrap = $('<div class="uploader"><ul class="file-list"><li class="add-file"></li></ul></div>'),
                    $queueList = $uploaderWrap.find('ul.file-list'),
                    $fileAdd = $uploaderWrap.find('li.add-file'),
                    $fileList = [];

                try {
                    $fileList = JSON.parse((attachments == '0' ? '' : attachments) || '[]');
                } catch (e) {
                    $.each(attachments.split('|'), function (idx, val) {
                        var ext = /\.([^.]+)$/.exec(val) ? RegExp.$1 : '';
                        $fileList.push({
                            originalFilename: idx + '.' + ext,
                            url: val,
                            ext: ext,
                            type: 'FILE',
                            id: Date.now()
                        });
                    });
                }

                $element.hide().before($uploaderWrap);

                if (readonly && $fileList.length === 0) {
                    $uploaderWrap.hide();
                }

                // 初始化
                (function () {
                    $.each($fileList, function (idx, file) {
                        //构建li结构
                        var $li = $('<li data-id="' + file.id + '" title="' + file.originalFilename + '">' +
                                '    <a class="file-icon" href="' + file.url + '" download="' + file.originalFilename + '" target="_blank">' +
                                '        <p>' + initItem(file) + '</p>' +
                                '    </a>' +
                                '    <p class="title">' +
                                '        <span>' + file.originalFilename + '</span>' +
                                '    </p>' +
                                '    <p class="progress">' +
                                '        <span></span>' +
                                '    </p>' +
                                '    <div class="file-panel">' +
                                '        <span class="delete"></span>' +
                                '    </div>' +
                                '</li>'),
                            $item = $li.find("div.file-panel"),
                            $delete = $li.find("span.delete");

                        if (!readonly) {
                            $li.on('mouseenter', function () {
                                $item.stop().animate({height: 20});
                                $delete.stop().animate({height: 15});
                            });
                            $li.on('mouseleave', function () {
                                $item.stop().animate({height: 0});
                                $delete.stop().animate({height: 0});
                            });
                            $delete.on('click', function () {
                                $li.remove();
                                $.each($fileList, function (i, file) {
                                    if (file.id == $li.data('id')) {
                                        $fileList.splice(i, 1);
                                        return false;
                                    }
                                });
                                $element.val(JSON.stringify($fileList));
                            });
                        } else {
                            $fileAdd.hide();
                        }

                        $fileAdd.before($li);
                    });
                })();

                if (!readonly) {
                    //初始化上传组件
                    var uploader = WebUploader.create({
                        // swf文件路径
                        swf: '/assets/js/plugins/webuploader/Uploader.swf',
                        server: '/common/file_upload/file.json',

                        pick: {
                            id: $fileAdd,
                            label: '<i class="glyphicon glyphicon-plus"></i>'
                        },
                        dnd: $queueList,
                        paste: $uploaderWrap,
                        accept: opts.accept,
                        disableGlobalDnd: false,
                        chunked: false,
                        auto: true,
                        fileNumLimit: 100,
                        fileSizeLimit: 1024 * 1024 * 1024 * 200,    // 200G
                        fileSingleSizeLimit: 1024 * 1024 * 1024 * 2,    // 2G

                        //缩略图大小
                        width: 60,
                        height: 60,
                        // 图片质量，只有type为`image/jpeg`的时候才有效。
                        quality: 70,
                        // 是否允许放大，如果想要生成小图的时候不失真，此选项应该设置为false.
                        allowMagnify: true,
                        // 是否允许裁剪。
                        crop: true,
                        // 为空的话则保留原有图片格式，否则强制转换成指定的类型。
                        type: 'image/jpeg'
                    });

                    /**
                     * 文件上传进度
                     */
                    uploader.on('uploadProgress', function (file, percent) {
                        file.trigger('uploadProgress', percent);
                    });

                    /**
                     * 当一批文件添加进队列以后触发
                     */
                    uploader.on('fileQueued', function (file) {
                        //构建li结构
                        var $li = $('<li data-id="' + file.id + '" title="' + file.name + '">' +
                                '    <a class="file-icon" target="_blank">' +
                                '        <p>' + initItem(file) + '</p>' +
                                '    </a>' +
                                '    <p class="title">' +
                                '        <span>' + file.name + '</span>' +
                                '    </p>' +
                                '    <p class="progress">' +
                                '        <span></span>' +
                                '    </p>' +
                                '    <div class="file-panel">' +
                                '        <span class="delete"></span>' +
                                '    </div>' +
                                '</li>'),
                            $item = $li.find("div.file-panel"),
                            $delete = $li.find("span.delete");

                        $li.on('mouseenter', function () {
                            $item.stop().animate({height: 20});
                            $delete.stop().animate({height: 15});
                        });
                        $li.on('mouseleave', function () {
                            $item.stop().animate({height: 0});
                            $delete.stop().animate({height: 0});
                        });
                        $delete.on('click', function () {
                            $li.remove();
                            uploader.removeFile(file);
                            $.each($fileList, function (i, file) {
                                if (file.id == $li.data('id')) {
                                    $fileList.splice(i, 1);
                                    return false;
                                }
                            });
                            $element.val(JSON.stringify($fileList));
                        });

                        //压缩图片
                        uploader.makeThumb(file, function (error, src) {
                            if (error) {
                                $li.find('a p').empty().append($(initItem(file)));
                            } else {
                                $li.find('a p').empty().append($('<img src="' + src + '">'));
                            }
                            $fileAdd.before($li);
                        }, opts.thumbnailWidth, opts.thumbnailHeight);

                        //文件上传进度
                        file.on('uploadProgress', function (percent) {
                            $li.find('p.progress span').width(Math.round(percent * 100) + "%");
                        });

                        //文件状态变化
                        file.on('statuschange', function (cur, prev) {
                            if (cur === 'complete') {
                                $li.find('p.progress').hide();
                            }
                        });

                        //文件上传成功
                        file.on('uploadSuccess', function (result) {
                            $li.attr('data-id', result.id);
                            $li.find('a').attr('href', result.url);
                        });

                    });

                    /**
                     * 文件上传成功
                     */
                    uploader.on('uploadSuccess', function (file, data) {
                        var result = data.response;
                        result.id = Date.now();
                        $fileList.push(result);
                        $element.val(JSON.stringify($fileList));
                        file.trigger('uploadSuccess', result);
                    });

                    /**
                     * 文件上传错误
                     */
                    uploader.on('error', function (code) {
                        if ('F_DUPLICATE' === code) {
                            alertMsg('您添加的文件已经存在。');
                        } else {
                            alertMsg(code);
                        }
                    });
                }
            };

            //遍历
            $this.each(function () {
                initWebUploader($(this));
            });

            return {};
        }
    });

    //默认参数
    var defaults = {
        // 优化retina, 在retina下这个值是2
        ratio: window["devicePixelRatio"] || 1,
        // 缩略图大小
        thumbnailWidth: 60,
        thumbnailHeight: 60,
        accept: {
            title: '文件',
            mimeTypes: '*/*'
        }
    };

    /**
     * 提示框
     * @param msg
     */
    var alertMsg = function (msg) {
        if (window.layer) {
            layer.msg(msg);
        } else {
            alert(msg);
        }
    }

    /**
     * 检测参数是否合法
     * @param options
     * @returns {boolean}
     */
    var isValid = function (options) {
        return !options || (options && typeof options === "object") ? true : false;
    }

})(window.jQuery);