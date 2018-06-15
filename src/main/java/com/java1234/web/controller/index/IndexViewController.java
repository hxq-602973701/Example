package com.java1234.web.controller.index;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.java1234.dal.annotation.AuthCheck;
import com.java1234.dal.constant.page.SystemPageID;
import com.java1234.dal.entity.main.book.Book;
import com.java1234.dal.entity.main.sys.menu.Menu;
import com.java1234.dal.entity.main.sys.user.User;
import com.java1234.dal.utils.DataPipe;
import com.java1234.dal.utils.LoginContext;
import com.java1234.service.book.BookService;
import com.java1234.service.sys.menu.MenuService;
import com.java1234.util.Ids;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2018/6/14.
 */
@Controller
public class IndexViewController {

    /**
     * 菜单Service
     */
    @Resource
    private MenuService menuService;

    /**
     * 图书Service
     */
    @Resource
    private BookService bookService;

    /**
     * 系统通知消息
     */
    public static final String SYSTEM_NOTIFY_MESSAGE_URL = "/hello";


    /**
     * 系统消息提示（铃铛提示）
     *
     * @return
     */
    @AuthCheck(page = SystemPageID.SYSTEM_NOTIFY_MESSAGE)
    @RequestMapping(value = "/project/system/notify-message", method = RequestMethod.GET)
    public void pingSystemMessageApi(final Model model) {

        User user = LoginContext.getUser();
        if (user != null) {

            //封装所有消息
            List<Map<String, Object>> msgMapList = Lists.newArrayList();

            //获取系统图书数量
            if (checkFunction(SYSTEM_NOTIFY_MESSAGE_URL)) {
                Book book = new Book();
                List<Book> bookList = bookService.select(book);
                msgMapList.addAll(getSystemMsgMap(bookList, SYSTEM_NOTIFY_MESSAGE_URL, "系统图书数量"));
            }

            //按时间排序
            List<Map<String, Object>> mapList = msgMapList.parallelStream()
                    .sorted((m1, m2) -> Ids.compare(Long.valueOf(m2.get("gmtCreate").toString()), Long.valueOf(m1.get("gmtCreate").toString())))
                    .collect(Collectors.toList());

            DataPipe.in(model).response(mapList);
        }

    }

    /**
     * 封装情报消息Map
     *
     * @param paramList
     * @param url
     */
    private List<Map<String, Object>> getSystemMsgMap(List<Book> paramList, String url, String type) {

        List<Map<String, Object>> msgMapList = Lists.newArrayList();
        paramList.forEach(book -> {
            Map<String, Object> map = Maps.newHashMap();
            map.put("url", url);
            map.put("title", book.getBookName() + "(" + type + ")");
            map.put("gmtCreate", book.getCreateTime().getTime());
            msgMapList.add(map);
        });
        return msgMapList;
    }

    /**
     * 校验是否有权限
     *
     * @param url
     * @return
     */
    private boolean checkFunction(String url) {
        boolean flag = false;
        User user = LoginContext.getUser();
        Long auth = user.getAuthType();
        List<Menu> menuList = menuService.selectAll();
        for (Menu menu : menuList) {
            boolean hasAuth = (menu.getMenuParentId() == 2 || menu.getMenuParentId() == 3)
                    && url.equals(menu.getMenuUrl()) && (menu.getMenuRoles() & auth) > 0;
            if (hasAuth) {
                flag = true;
                break;
            }
        }
        return flag;
    }
}
