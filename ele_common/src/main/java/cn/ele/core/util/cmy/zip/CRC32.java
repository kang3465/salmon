//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.ele.core.util.cmy.zip;

public class CRC32 implements Checksum {
    private int crc = 0;
    private static int[] crc_table = make_crc_table();

    public CRC32() {
    }

    private static int[] make_crc_table() {
        int[] var0 = new int[256];

        for(int var1 = 0; var1 < 256; ++var1) {
            int var2 = var1;
            int var3 = 8;

            while(true) {
                --var3;
                if (var3 < 0) {
                    var0[var1] = var2;
                    break;
                }

                if ((var2 & 1) != 0) {
                    var2 = -306674912 ^ var2 >>> 1;
                } else {
                    var2 >>>= 1;
                }
            }
        }

        return var0;
    }

    public long getValue() {
        return (long)this.crc & 4294967295L;
    }

    public void reset() {
        this.crc = 0;
    }

    public void update(int var1) {
        int var2 = ~this.crc;
        var2 = crc_table[(var2 ^ var1) & 255] ^ var2 >>> 8;
        this.crc = ~var2;
    }

    public void update(byte[] var1, int var2, int var3) {
        int var4 = ~this.crc;

        while(true) {
            --var3;
            if (var3 < 0) {
                this.crc = ~var4;
                return;
            }

            var4 = crc_table[(var4 ^ var1[var2++]) & 255] ^ var4 >>> 8;
        }
    }

    public void update(byte[] var1) {
        this.update(var1, 0, var1.length);
    }
}
