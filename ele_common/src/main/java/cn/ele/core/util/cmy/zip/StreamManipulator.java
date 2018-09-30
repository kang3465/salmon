//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.ele.core.util.cmy.zip;

class StreamManipulator {
    private byte[] window;
    private int window_start = 0;
    private int window_end = 0;
    private int buffer = 0;
    private int bits_in_buffer = 0;

    public final int peekBits(int var1) {
        if (this.bits_in_buffer < var1) {
            if (this.window_start == this.window_end) {
                return -1;
            }

            this.buffer |= (this.window[this.window_start++] & 255 | (this.window[this.window_start++] & 255) << 8) << this.bits_in_buffer;
            this.bits_in_buffer += 16;
        }

        return this.buffer & (1 << var1) - 1;
    }

    public final void dropBits(int var1) {
        this.buffer >>>= var1;
        this.bits_in_buffer -= var1;
    }

    public final int getBits(int var1) {
        int var2 = this.peekBits(var1);
        if (var2 >= 0) {
            this.dropBits(var1);
        }

        return var2;
    }

    public final int getAvailableBits() {
        return this.bits_in_buffer;
    }

    public final int getAvailableBytes() {
        return this.window_end - this.window_start + (this.bits_in_buffer >> 3);
    }

    public void skipToByteBoundary() {
        this.buffer >>= this.bits_in_buffer & 7;
        this.bits_in_buffer &= -8;
    }

    public final boolean needsInput() {
        return this.window_start == this.window_end;
    }

    public int copyBytes(byte[] var1, int var2, int var3) {
        if (var3 < 0) {
            throw new IllegalArgumentException("length negative");
        } else if ((this.bits_in_buffer & 7) != 0) {
            throw new IllegalStateException("Bit buffer is not aligned!");
        } else {
            int var4;
            for(var4 = 0; this.bits_in_buffer > 0 && var3 > 0; ++var4) {
                var1[var2++] = (byte)this.buffer;
                this.buffer >>>= 8;
                this.bits_in_buffer -= 8;
                --var3;
            }

            if (var3 == 0) {
                return var4;
            } else {
                int var5 = this.window_end - this.window_start;
                if (var3 > var5) {
                    var3 = var5;
                }

                System.arraycopy(this.window, this.window_start, var1, var2, var3);
                this.window_start += var3;
                if ((this.window_start - this.window_end & 1) != 0) {
                    this.buffer = this.window[this.window_start++] & 255;
                    this.bits_in_buffer = 8;
                }

                return var4 + var3;
            }
        }
    }

    public StreamManipulator() {
    }

    public void reset() {
        this.window_start = this.window_end = this.buffer = this.bits_in_buffer = 0;
    }

    public void setInput(byte[] var1, int var2, int var3) {
        if (this.window_start < this.window_end) {
            throw new IllegalStateException("Old input was not completely processed");
        } else {
            int var4 = var2 + var3;
            if (0 <= var2 && var2 <= var4 && var4 <= var1.length) {
                if ((var3 & 1) != 0) {
                    this.buffer |= (var1[var2++] & 255) << this.bits_in_buffer;
                    this.bits_in_buffer += 8;
                }

                this.window = var1;
                this.window_start = var2;
                this.window_end = var4;
            } else {
                throw new ArrayIndexOutOfBoundsException();
            }
        }
    }
}
