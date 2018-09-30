//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.ele.core.util.cmy.zip;

public class InflaterHuffmanTree {
    private static final int MAX_BITLEN = 15;
    private short[] tree;
    public static InflaterHuffmanTree defLitLenTree;
    public static InflaterHuffmanTree defDistTree;

    public InflaterHuffmanTree(byte[] var1) throws DataFormatException {
        this.buildTree(var1);
    }

    private void buildTree(byte[] var1) throws DataFormatException {
        int[] var2 = new int[16];
        int[] var3 = new int[16];

        int var4;
        for(var4 = 0; var4 < var1.length; ++var4) {
            byte var5 = var1[var4];
            if (var5 > 0) {
                ++var2[var5];
            }
        }

        var4 = 0;
        int var12 = 512;

        int var6;
        int var7;
        int var8;
        for(var6 = 1; var6 <= 15; ++var6) {
            var3[var6] = var4;
            var4 += var2[var6] << 16 - var6;
            if (var6 >= 10) {
                var7 = var3[var6] & 130944;
                var8 = var4 & 130944;
                var12 += var8 - var7 >> 16 - var6;
            }
        }

        if (var4 != 65536) {
            throw new DataFormatException("Code lengths don't add up properly.");
        } else {
            this.tree = new short[var12];
            var6 = 512;

            int var9;
            int var10;
            for(var7 = 15; var7 >= 10; --var7) {
                var8 = var4 & 130944;
                var4 -= var2[var7] << 16 - var7;
                var9 = var4 & 130944;

                for(var10 = var9; var10 < var8; var10 += 128) {
                    this.tree[DeflaterHuffman.bitReverse(var10)] = (short)(-var6 << 4 | var7);
                    var6 += 1 << var7 - 9;
                }
            }

            for(var7 = 0; var7 < var1.length; ++var7) {
                byte var13 = var1[var7];
                if (var13 != 0) {
                    var4 = var3[var13];
                    var9 = DeflaterHuffman.bitReverse(var4);
                    if (var13 <= 9) {
                        do {
                            this.tree[var9] = (short)(var7 << 4 | var13);
                            var9 += 1 << var13;
                        } while(var9 < 512);
                    } else {
                        short var14 = this.tree[var9 & 511];
                        int var11 = 1 << (var14 & 15);
                        var10 = -(var14 >> 4);

                        do {
                            this.tree[var10 | var9 >> 9] = (short)(var7 << 4 | var13);
                            var9 += 1 << var13;
                        } while(var9 < var11);
                    }

                    var3[var13] = var4 + (1 << 16 - var13);
                }
            }

        }
    }

    public int getSymbol(StreamManipulator var1) throws DataFormatException {
        int var2;
        short var3;
        int var4;
        if ((var2 = var1.peekBits(9)) >= 0) {
            if ((var3 = this.tree[var2]) >= 0) {
                var1.dropBits(var3 & 15);
                return var3 >> 4;
            } else {
                var4 = -(var3 >> 4);
                int var5 = var3 & 15;
                if ((var2 = var1.peekBits(var5)) >= 0) {
                    var3 = this.tree[var4 | var2 >> 9];
                    var1.dropBits(var3 & 15);
                    return var3 >> 4;
                } else {
                    int var6 = var1.getAvailableBits();
                    var2 = var1.peekBits(var6);
                    var3 = this.tree[var4 | var2 >> 9];
                    if ((var3 & 15) <= var6) {
                        var1.dropBits(var3 & 15);
                        return var3 >> 4;
                    } else {
                        return -1;
                    }
                }
            }
        } else {
            var4 = var1.getAvailableBits();
            var2 = var1.peekBits(var4);
            var3 = this.tree[var2];
            if (var3 >= 0 && (var3 & 15) <= var4) {
                var1.dropBits(var3 & 15);
                return var3 >> 4;
            } else {
                return -1;
            }
        }
    }

    static {
        try {
            byte[] var0 = new byte[288];

            int var1;
            for(var1 = 0; var1 < 144; var0[var1++] = 8) {
                ;
            }

            while(var1 < 256) {
                var0[var1++] = 9;
            }

            while(var1 < 280) {
                var0[var1++] = 7;
            }

            while(var1 < 288) {
                var0[var1++] = 8;
            }

            defLitLenTree = new InflaterHuffmanTree(var0);
            var0 = new byte[32];

            for(var1 = 0; var1 < 32; var0[var1++] = 5) {
                ;
            }

            defDistTree = new InflaterHuffmanTree(var0);
        } catch (DataFormatException var2) {
            throw new InternalError("InflaterHuffmanTree: static tree length illegal");
        }
    }
}
