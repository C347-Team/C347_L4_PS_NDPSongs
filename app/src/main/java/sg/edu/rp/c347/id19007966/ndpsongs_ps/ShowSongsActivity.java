package sg.edu.rp.c347.id19007966.ndpsongs_ps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ShowSongsActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<Song> aa;
    ArrayList<Song> al;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_songs);
        DBHelper helper = new DBHelper(this);
        System.out.println(helper.retrieveAll()); // retrieves all
        System.out.println(helper.retrieveWithConditions(5)); // retrieves only if stars == 5

        listView = findViewById(R.id.);

        al = new ArrayList<>();
        aa = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, al);
        listView.setAdapter(aa);

    }
}