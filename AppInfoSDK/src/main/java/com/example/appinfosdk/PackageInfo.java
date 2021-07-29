package com.example.appinfosdk;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class PackageInfo implements Parcelable {
    private static final String TAG = "PInfo";
    String appname = "";
    String pname = "";
    String versionName = "";
    long versionCode = 0;
    Drawable icon;

    public PackageInfo() {
    }

    public PackageInfo(String appname, String pname, String versionName, long versionCode, Drawable icon) {
        this.appname = appname;
        this.pname = pname;
        this.versionName = versionName;
        this.versionCode = versionCode;
        this.icon = icon;
    }

    protected PackageInfo(Parcel in) {
        appname = in.readString();
        pname = in.readString();
        versionName = in.readString();
        versionCode = in.readLong();
    }

    public static final Creator<PackageInfo> CREATOR = new Creator<PackageInfo>() {
        @Override
        public PackageInfo createFromParcel(Parcel in) {
            return new PackageInfo(in);
        }

        @Override
        public PackageInfo[] newArray(int size) {
            return new PackageInfo[size];
        }
    };

    void prettyPrint() {
        Log.v(TAG, appname + "\t" + pname + "\t" + versionName + "\t" + versionCode);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(appname);
        dest.writeString(pname);
        dest.writeString(versionName);
        dest.writeLong(versionCode);
    }

    public String getAppName() {
        return appname;
    }

    public void setAppName(String appname) {
        this.appname = appname;
    }

    public String getPName() {
        return pname;
    }

    public void setPName(String pname) {
        this.pname = pname;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public long getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(long versionCode) {
        this.versionCode = versionCode;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}