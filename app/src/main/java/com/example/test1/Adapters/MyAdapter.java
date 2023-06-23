package com.example.test1.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import com.example.test1.Classes.Exercises;
import com.example.test1.Classes.FirebaseServices;
import com.example.test1.Fragments.Details;
import com.example.test1.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Exercises> exercisesArrayList;
    FirebaseServices fbs;
    String about;

    public MyAdapter(Context context, ArrayList<Exercises> exercisesArrayList,String about /*, ArrayList<String> exercisesArrayListPath*/) {
        this.context = context;
        this.exercisesArrayList = exercisesArrayList;
      //  this.exercisesArrayListPath=exercisesArrayListPath;
        this.about=about;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.itemexe,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        fbs=FirebaseServices.getInstance();
        Exercises exercise=exercisesArrayList.get(position);
        holder.name.setText(exercise.getName());
        StorageReference storageRef=fbs.getStorage().getReference().child(exercise.getPhoto());
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context).load(uri).into(holder.photo);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        holder.loutRv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) context;
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.FramlayoutMain,new Details(exercisesArrayList.get(position))).addToBackStack(null).commit();
            }

        });


    }



    @Override
    public int getItemCount() {
        return exercisesArrayList.size();

    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView  name;
        ImageView photo;
        LinearLayout loutRv;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.tvNameL);
            photo=itemView.findViewById(R.id.ivPhoto);
            loutRv=itemView.findViewById(R.id.loutRvL);
        }
    }

}
