package com.example.tesseract_assignment_launcher.Model;


import android.graphics.drawable.Drawable;



public class Apps {

    String appName;
    String packageName;
    String mainActivityClassName;
    String versionCode;
    String versionName;
    Drawable icon;



    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getMainActivityClassName() {
        return mainActivityClassName;
    }

    public void setMainActivityClassName(String mainActivityClassName) {
        this.mainActivityClassName = mainActivityClassName;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}
