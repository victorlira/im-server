package android.os;

import android.annotation.NonNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// 没实际意义，仅仅是为了快速快速移植 Android 端定义的消息
public interface Parcelable {
    public @interface WriteFlags {
    }

    public static final int PARCELABLE_WRITE_RETURN_VALUE = 0x0001;
    public static final int PARCELABLE_ELIDE_DUPLICATES = 0x0002;

    public @interface ContentsFlags {
    }

    public @interface Stability {
    }

    public static final int PARCELABLE_STABILITY_LOCAL = 0x0000;
    public static final int PARCELABLE_STABILITY_VINTF = 0x0001;
    public static final int CONTENTS_FILE_DESCRIPTOR = 0x0001;

    public @ContentsFlags int describeContents();

    default @Stability int getStability() {
        return PARCELABLE_STABILITY_LOCAL;
    }

    public void writeToParcel(@NonNull Parcel dest, @WriteFlags int flags);

    public interface Creator<T> {
        public T createFromParcel(Parcel source);

        public T[] newArray(int size);
    }

    public interface ClassLoaderCreator<T> extends Creator<T> {
        public T createFromParcel(Parcel source, ClassLoader loader);
    }
}
