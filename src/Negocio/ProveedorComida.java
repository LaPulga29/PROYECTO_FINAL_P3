package Negocio;

public class ProveedorComida extends Proveedor {
    private String nombrePlato;
    private double costoPorPersona;
    private String nombreCatering;

    public ProveedorComida(String id, String nombre, String telefono,
                           String nombrePlato, double costoPorPersona,
                           String nombreCatering) {
        super(id, nombre, telefono, "Comida");
        this.nombrePlato = nombrePlato;
        this.costoPorPersona = costoPorPersona;
        this.nombreCatering = nombreCatering;
    }

    @Override
    public double calcularCosto(int personas) {
        return costoPorPersona * personas;
    }

    public String getNombrePlato() { return nombrePlato; }
    public double getCostoPorPersona() { return costoPorPersona; }
    public String getNombreCatering() { return nombreCatering; }

    public void setNombrePlato(String nombrePlato) { this.nombrePlato = nombrePlato; }
    public void setCostoPorPersona(double costoPorPersona) { this.costoPorPersona = costoPorPersona; }
    public void setNombreCatering(String nombreCatering) { this.nombreCatering = nombreCatering; }

    @Override
    public String toString() {
        return "Comida: " + nombre + " (" + nombreCatering + ") - Costo/persona: $" + costoPorPersona;
    }
}