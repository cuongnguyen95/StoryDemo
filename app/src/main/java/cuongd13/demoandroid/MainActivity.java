package cuongd13.demoandroid;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cuongd13.demoandroid.Adapter.HomeAdapter;
import cuongd13.demoandroid.Adapter.StoryAdapter;
import cuongd13.demoandroid.Adapter.UpdateAdapter;
import cuongd13.demoandroid.Sqlite.Database;
import cuongd13.demoandroid.model.Detail;
import cuongd13.demoandroid.model.Home;
import cuongd13.demoandroid.model.Update;

public class MainActivity extends AppCompatActivity {
    GridView gvhome;
    ArrayList<Home> arrayList1;
    ArrayList<Update> arrayList2;
    ArrayList<Detail> detailArrayList;
    HomeAdapter homeAdapter;
    UpdateAdapter updateAdapter;
    Database database ;
    StoryAdapter storyAdapter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    nav1();
                    return true;
                case R.id.navigation_dashboard:
                    nav2();
                    return true;
                case R.id.navigation_notifications:
                    nav3();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gvhome = (GridView) findViewById(R.id.home);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        nav1();

    }

    ;

    public void nav1() {
        arrayList1 = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://gomcineplex.com/data/anime/dailyHD.json";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ShowJson1(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(jsonArrayRequest);
    }

    public void nav2() {
        arrayList2 = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://gomcineplex.com/data/anime/hdmain_test.json";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ShowJson2(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(jsonArrayRequest);
    }

    public void nav3() {
        detailArrayList = new ArrayList<>();
        database = new Database(this , "truyen.sqlite" , null , 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS Truyen(Id INTEGER PRIMARY KEY AUTOINCREMENT , Title VARCHAR(200) , Href VARCHAR(200) )");
        Cursor data = database.getData("SELECT * FROM Truyen");
        while (data.moveToNext()){
            String title = data.getString(1);
            String href = data.getString(2);
            // Toast.makeText(MainActivity.this , title , Toast.LENGTH_LONG).show();
            // truyen vao 1 mang
            Detail detail = new Detail();
            detail.setTitle(title);
            detail.setHref(href);
            detailArrayList.add(detail);

        }

        storyAdapter = new StoryAdapter(MainActivity.this, R.layout.detail_row, detailArrayList);
        storyAdapter.notifyDataSetChanged();
        gvhome.setNumColumns(1);
        gvhome.setAdapter(storyAdapter);
        Onclick3(gvhome);
    }

    public void ShowJson1(JSONArray response) {
        if (response.length() == 0) {
            Toast.makeText(MainActivity.this, "Không có kết quả nào được tìm thấy !", Toast.LENGTH_LONG).show();
        }
        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject homeObject = response.getJSONObject(i);
                Home home = new Home();
                home.setTitle(homeObject.getString("Title"));
                home.setThumb(homeObject.getString("Thumb"));
                home.setLink(homeObject.getString("Link"));
                home.setGenres(homeObject.getString("Genres"));

                arrayList1.add(home);

                //        Log.d("kich thuoc" , String.valueOf(arrayList.size()));

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        homeAdapter = new HomeAdapter(MainActivity.this, R.layout.home_row, arrayList1);
        homeAdapter.notifyDataSetChanged();
        gvhome.setNumColumns(3);
        gvhome.setAdapter(homeAdapter);
        Onclick1(gvhome);
    }

    public void ShowJson2(JSONArray response) {
        if (response.length() == 0) {
            Toast.makeText(MainActivity.this, "Không có kết quả nào được tìm thấy !", Toast.LENGTH_LONG).show();
        }
        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject homeObject = response.getJSONObject(i);
                Update update = new Update();
                update.setTitle(homeObject.getString("Title"));
                update.setThumb(homeObject.getString("Thumb"));
                update.setLink(homeObject.getString("Link"));
                update.setGenres(homeObject.getString("Genres"));
                update.setIson(homeObject.getString("IsOn"));

                arrayList2.add(update);

                //        Log.d("kich thuoc" , String.valueOf(arrayList.size()));

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        updateAdapter = new UpdateAdapter(MainActivity.this, R.layout.home_row, arrayList2);
        updateAdapter.notifyDataSetChanged();
        gvhome.setNumColumns(3);
        gvhome.setAdapter(updateAdapter);
        Onclick2(gvhome);
    }

    public void Onclick1(GridView v) {
        v.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, StoryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Title", arrayList1.get(position).getTitle());
                bundle.putString("Thumb", arrayList1.get(position).getThumb());
                bundle.putString("Link", arrayList1.get(position).getLink());
                bundle.putString("Genres", arrayList1.get(position).getGenres());
                intent.putExtra("dulieu1", bundle);
                startActivity(intent);
            }
        });
    }

    public void Onclick2(GridView v) {
        v.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, StoryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Title", arrayList2.get(position).getTitle());
                bundle.putString("Thumb", arrayList2.get(position).getThumb());
                bundle.putString("Link", arrayList2.get(position).getLink());
                bundle.putString("Genres", arrayList2.get(position).getGenres());
                bundle.putString("IsOn", arrayList2.get(position).getIson());

                intent.putExtra("dulieu2", bundle);
                startActivity(intent);
            }
        });
    }

    public void Onclick3(GridView v) {
        v.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Title", detailArrayList.get(position).getTitle());
                bundle.putString("Href", detailArrayList.get(position).getHref());
                intent.putExtra("dulieu", bundle);
                startActivity(intent);

            }
        });
    }

}
