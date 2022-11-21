package fr.uha.ayoub.dut.booky;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.widget.Adapter;
import android.widget.BaseAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FetchBook extends AsyncTask<Void, Void, String> {

    public DatabaseHelper database ;

    public BookAdapter adapter ;
    String data = "" ;

    String isbn ;

    ;
    public Book booky ;


    public FetchBook(String isbn , BookAdapter adapter , DatabaseHelper db ){

        this.isbn=isbn ;
        this.adapter=adapter ;
        this.database = db ;

    }
    @Override
    protected String doInBackground(Void... voids) {
        try{
            URL url  = new URL("https://www.googleapis.com/books/v1/volumes?q=isbn:"+ this.isbn ) ;
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection() ;
            InputStream inputStream = httpURLConnection.getInputStream() ;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)) ;
            String line = "" ;
            while (line !=null){line=bufferedReader.readLine();
                data = data + line ;

            }


        } catch (MalformedURLException e){
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data ;
    }

    @Override
    protected void onPostExecute(String s) {


        super.onPostExecute(s);
        try {
            // Convert the response into a JSON object.
            JSONObject jsonObject = new JSONObject(s);
            // Get the JSONArray of book items.
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            // Initialize iterator and results fields.
            int i = 0;
            String title = null;
            String authors = null;
            String releaseDate = null ;
            String description = null ;


            while (i < itemsArray.length() || (authors == null && title == null)) {
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");
                try {
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getString("authors");
                    releaseDate = volumeInfo.getString("publishedDate");
                    description = volumeInfo.getString("description");
                } catch (Exception e){
                    e.printStackTrace();
                }

                i++;
            }


            if (title != null && authors != null){

                Book book = new Book(isbn , title ,authors.replaceAll("\"","")
                        .replaceAll("[^\\p{Alpha}]+","") , releaseDate ,description) ;
                BookListActivity.titleshow.setText(title);
                addBook(book,adapter);
                insert(book,database );
                BookListActivity.titleshow.setText(title);



            } else if (title != null && authors != null && description== null)  {
                Book book = new Book(isbn , title ,authors.replaceAll("\"","")
                        .replaceAll("[^\\p{Alpha}]+","") , releaseDate ,"No Description available !") ;
                BookListActivity.titleshow.setText(title);
                addBook(book,adapter);
                insert(book,database );
                BookListActivity.titleshow.setText(title);

            }

        } catch (Exception e){
            // If onPostExecute does not receive a proper JSON string,

            e.printStackTrace();
        }



    }



    public void addBook(Book book , BookAdapter adapter) {
        adapter.add(book) ;
        adapter.notifyDataSetChanged();


    }
    public  void  insert (Book book , DatabaseHelper db){
        db.insertData(book.getIsbn(),book.getTitle(),book.getAuthor(),book.getReleaseDate(),book.getDescription()) ;

    }

}
