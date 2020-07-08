package com.example.booksapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = findViewById(R.id.pb_loading);
        URL bookURL = ApiUtil.buildURL("java");
        new BookQueryClass().execute(bookURL);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int selectedId = item.getItemId();
        if(selectedId == R.id.action_search){

        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.book_list_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        URL url = ApiUtil.buildURL(query);
        new BookQueryClass().execute(url);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    public class BookQueryClass extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            return ApiUtil.getJson(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            mProgressBar.setVisibility(View.INVISIBLE);
            displayItems(result);
        }

        @Override
        protected void onPreExecute() {
            //starting the progressBar
            mProgressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }
    }

    private void displayItems(String result) {
        //TextView tv_response = findViewById(R.id.response);
        if(result == null){
            //tv_response.setText(getString(R.string.Data_Error));
        }
        else {
            ArrayList<BookModal> books = ApiUtil.getBootksFromJson(result);
            final RecyclerView recyclerBooks = findViewById(R.id.recycle_books);
            final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
            recyclerBooks.setLayoutManager(linearLayoutManager);

            final BookAdapter bookAdapter = new BookAdapter(MainActivity.this, books);
            recyclerBooks.setAdapter(bookAdapter);
        }
    }
}
