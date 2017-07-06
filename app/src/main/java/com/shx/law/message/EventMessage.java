package com.shx.law.message;

/**
 * Created by 邵鸿轩 on 2017/7/6.
 */

public class EventMessage {
    private String from;
    private String to;
    private int selectMenu;
    private String tabItem;
    private String searchType;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getSelectMenu() {
        return selectMenu;
    }

    public void setSelectMenu(int selectMenu) {
        this.selectMenu = selectMenu;
    }

    public String getTabItem() {
        return tabItem;
    }

    public void setTabItem(String tabItem) {
        this.tabItem = tabItem;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }
}
