
package cn.ele.core.Exception;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

public class CMyException extends Exception {
    protected int errNo = 0;
    protected Throwable rootCause = null;

    public CMyException(int var1) {
        this.errNo = var1;
    }

    public CMyException(int var1, String var2) {
        super(var2);
        this.errNo = var1;
    }

    public CMyException(String var1) {
        super(var1);
    }

    public CMyException(int var1, String var2, Throwable var3) {
        super(var2);
        this.errNo = var1;
        this.rootCause = var3;
    }

    public CMyException(String var1, Throwable var2) {
        super(var1);
        this.rootCause = var2;
    }

    public int getErrNo() {
        return this.errNo;
    }

    public Throwable getRootCause() {
        return this.rootCause;
    }

    public String getErrNoMsg() {
        return ExceptionNumber.getErrNoMsg(this.errNo);
    }

    public String getMyMessage() {
        return super.getMessage();
    }

    public String toString() {
        String var1 = "[ERR-" + this.errNo + "] " + this.getMyMessage();
        return var1;
    }

    public String getMessage() {
        String var1 = this.toString();
        if (this.rootCause != null) {
            var1 = var1 + "\r\n<-- " + this.rootCause.toString();
        }

        return var1;
    }

    public String getLocalizedMessage() {
        return this.getMessage();
    }

    public void printStackTrace(PrintStream var1) {
        if (this.rootCause == null) {
            super.printStackTrace(var1);
        } else {
            Throwable var2 = this.rootCause;
            synchronized(var1) {
                var1.println(this.toString());
                Throwable var4 = null;

                while(var2 instanceof CMyException) {
                    var1.println("<-- " + var2.toString());
                    var4 = var2;
                    var2 = ((CMyException)var2).getRootCause();
                    if (var2 == null) {
                        var4.printStackTrace(var1);
                        break;
                    }
                }

                if (var2 != null) {
                    var1.print("<-- ");
                    var2.printStackTrace(var1);
                }
            }
        }

    }

    public void printStackTrace(PrintWriter var1) {
        if (this.rootCause == null) {
            super.printStackTrace(var1);
        } else {
            Throwable var2 = this.rootCause;
            synchronized(var1) {
                var1.println(this.toString());
                Throwable var4 = null;

                while(var2 instanceof CMyException) {
                    var1.print("<-- ");
                    var4 = var2;
                    var2 = ((CMyException)var2).getRootCause();
                    if (var2 == null) {
                        var4.printStackTrace(var1);
                        break;
                    }

                    var1.println(var4.toString());
                }

                if (var2 != null) {
                    var1.print("<-- ");
                    var2.printStackTrace(var1);
                }
            }
        }

    }

    public String getStackTraceText() {
        return getStackTraceText(this);
    }

    public static String getStackTraceText(Throwable var0) {
        StringWriter var1 = null;
        PrintWriter var2 = null;

        String var4;
        try {
            var1 = new StringWriter();
            var2 = new PrintWriter(var1);
            var0.printStackTrace(var2);
            var2.flush();
            String var3 = var1.toString();
            return var3;
        } catch (Exception var18) {
            var4 = var0.getMessage();
        } finally {
            if (var1 != null) {
                try {
                    var1.close();
                } catch (Exception var17) {
                    ;
                }
            }

            if (var2 != null) {
                try {
                    var2.close();
                } catch (Exception var16) {
                    ;
                }
            }

        }

        return var4;
    }

    public static final void main(String[] var0) {
        CMyException var1 = new CMyException(1, "my exception 0");
        CMyException var2 = new CMyException(1, "my exception 1", var1);
        CMyException var3 = new CMyException(10, "my exception 2", var2);
        var3.printStackTrace(System.out);
        System.out.println("-------------------");
        System.out.println(var3.getMessage());
        System.out.println("-------------------");
        System.out.println(var3.getStackTraceText());

        try {
            byte var4 = 0;
            int var5 = 1 / var4;
            System.out.println(var5);
        } catch (Exception var6) {
            System.out.println(getStackTraceText(var6));
        }

    }
}
