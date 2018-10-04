//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.ele.core.message;

import cn.ele.core.util.cmy.CMyString;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public final class I18NMessage {
    private static final Logger LOG = Logger.getLogger(I18NMessage.class);
    private static final Map MESSAGES = new HashMap(500, 0.2F);

    public I18NMessage() {
    }

    public static String makeObjectNotFound(String[] var0) {
        return CMyString.format(get(I18NMessage.class, "I18NMessage.label1", "没有找到指定的{1}[ID={0}]"), var0);
    }

    public static String get(Class var0, String var1, String var2) {
        String var3 = var0.getPackage().getName();
        String var4 = (String)MESSAGES.get(var3 + "." + var1);
        return var4 != null ? var4 : var2;
    }

    static void load(String var0, String var1) {
        LOG.info("load i18n message begin...");
        long var2 = System.currentTimeMillis();
        File var4 = new File(var0);
        if (var4.exists() && var4.isDirectory()) {
            String var5 = "i18nmessage.properties";
            final HashSet var7 = new HashSet(500, 0.2F);
            final String finalVar = var5;
            var4.listFiles(new FileFilter() {
                public boolean accept(File var1) {
                    if (var1.isDirectory()) {
                        var1.listFiles(this);
                    } else if (finalVar.equals(var1.getName())) {
                        var7.add(var1.getAbsolutePath());
                    }

                    return false;
                }
            });
            Iterator var8 = var7.iterator();

            while(var8.hasNext()) {
                var5 = (String)var8.next();
                read(var5);
            }

            long var9 = System.currentTimeMillis();
            LOG.info("i18n message loaded.time used(ms):" + (var9 - var2));
        } else {
            throw new IllegalArgumentException("No such directory:" + var0);
        }
    }

    private static void read(String var0) {
        FileInputStream var1 = null;
        InputStreamReader var2 = null;
        BufferedReader var3 = null;

        try {
            var1 = new FileInputStream(var0);
            var2 = new InputStreamReader(var1, "GBK");
            var3 = new BufferedReader(var2);
            String var4 = var3.readLine();
            String var5 = var4.substring(1);

            for(var4 = var3.readLine(); var4 != null; var4 = var3.readLine()) {
                int var6 = var4.indexOf(61);
                MESSAGES.put(var5 + "." + var4.substring(0, var6), var4.substring(var6 + 1).replaceAll("\\\\n", "\n"));
            }
        } catch (Exception var24) {
            ;
        } finally {
            if (var1 != null) {
                try {
                    var1.close();
                } catch (Exception var23) {
                    ;
                }
            }

            if (var2 != null) {
                try {
                    var2.close();
                } catch (Exception var22) {
                    ;
                }
            }

            if (var3 != null) {
                try {
                    var3.close();
                } catch (Exception var21) {
                    ;
                }
            }

        }

    }
}
