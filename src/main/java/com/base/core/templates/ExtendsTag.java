package com.base.core.templates;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * @author YiMing on 2016/8/16.
 */
public class ExtendsTag  extends BodyTagSupport {
    private static final long serialVersionUID = 4230936938910999444L;
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public int doStartTag() throws JspException {
        return this.isOverrided()?0:2;
    }

    public int doEndTag() throws JspException {
        if(this.isOverrided()) {
            return 6;
        } else {
            //覆盖body的内容
            BodyContent b = this.getBodyContent();
            String overrideName = Utils.getOverrideVariableName(this.name);
            this.pageContext.getRequest().setAttribute(overrideName, b.getString());
            return 6;
        }
    }

    private boolean isOverrided() {
        String variableName = Utils.getOverrideVariableName(this.name);
        return this.pageContext.getRequest().getAttribute(variableName) != null;
    }
}
