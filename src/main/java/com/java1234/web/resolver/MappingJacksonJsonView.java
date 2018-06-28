package com.java1234.web.resolver;

/**
 * Created by Administrator on 2018/1/30.
 */

import com.google.common.collect.Maps;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

public class MappingJacksonJsonView extends MappingJackson2JsonView {
    public MappingJacksonJsonView() {
    }

    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LinkedHashMap jsonModel = Maps.newLinkedHashMap();
        Object metaObj = model.get("meta");
        if (metaObj != null) {
            jsonModel.put("meta", metaObj);
        }

        Object responseObj = model.get("response");
        if (responseObj != null) {
            jsonModel.put("response", responseObj);
        }

        super.render(jsonModel, request, response);
    }
}
