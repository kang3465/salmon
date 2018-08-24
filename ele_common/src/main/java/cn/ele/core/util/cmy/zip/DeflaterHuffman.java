//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.ele.core.util.cmy.zip;

class DeflaterHuffman {
    private static final int BUFSIZE = 16384;
    private static final int LITERAL_NUM = 286;
    private static final int DIST_NUM = 30;
    private static final int BITLEN_NUM = 19;
    private static final int REP_3_6 = 16;
    private static final int REP_3_10 = 17;
    private static final int REP_11_138 = 18;
    private static final int EOF_SYMBOL = 256;
    private static final int[] BL_ORDER = new int[]{16, 17, 18, 0, 8, 7, 9, 6, 10, 5, 11, 4, 12, 3, 13, 2, 14, 1, 15};
    private static final String bit4Reverse = "\u0000\b\u0004\f\u0002\n\u0006\u000e\u0001\t\u0005\r\u0003\u000b\u0007\u000f";
    DeflaterPending pending;
    private DeflaterHuffman.Tree literalTree;
    private DeflaterHuffman.Tree distTree;
    private DeflaterHuffman.Tree blTree;
    private short[] d_buf;
    private byte[] l_buf;
    private int last_lit;
    private int extra_bits;
    private static short[] staticLCodes = new short[286];
    private static byte[] staticLLength = new byte[286];
    private static short[] staticDCodes;
    private static byte[] staticDLength;

    static short bitReverse(int var0) {
        return (short)("\u0000\b\u0004\f\u0002\n\u0006\u000e\u0001\t\u0005\r\u0003\u000b\u0007\u000f".charAt(var0 & 15) << 12 | "\u0000\b\u0004\f\u0002\n\u0006\u000e\u0001\t\u0005\r\u0003\u000b\u0007\u000f".charAt(var0 >> 4 & 15) << 8 | "\u0000\b\u0004\f\u0002\n\u0006\u000e\u0001\t\u0005\r\u0003\u000b\u0007\u000f".charAt(var0 >> 8 & 15) << 4 | "\u0000\b\u0004\f\u0002\n\u0006\u000e\u0001\t\u0005\r\u0003\u000b\u0007\u000f".charAt(var0 >> 12));
    }

    public DeflaterHuffman(DeflaterPending var1) {
        this.pending = var1;
        this.literalTree = new DeflaterHuffman.Tree(286, 257, 15);
        this.distTree = new DeflaterHuffman.Tree(30, 1, 15);
        this.blTree = new DeflaterHuffman.Tree(19, 4, 7);
        this.d_buf = new short[16384];
        this.l_buf = new byte[16384];
    }

    public final void reset() {
        this.last_lit = 0;
        this.extra_bits = 0;
        this.literalTree.reset();
        this.distTree.reset();
        this.blTree.reset();
    }

    private final int l_code(int var1) {
        if (var1 == 255) {
            return 285;
        } else {
            int var2;
            for(var2 = 257; var1 >= 8; var1 >>= 1) {
                var2 += 4;
            }

            return var2 + var1;
        }
    }

    private final int d_code(int var1) {
        int var2;
        for(var2 = 0; var1 >= 4; var1 >>= 1) {
            var2 += 2;
        }

        return var2 + var1;
    }

    public void sendAllTrees(int var1) {
        this.blTree.buildCodes();
        this.literalTree.buildCodes();
        this.distTree.buildCodes();
        this.pending.writeBits(this.literalTree.numCodes - 257, 5);
        this.pending.writeBits(this.distTree.numCodes - 1, 5);
        this.pending.writeBits(var1 - 4, 4);

        for(int var2 = 0; var2 < var1; ++var2) {
            this.pending.writeBits(this.blTree.length[BL_ORDER[var2]], 3);
        }

        this.literalTree.writeTree(this.blTree);
        this.distTree.writeTree(this.blTree);
    }

    public void compressBlock() {
        for(int var1 = 0; var1 < this.last_lit; ++var1) {
            int var2 = this.l_buf[var1] & 255;
            short var3 = this.d_buf[var1];
            int var7 = var3 - 1;
            if (var3 != 0) {
                int var4 = this.l_code(var2);
                this.literalTree.writeSymbol(var4);
                int var5 = (var4 - 261) / 4;
                if (var5 > 0 && var5 <= 5) {
                    this.pending.writeBits(var2 & (1 << var5) - 1, var5);
                }

                int var6 = this.d_code(var7);
                this.distTree.writeSymbol(var6);
                var5 = var6 / 2 - 1;
                if (var5 > 0) {
                    this.pending.writeBits(var7 & (1 << var5) - 1, var5);
                }
            } else {
                this.literalTree.writeSymbol(var2);
            }
        }

        this.literalTree.writeSymbol(256);
    }

