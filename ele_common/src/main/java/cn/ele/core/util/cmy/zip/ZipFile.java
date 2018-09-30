//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.ele.core.util.cmy.zip;

import java.io.*;
import java.util.Enumeration;
import java.util.NoSuchElementException;

public class ZipFile implements ZipConstants {
    public static final int OPEN_READ = 1;
    public static final int OPEN_DELETE = 2;
    private String name;
    RandomAccessFile raf;
    ZipEntry[] entries;
    private String encoding = null;

    public String getEncoding() {
        return this.encoding;
    }

    public void setEncoding(String var1) {
        this.encoding = var1;
    }

    public ZipFile(String var1) throws ZipException, IOException {
        this.raf = new RandomAccessFile(var1, "r");
        this.name = var1;
        this.readEntries();
    }

    public ZipFile(String var1, String var2) throws ZipException, IOException {
        this.encoding = var2;
        this.raf = new RandomAccessFile(var1, "r");
        this.name = var1;
        this.readEntries();
    }

    public ZipFile(File var1) throws ZipException, IOException {
        this.raf = new RandomAccessFile(var1, "r");
        this.name = var1.getName();
        this.readEntries();
    }

    public ZipFile(File var1, int var2) throws ZipException, IOException {
        if ((var2 & 2) != 0) {
            throw new IllegalArgumentException("OPEN_DELETE mode not supported yet in java.util.zip.ZipFile");
        } else {
            this.raf = new RandomAccessFile(var1, "r");
            this.name = var1.getName();
            this.readEntries();
        }
    }

    private final int readLeShort() throws IOException {
        int var1 = this.raf.readUnsignedByte() | this.raf.readUnsignedByte() << 8;
        return var1;
    }

    private final int readLeInt() throws IOException {
        return this.readLeShort() | this.readLeShort() << 16;
    }

    private void readEntries() throws ZipException, IOException {
        long var1 = this.raf.length() - 22L;

        while(var1 >= 0L) {
            this.raf.seek(var1--);
            if (this.readLeInt() == 101010256) {
                if (this.raf.skipBytes(6) != 6) {
                    throw new EOFException();
                }

                int var3 = this.readLeShort();
                if (this.raf.skipBytes(4) != 4) {
                    throw new EOFException();
                }

                int var4 = this.readLeInt();
                this.entries = new ZipEntry[var3];
                this.raf.seek((long)var4);

                for(int var5 = 0; var5 < var3; ++var5) {
                    if (this.readLeInt() != 33639248) {
                        throw new ZipException("Wrong Central Directory signature");
                    }

                    if (this.raf.skipBytes(6) != 6) {
                        throw new EOFException();
                    }

                    int var6 = this.readLeShort();
                    int var7 = this.readLeInt();
                    int var8 = this.readLeInt();
                    int var9 = this.readLeInt();
                    int var10 = this.readLeInt();
                    int var11 = this.readLeShort();
                    int var12 = this.readLeShort();
                    int var13 = this.readLeShort();
                    if (this.raf.skipBytes(8) != 8) {
                        throw new EOFException();
                    }

                    int var14 = this.readLeInt();
                    byte[] var15 = new byte[Math.max(var11, var13)];
                    this.raf.readFully(var15, 0, var11);
                    String var16 = this.encoding == null ? new String(var15, 0, var11) : new String(var15, 0, var11, this.encoding);
                    ZipEntry var17 = new ZipEntry(var16);
                    var17.setMethod(var6);
                    var17.setCrc((long)var8 & 4294967295L);
                    var17.setSize((long)var10 & 4294967295L);
                    var17.setCompressedSize((long)var9 & 4294967295L);
                    var17.setDOSTime(var7);
                    if (var12 > 0) {
                        byte[] var18 = new byte[var12];
                        this.raf.readFully(var18);
                        var17.setExtra(var18);
                    }

                    if (var13 > 0) {
                        this.raf.readFully(var15, 0, var13);
                        var17.setComment(this.encoding == null ? new String(var15, 0, var13) : new String(var15, 0, var13, this.encoding));
                    }

                    var17.zipFileIndex = var5;
                    var17.offset = var14;
                    this.entries[var5] = var17;
                }

                return;
            }
        }

        throw new ZipException("central directory not found, probably not a zip file");
    }

    public void close() throws IOException {
        this.entries = null;
        RandomAccessFile var1 = this.raf;
        synchronized(this.raf) {
            this.raf.close();
        }
    }

    public Enumeration entries() {
        if (this.entries == null) {
            throw new IllegalStateException("ZipFile has closed");
        } else {
            return new ZipFile.ZipEntryEnumeration(this.entries);
        }
    }

