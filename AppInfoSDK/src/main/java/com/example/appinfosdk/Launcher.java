package com.example.appinfosdk;

import androidx.core.content.pm.PackageInfoCompat;

import android.app.ProgressDialog;
import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Launcher {
    private Context mContext;
    private ProgressDialog loadingDialog;

    public Launcher(Context mContext) {
        this.mContext = mContext;
    }

    public ArrayList<PackageInfo> getPackages() {
        ArrayList<PackageInfo> apps = getInstalledApps(false); /* false = no system packages */
        final int max = apps.size();
        for (int i=0; i<max; i++) {
            apps.get(i).prettyPrint();
        }

        Collections.sort(apps, (lhs, rhs) -> lhs.getAppName().compareTo(rhs.getAppName()));
        return apps;
    }

    public void showDialog()
    {
        loadingDialog = new ProgressDialog(mContext); // this = YourActivity
        loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loadingDialog.setTitle("Loading");
        loadingDialog.setMessage("Loading. Please wait...");
        loadingDialog.setIndeterminate(true);
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.show();
    }

    public void dismissDialog()
    {
        if (loadingDialog!=null && loadingDialog.isShowing())
        {
            loadingDialog.dismiss();
        }

    }


    private ArrayList<PackageInfo> getInstalledApps(boolean getSysPackages) {
        ArrayList<PackageInfo> res = new ArrayList<PackageInfo>();
        List<android.content.pm.PackageInfo> packs = mContext.getPackageManager().getInstalledPackages(0);
        for(int i=0;i<packs.size();i++) {
            android.content.pm.PackageInfo p = packs.get(i);
            if ((!getSysPackages) && (p.versionName == null)) {
                continue ;
            }
            PackageInfo newInfo = new PackageInfo();
            newInfo.appname = p.applicationInfo.loadLabel(mContext.getPackageManager()).toString();
            newInfo.pname = p.packageName;
            newInfo.versionName = p.versionName;
            //newInfo.versionCode = p.versionCode();
            // NOTE : versionCode() is deprecated in API level 28 now so using getLongVersionCode()
            newInfo.versionCode = PackageInfoCompat.getLongVersionCode(p);
            newInfo.icon = p.applicationInfo.loadIcon(mContext.getPackageManager());
            res.add(newInfo);
        }
        return res;
    }
}