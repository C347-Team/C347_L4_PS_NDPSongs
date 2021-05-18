package sg.edu.rp.c347.id19007966.ndpsongs_ps;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ShowSongsActivity extends AppCompatActivity {

    Button showAll5StarsButton;
    ListView listView;
    ArrayAdapter<Song> aa;
    ArrayList<Song> al;

    private int requestKey = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_songs);

        listView = findViewById(R.id.listView);
        showAll5StarsButton = findViewById(R.id.show5StarButton);

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

        showAll5StarsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                al.clear();
                al.addAll(getFromDB(true));

                aa.notifyDataSetChanged();
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
        return songs;
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