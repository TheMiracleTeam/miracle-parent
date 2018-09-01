package com.miracle.platform.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.miracle.common.annotation.ZookeeperWatch;
import com.miracle.common.util.FieldUtil;
import com.miracle.platform.common.ConfigKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 平台页面配置
 * Created at 2018-09-01 16:57:08
 * @author Allen
 */
@Component
public class PageSetupCnf {

    private static final Logger LOGGER = LoggerFactory.getLogger(PageSetupCnf.class);

    private static JSONObject json;
    /**
     * navigationList : [{"text":"首页","value":"index"},{"text":"公告","value":"notice"},{"text":"关于我们","value":"about"}]
     * themeColor : #000
     * tip : Welcome to Miracle!
     * setting : {"page":14,"colors":["#FFF","#666"]}
     */
    private static String themeColor;
    private static String tip;
    private static SettingBean setting;
    private static List<NavigationListBean> navigationList;

    @ZookeeperWatch(path = ConfigKey.PAGE_SETUP)
    public void init(String conf) {
        if (conf != null && !"".equals(conf)) {
            json = JSON.parseObject(conf);
            FieldUtil.fieldInit(this, json);
//            Field[] fields = getClass().getDeclaredFields();
//            for (Field field : fields) {
//                if (json.containsKey(field.getName())) {
//                    try {
//                        field.set(this, json.getObject(field.getName(), field.getType()));
//                    } catch (IllegalAccessException e) {
//                        LOGGER.error("公共配置[{}]字段[{}]数据初始化失败！JSON数据为：{}", getClass().getName(),
//                                field.getName(), conf);
//                    }
//                }
//            }
        }
    }

    public static JSONObject getJson() {
        return json;
    }

    public static void setJson(JSONObject json) {
        PageSetupCnf.json = json;
    }

    public static String getThemeColor() {
        return themeColor;
    }

    public static void setThemeColor(String themeColor) {
        PageSetupCnf.themeColor = themeColor;
    }

    public static String getTip() {
        return tip;
    }

    public static void setTip(String tip) {
        PageSetupCnf.tip = tip;
    }

    public static SettingBean getSetting() {
        return setting;
    }

    public static void setSetting(SettingBean setting) {
        PageSetupCnf.setting = setting;
    }

    public static List<NavigationListBean> getNavigationList() {
        return navigationList;
    }

    public static void setNavigationList(List<NavigationListBean> navigationList) {
        PageSetupCnf.navigationList = navigationList;
    }

    public static class SettingBean {
        /**
         * page : 14
         * colors : ["#FFF","#666"]
         */

        public int page;
        public List<String> colors;
    }

    public static class NavigationListBean {
        /**
         * text : 首页
         * value : index
         */

        public String text;
        public String value;
    }
}
