package com.maulanakurnia.movieroom.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.maulanakurnia.movieroom.R;
import com.maulanakurnia.movieroom.data.model.Credit;
import com.maulanakurnia.movieroom.utils.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Created by Maulana Kurnia on 6/2/2021
 * Keep Coding & Stay Awesome!
 **/
public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder>{

    protected ArrayList<Credit.Cast> credits = new ArrayList<>();
    protected Context context;

    public CastAdapter(Context context){
        this.context = context;
    }

    public void setData(ArrayList<Credit.Cast> items){
        credits.clear();
        credits.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull @NotNull @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_cast, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CastAdapter.ViewHolder holder, int position) {
        if(credits.get(position).getProfile_path() != null) {
            Glide.with(holder.itemView.getContext())
                    .load(Constants.IMG_URL_200 + credits.get(position).getProfile_path())
                    .into(holder.iv_image);
        } else {
            Glide.with(holder.itemView.getContext())
                    .load(R.drawable.default_user)
                    .into(holder.iv_image);
        }

        holder.tv_name.setText(credits.get(position).getName());
        holder.tv_role.setText(credits.get(position).getCharacter());
    }

    @Override
    public int getItemCount() {
        return credits.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        protected final TextView tv_name, tv_role;
        protected final ImageView iv_image;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv_name     = itemView.findViewById(R.id.tv_cast_name);
            tv_role     = itemView.findViewById(R.id.tv_cast_role);
            iv_image    = itemView.findViewById(R.id.iv_cast_image);
        }
    }
}
