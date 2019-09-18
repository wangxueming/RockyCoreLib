package com.rockyw.projectcore.widget.popup;

import android.widget.PopupWindow;

/**
 *
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/4/8
 */
public class ListPopupMenuBean {

    public interface OnClickListener {
        /**
         * 列表中item点击后的回调函数
         * @param popup
         * @param s
         * @param pos
         */
        void onClick(PopupWindow popup, String s, int pos);
    }

    public static class Title {

        public String titleText;

        public Title(String titleText) {
            this.titleText = titleText;
        }

        public void setTitleText(String titleText) {
            this.titleText = titleText;
        }
    }

    public static class Menu {

        public String menuText;
        public int menuIcon = 0;
        public boolean showDivider = false;
        public int selectedIndex = 0;
        public OnClickListener listener;

        public Menu(String menuText, OnClickListener listener) {
            this(menuText, 0, listener);
        }

        public Menu(String menuText, int menuIcon, OnClickListener listener) {
            this.menuText = menuText;
            this.menuIcon = menuIcon;
            this.listener = listener;
        }

        public void setMenuText(String menuText) {
            this.menuText = menuText;
        }
    }
}
