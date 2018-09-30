//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.ele.core.util.cmy;

import java.io.UnsupportedEncodingException;

public final class URLCoder {
    private static boolean[] dontNeed;
    private static String encoding;
    private static char[] hexDigit;

    public static final String decode(String var0) {
        if (var0 != null) {
            byte[] var1 = new byte[var0.length()];
            int var2 = decode(var0, var1);

            try {
                return new String(var1, 0, var2, encoding);
            } catch (UnsupportedEncodingException var4) {
                ;
            }
        }

        return null;
    }

    private static final int decode(String var0, byte[] var1) {
        boolean var2 = false;
        int var3 = var1.length;
        int var4 = 0;

        for(int var8 = 0; var8 < var3; ++var8) {
            char var5 = var0.charAt(var8);
            switch(var5) {
                case '%':
                    try {
                        var1[var4++] = hex2byte(var0.charAt(var8 + 1), var0.charAt(var8 + 2));
                        var8 += 2;
                        break;
                    } catch (NumberFormatException var7) {
                        return var4;
                    }
                case '+':
                    var1[var4++] = 32;
                    break;
                default:
                    var1[var4++] = (byte)var5;
            }
        }

        return var4;
    }

    public static final String decode(String var0, String var1) throws UnsupportedEncodingException {
        byte[] var2 = new byte[var0.length()];
        int var3 = decode(var0, var2);
        return new String(var2, 0, var3, var1);
    }

    private static final String encode(byte[] var0) {
        int var1 = 0;
        char[] var2 = new char[3 * var0.length + 1];

        for(int var3 = 0; var3 < var0.length; ++var3) {
            byte var4 = var0[var3];
            int var5 = var4 & 255;
            if (dontNeed[var5]) {
                if (var5 == 32) {
                    var5 = 43;
                }

                var2[var1++] = (char)var5;
            } else {
                var2[var1++] = '%';
                var2[var1++] = hexDigit[var5 >> 4 & 15];
                var2[var1++] = hexDigit[var5 & 15];
            }
        }

        return new String(var2, 0, var1);
    }

    public static final String encode(String var0) {
        if (var0 != null) {
            try {
                return encode(var0.getBytes(encoding));
            } catch (UnsupportedEncodingException var2) {
                ;
            }
        }

        return null;
    }

    public static final String encode(String var0, String var1) throws UnsupportedEncodingException {
        return encode(var0.getBytes(var1));
    }

    private static final byte hex2byte(char var0, char var1) {
        int var2 = var0 - 48;
        if (var2 >= 10) {
            var2 -= 7;
        }

        int var3 = var1 - 48;
        if (var3 >= 10) {
            var3 -= 7;
        }

        return (byte)(var2 << 4 | var3);
    }

    private static final void init() {
        hexDigit = new char[16];
        hexDigit[0] = '0';
        hexDigit[1] = '1';
        hexDigit[2] = '2';
        hexDigit[3] = '3';
        hexDigit[4] = '4';
        hexDigit[5] = '5';
        hexDigit[6] = '6';
        hexDigit[7] = '7';
        hexDigit[8] = '8';
        hexDigit[9] = '9';
        hexDigit[10] = 'A';
        hexDigit[11] = 'B';
        hexDigit[12] = 'C';
        hexDigit[13] = 'D';
        hexDigit[14] = 'E';
        hexDigit[15] = 'F';
        encoding = null;
        String var0 = null;
        var0 = System.getProperty("trs.url.encoding");
        if (testEncoding(var0)) {
            encoding = var0;
        }

        var0 = System.getProperty("file.encoding");
        if (testEncoding(var0)) {
            encoding = var0;
        }

        var0 = "GBK";
        if (testEncoding(var0)) {
            encoding = var0;
        }

        if (encoding == null) {
            encoding = "ASCII";
        }

        dontNeed = new boolean[256];

        int var1;
        for(var1 = 0; var1 < 256; ++var1) {
            dontNeed[var1] = false;
        }

        for(var1 = 97; var1 <= 122; ++var1) {
            dontNeed[var1] = true;
        }

        for(var1 = 65; var1 <= 90; ++var1) {
            dontNeed[var1] = true;
        }

        for(var1 = 48; var1 <= 57; ++var1) {
            dontNeed[var1] = true;
        }

        dontNeed[32] = true;
        dontNeed[45] = true;
        dontNeed[95] = true;
        dontNeed[46] = true;
        dontNeed[42] = true;
    }

    private static final boolean testEncoding(String var0) {
        if (var0 != null) {
            try {
                "test".getBytes(var0);
                return true;
            } catch (Exception var2) {
                ;
            }
        }

        return false;
    }

    private URLCoder() {
    }

    static {
        init();
    }
}
