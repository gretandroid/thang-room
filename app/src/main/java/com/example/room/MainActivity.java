package com.example.room;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.room.database.AppDatabase;
import com.example.room.database.PersonneDao;
import com.example.room.database.TestData;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // all Room queries must be invoked in another thread
        Executor executor = Executors.newSingleThreadExecutor();

        // init database and dao
        AppDatabase database = AppDatabase.getInstance(this);
        PersonneDao dao = database.personneDao();

        executor.execute(() -> {
                // manipulate with db
                dao.insertAll(TestData.getAll());
        });
        Log.d("App", "Before getAll");
        executor.execute(() -> {
            Log.d("App", dao.getAll().toString());
        });
    }
}