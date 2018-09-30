//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.ele.core.util.cmy;

import cn.ele.core.Exception.CMyException;
import cn.ele.core.message.I18NMessage;
import cn.ele.core.util.cmy.zip.ZipEntry;
import cn.ele.core.util.cmy.zip.ZipOutputStream;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class CMyZip {
    public static boolean IS_DEBUG = false;
    private String m_sPath = "";
    private String m_sEntryPath = "";
    private String m_sDstZipFileName = "";
    private ZipOutputStream m_zos = null;
    private String encoding = null;
    private boolean m_bZipWithChildDirPath = false;

    public CMyZip() {
    }

    public String getEncoding() {
        return this.encoding;
    }

    public void setEncoding(String var1) {
        this.encoding = var1;
    }

    public void setZipFileName(String var1) throws CMyException {
        if (this.m_zos != null) {
            throw new CMyException(1, I18NMessage.get(CMyZip.class, "CMyZip.label1", "非法操作：未完成上次压缩操作(CMyZip.setZipFileName)"));
        } else {
            this.m_sDstZipFileName = var1;
        }
    }

    public void addToZip(String var1) throws CMyException {
        this.addToZip(var1, true);
    }

    public void addToZip(String var1, String var2) throws CMyException {
        File var3 = new File(var1);
        if (var3.isDirectory()) {
            throw new CMyException("Accept file only.");
        } else {
            if (this.m_zos == null) {
                try {
                    this.m_zos = new ZipOutputStream(new FileOutputStream(this.m_sDstZipFileName));
                    this.m_zos.setEncoding(this.encoding);
                } catch (Exception var12) {
                    throw new CMyException(50, "创建zip输出流失败(CMyZip.addToZip)", var12);
                }

                this.m_zos.setMethod(8);
            }

            this.m_sPath = var1.replace(File.separatorChar, '/');
            this.m_sEntryPath = "";
            int var4 = this.m_sPath.lastIndexOf(47);
            if (var4 > 0) {
                this.m_sPath = this.m_sPath.substring(0, var4);
                var4 = this.m_sPath.lastIndexOf(47);
                if (var4 > 0) {
                    this.m_sEntryPath = this.m_sPath.substring(var4 + 1) + "/";
                } else {
                    this.m_sEntryPath = this.m_sPath + "/";
                }

                this.m_sPath = this.m_sPath + "/";
            }

            String var5 = var3.getPath();
            var5 = this.makeEntryName(var5, true);
            var5 = CMyString.replaceStr(var5, CMyFile.extractMainFileName(var3.getName()), var2);
            if (IS_DEBUG) {
                System.out.println("add to zip: " + var5);
            }

            try {
                if (!"".equals(var5) && !".".equals(var5) && !var5.equals(this.m_sPath)) {
                    long var6 = var3.length();
                    ZipEntry var8 = new ZipEntry(this.m_sEntryPath + var5);
                    var8.setMethod(8);
                    var8.setTime(var3.lastModified());
                    var8.setSize(var6);
                    this.m_zos.putNextEntry(var8);
                    byte[] var9 = new byte[1024];
                    BufferedInputStream var11 = new BufferedInputStream(new FileInputStream(var3));

                    int var10;
                    while((var10 = var11.read(var9, 0, var9.length)) != -1) {
                        this.m_zos.write(var9, 0, var10);
                    }

                    var11.close();
                    this.m_zos.closeEntry();
                }

            } catch (Exception var13) {
                throw new CMyException(1, "压缩文件或目录失败(CMyZip.addFile)", var13);
            }
        }
    }

    public void addToZip(String var1, boolean var2) throws CMyException {
        if (this.m_zos == null) {
            try {
                this.m_zos = new ZipOutputStream(new FileOutputStream(this.m_sDstZipFileName));
                this.m_zos.setEncoding(this.encoding);
            } catch (Exception var5) {
                throw new CMyException(50, I18NMessage.get(CMyZip.class, "CMyZip.label2", "创建zip输出流失败(CMyZip.addToZip)"), var5);
            }

            this.m_zos.setMethod(8);
        }

        this.m_sPath = var1.replace(File.separatorChar, '/');
        this.m_sEntryPath = "";
        int var3 = this.m_sPath.lastIndexOf(47);
        if (var3 > 0) {
            this.m_sPath = this.m_sPath.substring(0, var3);
            var3 = this.m_sPath.lastIndexOf(47);
            if (var3 > 0) {
                this.m_sEntryPath = this.m_sPath.substring(var3 + 1) + "/";
            } else {
                this.m_sEntryPath = this.m_sPath + "/";
            }

            this.m_sPath = this.m_sPath + "/";
        }

        this.addFile(new File(var1), var2);
    }

    public boolean done() throws CMyException {
        if (this.m_zos == null) {
            throw new CMyException(1, I18NMessage.get(CMyZip.class, "CMyZip.label3", "无效调用：未设定目标文件(CMyZip.done)"));
        } else {
            try {
                this.m_zos.finish();
                this.m_zos.close();
            } catch (Exception var6) {
                throw new CMyException(50, I18NMessage.get(CMyZip.class, "CMyZip.label4", "结束zip压缩时失败(CMyZip.done)"), var6);
            } finally {
                this.m_zos = null;
            }

            return true;
        }
    }

    public void zip(String var1, String var2) throws CMyException {
        this.setZipFileName(var2);
        this.addToZip(var1);
        this.done();
    }

    private void addFile(File var1, boolean var2) throws CMyException {
        String var3 = var1.getPath();
        boolean var4 = var1.isDirectory();
        if (var4 && !var3.endsWith(File.separator)) {
            var3 = var3 + File.separator;
        }

        var3 = this.makeEntryName(var3, var2);
        if (IS_DEBUG) {
            System.out.println("add to zip: " + var3);
        }

        try {
            if (!"".equals(var3) && !".".equals(var3) && !var3.equals(this.m_sPath)) {
                long var5 = var4 ? 0L : var1.length();
                ZipEntry var7 = new ZipEntry((var2 ? this.m_sEntryPath : "") + var3);
                var7.setMethod(8);
                var7.setTime(var1.lastModified());
                var7.setSize(var5);
                this.m_zos.putNextEntry(var7);
                if (!var4) {
                    byte[] var8 = new byte[1024];
                    BufferedInputStream var10 = new BufferedInputStream(new FileInputStream(var1));

                    int var9;
                    while((var9 = var10.read(var8, 0, var8.length)) != -1) {
                        this.m_zos.write(var8, 0, var9);
                    }

                    var10.close();
                }

                this.m_zos.closeEntry();
            }

            if (var4) {
                File[] var12 = var1.listFiles();
                int var6;
                if (this.m_bZipWithChildDirPath && !var2) {
                    this.m_sEntryPath = "";

                    for(var6 = 0; var6 < var12.length; ++var6) {
                        this.addFile(var12[var6], true);
                    }
                } else {
                    for(var6 = 0; var6 < var12.length; ++var6) {
                        this.addFile(var12[var6], var2);
                    }
                }
            }

        } catch (Exception var11) {
            throw new CMyException(1, I18NMessage.get(CMyZip.class, "CMyZip.label5", "压缩文件或目录失败(CMyZip.addFile)"), var11);
        }
    }

    private String makeEntryName(String var1, boolean var2) {
        if (!var2) {
            return CMyFile.extractFileName(var1);
        } else {
            String var3 = var1.replace(File.separatorChar, '/');
            String var4 = "";
            if (var3.startsWith(this.m_sPath) && this.m_sPath.length() > 0) {
                var4 = this.m_sPath;
            }

            var3 = var3.substring(var4.length());
            if (var3.startsWith("/")) {
                var3 = var3.substring(1);
            } else if (var3.startsWith("./")) {
                var3 = var3.substring(2);
            }

            return var3;
        }
    }


    public void setZipWithChildDirPath(boolean var1) {
        this.m_bZipWithChildDirPath = var1;
    }
}
