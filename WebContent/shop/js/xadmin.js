var QINIU_DOMAIN = "http://yhf.pub/";		//七牛域名

$(function () {
    //加载弹出层
    layui.use(['form', 'element'],
        function () {
            layer = layui.layer;
            element = layui.element;
        });

    //触发事件
    var tab = {
        tabAdd: function (title, url, id) {
            //新增一个Tab项
            element.tabAdd('xbs_tab', {
                title: title
                ,
                content: '<iframe tab-id="' + id + '" id="' + id + '" frameborder="0" src="' + url + '" scrolling="yes" class="x-iframe"></iframe>'
                ,
                id: id
            })
        }
        , tabDelete: function (othis) {
            //删除指定Tab项
            element.tabDelete('xbs_tab', '44'); //删除：“商品管理”


            othis.addClass('layui-btn-disabled');
        }
        , tabChange: function (id) {
            //切换到指定Tab项
            element.tabChange('xbs_tab', id); //切换到：用户管理

            $('#' + id).attr('src', $('#' + id).attr('src'));  //刷新当前iframe
        }
    };


    tableCheck = {
        init: function () {
            $(".layui-form-checkbox").click(function (event) {
                if ($(this).hasClass('layui-form-checked')) {
                    $(this).removeClass('layui-form-checked');
                    if ($(this).hasClass('header')) {
                        $(".layui-form-checkbox").removeClass('layui-form-checked');
                    }
                } else {
                    $(this).addClass('layui-form-checked');
                    if ($(this).hasClass('header')) {
                        $(".layui-form-checkbox").addClass('layui-form-checked');
                    }
                }

            });
        },
        getData: function () {
            var obj = $(".layui-form-checked").not('.header');
            var arr = [];
            obj.each(function (index, el) {
                arr.push(obj.eq(index).attr('data-id'));
            });
            return arr;
        }
    }

    //开启表格多选
    tableCheck.init();


    $('.container .left_open i').click(function (event) {
        if ($('.left-nav').css('left') == '0px') {
            $('.left-nav').animate({left: '-221px'}, 100);
            $('.page-content').animate({left: '0px'}, 100);
            $('.page-content-bg').hide();
        } else {
            $('.left-nav').animate({left: '0px'}, 100);
            $('.page-content').animate({left: '221px'}, 100);
            if ($(window).width() < 768) {
                $('.page-content-bg').show();
            }
        }

    });

    $('.page-content-bg').click(function (event) {
        $('.left-nav').animate({left: '-221px'}, 100);
        $('.page-content').animate({left: '0px'}, 100);
        $(this).hide();
    });

    $('.layui-tab-close').click(function (event) {
        $('.layui-tab-title li').eq(0).find('i').remove();
    });

    $("tbody.x-cate tr[fid!='0']").hide();
    // 栏目多级显示效果
    $('.x-show').click(function () {
        if ($(this).attr('status') == 'true') {
            $(this).html('&#xe625;');
            $(this).attr('status', 'false');
            cateId = $(this).parents('tr').attr('cate-id');
            $("tbody tr[fid=" + cateId + "]").show();
        } else {
            cateIds = [];
            $(this).html('&#xe623;');
            $(this).attr('status', 'true');
            cateId = $(this).parents('tr').attr('cate-id');
            getCateId(cateId);
            for (var i in cateIds) {
                $("tbody tr[cate-id=" + cateIds[i] + "]").hide().find('.x-show').html('&#xe623;').attr('status', 'true');
            }
        }
    })

    //左侧菜单效果
    // $('#content').bind("click",function(event){
    $('.left-nav #nav li').click(function (event) {

        if ($(this).children('.sub-menu').length) {
            if ($(this).hasClass('open')) {
                $(this).removeClass('open');
                $(this).find('.nav_right').html('&#xe697;');
                $(this).children('.sub-menu').stop().slideUp();
                $(this).siblings().children('.sub-menu').slideUp();
            } else {
                $(this).addClass('open');
                $(this).children('a').find('.nav_right').html('&#xe6a6;');
                $(this).children('.sub-menu').stop().slideDown();
                $(this).siblings().children('.sub-menu').stop().slideUp();
                $(this).siblings().find('.nav_right').html('&#xe697;');
                $(this).siblings().removeClass('open');
            }
        } else {

            var url = $(this).children('a').attr('_href');
            var title = $(this).find('cite').html();
            //var index = $('.left-nav #nav li').index($(this));
            var index = $('.sub-menu li').index($(this));
            console.log("index:" + index);
            for (var i = 0; i < $('.x-iframe').length; i++) {
                if ($('.x-iframe').eq(i).attr('tab-id') == index + 1) {
                    tab.tabChange(index + 1);
                    event.stopPropagation();
                    return;
                }
            }


            tab.tabAdd(title, url, index + 1);
            tab.tabChange(index + 1);
        }

        event.stopPropagation();

    })


    $('.add-tap').click(function (event) {
        var url = $(this).attr('_href');
        //var title = $(this).find('cite').html();
        var title = "增加tap";
        //var index  = $('.left-nav #nav li').index($(this));

        var iframeLen = $('.layui-tab-content div', window.parent.document).length;
        var navLen = $('.sub-menu li', window.parent.document).length;
        console.log("iframeLen:" + iframeLen);
        console.log("navLen:" + navLen);

        var id;
        var iframLen=$('.x-iframe',window.parent.document).length;
        console.log("flag");
        console.log("iframe length:"+length);
        for (var i = 0; i < iframLen; i++) {
            console.log("for run");
            var urlTemp = $('.x-iframe',window.parent.document).eq(i).attr('src');
            console.log("urlTemp:"+urlTemp);
            if ( urlTemp == url) {
                id = $('.x-iframe',window.parent.document).eq(i).attr('tab-id');
                console.log("idTemp:"+id);
                console.log("已有页面，直接跳转!");
                console.log("已有页面，直接跳转!"+$('.x-iframe',window.parent.document).eq(i).attr('src'));

                console.log("已有页面，直接跳转!"+id);
                parent.element.tabChange('xbs_tab', id); //切换到：用户管理
                event.stopPropagation();
                return;
            }
        }
        if (iframeLen > navLen) {
            id = iframeLen + 1;
        } else {
            id = navLen + 1;
        }
        console.log('id:' + id);
        console.log('url:' + url);
        console.log('title:' + title);
        parent.element.tabAdd('xbs_tab', {
            title: title
            ,
            content: '<iframe tab-id="' + id + '" id="' + id + '" frameborder="0" src="' + url + '" scrolling="yes" class="x-iframe"></iframe>'
            ,
            id: id
        })

        parent.element.tabChange('xbs_tab', id); //切换到：用户管理

        $('#' + id,window.parent.document).attr('src', $('#' + id).attr('src'));  //刷新当前iframe
    })


})
var cateIds = [];

