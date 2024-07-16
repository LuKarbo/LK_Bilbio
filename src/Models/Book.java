package Models;

import java.util.ArrayList;

public class Book {
    private int id_book;
    private String titulo;
    private String autor;
    private double price;
    private boolean status;
    private ArrayList<String> categories;

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

    public boolean getStatus() {
        return status;
    }

    public void setCategories(ArrayList<String> categoryList){
        this.categories = categoryList;
    }

    public ArrayList<String> getCategories(){
        return this.categories;
    }

}
