package com.java1234.web.resolver;

/**
 * Created by Administrator on 2018/1/30.
 */
import com.google.common.collect.Maps;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

public class MappingJacksonJsonpView extends MappingJackson2JsonView {
    public static final String DEFAULT_CONTENT_TYPE = "application/javascript";

    public MappingJacksonJsonpView() {
    }

    public String getContentType() {
        return "application/javascript";
    }

    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LinkedHashMap jsonModel = Maps.newLinkedHashMap();
        Object metaObj = model.get("meta");
        if(metaObj != null) {
            jsonModel.put("meta", metaObj);
        }

        Object responseObj = model.get("response");
        if(responseObj != null) {
            jsonModel.put("response", responseObj);
        }

        Map params = request.getParameterMap();
        if(params.containsKey("callback")) {
            this.prepareResponse(request, response);
            ServletOutputStream out = response.getOutputStream();
            out.write((new String(((String[])params.get("callback"))[0] + "(")).getBytes());
            super.render(jsonModel, request, response);
            out.write((new String(");")).getBytes());
            response.setContentType("application/javascript");
        } else {
            super.render(jsonModel, request, response);
        }

    }
}
