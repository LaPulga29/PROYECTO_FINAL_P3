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

    public void setId(String id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setTipoServicio(String tipoServicio) { this.tipoServicio = tipoServicio; }
}