//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.ele.core.Exception;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ExceptionMessages {
    private static final String BUNDLE_NAME = "exception_messages";
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("exception_messages");

    private ExceptionMessages() {
    }

    public static String getString(int var0) {
        String var1 = "ExNum." + String.valueOf(var0);

        try {
            return RESOURCE_BUNDLE.getString(var1);
        } catch (MissingResourceException var3) {
            return '!' + var1 + '!';
        }
    }
}
