package io.github.hanknguyen69.FinalProject;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ToyView extends RecyclerView.ViewHolder
                      implements View.OnClickListener {

    public TextView toy_name;
    public ImageView toy_image;

    private ItemClick itemClick;

    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public ToyView(View itemView) {
        super(itemView);
        toy_name=(TextView) itemView.findViewById(R.id.toy_name);
        toy_image=(ImageView) itemView.findViewById(R.id.toy_image);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClick.onClick(v, getAdapterPosition(),false);

    }
}
