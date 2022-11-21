package fr.uha.ayoub.dut.booky;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BookAdapter extends BaseAdapter {


    private Context context ;
    private ArrayList<Book> bookList = new ArrayList<Book>() ;


    public  BookAdapter(Context context , ArrayList<Book> bookList ){
        this.context=context ;
        this.bookList=bookList ;


    }



    @Override
    public int getCount() {
        return this.bookList.size() ;
    }

    @Override
    public Object getItem(int i) {
        return this.bookList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public boolean add(Book book) {
        return bookList.add(book);
    }


    public Book get(int index) {
        return bookList.get(index);
    }
    @Override
    public View getView(int i, View view, ViewGroup parent) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.presentation, null);
        }
        TextView titlev = view.findViewById(R.id.titletextView);
        TextView authorv = view.findViewById(R.id.authortextView);
        TextView datev = view.findViewById(R.id.datetextView);
        Book book = bookList.get(i);
        String isbn = book.getIsbn() ;
        String title = book.getTitle().toUpperCase() ;
        String author = book.getAuthor() ;
        String releaseDate =book.getReleaseDate() ;

        titlev.setText(title);
        authorv.setText(author);
        datev.setText(releaseDate);
        Log.i("-------------->" ,"In getView for " + i + " : " + book.toString() ) ;

        return view;
    }

}
