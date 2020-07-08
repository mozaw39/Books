package com.example.booksapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.booksapp.databinding.ActivityBookDetailsBinding;

public class BookDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        BookModal book = getIntent().getParcelableExtra("Book");
        ActivityBookDetailsBinding binding = DataBindingUtil.setContentView(this,
                R.layout.activity_book_details);
        binding.setBook(book);



    }

    private void displayBookInfo(BookModal book) {
        TextView title = findViewById(R.id.book_title);
        title.setText(book.title);
        TextView subtitle = findViewById(R.id.book_subtitle);
        subtitle.setText(book.subTitle);
        TextView authors = findViewById(R.id.book_authors);
        authors.setText(book.authors.toString().substring(1, book.authors.toString().length() - 1));
        TextView publisher = findViewById(R.id.book_publisher);
        publisher.setText(book.publisher);
        TextView published_date = findViewById(R.id.book_published_date);
        published_date.setText(book.publishedDate);
    }
}
