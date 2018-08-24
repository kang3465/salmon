//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.ele.core.util.cmy.zip;

class PendingBuffer {
    protected byte[] buf;
    int start;
    int end;
    int bits;
    int bitCount;

    public PendingBuffer() {
        this(4096);
    }

    public PendingBuffer(int var1) {
        this.buf = new byte[var1];
    }

    public final void reset() {
        this.start = this.end = this.bitCount = 0;
    }

    public final void writeByte(int var1) {
        this.buf[this.end++] = (byte)var1;
    }

    public final void writeShort(int var1) {
        this.buf[this.end++] = (byte)var1;
        this.buf[this.end++] = (byte)(var1 >> 8);
    }

    public final void writeInt(int var1) {
        this.buf[this.end++] = (byte)var1;
        this.buf[this.end++] = (byte)(var1 >> 8);
        this.buf[this.end++] = (byte)(var1 >> 16);
        this.buf[this.end++] = (byte)(var1 >> 24);
    }

    public final void writeBlock(byte[] var1, int var2, int var3) {
        System.arraycopy(var1, var2, this.buf, this.end, var3);
        this.end += var3;
    }

    public final int getBitCount() {
        return this.bitCount;
    }

    public final void alignToByte() {
        if (this.bitCount > 0) {
            this.buf[this.end++] = (byte)this.bits;
            if (this.bitCount > 8) {
                this.buf[this.end++] = (byte)(this.bits >>> 8);
            }
        }

        this.bits = 0;
        this.bitCount = 0;
    }

    public final void writeBits(int var1, int var2) {
        this.bits |= var1 << this.bitCount;
        this.bitCount += var2;
        if (this.bitCount >= 16) {
            this.buf[this.end++] = (byte)this.bits;
            this.buf[this.end++] = (byte)(this.bits >>> 8);
            this.bits >>>= 16;
            this.bitCount -= 16;
        }

    }

    public final void writeShortMSB(int var1) {
        this.buf[this.end++] = (byte)(var1 >> 8);
        this.buf[this.end++] = (byte)var1;
    }

    public final boolean isFlushed() {
        return this.end == 0;
    }

    public final int flush(byte[] var1, int var2, int var3) {
        if (this.bitCount >= 8) {
            this.buf[this.end++] = (byte)this.bits;
            this.bits >>>= 8;
            this.bitCount -= 8;
        }

        if (var3 > this.end - this.start) {
            var3 = this.end - this.start;
            System.arraycopy(this.buf, this.start, var1, var2, var3);
            this.start = 0;
            this.end = 0;
        } else {
            System.arraycopy(this.buf, this.start, var1, var2, var3);
            this.start += var3;
        }

        return var3;
    }

    public final byte[] toByteArray() {
        byte[] var1 = new byte[this.end - this.start];
        System.arraycopy(this.buf, this.start, var1, 0, var1.length);
        this.start = 0;
        this.end = 0;
        return var1;
    }
}
