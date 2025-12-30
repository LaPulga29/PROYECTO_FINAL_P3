package Negocio;

import java.util.Calendar;

public abstract class Evento {
    protected String id;
    protected Calendar fechaEvento;
    protected int horasDuracion;
    protected String lugar;
    protected String temaColor;
    protected int numeroInvitados;
    protected double presupuestoComida;
    protected double presupuestoSalon;
    protected double presupuestoBanda;

    public Evento(String id, Calendar fechaEvento, int horasDuracion,
                  String lugar, String temaColor, int numeroInvitados,
                  double presupuestoComida, double presupuestoSalon, double presupuestoBanda) {
        this.id = id;
        this.fechaEvento = fechaEvento;
        this.horasDuracion = horasDuracion;
        this.lugar = lugar;
        this.temaColor = temaColor;
        this.numeroInvitados = numeroInvitados;
        this.presupuestoComida = presupuestoComida;
        this.presupuestoSalon = presupuestoSalon;
        this.presupuestoBanda = presupuestoBanda;
    }

    public String getId() { return id; }
    public Calendar getFechaEvento() { return fechaEvento; }
    public int getHorasDuracion() { return horasDuracion; }
    public String getLugar() { return lugar; }
    public String getTemaColor() { return temaColor; }
    public int getNumeroInvitados() { return numeroInvitados; }
    public double getPresupuestoComida() { return presupuestoComida; }
    public double getPresupuestoSalon() { return presupuestoSalon; }
    public double getPresupuestoBanda() { return presupuestoBanda; }

    public void setId(String id) { this.id = id; }
    public void setFechaEvento(Calendar fechaEvento) { this.fechaEvento = fechaEvento; }
    public void setHorasDuracion(int horasDuracion) { this.horasDuracion = horasDuracion; }
    public void setLugar(String lugar) { this.lugar = lugar; }
    public void setTemaColor(String temaColor) { this.temaColor = temaColor; }
    public void setNumeroInvitados(int numeroInvitados) { this.numeroInvitados = numeroInvitados; }
    public void setPresupuestoComida(double presupuestoComida) { this.presupuestoComida = presupuestoComida; }
    public void setPresupuestoSalon(double presupuestoSalon) { this.presupuestoSalon = presupuestoSalon; }
    public void setPresupuestoBanda(double presupuestoBanda) { this.presupuestoBanda = presupuestoBanda; }
}