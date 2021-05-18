package sg.edu.rp.c347.id19007966.ndpsongs_ps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SongAdapter extends ArrayAdapter<Song> {

    private ArrayList<Song> songs;
    private Context context;
    private TextView textViewYear, textViewName, textViewSinger;

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

        textViewYear.setText(song.getYear());
        textViewName.setText(song.getStars());
        textViewSinger.setText(song.getSingers());

        return rowView;
    }
}
