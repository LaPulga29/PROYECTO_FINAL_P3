package Negocio;
import java.util.GregorianCalendar;

import java.util.Calendar;

public class Contrato {
    private String id;
    private Boda boda;
    private Proveedor proveedor;
    private double costoFinal;
    private Calendar fechaContrato;

    public Contrato(String id, Boda boda, Proveedor proveedor, double costoFinal) {
        this.id = id;
        this.boda = boda;
        this.proveedor = proveedor;
        this.costoFinal = costoFinal;
        this.fechaContrato = Calendar.getInstance();
    }

    public String getId() { return id; }
    public Boda getBoda() { return boda; }
    public Proveedor getProveedor() { return proveedor; }
    public double getCostoFinal() { return costoFinal; }
    public Calendar getFechaContrato() { return fechaContrato; }

    public void setId(String id) { this.id = id; }
    public void setBoda(Boda boda) { this.boda = boda; }
    public void setProveedor(Proveedor proveedor) { this.proveedor = proveedor; }
    public void setCostoFinal(double costoFinal) { this.costoFinal = costoFinal; }
    public void setFechaContrato(Calendar fechaContrato) { this.fechaContrato = fechaContrato; }
}