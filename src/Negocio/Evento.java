package Negocio;

import java.util.Calendar;

public abstract class Evento {
    public String id;
    public Calendar fechaEvento;
    public int horasDuracion;
    public String lugar;
    public String temaColor;
    public int numeroInvitados;
    public double presupuestoComida;
    public double presupuestoSalon;
    public double presupuestoBanda;

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

}