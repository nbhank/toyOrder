package io.github.hanknguyen69.FinalProject;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.google.android.material.internal.TextDrawableHelper;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class CartView extends RecyclerView.ViewHolder implements View.OnClickListener{


    public TextView txtCartName, txtPrice;
    public ImageView imageCartCount;

    private ItemClick itemClick;

    public void setTxtCartName(TextView txtCartName){
        this.txtCartName=txtCartName;
    }


    public CartView( View itemView) {
        super(itemView);

        txtCartName=(TextView)itemView.findViewById(R.id.cart_item_name);
        txtPrice=(TextView)itemView.findViewById(R.id.cart_item_price);
        imageCartCount =(ImageView)itemView.findViewById(R.id.cart_item_count);
    }

    @Override
    public void onClick(View v) {

    }
}

public class CartAdapt extends RecyclerView.Adapter<CartView> {

    private List<Order> listData = new ArrayList<>();
    private Context context;

    public CartAdapt(List<Order> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @Override
    public CartView onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.cart_layout,parent,false);
        return new CartView(itemView);
    }

    @Override
    public void onBindViewHolder(CartView holder, int position) {

        TextDrawable drawable = TextDrawable.builder().buildRound(""+listData.get(position).getQuantity(), Color.RED);
        holder.imageCartCount.setImageDrawable(drawable);

        Locale locale = new Locale("en","CA");
        NumberFormat number =NumberFormat.getCurrencyInstance(locale);
        int price = (Integer.parseInt(listData.get(position).getPrice())) * (Integer.parseInt(listData.get(position).getQuantity()));
        holder.txtPrice.setText(number.format(price));
        holder.txtCartName.setText(listData.get(position).getProductName());





    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
