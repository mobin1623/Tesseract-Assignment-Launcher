package com.example.tesseract_assignment_launcher;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

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
//        // retrieve the list of installed applications
//        List<ApplicationInfo> apps = mPm.getInstalledApplications(0);
//
//        if (apps == null) {
//            apps = new ArrayList<ApplicationInfo>();
//        }
//
//        final Context context = getContext();
//
//        // create corresponding apps and load their labels
//        ArrayList<Apps> items = new ArrayList<Apps>(apps.size());
//        for (int i = 0; i < apps.size(); i++) {
//            String pkg = apps.get(i).packageName;
//
//            // only apps which are launchable
//            if (context.getPackageManager().getLaunchIntentForPackage(pkg) != null) {
//                Apps app = new Apps();
//                app.setAppName(String.valueOf(apps.get(i).loadLabel(mPm)));
//                items.add(app);
//            }
//        }
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
            Apps apps = new Apps();
            apps.setIcon(ri.loadIcon(mPm));
            apps.setAppName(String.valueOf(ri.loadLabel(mPm)));
            items.add(apps);

        }


        // sort the list
        Collections.sort(items, ALPHA_COMPARATOR);

        return items;
    }

    @Override
    public void deliverResult(ArrayList<Apps> apps) {
        if (isReset()) {
            // An async query came in while the loader is stopped.  We
            // don't need the result.
            if (apps != null) {
                onReleaseResources(apps);
            }
        }

        ArrayList<Apps> oldApps = apps;
        mInstalledApps = apps;

        if (isStarted()) {
            // If the Loader is currently started, we can immediately
            // deliver its results.
            super.deliverResult(apps);
        }

        // At this point we can release the resources associated with
        // 'oldApps' if needed; now that the new result is delivered we
        // know that it is no longer in use.
        if (oldApps != null) {
            onReleaseResources(oldApps);
        }
    }

    @Override
    protected void onStartLoading() {
        if (mInstalledApps != null) {
            // If we currently have a result available, deliver it
            // immediately.
            deliverResult(mInstalledApps);
        }

        // watch for changes in app install and uninstall operation
        if (mPackageObserver == null) {
            mPackageObserver = new PackageIntentReceiver(this);
        }

        if (takeContentChanged() || mInstalledApps == null ) {
            // If the data has changed since the last time it was loaded
            // or is not currently available, start a load.
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }

    @Override
    public void onCanceled(ArrayList<Apps> apps) {
        super.onCanceled(apps);

        // At this point we can release the resources associated with 'apps'
        // if needed.
        onReleaseResources(apps);
    }

    @Override
    protected void onReset() {
        // Ensure the loader is stopped
        onStopLoading();

        // At this point we can release the resources associated with 'apps'
        // if needed.
        if (mInstalledApps != null) {
            onReleaseResources(mInstalledApps);
            mInstalledApps = null;
        }

        // Stop monitoring for changes.
        if (mPackageObserver != null) {
            getContext().unregisterReceiver(mPackageObserver);
            mPackageObserver = null;
        }
    }

    /**
     * Helper method to do the cleanup work if needed, for example if we're
     * using Cursor, then we should be closing it here
     *
     * @param apps
     */
    protected void onReleaseResources(ArrayList<Apps> apps) {
        // do nothing
    }


    /**
     * Perform alphabetical comparison of application entry objects.
     */
    public static final Comparator<Apps> ALPHA_COMPARATOR = new Comparator<Apps>() {
        private final Collator sCollator = Collator.getInstance();
        @Override
        public int compare(Apps object1, Apps object2) {
            return sCollator.compare(object1.getAppName(), object2.getAppName());
        }
    };
}
