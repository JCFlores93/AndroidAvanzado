package pe.cibertec.drawerdemo;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
       /* toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });*/

        setSupportActionBar(toolbar);
      //  getSupportActionBar().setIcon(R.mipmap.ic_launcher_round);

        drawerLayout =(DrawerLayout)findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_item_1:
                        showFragment("Option 1");
                        break;
                    case R.id.nav_item_2:
                        showFragment("Option 2");
                        break;
                    case R.id.nav_item_3:
                        showFragment("Option 3");
                        break;
                    case R.id.nav_item_4:
                        showFragment("Option 4");
                        break;
                    case R.id.nav_item_5:
                        showFragment("Option 5");
                        break;
               }
               drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        showFragment("Option 1");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //cuando se setea como actionBar
        if (item.getItemId() == android.R.id.home);{
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    private void showFragment(String option){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content,MainFragment.newInstance(option));
        ft.commit();
    }
}
