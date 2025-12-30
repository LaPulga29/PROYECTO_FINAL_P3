package Negocio;

import java.util.Calendar;

public class Boda extends Evento {
    private String tipoCeremonia;
    private String cancionVals;
    private String nombreNovios;
    private Organizador organizador;

    public Boda(String id, Calendar fechaEvento, int horasDuracion,
                String lugar, String temaColor, int numeroInvitados,
                double presupuestoComida, double presupuestoSalon, double presupuestoBanda,
                String tipoCeremonia, String cancionVals, String nombreNovios) {
        super(id, fechaEvento, horasDuracion, lugar, temaColor,
                numeroInvitados, presupuestoComida, presupuestoSalon, presupuestoBanda);
        this.tipoCeremonia = tipoCeremonia;
        this.cancionVals = cancionVals;
        this.nombreNovios = nombreNovios;
    }

    public String getTipoCeremonia() { return tipoCeremonia; }
    public String getCancionVals() { return cancionVals; }
    public String getNombreNovios() { return nombreNovios; }
    public Organizador getOrganizador() { return organizador; }

    public void setTipoCeremonia(String tipoCeremonia) { this.tipoCeremonia = tipoCeremonia; }
    public void setCancionVals(String cancionVals) { this.cancionVals = cancionVals; }
    public void setNombreNovios(String nombreNovios) { this.nombreNovios = nombreNovios; }
    public void setOrganizador(Organizador organizador) { this.organizador = organizador; }

    @Override
    public String toString() {
        return "Boda ID: " + id + " - Novios: " + nombreNovios +
                " - Fecha: " + Util.Utilitario.formatearFecha(fechaEvento);
    }
}