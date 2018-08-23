//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.ele.core.util.cmy;

import java.io.File;
import java.io.FilenameFilter;

public class CMyFilenameFilter implements FilenameFilter {
    private String sExt;

    public CMyFilenameFilter(String var1) {
        this.sExt = var1;
    }

    public boolean accept(File var1, String var2) {
        return var2.endsWith(this.sExt);
    }
}
