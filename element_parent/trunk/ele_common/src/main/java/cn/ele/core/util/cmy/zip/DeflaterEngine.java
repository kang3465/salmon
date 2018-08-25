//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.ele.core.util.cmy.zip;

class DeflaterEngine implements DeflaterConstants {
    private static final int TOO_FAR = 4096;
    private int ins_h;
    private byte[] buffer;
    private short[] head;
    private short[] prev;
    private int matchStart;
    private int matchLen;
    private boolean prevAvailable;
    private int blockStart;
    private int strstart;
    private int lookahead;
    private byte[] window;
    private int strategy;
    private int max_chain;
    private int max_lazy;
    private int niceLength;
    private int goodLength;
    private int comprFunc;
    private byte[] inputBuf;
    private int totalIn;
    private int inputOff;
    private int inputEnd;
    private DeflaterPending pending;
    private DeflaterHuffman huffman;
    private Adler32 adler;

    DeflaterEngine(DeflaterPending var1) {
        this.pending = var1;
        this.huffman = new DeflaterHuffman(var1);
        this.adler = new Adler32();
        this.window = new byte[65536];
        this.head = new short['ҫ'];
        this.prev = new short['ҫ'];
        this.blockStart = this.strstart = 1;
    }

    public void reset() {
        this.huffman.reset();
        this.adler.reset();
        this.blockStart = this.strstart = 1;
        this.lookahead = 0;
        this.totalIn = 0;
        this.prevAvailable = false;
        this.matchLen = 2;

        int var1;
        for(var1 = 0; var1 < 32768; ++var1) {
            this.head[var1] = 0;
        }

        for(var1 = 0; var1 < 32768; ++var1) {
            this.prev[var1] = 0;
        }

    }

    public final void resetAdler() {
        this.adler.reset();
    }

    public final int getAdler() {
        int var1 = (int)this.adler.getValue();
        return var1;
    }

    public final int getTotalIn() {
        return this.totalIn;
    }

    public final void setStrategy(int var1) {
        this.strategy = var1;
    }

    public void setLevel(int var1) {
        this.goodLength = DeflaterConstants.GOOD_LENGTH[var1];
        this.max_lazy = DeflaterConstants.MAX_LAZY[var1];
        this.niceLength = DeflaterConstants.NICE_LENGTH[var1];
        this.max_chain = DeflaterConstants.MAX_CHAIN[var1];
        if (DeflaterConstants.COMPR_FUNC[var1] != this.comprFunc) {
            switch(this.comprFunc) {
                case 0:
                    if (this.strstart > this.blockStart) {
                        this.huffman.flushStoredBlock(this.window, this.blockStart, this.strstart - this.blockStart, false);
                        this.blockStart = this.strstart;
                    }

                    this.updateHash();
                    break;
                case 1:
                    if (this.strstart > this.blockStart) {
                        this.huffman.flushBlock(this.window, this.blockStart, this.strstart - this.blockStart, false);
                        this.blockStart = this.strstart;
                    }
                    break;
                case 2:
                    if (this.prevAvailable) {
                        this.huffman.tallyLit(this.window[this.strstart - 1] & 255);
                    }

                    if (this.strstart > this.blockStart) {
                        this.huffman.flushBlock(this.window, this.blockStart, this.strstart - this.blockStart, false);
                        this.blockStart = this.strstart;
                    }

                    this.prevAvailable = false;
                    this.matchLen = 2;
            }

            this.comprFunc = COMPR_FUNC[var1];
        }

    }

    private final void updateHash() {
        this.ins_h = this.window[this.strstart] << 5 ^ this.window[this.strstart + 1];
    }

    private final int insertString() {
        int var2 = (this.ins_h << 5 ^ this.window[this.strstart + 2]) & 32767;
        short var1;
        this.prev[this.strstart & 32767] = var1 = this.head[var2];
        this.head[var2] = (short)this.strstart;
        this.ins_h = var2;
        return var1 & '\uffff';
    }

    public void fillWindow() {
        while(this.lookahead < 262 && this.inputOff < this.inputEnd) {
            int var1 = 65536 - this.lookahead - this.strstart;
            if (this.strstart >= 65274) {
                System.arraycopy(this.window, 32768, this.window, 0, 32768);
                this.matchStart -= 32768;
                this.strstart -= 32768;
                this.blockStart -= 32768;

                for(int var2 = 0; var2 < 32768; ++var2) {
                    short var3 = this.head[var2];
                    this.head[var2] = var3 >= 'ҫ' ? (short)(var3 - 'ҫ') : 0;
                }

                var1 += 32768;
            }

            if (var1 > this.inputEnd - this.inputOff) {
                var1 = this.inputEnd - this.inputOff;
            }

            System.arraycopy(this.inputBuf, this.inputOff, this.window, this.strstart + this.lookahead, var1);
            this.adler.update(this.inputBuf, this.inputOff, var1);
            this.inputOff += var1;
            this.totalIn += var1;
            this.lookahead += var1;
            if (this.lookahead >= 3) {
                this.updateHash();
            }
        }

    }

