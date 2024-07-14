package Models;

import java.sql.Date;

public class Prestamo {
    private int id_prestamo;
    private int id_user;
    private int id_book_list;
    private Date inicioDate;
    private boolean status;

    public Prestamo(int id_prestamo, int id_user, int id_book_list, Date inicioDate, boolean status) {
        this.id_prestamo = id_prestamo;
        this.id_user = id_user;
        this.id_book_list = id_book_list;
        this.inicioDate = inicioDate;
        this.status = status;
    }

    public int getId_prestamo() {
        return id_prestamo;
    }

    public int getId_user() {
        return id_user;
    }

    public int getId_book_list() {
        return id_book_list;
    }

    public Date getInicioDate() {
        return inicioDate;
    }

    public boolean isStatus() {
        return status;
    }
}
