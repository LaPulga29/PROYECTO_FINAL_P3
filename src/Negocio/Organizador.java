package Negocio;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Organizador {
    private String id;
    private String cedula;
    private String nombre;
    private String especialidad;
    private int añosExperiencia;
    private String email;  // Separado del contacto telefónico
    private String telefono;
    private String tokenSeguridad;
    private List<Boda> eventosAsociados;

    public Organizador(String id, String cedula, String nombre, String especialidad,
                       int añosExperiencia, String email, String telefono, String tokenSeguridad) {
        this.id = id;
        this.cedula = cedula;
        this.nombre = nombre;
        this.especialidad = especialidad;

        // Validar años de experiencia
        if (añosExperiencia < 0) {
            throw new IllegalArgumentException("Los años de experiencia no pueden ser negativos");
        }
        this.añosExperiencia = añosExperiencia;

        // Validar email
        if (!validarEmail(email)) {
            throw new IllegalArgumentException("Email inválido. Debe contener @ y dominio válido");
        }
        this.email = email;

        // Validar teléfono
        if (!validarTelefono(telefono)) {
            throw new IllegalArgumentException("Teléfono inválido. Debe tener 10 dígitos numéricos");
        }
        this.telefono = telefono;

        this.tokenSeguridad = tokenSeguridad;
        this.eventosAsociados = new ArrayList<>();
    }

    // Método para validar email
    private boolean validarEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return Pattern.matches(emailRegex, email);
    }

    // Método para validar teléfono
    private boolean validarTelefono(String telefono) {
        if (telefono == null || telefono.isEmpty()) {
            return false;
        }
        // Remover espacios, guiones, paréntesis
        String telefonoLimpio = telefono.replaceAll("[\\s\\-()]", "");

        // Validar que sean solo números y exactamente 10 dígitos
        if (!telefonoLimpio.matches("\\d{10}")) {
            return false;
        }

        // Validar que no sea negativo (ya que es String)
        try {
            Long.parseLong(telefonoLimpio);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public String getId() { return id; }
    public String getCedula() { return cedula; }
    public String getNombre() { return nombre; }
    public String getEspecialidad() { return especialidad; }
    public int getAñosExperiencia() { return añosExperiencia; }
    public String getEmail() { return email; }
    public String getTelefono() { return telefono; }
    public String getTokenSeguridad() { return tokenSeguridad; }
    public List<Boda> getEventosAsociados() { return eventosAsociados; }

    public void setId(String id) { this.id = id; }
    public void setCedula(String cedula) { this.cedula = cedula; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public void setAñosExperiencia(int añosExperiencia) {
        if (añosExperiencia < 0) {
            throw new IllegalArgumentException("Los años de experiencia no pueden ser negativos");
        }
        this.añosExperiencia = añosExperiencia;
    }

    public void setEmail(String email) {
        if (!validarEmail(email)) {
            throw new IllegalArgumentException("Email inválido. Debe contener @ y dominio válido");
        }
        this.email = email;
    }

    public void setTelefono(String telefono) {
        if (!validarTelefono(telefono)) {
            throw new IllegalArgumentException("Teléfono inválido. Debe tener 10 dígitos numéricos");
        }
        this.telefono = telefono;
    }

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
                "\nEmail: " + email +
                "\nTeléfono: " + telefono +
                "\nEventos asociados: " + eventosAsociados.size();
    }

    @Override
    public String toString() {
        return "Organizador: " + nombre + " (" + especialidad + ") - Cédula: " + cedula +
                " - Experiencia: " + añosExperiencia + " años";
    }
}