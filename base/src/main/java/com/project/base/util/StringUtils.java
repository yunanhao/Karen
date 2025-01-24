package com.project.base.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * 这个工具是 Mtk源码中提取出来供大家用的
 */
public class StringUtils {

    /**
     * Pseudo-random number generator object for use with randomString(). The
     * Random class is not considered to be cryptographically secure, so only
     * use these random Strings for low to medium security applications.
     */
    private static Random sRandGen = new Random();

    /**
     * Array of numbers and letters. Numbers appear in the list
     * twice so that there is a more equal chance that a number will be picked.
     * We can use the array to get a random number or letter by picking a random
     * array index.
     */
    private static char[] sNumbersAndLetters =
            ("0123456789abcdefghijklmnopqrstuvwxyz0123456789").toCharArray();

    /**
     * Array of numbers.
     */
    private static char[] sNumbers = ("0123456789").toCharArray();

    /**
     * This class cannot be instantiated
     */
    private StringUtils() {
    }

    /**
     * Returns a random String of numbers and letters (lower and upper case) of
     * the specified length. The method uses the Random class that is built-in
     * to Java which is suitable for low to medium grade security uses. This
     * means that the output is only pseudo random, i.e., each number is
     * mathematically generated so is not truly random.
     * <p>
     * The specified length must be at least one. If not, the method will return null.
     *
     * @param _length the desired length of the random String to return.
     * @return a random String of numbers and letters of the specified length.
     */
    public static String randomString(int _length) {
        if (_length < 1) {
            return null;
        }
        // Create a char buffer to put random letters and numbers in.  
        final char[] randBuffer = new char[_length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = sNumbersAndLetters[sRandGen.nextInt(sNumbersAndLetters.length - 1)];
        }

        return new String(randBuffer);
    }

    /**
     * Returns a random String of numbers of the specified length.
     * This means that the output is only pseudo random, i.e., each number is
     * mathematically generated so is not truly random.
     * <p>
     * The specified length must be at least one. If not, the method will return null.
     *
     * @param _length the desired length of the random String to return.
     * @return a random String of numbers of the specified length.
     */
    public static String randomStringOfNumbers(int _length) {
        if (_length < 1) {
            return null;
        }
        // Create a char buffer to put random letters and numbers in.  
        final char[] randBuffer = new char[_length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = sNumbers[sRandGen.nextInt(sNumbers.length - 1)];
        }
        return new String(randBuffer);
    }

    /**
     * Get a readable string displaying the time
     * 获得 可读的字符串显示时间
     *
     * @param _context The context (needed only for relative time)
     * @param _time    The time
     * @return The time string
     */
    public static String getTimestampAsString(Context _context, long _time) {
        final long hours = _time / 3600000;
        _time %= 3600000;
        final long mins = _time / 60000;
        _time %= 60000;
        final long sec = _time / 1000;
        _time %= 1000;
        _time /= 100;
        return String.format("%02d:%02d:%02d.%01d", hours, mins, sec, _time);
    }

    /**
     * Get a readable string displaying the time
     *
     * @param _context The context (needed only for relative time)
     * @param _time    The time
     * @return The time string
     */
    public static String getSimpleTimestampAsString(Context _context, long _time) {
        final long hours = _time / 3600000;
        _time %= 3600000;
        final long mins = _time / 60000;
        _time %= 60000;
        final long sec = _time / 1000;
        return String.format("%02d:%02d:%02d", hours, mins, sec);
    }

    /**
     * Get a readable string displaying the time 
     *
     * @param context The context (needed only for relative time) 
     * @param time The time 
     *
     * @return The time string 
     */  
    /*public static String getDurationAsString(Context context, long time) {  
        final long hours = time / 3600000;  
        time %= 3600000;  
        final long mins = time / 60000;  
        time %= 60000;  
        final long sec = time / 1000;  
  
        if (hours == 0) {  
            if (mins == 0) {  
                return String.format(context.getString(R.string.seconds), sec);  
            } else if (mins == 1) {  
                return String.format(context.getString(R.string.minute_and_seconds), sec);  
            } else {  
                return String.format(context.getString(R.string.minutes), mins);  
            }  
        } else if (hours == 1) {  
            return String.format(context.getString(R.string.hour_and_minutes), mins);  
        } else {  
            return String.format(context.getString(R.string.hours_and_minutes), hours, mins);  
        }  
    }  */

