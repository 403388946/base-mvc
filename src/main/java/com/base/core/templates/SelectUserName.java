package com.base.core.templates;

import com.base.core.spring.SpringUtils;
import com.base.shiro.model.User;
import com.base.shiro.service.UserService;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * @author YiMing on 2016/10/24.
 */
public class SelectUserName extends TagSupport {
    private static final long serialVersionUID = 3262283612415519155L;
    private Long userId;
    private String username;
    private User user;

    public int doStartTag() throws JspException {
        return 0;
    }

    public int doEndTag() throws JspException {
        if(userId == null && StringUtils.isEmpty(username)) {
            return 6;
        } else {
            UserService userService = SpringUtils.getBean(UserService.class);
            user = userService.findOne(userId);
            if(user == null) {
                user = userService.findByUsername(username);
            }
            try {
                this.pageContext.getOut().write(user.getRealName());
                return 6;
            } catch (IOException e) {
                throw new JspException(e);
            }
        }
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
