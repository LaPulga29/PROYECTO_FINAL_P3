package Negocio;

public class ProveedorSalon extends Proveedor {
    private String ubicacion;
    private int capacidadMaxima;
    private double costoPorHora;

    public ProveedorSalon(String id, String nombre, String telefono,
                          String ubicacion, int capacidadMaxima,
                          double costoPorHora) {
        super(id, nombre, telefono, "Salon");
        this.ubicacion = ubicacion;
        this.capacidadMaxima = capacidadMaxima;
        this.costoPorHora = costoPorHora;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public void setCostoPorHora(double costoPorHora) {
        this.costoPorHora = costoPorHora;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    @Override
    public double calcularCosto(int horas) {
        return costoPorHora * horas;
    }

    public String getUbicacion() { return ubicacion; }
    public int getCapacidadMaxima() { return capacidadMaxima; }
    public double getCostoPorHora() { return costoPorHora; }

    @Override
    public String toString() {
        return "Salón: " + nombre + " (" + ubicacion + ") - Capacidad: " + capacidadMaxima +
                " - Costo/hora: $" + costoPorHora;
    }
}