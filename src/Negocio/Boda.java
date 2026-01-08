package Negocio;

import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;

public class Boda extends Evento {
    private String tipoCeremonia;
    private String cancionVals;
    private String nombreNovios;
    private Organizador organizador;
    private List<Proveedor> proveedoresContratados;
    private boolean proformaAceptada;

    private int asistentesReales;
    private double gastoRealComida;
    private double gastoRealSalon;
    private double gastoRealBanda;
    private int horasRealesSalon;
    private int horasRealesBanda;

    public Boda(String id, Calendar fechaEvento, int horasDuracion,
                String lugar, String temaColor, int numeroInvitados,
                double presupuestoComida, double presupuestoSalon, double presupuestoBanda,
                String tipoCeremonia, String cancionVals, String nombreNovios) {
        super(id, fechaEvento, horasDuracion, lugar, temaColor,
                numeroInvitados, presupuestoComida, presupuestoSalon, presupuestoBanda);
        this.tipoCeremonia = tipoCeremonia;
        this.cancionVals = cancionVals;
        this.nombreNovios = nombreNovios;
        this.proveedoresContratados = new ArrayList<>();
        this.proformaAceptada = false;
        this.asistentesReales = 0;
        this.gastoRealComida = 0;
        this.gastoRealSalon = 0;
        this.gastoRealBanda = 0;
        this.horasRealesSalon = horasDuracion;
        this.horasRealesBanda = horasDuracion;
    }

    public String getCancionVals() { return cancionVals; }
    public void setCancionVals(String cancionVals) { this.cancionVals = cancionVals; }
    public String getNombreNovios() { return nombreNovios; }
    public void setNombreNovios(String nombreNovios) { this.nombreNovios = nombreNovios; }
    public Organizador getOrganizador() { return organizador; }
    public void setOrganizador(Organizador organizador) { this.organizador = organizador; }
    public String getTipoCeremonia() { return tipoCeremonia; }
    public void setTipoCeremonia(String tipoCeremonia) { this.tipoCeremonia = tipoCeremonia; }
    public List<Proveedor> getProveedoresContratados() { return proveedoresContratados; }
    public void setProveedoresContratados(List<Proveedor> proveedoresContratados) {
        this.proveedoresContratados = proveedoresContratados;
    }
    public boolean isProformaAceptada() { return proformaAceptada; }
    public void setProformaAceptada(boolean proformaAceptada) { this.proformaAceptada = proformaAceptada; }

    public int getAsistentesReales() { return asistentesReales; }
    public void setAsistentesReales(int asistentesReales) { this.asistentesReales = asistentesReales; }
    public double getGastoRealComida() { return gastoRealComida; }
    public void setGastoRealComida(double gastoRealComida) { this.gastoRealComida = gastoRealComida; }
    public double getGastoRealSalon() { return gastoRealSalon; }
    public void setGastoRealSalon(double gastoRealSalon) { this.gastoRealSalon = gastoRealSalon; }
    public double getGastoRealBanda() { return gastoRealBanda; }
    public void setGastoRealBanda(double gastoRealBanda) { this.gastoRealBanda = gastoRealBanda; }
    public int getHorasRealesSalon() { return horasRealesSalon; }
    public void setHorasRealesSalon(int horasRealesSalon) { this.horasRealesSalon = horasRealesSalon; }
    public int getHorasRealesBanda() { return horasRealesBanda; }
    public void setHorasRealesBanda(int horasRealesBanda) { this.horasRealesBanda = horasRealesBanda; }

    // MÉTODO PARA CALCULAR GASTO TOTAL REAL
    public double getGastoTotalReal() {
        return gastoRealComida + gastoRealSalon + gastoRealBanda;
    }

    // MÉTODO PARA CALCULAR PRESUPUESTO TOTAL PLANIFICADO
    public double getPresupuestoTotal() {
        return presupuestoComida + presupuestoSalon + presupuestoBanda;
    }

    // MÉTODO PARA VERIFICAR SOBRECUPO
    public boolean haySobrecupo(ProveedorSalon salon) {
        if (salon == null) return false;
        return asistentesReales > salon.getCapacidadMaxima();
    }

    // MÉTODO PARA CALCULAR PERSONAS EXCEDENTES
    public int getPersonasExcedentes(ProveedorSalon salon) {
        if (salon == null) return 0;
        return Math.max(0, asistentesReales - salon.getCapacidadMaxima());
    }

    public void contratarProveedor(Proveedor proveedor) {
        if (!proveedoresContratados.contains(proveedor)) {
            proveedoresContratados.add(proveedor);
        }
    }

    public ProveedorComida getProveedorComidaContratado() {
        for (Proveedor p : proveedoresContratados) {
            if (p instanceof ProveedorComida) {
                return (ProveedorComida) p;
            }
        }
        return null;
    }

    public ProveedorSalon getProveedorSalonContratado() {
        for (Proveedor p : proveedoresContratados) {
            if (p instanceof ProveedorSalon) {
                return (ProveedorSalon) p;
            }
        }
        return null;
    }

    public ProveedorBanda getProveedorBandaContratado() {
        for (Proveedor p : proveedoresContratados) {
            if (p instanceof ProveedorBanda) {
                return (ProveedorBanda) p;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Boda ID: " + id + " - Novios: " + nombreNovios +
                " - Fecha: " + Util.Utilitario.formatearFecha(fechaEvento);
    }
}