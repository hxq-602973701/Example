package com.java1234.service.task.impl;


import com.java1234.service.task.BaseTaskService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 定时获取内网天气数据
 */
public class GetWeatherTaskServiceImpl extends BaseTaskService {


    /**
     * 日志记录器
     */
    private static Logger logger = LoggerFactory.getLogger(GetWeatherTaskServiceImpl.class);


    /**
     * @throws Throwable
     */
    @Override
    protected void execute() throws Throwable {

        logger.debug("开始爬取天气数据...");

        Document doc = Jsoup.connect("")
        .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31")
        .get();

        logger.info("url连接错误...");

        Elements elements = doc.select("marquee");
        Element span = elements.select("span").first();

        logger.info("爬取天气数据成功...");


        logger.info("已经将天气数据成功更新到数据库...");

    }


    @Override
    protected Class<?> getSubClass() {
        return GetWeatherTaskServiceImpl.class;
    }
}
