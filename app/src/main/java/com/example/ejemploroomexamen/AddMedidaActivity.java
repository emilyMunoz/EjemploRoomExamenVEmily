package com.example.ejemploroomexamen;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class AddMedidaActivity extends AppCompatActivity implements View.OnClickListener {

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
        setContentView(R.layout.activity_add_medida);

        editTextFecha = findViewById(R.id.editTextFecha);
        editTextGrasa = findViewById(R.id.editTextGrasa);
        editTextMasaMuscular = findViewById(R.id.editTextMasaMuscular);
        editTextPeso = findViewById(R.id.editTextPeso);
        editTextEdadMetabolica = findViewById(R.id.editTextEdadMetabolica);

        findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveMedida();
            }
        });

        //Widget ImageButton del cual usaremos el evento clic para obtener la fecha
        ibObtenerFecha = (Button) findViewById(R.id.ib_obtener_fecha);
        //Evento setOnClickListener - clic
        ibObtenerFecha.setOnClickListener(this);

        findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveMedida();
            }
        });
    }

    private void saveMedida() {
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

        class SaveMedida extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //creating a task
                Medida medida = new Medida();
                medida.setFecha(fecha);
                medida.setGrasa(grasa);
                medida.setMasaMuscular(masaMuscular);
                medida.setPeso(peso);
                medida.setEdadMetabolica(edadMetabolica);

                //adding to database
                DatabaseClientMedida.getInstance(getApplicationContext()).getAppDatabaseMedia()
                        .medidaDAO()
                        .insert(medida);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        SaveMedida st = new SaveMedida();
        st.execute();
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
