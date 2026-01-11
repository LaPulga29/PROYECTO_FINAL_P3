package Negocio;

import java.util.ArrayList;
import java.util.List;

public class Organizador {
    private String id;
    private String cedula;
    private String nombre;
    private String especialidad;
    private int añosExperiencia;
    private String email;  // Separado del teléfono
    private String telefono; // Separado del email
    private String tokenSeguridad;
    private List<Boda> eventosAsociados;

    public Organizador(String id, String cedula, String nombre, String especialidad,
                       int añosExperiencia, String email, String telefono, String tokenSeguridad)
            throws Util.Utilitario.ValidacionException {
        this.id = id;
        setCedula(cedula);  // Usar setter para validación
        this.nombre = nombre;
        this.especialidad = especialidad;
        setAñosExperiencia(añosExperiencia);  // Usar setter para validación
        setEmail(email);  // Usar setter para validación
        setTelefono(telefono);  // Usar setter para validación
        this.tokenSeguridad = tokenSeguridad;
        this.eventosAsociados = new ArrayList<>();
    }

    // Getters
    public String getId() { return id; }
    public String getCedula() { return cedula; }
    public String getNombre() { return nombre; }
    public String getEspecialidad() { return especialidad; }
    public int getAñosExperiencia() { return añosExperiencia; }
    public String getEmail() { return email; }
    public String getTelefono() { return telefono; }
    public String getTokenSeguridad() { return tokenSeguridad; }
    public List<Boda> getEventosAsociados() { return eventosAsociados; }

    public void setCedula(String cedula) throws Util.Utilitario.CedulaInvalidaException {
        if (!validarCedula(cedula)) {
            throw new Util.Utilitario.CedulaInvalidaException("Cédula inválida. Debe tener 10 dígitos numéricos.");
        }
        this.cedula = cedula;
    }


    public void setAñosExperiencia(int añosExperiencia) throws Util.Utilitario.NumeroInvalidoException {
        if (añosExperiencia < 0) {
            throw new Util.Utilitario.NumeroInvalidoException("Los años de experiencia no pueden ser negativos");
        }
        this.añosExperiencia = añosExperiencia;
    }

    public void setEmail(String email) throws Util.Utilitario.EmailInvalidoException {
        if (!validarEmail(email)) {
            throw new Util.Utilitario.EmailInvalidoException("Email inválido. Debe contener @");
        }
        this.email = email;
    }

    public void setTelefono(String telefono) throws Util.Utilitario.TelefonoInvalidoException {
        if (!validarTelefono(telefono)) {
            throw new Util.Utilitario.TelefonoInvalidoException("Teléfono inválido. Debe tener 10 dígitos numéricos");
        }
        this.telefono = telefono;
    }

    // Métodos de validación privados
    private boolean validarEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return email.contains("@");
    }

    private boolean validarTelefono(String telefono) {
        if (telefono == null || telefono.isEmpty() || telefono.length() != 10) {
            return false;
        }

        try {
            Long.parseLong(telefono);
            return !telefono.startsWith("-");
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean validarCedula(String cedula) {
        if (cedula == null || cedula.isEmpty() || cedula.length() != 10) {
            return false;
        }

        try {
            Long.parseLong(cedula);
            return !cedula.startsWith("-");
        } catch (NumberFormatException e) {
            return false;
        }
    }

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
                " - Experiencia: " + añosExperiencia + " años - Email: " + email;
    }
}