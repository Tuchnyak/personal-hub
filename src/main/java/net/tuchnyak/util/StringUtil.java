package net.tuchnyak.util;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class StringUtil implements Logging {

    public String cutIfTooLong(String initial, int maxLength) {
        if (initial == null) {
            return "";
        }

        if (initial.length() <= maxLength) {
            return initial;
        }

        return initial.substring(0, maxLength);
    }

}