    /**
     * Trim text to a maximum size
     *
     * @param _text    The text
     * @param _p       The paint
     * @param _maxSize The maximum size
     * @return The text
     */
    public static String trimText(String _text, Paint _p, int _maxSize) {
        if (_text == null || "".equals(_text)) {//avoid ... if text is "" or null  
            return "";
        }

        final int textSize = (int) _p.measureText(_text);
        if (textSize > _maxSize) {
            final int chars = _p.breakText(_text, true, _maxSize - 12, null);
            _text = _text.substring(0, chars);
            _text += "...";
        }

        return _text;
    }

    /**
     * 校验手机
     *
     * @param phone
     * @return
     */
    public static boolean validatePhone(String phone) {
        Pattern pattern1 = Pattern.compile("^1[1,2,3,4,5,6,7,8,9,0]\\d{9}$");
        Matcher m1 = pattern1.matcher(phone);
        if (m1.matches()) {
            return true;
        }
        return false;
    }

    public static String phoneFormal(String mobile) {
        if (mobile.length() == 11) {
            return mobile.substring(0, 3) + "****" + mobile.substring(7);
        } else {
            return mobile;
        }
    }

    /**
     * 字符串按照字节截取
     *
     * @param str 字符串
     * @param len 截取字节长度
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String subStringByByte(String str, int len) throws UnsupportedEncodingException {
        String result = null;
        if (str != null) {
            byte[] a = str.getBytes("UTF-8");
            if (a.length <= len) {
                result = str;
            } else if (len > 0) {
                result = new String(a, 0, len);
                int length = result.length();
                if (str.charAt(length - 1) != result.charAt(length - 1)) {
                    if (length < 2) {
                        result = null;
                    } else {
                        result = result.substring(0, length - 1);
                    }
                }
            }
        }
        return result;
    }

    /**
     * <b>截取指定字节长度的字符串，不能返回半个汉字</b>
     * 例如：
     * 如果网页最多能显示17个汉字，那么 length 则为 34
     * StringTool.getSubString(str, 34);
     *
     * @param str
     * @param length
     * @return
     */
    public static String getSubString(String str, int length) {
        int count = 0;
        int offset = 0;
        char[] c = str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] > 256) {
                offset = 2;
                count += 2;
            } else {
                offset = 1;
                count++;
            }
            if (count == length) {
                return str.substring(0, i + 1);
            }
            if ((count == length + 1 && offset == 2)) {
                return str.substring(0, i);
            }
        }
        return str;
    }

    /**
     * 半角转换为全角
     *
     * @param input
     * @return
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 去除特殊字符或将所有中文标号替换为英文标号
     *
     * @param str
     * @return
     */
    public static String stringFilter(String str) {
        str = str.replaceAll("【", "[").replaceAll("】", "]")
                .replaceAll("！", "!").replaceAll("：", ":");// 替换中文标号  
        String regEx = "[『』]"; // 清除掉特殊字符  
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * 将字符串中间部分转为*    刚写完，还未测试，谨慎使用
     *
     * @param content 内容字符串
     * @param start   开始转换的位置
     * @param end     结束位置剩余的文字数量
     * @return 返回的结果
     */
    public static String replaceStar(String content, int start, int end) {
        if (TextUtils.isEmpty(content)) {
            return "";
        }
        String result = "";
        int length = content.length();
        if (start < 0 || start > length) {
            return content;
        }

        if (end < 0 || (end + start) > length) {
            return content;
        }
        String head = content.substring(0, start);
        String endOfStr = content.substring(length - end, length);
        int ll = length - start - end;
        String middle = "";
        for (int i = 0; i < ll; i++) {
            middle += "*";
        }
        result = head + middle + endOfStr;
        return result;
    }

    /**
     * 手机号中间四位替换为*
     *
     * @param phone phone
     * @return str
     */
    public static String getXPhone(String phone) {
        if (phone == null) {
            return null;
        }
        if (validatePhone(phone)) {
            String phoneNumber = phone.substring(0, 3) + "****" + phone.substring(7, phone.length());
            return phoneNumber;
        }
        return null;
    }

    /**
     * 手机号中间四位替换为*
     */
    public static String getXPhone2(String phone) {
        if (phone == null) {
            return "";
        }
        if (phone.length() == 11) {
            String phoneNumber = phone.substring(0, 3) + "****" + phone.substring(7);
            return phoneNumber;
        }
        return "";
    }


    /**
     * 数字保留两位
     *
     * @param f f
     * @return m2
     */
    public static String m2(String f) {
        if (f == null) {
            return "0.00";
        }
        double ff = 0;
        try {
            ff = Double.valueOf(f);
        } catch (Exception e) {
            // e.printStackTrace();
            ff = 0;
        }
        DecimalFormat formater = new DecimalFormat("#0.00");
        String m2 = formater.format(ff);
        return m2;
    }


    /***
     * 判断字符串非空
     * @param str str
     * @return boolean
     */
    public static boolean isEmpty(String str) {
        if (str == null) {
            return true;
        }
        if (str.length() == 0) {
            return true;
        }
        if (str.equals("null")) {
            return true;
        }
        if (str.equals("NULL")) {
            return true;
        }
        return false;
    }

    public static String rvZeroAndDot(String s) {
        if (s.isEmpty()) {
            return null;
        }
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
            //s = s.replaceFirst("^0*", "");//去掉前面的0
            if (s.indexOf(".") == 0) {
                s = 0 + s;
            }
        }
        return s;
    }

    /**
     * 数字保留两位，不做第三位不做四舍五入
     *
     * @param f f
     * @return m3
     */
    public static String m3(String f) {
        if (f == null) {
            return "0.00";
        }
        double ff = 0;
        try {
            ff = Double.valueOf(f);
        } catch (Exception e) {
            // e.printStackTrace();
            ff = 0;
        }
        DecimalFormat formater = new DecimalFormat("#0.00");
        formater.setRoundingMode(RoundingMode.FLOOR);
        String m2 = formater.format(ff);
        System.out.println(m2);
        return m2;
    }

    /**
     * 单位转换  将数据除以10000
     *
     * @param data 原数据
     * @return 处理后的数据
     */
    public static String unitConver(String data) {
        //为空的时候返回0
        if (TextUtils.isEmpty(data)) {
            return "0";
        }

        BigDecimal old = new BigDecimal(data);
        //小于10000的时候返回原数
        if (old.intValue() < 10000) {
            return old.stripTrailingZeros().toPlainString();
        }
        //大于10000的时候，单位改为万，返回
        BigDecimal div = new BigDecimal("10000");
        //保留2位小数
        BigDecimal num = old.divide(div, 2, BigDecimal.ROUND_DOWN);
        //去掉尾部的0
        //String result = num.stripTrailingZeros().toPlainString();
        String result = num.toPlainString();
        return result + "万";
    }

    /**
     * 减法运算
     *
     * @param front  被减数
     * @param behind 减数
     * @return 结果
     */
    public static String mathSub(String front, String behind) {
        BigDecimal ff = new BigDecimal(front);
        BigDecimal bb = new BigDecimal(behind);
        BigDecimal num = ff.subtract(bb);
        return num.toPlainString();
    }
