package io.github.hanknguyen69.FinalProject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {

    EditText edtname,phone,password;
    Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        edtname=(EditText)findViewById(R.id.name);
        phone=(EditText)findViewById(R.id.phone);
        password=(EditText)findViewById(R.id.password);

        btnSignup=(Button)findViewById(R.id.btnSingup);


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference user_table = database.getReference("User");

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_table.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(phone.getText().toString()).exists())
                        {
                            Toast.makeText(SignUp.this,"Phone already exist",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            User user = new User(edtname.getText().toString(),password.getText().toString());
                            user_table.child(phone.getText().toString()).setValue(user);
                            Toast.makeText(SignUp.this,"Register Success",Toast.LENGTH_SHORT).show();
                            finish();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });




    }
}
