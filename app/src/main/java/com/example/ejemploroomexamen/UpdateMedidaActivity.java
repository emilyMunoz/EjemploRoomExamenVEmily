package com.example.ejemploroomexamen;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class UpdateMedidaActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextFecha, editTextGrasa, editTextMasaMuscular, editTextPeso, editTextEdadMetabolica;

    private static final String CERO = "0";
    private static final String BARRA = "/";

    private static final String DOS_PUNTOS = ":";

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la fecha
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);


    private EditText etFecha;
    Button ibObtenerFecha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_medida);

        editTextFecha = findViewById(R.id.editTextFecha);
        editTextGrasa = findViewById(R.id.editTextGrasa);
        editTextMasaMuscular = findViewById(R.id.editTextMasaMuscular);
        editTextPeso = findViewById(R.id.editTextPeso);
        editTextEdadMetabolica = findViewById(R.id.editTextEdadMetabolica);

        //Widget ImageButton del cual usaremos el evento clic para obtener la fecha
        ibObtenerFecha = (Button) findViewById(R.id.ib_obtener_fecha);
        //Evento setOnClickListener - clic
        ibObtenerFecha.setOnClickListener(this);

        final Medida medida = (Medida) getIntent().getSerializableExtra("medida");

        loadMedida(medida);

        findViewById(R.id.button_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_LONG).show();
                updateMedida(medida);
            }
        });

        findViewById(R.id.button_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateMedidaActivity.this);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteMedida(medida);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog ad = builder.create();
                ad.show();
            }
        });
    }

    private void loadMedida(Medida medida) {
        editTextFecha.setText(medida.getFecha());
        editTextGrasa.setText(Integer.toString(medida.getGrasa()));
        editTextMasaMuscular.setText(Integer.toString(medida.getMasaMuscular()));
        editTextPeso.setText(Integer.toString(medida.getPeso()));
        editTextEdadMetabolica.setText(Integer.toString(medida.getEdadMetabolica()));

    }

    private void updateMedida(final Medida medida) {

        final String fecha = editTextFecha.getText().toString().trim();
        final int grasa = Integer.parseInt(editTextGrasa.getText().toString().trim());
        final int masaMuscular = Integer.parseInt(editTextMasaMuscular.getText().toString().trim());
        final int peso = Integer.parseInt(editTextPeso.getText().toString().trim());
        final int edadMetabolica = Integer.parseInt(editTextEdadMetabolica.getText().toString().trim());

        if (fecha.isEmpty()) {
            editTextFecha.setError("Date required");
            editTextFecha.requestFocus();
            return;
        }

        if (grasa == 0) {
            editTextGrasa.setError("grease required");
            editTextGrasa.requestFocus();
            return;
        }

        if (masaMuscular == 0) {
            editTextMasaMuscular.setError("muscle mass required");
            editTextMasaMuscular.requestFocus();
            return;
        }

        if (peso == 0) {
            editTextPeso.setError("weight required");
            editTextPeso.requestFocus();
            return;
        }

        if (edadMetabolica == 0) {
            editTextEdadMetabolica.setError("metabolic age required");
            editTextEdadMetabolica.requestFocus();
            return;
        }

        class UpdateMedida extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                medida.setFecha(fecha);
                medida.setGrasa(grasa);
                medida.setMasaMuscular(masaMuscular);
                medida.setPeso(peso);
                medida.setEdadMetabolica(edadMetabolica);

                //adding to database
                DatabaseClientMedida.getInstance(getApplicationContext()).getAppDatabaseMedia()
                        .medidaDAO()
                        .update(medida);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(UpdateMedidaActivity.this, MainActivity.class));
            }
        }

        UpdateMedida ut = new UpdateMedida();
        ut.execute();
    }


    private void deleteMedida(final Medida medida) {
        class DeleteMedida extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClientMedida.getInstance(getApplicationContext()).getAppDatabaseMedia()
                        .medidaDAO()
                        .delete(medida);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(UpdateMedidaActivity.this, MainActivity.class));
            }
        }

        DeleteMedida dt = new DeleteMedida();
        dt.execute();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_obtener_fecha:
                obtenerFecha();
                break;
        }
    }
    private void obtenerFecha(){
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
                editTextFecha.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);
            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        },anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();
    }
}
