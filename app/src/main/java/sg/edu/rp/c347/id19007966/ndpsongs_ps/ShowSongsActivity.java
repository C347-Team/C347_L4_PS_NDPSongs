package sg.edu.rp.c347.id19007966.ndpsongs_ps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ShowSongsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_songs);
        DBHelper helper = new DBHelper(this);
        System.out.println(helper.retrieveAll()); // retrieves all
        System.out.println(helper.retrieveWithConditions(5)); // retrieves only if stars == 5
    }
}