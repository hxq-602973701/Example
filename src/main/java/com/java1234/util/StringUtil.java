package com.java1234.util;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import com.google.common.base.Strings;
import com.java1234.dal.enums.DeviceTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.net.URLEncoder;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);
    private static final Pattern IDENTITY_PATTERN = Pattern.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{4}$");

    public StringUtil() {
    }

    public static boolean isNotBlank(String value) {
        return !isBlank(value);
    }

    public static boolean isBlank(String value) {
        return Strings.isNullOrEmpty(value);
    }

    public static DeviceTypeEnum getDeviceType(String userAgent) {
        if (isNotBlank(userAgent)) {
            if (userAgent.indexOf("Mozilla") > -1) {
                return DeviceTypeEnum.WEB;
            } else if (userAgent.indexOf("iPhone") > -1) {
                return DeviceTypeEnum.IOS;
            } else if (userAgent.indexOf("iPad") > -1) {
                return DeviceTypeEnum.IOS;
            } else if (userAgent.indexOf("Android") > -1) {
                return DeviceTypeEnum.ANDROID;
            } else {
                return userAgent.indexOf("BB10") > -1 ? DeviceTypeEnum.BB10 : DeviceTypeEnum.IOS;
            }
        } else {
            logger.error("UserAgent[{}]", userAgent);
            return DeviceTypeEnum.UNKNOWN;
        }
    }

    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    public static String trimLCR(String str) {
        StringBuffer sb = new StringBuffer();
        if (str != null && !str.equals("")) {
            for (int i = 0; i < str.length(); ++i) {
                if (str.charAt(i) != ' ' && str.charAt(i) != 12288) {
                    sb.append(str.charAt(i));
                }
            }

            return sb.toString();
        } else {
            return "";
        }
    }

    public static String cap(String data) {
        String firstLetter = data.substring(0, 1).toUpperCase();
        String restLetters = data.substring(1).toLowerCase();
        return firstLetter + restLetters;
    }

    public static String mosaicString(int start, int end, String value) {
        if (isEmpty(value)) {
            return value;
        } else {
            Assert.isTrue(start >= 0, "start can not be less than 0");
            Assert.isTrue(start < end, "start can not be greater than end");
            Assert.isTrue(end <= value.length(), "start can not be less than value length");
            StringBuilder sb = new StringBuilder(value);

            for (int i = start; i <= end; ++i) {
                sb.replace(i - 1, i, "*");
            }

            return sb.toString();
        }
    }

    public static boolean isWord(String str) {
        if (str == null) {
            return false;
        } else {
            byte[] asc = str.getBytes();

            for (int i = 0; i < asc.length; ++i) {
                if (!isVisibleChar(asc[i])) {
                    return false;
                }
            }

            return true;
        }
    }

    public static boolean isVisibleChar(byte asc) {
        return asc >= 48 && asc <= 57 || asc >= 65 && asc <= 90 || asc >= 97 && asc <= 122 || asc == 95 || asc == 45 || asc == 46;
    }

    public static boolean isNumberic(String str) {
        if (str == null) {
            return false;
        } else {
            str = str.trim();
            if (str.length() <= 0) {
                return false;
            } else {
                char[] ch = str.toCharArray();
                int count = 0;
                int i = 0;

                while (true) {
                    if (i >= str.length()) {
                        return true;
                    }

                    if (!Character.isDigit(ch[i]) && (i != 0 || ch[i] != '-' && ch[i] != '+')) {
                        if (ch[i] != '.') {
                            break;
                        }

                        ++count;
                        if (count > 1) {
                            break;
                        }
                    }

                    ++i;
                }

                return false;
            }
        }
    }

    public static boolean isMoney(String money) {
        if (money != null && money.length() > 0) {
            money = money.trim().replaceAll(",", "");
            boolean is = true;

            try {
                new Double(money);
            } catch (NumberFormatException var3) {
                is = false;
            }

            return is;
        } else {
            return false;
        }
    }

    public static Double formatDouble(String money) {
        return money != null && money.length() > 0 ? new Double(money.trim().replaceAll(",", "")) : new Double(0.0D);
    }

    public static boolean isEmail(String str) {
        if (str != null && str.length() != 0) {
            str = str.trim();
            if (str.indexOf(32) > 0) {
                return false;
            } else {
                int index = str.indexOf(64);
                if (index > 0 && index != str.length() - 1 && index == str.lastIndexOf(64)) {
                    if (str.indexOf(46) > 0 && str.lastIndexOf(46) != str.length() - 1) {
                        if (str.lastIndexOf(46) < str.lastIndexOf(64)) {
                            return false;
                        } else {
                            return str.indexOf(".@") <= 0 && str.indexOf("@.") <= 0;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    public static boolean isMobileNumber(String str) {
        if (isEmpty(str)) {
            return false;
        } else if (str.length() != 11) {
            return false;
        } else {
            try {
                Long.parseLong(str);
            } catch (NumberFormatException var2) {
                return false;
            }

            return str.startsWith("13") || str.startsWith("15") || str.startsWith("17") || str.startsWith("18");
        }
    }

    public static boolean isIdentity(String identity) {
        if (isEmpty(identity)) {
            return false;
        } else {
            Matcher m = IDENTITY_PATTERN.matcher(identity);
            return m.find();
        }
    }

    public static boolean isValidString(String str, int maxLen, boolean noAllowEmpty) {
        if (isExceed(str, maxLen)) {
            return false;
        } else {
            return !noAllowEmpty || !isEmpty(str);
        }
    }

    public static boolean isExceed(String str, int maxLen) {
        str = trim(str);
        return str != null && str.length() > maxLen;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() <= 0;
    }

    public static String idToPath(String code) {
        return idToPath((long) Math.abs(code.hashCode()));
    }

    public static String idToPath(long newId) {
        if (newId <= 0L) {
            return "";
        } else {
            StringBuffer result;
            for (result = new StringBuffer(""); newId > 0L; newId /= 100L) {
                result.insert(0, "" + newId % 100L + "/");
            }

            return result.toString();
        }
    }

    public static boolean strExistsInArray(String[] strArray, String str) {
        if (str == null) {
            return false;
        } else {
            for (int i = 0; i < strArray.length; ++i) {
                if (strArray[i].equals(str)) {
                    return true;
                }
            }

            return false;
        }
    }

    public static String encodeURL(String name) {
        try {
            name = URLEncoder.encode(name, "US-ASCII");
        } catch (Exception var2) {
            ;
        }

        return name;
    }

    public static String substring(String str, int len) {
        if (!isEmpty(str) && str.length() > len) {
            if (len <= 0) {
                return "";
            } else {
                String sub = str.substring(0, len);
                byte[] b = sub.getBytes();
                int count = 0;

                for (int index = 0; index < b.length; ++index) {
                    if (b[index] < 0) {
                        ++count;
                    }
                }

                if (count % 2 != 0) {
                    sub = sub.substring(0, sub.length() - 1);
                }

                return sub + "...";
            }
        } else {
            return str;
        }
    }

    public static boolean isValidPhoneNumber(String phone, int minLength, int maxLength, String dash, String delim) {
        if (isEmpty(phone)) {
            return false;
        } else {
            Pattern pattern = Pattern.compile("[0-9" + dash + "]*");
            Matcher matcher = pattern.matcher(phone);
            if (matcher.matches()) {
                String[] phoneArr = phone.split(delim);
                return phoneArr.length >= minLength && phoneArr.length <= maxLength;
            } else {
                return false;
            }
        }
    }

    public static String getSuffixName(String fileName) {
        if (!isEmpty(fileName) && fileName.lastIndexOf(".") >= 0) {
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            return suffixName.toLowerCase().trim();
        } else {
            return "";
        }
    }

    public static String getQuarterName(Integer quarter) {
        switch (quarter) {
            case 1:
                return "一季度";
            case 2:
                return "二季度";
            case 3:
                return "三季度";
            case 4:
                return "四季度";
            default:
                return "未知季度";
        }
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String toLowerCaseWithUnderscores(String str) {
        return (new StringUtil.WordTokenizer() {
            protected void startSentence(StringBuilder buffer, char ch) {
                buffer.append(Character.toLowerCase(ch));
            }

            protected void startWord(StringBuilder buffer, char ch) {
                if (!this.isDelimiter(buffer.charAt(buffer.length() - 1))) {
                    buffer.append('_');
                }

                buffer.append(Character.toLowerCase(ch));
            }

            protected void inWord(StringBuilder buffer, char ch) {
                buffer.append(Character.toLowerCase(ch));
            }

            protected void startDigitSentence(StringBuilder buffer, char ch) {
                buffer.append(ch);
            }

            protected void startDigitWord(StringBuilder buffer, char ch) {
                if (!this.isDelimiter(buffer.charAt(buffer.length() - 1))) {
                    buffer.append('_');
                }

                buffer.append(ch);
            }

            protected void inDigitWord(StringBuilder buffer, char ch) {
                buffer.append(ch);
            }

            protected void inDelimiter(StringBuilder buffer, char ch) {
                buffer.append(ch);
            }
        }).parse(str);
    }

    public static void main(String[] args) {
    }

    private abstract static class WordTokenizer {
        protected static final char UNDERSCORE = '_';

        private WordTokenizer() {
        }

        public String parse(String str) {
            if (StringUtil.isEmpty(str)) {
                return str;
            } else {
                int length = str.length();
                StringBuilder buffer = new StringBuilder(length);

                for (int index = 0; index < length; ++index) {
                    char ch = str.charAt(index);
                    if (!Character.isWhitespace(ch)) {
                        if (!Character.isUpperCase(ch)) {
                            if (Character.isLowerCase(ch)) {
                                index = this.parseLowerCaseWord(buffer, str, index);
                            } else if (Character.isDigit(ch)) {
                                index = this.parseDigitWord(buffer, str, index);
                            } else {
                                this.inDelimiter(buffer, ch);
                            }
                        } else {
                            int wordIndex;
                            for (wordIndex = index + 1; wordIndex < length; ++wordIndex) {
                                char wordChar = str.charAt(wordIndex);
                                if (!Character.isUpperCase(wordChar)) {
                                    if (Character.isLowerCase(wordChar)) {
                                        --wordIndex;
                                    }
                                    break;
                                }
                            }

                            if (wordIndex != length && wordIndex <= index) {
                                index = this.parseTitleCaseWord(buffer, str, index);
                            } else {
                                index = this.parseUpperCaseWord(buffer, str, index, wordIndex);
                            }
                        }
                    }
                }

                return buffer.toString();
            }
        }

        private int parseUpperCaseWord(StringBuilder buffer, String str, int index, int length) {
            char ch = str.charAt(index++);
            if (buffer.length() == 0) {
                this.startSentence(buffer, ch);
            } else {
                this.startWord(buffer, ch);
            }

            while (index < length) {
                ch = str.charAt(index);
                this.inWord(buffer, ch);
                ++index;
            }

            return index - 1;
        }

        private int parseLowerCaseWord(StringBuilder buffer, String str, int index) {
            char ch = str.charAt(index++);
            if (buffer.length() == 0) {
                this.startSentence(buffer, ch);
            } else {
                this.startWord(buffer, ch);
            }

            for (int length = str.length(); index < length; ++index) {
                ch = str.charAt(index);
                if (!Character.isLowerCase(ch)) {
                    break;
                }

                this.inWord(buffer, ch);
            }

            return index - 1;
        }

        private int parseTitleCaseWord(StringBuilder buffer, String str, int index) {
            char ch = str.charAt(index++);
            if (buffer.length() == 0) {
                this.startSentence(buffer, ch);
            } else {
                this.startWord(buffer, ch);
            }

            for (int length = str.length(); index < length; ++index) {
                ch = str.charAt(index);
                if (!Character.isLowerCase(ch)) {
                    break;
                }

                this.inWord(buffer, ch);
            }

            return index - 1;
        }

        private int parseDigitWord(StringBuilder buffer, String str, int index) {
            char ch = str.charAt(index++);
            if (buffer.length() == 0) {
                this.startDigitSentence(buffer, ch);
            } else {
                this.startDigitWord(buffer, ch);
            }

            for (int length = str.length(); index < length; ++index) {
                ch = str.charAt(index);
                if (!Character.isDigit(ch)) {
                    break;
                }

                this.inDigitWord(buffer, ch);
            }

            return index - 1;
        }

        protected boolean isDelimiter(char ch) {
            return !Character.isUpperCase(ch) && !Character.isLowerCase(ch) && !Character.isDigit(ch);
        }

        protected abstract void startSentence(StringBuilder var1, char var2);

        protected abstract void startWord(StringBuilder var1, char var2);

        protected abstract void inWord(StringBuilder var1, char var2);

        protected abstract void startDigitSentence(StringBuilder var1, char var2);

        protected abstract void startDigitWord(StringBuilder var1, char var2);

        protected abstract void inDigitWord(StringBuilder var1, char var2);

        protected abstract void inDelimiter(StringBuilder var1, char var2);
    }
}
