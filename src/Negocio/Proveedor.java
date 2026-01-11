package Negocio;

public abstract class Proveedor {
    private String id;
    public String nombre;
    private String telefono;
    private String tipoServicio;

    public Proveedor(String id, String nombre, String telefono, String tipoServicio) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.tipoServicio = tipoServicio;
    }

    public abstract double calcularCosto(int cantidad);

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getTelefono() { return telefono; }
    public String getTipoServicio() { return tipoServicio; }

}