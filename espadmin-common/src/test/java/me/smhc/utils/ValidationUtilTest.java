package me.smhc.utils;

import org.junit.Test;

import static me.smhc.utils.FileUtil.getExtensionName;
import java.io.UnsupportedEncodingException;
import static me.smhc.utils.ValidationUtil.*;
import static org.junit.Assert.assertEquals;

public class ValidationUtilTest {

    /**
     * 控制行数
     * checkLength为验证是否是指定长度
     * limitLength为验证是否在指定长度内
     */
    @Test
    public void testCheckStrLength() throws UnsupportedEncodingException  {
        assertEquals(true, checkLength("12345",5));
        assertEquals(true, checkLength("12345 ",5));
        assertEquals(true, checkLength("12345　",5));
        assertEquals(true, checkLength(" 12345　",5));
        assertEquals(true, checkLength("　12345 ",5));
        assertEquals(true, checkLength("　12345　",5));
        assertEquals(true, checkLength(" 12345 ",5));
        assertEquals(true, checkLength("  　12345　 　",5));
        assertEquals(true, checkLength("cbd",3));
        assertEquals(true, checkLength("あ",3));
        assertEquals(true, checkLength("あいab1",9));
        assertEquals(true, checkLength("１２３",9));
        assertEquals(true, checkLength("　1234あ ",7));
        assertEquals(true, checkLength("上海",6));
        assertEquals(false, checkLength(null,6));
        assertEquals(false, checkLength("123456",5));
        assertEquals(false, checkLength("１２３４",3));
        assertEquals(false, checkLength("あいうえお",4));
        assertEquals(false, checkLength("JAY　C",6));
    }
    @Test
    public void testLimitLength() throws UnsupportedEncodingException {
        assertEquals(true, limitLength("12345",6));
        assertEquals(true, limitLength("12345 ",6));
        assertEquals(true, limitLength("12345　",6));
        assertEquals(true, limitLength(" 12345　",6));
        assertEquals(true, limitLength("　12345 ",6));
        assertEquals(true, limitLength("　12345　",6));
        assertEquals(true, limitLength(" 12345 ",6));
        assertEquals(true, limitLength("  　12345　 　",6));
        assertEquals(true, limitLength("１２３４５",16));
        assertEquals(true, limitLength("１２３ab",12));
        assertEquals(true, limitLength("１２３！#",14));
        assertEquals(true, limitLength(" 1234あ ",8));
        assertEquals(true, limitLength("饕餮",7));
        assertEquals(false, limitLength(null,5));
        assertEquals(false, limitLength("123456",5));
        assertEquals(false, limitLength("１２３４",4));
        assertEquals(false, limitLength("あいうえお",5));
        assertEquals(false, limitLength("JAY　C",7));
    }

    /**
     * 验证日期格式是否正确
     */
    @Test
    public void testIsValidDate() {
        assertEquals(true, isValidDate("2013/3/3"));
        assertEquals(true, isValidDate("2020/2/29"));
        assertEquals(false, isValidDate(null));
        assertEquals(false, isValidDate("3/32/2013"));
        assertEquals(false, isValidDate("2/29/2013"));
        assertEquals(false, isValidDate("12/23/2012"));
    }

    /**
     * 验证是否为半角数字
     */
    @Test
    public void testIsHalfNumeric() {
        assertEquals(true, isHalfNumeric("1234"));
        assertEquals(false, isHalfNumeric(null));
        assertEquals(false, isHalfNumeric("１２３４"));
        assertEquals(false, isHalfNumeric("1１２"));
        assertEquals(false, isHalfNumeric("asd123"));
    }

    /**
     * 验证是否为半角英字
     */
    @Test
    public void testIsHalfAlpha() {
        assertEquals(true, isHalfAlpha("JAY"));
        assertEquals(true, isHalfAlpha("jay"));
        assertEquals(false, isHalfAlpha(null));
        assertEquals(false, isHalfAlpha("ＪＡＹ"));
        assertEquals(false, isHalfAlpha("ｊａｙ"));
        assertEquals(false, isHalfAlpha("Ｊａｙ"));
        assertEquals(false, isHalfAlpha("JAY６６６"));
        assertEquals(false, isHalfAlpha("JAY666"));
        assertEquals(false, isHalfAlpha("JAYや"));
        assertEquals(false, isHalfAlpha("JAY、"));
    }

    /**
     * 验证半角大写英文和特殊符号
     */
    @Test
    public void testIsHankakuCapitalLetterEngAndPun(){
        assertEquals(true, isHankakuCapitalLetterEngAndPun("FOB"));
        assertEquals(true, isHankakuCapitalLetterEngAndPun("C&I"));
        assertEquals(true, isHankakuCapitalLetterEngAndPun("$&*"));
        assertEquals(false, isHankakuCapitalLetterEngAndPun("fob"));
        assertEquals(false, isHankakuCapitalLetterEngAndPun("C&i"));
        assertEquals(false, isHankakuCapitalLetterEngAndPun("Ｃ&Ｉ"));

    }

    /**
     * 验证半角大写英文和数字
     */
    @Test
    public void testIsHankakuCapitalLetterEngAndNum() {
        assertEquals(true, isHankakuCapitalLetterEngAndNum("GI4101"));
        assertEquals(true, isHankakuCapitalLetterEngAndNum("FLIGHT"));
        assertEquals(true, isHankakuCapitalLetterEngAndNum("123456"));
        assertEquals(false, isHankakuCapitalLetterEngAndNum("gi4101"));
        assertEquals(false, isHankakuCapitalLetterEngAndNum("flight"));
        assertEquals(false, isHankakuCapitalLetterEngAndNum("ＦＬＩＧＨＴ"));
        assertEquals(false, isHankakuCapitalLetterEngAndNum("１２３４５６"));
        assertEquals(false, isHankakuCapitalLetterEngAndNum(null));
    }

    /**
     * 验证住所地址是否包含和文(全英文NG)
     */
    @Test
    public void testIsJapanese(){
        assertEquals(true, isJapanese("群馬県邑楽郡明和町中谷  100MFLP厚木Ⅱ "));
        assertEquals(true, isJapanese("福岡県北九州市八幡西区木屋瀬  1-10-15"));
        assertEquals(false, isJapanese("HuaJiang Road JiaDing District"));
        assertEquals(false, isJapanese("Unit 205 of 36 buildings and 3 units in Chunhe 2 District"));
        assertEquals(false, isJapanese("TOKYO TO TOSHIMA KU HIGASHIIKEBUKURO TSUGINOBIRUONOZO 4-5-1 "));
    }


    /**
     * 验证半角英文数字和标点(全角以外)
     */
    @Test
    public void testIsHankakuEngAndNumAndPun() {
        assertEquals(true, isHankakuEngAndNumAndPun("TOKYO TO TOSHIMA 4-5-1"));
        assertEquals(true, isHankakuEngAndNumAndPun("lane 102,No.5 Yang Road 345"));
        assertEquals(false, isHankakuEngAndNumAndPun("ＴＯＫＹＯ　ＴＯＳＨＩＢＡ"));
        assertEquals(false, isHankakuEngAndNumAndPun("１２３"));
        assertEquals(false, isHankakuEngAndNumAndPun("ＴＯＫＹＯ　ＴＯＳＨＩＢＡ　１２３"));
        assertEquals(false, isHankakuEngAndNumAndPun("lane 102， No.5 Yang Road　１２３"));
        assertEquals(false, isHankakuEngAndNumAndPun(null));
    }

}
