package com.example.booksapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private final Context mContext;
    private final ArrayList<BookModal> books;
    private final LayoutInflater mLayoutInflater;

    public BookAdapter(Context mContext, ArrayList<BookModal> books) {
        this.mContext = mContext;
        this.books = books;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = mLayoutInflater.inflate(R.layout.item_list, parent, false);
        return new ViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookModal book = books.get(position);
        holder.mBookTitle.setText(book.title);
        String authors = book.authors;
        holder.mBookAuthors.setText(authors);
        holder.mBookPublisher.setText(book.publisher);
        holder.mBookPublishedDate.setText(book.publishedDate);
        holder.mCurrentPosition = position;
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mBookTitle;
        public final TextView mBookAuthors;
        public final TextView mBookPublisher;
        public final TextView mBookPublishedDate;
        public int mCurrentPosition;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mBookTitle = itemView.findViewById(R.id.book_title);
            mBookAuthors = itemView.findViewById(R.id.book_authors);
            mBookPublisher = itemView.findViewById(R.id.book_publisher);
            mBookPublishedDate = itemView.findViewById(R.id.book_published_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BookModal selectedBook = books.get(mCurrentPosition);
                    Intent intent = new Intent(mContext, BookDetails.class);
                    intent.putExtra("Book", selectedBook);
                    mContext.startActivity(intent);
                }
            });

        }

    }
}
