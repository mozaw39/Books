package com.example.booksapp;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookModal implements Parcelable {
    public String id;
    public String title;
    public String subTitle;
    public String authors;
    public String publisher;
    public String publishedDate;
    public String description;
    public String thumbnail;
    public BookModal(String id, String title, String subTitle, ArrayList<String> authors, String publisher, String publishedDate, String description, String thumbnail) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.authors = authors.toString().substring(1, authors.toString().length()-1);
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    protected BookModal(Parcel in) {
        id = in.readString();
        title = in.readString();
        subTitle = in.readString();
        authors = in.readString();
        publisher = in.readString();
        publishedDate = in.readString();
        description = in.readString();
        thumbnail = in.readString();
    }

    public static final Creator<BookModal> CREATOR = new Creator<BookModal>() {
        @Override
        public BookModal createFromParcel(Parcel in) {
            return new BookModal(in);
        }

        @Override
        public BookModal[] newArray(int size) {
            return new BookModal[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(subTitle);
        dest.writeString(authors);
        dest.writeString(publisher);
        dest.writeString(publishedDate);
        dest.writeString(description);
        dest.writeString(thumbnail);
    }

    @BindingAdapter({"android:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl){
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.ic_book_black_24dp)
                .into(view);

    }
}
