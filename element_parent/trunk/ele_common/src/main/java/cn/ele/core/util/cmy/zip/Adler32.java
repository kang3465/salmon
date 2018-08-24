//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.ele.core.util.cmy.zip;

public class Adler32 implements Checksum {
    private static final int BASE = 65521;
    private int checksum;

    public Adler32() {
        this.reset();
    }

    public void reset() {
        this.checksum = 1;
    }

    public void update(int var1) {
        int var2 = this.checksum & '\uffff';
        int var3 = this.checksum >>> 16;
        var2 = (var2 + (var1 & 255)) % '\ufff1';
        var3 = (var2 + var3) % '\ufff1';
        this.checksum = (var3 << 16) + var2;
    }

    public void update(byte[] var1) {
        this.update(var1, 0, var1.length);
    }

    public void update(byte[] var1, int var2, int var3) {
        int var4 = this.checksum & '\uffff';

        int var5;
        for(var5 = this.checksum >>> 16; var3 > 0; var5 %= 65521) {
            int var6 = 3800;
            if (var6 > var3) {
                var6 = var3;
            }

            var3 -= var6;

            while(true) {
                --var6;
                if (var6 < 0) {
                    var4 %= 65521;
                    break;
                }

                var4 += var1[var2++] & 255;
                var5 += var4;
            }
        }

        this.checksum = var5 << 16 | var4;
    }

    public long getValue() {
        return (long)this.checksum & 4294967295L;
    }
}
