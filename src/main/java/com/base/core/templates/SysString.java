package com.base.core.templates;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;

/**
 * @author YiMing on 2016/8/16.
 */
public class SysString extends BodyTagSupport {
    private static final long serialVersionUID = -2199380827602500032L;
    private String value;
    private int length = 100;
    private Boolean replaceHtml;
    private String suffix;

    public SysString() {
        this.replaceHtml = Boolean.FALSE;
        this.suffix = "...";
    }

    public int doStartTag() throws JspException {
        return 0;
    }

    public int doEndTag() throws JspException {
        if(StringUtils.isEmpty(this.value)) {
            return 6;
        } else {
            if(this.getReplaceHtml().booleanValue()) {
                this.value = this.replaceHtml(this.value);
            }

            if(this.value.length() >= this.length) {
                this.value = this.value.substring(0, this.length) + this.suffix;
            }

            try {
                this.pageContext.getOut().write(this.value);
                return 6;
            } catch (IOException e) {
                throw new JspException(e);
            }
        }
    }

    public String replaceHtml(String html) {
        if(html != null && !html.trim().equals("")) {
            String replaced = html.replaceAll("\\&[a-zA-Z]{1,10};", "");
            replaced = replaced.replaceAll("<[^>]*>", "");
            replaced = replaced.replaceAll("[(/>)<]", "");
            return replaced;
        } else {
            return "";
        }
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Boolean getReplaceHtml() {
        return this.replaceHtml;
    }

    public void setReplaceHtml(Boolean replaceHtml) {
        this.replaceHtml = replaceHtml;
    }
}
