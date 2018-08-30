//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.ele.core.util.cmy.zip;

/**
 * @author kang
 */
public class Deflater {
    public static final int BEST_COMPRESSION = 9;
    public static final int BEST_SPEED = 1;
    public static final int DEFAULT_COMPRESSION = -1;
    public static final int NO_COMPRESSION = 0;
    public static final int DEFAULT_STRATEGY = 0;
    public static final int FILTERED = 1;
    public static final int HUFFMAN_ONLY = 2;
    public static final int DEFLATED = 8;
    private static final int IS_SETDICT = 1;
    private static final int IS_FLUSHING = 4;
    private static final int IS_FINISHING = 8;
    private static final int INIT_STATE = 0;
    private static final int SETDICT_STATE = 1;
    private static final int INIT_FINISHING_STATE = 8;
    private static final int SETDICT_FINISHING_STATE = 9;
    private static final int BUSY_STATE = 16;
    private static final int FLUSHING_STATE = 20;
    private static final int FINISHING_STATE = 28;
    private static final int FINISHED_STATE = 30;
    private static final int CLOSED_STATE = 127;
    private int level;
    private boolean noHeader;
    private int strategy;
    private int state;
    private int totalOut;
    private DeflaterPending pending;
    private DeflaterEngine engine;

    public Deflater() {
        this(-1, false);
    }

    public Deflater(int var1) {
        this(var1, false);
    }

    public Deflater(int var1, boolean var2) {
        if (var1 == -1) {
            var1 = 6;
        } else if (var1 < 0 || var1 > 9) {
            throw new IllegalArgumentException();
        }

        this.pending = new DeflaterPending();
        this.engine = new DeflaterEngine(this.pending);
        this.noHeader = var2;
        this.setStrategy(0);
        this.setLevel(var1);
        this.reset();
    }

    public void reset() {
        this.state = this.noHeader ? 16 : 0;
        this.totalOut = 0;
        this.pending.reset();
        this.engine.reset();
    }

    /** @deprecated */
    public void end() {
        this.engine = null;
        this.pending = null;
        this.state = 127;
    }

    public int getAdler() {
        return this.engine.getAdler();
    }

    public int getTotalIn() {
        return this.engine.getTotalIn();
    }

    public int getTotalOut() {
        return this.totalOut;
    }

    void flush() {
        this.state |= 4;
    }

    public void finish() {
        this.state |= 12;
    }

    public boolean finished() {
        return this.state == 30 && this.pending.isFlushed();
    }

    public boolean needsInput() {
        return this.engine.needsInput();
    }

    public void setInput(byte[] var1) {
        this.setInput(var1, 0, var1.length);
    }

    public void setInput(byte[] var1, int var2, int var3) {
        if ((this.state & 8) != 0) {
            throw new IllegalStateException("finish()/end() already called");
        } else {
            this.engine.setInput(var1, var2, var3);
        }
    }

    public void setLevel(int var1) {
        if (var1 == -1) {
            var1 = 6;
        } else if (var1 < 0 || var1 > 9) {
            throw new IllegalArgumentException();
        }

        if (this.level != var1) {
            this.level = var1;
            this.engine.setLevel(var1);
        }

    }

    public void setStrategy(int var1) {
        if (var1 != 0 && var1 != 1 && var1 != 2) {
            throw new IllegalArgumentException();
        } else {
            this.engine.setStrategy(var1);
        }
    }

    public int deflate(byte[] var1) {
        return this.deflate(var1, 0, var1.length);
    }

    public int deflate(byte[] var1, int var2, int var3) {
        int var4 = var3;
        if (this.state == 127) {
            throw new IllegalStateException("Deflater closed");
        } else {
            int var6;
            int var8;
            if (this.state < 16) {
                short var5 = 30720;
                var6 = this.level - 1 >> 1;
                if (var6 < 0 || var6 > 3) {
                    var6 = 3;
                }

                var8 = var5 | var6 << 6;
                if ((this.state & 1) != 0) {
                    var8 |= 32;
                }

                var8 += 31 - var8 % 31;
                this.pending.writeShortMSB(var8);
                if ((this.state & 1) != 0) {
                    int var7 = this.engine.getAdler();
                    this.engine.resetAdler();
                    this.pending.writeShortMSB(var7 >> 16);
                    this.pending.writeShortMSB(var7 & '\uffff');
                }

                this.state = 16 | this.state & 12;
            }

            while(true) {
                var8 = this.pending.flush(var1, var2, var3);
                var2 += var8;
                this.totalOut += var8;
                var3 -= var8;
                if (var3 == 0 || this.state == 30) {
                    return var4 - var3;
                }

                if (!this.engine.deflate((this.state & 4) != 0, (this.state & 8) != 0)) {
                    if (this.state == 16) {
                        return var4 - var3;
                    }

                    if (this.state != 20) {
                        if (this.state == 28) {
                            this.pending.alignToByte();
                            if (!this.noHeader) {
                                var6 = this.engine.getAdler();
                                this.pending.writeShortMSB(var6 >> 16);
                                this.pending.writeShortMSB(var6 & '\uffff');
                            }

                            this.state = 30;
                        }
                    } else {
                        if (this.level != 0) {
                            for(var6 = 8 + (-this.pending.getBitCount() & 7); var6 > 0; var6 -= 10) {
                                this.pending.writeBits(2, 10);
                            }
                        }

                        this.state = 16;
                    }
                }
            }
        }
    }

    public void setDictionary(byte[] var1) {
        this.setDictionary(var1, 0, var1.length);
    }

    public void setDictionary(byte[] var1, int var2, int var3) {
        if (this.state != 0) {
            throw new IllegalStateException();
        } else {
            this.state = 1;
            this.engine.setDictionary(var1, var2, var3);
        }
    }
}
