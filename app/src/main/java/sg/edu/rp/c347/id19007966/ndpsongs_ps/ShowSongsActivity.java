package sg.edu.rp.c347.id19007966.ndpsongs_ps;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ShowSongsActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<Song> aa;
    ArrayList<Song> al;

    private int requestKey = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_songs);

        listView = findViewById(R.id.listView);

        al = getFromDB(false);
        aa = new SongAdapter(this, R.layout.row_song, al);
        listView.setAdapter(aa);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ShowSongsActivity.this, ModifySongActivity.class);
                intent.putExtra("data", al.get(i));
                startActivityForResult(intent, requestKey);
            }
        });
    }

    private ArrayList<Song> getFromDB(boolean useStar) {
        ArrayList<Song> songs;
        DBHelper helper = new DBHelper(this);
        if (useStar) {
            songs = helper.retrieveWithConditions(5);
        }
        else {
            songs = helper.retrieveAll();
        }
        helper.close();
        return helper.retrieveAll();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == requestKey && resultCode == RESULT_OK) {
            al.clear();
            al.addAll(getFromDB(false));
            aa.notifyDataSetChanged();
        }
    }
}