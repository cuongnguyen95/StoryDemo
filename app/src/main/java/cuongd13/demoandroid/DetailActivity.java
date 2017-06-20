package cuongd13.demoandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailActivity extends AppCompatActivity {
    Intent intent ;
    Bundle bundle ;
    String title , href ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        intent = getIntent();

        if (intent.getBundleExtra("dulieu1") != null) {
            bundle = intent.getBundleExtra("dulieu1");

            title = bundle.getString("Title");
            href = bundle.getString("Href");
        }
    }
}
