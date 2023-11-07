package com.example.test1;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FunkoPOPProvider extends ContentProvider {


    public final static String DBNAME = "FunkoPopDB";

    protected static final class MainDatabaseHelper extends SQLiteOpenHelper {
        MainDatabaseHelper(Context context) {
            super(context, DBNAME, null, 1);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(SQL_CREATE_MAIN);
        }
        @Override
        public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        }
    };


    public final static String TABLE_NAMESTABLE = "Names";

    public final static String COLUMN_ID = "_ID";
    public final static String COLUMN_POP_NAME = "POP_NAME";
    public final static String COLUMN_POP_NUMBER = "POP_NUMBER";

    public final static String COLUMN_POP_TYPE = "POP_TYPE";
    public final static String COLUMN_FANDOM = "FANDOM";

    public final static String COLUMN_ON = "POP_ON";
    public final static String COLUMN_ULTIMATE = "ULTIMATE";

    public final static String COLUMN_PRICE = "PRICE";

    public final static String[] dbColumns = {
            COLUMN_ID,
            COLUMN_POP_NAME,
            COLUMN_POP_NUMBER,
            COLUMN_POP_TYPE,
            COLUMN_FANDOM,
            COLUMN_ON,
            COLUMN_ULTIMATE,
            COLUMN_PRICE
    };

    public static final String AUTHORITY = "com.example.provider";
    public static final Uri CONTENT_URI = Uri.parse(
            "content://" + AUTHORITY +"/" + TABLE_NAMESTABLE);

    private static UriMatcher sUriMatcher;

    private MainDatabaseHelper mOpenHelper;

    private static final String SQL_CREATE_MAIN = "CREATE TABLE " +
            TABLE_NAMESTABLE +  // Table's name
            "(" +               // The columns in the table
            COLUMN_ID +
            " INTEGER PRIMARY KEY, " +
            COLUMN_POP_NAME +
            " TEXT," +
            COLUMN_POP_NUMBER +
            " INTEGER," +
            COLUMN_POP_TYPE +
            " TEXT," +
            COLUMN_FANDOM +
            " TEXT," +
            COLUMN_ON +
            " INTEGER," +
            COLUMN_ULTIMATE +
            " TEXT," +
            COLUMN_PRICE +
            " REAL" +
            ")";


    public FunkoPOPProvider() {

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return mOpenHelper.getWritableDatabase().delete(TABLE_NAMESTABLE, selection, selectionArgs);
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        String popName = values.getAsString(COLUMN_POP_NAME).trim();
        String popNumber = values.getAsString(COLUMN_POP_NUMBER).trim();
        String popType = values.getAsString(COLUMN_POP_TYPE).trim();
        String popFandom = values.getAsString(COLUMN_FANDOM).trim();
        String popOn = values.getAsString(COLUMN_ON).trim();
        String popUltimate = values.getAsString(COLUMN_ULTIMATE).trim();
        String popPrice = values.getAsString(COLUMN_PRICE).trim();




        if (popName.equals("") || popNumber.equals("") || popType.equals("") || popFandom.equals("") || popOn.equals("") || popUltimate.equals("") || popPrice.equals(""))
            return null;


        long id = mOpenHelper.getWritableDatabase().insert(TABLE_NAMESTABLE, null, values);

        return Uri.withAppendedPath(CONTENT_URI, "" + id);
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new MainDatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        return mOpenHelper.getReadableDatabase().query(TABLE_NAMESTABLE, projection, selection, selectionArgs,
                null, null, sortOrder);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return mOpenHelper.getWritableDatabase().update(TABLE_NAMESTABLE, values, selection, selectionArgs);
    }
}
