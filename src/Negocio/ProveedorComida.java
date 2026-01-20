package Negocio;

public class ProveedorComida extends Proveedor {
    private String nombrePlato;
    private double costoPorPersona;
    private String nombreCatering;

    // Constructor
    public ProveedorComida(String id, String nombre, String telefono,
                           String nombrePlato, double costoPorPersona,
                           String nombreCatering) {
        super(id, nombre, telefono, "Comida");
        this.nombrePlato = nombrePlato;
        this.costoPorPersona = costoPorPersona;
        this.nombreCatering = nombreCatering;
    }

    public void setCostoPorPersona(double costoPorPersona) {
        this.costoPorPersona = costoPorPersona;
    }

    public void setNombreCatering(String nombreCatering) {
        this.nombreCatering = nombreCatering;
    }

    public void setNombrePlato(String nombrePlato) {
        this.nombrePlato = nombrePlato;
    }

    // Metodo para calcular costo
    @Override
    public double calcularCosto(int cantidadPersonas) {
        return costoPorPersona * cantidadPersonas;
    }

    // Getters y Setters
    public String getNombrePlato() { return nombrePlato; }
    public double getCostoPorPersona() { return costoPorPersona; }
    public String getNombreCatering() { return nombreCatering; }


    @Override
    public String toString() {
        return "Comida: " + getNombre() + " (" + nombreCatering + ") - Plato: " + nombrePlato +
                " - Costo/persona: $" + costoPorPersona;
    }
}