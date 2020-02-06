package nuce.locklinh.qlct.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import nuce.locklinh.qlct.Fragments.FragmentExpense;
import nuce.locklinh.qlct.Fragments.FragmentUser;
import nuce.locklinh.qlct.Fragments.FragmentReport;
import nuce.locklinh.qlct.Fragments.FragmentIncome;
import nuce.locklinh.qlct.R;

public class Home extends AppCompatActivity {
    public static String username;
    Toolbar toolbar;
    BottomNavigationView navigation;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        Intent getUsername = getIntent();
        username = getUsername.getStringExtra("username");

        setContentView(R.layout.activity_home);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Quản lý chi tiêu");



        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setItemIconTintList(null);
        navigation.setItemBackground(getDrawable(R.drawable.custom_tab_background));

        loadFragment(new FragmentIncome());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment: getSupportFragmentManager().getFragments()){
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.nav_thu:
                    fragment = new FragmentIncome();
                    loadFragment(fragment);
                    return true;
                case R.id.nav_chi:
                    fragment = new FragmentExpense();
                    loadFragment(fragment);
                    return true;
                case R.id.nav_thongke:
                    fragment = new FragmentReport();
                    loadFragment(fragment);
                    return true;
                case R.id.nav_nguoidung:
                    fragment = new FragmentUser();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
