package sg.edu.rp.c347.id19007966.ndpsongs_ps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SongAdapter extends ArrayAdapter<Song> {

    private ArrayList<Song> songs;
    private Context context;
    private TextView textViewYear, textViewName, textViewSinger;
    private ImageView iv1, iv2, iv3, iv4, iv5;
    private int stars;

    public SongAdapter(Context context, int resource, ArrayList<Song> objects) {
        super(context, resource, objects);
        // Store the module that is passed to this adapter
        songs = objects;
        // Store Context object as we would need to use it later
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_song, parent, false);
        Song song = songs.get(position);

        textViewName = rowView.findViewById(R.id.textViewName);
        textViewYear = rowView.findViewById(R.id.textViewYear);
        textViewSinger = rowView.findViewById(R.id.textViewSinger);

        textViewYear.setText("" + song.getYear());
        textViewName.setText(song.getTitle());
        textViewSinger.setText(song.getSingers());

        //Match the UI components with Java variables
        iv1 = (ImageView) rowView.findViewById(R.id.imageView1star) ;
        iv2 = (ImageView) rowView.findViewById(R.id.imageView2star) ;
        iv3 = (ImageView) rowView.findViewById(R.id.imageView3star) ;
        iv4 = (ImageView) rowView.findViewById(R.id.imageView4star) ;
        iv5 = (ImageView) rowView.findViewById(R.id.imageView5star) ;


        DBHelper db = new DBHelper(context);
        ArrayList<Song> songs = db.retrieveAll();
        stars = songs.get(position).getStars();

        if (stars >= 5) {
            iv5.setImageResource(android.R.drawable.btn_star_big_on);
            iv4.setImageResource(android.R.drawable.btn_star_big_on);
            iv3.setImageResource(android.R.drawable.btn_star_big_on);
            iv2.setImageResource(android.R.drawable.btn_star_big_on);
            iv1.setImageResource(android.R.drawable.btn_star_big_on);

        }
        else if (stars == 4){
            iv5.setImageResource(android.R.drawable.btn_star_big_off);
            iv4.setImageResource(android.R.drawable.btn_star_big_on);
            iv3.setImageResource(android.R.drawable.btn_star_big_on);
            iv2.setImageResource(android.R.drawable.btn_star_big_on);
            iv1.setImageResource(android.R.drawable.btn_star_big_on);
        }
        else if (stars == 3){
            iv5.setImageResource(android.R.drawable.btn_star_big_off);
            iv4.setImageResource(android.R.drawable.btn_star_big_off);
            iv3.setImageResource(android.R.drawable.btn_star_big_on);
            iv2.setImageResource(android.R.drawable.btn_star_big_on);
            iv1.setImageResource(android.R.drawable.btn_star_big_on);
        }
        else if (stars == 2){
            iv5.setImageResource(android.R.drawable.btn_star_big_off);
            iv4.setImageResource(android.R.drawable.btn_star_big_off);
            iv3.setImageResource(android.R.drawable.btn_star_big_off);
            iv2.setImageResource(android.R.drawable.btn_star_big_on);
            iv1.setImageResource(android.R.drawable.btn_star_big_on);
        }
        else if (stars == 1) {
            iv5.setImageResource(android.R.drawable.btn_star_big_off);
            iv4.setImageResource(android.R.drawable.btn_star_big_off);
            iv3.setImageResource(android.R.drawable.btn_star_big_off);
            iv2.setImageResource(android.R.drawable.btn_star_big_off);
            iv1.setImageResource(android.R.drawable.btn_star_big_on);
        }else{

        }

        return rowView;
    }
}
