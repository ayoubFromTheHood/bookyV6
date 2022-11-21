package fr.uha.ayoub.dut.booky;

public class Book implements Comparable<Book>{
    private String isbn;
    private String title;
    private String author;
    private String releaseDate;
    private String description ;


    public Book(String isbn, String title, String author, String releaseDate,String description) {
        this.isbn = isbn;
        this.author = author;
        this.releaseDate = releaseDate;
        this.title = title;
        this.description=  description ;  }

    public String getIsbn() {
        return isbn;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }



    @Override
    public int compareTo(Book book) {
        int val = this.isbn.compareTo(book.isbn);

        return val ;
    }
}