    public void flushStoredBlock(byte[] var1, int var2, int var3, boolean var4) {
        this.pending.writeBits(0 + (var4 ? 1 : 0), 3);
        this.pending.alignToByte();
        this.pending.writeShort(var3);
        this.pending.writeShort(~var3);
        this.pending.writeBlock(var1, var2, var3);
        this.reset();
    }

    public void flushBlock(byte[] var1, int var2, int var3, boolean var4) {
        ++this.literalTree.freqs[256];
        this.literalTree.buildTree();
        this.distTree.buildTree();
        this.literalTree.calcBLFreq(this.blTree);
        this.distTree.calcBLFreq(this.blTree);
        this.blTree.buildTree();
        int var5 = 4;

        int var6;
        for(var6 = 18; var6 > var5; --var6) {
            if (this.blTree.length[BL_ORDER[var6]] > 0) {
                var5 = var6 + 1;
            }
        }

        var6 = 14 + var5 * 3 + this.blTree.getEncodedLength() + this.literalTree.getEncodedLength() + this.distTree.getEncodedLength() + this.extra_bits;
        int var7 = this.extra_bits;

        int var8;
        for(var8 = 0; var8 < 286; ++var8) {
            var7 += this.literalTree.freqs[var8] * staticLLength[var8];
        }

        for(var8 = 0; var8 < 30; ++var8) {
            var7 += this.distTree.freqs[var8] * staticDLength[var8];
        }

        if (var6 >= var7) {
            var6 = var7;
        }

        if (var2 >= 0 && var3 + 4 < var6 >> 3) {
            this.flushStoredBlock(var1, var2, var3, var4);
        } else if (var6 == var7) {
            this.pending.writeBits(2 + (var4 ? 1 : 0), 3);
            this.literalTree.setStaticCodes(staticLCodes, staticLLength);
            this.distTree.setStaticCodes(staticDCodes, staticDLength);
            this.compressBlock();
            this.reset();
        } else {
            this.pending.writeBits(4 + (var4 ? 1 : 0), 3);
            this.sendAllTrees(var5);
            this.compressBlock();
            this.reset();
        }

    }

    public final boolean isFull() {
        return this.last_lit == 16384;
    }

    public final boolean tallyLit(int var1) {
        this.d_buf[this.last_lit] = 0;
        this.l_buf[this.last_lit++] = (byte)var1;
        ++this.literalTree.freqs[var1];
        return this.last_lit == 16384;
    }

    public final boolean tallyDist(int var1, int var2) {
        this.d_buf[this.last_lit] = (short)var1;
        this.l_buf[this.last_lit++] = (byte)(var2 - 3);
        int var3 = this.l_code(var2 - 3);
        ++this.literalTree.freqs[var3];
        if (var3 >= 265 && var3 < 285) {
            this.extra_bits += (var3 - 261) / 4;
        }

        int var4 = this.d_code(var1 - 1);
        ++this.distTree.freqs[var4];
        if (var4 >= 4) {
            this.extra_bits += var4 / 2 - 1;
        }

        return this.last_lit == 16384;
    }

    static {
        int var0;
        for(var0 = 0; var0 < 144; staticLLength[var0++] = 8) {
            staticLCodes[var0] = bitReverse(48 + var0 << 8);
        }

        while(var0 < 256) {
            staticLCodes[var0] = bitReverse(256 + var0 << 7);
            staticLLength[var0++] = 9;
        }

        while(var0 < 280) {
            staticLCodes[var0] = bitReverse(-256 + var0 << 9);
            staticLLength[var0++] = 7;
        }

        while(var0 < 286) {
            staticLCodes[var0] = bitReverse(-88 + var0 << 8);
            staticLLength[var0++] = 8;
        }

        staticDCodes = new short[30];
        staticDLength = new byte[30];

        for(var0 = 0; var0 < 30; ++var0) {
            staticDCodes[var0] = bitReverse(var0 << 11);
            staticDLength[var0] = 5;
        }

    }

    class Tree {
        short[] freqs;
        short[] codes;
        byte[] length;
        int[] bl_counts;
        int minNumCodes;
        int numCodes;
        int maxLength;

        Tree(int var2, int var3, int var4) {
            this.minNumCodes = var3;
            this.maxLength = var4;
            this.freqs = new short[var2];
            this.bl_counts = new int[var4];
        }

        void reset() {
            for(int var1 = 0; var1 < this.freqs.length; ++var1) {
                this.freqs[var1] = 0;
            }

            this.codes = null;
            this.length = null;
        }

