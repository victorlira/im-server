package android.text;

import com.sun.istack.internal.Nullable;

public class TextUtils {
    public static boolean isEmpty(@Nullable CharSequence str) {
        return str == null || str.length() == 0;
    }
}
