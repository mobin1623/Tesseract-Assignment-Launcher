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
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
//                StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        appsAdapter = new AppsAdapter(getApplicationContext(),appsList);
        recyclerView.setAdapter(appsAdapter);


        LoaderManager.getInstance(this).initLoader(0,null,this);


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

        Log.d("TAG","onLoadFinished : " + data.size());
        appsAdapter.addData(data);

    }

    @Override
    public void onLoaderReset(@NonNull @NotNull Loader<ArrayList<Apps>> loader) {
        appsAdapter.addData(null);

    }
}