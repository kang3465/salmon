//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.ele.core.util.cmy;

import cn.ele.core.Exception.CMyException;
import cn.ele.core.Exception.WCMException;
import cn.ele.core.message.I18NMessage;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CMyString {
    public static String ENCODING_DEFAULT = "UTF-8";
    public static String GET_ENCODING_DEFAULT = "UTF-8";
    public static String FILE_WRITING_ENCODING = "GBK";
    private static final int PGVARNAME_MAX_LENGTH = 20;
    private static char[] charSQLEnds = new char[]{';', '\n', '\r'};
    private static String PY_RESOURCE_FILE = "winpy2000.txt";
    private static Hashtable m_hCharName = null;
    private static final String CDATA_END = "]]>";
    private static final String CDATA_END_REPLACER = "(TRSWCM_CDATA_END_HOLDER_TRSWCM)";

    public CMyString() {
    }

    /**
     * 判断字符串是否为空
     * @param str 需要判断的字符串
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     *  判断字符串是否为空
     * @param str 需要判断的字符串
     *  @deprecated
     */
    public static boolean isEmptyStr(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * 字符串显示处理函数：若为空对象，则返回空字符串""
     * @param obj 需要处理的对象
     * @return
     */
    public static String showObjNull(Object obj) {
        return showObjNull(obj, "");
    }
    /**
     * 字符串显示处理函数：若为空对象，则返回指定的字符串
     * @param obj 需要处理的对象
     * @param str 如果对象为空返回的字符串
     * @return
     */
    public static String showObjNull(Object obj, String str) {
        return obj == null ? str : obj.toString();
    }

    /**
     * 字符串显示处理函数：若为空对象，则返回空字符串""
     * @param Str
     * @return
     */
    public static String showNull(String Str) {
        return showNull(Str, "");
    }

    /**
     * 字符串显示处理函数：若为空对象，则返回指定的字符串
     * @param StrSrc 需要处理的字符串对象
     * @param str 如果StrSrc字段为null则返回此参数
     * @return
     */
    public static String showNull(String StrSrc, String str) {
        return StrSrc == null ? str : StrSrc;
    }

    /**
     * 字符串显示处理函数：若为空对象，则返回空串""
     * @param str
     * @return
     */
    public static String showEmpty(String str) {
        return showEmpty(str, "");
    }

    /**
     * 字符串显示处理函数：若为空对象，则返回指定的字符串
     * @param strSrc 需要处理的字符串
     * @param str 如果strSrc为null或者为空串则返回此参数
     * @return
     */
    public static String showEmpty(String strSrc, String str) {
        return isEmpty(strSrc) ? str : strSrc;
    }

    /**
     * 扩展字符串长度；若长度不足，则是用指定的字符串填充
     * @param str 要扩展的字符串
     * @param lenght 扩展后的字符串长度
     * @param ch 扩展时，用于填充的字符。
     * @param bool 扩展时，是否为左填充（扩展）；否则，为右填充
     * @return
     */
    public static String expandStr(String str, int lenght, char ch, boolean bool) {
        int tempLenght = str.length();
        if (lenght <= tempLenght) {
            return str;
        } else {
            String tempStr = str;

            for(int i = 0; i < lenght - tempLenght; ++i) {
                tempStr = bool ? ch + tempStr : tempStr + ch;
            }

            return tempStr;
        }
    }

    /**
     * 设置字符串最后一位为指定的字符
     * @param str 指定的字符串
     * @param ch 指定字符，若字符串最后一位不是该字符，则在字符串尾部追加该字符
     * @return 处理后的字符串 如果isEmpty(_string)返回true,则原样返回
     */

    public static String setStrEndWith(String str, char ch) {
        return setStrEndWith0(str, ch);
    }

    /**
     * 设置字符串最后一位为指定的字符
     * @param str 指定的字符串
     * @param ch 指定字符，若字符串最后一位不是该字符，则在字符串尾部追加该字符
     * @return 处理后的字符串 如果isEmpty(_string)返回true,则原样返回
     */
    private static String setStrEndWith0(String str, char ch) {
        return !isEmpty(str) && !str.endsWith(String.valueOf(ch)) ? str + ch : str;
    }

    /**
     * 构造指定长度的空格字符串
     * @param lenght 指定长度
     * @return 指定长度的空格字符串
     */
    public static String makeBlanks(int lenght) {
        if (lenght < 1) {
            return "";
        } else {
            StringBuffer var1 = new StringBuffer(lenght);

            for(int var2 = 0; var2 < lenght; ++var2) {
                var1.append(' ');
            }

            return var1.toString();
        }
    }

    /**
     * 字符串替换函数：用于将指定字符串中指定的字符串替换为新的字符串
     * @param strSrc 源字符串
     * @param strOld 被替换的旧字符串
     * @param strNew 用来替换旧字符串的新字符串
     * @return 替换处理后的字符串
     */
    public static String replaceStr(String strSrc, String strOld, String strNew) {
        if (strSrc != null && strNew != null && strOld != null) {
            char[] var3 = strSrc.toCharArray();
            int var4 = var3.length;
            if (var4 == 0) {
                return "";
            } else {
                char[] var5 = strOld.toCharArray();
                int var6 = var5.length;
                if (var6 != 0 && var6 <= var4) {
                    StringBuffer var7 = new StringBuffer(var4 * (1 + strNew.length() / var6));
                    boolean var11 = false;
                    int var8 = 0;

                    while(true) {
                        while(var8 < var4) {
                            var11 = false;
                            if (var3[var8] == var5[0]) {
                                int var9;
                                for(var9 = 1; var9 < var6 && var8 + var9 < var4 && var3[var8 + var9] == var5[var9]; ++var9) {
                                    ;
                                }

                                var11 = var9 == var6;
                            }

                            if (var11) {
                                var7.append(strNew);
                                var8 += var6;
                            } else {
                                int var10;
                                if (var8 + var6 >= var4) {
                                    var10 = var4 - 1;
                                } else {
                                    var10 = var8;
                                }

                                while(var8 <= var10) {
                                    var7.append(var3[var8]);
                                    ++var8;
                                }
                            }
                        }

                        Object var12 = null;
                        Object var13 = null;
                        return var7.toString();
                    }
                } else {
                    return strSrc;
                }
            }
        } else {
            return strSrc;
        }
    }
    /**
     * 字符串替换函数：用于将指定字符串中指定的字符串替换为新的字符串
     * @param stringBuffer 源字符串
     * @param strOld 被替换的旧字符串
     * @param strNew 用来替换旧字符串的新字符串
     * @return 替换处理后的字符串
     */
    public static String replaceStr(StringBuffer stringBuffer, String strOld, String strNew) {
        if (stringBuffer == null) {
            return null;
        } else {
            int var3 = stringBuffer.length();
            if (var3 == 0) {
                return "";
            } else {
                char[] var4 = strOld.toCharArray();
                int var5 = var4.length;
                if (var5 != 0 && var5 <= var3) {
                    StringBuffer var6 = new StringBuffer(var3 * (1 + strNew.length() / var5));
                    boolean var10 = false;
                    int var7 = 0;

                    while(true) {
                        while(var7 < var3) {
                            var10 = false;
                            if (stringBuffer.charAt(var7) == var4[0]) {
                                int var8;
                                for(var8 = 1; var8 < var5 && var7 + var8 < var3 && stringBuffer.charAt(var7 + var8) == var4[var8]; ++var8) {
                                    ;
                                }

                                var10 = var8 == var5;
                            }

                            if (var10) {
                                var6.append(strNew);
                                var7 += var5;
                            } else {
                                int var9;
                                if (var7 + var5 >= var3) {
                                    var9 = var3 - 1;
                                } else {
                                    var9 = var7;
                                }

                                while(var7 <= var9) {
                                    var6.append(stringBuffer.charAt(var7));
                                    ++var7;
                                }
                            }
                        }

                        Object var11 = null;
                        return var6.toString();
                    }
                } else {
                    return stringBuffer.toString();
                }
            }
        }
    }

    /**
     * 字符串编码转换函数，用于将指定编码的字符串转换为标准（Unicode）字符串,默认为UTF-8
     * @param str
     * @return
     */
    public static String getStr(String str) {
        return getStr(str, ENCODING_DEFAULT);
    }

    /**
     * 字符转换函数，处理中文问题
     * @param strSrc 源字符串
     * @param bPostMethod 是否为post方式
     * @return
     */
    public static String getStr(String strSrc, boolean bPostMethod) {
        return getStr(strSrc, bPostMethod ? ENCODING_DEFAULT : GET_ENCODING_DEFAULT);
    }

    /**
     * 字符串编码转换函数，用于将指定编码的字符串转换为标准（Unicode）字符串
     * Purpose: 转换字符串内码，用于解决中文显示问题
     * Usage： 在页面切换时，获取并显示中文字符串参数时可用。
     * @param strSrc
     * @param encoding
     * @return
     */
    public static String getStr(String strSrc, String encoding) {
        if (encoding != null && encoding.length() != 0) {
            try {
                byte[] var2 = new byte[strSrc.length()];
                char[] var3 = strSrc.toCharArray();

                for(int var4 = var2.length - 1; var4 >= 0; --var4) {
                    var2[var4] = (byte)var3[var4];
                }

                return new String(var2, encoding);
            } catch (Exception var5) {
                return strSrc;
            }
        } else {
            return strSrc;
        }
    }

    /**
     * 将指定的字符串转化为ISO-8859-1编码的字符串
     * @param strSrc
     * @return 转化后的字符串
     */
    public static String toISO_8859(String strSrc) {
        if (strSrc == null) {
            return null;
        } else {
            try {
                return new String(strSrc.getBytes(), "ISO-8859-1");
            } catch (Exception var2) {
                return strSrc;
            }
        }
    }

    /** @deprecated */
    public static String toUnicode(String var0) {
        return toISO_8859(var0);
    }

    /**
     * 提取字符串UTF8编码的字节流
     * 说明：等价于 _string.getBytes("UTF8")
     * @param str 源字符串
     * @return UTF8编码的字节数组
     */
    public static byte[] getUTF8Bytes(String str) {
        char[] var1 = str.toCharArray();
        int var2 = var1.length;
        int var3 = 0;

        for(int var4 = 0; var4 < var2; ++var4) {
            char var5 = var1[var4];
            if (var5 <= 127) {
                ++var3;
            } else if (var5 <= 2047) {
                var3 += 2;
            } else {
                var3 += 3;
            }
        }

        byte[] var8 = new byte[var3];
        int var9 = 0;

        for(int var6 = 0; var6 < var2; ++var6) {
            char var7 = var1[var6];
            if (var7 <= 127) {
                var8[var9++] = (byte)var7;
            } else if (var7 <= 2047) {
                var8[var9++] = (byte)(var7 >> 6 | 192);
                var8[var9++] = (byte)(var7 & 63 | 128);
            } else {
                var8[var9++] = (byte)(var7 >> 12 | 224);
                var8[var9++] = (byte)(var7 >> 6 & 63 | 128);
                var8[var9++] = (byte)(var7 & 63 | 128);
            }
        }

        return var8;
    }

    /**
     * 从指定的字节数组中提取UTF8编码的字符串
     * 说明：函数等价于 new String(b,"UTF8")
     * @param bytes 指定的字节数组（UTF8编码）
     * @param off 开始提取的字节起始位置；可选参数，缺省值为0；
     * @param len 提取的字节数；可选择书，缺省值为全部
     * @return
     */
    public static String getUTF8String(byte[] bytes, int off, int len) {
        int var3 = 0;
        int var4 = off + len;
        int var5 = off;

        while(var5 < var4) {
            int var6 = bytes[var5++] & 255;
            switch(var6 >> 4) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    ++var3;
                    break;
                case 8:
                case 9:
                case 10:
                case 11:
                default:
                    throw new IllegalArgumentException();
                case 12:
                case 13:
                    if ((bytes[var5++] & 192) != 128) {
                        throw new IllegalArgumentException();
                    }

                    ++var3;
                    break;
                case 14:
                    if ((bytes[var5++] & 192) != 128 || (bytes[var5++] & 192) != 128) {
                        throw new IllegalArgumentException();
                    }

                    ++var3;
            }
        }

        if (var5 != var4) {
            throw new IllegalArgumentException();
        } else {
            char[] var9 = new char[var3];
            var5 = 0;

            while(off < var4) {
                int var7 = bytes[off++] & 255;
                switch(var7 >> 4) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                        var9[var5++] = (char)var7;
                        break;
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                    default:
                        throw new IllegalArgumentException();
                    case 12:
                    case 13:
                        var9[var5++] = (char)((var7 & 31) << 6 | bytes[off++] & 63);
                        break;
                    case 14:
                        int var8 = (bytes[off++] & 63) << 6;
                        var9[var5++] = (char)((var7 & 15) << 12 | var8 | bytes[off++] & 63);
                }
            }

            return new String(var9, 0, var3);
        }
    }

    public static String getUTF8String(byte[] var0) {
        return getUTF8String(var0, 0, var0.length);
    }

    /**
     * 将字节数据输出为16进制数表示的字符串
     * @param str
     * @return
     * @see #byteToHexString(byte[], char)
     */
    public static String byteToHexString(byte[] str) {
        return byteToHexString(str, ',');
    }

    /**
     * 将字节数据输出为16进制无符号数表示的字符串
     * @param bytes 字节数组
     * @param delim 字节数据显示时，字节之间的分隔符；可选参数，缺省值为','
     * @return 16进制无符号数表示的字节数据
     */
    public static String byteToHexString(byte[] bytes, char delim) {
        String var2 = "";

        for(int var3 = 0; var3 < bytes.length; ++var3) {
            if (var3 > 0) {
                var2 = var2 + delim;
            }

            var2 = var2 + Integer.toHexString(bytes[var3]);
        }

        return var2;
    }

    /**
     * 将字节数据输出为指定进制数表示的字符串（注意：负数带有负号）
     * @param bytes 字节数组
     * @param delim 字节数据显示时，字节之间的分隔符；可选参数，缺省值为','
     * @param radix 进制数（如16进制）
     * @return 指定进制数表示的字节数据（负数带由负号）
     */
    public static String byteToString(byte[] bytes, char delim, int radix) {
        String result = "";

        for(int i = 0; i < bytes.length; ++i) {
            if (i > 0) {
                result = result + delim;
            }

            result = result + Integer.toString(bytes[i], radix);
        }

        return result;
    }

    /**
     * 用于在Html中显示文本内容
     * @param var0
     * @return
     * @see #transDisplay(String, boolean)
     */
    public static String transDisplay(String var0) {
        return transDisplay(var0, true);
    }

    /**
     * 用于在Html中显示文本内容。将空格等转化为html标记。
     * 说明：处理折行时，若使用 style="WORD_WRAP:keepall" ，则不能将空格转换为 &nbsp;
     * @param sContent 要显示的内容
     * @param bChangeBlank 是否转换空格符；可选参数，默认值为true.
     * @return 转化后的Html文本
     */
    public static String transDisplay(String sContent, boolean bChangeBlank) {
        if (sContent == null) {
            return "";
        } else {
            char[] var2 = sContent.toCharArray();
            int var3 = var2.length;
            StringBuffer var4 = new StringBuffer(var3 * 2);

            for(int var5 = 0; var5 < var3; ++var5) {
                char var6 = var2[var5];
                switch(var6) {
                    case '\t':
                        var4.append(bChangeBlank ? "&nbsp;&nbsp;&nbsp;&nbsp;" : "    ");
                        break;
                    case '\n':
                        if (bChangeBlank) {
                            var4.append("<br/>");
                        } else {
                            var4.append(var6);
                        }
                        break;
                    case ' ':
                        var4.append(bChangeBlank ? "&nbsp;" : " ");
                        break;
                    case '"':
                        var4.append("&quot;");
                        break;
                    case '&':
                        var4.append("&amp;");
                        break;
                    case '<':
                        var4.append("&lt;");
                        break;
                    case '>':
                        var4.append("&gt;");
                        break;
                    default:
                        var4.append(var6);
                }
            }

            if (bChangeBlank) {
                return var4.toString();
            } else {
                return replaceParasStartEndSpaces(var4.toString());
            }
        }
    }

    public static String transDisplay_bbs(String var0, String var1) {
        return transDisplay_bbs(var0, var1, true);
    }

    public static String transDisplay_bbs(String var0, String var1, boolean var2) {
        if (var0 == null) {
            return "";
        } else {
            boolean var5 = false;
            boolean var6 = true;
            char[] var7 = var0.toCharArray();
            int var8 = var7.length;
            StringBuffer var9 = new StringBuffer((int)((double)var8 * 1.8D));

            for(int var3 = 0; var3 < var8; ++var3) {
                char var4 = var7[var3];
                switch(var4) {
                    case '\t':
                        var9.append(var2 ? "&nbsp;&nbsp;&nbsp;&nbsp;" : "    ");
                        var6 = false;
                        break;
                    case '\n':
                        if (var5) {
                            var5 = false;
                            var9.append("</font>");
                        }

                        var9.append("<br/>");
                        var6 = true;
                        break;
                    case ' ':
                        var9.append(var2 ? "&nbsp;" : " ");
                        var6 = false;
                        break;
                    case '"':
                        var9.append("&quot;");
                        var6 = false;
                        break;
                    case '&':
                        var9.append("&amp;");
                        var6 = false;
                        break;
                    case ':':
                        if (var6) {
                            var5 = true;
                            var9.append("<font color=" + var1 + ">:");
                        } else {
                            var9.append(":");
                        }

                        var6 = false;
                        break;
                    case '<':
                        var9.append("&lt;");
                        var6 = false;
                        break;
                    case '>':
                        var9.append("&gt;");
                        var6 = false;
                        break;
                    default:
                        var9.append(var4);
                        var6 = false;
                }
            }

            if (var5) {
                var9.append("</font>");
            }

            return var9.toString();
        }
    }

    public static String transJsDisplay(String var0) {
        if (var0 == null) {
            return "";
        } else {
            char[] var1 = var0.toCharArray();
            int var2 = var1.length;
            StringBuffer var3 = new StringBuffer((int)((double)var2 * 1.5D));

            for(int var4 = 0; var4 < var2; ++var4) {
                char var5 = var1[var4];
                switch(var5) {
                    case '"':
                        var3.append("&quot;");
                        break;
                    case '<':
                        var3.append("&lt;");
                        break;
                    case '>':
                        var3.append("&gt;");
                        break;
                    default:
                        var3.append(var5);
                }
            }

            return var3.toString();
        }
    }

    public static String transDisplayMark(String var0, char var1) {
        if (var0 == null) {
            return "";
        } else {
            char[] var2 = new char[var0.length()];

            for(int var3 = 0; var3 < var2.length; ++var3) {
                var2[var3] = var1;
            }

            return new String(var2);
        }
    }

    public static String filterForSQL(String var0) {
        if (var0 == null) {
            return "";
        } else {
            int var1 = var0.length();
            if (var1 == 0) {
                return "";
            } else {
                char[] var2 = var0.toCharArray();
                StringBuffer var3 = new StringBuffer((int)((double)var1 * 1.5D));

                for(int var4 = 0; var4 < var1; ++var4) {
                    char var5 = var2[var4];
                    switch(var5) {
                        case '\'':
                            var3.append("''");
                            break;
                        case ';':
                            boolean var6 = false;
                            int var7 = var4 + 1;

                            for(; var7 < var1 && !var6; ++var7) {
                                char var8 = var2[var7];
                                if (var8 != ' ') {
                                    if (var8 == '&') {
                                        var3.append(';');
                                    }

                                    var6 = true;
                                }
                            }

                            if (!var6) {
                                var3.append(';');
                            }
                            break;
                        default:
                            var3.append(var5);
                    }
                }

                return var3.toString();
            }
        }
    }

    public static String filterForSQL2(String var0) {
        return isEmpty(var0) ? var0 : var0.replaceAll("(?i)([;\n\r])", "");
    }

    public static String filterForXsltValue(String var0) {
        if (var0 == null) {
            return "";
        } else {
            var0 = var0.replaceAll("\\{", "{{");
            var0 = var0.replaceAll("\\}", "}}");
            return var0;
        }
    }

    public static String filterForXML(String var0) {
        if (var0 == null) {
            return "";
        } else {
            char[] var1 = var0.toCharArray();
            int var2 = var1.length;
            if (var2 == 0) {
                return "";
            } else {
                StringBuffer var3 = new StringBuffer((int)((double)var2 * 1.8D));

                for(int var4 = 0; var4 < var2; ++var4) {
                    char var5 = var1[var4];
                    switch(var5) {
                        case '"':
                            var3.append("&quot;");
                            break;
                        case '&':
                            var3.append("&amp;");
                            break;
                        case '\'':
                            var3.append("&apos;");
                            break;
                        case '<':
                            var3.append("&lt;");
                            break;
                        case '>':
                            var3.append("&gt;");
                            break;
                        default:
                            var3.append(var5);
                    }
                }

                return var3.toString();
            }
        }
    }

    public static String filterForHTMLValue(String var0) {
        if (var0 == null) {
            return "";
        } else {
            char[] var1 = var0.toCharArray();
            int var2 = var1.length;
            if (var2 == 0) {
                return "";
            } else {
                StringBuffer var3 = new StringBuffer((int)((double)var2 * 1.8D));

                for(int var4 = 0; var4 < var2; ++var4) {
                    char var5 = var1[var4];
                    switch(var5) {
                        case '"':
                            var3.append("&quot;");
                            break;
                        case '&':
                            if (var4 + 1 < var2) {
                                var5 = var1[var4 + 1];
                                if (var5 == '#') {
                                    var3.append("&");
                                } else {
                                    var3.append("&amp;");
                                }
                            } else {
                                var3.append("&amp;");
                            }
                            break;
                        case '<':
                            var3.append("&lt;");
                            break;
                        case '>':
                            var3.append("&gt;");
                            break;
                        default:
                            var3.append(var5);
                    }
                }

                return var3.toString();
            }
        }
    }

    public static String unfilterForHTMLValue(String var0) {
        if (isEmpty(var0)) {
            return "";
        } else {
            String[][] var1 = new String[][]{{"&amp;", "&"}, {"&lt;", "<"}, {"&gt;", ">"}, {"&quot;", "\""}};
            StringBuffer var2 = new StringBuffer(var0.length());
            Pattern var3 = Pattern.compile("(?im)(&[^;]+;)");
            Matcher var4 = var3.matcher(var0);
            boolean var5 = false;
            int var6 = 0;

            while(true) {
                while(var4.find()) {
                    int var9 = var4.start();
                    var2.append(var0.substring(var6, var9));
                    var6 = var4.end();
                    String var7 = var4.group(1).toLowerCase();

                    for(int var8 = 0; var8 < var1.length; ++var8) {
                        if (var1[var8][0].equals(var7)) {
                            var2.append(var1[var8][1]);
                            break;
                        }
                    }
                }

                if (var6 < var0.length()) {
                    var2.append(var0.substring(var6));
                }

                return var2.toString();
            }
        }
    }

    public static String filterForUrl(String var0) {
        if (var0 == null) {
            return "";
        } else {
            char[] var1 = var0.toCharArray();
            int var2 = var1.length;
            if (var2 == 0) {
                return "";
            } else {
                StringBuffer var3 = new StringBuffer((int)((double)var2 * 1.8D));

                for(int var4 = 0; var4 < var2; ++var4) {
                    char var5 = var1[var4];
                    switch(var5) {
                        case ' ':
                            var3.append("%20");
                            break;
                        case '#':
                            var3.append("%23");
                            break;
                        case '%':
                            var3.append("%25");
                            break;
                        case '&':
                            var3.append("%26");
                            break;
                        case '?':
                            var3.append("%3F");
                            break;
                        default:
                            var3.append(var5);
                    }
                }

                return var3.toString();
            }
        }
    }

    public static String filterForJs(String var0) {
        if (var0 == null) {
            return "";
        } else {
            char[] var1 = var0.toCharArray();
            int var2 = var1.length;
            if (var2 == 0) {
                return "";
            } else {
                StringBuffer var3 = new StringBuffer((int)((double)var2 * 1.8D));

                for(int var4 = 0; var4 < var2; ++var4) {
                    char var5 = var1[var4];
                    switch(var5) {
                        case '\t':
                            var3.append("\\t");
                            break;
                        case '\n':
                            var3.append("\\n");
                            break;
                        case '\f':
                            var3.append("\\f");
                            break;
                        case '\r':
                            var3.append("\\r");
                            break;
                        case '"':
                            var3.append("\\\"");
                            break;
                        case '\'':
                            var3.append("\\'");
                            break;
                        case '/':
                            var3.append("\\/");
                            break;
                        case '\\':
                            var3.append("\\\\");
                            break;
                        default:
                            var3.append(var5);
                    }
                }

                return var3.toString();
            }
        }
    }

    public static String filterForJava(String var0) {
        if (var0 == null) {
            return "";
        } else {
            char[] var1 = var0.toCharArray();
            int var2 = var1.length;
            if (var2 == 0) {
                return "";
            } else {
                StringBuffer var3 = new StringBuffer((int)((double)var2 * 1.8D));

                for(int var4 = 0; var4 < var2; ++var4) {
                    char var5 = var1[var4];
                    switch(var5) {
                        case '"':
                            var3.append("\\\"");
                            break;
                        case '\\':
                            var3.append("\\\\");
                            break;
                        default:
                            var3.append(var5);
                    }
                }

                return var3.toString();
            }
        }
    }

    public static String numberToStr(int var0) {
        return numberToStr(var0, 0);
    }

    public static String numberToStr(int var0, int var1) {
        return numberToStr(var0, var1, '0');
    }

    public static String numberToStr(int var0, int var1, char var2) {
        String var3 = String.valueOf(var0);
        return expandStr(var3, var1, var2, true);
    }

    public static String numberToStr(long var0) {
        return numberToStr(var0, 0);
    }

    public static String numberToStr(long var0, int var2) {
        return numberToStr(var0, var2, '0');
    }

    public static String numberToStr(long var0, int var2, char var3) {
        String var4 = String.valueOf(var0);
        return expandStr(var4, var2, var3, true);
    }

    public static String circleStr(String var0) {
        if (var0 == null) {
            return null;
        } else {
            String var1 = "";
            int var2 = var0.length();

            for(int var3 = var2 - 1; var3 >= 0; --var3) {
                var1 = var1 + var0.charAt(var3);
            }

            return var1;
        }
    }

    public static final boolean isChineseChar(int var0) {
        return var0 > 127;
    }

    public static final int getCharViewWidth(int var0) {
        return isChineseChar(var0) ? 2 : 1;
    }

    public static final int getStringViewWidth(String var0) {
        if (var0 != null && var0.length() != 0) {
            int var1 = 0;
            int var2 = var0.length();

            for(int var3 = 0; var3 < var2; ++var3) {
                var1 += getCharViewWidth(var0.charAt(var3));
            }

            return var1;
        } else {
            return 0;
        }
    }

    public static String truncateStr(String var0, int var1) {
        return truncateStr(var0, var1, "..");
    }

    public static String truncateStr(String var0, int var1, String var2) {
        if (var0 == null) {
            return null;
        } else {
            if (var2 == null) {
                var2 = "..";
            }

            int var3 = getStringViewWidth(var0);
            if (var3 <= var1) {
                return var0;
            } else {
                int var4 = getStringViewWidth(var2);
                if (var4 >= var1) {
                    return var0;
                } else {
                    int var5 = var0.length();
                    int var6 = var1 - var4;
                    StringBuffer var7 = new StringBuffer(var1 + 2);

                    for(int var8 = 0; var8 < var5; ++var8) {
                        char var9 = var0.charAt(var8);
                        int var10 = getCharViewWidth(var9);
                        if (var10 > var6) {
                            var7.append(var2);
                            break;
                        }

                        var7.append(var9);
                        var6 -= var10;
                    }

                    return var7.toString();
                }
            }
        }
    }

    public static String filterForJDOM(String var0) {
        if (var0 == null) {
            return null;
        } else {
            char[] var1 = var0.toCharArray();
            int var2 = var1.length;
            StringBuffer var3 = new StringBuffer(var2);

            for(int var4 = 0; var4 < var2; ++var4) {
                char var5 = var1[var4];
                if (isValidCharOfXML(var5)) {
                    var3.append(var5);
                }
            }

            return var3.toString();
        }
    }

    public static boolean isValidCharOfXML(char var0) {
        return var0 == '\t' || var0 == '\n' || var0 == '\r' || ' ' <= var0 && var0 <= '\ud7ff' || '\ue000' <= var0 && var0 <= '\ufffd' || 65536 <= var0 && var0 <= 1114111;
    }

    public static int getBytesLength(String var0) {
        if (var0 == null) {
            return 0;
        } else {
            char[] var1 = var0.toCharArray();
            int var2 = 0;

            for(int var3 = 0; var3 < var1.length; ++var3) {
                char var4 = var1[var3];
                var2 += var4 <= 127 ? 1 : 2;
            }

            return var2;
        }
    }

    /** @deprecated */
    public static String cutStr(String var0, int var1) {
        return truncateStr(var0, var1);
    }

    public static String URLEncode(String var0) {
        try {
            return URLCoder.encode(var0, GET_ENCODING_DEFAULT);
        } catch (Exception var2) {
            return var0;
        }
    }

    public static String[] split(String var0, String var1) {
        if (var0 != null && var1 != null) {
            StringTokenizer var2 = new StringTokenizer(var0, var1);
            int var3 = var2.countTokens();
            if (var3 == 0) {
                return new String[0];
            } else {
                String[] var4 = new String[var3];

                for(int var5 = 0; var2.hasMoreElements(); ++var5) {
                    var4[var5] = var2.nextToken().trim();
                }

                return var4;
            }
        } else {
            return new String[0];
        }
    }

    public static int countTokens(String var0, String var1) {
        StringTokenizer var2 = new StringTokenizer(var0, var1);
        return var2.countTokens();
    }

    public static int[] splitToInt(String var0, String var1) {
        if (isEmpty(var0)) {
            return new int[0];
        } else {
            if (isEmpty(var1)) {
                var1 = ",";
            }

            StringTokenizer var2 = new StringTokenizer(var0, var1);
            int[] var3 = new int[var2.countTokens()];

            for(int var4 = 0; var2.hasMoreElements(); ++var4) {
                String var5 = (String)var2.nextElement();
                var3[var4] = Integer.parseInt(var5.trim());
            }

            return var3;
        }
    }

    public static String getIdListAsString(List var0, char var1) {
        String var2 = null;
        Object var3 = null;

        for(int var5 = 0; var5 < var0.size(); ++var5) {
            var3 = var0.get(var5);
            if (var3 != null) {
                int var4 = (Integer)var3;
                if (var2 == null) {
                    var2 = String.valueOf(var4);
                } else {
                    var2 = var2 + var1 + var4;
                }
            }
        }

        return var2;
    }

    public static int[] getIdListAsIntArr(List var0) {
        return splitToInt(getIdListAsString(var0, ','), ",");
    }

    private static void loadFirstLetter(String var0) throws Exception {
        if (m_hCharName == null) {
            m_hCharName = new Hashtable(300);
        } else {
            m_hCharName.clear();
        }

        Object var1 = null;
        FileInputStream var2 = null;
        BufferedReader var3 = null;

        try {
            var2 = new FileInputStream(var0);
            var3 = new BufferedReader(new InputStreamReader(var2, FILE_WRITING_ENCODING));

            String var4;
            while((var4 = var3.readLine()) != null) {
                var4 = var4.trim();
                if (var4.length() >= 2) {
                    char var5 = var4.charAt(1);
                    if (var5 <= 127) {
                        m_hCharName.put(var4.substring(0, 1).toUpperCase(), var4.substring(1, 2));
                    }
                }
            }
        } catch (FileNotFoundException var15) {
            throw new CMyException(55, I18NMessage.get(CMyString.class, "CMyString.label2", "要读取的拼音配置文件没有找到(CMyString.getFirstLetter)"), var15);
        } catch (IOException var16) {
            throw new CMyException(53, I18NMessage.get(CMyString.class, "CMyString.label3", "读拼音配置文件件时错误(CMyString.getFirstLetter)"), var16);
        } finally {
            if (var3 != null) {
                var3.close();
            }

            if (var2 != null) {
                var2.close();
            }

            if (var1 != null) {
                try {
                    ((FileReader)var1).close();
                } catch (Exception var14) {
                    ;
                }
            }

        }

    }

    private static Hashtable getPYResource() throws Exception {
        if (m_hCharName != null) {
            return m_hCharName;
        } else {
            String var0 = CMyFile.mapResouceFullPath(PY_RESOURCE_FILE);
            loadFirstLetter(var0);
            return m_hCharName;
        }
    }

    public static String getFirstLetter(String var0) throws Exception {
        if (var0 != null && var0.length() >= 0) {
            char[] var1 = var0.toCharArray();
            String var2 = var0.substring(0, 1);
            return var1[0] > 127 ? ((String)getPYResource().get(var2.toUpperCase())).toUpperCase() : var2.toUpperCase();
        } else {
            return "";
        }
    }

    public static final String encodeForCDATA(String var0) {
        return var0 != null && var0.length() >= 1 ? replaceStr(var0, "]]>", "(TRSWCM_CDATA_END_HOLDER_TRSWCM)") : var0;
    }

    public static final String decodeForCDATA(String var0) {
        return var0 != null && var0.length() >= 1 ? replaceStr(var0, "(TRSWCM_CDATA_END_HOLDER_TRSWCM)", "]]>") : var0;
    }

    public static final boolean isContainChineseChar(String var0) {
        if (var0 == null) {
            return false;
        } else {
            return var0.getBytes().length != var0.length();
        }
    }

    public static String join(ArrayList var0, String var1) {
        return var0 == null ? null : join(var0.toArray(), var1);
    }

    public static String join(Object[] var0, String var1) {
        if (var0 != null && var0.length != 0 && var1 != null) {
            if (var0.length == 1) {
                return var0[0].toString();
            } else {
                StringBuffer var2 = new StringBuffer(var0[0].toString());

                for(int var3 = 1; var3 < var0.length; ++var3) {
                    if (var0[var3] != null) {
                        var2.append(var1);
                        var2.append(var0[var3].toString());
                    }
                }

                return var2.toString();
            }
        } else {
            return null;
        }
    }

    public static String join(int[] var0, String var1) {
        if (var0 != null && var0.length != 0) {
            if (var1 == null) {
                var1 = ",";
            }

            StringBuffer var2 = new StringBuffer();
            var2.append(var0[0]);

            for(int var3 = 1; var3 < var0.length; ++var3) {
                var2.append(var1).append(var0[var3]);
            }

            return var2.toString();
        } else {
            return "";
        }
    }

    public static boolean containsCDATAStr(String var0) {
        return var0 == null ? false : var0.matches("(?ism).*<!\\[CDATA\\[.*|.*\\]\\]>.*");
    }

    public static String parsePageVariables(String var0, Map var1) throws WCMException {
        return parsePageVariables(var0, var1, new char[]{'{', '}'}, 20);
    }

    public static String parsePageVariables(String var0, Map var1, char[] var2) throws WCMException {
        return parsePageVariables(var0, var1, var2, 20);
    }

    public static String parsePageVariables(String var0, Map var1, int var2) throws WCMException {
        return parsePageVariables(var0, var1, new char[]{'{', '}'}, var2);
    }

    public static String parsePageVariables(String var0, Map var1, char[] var2, int var3) throws WCMException {
        if (var0 == null) {
            return null;
        } else {
            StringBuffer var4 = null;

            try {
                char[] var5 = var0.toCharArray();
                int var6 = var5.length;
                int var7 = 0;
                var4 = new StringBuffer();

                while(true) {
                    while(var7 < var5.length) {
                        char var8 = var5[var7++];
                        if (var8 == '$' && var7 < var6 && var5[var7] == var2[0]) {
                            StringBuffer var9 = new StringBuffer(16);
                            ++var7;
                            int var10 = 0;
                            boolean var11 = false;

                            while(var10++ < var3 && var7 < var6) {
                                if ((var8 = var5[var7++]) == var2[1]) {
                                    var11 = true;
                                    break;
                                }

                                var9.append(var8);
                            }

                            if (var11) {
                                String var12 = var9.toString().toUpperCase();
                                String var13 = "";
                                Object var14 = var1.get(var12);
                                if (var14 != null) {
                                    var13 = var14.toString();
                                }

                                if (var13 != null) {
                                    var4.append(var13);
                                } else {
                                    var4.append("$").append(var2[0]).append(var9).append(var2[1]);
                                }
                            } else {
                                var4.append("$").append(var2[0]).append(var9);
                            }
                        } else {
                            var4.append(var8);
                        }
                    }

                    return var4.toString();
                }
            } catch (Exception var15) {
                throw new WCMException(1100, I18NMessage.get(CMyString.class, "CMyString.label4", "解析内容中的变量失败!"), var15);
            }
        }
    }

    public static String transPrettyUrl(String var0, int var1) {
        return transPrettyUrl(var0, var1, (String)null);
    }

    public static String transPrettyUrl(String var0, int var1, String var2) {
        boolean var3 = false;
        int var9;
        if (var0 != null && var1 > 0 && var0.length() > var1 && (var9 = var0.lastIndexOf(47)) != -1) {
            int var4 = var0.lastIndexOf("://") + 3;
            String var5 = var0.substring(0, var4);
            String var6 = var0.substring(var4, var9);
            if (var6.length() < 3) {
                return var0;
            } else {
                int var7 = var1 + var6.length() - var0.length();
                if (var7 <= 3) {
                    var7 = 3;
                }

                var6 = var6.substring(0, var7);
                var6 = var6 + (var2 != null ? var2 : "....");
                String var8 = var0.substring(var9);
                return var5 + var6 + var8;
            }
        } else {
            return var0;
        }
    }

    public static String replaceStartEndSpaces(String var0) {
        Pattern var1 = Pattern.compile("(?m)^(\\s*)(.*?)(\\s*)$");
        Matcher var2 = var1.matcher(var0);
        byte var3 = 30;
        StringBuffer var4 = new StringBuffer(var3 * 100 + var0.length());

        while(var2.find()) {
            String var5 = var2.group(1);

            char var7;
            for(int var6 = 0; var6 < var5.length(); ++var6) {
                var7 = var5.charAt(var6);
                if (var7 == ' ') {
                    var4.append("&nbsp;");
                } else {
                    var4.append(var7);
                }
            }

            var4.append(var2.group(2));
            String var9 = var2.group(3);
            boolean var10 = false;

            for(int var8 = 0; var8 < var9.length(); ++var8) {
                var7 = var9.charAt(var8);
                if (var7 == ' ') {
                    var4.append("&nbsp;");
                } else {
                    var4.append(var7);
                }
            }
        }

        return var4.toString();
    }

    public static String replaceParasStartEndSpaces(String var0) {
        Pattern var1 = Pattern.compile("\n\r|\n|\r");
        Matcher var2 = var1.matcher(var0);
        byte var3 = 30;
        StringBuffer var4 = new StringBuffer(var3 * 100 + var0.length());

        int var5;
        for(var5 = 0; var2.find(); var5 = var2.end()) {
            int var6 = var2.start();
            String var7 = var0.substring(var5, var6);
            var7 = replaceStartEndSpaces(var7);
            var4.append(var7);
            var4.append("<br/>");
        }

        String var8 = var0.substring(var5);
        var8 = replaceStartEndSpaces(var8);
        var4.append(var8);
        return var4.toString();
    }

    public static Map split2AttrMap(String var0) {
        Pattern var1 = Pattern.compile("([^\\s=]*)\\s*=(([^\\s'\"]+\\s)|(\\s*(['\"]?)(.*?)\\5))");
        Matcher var2 = var1.matcher(var0);
        HashMap var3 = new HashMap();

        while(var2.find()) {
            String var4 = showNull(var2.group(6), var2.group(3));
            var3.put(var2.group(1), var4);
        }

        return var3;
    }

    public static String format(String var0, String[] var1) {
        StringBuffer var2 = new StringBuffer(var0.length());
        Pattern var3 = Pattern.compile("\\{(\\d+)\\}");
        Matcher var4 = var3.matcher(var0);
        boolean var6 = false;

        int var7;
        for(var7 = 0; var4.find(); var7 = var4.end()) {
            int var8 = var4.start();
            var2.append(var0.substring(var7, var8));
            int var5 = Integer.parseInt(var4.group(1));
            var2.append(var1[var5]);
        }

        if (var7 < var0.length()) {
            var2.append(var0.substring(var7));
        }

        return var2.toString();
    }

    public static String format(String var0, Object[] var1) {
        if (isEmpty(var0)) {
            return "";
        } else {
            StringBuffer var2 = new StringBuffer(var0.length());
            Pattern var3 = Pattern.compile("\\{(\\d+)\\}");
            Matcher var4 = var3.matcher(var0);
            boolean var6 = false;

            int var7;
            for(var7 = 0; var4.find(); var7 = var4.end()) {
                int var8 = var4.start();
                var2.append(var0.substring(var7, var8));
                int var5 = Integer.parseInt(var4.group(1));
                var2.append(var1[var5]);
            }

            if (var7 < var0.length()) {
                var2.append(var0.substring(var7));
            }

            return var2.toString();
        }
    }

    public static String format(String var0, int[] var1) {
        if (isEmpty(var0)) {
            return "";
        } else {
            StringBuffer var2 = new StringBuffer(var0.length());
            Pattern var3 = Pattern.compile("\\{(\\d+)\\}");
            Matcher var4 = var3.matcher(var0);
            boolean var6 = false;

            int var7;
            for(var7 = 0; var4.find(); var7 = var4.end()) {
                int var8 = var4.start();
                var2.append(var0.substring(var7, var8));
                int var5 = Integer.parseInt(var4.group(1));
                var2.append(var1[var5]);
            }

            if (var7 < var0.length()) {
                var2.append(var0.substring(var7));
            }

            return var2.toString();
        }
    }

    public static String innerText(String var0) {
        if (isEmpty(var0)) {
            return "";
        } else {
            String var1 = var0.replaceAll("(?is)<style[^>]*>.*</style>", "");
            var1 = var1.replaceAll("(?is)<br[^>]*>([\\n\\s]*(&nbsp;| |　)*)*", "\n　　");
            var1 = var1.replaceAll("(?is)<p[^>]*>([\\n\\s]*(&nbsp;| |　)*)*", "\n　　");
            var1 = var1.replaceAll("(?is)</p[^>]*>", "");
            var1 = var1.replaceAll("<[^>]+>", "");
            var1 = var1.replaceAll("(?is)([ 　]*\n+[ 　]*)+", "\n　　");
            return var1.replaceAll("&nbsp;", " ").replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&quot;", "\"").replaceAll("&apos;", "'").replaceAll("&amp;", "&");
        }
    }

    public static String encodeForRegExp(String var0) {
        String var1 = ".^${[(|}])*+?\";\\";
        StringBuffer var2 = new StringBuffer(2 * var0.length());
        int var3 = 0;

        for(int var4 = var0.length(); var3 < var4; ++var3) {
            char var5 = var0.charAt(var3);
            if (var1.indexOf(var5) >= 0) {
                var2.append("\\");
            }

            var2.append((char)var5);
        }

        return var2.toString();
    }

    public static String capitalize(String var0) {
        if (isEmpty(var0)) {
            return var0;
        } else {
            char var1 = var0.charAt(0);
            char var2 = Character.toUpperCase(var1);
            return var1 == var2 ? var0 : var2 + var0.substring(1);
        }
    }

    public static boolean isStringInArray(String var0, String[] var1, boolean var2) {
        if (var0 != null && var1 != null && var1.length != 0) {
            int var3;
            if (var2) {
                for(var3 = var1.length - 1; var3 >= 0; --var3) {
                    if (var0.equalsIgnoreCase(var1[var3])) {
                        return true;
                    }
                }
            } else {
                for(var3 = var1.length - 1; var3 >= 0; --var3) {
                    if (var0.equals(var1[var3])) {
                        return true;
                    }
                }
            }

            return false;
        } else {
            return false;
        }
    }
}
