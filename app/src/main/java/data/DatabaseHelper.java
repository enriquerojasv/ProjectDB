package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "CommentDB.db";

    private final String COMMENT_TABLE = "COMMENT";
    private final String COMMENT_ID = "_id";
    private final String COMMENT_NAME = "name";
    private final String COMMENT_TEXT = "commentary";

    private String createTable = "CREATE TABLE IF NOT EXISTS " +
            COMMENT_TABLE + "(" +
            COMMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COMMENT_NAME + " TEXT," +
            COMMENT_TEXT + " TEXT)";

    private String selectedID;

    SQLiteDatabase database;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + COMMENT_TABLE);
        onCreate(db);
    }

    public void insertData(String name, String commentary) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COMMENT_NAME, name);
        values.put(COMMENT_TEXT, commentary);

        db.insert(COMMENT_TABLE, null, values);
        db.close();
    }

    public void spinner(Context context, Spinner spinner) {
        Cursor cur = database.rawQuery("SELECT * FROM " + COMMENT_TABLE, null);

        String[] from = new String[]{COMMENT_NAME};
        int[] to = new int[]{android.R.id.text1};

        SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(context,
                android.R.layout.simple_spinner_item, cur, from, to, 0);

        spinner.setAdapter(mAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                selectedID = String.valueOf(id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public String getData() {
        Cursor cursor = database.rawQuery("SELECT " + COMMENT_TEXT + " FROM " + COMMENT_TABLE +
                " WHERE " + COMMENT_ID + " = " + selectedID, null);

        cursor.moveToFirst();
        String data = cursor.getString(0);
        cursor.close();
        return data;
    }

    public void deleteData() {
        database.delete(COMMENT_TABLE, COMMENT_ID + "=" + selectedID, null);
    }
}
