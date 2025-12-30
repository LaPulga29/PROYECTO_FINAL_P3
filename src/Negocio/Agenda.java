package Negocio;

import java.util.*;

public class Agenda {
    // disponibilidad por salón -> lista de rangos ocupados
    private Map<String, List<RangoFecha>> ocupacionesPorSalon = new HashMap<>();

    public static class RangoFecha {
        public GregorianCalendar inicio;
        public GregorianCalendar fin;
        public RangoFecha(GregorianCalendar i, GregorianCalendar f) { this.inicio = i; this.fin = f; }
    }

    public boolean estaDisponible(String salonNombre, GregorianCalendar inicio, GregorianCalendar fin) {
        List<RangoFecha> ocupados = ocupacionesPorSalon.getOrDefault(salonNombre, new ArrayList<>());
        for (RangoFecha r : ocupados) {
            boolean solapa = !(fin.before(r.inicio) || inicio.after(r.fin));
            if (solapa) return false;
        }
        return true;
    }

    public void bloquear(String salonNombre, GregorianCalendar inicio, GregorianCalendar fin) {
        ocupacionesPorSalon.computeIfAbsent(salonNombre, k -> new ArrayList<>()).add(new RangoFecha(inicio, fin));
    }
}
