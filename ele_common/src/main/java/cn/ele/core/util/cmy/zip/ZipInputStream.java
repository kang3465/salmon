//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.ele.core.util.cmy.zip;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public class ZipInputStream extends InflaterInputStream implements ZipConstants {
    private CRC32 crc = new CRC32();
    private ZipEntry entry = null;
    private int csize;
    private int size;
    private int method;
    private int flags;
    private int avail;

    public ZipInputStream(InputStream var1) {
        super(var1, new Inflater(true));
    }

    private void fillBuf() throws IOException {
        this.avail = this.len = this.in.read(this.buf, 0, this.buf.length);
    }

    private int readBuf(byte[] var1, int var2, int var3) throws IOException {
        if (this.avail <= 0) {
            this.fillBuf();
            if (this.avail <= 0) {
                return -1;
            }
        }

        if (var3 > this.avail) {
            var3 = this.avail;
        }

        System.arraycopy(this.buf, this.len - this.avail, var1, var2, var3);
        this.avail -= var3;
        return var3;
    }

    private void readFully(byte[] var1) throws IOException {
        int var2 = 0;

        int var4;
        for(int var3 = var1.length; var3 > 0; var3 -= var4) {
            var4 = this.readBuf(var1, var2, var3);
            if (var4 == -1) {
                throw new EOFException();
            }

            var2 += var4;
        }

    }

    private final int readLeByte() throws IOException {
        if (this.avail <= 0) {
            this.fillBuf();
            if (this.avail <= 0) {
                throw new ZipException("EOF in header");
            }
        }

        return this.buf[this.len - this.avail--] & 255;
    }

    private final int readLeShort() throws IOException {
        return this.readLeByte() | this.readLeByte() << 8;
    }

    private final int readLeInt() throws IOException {
        return this.readLeShort() | this.readLeShort() << 16;
    }

    public ZipEntry getNextEntry() throws IOException {
        return this.getNextEntry((String)null);
    }

    public ZipEntry getNextEntry(String var1) throws IOException {
        if (this.crc == null) {
            throw new IllegalStateException("Closed.");
        } else {
            if (this.entry != null) {
                this.closeEntry();
            }

            int var2 = this.readLeInt();
            if (var2 == 33639248) {
                this.close();
                return null;
            } else if (var2 != 67324752) {
                throw new ZipException("Wrong Local header signature" + Integer.toHexString(var2));
            } else {
                this.readLeShort();
                this.flags = this.readLeShort();
                this.method = this.readLeShort();
                int var3 = this.readLeInt();
                int var4 = this.readLeInt();
                this.csize = this.readLeInt();
                this.size = this.readLeInt();
                int var5 = this.readLeShort();
                int var6 = this.readLeShort();
                if (this.method == 0 && this.csize != this.size) {
                    throw new ZipException("Stored, but compressed != uncompressed");
                } else {
                    byte[] var7 = new byte[var5];
                    this.readFully(var7);
                    String var8 = var1 == null ? new String(var7) : new String(var7, var1);
                    this.entry = new ZipEntry(var8);
                    this.entry.setMethod(this.method);
                    if ((this.flags & 8) == 0) {
                        this.entry.setCrc((long)var4 & 4294967295L);
                        this.entry.setSize((long)this.size & 4294967295L);
                        this.entry.setCompressedSize((long)this.csize & 4294967295L);
                    }

                    this.entry.setDOSTime(var3);
                    if (var6 > 0) {
                        byte[] var9 = new byte[var6];
                        this.readFully(var9);
                        this.entry.setExtra(var9);
                    }

                    if (this.method == 8 && this.avail > 0) {
                        System.arraycopy(this.buf, this.len - this.avail, this.buf, 0, this.avail);
                        this.len = this.avail;
                        this.avail = 0;
                        this.inf.setInput(this.buf, 0, this.len);
                    }

                    return this.entry;
                }
            }
        }
    }

    private void readDataDescr() throws IOException {
        if (this.readLeInt() != 134695760) {
            throw new ZipException("Data descriptor signature not found");
        } else {
            this.entry.setCrc((long)this.readLeInt() & 4294967295L);
            this.csize = this.readLeInt();
            this.size = this.readLeInt();
            this.entry.setSize((long)this.size & 4294967295L);
            this.entry.setCompressedSize((long)this.csize & 4294967295L);
        }
    }

    public void closeEntry() throws IOException {
        if (this.crc == null) {
            throw new IllegalStateException("Closed.");
        } else if (this.entry != null) {
            if (this.method == 8) {
                if ((this.flags & 8) != 0) {
                    byte[] var3 = new byte[2048];

                    while(this.read(var3) > 0) {
                        ;
                    }

                    return;
                }

                this.csize -= this.inf.getTotalIn();
                this.avail = this.inf.getRemaining();
            }

            if (this.avail > this.csize && this.csize >= 0) {
                this.avail -= this.csize;
            } else {
                this.csize -= this.avail;

                long var1;
                for(this.avail = 0; this.csize != 0; this.csize = (int)((long)this.csize - var1)) {
                    var1 = this.in.skip((long)this.csize & 4294967295L);
                    if (var1 <= 0L) {
                        throw new ZipException("zip archive ends early.");
                    }
                }
            }

            this.size = 0;
            this.crc.reset();
            if (this.method == 8) {
                this.inf.reset();
            }

            this.entry = null;
        }
    }

    public int available() throws IOException {
        return this.entry != null ? 1 : 0;
    }

    public int read() throws IOException {
        byte[] var1 = new byte[1];
        return this.read(var1, 0, 1) <= 0 ? -1 : var1[0] & 255;
    }

    public int read(byte[] var1, int var2, int var3) throws IOException {
        if (this.crc == null) {
            throw new IllegalStateException("Closed.");
        } else if (this.entry == null) {
            return -1;
        } else {
            boolean var4 = false;
            switch(this.method) {
                case 0:
                    if (var3 > this.csize && this.csize >= 0) {
                        var3 = this.csize;
                    }

                    var3 = this.readBuf(var1, var2, var3);
                    if (var3 > 0) {
                        this.csize -= var3;
                        this.size -= var3;
                    }

                    if (this.csize == 0) {
                        var4 = true;
                    } else if (var3 < 0) {
                        throw new ZipException("EOF in stored block");
                    }
                    break;
                case 8:
                    var3 = super.read(var1, var2, var3);
                    if (var3 < 0) {
                        if (!this.inf.finished()) {
                            throw new ZipException("Inflater not finished!?");
                        }

                        this.avail = this.inf.getRemaining();
                        if ((this.flags & 8) != 0) {
                            this.readDataDescr();
                        }

                        if (this.inf.getTotalIn() != this.csize || this.inf.getTotalOut() != this.size) {
                            throw new ZipException("size mismatch: " + this.csize + ";" + this.size + " <-> " + this.inf.getTotalIn() + ";" + this.inf.getTotalOut());
                        }

                        this.inf.reset();
                        var4 = true;
                    }
            }

            if (var3 > 0) {
                this.crc.update(var1, var2, var3);
            }

            if (var4) {
                if ((this.crc.getValue() & 4294967295L) != this.entry.getCrc()) {
                    throw new ZipException("CRC mismatch");
                }

                this.crc.reset();
                this.entry = null;
            }

            return var3;
        }
    }

    public void close() throws IOException {
        super.close();
        this.crc = null;
        this.entry = null;
    }

    protected ZipEntry createZipEntry(String var1) {
        return new ZipEntry(var1);
    }
}
