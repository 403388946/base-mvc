package com.base.core.templates;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * @author YiMing on 2016/8/16.
 */
public class BlockTag extends TagSupport {
    private static final long serialVersionUID = -5658405869941401194L;
    private String name;


    public void setName(String name) {
        this.name = name;
    }

    public int doStartTag() throws JspException {
        return this.getOverriedContent() == null?1:0;
    }

    public int doEndTag() throws JspException {
        //需要写入的内容
        String overriedContent = this.getOverriedContent();
        if(overriedContent == null) {
            return 6;
        } else {
            try {
                this.pageContext.getOut().write(overriedContent);
                return 6;
            } catch (IOException e) {
                throw new JspException("write overridedContent occer IOException,block name:" + this.name, e);
            }
        }
    }

    private String getOverriedContent() {
        //需要重写的变量名
        String variableName = Utils.getOverrideVariableName(this.name);
        return (String)this.pageContext.getRequest().getAttribute(variableName);
    }
}
