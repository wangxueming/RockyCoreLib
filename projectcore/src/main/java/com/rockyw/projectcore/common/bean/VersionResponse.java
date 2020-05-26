package com.rockyw.projectcore.common.bean;

/**
 * 版本更新日志
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2020/5/26
 */
public class VersionResponse {

    /**
     * code : 1
     * message : ok
     * data : {"pid":"5a003716dd337b4c85c3d491","packageName":"com.gezbox.windmessage","versionCode":11,"versionName":"1.0.1","title":"版本更新","description":"1.description1; \n2.descrition2;\n 3.descritpion3; ","domain":"http://music.baidu.com/","postfix":"song/2495021?from=bh_card_song","mode":"remind","frequency":"36","upgradeTime":"","platform":"android","channel":"offical","md5":""}
     */

    public String code;
    public String message;
    public ContentBean data;

    public static class ContentBean {
        /**
         * pid : 5a003716dd337b4c85c3d491
         * packageName : com.gezbox.windmessage
         * versionCode : 11
         * versionNumber : 1.0.1
         * title : 版本更新
         * description : 1.description1;
         2.descrition2;
         3.descritpion3;
         * domain : http://music.baidu.com/
         * postfix : song/2495021?from=bh_card_song
         * type : remind
         * frequency : 36
         * upgradeTime :
         * platform : android
         * channel : offical
         * md5 :
         */

        public String id;
        public String packageName;
        public int versionCode;
        public String versionNumber;
        public String title;
        public String description;
        public String domain;
        public String postfix;
        public int type;
        public String frequency;
        public String upgradeTime;
        public String platform;
        public String channel;
        public String md5;
    }
}
