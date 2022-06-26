package io.github.hanknguyen69.FinalProject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import io.github.hanknguyen69.FinalProject.R;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ToyDetail extends AppCompatActivity {

    TextView toyName, toyPrice,toyDescription;
    ImageView toyImage;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;

    String toyID="";
    FirebaseDatabase database;
    DatabaseReference toys;
    Toy currentToy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toy_detail);

        database= FirebaseDatabase.getInstance();
        toys= database.getReference("Toys");

        toyDescription=(TextView)findViewById(R.id.toy_description);
        toyName=(TextView)findViewById(R.id.toy_name);
        toyPrice=(TextView)findViewById(R.id.toy_price);
        toyImage=(ImageView)findViewById(R.id.img_toy);


        numberButton=(ElegantNumberButton)findViewById(R.id.number_button);


        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Database(getBaseContext()).addToCart(new Order(toyID,currentToy.getName(),numberButton.getNumber(),currentToy.getPrice()));

                Toast.makeText(ToyDetail.this, "Added to Cart", Toast.LENGTH_SHORT).show();

            }
        });


        collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        if (getIntent()!=null)
        {
            toyID=getIntent().getStringExtra("ToyId");
        }
        if (!toyID.isEmpty())
        {
            getDetailToy(toyID);
        }


    }

    private void getDetailToy(String toyID) {
        toys.child(toyID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                currentToy = dataSnapshot.getValue(Toy.class);
                Picasso.with(getBaseContext()).load(currentToy.getImage()).into(toyImage);
                collapsingToolbarLayout.setTitle(currentToy.getName());

                toyName.setText(currentToy.getName());
                toyDescription.setText(currentToy.getDescription());
                toyPrice.setText(currentToy.getPrice());

            }

            @Override
            public void onCancelled( DatabaseError databaseError) {

            }
        });
    }

}
