package com.example.login;
import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.login.DatabaseHelper;
import com.example.login.R;

public class MainActivity extends AppCompatActivity {

    private LinearLayout llStudents;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llStudents = findViewById(R.id.ll_students);
        databaseHelper = new DatabaseHelper(this);

        displayRegisteredStudents();
    }

    private void displayRegisteredStudents() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_USERS, null);

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No registered students found", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
                @SuppressLint("Range") String usn = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USN));

                // Create a TextView for each registered student
                TextView textView = new TextView(this);
                textView.setText("Name: " + name + "\nUSN: " + usn);
                textView.setTextSize(16);

                textView.setTextColor(Color.WHITE);


                // Add the TextView to the LinearLayout
                llStudents.addView(textView);
            }
        }

        cursor.close();
        db.close();
    }
}
