package Models;

public class Book {
    private int id_book;
    private String titulo;
    private String autor;
    private double price;
    private boolean status;

    public Book(int id_book, String titulo, String autor, double price, boolean status) {
        this.id_book = id_book;
        this.titulo = titulo;
        this.autor = autor;
        this.price = price;
        this.status = status;
    }

    public int getId_book() {
        return id_book;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public double getPrice() {
        return price;
    }

    public boolean isStatus() {
        return status;
    }
}
