package com.example.yaowu.steamhelper;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.yaowu.steamhelper.db.Achievements;
import com.example.yaowu.steamhelper.db.Friend;
import com.example.yaowu.steamhelper.db.Game;
import com.example.yaowu.steamhelper.util.NavigationIniti;

import org.litepal.crud.DataSupport;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //set navigation menu
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        //set navigation button
        toolbar.setNavigationIcon(R.drawable.castlecrahsers);
        getSupportActionBar().setTitle("Owned Games");

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        NavigationIniti.initiNavView(navigationView, this, drawerLayout);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }


    /**
     * 显示进度对话框
     */
    public static void showProgressDialog(Context context, ProgressDialog progressDialog){
        if(progressDialog == null){
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("正在加载");
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }

    /**
     * 关闭进度对话框
     */
    public static void closeProgressDialog(ProgressDialog progressDialog){
        if(progressDialog != null) progressDialog.dismiss();
    }
}
