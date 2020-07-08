package com.example.booksapp;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class ApiUtil {
    public static final String BASE_API_URL =
            "https://www.googleapis.com/books/v1/volumes";
    private static final String QUERY_PARAMETER_KEY = "q";
    private static final String KEY = "key";
    private static final String API_KEY = "";

    public static URL buildURL(String title){
        //String fullURL = BASE_API_URL + "?q=" + title;
        Uri uri = Uri.parse(BASE_API_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAMETER_KEY,title)
                //.appendQueryParameter(KEY,API_KEY)
                .build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return url;
    }

    public static String getJson(URL url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream stream = connection.getInputStream();
            // convert the stream into string
            Scanner scanner = new Scanner(stream);
            scanner.useDelimiter("\\A"); // read everything
            return (scanner.hasNext()?scanner.next():null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<BookModal> getBootksFromJson(String json){
        ArrayList<BookModal> books = new ArrayList<>();
        final String ID = "id";
        final String TITLE = "title";
        final String SUBTITLE = "subtitle";
        final String AUTHORS = "authors";
        final String PUBLISHER = "publisher";
        final String PUBLISHED_DATE = "publishedDate";
        final String ITEMS = "items";
        final String VOLUMEINFO = "volumeInfo";
        final String DESCRIPTION = "description";
        final String IMAGELINKS = "imageLinks";
        final String THUMBNAIL = "thumbnail";
        try {
            JSONObject jsonBooks = new JSONObject(json);
            JSONArray arrayBooks = jsonBooks.getJSONArray(ITEMS);
            int nbBooks = arrayBooks.length();
            for(int i=0; i<nbBooks; i++){
                JSONObject bookJSON = arrayBooks.getJSONObject(i);
                JSONObject volumeInfoJSON = bookJSON.getJSONObject(VOLUMEINFO);
                int nbAuthors = volumeInfoJSON.getJSONArray(AUTHORS).length();
                ArrayList<String> authors = new ArrayList<>();
                for (int j = 0; j < nbAuthors; j++) {
                    authors.add(volumeInfoJSON.getJSONArray(AUTHORS).getString(j));
                }
                JSONObject imageLinksJSON = volumeInfoJSON.getJSONObject(IMAGELINKS);
                BookModal book = new BookModal(bookJSON.getString(ID),
                        volumeInfoJSON.getString(TITLE),
                        volumeInfoJSON.isNull(SUBTITLE)?"":volumeInfoJSON.getString(SUBTITLE),
                        authors,
                        volumeInfoJSON.getString(PUBLISHER),
                        volumeInfoJSON.getString(PUBLISHED_DATE),
                        volumeInfoJSON.isNull(DESCRIPTION)?"":volumeInfoJSON.getString(DESCRIPTION),
                        imageLinksJSON.getString(THUMBNAIL)
                        );
                books.add(book);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return books;
    }
}
