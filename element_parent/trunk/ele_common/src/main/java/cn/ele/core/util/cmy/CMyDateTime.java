//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.ele.core.util.cmy;

import cn.ele.core.Exception.CMyException;
import cn.ele.core.message.I18NMessage;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class CMyDateTime implements Cloneable, Serializable {
    private Date m_dtDate = null;
    private SimpleDateFormat m_dtFormater = null;
    public static final int FORMAT_DEFAULT = 0;
    public static final int FORMAT_LONG = 1;
    public static final int FORMAT_SHORT = 2;
    public static final String DEF_DATE_FORMAT_PRG = "yyyy-MM-dd";
    public static final String DEF_TIME_FORMAT_PRG = "HH:mm:ss";
    public static final String DEF_DATETIME_FORMAT_PRG = "yyyy-MM-dd HH:mm:ss";
    public static final String DEF_DATETIME_FORMAT_DB = "YYYY-MM-DD HH24:MI:SS";
    public static final int YEAR = 1;
    public static final int MONTH = 2;
    public static final int DAY = 3;
    public static final int HOUR = 4;
    public static final int MINUTE = 5;
    public static final int SECOND = 6;
    public static final int QUATER = 11;
    public static final int WEEK = 12;
    public static final int DAY_OF_MONTH = 13;
    public static final int WEEK_OF_MONTH = 14;
    public static final int DAY_OF_YEAR = 15;
    public static final int WEEK_OF_YEAR = 16;
    public static final long ADAY_MILLIS = 86400000L;
    public static final String[] MONTHS = new String[]{"January", "February", " March", " April", "May", "June", "July", "August", "September", "October", "November", "December"};
    public static final String[] WEEKS = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

    public CMyDateTime() {
    }

    public CMyDateTime(long var1) {
        this.m_dtDate = new Date(var1);
    }

    public static CMyDateTime now() {
        CMyDateTime var0 = new CMyDateTime();
        var0.setDateTimeWithCurrentTime();
        return var0;
    }

    public boolean isNull() {
        return this.m_dtDate == null;
    }

    public long getTimeInMillis() {
        return this.m_dtDate == null ? 0L : this.m_dtDate.getTime();
    }

    public static int getTimeZoneRawOffset() {
        TimeZone var0 = TimeZone.getDefault();
        int var1 = var0.getRawOffset();
        return var1;
    }

    public long compareTo(Date var1) {
        long var2 = this.m_dtDate == null ? 0L : this.m_dtDate.getTime();
        long var4 = var1 == null ? 0L : var1.getTime();
        return var2 - var4;
    }

    public long compareTo(CMyDateTime var1) {
        return this.compareTo(var1.getDateTime());
    }

    public long dateDiff(int var1, CMyDateTime var2) throws CMyException {
        if (var2 == null) {
            throw new CMyException(10, I18NMessage.get(CMyDateTime.class, "CMyDateTime.label1", "无效的日期时间对象参数(CMyDateTime.dateDiff(CMyDateTime))"));
        } else {
            return this.dateDiff(var1, var2.getDateTime());
        }
    }

    public long dateDiff(int var1, Date var2) throws CMyException {
        if (var2 == null) {
            throw new CMyException(10, I18NMessage.get(CMyDateTime.class, "CMyDateTime.label2", "无效的日期时间参数（CMyDateTime.dateDiff(int,java.util.Date)）"));
        } else if (this.isNull()) {
            throw new CMyException(10, I18NMessage.get(CMyDateTime.class, "CMyDateTime.label3", "日期时间为空（CMyDateTime.dateDiff(int,java.util.Date)）"));
        } else if (var1 == 1) {
            return this.dateDiff_year(var2);
        } else if (var1 == 2) {
            return this.dateDiff_month(var2);
        } else {
            long var3 = this.m_dtDate == null ? 0L : this.m_dtDate.getTime();
            long var5 = var2 == null ? 0L : var2.getTime();
            long var7 = (var3 - var5) / 1000L;
            switch(var1) {
                case 3:
                    return var7 / 86400L;
                case 4:
                    return var7 / 3600L;
                case 5:
                    return var7 / 60L;
                case 6:
                    return var7;
                case 7:
                case 8:
                case 9:
                case 10:
                default:
                    throw new CMyException(10, I18NMessage.get(CMyDateTime.class, "CMyDateTime.label4", "参数无效(CMyDateTime.dateDiff(int,java.util.Date))"));
                case 11:
                    return var7 / 86400L / 91L;
                case 12:
                    return var7 / 86400L / 7L;
            }
        }
    }

    private long dateDiff_year(Date var1) {
        GregorianCalendar var6 = new GregorianCalendar();
        var6.setTimeZone(TimeZone.getDefault());
        var6.setTime(this.m_dtDate);
        int var2 = var6.get(1);
        int var4 = var6.get(2);
        var6.setTime(var1);
        int var3 = var6.get(1);
        int var5 = var6.get(2);
        if (var2 == var3) {
            return 0L;
        } else {
            return var2 > var3 ? (long)(var2 - var3 + (var4 >= var5 ? 0 : -1)) : (long)(var2 - var3 + (var4 > var5 ? 1 : 0));
        }
    }

    public long dateDiff_month(Date var1) {
        GregorianCalendar var6 = new GregorianCalendar();
        var6.setTimeZone(TimeZone.getDefault());
        var6.setTime(this.m_dtDate);
        int var2 = var6.get(1) * 12 + var6.get(2);
        int var4 = var6.get(5);
        var6.setTime(var1);
        int var3 = var6.get(1) * 12 + var6.get(2);
        int var5 = var6.get(5);
        if (var2 == var3) {
            return 0L;
        } else {
            return var2 > var3 ? (long)(var2 - var3 + (var4 < var5 ? -1 : 0)) : (long)(var2 - var3 + (var4 > var5 ? 1 : 0));
        }
    }

    public int get(int var1) throws CMyException {
        if (this.m_dtDate == null) {
            throw new CMyException(20, I18NMessage.get(CMyDateTime.class, "CMyDateTime.label5", "日期时间为空（CMyDateTime.get）"));
        } else {
            GregorianCalendar var2 = new GregorianCalendar();
            var2.setTimeZone(TimeZone.getDefault());
            var2.setTime(this.m_dtDate);
            switch(var1) {
                case 1:
                    return var2.get(1);
                case 2:
                    return var2.get(2) + 1;
                case 3:
                    return var2.get(5);
                case 4:
                    return var2.get(11);
                case 5:
                    return var2.get(12);
                case 6:
                    return var2.get(13);
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                default:
                    throw new CMyException(10, I18NMessage.get(CMyDateTime.class, "CMyDateTime.label6", "无效的日期时间域参数（CMyDateTime.get）"));
                case 12:
                    return var2.get(7);
                case 13:
                    return ((GregorianCalendar)var2).getActualMaximum(5);
                case 14:
                    return this.getWeekCountsOfMonth(true);
                case 15:
                    return ((GregorianCalendar)var2).getActualMaximum(6);
                case 16:
                    return ((GregorianCalendar)var2).getActualMaximum(3);
            }
        }
    }

    public int getYear() throws CMyException {
        return this.get(1);
    }

    public int getMonth() throws CMyException {
        return this.get(2);
    }

    public int getDay() throws CMyException {
        return this.get(3);
    }

    public int getHour() throws CMyException {
        return this.get(4);
    }

    public int getMinute() throws CMyException {
        return this.get(5);
    }

    public int getSecond() throws CMyException {
        return this.get(6);
    }

    public int getDayOfWeek() throws CMyException {
        return this.get(12);
    }

    public CMyDateTime dateAdd(int var1, int var2) throws CMyException {
        if (this.m_dtDate == null) {
            throw new CMyException(20, I18NMessage.get(CMyDateTime.class, "CMyDateTime.label7", "日期时间为空（CMyDateTime.dateAdd）"));
        } else {
            boolean var3 = false;
            byte var5;
            switch(var1) {
                case 1:
                    var5 = 1;
                    break;
                case 2:
                    var5 = 2;
                    break;
                case 3:
                    var5 = 5;
                    break;
                case 4:
                    var5 = 10;
                    break;
                case 5:
                    var5 = 12;
                    break;
                case 6:
                    var5 = 13;
                    break;
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                default:
                    throw new CMyException(10, I18NMessage.get(CMyDateTime.class, "CMyDateTime.label8", "无效的日期时间域参数（CMyDateTime.dateAdd）"));
                case 12:
                    var5 = 5;
                    var2 *= 7;
            }

            GregorianCalendar var4 = new GregorianCalendar();
            var4.setTimeZone(TimeZone.getDefault());
            var4.setTime(this.m_dtDate);
            var4.set(var5, var4.get(var5) + var2);
            this.m_dtDate = var4.getTime();
            return this;
        }
    }

    public synchronized Object clone() {
        CMyDateTime var1 = new CMyDateTime();
        var1.m_dtDate = this.m_dtDate == null ? null : (Date)this.m_dtDate.clone();
        var1.m_dtFormater = this.m_dtFormater == null ? null : (SimpleDateFormat)this.m_dtFormater.clone();
        return var1;
    }

    public Date getDateTime() {
        return this.m_dtDate;
    }

    public String toString() {
        return this.toString("yyyy-MM-dd HH:mm:ss");
    }

    public String toString(String var1) {
        if (this.m_dtDate == null) {
            return null;
        } else {
            try {
                return this.getDateTimeAsString(var1);
            } catch (CMyException var3) {
                return null;
            }
        }
    }

    public String toString(String var1, String var2, String var3) {
        if (this.m_dtDate == null) {
            return null;
        } else {
            boolean var4 = !CMyString.isEmpty(var2);
            boolean var5 = !CMyString.isEmpty(var3);
            if (!var4 && !var5) {
                return this.toString(var1);
            } else {
                try {
                    Locale var6 = var4 ? new Locale(var2) : Locale.getDefault();
                    TimeZone var7 = var5 ? TimeZone.getTimeZone(var3) : TimeZone.getDefault();
                    SimpleDateFormat var8 = new SimpleDateFormat(var1, var6);
                    var8.setTimeZone(var7);
                    return var8.format(this.m_dtDate);
                } catch (Exception var9) {
                    var9.printStackTrace();
                    return null;
                }
            }
        }
    }

    public java.sql.Date toDate() {
        return this.m_dtDate == null ? null : new java.sql.Date(this.m_dtDate.getTime());
    }

    public Time toTime() {
        return this.m_dtDate == null ? null : new Time(this.m_dtDate.getTime());
    }

    public Timestamp toTimestamp() {
        return this.m_dtDate == null ? null : new Timestamp(this.m_dtDate.getTime());
    }

    public void setDateTime(Date var1) {
        this.m_dtDate = var1;
    }

    public boolean setDateTimeWithString(String var1, String var2) throws CMyException {
        try {
            SimpleDateFormat var3 = new SimpleDateFormat(var2);
            this.m_dtDate = var3.parse(var1);
            return true;
        } catch (Exception var4) {
            throw new CMyException(10, I18NMessage.get(CMyDateTime.class, "CMyDateTime.label9", "日期时间字符串值和格式无效（CMyDateTime.setDateTimeWithString）"), var4);
        }
    }

    public void setDateTimeWithCurrentTime() {
        if (this.m_dtDate == null) {
            this.m_dtDate = new Date(System.currentTimeMillis());
        } else {
            this.m_dtDate.setTime(System.currentTimeMillis());
        }

    }

    public void setDateTimeWithTimestamp(Timestamp var1) throws CMyException {
        try {
            if (var1 == null) {
                this.m_dtDate = null;
            } else {
                if (this.m_dtDate == null) {
                    this.m_dtDate = new Date();
                }

                this.m_dtDate.setTime(var1.getTime());
            }

        } catch (Exception var3) {
            throw new CMyException(0, I18NMessage.get(CMyDateTime.class, "CMyDateTime.label10", "使用Timestamp对象设置日期和时间出错：CMyDateTime.setDateTimeWithTimestamp()"), var3);
        }
    }

    public void setDateTimeWithRs(ResultSet var1, int var2) throws CMyException {
        try {
            Timestamp var3 = var1.getTimestamp(var2);
            this.setDateTimeWithTimestamp(var3);
        } catch (SQLException var4) {
            throw new CMyException(40, I18NMessage.get(CMyDateTime.class, "CMyDateTime.label11", "从记录集中读取时间字段时出错：CMyDateTime.setDateTimeWithRs()"), var4);
        }
    }

    public void setDateTimeWithRs(ResultSet var1, String var2) throws CMyException {
        try {
            Timestamp var3 = var1.getTimestamp(var2);
            this.setDateTimeWithTimestamp(var3);
        } catch (SQLException var4) {
            throw new CMyException(40, I18NMessage.get(CMyDateTime.class, "CMyDateTime.label11", "从记录集中读取时间字段时出错：CMyDateTime.setDateTimeWithRs()"), var4);
        }
    }

    public boolean setDate(java.sql.Date var1) throws CMyException {
        return var1 == null ? false : this.setDateWithString(var1.toString(), 0);
    }

    public boolean setTime(Time var1) throws CMyException {
        return var1 == null ? false : this.setTimeWithString(var1.toString(), 0);
    }

    public boolean setDateWithString(String var1, int var2) throws CMyException {
        boolean var5 = false;
        int var6 = var1.length();
        if (var6 < 6) {
            throw new CMyException(10, I18NMessage.get(CMyDateTime.class, "CMyDateTime.label12", "日期字符串无效（CMyDateTime.setDateWithString）"));
        } else {
            try {
                String var3;
                switch(var2) {
                    case 1:
                        var5 = var6 >= 10;
                        var3 = var1.substring(0, 4) + "-" + var1.substring(var5 ? 5 : 4, var5 ? 7 : 6) + "-" + var1.substring(var5 ? 8 : 6, var5 ? 10 : 8);
                        break;
                    case 2:
                        var3 = var1.charAt(0) < '5' ? "20" : "19";
                        var5 = var6 >= 8;
                        var3 = var3 + var1.substring(0, 2) + "-" + var1.substring(var5 ? 3 : 2, var5 ? 5 : 4) + "-" + var1.substring(var5 ? 6 : 4, var5 ? 8 : 6);
                        break;
                    default:
                        var3 = var1;
                }

                if (this.m_dtDate == null) {
                    return this.setDateTimeWithString(var3, "yyyy-MM-dd");
                } else {
                    String var4 = this.getDateTimeAsString("HH:mm:ss");
                    return this.setDateTimeWithString(var3 + " " + var4, "yyyy-MM-dd HH:mm:ss");
                }
            } catch (Exception var8) {
                throw new CMyException(10, I18NMessage.get(CMyDateTime.class, "CMyDateTime.label13", "无效的日期字符串（CMyException.setDateWithString）"), var8);
            }
        }
    }

    public boolean setTimeWithString(String var1, int var2) throws CMyException {
        boolean var5 = false;
        int var6 = var1.length();
        if (var6 < 4) {
            throw new CMyException(10, I18NMessage.get(CMyDateTime.class, "CMyDateTime.label14", "时间字符串格式无效（）"));
        } else {
            try {
                String var4;
                switch(var2) {
                    case 1:
                        var5 = var6 >= 8;
                        var4 = var1.substring(0, 2) + ":" + var1.substring(var5 ? 3 : 2, var5 ? 5 : 4) + ":" + var1.substring(var5 ? 6 : 4, var5 ? 8 : 6);
                        break;
                    case 2:
                        var5 = var6 >= 5;
                        var4 = var1.substring(0, 2) + ":" + var1.substring(var5 ? 3 : 2, var5 ? 5 : 4) + ":00";
                        break;
                    default:
                        var4 = var1;
                }

                if (this.m_dtDate == null) {
                    return this.setDateTimeWithString(var4, "HH:mm:ss");
                } else {
                    String var3 = this.getDateTimeAsString("yyyy-MM-dd");
                    return this.setDateTimeWithString(var3 + " " + var4, "yyyy-MM-dd HH:mm:ss");
                }
            } catch (Exception var8) {
                throw new CMyException(10, I18NMessage.get(CMyDateTime.class, "CMyDateTime.label15", "无效的时间字符串（CMyException.setTimeWithString）"), var8);
            }
        }
    }

    public void setDateTimeFormat(String var1) {
        if (this.m_dtFormater == null) {
            this.m_dtFormater = new SimpleDateFormat(var1);
        } else {
            this.m_dtFormater.applyPattern(var1);
        }

    }

    public String getDateTimeAsString(String var1) throws CMyException {
        if (this.m_dtDate == null) {
            return null;
        } else {
            try {
                this.setDateTimeFormat(var1);
                return this.m_dtFormater.format(this.m_dtDate);
            } catch (Exception var3) {
                throw new CMyException(10, I18NMessage.get(CMyDateTime.class, "CMyDateTime.label16", "指定的日期时间格式有错（CMyDateTime.getDateTimeAsString）"), var3);
            }
        }
    }

    public String getDateTimeAsString() throws CMyException {
        if (this.m_dtDate != null && this.m_dtFormater != null) {
            try {
                return this.m_dtFormater.format(this.m_dtDate);
            } catch (Exception var2) {
                throw new CMyException(0, I18NMessage.get(CMyDateTime.class, "CMyDateTime.label17", "格式化日期时间字符串出错（CMyDateTime.getDateTimeAsString()）"), var2);
            }
        } else {
            return null;
        }
    }

    public static String extractDateTimeFormat(String var0) {
        char[] var1 = new char[]{'y', 'M', 'd', 'H', 'm', 's'};
        return extractFormat(var0, var1);
    }

    public static String extractDateFormat(String var0) {
        char[] var1 = new char[]{'y', 'M', 'd'};
        return extractFormat(var0, var1);
    }

    public static String extractTimeFormat(String var0) {
        char[] var1 = new char[]{'H', 'm', 's'};
        return extractFormat(var0, var1);
    }

    private static String extractFormat(String var0, char[] var1) {
        if (var0 == null) {
            return null;
        } else {
            char[] var2 = var0.trim().toCharArray();
            if (var2.length == 0) {
                return null;
            } else {
                StringBuffer var3 = new StringBuffer(19);
                int var4 = 0;
                int var5 = 0;

                while(var4 < var2.length) {
                    char var6 = var2[var4++];
                    if (Character.isDigit(var6)) {
                        var3.append(var1[var5]);
                    } else {
                        var3.append(var6);
                        ++var5;
                        if (var5 >= var1.length) {
                            break;
                        }
                    }
                }

                return var3.toString();
            }
        }
    }

    public boolean setDateTimeWithString(String var1) throws CMyException {
        String var2 = extractDateTimeFormat(var1);
        return var1 == null ? false : this.setDateTimeWithString(var1, var2);
    }

    public static final String formatTimeUsed(long var0) {
        if (var0 <= 0L) {
            return "";
        } else {
            boolean var2 = false;
            int var3 = 0;
            StringBuffer var4 = new StringBuffer(16);
            int var5 = (int)(var0 / 1000L);
            var0 %= 1000L;
            if (var5 > 0) {
                var3 = var5 / 60;
                var5 %= 60;
            }

            if (var3 > 0) {
                if (var3 > 1) {
                    var4.append(var3).append(I18NMessage.get(CMyDateTime.class, "CMyDateTime.label18", "分"));
                } else {
                    var4.append(var3).append(I18NMessage.get(CMyDateTime.class, "CMyDateTime.label20", "分"));
                }

                if (var5 < 10) {
                    var4.append('0');
                }

                var4.append(var5);
            } else {
                var4.append(var5).append('.');
                if (var0 < 10L) {
                    var4.append('0').append('0');
                } else if (var0 < 100L) {
                    var4.append('0');
                }

                var4.append(var0);
            }

            if (var0 > 1L) {
                var4.append(I18NMessage.get(CMyDateTime.class, "CMyDateTime.label19", "秒"));
            } else {
                var4.append(I18NMessage.get(CMyDateTime.class, "CMyDateTime.label21", "秒"));
            }

            return var4.toString();
        }
    }

    public static String getStr(Object var0, String var1) {
        return var0 instanceof CMyDateTime ? ((CMyDateTime)var0).toString(var1) : CMyString.showObjNull(var0);
    }

    public static void main(String[] var0) {
        CMyDateTime var1 = new CMyDateTime();

        try {
            CMyDateTime var2 = now();
            System.out.println("now:" + var2.toString("yyyy-MM-dd HH:mm:ss"));
            CMyDateTime var3 = (CMyDateTime)var2.clone();
            CMyDateTime var4 = var3.dateAdd(3, -30);
            System.out.println("now-30:" + var4.toString("yyyy-MM-dd HH:mm:ss"));
            System.out.println("now:" + var2.toString("yyyy-MM-dd HH:mm:ss"));
            System.out.println("nowClone:" + var3.toString("yyyy-MM-dd HH:mm:ss"));
            var2 = now();
            var4 = var2.dateAdd(3, -3);
            System.out.println("now-3:" + var4.toString("yyyy-MM-dd HH:mm:ss"));
            var2 = now();
            var4 = var2.dateAdd(4, -3);
            System.out.println("now-3:" + var4.toString("yyyy-MM-dd HH:mm:ss"));
            var4.setDateTimeWithString("2002.1.1 00:00:00", "yyyy.MM.dd HH:mm:ss");
            System.out.println("time:" + var4.toString("yyyy-MM-dd HH:mm:ss"));
            var4.setDateTimeWithString(var4.toString("yyyy-MM-dd") + " 23:00:00", "yyyy-MM-dd HH:mm:ss");
            CMyDateTime var5 = now();
            var5.setDateTimeWithString(var5.toString("yyyy-MM-dd") + " 24:00:00", "yyyy-MM-dd HH:mm:ss");
            System.out.println("now:" + var2.toString());
            System.out.println("execStartTime:" + var4.toString());
            System.out.println("now.compareTo(execStartTime):" + var2.compareTo(var4));
            System.out.println("TimeZone = " + getTimeZoneRawOffset());
            var1.setDateTimeWithCurrentTime();
            System.out.println("Start:" + var1.getDateTimeAsString("yyyy/MM/dd HH:mm:ss"));
            long var6 = var1.getTimeInMillis() % 3600000L;
            System.out.print("\nTime=" + var6);
            Time var8 = new Time(var6);
            System.out.print("  " + var8.toString());
            System.out.print("\n");
            var1.setDateWithString("2001-04-15", 0);
            System.out.println(var1.getDateTimeAsString("yyyy.MM.dd"));
            var1.setDateWithString("000505", 2);
            System.out.println(var1.getDateTimeAsString("yyyy.MM.dd"));
            var1.setTimeWithString("12:01:02", 0);
            System.out.println(var1.getDateTimeAsString("HH:mm:ss"));
            var1.setTimeWithString("00:25", 2);
            System.out.println(var1.getDateTimeAsString("yyyy-MM-dd HH:mm:ss"));
            new java.sql.Date(0L);
            new Time(0L);
            java.sql.Date var9 = java.sql.Date.valueOf("1978-02-04");
            Time var10 = Time.valueOf("12:00:20");
            System.out.println(var1.getDateTimeAsString("yyyy/MM/dd HH:mm:ss"));
            var1.setDate(var9);
            System.out.println(var1.getDateTimeAsString("yyyy/MM/dd HH:mm:ss"));
            var1.setTime(var10);
            System.out.println(var1.getDateTimeAsString("yyyy/MM/dd HH:mm:ss"));
            var1.setDateTimeWithCurrentTime();
            System.out.println("End:" + var1.getDateTimeAsString("yyyy/MM/dd HH:mm:ss"));
            CMyDateTime var11 = new CMyDateTime();
            int[] var12 = new int[]{1, 2, 3, 4, 5, 6, 11, 12};
            var11.setDateTimeWithString("2001-02-07 14:34:00", "yyyy-MM-dd HH:mm:ss");
            var1.setDateTimeWithString("2001-03-07 15:35:01", "yyyy-MM-dd HH:mm:ss");

            int var13;
            for(var13 = 0; var13 < 8; ++var13) {
                long var14 = var1.dateDiff(var12[var13], var11.getDateTime());
                System.out.println("DateDiff(" + var12[var13] + ")=" + var14);
            }

            for(var13 = 0; var13 < 6; ++var13) {
                System.out.println("get(" + var12[var13] + ")=" + var1.get(var12[var13]));
            }

            System.out.println("getWeek=" + var1.get(12));
            System.out.println("Test for dateAdd()");
            System.out.println("oldDateTime = " + var1.toString());
            var1.dateAdd(1, 12);
            System.out.println("dateAdd(YEAR,12) = " + var1.toString());
            var1.dateAdd(1, -12);
            System.out.println("dateAdd(YEAR,-12) = " + var1.toString());
            var1.dateAdd(2, -3);
            System.out.println("dateAdd(MONTH,-3) = " + var1.toString());
            var1.dateAdd(3, 10);
            System.out.println("dateAdd(DAY,10) = " + var1.toString());
            var1.setDateTimeWithCurrentTime();
            var13 = var1.getDayOfWeek();
            var1.dateAdd(3, -var13);
            System.out.println("Monday of this week is:" + var1.toString("yyyy-MM-dd"));

            for(int var17 = 1; var17 < 7; ++var17) {
                var1.dateAdd(3, 1);
                System.out.println(var17 + 1 + " of this week is:" + var1.toString("yyyy-MM-dd"));
            }

            System.out.println("\n\n===== test for CMyDateTime.set() ====== ");
            String[] var18 = new String[]{"2002.06.13 12:00:12", "1900.2.4 3:4:5", "1901-03-15 23:05:10", "1978-2-4 5:6:7", "2001/12/31 21:08:22", "1988/2/5 9:1:2", "1986.12.24", "0019.2.8", "2002-12-20", "1999-8-1", "2001/12/21", "2000/1/5", "78.02.04", "89.2.6", "99-12-31", "22-3-6", "01/02/04", "02/5/8"};

            for(int var15 = 0; var15 < var18.length; ++var15) {
                var1.setDateTimeWithString(var18[var15]);
                System.out.println("[" + var15 + "]" + extractDateTimeFormat(var18[var15]) + "  " + var1.toString());
            }
        } catch (CMyException var16) {
            var16.printStackTrace(System.out);
        }

    }

    public boolean isLeapYear() throws CMyException {
        GregorianCalendar var1 = new GregorianCalendar();
        var1.setTime(this.m_dtDate);
        return var1.isLeapYear(this.getYear());
    }

    public boolean isToday() {
        CMyDateTime var1 = now();
        return this.toString("yyyy-MM-dd").equals(var1.toString("yyyy-MM-dd"));
    }

    public int getWeekCountsOfMonth(boolean var1) throws CMyException {
        GregorianCalendar var2 = new GregorianCalendar();
        var2.setTime(this.m_dtDate);
        int var3 = var2.getActualMaximum(4);
        if (var1) {
            return var3;
        } else {
            CMyDateTime var4 = new CMyDateTime();
            var4.setDateTime(this.m_dtDate);
            var4.setDateTimeWithString(var4.getYear() + "-" + var4.getMonth() + "-1");
            if (var4.getDayOfWeek() == 6) {
                ++var3;
            }

            return var3;
        }
    }

    public boolean equals(Object var1) {
        return var1 != null && var1 instanceof CMyDateTime && ((CMyDateTime)var1).getTimeInMillis() == this.getTimeInMillis();
    }

    public boolean testDateFormat(String var1) {
        try {
            boolean var2 = this.setDateTimeWithString(var1);
            return var2;
        } catch (Exception var3) {
            return false;
        }
    }
}
