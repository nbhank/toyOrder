package io.github.hanknguyen69.FinalProject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Home extends AppCompatActivity
                    implements NavigationView.OnNavigationItemSelectedListener{

        FirebaseDatabase database;
        DatabaseReference category;

        TextView txtFullName;
        RecyclerView recycle_menu;
        RecyclerView.LayoutManager layoutManager;
        FirebaseRecyclerAdapter<Category,MenuView> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Menu");

        database=FirebaseDatabase.getInstance();
        category=database.getReference("category");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cartIntent = new Intent(Home.this,Cart.class);
                startActivity(cartIntent);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(
                this,drawer,toolbar,0,0);
        drawer.setDrawerListener(toogle);
        toogle.syncState();


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);

        txtFullName=(TextView)headerView.findViewById(R.id.txtFullName);
        txtFullName.setText(CurrentUser.currentUser.getName());

        recycle_menu=(RecyclerView)findViewById(R.id.recycle_menu);
        recycle_menu.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recycle_menu.setLayoutManager(layoutManager);
        loadMenu();




    }

    private void loadMenu() {
             adapter = new FirebaseRecyclerAdapter<Category, MenuView>(Category.class,R.layout.menu_item,MenuView.class,category) {
            @Override
            protected void populateViewHolder(MenuView menuView, Category category, int i) {
                menuView.txtMenuName.setText(category.getName());
                Picasso.with(getBaseContext()).load(category.getImage())
                        .into(menuView.imageView);

                final Category clickItem = category;
                menuView.setItemClick(new ItemClick() {
                    @Override
                    public void onClick(View v, int position, boolean isLongClick) {
                        Intent toyList = new Intent(Home.this,ToyList.class);
                        toyList.putExtra("CategoryId",adapter.getRef(position).getKey());
                        startActivity(toyList);
                    }
                });

            }
        };

        recycle_menu.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer=(DrawerLayout)findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id=item.getItemId();
        if (id==R.id.nav_menu) {
        }
        else if (id==R.id.nav_cart) {
        }
        else if (id==R.id.nav_order) {
        }
        else if (id==R.id.nav_logout) {
        }

        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
