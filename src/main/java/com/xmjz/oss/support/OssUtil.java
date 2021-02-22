package com.xmjz.oss.support;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

public class OssUtil {
    /**
     * 路径占位符 可扩展
     */
    private static final Map<String, Object> PATH_PLACEHOLDER_MAP;
    /**
     * 占位符替换器 可自行修改前后缀之类
     */
    private static final StrSubstitutor STR_SUBSTITUTOR;

    static {
        PATH_PLACEHOLDER_MAP = new SupplierMap<>();
        Function<String, String> dateFormater = pattern -> DateFormatUtils.format(new Date(), pattern);
        Supplier<String> year = () -> dateFormater.apply("yyyy");
        Supplier<String> month = () -> dateFormater.apply("MM");
        Supplier<String> day = () -> dateFormater.apply("dd");
        Supplier<String> date = () -> dateFormater.apply("yyyyMMdd");
        Supplier<String> hour = () -> dateFormater.apply("HH");
        Supplier<String> minute = () -> dateFormater.apply("mm");
        Supplier<String> second = () -> dateFormater.apply("ss");
        Supplier<String> time = () -> dateFormater.apply("HHmmss");
        Supplier<String> millisecond = () -> dateFormater.apply("SSS");

        Supplier<String> uuid = () -> UUID.randomUUID().toString().replace("-", "");
        Supplier<Long> timestamp = System::currentTimeMillis;

        Supplier<String> randomLetters6 = () -> RandomStringUtils.random(6, true, false);
        Supplier<String> randomLetters8 = () -> RandomStringUtils.random(8, true, false);
        Supplier<String> randomNumbers6 = () -> RandomStringUtils.random(6, false, true);
        Supplier<String> randomNumbers8 = () -> RandomStringUtils.random(8, false, true);

        PATH_PLACEHOLDER_MAP.put("year", year);
        PATH_PLACEHOLDER_MAP.put("month", month);
        PATH_PLACEHOLDER_MAP.put("day", day);
        PATH_PLACEHOLDER_MAP.put("date", date);
        PATH_PLACEHOLDER_MAP.put("hour", hour);
        PATH_PLACEHOLDER_MAP.put("minute", minute);
        PATH_PLACEHOLDER_MAP.put("second", second);
        PATH_PLACEHOLDER_MAP.put("time", time);
        PATH_PLACEHOLDER_MAP.put("millisecond", millisecond);

        PATH_PLACEHOLDER_MAP.put("uuid", uuid);
        PATH_PLACEHOLDER_MAP.put("timestamp", timestamp);

        PATH_PLACEHOLDER_MAP.put("randomLetters6", randomLetters6);
        PATH_PLACEHOLDER_MAP.put("randomLetters8", randomLetters8);
        PATH_PLACEHOLDER_MAP.put("randomNumbers6", randomNumbers6);
        PATH_PLACEHOLDER_MAP.put("randomNumbers8", randomNumbers8);

        STR_SUBSTITUTOR = new StrSubstitutor(PATH_PLACEHOLDER_MAP, "#{", "}", '#');
    }

    public static Map<String, Object> getPathPlaceholderMap() {
        return PATH_PLACEHOLDER_MAP;
    }

    public static StrSubstitutor getStrSubstitutor() {
        return STR_SUBSTITUTOR;
    }

    /**
     * 路径占位符替换 支持的占位符有:#{uuid}, #{timestamp}, #{year}, #{month}, #{day}, #{date}, #{hour}, #{minute}, #{second}, #{time}, #{millisecond}
     * #{randomLetters6}, #{randomLetters8}, #{randomNumbers6}, #{randomNumbers8}
     *
     * @param path 路径
     * @return 解析后的路径
     * @see OssUtil#PATH_PLACEHOLDER_MAP
     */
    public static String parsePath(String path) {
        return STR_SUBSTITUTOR.replace(path);
    }

    /**
     * 从文件名获取后缀
     *
     * @param fileName 文件名
     * @return 后缀 带点 例如.jpg
     */
    public static String getSuffixFromFileName(String fileName) {
        return getSuffixFromFileName(fileName, false);
    }

    /**
     * 从文件名获取后缀
     *
     * @param fileName 文件名
     * @param noDot    返回的后缀是否带点
     * @return 后缀
     */
    public static String getSuffixFromFileName(String fileName, boolean noDot) {
        if (fileName == null) {
            return "";
        }
        int i = fileName.lastIndexOf(".");
        if (i == -1) {
            return "";
        }
        return noDot ? fileName.substring(i + 1) : fileName.substring(i);
    }
}