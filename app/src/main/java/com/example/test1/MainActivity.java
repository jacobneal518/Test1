package com.example.test1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    /**
     * Buttons
     */
    Button insertButton;
    Button queryButton;
    Button updateButton;
    Button deleteButton;
    Button nextButton;
    Button previousButton;

    /**
     * Edit Texts
     */
    EditText popName;
    EditText popNumber;

    EditText popType;
    EditText fandom;
    EditText popOn;
    EditText ultimate;
    EditText price;


    /**
     * Text Views
     */
    TextView idTv;
    TextView popNameTV;
    TextView popNumberTV;
    TextView popTypeTV;
    TextView fandomTV;
    TextView popOnTV;
    TextView ultimateTV;
    TextView priceTV;

    Cursor mCursor;

    Uri funkoURI = FunkoPOPProvider.CONTENT_URI;

    //Listeners

    /**
     * updates currently selected row
     */
    View.OnClickListener updateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ContentValues mUpdateValues = new ContentValues();

            mUpdateValues.put(FunkoPOPProvider.COLUMN_POP_NAME, popName.getText().toString().trim());
            mUpdateValues.put(FunkoPOPProvider.COLUMN_POP_NUMBER, popNumber.getText().toString().trim());
            mUpdateValues.put(FunkoPOPProvider.COLUMN_POP_TYPE, popType.getText().toString().trim());
            mUpdateValues.put(FunkoPOPProvider.COLUMN_FANDOM, fandom.getText().toString().trim());
            mUpdateValues.put(FunkoPOPProvider.COLUMN_ON, popOn.getText().toString().trim());
            mUpdateValues.put(FunkoPOPProvider.COLUMN_ULTIMATE, ultimate.getText().toString().trim());
            mUpdateValues.put(FunkoPOPProvider.COLUMN_PRICE, price.getText().toString().trim());

            String mSelectionClause = FunkoPOPProvider.COLUMN_POP_NAME + " = ? "
                + " AND " + FunkoPOPProvider.COLUMN_POP_NUMBER + " = ? ";

            String[] mSelectionArgs = { popNameTV.getText().toString().trim(),
                    popNumberTV.getText().toString().trim() };

            int numRowsUpdates= getContentResolver().update(FunkoPOPProvider.CONTENT_URI, mUpdateValues,
                    mSelectionClause, mSelectionArgs);

            refreshDatabase();

        }
    };


    /**
     * deletes currently selected row from db
     */
    View.OnClickListener deleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String mSelectionClause = FunkoPOPProvider.COLUMN_POP_NAME + " = ? " + " AND " +
                    FunkoPOPProvider.COLUMN_POP_NUMBER + " = ? ";

            String[] mSelectionArgs = { popNameTV.getText().toString().trim(),
                    popNumberTV.getText().toString().trim() };

            int mRowsDeleted = getContentResolver().delete(FunkoPOPProvider.CONTENT_URI, mSelectionClause,
                    mSelectionArgs);

            refreshDatabase();

        }
    };

    /**
     * inserts new row into db
     */
    View.OnClickListener insertListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            ContentValues mNewValues = new ContentValues();

            mNewValues.put(FunkoPOPProvider.COLUMN_POP_NAME, popName.getText().toString().trim());
            mNewValues.put(FunkoPOPProvider.COLUMN_POP_NUMBER, popNumber.getText().toString().trim());
            mNewValues.put(FunkoPOPProvider.COLUMN_POP_TYPE, popType.getText().toString().trim());
            mNewValues.put(FunkoPOPProvider.COLUMN_FANDOM, fandom.getText().toString().trim());
            mNewValues.put(FunkoPOPProvider.COLUMN_ON, popOn.getText().toString().trim());
            mNewValues.put(FunkoPOPProvider.COLUMN_ULTIMATE, ultimate.getText().toString().trim());
            mNewValues.put(FunkoPOPProvider.COLUMN_PRICE, price.getText().toString().trim());

            getContentResolver().insert(FunkoPOPProvider.CONTENT_URI, mNewValues);

            refreshDatabase();
        }
    };

    View.OnClickListener queryListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mCursor != null) {
                if (mCursor.getCount() > 0) {
                    mCursor.moveToNext();
                    setViews();
                }
            }
        }
    };

    /**
     * calls the previous button to iterate -1
     */
    View.OnClickListener previousListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mCursor != null) {
                if (!mCursor.moveToPrevious()) {
                    mCursor.moveToLast();
                }
                setViews();
            }
            else{
                Log.i("previousListener", "Cursor was null");
            }

        }
    };

    /**
     * calls the next button to iterate +1
     */
    View.OnClickListener nextListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (mCursor != null) {
                if (!mCursor.moveToNext()) {
                    mCursor.moveToFirst();
                }
                setViews();
            }
            else{
                Log.i("nextListener", "Cursor was null");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Edit Texts
         */
        popName = findViewById(R.id.popName);
        popNumber = findViewById(R.id.popNumber);
        popType = findViewById(R.id.popType);
        fandom = findViewById(R.id.fandom);
        popOn = findViewById(R.id.popOn);
        ultimate = findViewById(R.id.ultimate);
        price = findViewById(R.id.price);

        /**
         * Buttons
         */
        insertButton = findViewById(R.id.insertButton);
        queryButton = findViewById(R.id.queryButton);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);


        /**
         * Text Views
         */
        idTv = findViewById(R.id.unique_id);
        popNameTV = findViewById(R.id.popNameTV);
        popNumberTV = findViewById(R.id.popNumberTV);
        popTypeTV = findViewById(R.id.popTypeTV);
        fandomTV = findViewById(R.id.fandomTV);
        popOnTV = findViewById(R.id.popOnTV);
        ultimateTV = findViewById(R.id.ultimateTV);
        priceTV = findViewById(R.id.priceTV);


        /**
         * Buttons part 2, electric boogaloo
         */
        nextButton = findViewById(R.id.nextButton);
        previousButton = findViewById(R.id.previousButton);


        insertButton.setOnClickListener(insertListener);

        updateButton.setOnClickListener(updateListener);

        deleteButton.setOnClickListener(deleteListener);

        queryButton.setOnClickListener(queryListener);

        previousButton.setOnClickListener(previousListener);

        nextButton.setOnClickListener(nextListener);

        mCursor = getContentResolver().query(funkoURI, FunkoPOPProvider.dbColumns, null, null, null);


    }

    /**
     * clears out user input and refreshes db
     */
    public void refreshDatabase(){
        clear();
        mCursor = getContentResolver().query(funkoURI, FunkoPOPProvider.dbColumns, null, null, null);
    }

    /**
     * Loads values from db
     */
    private void setViews() {
        try {
            if(mCursor != null){
                idTv.setText(mCursor.getString(0));

                String text1 = mCursor.getString(1) + " ";
                popNameTV.setText(text1);

                popNumberTV.setText(mCursor.getString(2));

                String text2 = mCursor.getString(3) + " ";
                popTypeTV.setText(text2);

                String text3 = mCursor.getString(4) + " ";
                fandomTV.setText(text3);

                popOnTV.setText(mCursor.getString(5));

                String text4 = mCursor.getString(6) + " ";
                ultimateTV.setText(text4);

                priceTV.setText(mCursor.getString(7));

            }
        }
        catch (CursorIndexOutOfBoundsException e){
            Toast.makeText(this, "No More DB Entries", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Clears out input
     */
    private void clear() {
        popName.setText("");
        popNumber.setText("");
        popType.setText("");
        fandom.setText("");
        popOn.setText("");
        ultimate.setText("");
        price.setText("");

        idTv.setText("");
        popNameTV.setText("");
        popNumberTV.setText("");
        popTypeTV.setText("");
        fandomTV.setText("");
        popOnTV.setText("");
        ultimateTV.setText("");
        priceTV.setText("");

        mCursor = null;
    }
}