package cn.ele.core.Exception;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import org.apache.log4j.Logger;

public class WCMException extends CMyException {
    private static final long serialVersionUID = 8819172007753214059L;
    private static final Logger logger = Logger.getLogger(WCMException.class.getName());

    public WCMException(int var1) {
        super(var1);
    }

    public WCMException(int var1, String var2) {
        super(var1, var2);
    }

    public WCMException(String var1) {
        super(var1);
        this.errNo = 1008;
    }

    public WCMException(int var1, String var2, Throwable var3) {
        super(var1, var2, var3);
    }

    public WCMException(String var1, Throwable var2) {
        super(var1, var2);
        this.errNo = 1100;
    }

    public static void catchException(String var0, Exception var1, boolean var2) throws WCMException {
        logger.error(var0, var1);
        if (var2) {
            throw new WCMException(1000, var0, var1);
        }
    }
}
