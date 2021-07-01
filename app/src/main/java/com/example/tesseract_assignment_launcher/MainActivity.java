package com.example.tesseract_assignment_launcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;

import com.example.tesseract_assignment_launcher.Adapter.AppsAdapter;
import com.example.tesseract_assignment_launcher.Model.Apps;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<ArrayList<Apps>> {

    private List<Apps> appsList;
    private Apps apps;
    private AppsAdapter appsAdapter;
    private RecyclerView recyclerView;
    private PackageManager packageManager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        packageManager = getPackageManager();
        appsList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));
        appsAdapter = new AppsAdapter(getApplicationContext(),appsList);
        recyclerView.setAdapter(appsAdapter);

        getApps();

        LoaderManager.getInstance(this).initLoader(0,null,this);


    }


    private void getApps(){
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> availableActivities =
                null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            availableActivities = packageManager.queryIntentActivities(intent, PackageManager.MATCH_ALL);
        }else{
            availableActivities = packageManager.queryIntentActivities(intent, 0);
        }
//        for (ResolveInfo ri : availableActivities){
////            Log.d("TAG","APp : " + ri.loadLabel(packageManager));
//            apps = new Apps();
//            apps.setIcon(ri.loadIcon(packageManager));
//            appsList.add(apps);
//            appsAdapter.notifyDataSetChanged();
//        }


//        List<PackageInfo> packs = getPackageManager().getInstalledPackages(0);
//        for (int i = 0; i < packs.size(); i++) {
//            PackageInfo p = packs.get(i);
//            if ((!isSystemPackage(p))) {
//                String appName = p.applicationInfo.loadLabel(getPackageManager()).toString();
//                String packages = p.applicationInfo.packageName;
//                Log.d("TAG","name : " + appName);
//
//            }
//        }





    }
    private boolean isSystemPackage(PackageInfo pkgInfo) {
        return (pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
    }

    @NonNull
    @NotNull
    @Override
    public Loader<ArrayList<Apps>> onCreateLoader(int id, @Nullable @org.jetbrains.annotations.Nullable Bundle args) {
        return new AppsLoader(getApplicationContext());
    }

    @Override
    public void onLoadFinished(@NonNull @NotNull Loader<ArrayList<Apps>> loader,
                               ArrayList<Apps> data) {


//        appListAdapter.setData(data);
//        appListAdapter.notifyDataSetChanged();


        Log.d("TAG","onLoadFinished : " + data.size());
        appsAdapter.addData(data);

    }

    @Override
    public void onLoaderReset(@NonNull @NotNull Loader<ArrayList<Apps>> loader) {
//        appListAdapter.setData(null);

    }
}