//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.ele.core.util.cmy.zip;

class OutputWindow {
    private final int WINDOW_SIZE = 32768;
    private final int WINDOW_MASK = 32767;
    private byte[] window = new byte['耀'];
    private int window_end = 0;
    private int window_filled = 0;

    OutputWindow() {
    }

    public void write(int var1) {
        if (this.window_filled++ == 32768) {
            throw new IllegalStateException("Window full");
        } else {
            this.window[this.window_end++] = (byte)var1;
            this.window_end &= 32767;
        }
    }

    private final void slowRepeat(int var1, int var2, int var3) {
        while(var2-- > 0) {
            this.window[this.window_end++] = this.window[var1++];
            this.window_end &= 32767;
            var1 &= 32767;
        }

    }

    public void repeat(int var1, int var2) {
        if ((this.window_filled += var1) > 32768) {
            throw new IllegalStateException("Window full");
        } else {
            int var3 = this.window_end - var2 & 32767;
            int var4 = '耀' - var1;
            if (var3 <= var4 && this.window_end < var4) {
                if (var1 > var2) {
                    while(var1-- > 0) {
                        this.window[this.window_end++] = this.window[var3++];
                    }
                } else {
                    System.arraycopy(this.window, var3, this.window, this.window_end, var1);
                    this.window_end += var1;
                }
            } else {
                this.slowRepeat(var3, var1, var2);
            }

        }
    }

    public int copyStored(StreamManipulator var1, int var2) {
        var2 = Math.min(Math.min(var2, '耀' - this.window_filled), var1.getAvailableBytes());
        int var4 = '耀' - this.window_end;
        int var3;
        if (var2 > var4) {
            var3 = var1.copyBytes(this.window, this.window_end, var4);
            if (var3 == var4) {
                var3 += var1.copyBytes(this.window, 0, var2 - var4);
            }
        } else {
            var3 = var1.copyBytes(this.window, this.window_end, var2);
        }

        this.window_end = this.window_end + var3 & 32767;
        this.window_filled += var3;
        return var3;
    }

    public void copyDict(byte[] var1, int var2, int var3) {
        if (this.window_filled > 0) {
            throw new IllegalStateException();
        } else {
            if (var3 > 32768) {
                var2 += var3 - '耀';
                var3 = 32768;
            }

            System.arraycopy(var1, var2, this.window, 0, var3);
            this.window_end = var3 & 32767;
        }
    }

    public int getFreeSpace() {
        return '耀' - this.window_filled;
    }

    public int getAvailable() {
        return this.window_filled;
    }

    public int copyOutput(byte[] var1, int var2, int var3) {
        int var4 = this.window_end;
        if (var3 > this.window_filled) {
            var3 = this.window_filled;
        } else {
            var4 = this.window_end - this.window_filled + var3 & 32767;
        }

        int var5 = var3;
        int var6 = var3 - var4;
        if (var6 > 0) {
            System.arraycopy(this.window, '耀' - var6, var1, var2, var6);
            var2 += var6;
            var3 = var4;
        }

        System.arraycopy(this.window, var4 - var3, var1, var2, var3);
        this.window_filled -= var5;
        if (this.window_filled < 0) {
            throw new IllegalStateException();
        } else {
            return var5;
        }
    }

    public void reset() {
        this.window_filled = this.window_end = 0;
    }
}
