package com.example.ccprmobile;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    String[] equipmentsList = {"SG-01 (Silo grão)", "EX-01 (Exaustor do silo SG-01)", "EX-02 (Exaustor do silo SG-01)", "EX-03 (Exaustor do silo SG-01)", "EX-04 (Exaustor do silo SG-01)",
    "RV-01 (Rosca abaixo do silo SG-01)", "RPS-01 (Gaveta Abaixo Do RV-01)", "TC-25", "TC-26", "VD-51", "VD-21", "Tc-03", "TC-12 (Acima do silo SG-01)", "TC-11",
     "VD-29", "VD-25", "EL-05", "EL-04", "VD-42", "VD-43", "TC-33", "TC-36", "RT-01", "RPS-C", "RT-01B", "ELI-01", "ELI-01", "THI-01", "THI-02", "MPL-01B", "MPL-01A",
     "MPL-01D", "MPL-01C", "VD-15", "VD-16", "VD-14", "VD-11", "VD-12", "VD-13", "VD-20", "VD-09", "VD-56", "VD-55", "VD-57A", "VD-57B", "VD-57C","TC-34", "TC-37", "TC-35",
      "TC-38", "TC-39", "TC-40", "TC-41", "TC-42", "TC-43", "TC-44", "TC-45", "TC-46", "TC-01", "TC-05", "EL-03", "EL-01", "MOEGA1", "MOEGA2", "VENT-01 (Exaustor da moega)",
       "RM-01 (Gaveta da MOEGA1)",  "RM-02 (Gaveta da MOEGA1)", "RM-03 (Gaveta da MOEGA1)", "RM-04 (Gaveta da MOEGA1)", "RM-05 (Gaveta da MOEGA 2)", "RM-06 (Gaveta da MOEGA 2)",
            "RM-07 (Gaveta da MOEGA 2)", "RM-08 (Gaveta da MOEGA 2)", "PRF-01A", "PRF-01B (Exaustor da PRF-01A)", "RP-34 (Abaixo do TC-35)", "RP-35 (Abaixo do TC-38)", "RP-08 (Abaixo do TC-39)",
            "RP-09 (Abaixo do TC-39)", "RP-10 (Abaixo do TC-39)", "RP-11 (Abaixo do TC-40)", "RP-12 (Abaixo do TC-40)", "RP-13 (Abaixo do TC-40)", "RP-15 (Abaixo do TC-41)", "RP-16 (Abaixo do TC-41)", "RP-17 (Abaixo do TC-41)",
            "RP-19 (Abaixo do TC-42)", "RP-20 (Abaixo do TC-42)", "RP-21 (Abaixo do TC-42)", "RP-22 (Gaveta do silo de armazenamento SA-01)", "RP-22A (Gaveta do silo de armazenamento SA-01)",
            "RP-23 (Gaveta do silo de armazenamento SA-02)","RP-23A (Gaveta do silo de armazenamento SA-02)", "RP-24 (Gaveta do silo de armazenamento SA-03)","RP-24A (Gaveta do silo de armazenamento SA-03)",
            "RP-25 (Gaveta do silo de armazenamento SA-04)","RP-25A (Gaveta do silo de armazenamento SA-04)", "RP-26 (Gaveta do silo de armazenamento SA-05)","RP-26A (Gaveta do silo de armazenamento SA-05)",
            "RP-27 (Gaveta do silo de armazenamento SA-06)","RP-27A (Gaveta do silo de armazenamento SA-06)","RP-28 (Gaveta do silo de armazenamento SA-07)","RP-28A (Gaveta do silo de armazenamento SA-08)","RP-29 (Gaveta do silo de armazenamento SA-08)","RP-29A (Gaveta do silo de armazenamento SA-08)",
            "RP-30 (Gaveta do silo de armazenamento SA-09)","RP-30A (Gaveta do silo de armazenamento SA-09)",  "RP-31 (Gaveta do silo de armazenamento SA-10)","RP-31A (Gaveta do silo de armazenamento SA-10)",
            "RP-32 (Gaveta do silo de armazenamento SA-11)",   "RP-32A (Gaveta do silo de armazenamento SA-11)",  "RP-33 (Gaveta do silo de armazenamento SA-12)", "RP-33A (Gaveta do silo de armazenamento SA-12)",


    };
    ArrayAdapter<String> arrayAdapter;
    SearchView searchView;
    String searchQuery = ""; // Armazena o estado da pesquisa

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configuração da Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Configuração da ListView
        listView = findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, equipmentsList);
        listView.setAdapter(arrayAdapter);

        // Restaurar estado da pesquisa
        if (savedInstanceState != null) {
            searchQuery = savedInstanceState.getString("searchQuery", "");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search);

        if (searchItem != null) {
            searchView = new SearchView(this);
            searchItem.setActionView(searchView);
            searchView.setQueryHint("Buscar por equipamento");

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    searchQuery = query;
                    hideKeyboard();
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    arrayAdapter.getFilter().filter(newText);
                    return false;
                }
            });

            // Restaurar a pesquisa após a rotação da tela
            if (!searchQuery.isEmpty()) {
                searchView.setQuery(searchQuery, false);
            }
        }
        return super.onCreateOptionsMenu(menu);
    }

    // Método para esconder o teclado após submissão da pesquisa
    private void hideKeyboard() {
        if (searchView != null) {
            searchView.clearFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("searchQuery", searchQuery);
    }
}
