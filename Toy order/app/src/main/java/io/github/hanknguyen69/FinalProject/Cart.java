package io.github.hanknguyen69.FinalProject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Cart extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    TextView txtTotal;
    Button btnPlace;

    FirebaseDatabase database;
    DatabaseReference request;
    List<Order> cart = new ArrayList<>();
    CartAdapt adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        database=FirebaseDatabase.getInstance();
        request = database.getReference("requests");
        recyclerView=(RecyclerView)findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotal=(TextView)findViewById(R.id.total);
        btnPlace=(Button)findViewById(R.id.btnPlaceOrder);

        loadListToy();

    }

    private void loadListToy() {
        cart= new Database(this).getCart();
        adapter = new CartAdapt(cart,this);
        recyclerView.setAdapter(adapter);

        int total =0;
        for (Order order:cart)
        {
            total+=(Integer.parseInt(order.getPrice()))* (Integer.parseInt(order.getQuantity()));
            Locale locale = new Locale("en","CA");
            NumberFormat number =NumberFormat.getCurrencyInstance(locale);
            txtTotal.setText(number.format(total));
        }


    }
}
