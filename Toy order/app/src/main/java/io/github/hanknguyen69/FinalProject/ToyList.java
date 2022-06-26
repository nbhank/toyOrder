package io.github.hanknguyen69.FinalProject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import io.github.hanknguyen69.FinalProject.R;

import static android.widget.AdapterView.*;

public class ToyList extends AppCompatActivity {

    RecyclerView recycleView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference toyList;
    String  categoryID="";
    FirebaseRecyclerAdapter<Toy,ToyView> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toy_list);

        database=FirebaseDatabase.getInstance();
        toyList=database.getReference("Toys");

        recycleView=(RecyclerView)findViewById(R.id.recycle_toy);
        recycleView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recycleView.setLayoutManager(layoutManager);

        if (getIntent()!=null)
        {
            categoryID=getIntent().getStringExtra("CategoryId");
        }
        if (!categoryID.isEmpty()&& categoryID!=null)
        {
            loadListToy(categoryID);
        }


    }

    private void loadListToy(String categoryID) {
        adapter= new FirebaseRecyclerAdapter<Toy, ToyView>(Toy.class,
                R.layout.toy_item,
                ToyView.class,toyList.orderByChild("menuID").equalTo(categoryID)) {
            @Override
            protected void populateViewHolder(ToyView toyView, Toy toy, int i) {

                toyView.toy_name.setText(toy.getName());
                Picasso.with(getBaseContext()).load(toy.getImage()).into(toyView.toy_image);

                final Toy local = toy;
                toyView.setItemClick(new ItemClick() {
                    @Override
                    public void onClick(View v, int position, boolean isLongClick) {

                        Intent toyDetail = new Intent(ToyList.this,ToyDetail.class);
                        toyDetail.putExtra("ToyId",adapter.getRef(position).getKey());
                        startActivity(toyDetail);

                    }
                });


            }
        };
        recycleView.setAdapter(adapter);

    }
}
