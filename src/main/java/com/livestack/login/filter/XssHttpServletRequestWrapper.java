package com.livestack.login.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.lang3.StringUtils;

public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    public XssHttpServletRequestWrapper(HttpServletRequest servletRequest) {
        super(servletRequest);
    }

    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);

        if (values == null) {
            return null;
        }

        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = replaceXSSCharacters((values[i]));
        }

        return encodedValues;
    }

    private String replaceXSSCharacters(String value) {
        if (value == null) {
            return null;
        }

        return value
                .replace("&","&#38;")
                .replace("<", "&#60;")
                .replace(">","&#62;")
                .replace("\"","&#34;")
                .replace("'","&#39;");
    }

    @Override
    public String getParameter(String parameter) {
        return replaceXSSCharacters(super.getParameter(parameter));
    }

    @Override
    public String getHeader(String name) {
        return replaceXSSCharacters(super.getHeader(name));
    }

}