    private boolean findLongestMatch(int var1) {
        int var2 = this.max_chain;
        int var3 = this.niceLength;
        short[] var4 = this.prev;
        int var5 = this.strstart;
        int var7 = this.strstart + this.matchLen;
        int var8 = Math.max(this.matchLen, 2);
        int var9 = Math.max(this.strstart - 32506, 0);
        int var10 = this.strstart + 258 - 1;
        byte var11 = this.window[var7 - 1];
        byte var12 = this.window[var7];
        if (var8 >= this.goodLength) {
            var2 >>= 2;
        }

        if (var3 > this.lookahead) {
            var3 = this.lookahead;
        }

        do {
            if (this.window[var1 + var8] == var12 && this.window[var1 + var8 - 1] == var11 && this.window[var1] == this.window[var5] && this.window[var1 + 1] == this.window[var5 + 1]) {
                int var6 = var1 + 2;
                var5 += 2;

                byte var10000;
                do {
                    ++var5;
                    var10000 = this.window[var5];
                    ++var6;
                    if (var10000 != this.window[var6]) {
                        break;
                    }

                    ++var5;
                    var10000 = this.window[var5];
                    ++var6;
                    if (var10000 != this.window[var6]) {
                        break;
                    }

                    ++var5;
                    var10000 = this.window[var5];
                    ++var6;
                    if (var10000 != this.window[var6]) {
                        break;
                    }

                    ++var5;
                    var10000 = this.window[var5];
                    ++var6;
                    if (var10000 != this.window[var6]) {
                        break;
                    }

                    ++var5;
                    var10000 = this.window[var5];
                    ++var6;
                    if (var10000 != this.window[var6]) {
                        break;
                    }

                    ++var5;
                    var10000 = this.window[var5];
                    ++var6;
                    if (var10000 != this.window[var6]) {
                        break;
                    }

                    ++var5;
                    var10000 = this.window[var5];
                    ++var6;
                    if (var10000 != this.window[var6]) {
                        break;
                    }

                    ++var5;
                    var10000 = this.window[var5];
                    ++var6;
                } while(var10000 == this.window[var6] && var5 < var10);

                if (var5 > var7) {
                    this.matchStart = var1;
                    var7 = var5;
                    var8 = var5 - this.strstart;
                    if (var8 >= var3) {
                        break;
                    }

                    var11 = this.window[var5 - 1];
                    var12 = this.window[var5];
                }

                var5 = this.strstart;
            }

            if ((var1 = var4[var1 & 32767] & '\uffff') <= var9) {
                break;
            }

            --var2;
        } while(var2 != 0);

        this.matchLen = Math.min(var8, this.lookahead);
        return this.matchLen >= 3;
    }

    void setDictionary(byte[] var1, int var2, int var3) {
        this.adler.update(var1, var2, var3);
        if (var3 >= 3) {
            if (var3 > 32506) {
                var2 += var3 - 32506;
                var3 = 32506;
            }

            System.arraycopy(var1, var2, this.window, this.strstart, var3);
            this.updateHash();
            --var3;

            while(true) {
                --var3;
                if (var3 <= 0) {
                    this.strstart += 2;
                    this.blockStart = this.strstart;
                    return;
                }

                this.insertString();
                ++this.strstart;
            }
        }
    }

    private boolean deflateStored(boolean var1, boolean var2) {
        if (!var1 && this.lookahead == 0) {
            return false;
        } else {
            this.strstart += this.lookahead;
            this.lookahead = 0;
            int var3 = this.strstart - this.blockStart;
            if (var3 < DeflaterConstants.MAX_BLOCK_SIZE && (this.blockStart >= 32768 || var3 < 32506) && !var1) {
                return true;
            } else {
                boolean var4 = var2;
                if (var3 > DeflaterConstants.MAX_BLOCK_SIZE) {
                    var3 = DeflaterConstants.MAX_BLOCK_SIZE;
                    var4 = false;
                }

                this.huffman.flushStoredBlock(this.window, this.blockStart, var3, var4);
                this.blockStart += var3;
                return !var4;
            }
        }
    }