function getCateId(cateId) {

    $("tbody tr[fid=" + cateId + "]").each(function (index, el) {
        id = $(el).attr('cate-id');
        cateIds.push(id);
        getCateId(id);
    });
}

/*弹出层*/

/*
    参数解释：
    title   标题
    url     请求的url
    id      需要操作的数据id
    w       弹出层宽度（缺省调默认值）
    h       弹出层高度（缺省调默认值）
*/
function x_admin_show(title, url, w, h) {
    if (title == null || title == '') {
        title = false;
    }
    ;
    if (url == null || url == '') {
        url = "404.html";
    }
    ;
    if (w == null || w == '') {
        w = ($(window).width() * 0.9);
    }
    ;
    if (h == null || h == '') {
        h = ($(window).height() - 50);
    }
    ;
    layer.open({
        type: 2,
        area: [w + 'px', h + 'px'],
        fix: false, //不固定
        maxmin: true,
        shadeClose: true,
        shade: 0.4,
        title: title,
        content: url
    });
}

/*关闭弹出框口*/
function x_admin_close() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}


//时间格式化批处理
//时间格式化
//dateFtt("yyyy-MM-dd hh:mm:ss",DateTime);
//例1
//String Time = dateFtt("yyyy-MM-dd hh:mm:ss",new Date());//返回字符串："2018-7-24 21:13:28"
//例2
//String Time = dateFtt("yyyy-MM-dd",new Date());//返回："2018-7-24"
function dateFtt(fmt, date) {
    var o = {
        "M+": date.getMonth() + 1,                 //月份
        "d+": date.getDate(),                    //日
        "h+": date.getHours(),                   //小时
        "m+": date.getMinutes(),                 //分
        "s+": date.getSeconds(),                 //秒
        "q+": Math.floor((date.getMonth() + 3) / 3), //季度
        "S": date.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
}


/*生成图片时间戳*/
function createImgKey() {
    var key = "";
    var myDate = new Date();
    var year = myDate.getFullYear() //获取完整的年份(4位,1970-????)
    var month = myDate.getMonth() + 1; //获取当前月份(0-11,0代表1月)
    var day = myDate.getDate(); //获取当前日(1-31)
    var hour = myDate.getHours(); //获取当前小时数(0-23)
    var minute = myDate.getMinutes(); //获取当前分钟数(0-59)
    var second = myDate.getSeconds(); //获取当前秒数(0-59)
    var milliSeconds = myDate.getMilliseconds(); //获取当前毫秒数(0-999)
    month = PrefixInteger(month, 2);
    day = PrefixInteger(day, 2);
    hour = PrefixInteger(hour, 2);
    minute = PrefixInteger(minute, 2);
    second = PrefixInteger(second, 2);
    milliSeconds = PrefixInteger(Math.floor(milliSeconds / 10), 2);
    key = year + month + day + hour + minute + second + milliSeconds;
    return key;
}

function PrefixInteger(num, length) {
    return (Array(length).join('0') + num).slice(-length);
}

/**
 * 更新类的属性值
 * @param oldObj
 * @param newObj
 */
function objUpdate(oldObj, newObj) {
    var keys = Object.keys(newObj);

    for (var i = 0, len = keys.length; i < len; i++) {
        oldObj[keys[i]] = newObj[keys[i]];
    }
    return oldObj;
}



