//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.ele.core.util.cmy.zip;

public interface Checksum {
    long getValue();

    void reset();

    void update(int var1);

    void update(byte[] var1, int var2, int var3);
}
