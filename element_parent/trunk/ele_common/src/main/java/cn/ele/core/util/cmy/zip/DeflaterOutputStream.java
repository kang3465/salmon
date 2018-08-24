//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.ele.core.util.cmy.zip;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class DeflaterOutputStream extends FilterOutputStream {
    protected byte[] buf;
    protected Deflater def;

    protected void deflate() throws IOException {
        while(true) {
            if (!this.def.needsInput()) {
                int var1 = this.def.deflate(this.buf, 0, this.buf.length);
                if (var1 > 0) {
                    this.out.write(this.buf, 0, var1);
                    continue;
                }
            }

            if (!this.def.needsInput()) {
                throw new InternalError("Can't deflate all input?");
            }

            return;
        }
    }

    public DeflaterOutputStream(OutputStream var1) {
        this(var1, new Deflater(), 512);
    }

    public DeflaterOutputStream(OutputStream var1, Deflater var2) {
        this(var1, var2, 512);
    }

    public DeflaterOutputStream(OutputStream var1, Deflater var2, int var3) {
        super(var1);
        if (var3 <= 0) {
            throw new IllegalArgumentException("bufsize <= 0");
        } else {
            this.buf = new byte[var3];
            this.def = var2;
        }
    }

    public void flush() throws IOException {
        this.def.flush();
        this.deflate();
        this.out.flush();
    }

    public void finish() throws IOException {
        this.def.finish();

        while(!this.def.finished()) {
            int var1 = this.def.deflate(this.buf, 0, this.buf.length);
            if (var1 <= 0) {
                break;
            }

            this.out.write(this.buf, 0, var1);
        }

        if (!this.def.finished()) {
            throw new InternalError("Can't deflate all input?");
        } else {
            this.out.flush();
        }
    }

    public void close() throws IOException {
        this.finish();
        this.out.close();
    }

    public void write(int var1) throws IOException {
        byte[] var2 = new byte[]{(byte)var1};
        this.write(var2, 0, 1);
    }

    public void write(byte[] var1, int var2, int var3) throws IOException {
        this.def.setInput(var1, var2, var3);
        this.deflate();
    }
}
