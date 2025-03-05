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
    String[] equipmentsList = {"Tc-105 (Quinto Piso)", "Sen-112 (Sexto Piso)", "Moinho 101 (Quarto piso)"};
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