        final void writeSymbol(int var1) {
            DeflaterHuffman.this.pending.writeBits(this.codes[var1] & '\uffff', this.length[var1]);
        }

        final void checkEmpty() {
            boolean var1 = true;

            for(int var2 = 0; var2 < this.freqs.length; ++var2) {
                if (this.freqs[var2] != 0) {
                    System.err.println("freqs[" + var2 + "] == " + this.freqs[var2]);
                    var1 = false;
                }
            }

            if (!var1) {
                throw new InternalError();
            } else {
                System.err.println("checkEmpty suceeded!");
            }
        }

        void setStaticCodes(short[] var1, byte[] var2) {
            this.codes = var1;
            this.length = var2;
        }

        public void buildCodes() {
            int var1 = this.freqs.length;
            int[] var2 = new int[this.maxLength];
            int var3 = 0;
            this.codes = new short[this.freqs.length];

            int var4;
            for(var4 = 0; var4 < this.maxLength; ++var4) {
                var2[var4] = var3;
                var3 += this.bl_counts[var4] << 15 - var4;
            }

            for(var4 = 0; var4 < this.numCodes; ++var4) {
                byte var5 = this.length[var4];
                if (var5 > 0) {
                    this.codes[var4] = DeflaterHuffman.bitReverse(var2[var5 - 1]);
                    var2[var5 - 1] += 1 << 16 - var5;
                }
            }

        }

        private void buildLength(int[] var1) {
            this.length = new byte[this.freqs.length];
            int var2 = var1.length / 2;
            int var3 = (var2 + 1) / 2;
            int var4 = 0;

            for(int var5 = 0; var5 < this.maxLength; ++var5) {
                this.bl_counts[var5] = 0;
            }

            int[] var11 = new int[var2];
            var11[var2 - 1] = 0;

            int var6;
            int var7;
            for(var6 = var2 - 1; var6 >= 0; --var6) {
                if (var1[2 * var6 + 1] != -1) {
                    var7 = var11[var6] + 1;
                    if (var7 > this.maxLength) {
                        var7 = this.maxLength;
                        ++var4;
                    }

                    var11[var1[2 * var6]] = var11[var1[2 * var6 + 1]] = var7;
                } else {
                    var7 = var11[var6];
                    ++this.bl_counts[var7 - 1];
                    this.length[var1[2 * var6]] = (byte)var11[var6];
                }
            }

            if (var4 != 0) {
                var6 = this.maxLength - 1;

                do {
                    do {
                        --var6;
                    } while(this.bl_counts[var6] == 0);

                    do {
                        --this.bl_counts[var6];
                        ++var6;
                        ++this.bl_counts[var6];
                        var4 -= 1 << this.maxLength - 1 - var6;
                    } while(var4 > 0 && var6 < this.maxLength - 1);
                } while(var4 > 0);

                this.bl_counts[this.maxLength - 1] += var4;
                this.bl_counts[this.maxLength - 2] -= var4;
                var7 = 2 * var3;

                for(int var8 = this.maxLength; var8 != 0; --var8) {
                    int var9 = this.bl_counts[var8 - 1];

                    while(var9 > 0) {
                        int var10 = 2 * var1[var7++];
                        if (var1[var10 + 1] == -1) {
                            this.length[var1[var10]] = (byte)var8;
                            --var9;
                        }
                    }
                }

            }
        }

