package com.example.tesseract_assignment_launcher;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;

import com.example.tesseract_assignment_launcher.Model.Apps;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.loader.content.AsyncTaskLoader;


public class AppsLoader extends AsyncTaskLoader<ArrayList<Apps>> {
    ArrayList<Apps> mInstalledApps;

    final PackageManager mPm;
    PackageIntentReceiver mPackageObserver;

    public AppsLoader(Context context) {
        super(context);

        mPm = context.getPackageManager();
    }

    @Override
    public ArrayList<Apps> loadInBackground() {

         ArrayList<Apps> items = new ArrayList<>();

        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> availableActivities =
                null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            availableActivities = mPm.queryIntentActivities(intent, PackageManager.MATCH_ALL);
        }else{
            availableActivities = mPm.queryIntentActivities(intent, 0);
        }
        for (ResolveInfo ri : availableActivities){
//            Log.d("TAG","APp : " + ri.loadLabel(packageManager));

            try {
                Apps apps = new Apps();
                apps.setIcon(ri.loadIcon(mPm));
                apps.setAppName(String.valueOf(ri.loadLabel(mPm)));
                apps.setMainActivityClassName((String) ri.activityInfo.name);
                apps.setVersionCode(String.valueOf(mPm.getPackageInfo(ri.activityInfo.packageName, 0).versionCode));
                apps.setVersionName(String.valueOf(mPm.getPackageInfo(ri.activityInfo.packageName, 0).versionName));
                apps.setPackageName(ri.activityInfo.packageName);
                items.add(apps);

                Log.d("TAG","RI : " +
                        mPm.getPackageInfo(ri.activityInfo.packageName, 0));


            } catch (Exception e) {
                e.printStackTrace();
            }


        }


        // sort the list
        Collections.sort(items, ALPHA_COMPARATOR);

        return items;
    }

    @Override
    public void deliverResult(ArrayList<Apps> apps) {
        if (isReset()) {

            if (apps != null) {
                onReleaseResources(apps);
            }
        }

        ArrayList<Apps> oldApps = apps;
        mInstalledApps = apps;

        if (isStarted()) {

            super.deliverResult(apps);
        }

        if (oldApps != null) {
            onReleaseResources(oldApps);
        }
    }

    @Override
    protected void onStartLoading() {
        if (mInstalledApps != null) {

            deliverResult(mInstalledApps);
        }


        if (mPackageObserver == null) {
            mPackageObserver = new PackageIntentReceiver(this);
        }

        if (takeContentChanged() || mInstalledApps == null ) {

            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {

        cancelLoad();
    }

    @Override
    public void onCanceled(ArrayList<Apps> apps) {
        super.onCanceled(apps);


        onReleaseResources(apps);
    }

    @Override
    protected void onReset() {

        onStopLoading();


        if (mInstalledApps != null) {
            onReleaseResources(mInstalledApps);
            mInstalledApps = null;
        }

        if (mPackageObserver != null) {
            getContext().unregisterReceiver(mPackageObserver);
            mPackageObserver = null;
        }
    }


    protected void onReleaseResources(ArrayList<Apps> apps) {

    }



    public static final Comparator<Apps> ALPHA_COMPARATOR = new Comparator<Apps>() {
        private final Collator sCollator = Collator.getInstance();
        @Override
        public int compare(Apps object1, Apps object2) {
            return sCollator.compare(object1.getAppName(), object2.getAppName());
        }
    };
}