//    除法运算
//    public static int mathDiv(String front,String behind){
//        BigDecimal ff = new BigDecimal(front);
//        BigDecimal bb = new BigDecimal(behind);
//        BigDecimal num = ff.divide(bb,1,BigDecimal.ROUND_DOWN);
//        return (int)num.doubleValue()*10;
//    }

    public static boolean isMobileNO(String mobileNums) {
        /**
         * 判断字符串是否符合手机号码格式
         * 移动号段:   134 135 136 137 138 139 147 148 150 151 152 157 158 159  165 172 178 182 183 184 187 188 198
         * 联通号段:   130 131 132 145 146 155 156 166 170 171 175 176 185 186
         * 电信号段:   133 149 153 170 173 174 177 180 181 189  191  199
         * 虚拟运营商: 170
         * @param str
         * @return 待检测的字符串
         */
        //与商城后台校验规则保持一致即可
        String telRegex = "^1[3-9]\\d{9}$";
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }

    /**
     * 是否为纯数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }


    public static Spanned string2Spanned(String s) {
        if (s.startsWith("<img")) {
            s = "";
        }
        String str = s == null ? "" : s;
        str = str.replace("\n", "<br />");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(str, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(str);

        }
    }


    /**
     * 解析iOS端特定表情 😩😩😄😄😒😒😩😩😩😩😩
     **/

    public static String emoji2android(String s) {

        if (!isEmpty(s) && s.contains("[")) {
            if (s.contains("[兴奋]"))
                s = s.replace("[兴奋]", new String(Character.toChars(0X1F603)));
            if (s.contains("[哈哈]"))
                s = s.replace("[哈哈]", new String(Character.toChars(0X1F604)));
            if (s.contains("[笑哭]"))
                s = s.replace("[笑哭]", new String(Character.toChars(0X1F602)));
            if (s.contains("[开心]"))
                s = s.replace("[开心]", new String(Character.toChars(0X1F600)));
            if (s.contains("[大笑]"))
                s = s.replace("[大笑]", new String(Character.toChars(0X1F603)));
            if (s.contains("[流汗]"))
                s = s.replace("[流汗]", new String(Character.toChars(0X1F605)));
            if (s.contains("[汗汗]"))
                s = s.replace("[汗汗]", new String(Character.toChars(0X1F605)));
            if (s.contains("[笑笑]"))
                s = s.replace("[笑笑]", new String(Character.toChars(0X1F606)));
            if (s.contains("[挤眼]"))
                s = s.replace("[挤眼]", new String(Character.toChars(0X1F609)));
            if (s.contains("[愉快]"))
                s = s.replace("[愉快]", new String(Character.toChars(0X1F60A)));
            if (s.contains("[馋鬼]"))
                s = s.replace("[馋鬼]", new String(Character.toChars(0X1F60B)));
            if (s.contains("[墨镜]"))
                s = s.replace("[墨镜]", new String(Character.toChars(0X1F60E)));

            if (s.contains("[爱你]"))
                s = s.replace("[爱你]", new String(Character.toChars(0X1F60D)));
            if (s.contains("[懵圈]"))
                s = s.replace("[懵圈]", new String(Character.toChars(0X1F633)));
            if (s.contains("[亲亲]"))
                s = s.replace("[亲亲]", new String(Character.toChars(0X1F617)));
            if (s.contains("[亲吻]"))
                s = s.replace("[亲吻]", new String(Character.toChars(0X1F619)));
            if (s.contains("[亲嘴]"))
                s = s.replace("[亲嘴]", new String(Character.toChars(0X1F61A)));
            if (s.contains("[嘻嘻]"))
                s = s.replace("[嘻嘻]", new String(Character.toChars(0X1F633)));
            if (s.contains("[天使]"))
                s = s.replace("[天使]", new String(Character.toChars(0X1F607)));
            if (s.contains("[平静]"))
                s = s.replace("[平静]", new String(Character.toChars(0X1F610)));
            if (s.contains("[难过]"))
                s = s.replace("[难过]", new String(Character.toChars(0X1F61E)));
            if (s.contains("[失落]"))
                s = s.replace("[失落]", new String(Character.toChars(0X1F627)));
            if (s.contains("[无感]"))
                s = s.replace("[无感]", new String(Character.toChars(0X1F62E)));

            if (s.contains("[思考]"))
                s = s.replace("[思考]", new String(Character.toChars(0X1F914)));
            if (s.contains("[愉悦]"))
                s = s.replace("[愉悦]", new String(Character.toChars(0X1F61D)));
            if (s.contains("[嘿嘿]"))
                s = s.replace("[嘿嘿]", new String(Character.toChars(0X1F601)));
            if (s.contains("[吐舌]"))
                s = s.replace("[吐舌]", new String(Character.toChars(0X1F61B)));
            if (s.contains("[鬼脸]"))
                s = s.replace("[鬼脸]", new String(Character.toChars(0X1F92A)));
            if (s.contains("[诧异]"))
                s = s.replace("[诧异]", new String(Character.toChars(0X1F632)));
            if (s.contains("[天哪]"))
                s = s.replace("[天哪]", new String(Character.toChars(0X1F626)));
            if (s.contains("[惊呆]"))
                s = s.replace("[惊呆]", new String(Character.toChars(0X1F62F)));
            if (s.contains("[无语]"))
                s = s.replace("[无语]", new String(Character.toChars(0X1F636)));
            if (s.contains("[低落]"))
                s = s.replace("[低落]", new String(Character.toChars(0X1F61E)));
            if (s.contains("[担忧]"))
                s = s.replace("[担忧]", new String(Character.toChars(0X1F61F)));

            if (s.contains("[讨厌]"))
                s = s.replace("[讨厌]", new String(Character.toChars(0X1F612)));
            if (s.contains("[害怕]"))
                s = s.replace("[害怕]", new String(Character.toChars(0X1F630)));
            if (s.contains("[白眼]"))
                s = s.replace("[白眼]", new String(Character.toChars(0X1F644)));
            if (s.contains("[愤怒]"))
                s = s.replace("[愤怒]", new String(Character.toChars(0X1F620)));
            if (s.contains("[失望]"))
                s = s.replace("[失望]", new String(Character.toChars(0X1F643)));
            if (s.contains("[纠结]"))
                s = s.replace("[纠结]", new String(Character.toChars(0X1F616)));
            if (s.contains("[悔恨]"))
                s = s.replace("[悔恨]", new String(Character.toChars(0X1F623)));
            if (s.contains("[不嘛]"))
                s = s.replace("[不嘛]", new String(Character.toChars(0X1F629)));
            if (s.contains("[疲惫]"))
                s = s.replace("[疲惫]", new String(Character.toChars(0X1F62B)));
            if (s.contains("[晕头]"))
                s = s.replace("[晕头]", new String(Character.toChars(0X1F635)));
            if (s.contains("[大哭]"))
                s = s.replace("[大哭]", new String(Character.toChars(0X1F62D)));

            if (s.contains("[坏笑]"))
                s = s.replace("[坏笑]", new String(Character.toChars(0X1F60F)));
            if (s.contains("[满足]"))
                s = s.replace("[满足]", new String(Character.toChars(0X1F60D)));
            if (s.contains("[呲牙]"))
                s = s.replace("[呲牙]", new String(Character.toChars(0X1F62C)));
            if (s.contains("[学霸]"))
                s = s.replace("[学霸]", new String(Character.toChars(0X1F913)));
            if (s.contains("[星星]"))
                s = s.replace("[星星]", new String(Character.toChars(0X1F929)));
            if (s.contains("[抱抱]"))
                s = s.replace("[抱抱]", new String(Character.toChars(0X1F917)));
            if (s.contains("[笑死]"))
                s = s.replace("[笑死]", new String(Character.toChars(0X1F923)));
            if (s.contains("[糟糕]"))
                s = s.replace("[糟糕]", new String(Character.toChars(0X1F630)));
            if (s.contains("[咋办]"))
                s = s.replace("[咋办]", new String(Character.toChars(0X1F625)));
            if (s.contains("[想哭]"))
                s = s.replace("[想哭]", new String(Character.toChars(0X1F622)));
            if (s.contains("[滴汗]"))
                s = s.replace("[滴汗]", new String(Character.toChars(0X1F975)));

            if (s.contains("[打盹]"))
                s = s.replace("[打盹]", new String(Character.toChars(0X1F62A)));
            if (s.contains("[微笑]"))
                s = s.replace("[微笑]", new String(Character.toChars(0X1F60A)));
            if (s.contains("[犯困]"))
                s = s.replace("[犯困]", new String(Character.toChars(0X1F634)));
            if (s.contains("[发火]"))
                s = s.replace("[发火]", new String(Character.toChars(0X1F621)));
            if (s.contains("[惊恐]"))
                s = s.replace("[惊恐]", new String(Character.toChars(0X1F640)));
            if (s.contains("[笑猫]"))
                s = s.replace("[笑猫]", new String(Character.toChars(0X1F639)));
            if (s.contains("[求吻]"))
                s = s.replace("[求吻]", new String(Character.toChars(0X1F63D)));
            if (s.contains("[不听]"))
                s = s.replace("[不听]", new String(Character.toChars(0X1F649)));
            if (s.contains("[捂眼]"))
                s = s.replace("[捂眼]", new String(Character.toChars(0X1F648)));
            if (s.contains("[不说]"))
                s = s.replace("[不说]", new String(Character.toChars(0X1F64A)));
            if (s.contains("[幽灵]"))
                s = s.replace("[幽灵]", new String(Character.toChars(0X1F47B)));
            if (s.contains("[厉害]"))
                s = s.replace("[厉害]", new String(Character.toChars(0X1F44D)));
            if (s.contains("[不行]"))
                s = s.replace("[不行]", new String(Character.toChars(0X1F44E)));
            if (s.contains("[好的]"))
                s = s.replace("[好的]", new String(Character.toChars(0X1F44C)));
            if (s.contains("[拳头]"))
                s = s.replace("[拳头]", new String(Character.toChars(0X1F44A)));
            if (s.contains("[拳拳]"))
                s = s.replace("[拳拳]", new String(Character.toChars(0X270A)));
            if (s.contains("[胜利]"))
                s = s.replace("[胜利]", new String(Character.toChars(0X270C)));
            if (s.contains("[鼓掌]"))
                s = s.replace("[鼓掌]", new String(Character.toChars(0X1F44F)));
            if (s.contains("[电话]"))
                s = s.replace("[电话]", new String(Character.toChars(0X1F919)));
            if (s.contains("[手背]"))
                s = s.replace("[手背]", new String(Character.toChars(0X1F91A)));
            if (s.contains("[大手]"))
                s = s.replace("[大手]", new String(Character.toChars(0X270B)));
            if (s.contains("[手掌]"))
                s = s.replace("[手掌]", new String(Character.toChars(0X1F590)));
            if (s.contains("[挥手]"))
                s = s.replace("[挥手]", new String(Character.toChars(0X1F44B)));
            if (s.contains("[六六]"))
                s = s.replace("[六六]", new String(Character.toChars(0X1F91F)));
            if (s.contains("[摇滚]"))
                s = s.replace("[摇滚]", new String(Character.toChars(0X1F918)));
            if (s.contains("[一个]"))
                s = s.replace("[一个]", new String(Character.toChars(0X261D)));
            if (s.contains("[祝愿]"))
                s = s.replace("[祝愿]", new String(Character.toChars(0X1F91E)));
            if (s.contains("[上面]"))
                s = s.replace("[上面]", new String(Character.toChars(0X1F446)));
            if (s.contains("[下面]"))
                s = s.replace("[下面]", new String(Character.toChars(0X1F447)));
            if (s.contains("[左边]"))
                s = s.replace("[左边]", new String(Character.toChars(0X1F448)));
            if (s.contains("[右边]"))
                s = s.replace("[右边]", new String(Character.toChars(0X1F449)));
            if (s.contains("[汗水]"))
                s = s.replace("[汗水]", new String(Character.toChars(0X1F4A6)));
            if (s.contains("[生气]"))
                s = s.replace("[生气]", new String(Character.toChars(0X1F4A2)));
            if (s.contains("[心跳]"))
                s = s.replace("[心跳]", new String(Character.toChars(0X1F493)));
            if (s.contains("[蓝心]"))
                s = s.replace("[蓝心]", new String(Character.toChars(0X1F499)));
            if (s.contains("[睡觉]"))
                s = s.replace("[睡觉]", new String(Character.toChars(0X1F4A4)));
            if (s.contains("[黑心]"))
                s = s.replace("[黑心]", new String(Character.toChars(0X1F5A4)));
            if (s.contains("[满分]"))
                s = s.replace("[满分]", new String(Character.toChars(0X1F4AF)));
            if (s.contains("[爱心]"))
                s = s.replace("[爱心]", new String(Character.toChars(0X1F495)));
            if (s.contains("[心碎]"))
                s = s.replace("[心碎]", new String(Character.toChars(0X1F494)));

            if (s.contains("[惊讶]")) s = s.replace("[惊讶]", "&#128562;");

            return s;
        } else {
            return isEmpty(s) ? "" : s;
        }

    }


    public static Boolean isName(String value) {
        String pas = "^[_a-zA-Z0-9\u4e00-\u9fa5]{2,20}";
        Pattern p = Pattern.compile(pas);
        Matcher m = p.matcher(value);
        if (m.matches()) {
            return false;
        }
        return true;
    }

    public static String numberOfConversion(String num) {
        try {
            int count = Integer.parseInt(num);
            return numberOfconversion(count);
        } catch (Exception exception) {
            exception.printStackTrace();
            return num;
        }
    }

    /**
     * 次数换算规则
     **/

    @SuppressLint("NewApi")
    public static String numberOfconversion(int num) {
        DecimalFormat df1 = new DecimalFormat("###.#");
        df1.setRoundingMode(RoundingMode.DOWN);
        try {


            if (num < 1000) {
                return "" + num;
            } else if (num < 10000) {
                return df1.format(BigDecimal.valueOf((float) num / 1000)) + "k+";
            } else if (num < 100000) {
                return df1.format(BigDecimal.valueOf((float) num / 10000)) + "w+";
            } else if (num < 1000000) {
                return num / 10000 + "w+";
            } else {
                return "100w+";
            }
        } catch (Exception E) {


        }
        return "";
    }

    /**
     * 个人主页次数换算规则
     **/

    @SuppressLint("NewApi")
    public static String numberOfconversion2(int num) {
        DecimalFormat df1 = new DecimalFormat("###.#");
        df1.setRoundingMode(RoundingMode.DOWN);
        try {


            if (num < 10000) {
                return "" + num;
            } else if (num < 100000) {
                return df1.format(BigDecimal.valueOf((float) num / 10000)) + "w+";
            } else if (num < 1000000) {
                return num / 10000 + "w+";
            } else {
                return "100w+";
            }
        } catch (Exception E) {


        }
        return "";
    }

    public static String labelLength(String str) {
        return labelLength(str, 10);
    }

    public static String labelLength(String str, int len) {
        if (str != null) {
            //等于n个字时不显示省略号，直接显示话题文字；大于n个字时截取前n个字并加省略号
            if (str.length() > len) {
                return str.substring(0, len) + "...";
            } else {
                return str;
            }
        } else {
            return "";
        }
    }

    /**
     * 随机生成16位
     **/
    public static String genRandomNum() {
        final int maxNum = 50;
        int i;
        int count = 0;
        char[] str = {'a', 'A', 'b', 'B', 'c', 'C', 'd', 'D', 'e', 'E', 'f', 'F', 'g', 'G', 'h', 'H', 'i', 'I', 'j', 'J', 'k', 'K', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'X', 'y', 'Y', 'z', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',};
        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        while (count < 16) {
            i = Math.abs(r.nextInt(maxNum));

            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }
        return pwd.toString();
    }

    /**
     * 关键字高亮显示
     *
     * @param text   需要显示的文字
     * @param target 需要高亮的关键字
     * @param color  高亮颜色
     * @param start  头部增加高亮文字个数
     * @param end    尾部增加高亮文字个数
     * @return 处理完后的结果
     */
    public static SpannableStringBuilder highlight(String text, String target,
                                                   int color, int start, int end) {
        SpannableStringBuilder spannableString = new SpannableStringBuilder(text);
        Pattern pattern = Pattern.compile(target);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            ForegroundColorSpan span = new ForegroundColorSpan(color);
            spannableString.setSpan(span, matcher.start() - start, matcher.end() + end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return spannableString;
    }

    public static String splitStr(String value, String delimiter, int index, String defValue) {
        if (TextUtils.isEmpty(value) || TextUtils.isEmpty(delimiter)) {
            return defValue;
        }

        String[] split = value.split(delimiter);
        if (split.length <= index) {
            return defValue;
        }

        return split[index];
    }

    /***
     * 字符串转化成INT
     * @param value
     * @return
     */
    public static int strToInt(String value) {
        return strToInt(value, -1);
    }

    /***
     * 字符串转化成INT
     * @param value
     * @param defValue
     * @return
     */
    public static int strToInt(String value, int defValue) {
        if (TextUtils.isEmpty(value)) {
            return defValue;
        }

        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
        }
        return defValue;
    }

    /***
     * 字符串转化成Float
     * @param value
     * @return
     */
    public static float strToFloat(String value) {
        return strToFloat(value, -1f);
    }

    /***
     * 字符串转化成Float
     * @param value
     * @param defValue
     * @return
     */
    public static float strToFloat(String value, float defValue) {
        if (TextUtils.isEmpty(value)) {
            return defValue;
        }

        try {
            return Float.parseFloat(value);
        } catch (Exception e) {
        }
        return defValue;
    }

    /***
     * 字符串转化成Double
     * @param value
     * @return
     */
    public static double strToDouble(String value) {
        return strToFloat(value, -1f);
    }

    /***
     * 字符串转化成Double
     * @param value
     * @param defValue
     * @return
     */
    public static double strToDouble(String value, double defValue) {
        if (TextUtils.isEmpty(value)) {
            return defValue;
        }

        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
        }
        return defValue;
    }

    /***
     * 是否是数值
     * @param value
     * @return
     */
    public static boolean isInt(String value) {
        boolean result = true;
        try {
            Integer.parseInt(value);
        } catch (Exception e) {
            result = false;
        }
        return result;
    }


}
