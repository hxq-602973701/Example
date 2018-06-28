package com.java1234.web.filter;

/**
 * Created by Administrator on 2018/1/30.
 */

import java.io.IOException;
import java.util.Locale;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import com.java1234.util.Config;
import org.springframework.web.filter.OncePerRequestFilter;

public class HiddenHttpMethodFilter extends OncePerRequestFilter {
    public HiddenHttpMethodFilter() {
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Config.getRunInstance();
        String putValue = request.getParameter("PUT");
        String deleteValue = request.getParameter("DELETE");
        String postValue = request.getParameter("POST");
        if (putValue == null && deleteValue == null && postValue == null) {
            filterChain.doFilter(request, response);
        } else {
            String paramValue = putValue != null ? "PUT" : (deleteValue != null ? "DELETE" : (postValue != null ? "POST" : "GET"));
            String method = paramValue.toUpperCase(Locale.ENGLISH);
            HiddenHttpMethodFilter.HttpMethodRequestWrapper wrapper = new HiddenHttpMethodFilter.HttpMethodRequestWrapper(request, method);
            filterChain.doFilter(wrapper, response);
        }

    }

    private static class HttpMethodRequestWrapper extends HttpServletRequestWrapper {
        private final String method;

        public HttpMethodRequestWrapper(HttpServletRequest request, String method) {
            super(request);
            this.method = method;
        }

        public String getMethod() {
            return this.method;
        }
    }
}