//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.ele.core.util.cmy;

import cn.ele.core.Exception.CMyException;
import cn.ele.core.message.I18NMessage;
import cn.ele.core.util.cmy.zip.ZipEntry;
import cn.ele.core.util.cmy.zip.ZipFile;
import cn.ele.core.util.cmy.zip.ZipInputStream;

import java.io.*;
import java.util.*;

public class CMyUnzip {
    public static boolean IS_DEBUG = false;
    private String zipFileName = null;
    private Hashtable hZipItems = null;
    private String encoding = null;
    private Set m_hStrictFileSet = new HashSet(8);

    public CMyUnzip() {
    }

    public String getEncoding() {
        return this.encoding;
    }

    public void setEncoding(String var1) {
        this.encoding = var1;
    }

    private Hashtable getMyZipItems() throws CMyException {
        if (this.hZipItems == null) {
            this.extractZipItems();
        }

        return this.hZipItems;
    }

    private void extractZipItems() throws CMyException {
        if (this.zipFileName == null) {
            throw new CMyException(10, I18NMessage.get(CMyUnzip.class, "CMyUnzip.label1", "zip文件尚未设置（CMyUnzip.getItems）"));
        } else {
            this.hZipItems = new Hashtable();
            ZipFile var1 = null;

            try {
                var1 = new ZipFile(this.zipFileName, this.encoding);
                Enumeration var2 = var1.entries();

                while(var2.hasMoreElements()) {
                    ZipEntry var3 = (ZipEntry)var2.nextElement();
                    this.hZipItems.put(var3.getName().trim().toLowerCase(), var3);
                }
            } catch (FileNotFoundException var13) {
                throw new CMyException(55, I18NMessage.get(CMyUnzip.class, "CMyUnzip.label2", "zip文件(") + this.zipFileName + I18NMessage.get(CMyUnzip.class, "CMyUnzip.label3", ")没有找到（CMyUnzip.getItems）"), var13);
            } catch (Exception var14) {
                throw new CMyException(50, I18NMessage.get(CMyUnzip.class, "CMyUnzip.label4", "提取zip文件信息失败（CMyUnzip.getItems）"), var14);
            } finally {
                if (var1 != null) {
                    try {
                        var1.close();
                    } catch (Exception var12) {
                        ;
                    }
                }

            }

        }
    }

    public void setZipFile(String var1) throws CMyException {
        if (var1 != null && var1.length() != 0) {
            this.zipFileName = var1;
            if (this.hZipItems != null) {
                this.hZipItems.clear();
                this.hZipItems = null;
            }

        } else {
            throw new CMyException(10, I18NMessage.get(CMyUnzip.class, "CMyUnzip.label5", "指定zip文件名无效（CMyUnzip.setZipFile）"));
        }
    }

    public Enumeration getItems() throws CMyException {
        return this.getMyZipItems().elements();
    }

    public ZipEntry getItem(String var1) throws CMyException {
        if (var1 == null) {
            return null;
        } else {
            Hashtable var2 = this.getMyZipItems();
            return var2 == null ? null : (ZipEntry)var2.get(var1.toLowerCase());
        }
    }

    public void unzip(String var1, boolean var2) throws CMyException {
        if (this.zipFileName == null) {
            throw new CMyException(10, I18NMessage.get(CMyUnzip.class, "CMyUnzip.label6", "zip文件尚未设置（CMyUnzip.unzip）"));
        } else {
            if (!CMyFile.fileExists(var1)) {
                if (!var2) {
                    throw new CMyException(55, I18NMessage.get(CMyUnzip.class, "CMyUnzip.label7", "目标目录") + var1 + I18NMessage.get(CMyUnzip.class, "CMyUnzip.label8", "不存在（CMyUnzip.unzip）"));
                }

                CMyFile.makeDir(var1, true);
            }

            FileInputStream var3 = null;
            BufferedInputStream var4 = null;
            ZipInputStream var5 = null;

            try {
                var3 = new FileInputStream(this.zipFileName);
                var4 = new BufferedInputStream(var3);
                var5 = new ZipInputStream(var4);
                ZipEntry var6 = null;

                while(true) {
                    String var7;
                    do {
                        do {
                            if ((var6 = var5.getNextEntry(this.encoding)) == null) {
                                return;
                            }

                            if (IS_DEBUG) {
                                System.out.println("[unzip] name=" + var6.getName() + "," + " size=" + var6.getSize());
                            }
                        } while(var6.isDirectory());

                        var7 = var6.getName();
                    } while(!this.isStrictFile(var7));

                    if (File.separatorChar != '/') {
                        var7 = CMyString.replaceStr(var7, "/", File.separator);
                    }

                    var7 = var1 + var7;
                    if (IS_DEBUG) {
                        System.out.println("==>Dest=" + var7);
                    }

                    String var8 = CMyFile.extractFilePath(var7);
                    if (!CMyFile.pathExists(var8)) {
                        CMyFile.makeDir(var8, true);
                    }

                    FileOutputStream var9 = null;

                    try {
                        byte[] var10 = new byte[1024];
                        boolean var11 = false;
                        var9 = new FileOutputStream(var7);

                        int var50;
                        while((var50 = var5.read(var10, 0, var10.length)) != -1) {
                            var9.write(var10, 0, var50);
                        }
                    } catch (Exception var45) {
                        throw new CMyException(50, I18NMessage.get(CMyUnzip.class, "CMyUnzip.label9", "读取文件数据流失败（CMyUnzip.unzip）"), var45);
                    } finally {
                        if (var9 != null) {
                            try {
                                var9.close();
                            } catch (Exception var44) {
                                ;
                            }
                        }

                    }
                }
            } catch (FileNotFoundException var47) {
                throw new CMyException(55, I18NMessage.get(CMyUnzip.class, "CMyUnzip.label10", "压缩文件") + this.zipFileName + I18NMessage.get(CMyUnzip.class, "CMyUnzip.label11", "没有找到（CMyUnzip.unzip）"), var47);
            } catch (Exception var48) {
                throw new CMyException(1, I18NMessage.get(CMyUnzip.class, "CMyUnzip.label12", "文件解压缩失败，可能是字符编码集不匹配（CMyUnzip.unzip）"), var48);
            } finally {
                this.m_hStrictFileSet.clear();
                if (var5 != null) {
                    try {
                        var5.close();
                    } catch (Exception var43) {
                        ;
                    }
                }

                if (var4 != null) {
                    try {
                        var4.close();
                    } catch (Exception var42) {
                        ;
                    }
                }

                if (var3 != null) {
                    try {
                        var3.close();
                    } catch (Exception var41) {
                        ;
                    }
                }

            }
        }
    }

    public static void unzip(String var0, String var1, boolean var2) throws CMyException {
        CMyUnzip var3 = new CMyUnzip();
        var3.setZipFile(var0);
        var3.unzip(var1, var2);
    }

    public void addStrictFile(String var1) {
        if (!CMyString.isEmpty(var1)) {
            var1 = var1.toLowerCase();
            this.m_hStrictFileSet.addAll(Arrays.asList(CMyString.split(var1, ",")));
        }
    }

    private boolean isStrictFile(String var1) {
        if (this.m_hStrictFileSet.isEmpty()) {
            return true;
        } else {
            String var2 = CMyFile.extractFileExt(var1);
            return this.m_hStrictFileSet.contains(var2.toLowerCase());
        }
    }

}
