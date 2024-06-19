package android.os;

import android.annotation.NonNull;
import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// 没实际意义，仅仅是为了快速快速移植 Android 端定义的消息
public class Parcel {
    @Nullable
    public final String readString() {
        return null;
    }

    public final void writeString(@Nullable String val) {
    }

    public final void writeByteArray(@Nullable byte[] b) {
    }

    @Nullable
    public final byte[] createByteArray() {
        return null;
    }

    @Nullable
    public <T> T readParcelable(@Nullable ClassLoader loader) {
        return null;
    }

    public final void writeParcelable(@Nullable Parcelable p, int parcelableFlags) {

    }

    public final void writeInt(int val) {
    }

    public final void writeStringList(@Nullable List<String> val) {
    }

    public final int readInt() {
        return 0;
    }

    public final ArrayList<String> createStringArrayList() {
        return null;
    }

    public final long readLong() {
        return 0;
    }

    public final void writeLong(long val) {
    }

    public final void writeDouble(double val) {
    }

    public final double readDouble() {
        return 0.0;
    }

    public final void writeList(@Nullable List val) {

    }

    public final void readList(@NonNull List outVal, @Nullable ClassLoader loader) {
        return;
    }

    public final void writeByte(byte val) {
    }

    public final byte readByte() {
        return 0;
    }

    public final void readStringList(@NonNull List<String> list) {
    }

    public final void writeStringArray(@Nullable String[] val) {
    }


    @Nullable
    public final String[] createStringArray() {
        return null;
    }
}

