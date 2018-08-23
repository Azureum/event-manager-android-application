package shridhar_manages.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class SMainActivity extends AppCompatActivity {
    Button btn_share;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smain);

        btn_share=(Button) findViewById(R.id.shareit);


        btn_share.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                shareIt(v);
            }
        });
    }
    private void shareIt(View v) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Event Manager App");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Now be free from the worry of scheduling your events by downloading the app from here to visit https://play.google.com/store?hl=en");
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.shareit) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}