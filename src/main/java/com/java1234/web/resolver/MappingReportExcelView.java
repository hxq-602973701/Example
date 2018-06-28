package com.java1234.web.resolver;

/**
 * Created by Administrator on 2018/1/30.
 */

import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java1234.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.view.AbstractView;

public class MappingReportExcelView extends AbstractView {
    public static final String DEFAULT_CONTENT_TYPE = "application/vnd.ms-excel";

    public MappingReportExcelView() {
    }

    public String getContentType() {
        return "application/vnd.ms-excel";
    }

    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String excelContent = model.getOrDefault("excelContent", "").toString();
        if (StringUtils.isNotBlank(excelContent)) {
            String excelStream = model.getOrDefault("fileName", "").toString();
            if (StringUtils.isNotBlank(excelStream)) {
                excelStream = URLEncoder.encode(excelStream, "UTF-8") + "_" + DateUtil.format(Long.valueOf(System.currentTimeMillis()), "yyyyMMddHHmm") + ".xls";
                response.addHeader("Content-Disposition", "attachment;filename=" + excelStream);
            }

            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            bao.write(excelContent.getBytes());
            this.writeToResponse(response, bao);
        } else {
            ByteArrayOutputStream excelStream1 = (ByteArrayOutputStream) model.get("excelStream");
            if (excelStream1 != null) {
                this.writeToResponse(response, excelStream1);
            }
        }

    }
}