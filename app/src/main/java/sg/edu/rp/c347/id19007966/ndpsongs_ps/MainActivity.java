package sg.edu.rp.c347.id19007966.ndpsongs_ps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editTextTitle, editTextSingers, editTextYear;
    RadioGroup radioGroupStarsSelection;
    Button insertBtn, showListBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextTitle = findViewById(R.id.editTextInsertSongTitle);
        editTextSingers = findViewById(R.id.editTextInsertSingers);
        editTextYear = findViewById(R.id.editTextInsertYear);

        radioGroupStarsSelection = findViewById(R.id.radioGroupInsertStars);

        insertBtn = findViewById(R.id.buttonInsert);
        showListBtn = findViewById(R.id.buttonShowList);

        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkAllFields()) {
                    DBHelper dbHelper = new DBHelper(MainActivity.this);
                    String title = editTextTitle.getText().toString();
                    String singers = editTextSingers.getText().toString();
                    int year = Integer.parseInt(editTextYear.getText().toString());
                    RadioButton selected = findViewById(radioGroupStarsSelection.getCheckedRadioButtonId());
                    int stars = Integer.parseInt(selected.getText().toString());

                    Song newSong = new Song(title, singers, year, stars);

                    long statusId = dbHelper.insert(newSong);
                    dbHelper.close();
                    if (statusId != -1) {
                        Toast.makeText(MainActivity.this, "Inserted song successfully.", Toast.LENGTH_SHORT).show();
                        clearAllFields();
                    }
                }
            }
        });

        showListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ShowSongsActivity.class));
            }
        });
    }

    private boolean checkAllFields() {
        ArrayList<String> problems = new ArrayList<>();
        if (editTextTitle.getText().toString().trim().isEmpty()) {
            problems.add("title");
        }
        if (editTextSingers.getText().toString().trim().isEmpty()) {
            problems.add("singers");
        }
        if (editTextYear.getText().toString().trim().isEmpty()) {
            problems.add("year");
        }
        if (radioGroupStarsSelection.getCheckedRadioButtonId() == -1) {
            problems.add("stars");
        }
        if (problems.size() > 0) {
            String message = "Ensure that: ";

            if (problems.size() == 1) {
                message += problems.get(0) + " ";
            }
            else if (problems.size() > 1) {
                for (int i = 0; i < problems.size(); i++) {
                    if (i == problems.size() - 1) {
                        message += "and " + problems.get(i) + " ";
                    }
                    else {
                        message += problems.get(i) + ", ";
                    }
                }
            }

            message += "are filled/selected correctly.";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }

        return problems.isEmpty();
    }

    private void clearAllFields() {
        editTextTitle.setText("");
        editTextYear.setText("");
        editTextSingers.setText("");
        radioGroupStarsSelection.clearCheck();
    }
}