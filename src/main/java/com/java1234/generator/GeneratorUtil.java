package com.java1234.generator;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Maps;
import org.mybatis.generator.api.dom.java.JavaElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.nio.file.*;
import java.nio.file.attribute.FileAttribute;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

public class GeneratorUtil {
    private static final Logger logger;
    private static final Map<String, String> prop;

    private static Map<String, String> initProp() {
        Map<String, String> map = Maps.newHashMap();
        map.put("author", System.getProperty("user.name"));
        try {
            final String userHome = System.getProperty("user.home");
            final Path userHomePath = Paths.get(userHome, ".generator");
            if (Files.notExists(userHomePath, new LinkOption[0])) {
                Files.createDirectories(userHomePath, (FileAttribute<?>[]) new FileAttribute[0]);
                GeneratorUtil.logger.info("Create the [.generator] directory in [user.home] success!");
            }
            final Path profile = Paths.get(userHomePath.toString(), "generator.json");
            if (Files.notExists(profile, new LinkOption[0])) {
                Files.createFile(profile, (FileAttribute<?>[]) new FileAttribute[0]);
                GeneratorUtil.logger.info("Create the [generator.json] file success!");
                final BufferedWriter bw = Files.newBufferedWriter(profile, new OpenOption[0]);
                bw.write(JSON.toJSONString((Object) map, new SerializerFeature[]{SerializerFeature.PrettyFormat}));
                bw.close();
                GeneratorUtil.logger.info("Write config data in [generator.json] file success!");
            }
            final String jsonStr = Files.lines(profile).collect(Collectors.joining());
            GeneratorUtil.logger.info("generator config = \n {}", (Object) jsonStr);
            map = (Map<String, String>) JSON.parseObject(jsonStr, (Class) Map.class);
        } catch (Exception e) {
            GeneratorUtil.logger.error("init generator config error", (Throwable) e);
        }
        return map;
    }

    public static String getConfig(final String key) {
        return GeneratorUtil.prop.get(key);
    }

    public static String getAuthor() {
        return getConfig("author");
    }

    public static void fillJavaDocLine(final JavaElement element, final String comment, final ClassType classType) {
        element.addJavaDocLine("/**");
        element.addJavaDocLine(" * " + comment + ((classType != null) ? classType.getComment() : ""));
        element.addJavaDocLine(" * ");
        element.addJavaDocLine(" * @author " + getAuthor() + " " + getDateByYYYYMD());
        element.addJavaDocLine(" * @since 南阳理工学院-辣条");
        element.addJavaDocLine(" */");
    }

    private static String getDateByYYYYMD() {
        return new SimpleDateFormat("yyyy-M-d").format(new Date());
    }

    static {
        logger = LoggerFactory.getLogger((Class) GeneratorUtil.class);
        prop = initProp();
    }
}
