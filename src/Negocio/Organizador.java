package Negocio;

import java.util.ArrayList;
import java.util.List;

public class Organizador {
    private String id;
    private String cedula;
    private String nombre;
    private String especialidad;
    private int añosExperiencia;
    private String contacto;  // Aquí estaba juntos email y teléfono
    private String tokenSeguridad;
    private List<Boda> eventosAsociados;

    public Organizador(String id, String cedula, String nombre, String especialidad,
                       int añosExperiencia, String contacto, String tokenSeguridad) {
        this.id = id;
        this.cedula = cedula;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.añosExperiencia = añosExperiencia;
        this.contacto = contacto;
        this.tokenSeguridad = tokenSeguridad;
        this.eventosAsociados = new ArrayList<>();
    }

    // Getters
    public String getId() { return id; }
    public String getCedula() { return cedula; }
    public String getNombre() { return nombre; }
    public String getEspecialidad() { return especialidad; }
    public int getAñosExperiencia() { return añosExperiencia; }
    public String getContacto() { return contacto; }  // Getter único para contacto
    public String getTokenSeguridad() { return tokenSeguridad; }
    public List<Boda> getEventosAsociados() { return eventosAsociados; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setCedula(String cedula) { this.cedula = cedula; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
    public void setAñosExperiencia(int añosExperiencia) { this.añosExperiencia = añosExperiencia; }
    public void setContacto(String contacto) { this.contacto = contacto; }
    public void setTokenSeguridad(String tokenSeguridad) { this.tokenSeguridad = tokenSeguridad; }

    public void agregarEvento(Boda boda) {
        eventosAsociados.add(boda);
    }

    public String toStringSinToken() {
        return "Organizador ID: " + id +
                "\nCédula: " + cedula +
                "\nNombre: " + nombre +
                "\nEspecialidad: " + especialidad +
                "\nAños experiencia: " + añosExperiencia +
                "\nContacto: " + contacto +
                "\nEventos asociados: " + eventosAsociados.size();
    }

    @Override
    public String toString() {
        return "Organizador: " + nombre + " (" + especialidad + ") - Cédula: " + cedula +
                " - Experiencia: " + añosExperiencia + " años";
    }
}