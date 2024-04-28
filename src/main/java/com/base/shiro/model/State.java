package com.base.shiro.model;

public class State {
    private Boolean opened;
    private Boolean disabled;
    private Boolean selected;

    public State() {
        this.opened = true;
    }

    public State(Boolean opened, Boolean disabled, Boolean selected) {
        this.opened = opened;
        this.disabled = disabled;
        this.selected = selected;
    }

    public Boolean getOpened() {
        return opened;
    }

    public void setOpened(Boolean opened) {
        this.opened = opened;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
