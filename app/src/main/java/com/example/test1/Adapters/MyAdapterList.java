package com.example.test1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test1.Fragments.LoginFragment;
import com.example.test1.R;
import com.example.test1.Recycler.RvExercises;
import com.squareup.picasso.Picasso;

public class MyAdapterList   extends RecyclerView.Adapter<MyAdapterList.MyViewHolder>{

    private String [] mDataset;
    private int [] images;
    Context context;

    public MyAdapterList(Context context,String [] mDataset, int[] images ) {
        this.context = context;
        this.mDataset = mDataset;
        this.images=images;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView  name,logout;
        LinearLayout loutRv;
        ImageView photo;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.tvNameL);
            photo=itemView.findViewById(R.id.ivPhoto);
            loutRv=itemView.findViewById(R.id.loutRvL);
            logout=itemView.findViewById(R.id.tvLogout);


        }
    }
    @Override
    public MyAdapterList.MyViewHolder onCreateViewHolder(ViewGroup parent , int viewType){
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist,parent,false);
        return new MyViewHolder(v);

    }
    @Override
    public void onBindViewHolder(MyViewHolder holder,int position){
        holder.name.setText(mDataset[position]);
        Picasso.get().load(images[position]).into(holder.photo);


        holder.loutRv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) context;
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.FramlayoutMain,new RvExercises(mDataset[position])).addToBackStack(null).commit();

            }

        });


    }
    @Override
    public int getItemCount(){return mDataset.length;}
}
