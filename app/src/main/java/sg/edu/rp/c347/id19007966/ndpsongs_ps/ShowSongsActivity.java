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
import android.widget.Spinner;

import java.util.ArrayList;

public class ShowSongsActivity extends AppCompatActivity {

    Button showAll5StarsButton;
    ListView listView;
    ArrayAdapter<Song> aa;
    ArrayList<Song> al;
    ArrayAdapter<String> yearAdapter;
    ArrayList<String> yearList;
    Spinner yearSpinner;
    String selectedYear = DBHelper.ALL_YEARS_OPTION;
    boolean show5Stars = false;

    private int requestKey = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_songs);

        listView = findViewById(R.id.listView);
        showAll5StarsButton = findViewById(R.id.show5StarButton);

        yearSpinner = findViewById(R.id.yearSpinner);

        yearList = getYearsFromDB();
        yearAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, yearList);

        yearSpinner.setAdapter(yearAdapter);

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                al.clear();
                selectedYear = yearList.get(i);
                al.addAll(getFromDB());
                aa.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                al.clear();
                selectedYear = null;
                al.addAll(getFromDB());
                aa.notifyDataSetChanged();
            }
        });

        al = getFromDB();
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
                show5Stars = !show5Stars;
                al.addAll(getFromDB());
                aa.notifyDataSetChanged();
            }
        });
    }

    private ArrayList<String> getYearsFromDB() {
        DBHelper helper = new DBHelper(this);
        ArrayList<String> years = helper.retrieveAllYears();
        helper.close();

        return years;
    }

    private ArrayList<Song> getFromDB() {
        ArrayList<Song> songs;
        DBHelper helper = new DBHelper(this);
        int stars = show5Stars ? 5 : DBHelper.NO_FILTERING_KEY;

        if (!selectedYear.equals(DBHelper.ALL_YEARS_OPTION)) {
            songs = helper.retrieveWithConditions(stars, Integer.parseInt(selectedYear));
        }
        else {
            songs = helper.retrieveWithConditions(stars);
        }
        helper.close();
        return songs;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == requestKey && resultCode == RESULT_OK) {
            al.clear();
            al.addAll(getFromDB());
            aa.notifyDataSetChanged();
            yearList.clear();
            yearList.addAll(getYearsFromDB());
            yearAdapter.notifyDataSetChanged();
        }
    }
}