package com.samsung.muhammad.polisi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewStub;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;
import com.samsung.muhammad.polisi.responses.LoginResponse;
import com.samsung.muhammad.polisi.R;
import com.samsung.muhammad.polisi.adapter.GridViewAdapter;
import com.samsung.muhammad.polisi.adapter.ViewPagerAdapter;
import com.samsung.muhammad.polisi.product.Product;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by muhammad on 19/09/17.
 */

public class HomeActivity extends AppCompatActivity {

    ViewPager viewPager;

    private ViewStub stubGrid;
    GridView gridView;

    private GridViewAdapter gridViewAdapter;
    private List<Product> productList;
    private int currentViewMode = 0;
    LoginResponse user;

    Toolbar myToolbar;
    static final int VIEW_MODE_GRIDVIEW = 1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        Hawk.init(this).build();
        user = Hawk.get("user");

        //menu toolbar
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        /*        if(user.getLevel().equals("admin"))
        {
            getSupportActionBar().setTitle("Home Admin");
        }
        else {
            getSupportActionBar().setTitle("Home Polres");
        }
*/
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);

        Timer timer = new Timer();
        timer.schedule(new MyTimerTask(), 2000, 4000);

        stubGrid = (ViewStub) findViewById(R.id.stub_grid);

        //inflate ViewStub before get view
        stubGrid.inflate();

        gridView = (GridView) findViewById(R.id.mygridview);

//        SharedPreferences sharedPreferences = getSharedPreferences("ViewMode", MODE_PRIVATE);
//        currentViewMode = sharedPreferences.getInt("currentViewMode", VIEW_MODE_GRIDVIEW); //Default is view listview

        getProductList();
        stubGrid.setVisibility(View.VISIBLE);
        setAdapters();

        gridView.setOnItemClickListener(onItemClick);


    }

    public void setAdapters() {

        gridViewAdapter = new GridViewAdapter(this, R.layout.grid_item, productList);
        gridView.setAdapter(gridViewAdapter);

    }

    public List<Product> getProductList() {
        //code to get product, replace code here
        productList = new ArrayList<>();
        productList.add(new Product(R.drawable.home, "Polda Sumut"));
        productList.add(new Product(R.drawable.student, "Biodata"));
        if(user.getLevel().equals("admin")){
            productList.add(new Product(R.drawable.folder, "Laporan Kinerja"));
        }
        else {
            productList.add(new Product(R.drawable.folder, "Laporan Kegiatan"));
        }

        productList.add(new Product(R.drawable.aptoide, "Arahan Pimpinan"));
        productList.add(new Product(R.drawable.news, "Berita"));
        productList.add(new Product(R.drawable.chat, "Komunikasi Personil"));

        return productList;
    }

    public class  MyTimerTask extends TimerTask {


        @Override
        public void run() {

            if (this == null){
                return;
            }else {
                HomeActivity.this.runOnUiThread(new Runnable() {



                    @Override
                    public void run() {
                        if (viewPager.getCurrentItem() == 0 ){
                            viewPager.setCurrentItem(1);
                        }else if (viewPager.getCurrentItem() == 1){
                            viewPager.setCurrentItem(2);
                        } else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });

            }
        }
    }

    AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
            Toast.makeText(HomeActivity.this, productList.get(i).getTitle(), Toast.LENGTH_SHORT).show();
            if (productList.get(i).getTitle().equals("Laporan Kegiatan")){
                Intent in = new Intent(getApplicationContext(), ListLaporActivity.class);
                startActivity(in);
            } else if (productList.get(i).getTitle().equals("Profil Polda")){
                Intent in = new Intent(getApplicationContext(), InfoActivity.class);
                startActivity(in);
            } else if (productList.get(i).getTitle().equals("Biodata")){
                Intent in = new Intent(getApplicationContext(), ProfilActivity.class);
                startActivity(in);
            }else if (productList.get(i).getTitle().equals("Pesan")){
                Intent in = new Intent(getApplicationContext(), PesanActivity.class);
                startActivity(in);
            }else if (productList.get(i).getTitle().equals("Arahan Pimpinan")){
                Intent in = new Intent(getApplicationContext(), AboutUsActivity.class);
                startActivity(in);
            }else if (productList.get(i).getTitle().equals("Berita")){
                Intent in = new Intent(getApplicationContext(), BeritaActivity.class);
                startActivity(in);
            }else if (productList.get(i).getTitle().equals("Laporan Kinerja")){
                Intent in = new Intent(getApplicationContext(), RankingActivity.class);
                startActivity(in);
            } else if (productList.get(i).getTitle().equals("Komunikasi Personil")){
                Toast.makeText(getApplicationContext(), "Diskusi", Toast.LENGTH_SHORT).show();
                Intent in = new Intent(getApplicationContext(), DiskusiActivity.class);
                startActivity(in);
                //finish();
                //System.exit(0);

            }
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        /// Meng inflate file Menu
        getMenuInflater().inflate(R.menu.user_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int ID = item.getItemId();

        switch (ID) {
            case R.id.menu_1:
                Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_2:
                Toast.makeText(getApplicationContext(), "Keluar Aplikasi", Toast.LENGTH_SHORT).show();

                System.exit(0);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}
