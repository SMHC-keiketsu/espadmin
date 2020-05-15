package me.smhc.utils;

import cn.hutool.core.util.ObjectUtil;
import me.smhc.exception.BadRequestException;
import org.apache.commons.codec.language.bm.Rule;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证工具
 *
 * @author Zheng Jie
 * @date 2018-11-23
 */
public class ValidationUtil {

    /**
     * 验证空
     */
    public static void isNull(Object obj, String entity, String parameter, Object value) {
        if (ObjectUtil.isNull(obj)) {
            String msg = entity + " 不存在: " + parameter + " is " + value;
            throw new BadRequestException(msg);
        }
    }

    /**
     * 验证是否为邮箱
     */
    public static boolean isEmail(String email) {
        return new EmailValidator().isValid(email, null);
    }

    /**
     * 验证是否是指定长度
     * @param str
     * @param equal
     * @return
     */
    public static boolean checkLength(String str,int equal) throws UnsupportedEncodingException {

        if (str == null) {
            return false;
        }
        str = str.trim();
        while (str.startsWith("　")) {//这里判断字符串首是不是全角空格
            str = str.substring(1, str.length()).trim();
        }
        while (str.endsWith("　")) {//这里判断字符串尾是不是全角空格
            str = str.substring(0, str.length() - 1).trim();
        }
        int styl = str.getBytes("utf-8").length;
        return (styl == equal);
    }

    /**
     * 验证是否在指定长度内
     * @param str
     * @param max
     * @return
     */
    public static boolean limitLength(String str, int max) throws UnsupportedEncodingException {
        if(str == null){ return false; }
        str = str.trim();
        while (str.startsWith("　")) {//这里判断字符串首是不是全角空格
            str = str.substring(1, str.length()).trim();
        }
        while (str.endsWith("　")) {//这里判断字符串尾是不是全角空格
            str = str.substring(0, str.length() - 1).trim();
        }
        int styl = str.getBytes("utf-8").length;
        return (styl < max);
    }

    /**
     * 验证日期格式是否正确(yyyy/MM/dd)
     * @param data
     * @return
     */
    public static boolean isValidDate(String data) {
        if(data == null){ return false; }
        boolean convertSuccess=true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        try {
            format.setLenient(false);
            format.parse(data);
        } catch (ParseException e) {
            convertSuccess=false;
        }
        return convertSuccess;
    }

    /**
     * 验证是否为半角数字
     * @param numeric
     * @return
     */
    public static boolean isHalfNumeric(String numeric) {
        if(numeric == null){ return false; }
        return Pattern.matches("^[0-9]+$", numeric);
    }

    /**
     * 验证是否为半角英字
     * @param halfalpha
     * @return
     */
    public static boolean isHalfAlpha(String halfalpha) {
        if(halfalpha == null){ return false; }
        return Pattern.matches("^[\\p{Alpha}]+$", halfalpha);
    }

    /**
     * 验证半角大写英文和特殊符号
     * @param str
     * @return
     */
    public static boolean isHankakuCapitalLetterEngAndPun(String str) {
        return str.matches("^[A-Z[\"-_@+!;:#¥$%&*\"]]*$");
    }

    /**
     * 验证半角大写英文和数字
     * @param str
     * @return
     */
    public static boolean isHankakuCapitalLetterEngAndNum(String str) {
        if (str == null) {
            return false;
        }
        return str.matches("^[A-Z0-9]*$");
    }

    /**
     * 验证住所地址是否包含和文(全英文NG)
     * @param str
     * @return
     */
    public static boolean isJapanese(String str) {
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            Character.UnicodeBlock unicodeBlock = Character.UnicodeBlock.of(ch);

            if (Character.UnicodeBlock.HIRAGANA.equals(unicodeBlock))
                return true;

            if (Character.UnicodeBlock.KATAKANA.equals(unicodeBlock))
                return true;

            if (Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS.equals(unicodeBlock))
                return true;

            if (Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS.equals(unicodeBlock))
                return true;

            if (Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION.equals(unicodeBlock))
                return true;
        }
        return false;
    }


    /**
     * 验证半角英文数字和标点(全角以外)
     * @param str
     * @return
     */
    public static boolean isHankakuEngAndNumAndPun(String str) {
        if (str == null) {
            return false;
        }
        Pattern p = Pattern.compile("^[a-zA-Z0-9,-. ]*$");
        Matcher m = p.matcher(str);
        return m.find();
    }

}