    private int getEntryIndex(String var1) {
        for(int var2 = 0; var2 < this.entries.length; ++var2) {
            if (var1.equals(this.entries[var2].getName())) {
                return var2;
            }
        }

        return -1;
    }

    public ZipEntry getEntry(String var1) {
        if (this.entries == null) {
            throw new IllegalStateException("ZipFile has closed");
        } else {
            int var2 = this.getEntryIndex(var1);
            return var2 >= 0 ? (ZipEntry)this.entries[var2].clone() : null;
        }
    }

    private long checkLocalHeader(ZipEntry var1) throws IOException {
        RandomAccessFile var2 = this.raf;
        synchronized(this.raf) {
            this.raf.seek((long)var1.offset);
            if (this.readLeInt() != 67324752) {
                throw new ZipException("Wrong Local header signature");
            } else if (this.raf.skipBytes(4) != 4) {
                throw new EOFException();
            } else if (var1.getMethod() != this.readLeShort()) {
                throw new ZipException("Compression method mismatch");
            } else if (this.raf.skipBytes(16) != 16) {
                throw new EOFException();
            } else {
                int var3 = var1.getName().getBytes().length;
                if (var3 != this.readLeShort()) {
                    throw new ZipException("file name length mismatch");
                } else {
                    int var4 = var3 + this.readLeShort();
                    return (long)(var1.offset + 30 + var4);
                }
            }
        }
    }

    public InputStream getInputStream(ZipEntry var1) throws IOException {
        if (this.entries == null) {
            throw new IllegalStateException("ZipFile has closed");
        } else {
            int var2 = var1.zipFileIndex;
            if (var2 < 0 || var2 >= this.entries.length || this.entries[var2].getName() != var1.getName()) {
                var2 = this.getEntryIndex(var1.getName());
                if (var2 < 0) {
                    throw new NoSuchElementException();
                }
            }

            long var3 = this.checkLocalHeader(this.entries[var2]);
            int var5 = this.entries[var2].getMethod();
            ZipFile.PartialInputStream var6 = new ZipFile.PartialInputStream(this.raf, var3, this.entries[var2].getCompressedSize());
            switch(var5) {
                case 0:
                    return var6;
                case 8:
                    return new InflaterInputStream(var6, new Inflater(true));
                default:
                    throw new ZipException("Unknown compression method " + var5);
            }
        }
    }

    public String getName() {
        return this.name;
    }

    public int size() {
        try {
            return this.entries.length;
        } catch (NullPointerException var2) {
            throw new IllegalStateException("ZipFile has closed");
        }
    }

    private static class PartialInputStream extends InputStream {
        RandomAccessFile raf;
        long filepos;
        long end;

        public PartialInputStream(RandomAccessFile var1, long var2, long var4) {
            this.raf = var1;
            this.filepos = var2;
            this.end = var2 + var4;
        }

        public int available() {
            long var1 = this.end - this.filepos;
            return var1 > 2147483647L ? 2147483647 : (int)var1;
        }

        public int read() throws IOException {
            if (this.filepos == this.end) {
                return -1;
            } else {
                RandomAccessFile var1 = this.raf;
                synchronized(this.raf) {
                    this.raf.seek((long)(this.filepos++));
                    return this.raf.read();
                }
            }
        }

        public int read(byte[] var1, int var2, int var3) throws IOException {
            if ((long)var3 > this.end - this.filepos) {
                var3 = (int)(this.end - this.filepos);
                if (var3 == 0) {
                    return -1;
                }
            }

            RandomAccessFile var4 = this.raf;
            synchronized(this.raf) {
                this.raf.seek(this.filepos);
                int var5 = this.raf.read(var1, var2, var3);
                if (var5 > 0) {
                    this.filepos += (long)var3;
                }

                return var5;
            }
        }

        public long skip(long var1) {
            if (var1 < 0L) {
                throw new IllegalArgumentException();
            } else {
                if (var1 > this.end - this.filepos) {
                    var1 = this.end - this.filepos;
                }

                this.filepos += var1;
                return var1;
            }
        }
    }

    private static class ZipEntryEnumeration implements Enumeration {
        ZipEntry[] array;
        int ptr = 0;

        public ZipEntryEnumeration(ZipEntry[] var1) {
            this.array = var1;
        }

        public boolean hasMoreElements() {
            return this.ptr < this.array.length;
        }

        public Object nextElement() {
            try {
                return this.array[this.ptr++].clone();
            } catch (ArrayIndexOutOfBoundsException var2) {
                throw new NoSuchElementException();
            }
        }
    }
}