        void buildTree() {
            int var1 = this.freqs.length;
            int[] var2 = new int[var1];
            int var3 = 0;
            int var4 = 0;

            int var5;
            int var8;
            for(var5 = 0; var5 < var1; ++var5) {
                short var6 = this.freqs[var5];
                if (var6 != 0) {
                    int var7;
                    for(var7 = var3++; var7 > 0 && this.freqs[var2[var8 = (var7 - 1) / 2]] > var6; var7 = var8) {
                        var2[var7] = var2[var8];
                    }

                    var2[var7] = var5;
                    var4 = var5;
                }
            }

            while(var3 < 2) {
                int var10000;
                if (var4 < 2) {
                    ++var4;
                    var10000 = var4;
                } else {
                    var10000 = 0;
                }

                var5 = var10000;
                var2[var3++] = var5;
            }

            this.numCodes = Math.max(var4 + 1, this.minNumCodes);
            int[] var16 = new int[4 * var3 - 2];
            int[] var17 = new int[2 * var3 - 1];
            var8 = var3;

            int var9;
            int var10;
            for(var9 = 0; var9 < var3; var2[var9] = var9++) {
                var10 = var2[var9];
                var16[2 * var9] = var10;
                var16[2 * var9 + 1] = -1;
                var17[var9] = this.freqs[var10] << 8;
            }

            label82:
            do {
                var9 = var2[0];
                --var3;
                var10 = var2[var3];
                int var11 = 0;

                int var12;
                for(var12 = 1; var12 < var3; var12 = var12 * 2 + 1) {
                    if (var12 + 1 < var3 && var17[var2[var12]] > var17[var2[var12 + 1]]) {
                        ++var12;
                    }

                    var2[var11] = var2[var12];
                    var11 = var12;
                }

                int var13 = var17[var10];

                while(true) {
                    var12 = var11;
                    if (var11 <= 0 || var17[var2[var11 = (var11 - 1) / 2]] <= var13) {
                        var2[var11] = var10;
                        int var14 = var2[0];
                        var10 = var8++;
                        var16[2 * var10] = var9;
                        var16[2 * var10 + 1] = var14;
                        int var15 = Math.min(var17[var9] & 255, var17[var14] & 255);
                        var17[var10] = var13 = var17[var9] + var17[var14] - var15 + 1;
                        var11 = 0;

                        for(var12 = 1; var12 < var3; var12 = var12 * 2 + 1) {
                            if (var12 + 1 < var3 && var17[var2[var12]] > var17[var2[var12 + 1]]) {
                                ++var12;
                            }

                            var2[var11] = var2[var12];
                            var11 = var12;
                        }

                        while(true) {
                            var12 = var11;
                            if (var11 <= 0 || var17[var2[var11 = (var11 - 1) / 2]] <= var13) {
                                var2[var11] = var10;
                                continue label82;
                            }

                            var2[var12] = var2[var11];
                        }
                    }

                    var2[var12] = var2[var11];
                }
            } while(var3 > 1);

            if (var2[0] != var16.length / 2 - 1) {
                throw new RuntimeException("Weird!");
            } else {
                this.buildLength(var16);
            }
        }

        int getEncodedLength() {
            int var1 = 0;

            for(int var2 = 0; var2 < this.freqs.length; ++var2) {
                var1 += this.freqs[var2] * this.length[var2];
            }

            return var1;
        }

        void calcBLFreq(DeflaterHuffman.Tree var1) {
            byte var5 = -1;
            int var6 = 0;

            while(var6 < this.numCodes) {
                int var4 = 1;
                byte var7 = this.length[var6];
                short var2;
                byte var3;
                if (var7 == 0) {
                    var2 = 138;
                    var3 = 3;
                } else {
                    var2 = 6;
                    var3 = 3;
                    if (var5 != var7) {
                        ++var1.freqs[var7];
                        var4 = 0;
                    }
                }

                var5 = var7;
                ++var6;

                while(var6 < this.numCodes && var5 == this.length[var6]) {
                    ++var6;
                    ++var4;
                    if (var4 >= var2) {
                        break;
                    }
                }

                if (var4 < var3) {
                    var1.freqs[var5] = (short)(var1.freqs[var5] + var4);
                } else if (var5 != 0) {
                    ++var1.freqs[16];
                } else if (var4 <= 10) {
                    ++var1.freqs[17];
                } else {
                    ++var1.freqs[18];
                }
            }

        }

        void writeTree(DeflaterHuffman.Tree var1) {
            byte var5 = -1;
            int var6 = 0;

            while(true) {
                while(var6 < this.numCodes) {
                    int var4 = 1;
                    byte var7 = this.length[var6];
                    short var2;
                    byte var3;
                    if (var7 == 0) {
                        var2 = 138;
                        var3 = 3;
                    } else {
                        var2 = 6;
                        var3 = 3;
                        if (var5 != var7) {
                            var1.writeSymbol(var7);
                            var4 = 0;
                        }
                    }

                    var5 = var7;
                    ++var6;

                    while(var6 < this.numCodes && var5 == this.length[var6]) {
                        ++var6;
                        ++var4;
                        if (var4 >= var2) {
                            break;
                        }
                    }

                    if (var4 < var3) {
                        while(var4-- > 0) {
                            var1.writeSymbol(var5);
                        }
                    } else if (var5 != 0) {
                        var1.writeSymbol(16);
                        DeflaterHuffman.this.pending.writeBits(var4 - 3, 2);
                    } else if (var4 <= 10) {
                        var1.writeSymbol(17);
                        DeflaterHuffman.this.pending.writeBits(var4 - 3, 3);
                    } else {
                        var1.writeSymbol(18);
                        DeflaterHuffman.this.pending.writeBits(var4 - 11, 7);
                    }
                }

                return;
            }
        }
    }
}
