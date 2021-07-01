package com.example.tesseract_assignment_launcher.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tesseract_assignment_launcher.Model.Apps;
import com.example.tesseract_assignment_launcher.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AppsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Apps> list;
    private LayoutInflater layoutInflater;

    public AppsAdapter(Context context, List<Apps> list) {
        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }

    public void addData(List<Apps> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.app_list_item,parent,false);


        return new AppHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof AppHolder){
            AppHolder appHolder = (AppHolder) holder;

            appHolder.imageView.setImageDrawable(list.get(position).getIcon());
            appHolder.textView_appName.setText(list.get(position).getAppName());

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class AppHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView_appName;
        public AppHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textView_appName = itemView.findViewById(R.id.textView_appName);
        }
    }
}
