package cuongd13.demoandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cuongd13.demoandroid.Adapter.StoryAdapter;
import cuongd13.demoandroid.model.Detail;

public class StoryActivity extends AppCompatActivity {
    private ListView lvStory;
    private Button btnXem;
    private String title, thumb, link, genres, isOn, url;
    private Bundle bundle1;
    private Intent intent;
    ArrayList<Detail> detailArrayList;
    StoryAdapter storyAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        lvStory = (ListView)findViewById(R.id.list)  ;

        detailArrayList = new ArrayList<>();

        intent = getIntent();

        if (intent.getBundleExtra("dulieu1") != null) {
            bundle1 = intent.getBundleExtra("dulieu1");

            title = bundle1.getString("Title");
            thumb = bundle1.getString("Thumb");
            link = bundle1.getString("Link");
            genres = bundle1.getString("Genres");

        }
        if (intent.getBundleExtra("dulieu2") != null) {

            bundle1 = intent.getBundleExtra("dulieu2");
            title = bundle1.getString("Title");
            thumb = bundle1.getString("Thumb");
            link = bundle1.getString("Link");
            genres = bundle1.getString("Genres");
            isOn = bundle1.getString("IsOn");

        }

        RequestQueue queue = Volley.newRequestQueue(this);
        url = "http://acecinema.net/php/animewatch_new.php";

        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //            Toast.makeText(StoryActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                CustomJson(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(StoryActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Episodes", link);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);

    }

    public void CustomJson(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray episodes = jsonObject.getJSONArray("episodes");
            for (int i = 0; i < episodes.length(); i++) {
                try {
                    JSONObject detailObject = episodes.getJSONObject(i);
                    Detail detail = new Detail();
                    detail.setTitle(detailObject.getString("title"));
                    detail.setHref(detailObject.getString("href"));
                    detailArrayList.add(detail);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        storyAdapter = new StoryAdapter(StoryActivity.this, R.layout.detail_row, detailArrayList);
        storyAdapter.notifyDataSetChanged();
        lvStory.setAdapter(storyAdapter);

    }
}