    private boolean deflateFast(boolean var1, boolean var2) {
        if (this.lookahead < 262 && !var1) {
            return false;
        } else {
            while(true) {
                while(this.lookahead >= 262 || var1) {
                    if (this.lookahead == 0) {
                        this.huffman.flushBlock(this.window, this.blockStart, this.strstart - this.blockStart, var2);
                        this.blockStart = this.strstart;
                        return false;
                    }

                    int var3;
                    if (this.lookahead >= 3 && (var3 = this.insertString()) != 0 && this.strategy != 2 && this.strstart - var3 <= 32506 && this.findLongestMatch(var3)) {
                        this.huffman.tallyDist(this.strstart - this.matchStart, this.matchLen);
                        this.lookahead -= this.matchLen;
                        if (this.matchLen <= this.max_lazy && this.lookahead >= 3) {
                            while(--this.matchLen > 0) {
                                ++this.strstart;
                                this.insertString();
                            }

                            ++this.strstart;
                        } else {
                            this.strstart += this.matchLen;
                            if (this.lookahead >= 2) {
                                this.updateHash();
                            }
                        }

                        this.matchLen = 2;
                    } else {
                        this.huffman.tallyLit(this.window[this.strstart] & 255);
                        ++this.strstart;
                        --this.lookahead;
                        if (this.huffman.isFull()) {
                            boolean var4 = var2 && this.lookahead == 0;
                            this.huffman.flushBlock(this.window, this.blockStart, this.strstart - this.blockStart, var4);
                            this.blockStart = this.strstart;
                            return !var4;
                        }
                    }
                }

                return true;
            }
        }
    }

    private boolean deflateSlow(boolean var1, boolean var2) {
        if (this.lookahead < 262 && !var1) {
            return false;
        } else {
            int var5;
            do {
                if (this.lookahead < 262 && !var1) {
                    return true;
                }

                if (this.lookahead == 0) {
                    if (this.prevAvailable) {
                        this.huffman.tallyLit(this.window[this.strstart - 1] & 255);
                    }

                    this.prevAvailable = false;
                    this.huffman.flushBlock(this.window, this.blockStart, this.strstart - this.blockStart, var2);
                    this.blockStart = this.strstart;
                    return false;
                }

                int var3 = this.matchStart;
                int var4 = this.matchLen;
                if (this.lookahead >= 3) {
                    var5 = this.insertString();
                    if (this.strategy != 2 && var5 != 0 && this.strstart - var5 <= 32506 && this.findLongestMatch(var5) && this.matchLen <= 5 && (this.strategy == 1 || this.matchLen == 3 && this.strstart - this.matchStart > 4096)) {
                        this.matchLen = 2;
                    }
                }

                if (var4 >= 3 && this.matchLen <= var4) {
                    this.huffman.tallyDist(this.strstart - 1 - var3, var4);
                    var4 -= 2;

                    do {
                        ++this.strstart;
                        --this.lookahead;
                        if (this.lookahead >= 3) {
                            this.insertString();
                        }

                        --var4;
                    } while(var4 > 0);

                    ++this.strstart;
                    --this.lookahead;
                    this.prevAvailable = false;
                    this.matchLen = 2;
                } else {
                    if (this.prevAvailable) {
                        this.huffman.tallyLit(this.window[this.strstart - 1] & 255);
                    }

                    this.prevAvailable = true;
                    ++this.strstart;
                    --this.lookahead;
                }
            } while(!this.huffman.isFull());

            var5 = this.strstart - this.blockStart;
            if (this.prevAvailable) {
                --var5;
            }

            boolean var6 = var2 && this.lookahead == 0 && !this.prevAvailable;
            this.huffman.flushBlock(this.window, this.blockStart, var5, var6);
            this.blockStart += var5;
            return !var6;
        }
    }

    public boolean deflate(boolean var1, boolean var2) {
        boolean var3;
        do {
            this.fillWindow();
            boolean var4 = var1 && this.inputOff == this.inputEnd;
            switch(this.comprFunc) {
                case 0:
                    var3 = this.deflateStored(var4, var2);
                    break;
                case 1:
                    var3 = this.deflateFast(var4, var2);
                    break;
                case 2:
                    var3 = this.deflateSlow(var4, var2);
                    break;
                default:
                    throw new InternalError();
            }
        } while(this.pending.isFlushed() && var3);

        return var3;
    }

    public void setInput(byte[] var1, int var2, int var3) {
        if (this.inputOff < this.inputEnd) {
            throw new IllegalStateException("Old input was not completely processed");
        } else {
            int var4 = var2 + var3;
            if (0 <= var2 && var2 <= var4 && var4 <= var1.length) {
                this.inputBuf = var1;
                this.inputOff = var2;
                this.inputEnd = var4;
            } else {
                throw new ArrayIndexOutOfBoundsException();
            }
        }
    }

    public final boolean needsInput() {
        return this.inputEnd == this.inputOff;
    }
}
