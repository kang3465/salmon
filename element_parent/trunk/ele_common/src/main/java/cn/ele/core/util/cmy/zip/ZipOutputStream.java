//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.ele.core.util.cmy.zip;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Vector;
import java.util.zip.CRC32;
import java.util.zip.ZipException;

public class ZipOutputStream extends DeflaterOutputStream implements ZipConstants {
    private Vector entries = new Vector();
    private CRC32 crc = new CRC32();
    private ZipEntry curEntry = null;
    private int curMethod;
    private int size;
    private int offset = 0;
    private byte[] zipComment = new byte[0];
    private int defaultMethod = 8;
    private String encoding = null;
    private static final int ZIP_STORED_VERSION = 10;
    private static final int ZIP_DEFLATED_VERSION = 20;
    public static final int STORED = 0;
    public static final int DEFLATED = 8;

    public String getEncoding() {
        return this.encoding;
    }

    public void setEncoding(String var1) {
        this.encoding = var1;
    }

    public ZipOutputStream(OutputStream var1) {
        super(var1, new Deflater(-1, true));
    }

    public void setComment(String var1) {
        byte[] var2;
        try {
            var2 = this.encoding == null ? var1.getBytes() : var1.getBytes(this.encoding);
        } catch (Exception var4) {
            throw new InternalError("failed to get bytes");
        }

        if (var2.length > 65535) {
            throw new IllegalArgumentException("Comment too long.");
        } else {
            this.zipComment = var2;
        }
    }

    public void setMethod(int var1) {
        if (var1 != 0 && var1 != 8) {
            throw new IllegalArgumentException("Method not supported.");
        } else {
            this.defaultMethod = var1;
        }
    }

    public void setLevel(int var1) {
        this.def.setLevel(var1);
    }

    private final void writeLeShort(int var1) throws IOException {
        this.out.write(var1 & 255);
        this.out.write(var1 >> 8 & 255);
    }

    private final void writeLeInt(int var1) throws IOException {
        this.writeLeShort(var1);
        this.writeLeShort(var1 >> 16);
    }

    public void putNextEntry(ZipEntry var1) throws IOException {
        if (this.entries == null) {
            throw new IllegalStateException("ZipOutputStream was finished");
        } else {
            int var2 = var1.getMethod();
            int var3 = 0;
            if (var2 == -1) {
                var2 = this.defaultMethod;
            }

            if (var2 == 0) {
                if (var1.getCompressedSize() >= 0L) {
                    if (var1.getSize() < 0L) {
                        var1.setSize(var1.getCompressedSize());
                    } else if (var1.getSize() != var1.getCompressedSize()) {
                        throw new ZipException("Method STORED, but compressed size != size");
                    }
                } else {
                    var1.setCompressedSize(var1.getSize());
                }

                if (var1.getSize() < 0L) {
                    throw new ZipException("Method STORED, but size not set");
                }

                if (var1.getCrc() < 0L) {
                    throw new ZipException("Method STORED, but crc not set");
                }
            } else if (var2 == 8 && (var1.getCompressedSize() < 0L || var1.getSize() < 0L || var1.getCrc() < 0L)) {
                var3 |= 8;
            }

            if (this.curEntry != null) {
                this.closeEntry();
            }

            if (var1.getTime() < 0L) {
                var1.setTime(System.currentTimeMillis());
            }

            var1.flags = var3;
            var1.offset = this.offset;
            var1.setMethod(var2);
            this.curMethod = var2;
            this.writeLeInt(67324752);
            this.writeLeShort(var2 == 0 ? 10 : 20);
            this.writeLeShort(var3);
            this.writeLeShort(var2);
            this.writeLeInt(var1.getDOSTime());
            if ((var3 & 8) == 0) {
                this.writeLeInt((int)var1.getCrc());
                this.writeLeInt((int)var1.getCompressedSize());
                this.writeLeInt((int)var1.getSize());
            } else {
                this.writeLeInt(0);
                this.writeLeInt(0);
                this.writeLeInt(0);
            }

            String var4 = var1.getName();
            byte[] var5 = this.encoding == null ? var4.getBytes() : var4.getBytes(this.encoding);
            if (var5.length > 65535) {
                throw new ZipException("Name too long.");
            } else {
                byte[] var6 = var1.getExtra();
                if (var6 == null) {
                    var6 = new byte[0];
                }

                this.writeLeShort(var5.length);
                this.writeLeShort(var6.length);
                this.out.write(var5);
                this.out.write(var6);
                this.offset += 30 + var5.length + var6.length;
                this.curEntry = var1;
                this.crc.reset();
                if (var2 == 8) {
                    this.def.reset();
                }

                this.size = 0;
            }
        }
    }

