package cuongd13.demoandroid;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailActivity extends Activity {

    Intent intent ;
    Bundle bundle ;
    String title , href , url , gstream , mp4upload , openload ;
    VideoView videoView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        videoView = (VideoView)findViewById(R.id.videoView) ;

        intent = getIntent();

        if (intent.getBundleExtra("dulieu") != null) {
            bundle = intent.getBundleExtra("dulieu");
            title = bundle.getString("Title");
            href = bundle.getString("Href");
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        url = "http://acecinema.net/php/animewatch_new.php";

        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Toast.makeText(DetailActivity.this , response.toString() , Toast.LENGTH_LONG).show();
                CustomJson(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetailActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("LinkIos", href );
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

            gstream = jsonObject.getString("gstream").replace("\\", "");
            mp4upload = jsonObject.getString("mp4upload").replace("\\", "");
            openload = jsonObject.getString("openload").replace("\\", "");

            Video();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void Video(){

        Uri uri=Uri.parse(gstream);
        videoView.setVideoURI(uri);
        videoView.start();
        MediaController mediaController = new MediaController(DetailActivity.this);
        mediaController.setMediaPlayer(videoView);
        mediaController.show();
        videoView.setMediaController(mediaController) ;
    }
}
