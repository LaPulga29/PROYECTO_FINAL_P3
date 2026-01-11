package Negocio;
public class ProveedorBanda extends Proveedor {
    private double costoPorHora;
    private String[] generos;

    public ProveedorBanda(String id, String nombre, String telefono,
                          double costoPorHora, String[] generos) {
        super(id, nombre, telefono, "Banda");
        this.costoPorHora = costoPorHora;
        this.generos = generos;
    }

    @Override
    public double calcularCosto(int horas) {
        return costoPorHora * horas;
    }

    public double getCostoPorHora() { return costoPorHora; }
    public String[] getGeneros() { return generos; }

    @Override
    public String toString() {
        return "Banda: " + nombre + " - Costo/hora: $" + costoPorHora;
    }
}