    public void closeEntry() throws IOException {
        if (this.curEntry == null) {
            throw new IllegalStateException("No open entry");
        } else {
            if (this.curMethod == 8) {
                super.finish();
            }

            int var1 = this.curMethod == 8 ? this.def.getTotalOut() : this.size;
            if (this.curEntry.getSize() < 0L) {
                this.curEntry.setSize((long)this.size);
            } else if (this.curEntry.getSize() != (long)this.size) {
                throw new ZipException("size was " + this.size + ", but I expected " + this.curEntry.getSize());
            }

            if (this.curEntry.getCompressedSize() < 0L) {
                this.curEntry.setCompressedSize((long)var1);
            } else if (this.curEntry.getCompressedSize() != (long)var1) {
                throw new ZipException("compressed size was " + var1 + ", but I expected " + this.curEntry.getSize());
            }

            if (this.curEntry.getCrc() < 0L) {
                this.curEntry.setCrc(this.crc.getValue());
            } else if (this.curEntry.getCrc() != this.crc.getValue()) {
                throw new ZipException("crc was " + Long.toHexString(this.crc.getValue()) + ", but I expected " + Long.toHexString(this.curEntry.getCrc()));
            }

            this.offset += var1;
            if (this.curMethod == 8 && (this.curEntry.flags & 8) != 0) {
                this.writeLeInt(134695760);
                this.writeLeInt((int)this.curEntry.getCrc());
                this.writeLeInt((int)this.curEntry.getCompressedSize());
                this.writeLeInt((int)this.curEntry.getSize());
                this.offset += 16;
            }

            this.entries.addElement(this.curEntry);
            this.curEntry = null;
        }
    }

    @Override
    public void write(byte[] var1, int var2, int var3) throws IOException {
        if (this.curEntry == null) {
            throw new IllegalStateException("No open entry.");
        } else {
            switch(this.curMethod) {
                case 0:
                    this.out.write(var1, var2, var3);
                    break;
                case 8:
                    super.write(var1, var2, var3);
            }

            this.crc.update(var1, var2, var3);
            this.size += var3;
        }
    }

    public void finish() throws IOException {
        if (this.entries != null) {
            if (this.curEntry != null) {
                this.closeEntry();
            }

            int var1 = 0;
            int var2 = 0;

            byte[] var7;
            byte[] var8;
            byte[] var10;
            for(Enumeration var3 = this.entries.elements(); var3.hasMoreElements(); var2 += 46 + var7.length + var8.length + var10.length) {
                ZipEntry var4 = (ZipEntry)var3.nextElement();
                int var5 = var4.getMethod();
                this.writeLeInt(33639248);
                this.writeLeShort(var5 == 0 ? 10 : 20);
                this.writeLeShort(var5 == 0 ? 10 : 20);
                this.writeLeShort(var4.flags);
                this.writeLeShort(var5);
                this.writeLeInt(var4.getDOSTime());
                this.writeLeInt((int)var4.getCrc());
                this.writeLeInt((int)var4.getCompressedSize());
                this.writeLeInt((int)var4.getSize());
                String var6 = var4.getName();
                var7 = this.encoding == null ? var6.getBytes() : var6.getBytes(this.encoding);
                if (var7.length > 65535) {
                    throw new ZipException("Name too long.");
                }

                var8 = var4.getExtra();
                if (var8 == null) {
                    var8 = new byte[0];
                }

                String var9 = var4.getComment();
                if (var9 != null) {
                    var10 = this.encoding == null ? var9.getBytes() : var9.getBytes(this.encoding);
                } else {
                    var10 = new byte[0];
                }

                if (var10.length > 65535) {
                    throw new ZipException("Comment too long.");
                }

                this.writeLeShort(var7.length);
                this.writeLeShort(var8.length);
                this.writeLeShort(var10.length);
                this.writeLeShort(0);
                this.writeLeShort(0);
                this.writeLeInt(0);
                this.writeLeInt(var4.offset);
                this.out.write(var7);
                this.out.write(var8);
                this.out.write(var10);
                ++var1;
            }

            this.writeLeInt(101010256);
            this.writeLeShort(0);
            this.writeLeShort(0);
            this.writeLeShort(var1);
            this.writeLeShort(var1);
            this.writeLeInt(var2);
            this.writeLeInt(this.offset);
            this.writeLeShort(this.zipComment.length);
            this.out.write(this.zipComment);
            this.out.flush();
            this.entries = null;
        }
    }
}
