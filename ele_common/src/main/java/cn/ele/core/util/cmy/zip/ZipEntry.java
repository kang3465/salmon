//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.ele.core.util.cmy.zip;

import java.util.Calendar;
import java.util.Date;

public class ZipEntry implements Cloneable {
    private static int KNOWN_SIZE = 1;
    private static int KNOWN_CSIZE = 2;
    private static int KNOWN_CRC = 4;
    private static int KNOWN_TIME = 8;
    private static Calendar cal = Calendar.getInstance();
    private String name;
    private int size;
    private int compressedSize;
    private int crc;
    private int time;
    private short known = 0;
    private short method = -1;
    private byte[] extra = null;
    private String comment = null;
    int zipFileIndex = -1;
    int flags;
    int offset;
    public static final int STORED = 0;
    public static final int DEFLATED = 8;

    public ZipEntry(String var1) {
        if (var1 == null) {
            throw new NullPointerException();
        } else {
            this.name = var1;
        }
    }

    public ZipEntry(ZipEntry var1) {
        this.name = var1.name;
        this.known = var1.known;
        this.size = var1.size;
        this.compressedSize = var1.compressedSize;
        this.crc = var1.crc;
        this.time = var1.time;
        this.method = var1.method;
        this.extra = var1.extra;
        this.comment = var1.comment;
    }

    void setDOSTime(int var1) {
        int var2 = 2 * (var1 & 31);
        int var3 = var1 >> 5 & 63;
        int var4 = var1 >> 11 & 31;
        int var5 = var1 >> 16 & 31;
        int var6 = (var1 >> 21 & 15) - 1;
        int var7 = (var1 >> 25 & 127) + 1980;

        try {
            Calendar var8 = cal;
            synchronized(cal) {
                cal.set(var7, var6, var5, var4, var3, var2);
                this.time = (int)(cal.getTime().getTime() / 1000L);
            }

            this.known = (short)(this.known | KNOWN_TIME);
        } catch (RuntimeException var11) {
            this.known = (short)(this.known & ~KNOWN_TIME);
        }

    }

    int getDOSTime() {
        if ((this.known & KNOWN_TIME) == 0) {
            return 0;
        } else {
            Calendar var1 = cal;
            synchronized(cal) {
                cal.setTime(new Date((long)this.time * 1000L));
                return (cal.get(1) - 1980 & 127) << 25 | cal.get(2) + 1 << 21 | cal.get(5) << 16 | cal.get(11) << 11 | cal.get(12) << 5 | cal.get(13) >> 1;
            }
        }
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException var2) {
            throw new InternalError();
        }
    }

    public String getName() {
        return this.name;
    }

    public void setTime(long var1) {
        this.time = (int)(var1 / 1000L);
        this.known = (short)(this.known | KNOWN_TIME);
    }

    public long getTime() {
        return (this.known & KNOWN_TIME) != 0 ? (long)this.time * 1000L : -1L;
    }

    public void setSize(long var1) {
        if ((var1 & -4294967296L) != 0L) {
            throw new IllegalArgumentException();
        } else {
            this.size = (int)var1;
            this.known = (short)(this.known | KNOWN_SIZE);
        }
    }

    public long getSize() {
        return (this.known & KNOWN_SIZE) != 0 ? (long)this.size & 4294967295L : -1L;
    }

    public void setCompressedSize(long var1) {
        if ((var1 & -4294967296L) != 0L) {
            throw new IllegalArgumentException();
        } else {
            this.compressedSize = (int)var1;
            this.known = (short)(this.known | KNOWN_CSIZE);
        }
    }

    public long getCompressedSize() {
        return (this.known & KNOWN_CSIZE) != 0 ? (long)this.compressedSize & 4294967295L : -1L;
    }

    public void setCrc(long var1) {
        if ((var1 & -4294967296L) != 0L) {
            throw new IllegalArgumentException();
        } else {
            this.crc = (int)var1;
            this.known = (short)(this.known | KNOWN_CRC);
        }
    }

    public long getCrc() {
        return (this.known & KNOWN_CRC) != 0 ? (long)this.crc & 4294967295L : -1L;
    }

    public void setMethod(int var1) {
        if (var1 != 0 && var1 != 8) {
            throw new IllegalArgumentException();
        } else {
            this.method = (short)var1;
        }
    }

    public int getMethod() {
        return this.method;
    }

    public void setExtra(byte[] var1) {
        if (var1 == null) {
            this.extra = null;
        } else if (var1.length > 65535) {
            throw new IllegalArgumentException();
        } else {
            this.extra = var1;

            try {
                int var4;
                for(int var2 = 0; var2 < var1.length; var2 += var4) {
                    int var3 = var1[var2++] & 255 | (var1[var2++] & 255) << 8;
                    var4 = var1[var2++] & 255 | (var1[var2++] & 255) << 8;
                    if (var3 == 21589) {
                        byte var5 = var1[var2];
                        if ((var5 & 1) != 0) {
                            this.time = var1[var2 + 1] & 255 | (var1[var2 + 2] & 255) << 8 | (var1[var2 + 3] & 255) << 16 | (var1[var2 + 4] & 255) << 24;
                            this.known = (short)(this.known | KNOWN_TIME);
                        }
                    }
                }

            } catch (ArrayIndexOutOfBoundsException var6) {
                ;
            }
        }
    }

    public byte[] getExtra() {
        return this.extra;
    }

    public void setComment(String var1) {
        if (var1.length() > 65535) {
            throw new IllegalArgumentException();
        } else {
            this.comment = var1;
        }
    }

    public String getComment() {
        return this.comment;
    }

    public boolean isDirectory() {
        int var1 = this.name.length();
        return var1 > 0 && this.name.charAt(var1 - 1) == '/';
    }

    public String toString() {
        return this.name;
    }

    public int hashCode() {
        return this.name.hashCode();
    }
}
