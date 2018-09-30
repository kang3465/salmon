//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.ele.core.util.cmy.zip;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class InflaterInputStream extends FilterInputStream {
    protected Inflater inf;
    protected byte[] buf;
    protected int len;
    private byte[] onebytebuffer;

    public InflaterInputStream(InputStream var1) {
        this(var1, new Inflater(), 4096);
    }

    public InflaterInputStream(InputStream var1, Inflater var2) {
        this(var1, var2, 4096);
    }

    public InflaterInputStream(InputStream var1, Inflater var2, int var3) {
        super(var1);
        this.onebytebuffer = new byte[1];
        this.inf = var2;
        this.len = 0;
        if (var3 <= 0) {
            throw new IllegalArgumentException("size <= 0");
        } else {
            this.buf = new byte[var3];
        }
    }

    public int available() throws IOException {
        return this.inf.finished() ? 0 : 1;
    }

    public void close() throws IOException {
        this.in.close();
    }

    protected void fill() throws IOException {
        this.len = this.in.read(this.buf, 0, this.buf.length);
        if (this.len < 0) {
            throw new ZipException("Deflated stream ends early.");
        } else {
            this.inf.setInput(this.buf, 0, this.len);
        }
    }

    public int read() throws IOException {
        int var1 = this.read(this.onebytebuffer, 0, 1);
        return var1 > 0 ? this.onebytebuffer[0] & 255 : -1;
    }

    public int read(byte[] var1, int var2, int var3) throws IOException {
        while(true) {
            int var4;
            try {
                var4 = this.inf.inflate(var1, var2, var3);
            } catch (DataFormatException var6) {
                throw new ZipException(var6.getMessage());
            }

            if (var4 > 0) {
                return var4;
            }

            if (this.inf.needsDictionary()) {
                throw new ZipException("Need a dictionary");
            }

            if (this.inf.finished()) {
                return -1;
            }

            if (!this.inf.needsInput()) {
                throw new InternalError("Don't know what to do");
            }

            this.fill();
        }
    }

    public long skip(long var1) throws IOException {
        if (var1 < 0L) {
            throw new IllegalArgumentException();
        } else {
            int var3 = 2048;
            if (var1 < (long)var3) {
                var3 = (int)var1;
            }

            byte[] var4 = new byte[var3];
            return (long)this.read(var4);
        }
    }
}
