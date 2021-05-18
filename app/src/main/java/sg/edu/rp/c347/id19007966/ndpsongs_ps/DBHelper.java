package sg.edu.rp.c347.id19007966.ndpsongs_ps;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "NationalDayParade.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_SONG = "song";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_SINGERS = "singers";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_STARS = "stars";
    static final int NO_FILTERING_KEY = -999;
    static final int NO_YEAR_FILTERING_KEY = -1000;
    static final String ALL_YEARS_OPTION = "All Years";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createStatement = "CREATE TABLE " + TABLE_SONG;
        createStatement += "(";
        createStatement += COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,";
        createStatement += COLUMN_TITLE + " TEXT, ";
        createStatement += COLUMN_SINGERS + " TEXT, ";
        createStatement += COLUMN_YEAR + " INTEGER, ";
        createStatement += COLUMN_STARS + " INTEGER";
        createStatement += ")";

        db.execSQL(createStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONG);
        onCreate(db);
    }

    public long insert(Song song) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, song.getTitle());
        values.put(COLUMN_SINGERS, song.getSingers());
        values.put(COLUMN_YEAR, song.getYear());
        values.put(COLUMN_STARS, song.getStars());

        long result = db.insert(TABLE_SONG, null, values);
        db.close();

        return result;
    }
    public int updateSong(Song data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SINGERS, data.getSingers());
        values.put(COLUMN_TITLE, data.getTitle());
        values.put(COLUMN_YEAR, data.getYear());
        values.put(COLUMN_STARS, data.getStars());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_SONG, values, condition, args);
        db.close();
        return result;
    }

    public int deleteNote(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_SONG, condition, args);
        db.close();
        return result;
    }

    public ArrayList<Song> retrieveWithConditions(int filterStars, int filterYear) {
        ArrayList<Song> songs = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] queryingColumns = {COLUMN_ID, COLUMN_TITLE, COLUMN_SINGERS, COLUMN_YEAR, COLUMN_STARS};
        Cursor cursor;
        if (filterStars == NO_FILTERING_KEY && filterYear == NO_YEAR_FILTERING_KEY) {
            cursor = db.query(TABLE_SONG, queryingColumns, null, null,
                    null, null, null, null);
        }
        else {
            boolean fYear = filterYear != NO_YEAR_FILTERING_KEY;
            boolean fStar = filterStars != NO_FILTERING_KEY;
            String conditions;
            String[] args;

            if (fYear && fStar) {
                conditions = COLUMN_STARS + " = ?" + " AND " + COLUMN_YEAR + " = ?";
                args = new String[]{"" + filterStars, "" + filterYear};
            }
            else if (fYear) {
                conditions =  COLUMN_YEAR + " = ?";
                args = new String[]{"" + filterYear};
            }
            else {
                conditions =  COLUMN_STARS + " = ?";
                args = new String[]{"" + filterStars};
            }
            cursor = db.query(TABLE_SONG, queryingColumns, conditions, args,
                    null, null, null, null);
        }
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singers = cursor.getString(2);
                int year = cursor.getInt(3);
                int stars = cursor.getInt(4);
                Song song = new Song(id, title, singers, year, stars);
                songs.add(song);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;
    }

    // use this for list WITH filtering of stars, pass star amount in.
    public ArrayList<Song> retrieveWithConditions(int filterStars) {
        return retrieveWithConditions(filterStars, NO_YEAR_FILTERING_KEY);
    }

    // use this for list without filtering of 5 stars.
    public ArrayList<Song> retrieveAll() {
        return retrieveWithConditions(NO_FILTERING_KEY, NO_YEAR_FILTERING_KEY);
    }

    public ArrayList<String> retrieveAllYears() {
        ArrayList<String> years = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] queryingColumns = {COLUMN_YEAR};
        Cursor cursor = db.query(true, TABLE_SONG, queryingColumns, null, null,
                null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int year = cursor.getInt(0);
                years.add(year + "");
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        years.add(0, ALL_YEARS_OPTION);
        return years;
    }
}

