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
    private String observaciones; // AGREGAR ESTE ATRIBUTO
    private boolean evaluacionCompletada; // AGREGAR ESTE ATRIBUTO

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

        // Validar fecha no pasada (simplificado - el Utilitario ya valida esto)
        Calendar hoy = Calendar.getInstance();
        if (fechaEvento.before(hoy)) {
            throw new IllegalArgumentException("No se puede crear un evento con fecha pasada.");
        }

        // Validar datos numéricos positivos
        if (horasDuracion <= 0) {
            throw new IllegalArgumentException("Las horas de duración deben ser mayores a 0.");
        }

        if (numeroInvitados <= 0) {
            throw new IllegalArgumentException("El número de invitados debe ser mayor a 0.");
        }

        if (presupuestoComida < 0) {
            throw new IllegalArgumentException("El presupuesto de comida no puede ser negativo.");
        }

        if (presupuestoSalon < 0) {
            throw new IllegalArgumentException("El presupuesto de salón no puede ser negativo.");
        }

        if (presupuestoBanda < 0) {
            throw new IllegalArgumentException("El presupuesto de banda no puede ser negativo.");
        }

        // Validar datos de texto
        if (tipoCeremonia == null || tipoCeremonia.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de ceremonia es requerido.");
        }

        if (nombreNovios == null || nombreNovios.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de los novios es requerido.");
        }

        this.tipoCeremonia = tipoCeremonia;
        this.cancionVals = cancionVals;
        this.nombreNovios = nombreNovios;
        this.proveedoresContratados = new ArrayList<>();
        this.proformaAceptada = false;
        this.observaciones = ""; // INICIALIZAR
        this.evaluacionCompletada = false; // INICIALIZAR
        this.asistentesReales = 0;
        this.gastoRealComida = 0;
        this.gastoRealSalon = 0;
        this.gastoRealBanda = 0;
        this.horasRealesSalon = horasDuracion;
        this.horasRealesBanda = horasDuracion;
    }

    // Getters y Setters
    public String getTipoCeremonia() { return tipoCeremonia; }
    public void setTipoCeremonia(String tipoCeremonia) { this.tipoCeremonia = tipoCeremonia; }

    public String getCancionVals() { return cancionVals; }
    public void setCancionVals(String cancionVals) { this.cancionVals = cancionVals; }

    public String getNombreNovios() { return nombreNovios; }
    public void setNombreNovios(String nombreNovios) { this.nombreNovios = nombreNovios; }

    public Organizador getOrganizador() { return organizador; }
    public void setOrganizador(Organizador organizador) { this.organizador = organizador; }

    public List<Proveedor> getProveedoresContratados() { return proveedoresContratados; }
    public void setProveedoresContratados(List<Proveedor> proveedoresContratados) {
        this.proveedoresContratados = proveedoresContratados;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public boolean isEvaluacionCompletada() {
        return evaluacionCompletada;
    }

    public void setEvaluacionCompletada(boolean evaluacionCompletada) {
        this.evaluacionCompletada = evaluacionCompletada;
    }

    public boolean isProformaAceptada() { return proformaAceptada; }
    public void setProformaAceptada(boolean proformaAceptada) { this.proformaAceptada = proformaAceptada; }

    public int getAsistentesReales() { return asistentesReales; }
    public void setAsistentesReales(int asistentesReales) {
        if (asistentesReales < 0) {
            throw new IllegalArgumentException("Los asistentes reales no pueden ser negativos.");
        }
        this.asistentesReales = asistentesReales;
    }

    public double getGastoRealComida() { return gastoRealComida; }
    public void setGastoRealComida(double gastoRealComida) {
        if (gastoRealComida < 0) {
            throw new IllegalArgumentException("El gasto real de comida no puede ser negativo.");
        }
        this.gastoRealComida = gastoRealComida;
    }

    public double getGastoRealSalon() { return gastoRealSalon; }
    public void setGastoRealSalon(double gastoRealSalon) {
        if (gastoRealSalon < 0) {
            throw new IllegalArgumentException("El gasto real de salón no puede ser negativo.");
        }
        this.gastoRealSalon = gastoRealSalon;
    }

    public double getGastoRealBanda() { return gastoRealBanda; }
    public void setGastoRealBanda(double gastoRealBanda) {
        if (gastoRealBanda < 0) {
            throw new IllegalArgumentException("El gasto real de banda no puede ser negativo.");
        }
        this.gastoRealBanda = gastoRealBanda;
    }

    public int getHorasRealesSalon() { return horasRealesSalon; }
    public void setHorasRealesSalon(int horasRealesSalon) {
        if (horasRealesSalon < 0) {
            throw new IllegalArgumentException("Las horas reales de salón no pueden ser negativas.");
        }
        this.horasRealesSalon = horasRealesSalon;
    }

    public int getHorasRealesBanda() { return horasRealesBanda; }
    public void setHorasRealesBanda(int horasRealesBanda) {
        if (horasRealesBanda < 0) {
            throw new IllegalArgumentException("Las horas reales de banda no pueden ser negativas.");
        }
        this.horasRealesBanda = horasRealesBanda;
    }

    // MÉTODO PARA CALCULAR GASTO TOTAL REAL
    public double getGastoTotalReal() {
        return gastoRealComida + gastoRealSalon + gastoRealBanda;
    }

    // MÉTODO PARA CALCULAR PRESUPUESTO TOTAL PLANIFICADO
    public double getPresupuestoTotal() {
        return getPresupuestoComida() + getPresupuestoSalon() + getPresupuestoBanda();
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
        return "Boda ID: " + getId() + " - Novios: " + nombreNovios +
                " - Fecha: " + Util.Utilitario.formatearFecha(getFechaEvento());
    }
}