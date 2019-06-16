package com.example.ejemploroomexamen;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton buttonAddTask;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview_medida);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        buttonAddTask = findViewById(R.id.floating_button_add);
        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddMedidaActivity.class);
                startActivity(intent);
            }
        });
        getMedidas();
    }

    private void getMedidas() {
        class GetMedidas extends AsyncTask<Void, Void, List<Medida>> {

            @Override
            protected List<Medida> doInBackground(Void... voids) {
                List<Medida> medidaList = DatabaseClientMedida
                        .getInstance(getApplicationContext())
                        .getAppDatabaseMedia()
                        .medidaDAO()
                        .getAll();
                return medidaList;
            }

            @Override
            protected void onPostExecute(List<Medida> medida) {
                super.onPostExecute(medida);
               MedidasAdapter adapter = new MedidasAdapter(MainActivity.this, medida);
                recyclerView.setAdapter(adapter);
            }
        }

        GetMedidas gt = new GetMedidas();
        gt.execute();
    }
}
