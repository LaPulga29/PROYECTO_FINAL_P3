package Util;
import Negocio.*;
import java.util.*;
import java.util.GregorianCalendar;
//


public class Utilitario {
    private static final double IVA = 0.15;
    private static int idBodaCounter = 1000;
    private static int idProveedorCounter = 2000;
    private static int idOrganizadorCounter = 3000;
    private static int idContratoCounter = 4000;

    // Validaciones
    public static boolean validarNoNegativo(double valor) {
        return valor >= 0;
    }

    public static boolean validarNoNegativo(int valor) {
        return valor >= 0;
    }

    public static boolean validarCapacidadSalon(int invitados, int capacidadSalon) {
        return invitados <= capacidadSalon;
    }

    // Cálculos
    public static double calcularConIVA(double costo) {
        return costo + (costo * IVA);
    }

    public static boolean esProveedorApto(Boda boda, Proveedor proveedor) {
        if (proveedor instanceof ProveedorComida) {
            ProveedorComida comida = (ProveedorComida) proveedor;
            double costoTotal = comida.calcularCosto(boda.getNumeroInvitados());
            double costoConIVA = calcularConIVA(costoTotal);
            return costoConIVA <= boda.getPresupuestoComida();
        }
        else if (proveedor instanceof ProveedorSalon) {
            ProveedorSalon salon = (ProveedorSalon) proveedor;
            if (!validarCapacidadSalon(boda.getNumeroInvitados(), salon.getCapacidadMaxima())) {
                return false;
            }
            double costoTotal = salon.calcularCosto(boda.getHorasDuracion());
            double costoConIVA = calcularConIVA(costoTotal);
            return costoConIVA <= boda.getPresupuestoSalon();
        }
        else if (proveedor instanceof ProveedorBanda) {
            ProveedorBanda banda = (ProveedorBanda) proveedor;
            double costoTotal = banda.calcularCosto(boda.getHorasDuracion());
            double costoConIVA = calcularConIVA(costoTotal);
            return costoConIVA <= boda.getPresupuestoBanda();
        }
        return false;
    }

    // Formateo de fechas (solo día)
    public static String formatearFecha(Calendar fecha) {
        if (fecha == null) return "No definida";

        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        int mes = fecha.get(Calendar.MONTH) + 1;
        int año = fecha.get(Calendar.YEAR);

        return String.format("%02d/%02d/%04d", dia, mes, año);
    }

    public static Calendar crearFecha(int dia, int mes, int año) {
        return new GregorianCalendar(año, mes - 1, dia);
    }

    // Búsqueda y filtrado
    public static List<Proveedor> filtrarProveedoresPorCosto(List<Proveedor> proveedores,
                                                             double costoMaximo) {
        List<Proveedor> resultado = new ArrayList<>();
        for (Proveedor p : proveedores) {
            double costoBase = 0;
            if (p instanceof ProveedorComida) {
                costoBase = ((ProveedorComida) p).getCostoPorPersona();
            } else if (p instanceof ProveedorSalon) {
                costoBase = ((ProveedorSalon) p).getCostoPorHora();
            } else if (p instanceof ProveedorBanda) {
                costoBase = ((ProveedorBanda) p).getCostoPorHora();
            }

            if (costoBase <= costoMaximo) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    public static List<Proveedor> filtrarProveedoresPorTipo(List<Proveedor> proveedores,
                                                            String tipo) {
        List<Proveedor> resultado = new ArrayList<>();
        for (Proveedor p : proveedores) {
            if (p.getTipoServicio().equalsIgnoreCase(tipo)) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    public static List<Boda> filtrarBodasPorFecha(List<Boda> bodas, Calendar fecha) {
        List<Boda> resultado = new ArrayList<>();
        for (Boda boda : bodas) {
            if (mismasFechas(boda.getFechaEvento(), fecha)) {
                resultado.add(boda);
            }
        }
        return resultado;
    }

    public static boolean mismasFechas(Calendar fecha1, Calendar fecha2) {
        if (fecha1 == null || fecha2 == null) return false;

        return fecha1.get(Calendar.YEAR) == fecha2.get(Calendar.YEAR) &&
                fecha1.get(Calendar.MONTH) == fecha2.get(Calendar.MONTH) &&
                fecha1.get(Calendar.DAY_OF_MONTH) == fecha2.get(Calendar.DAY_OF_MONTH);
    }

    public static boolean validarDisponibilidadSalon(ProveedorSalon salon,
                                                     Calendar fechaEvento,
                                                     int horasDuracion,
                                                     List<Contrato> contratos) {
        // Verificar si hay algún contrato que use este salón en la misma fecha
        for (Contrato contrato : contratos) {
            if (contrato.getProveedor().equals(salon)) {
                Calendar fechaExistente = contrato.getBoda().getFechaEvento();
                if (mismasFechas(fechaExistente, fechaEvento)) {
                    return false; // Salón ya ocupado en esta fecha
                }
            }
        }
        return true;
    }

    // Generación de IDs únicos
    public static String generarIdBoda() {
        idBodaCounter++;
        return "BOD" + idBodaCounter;
    }

    public static String generarIdProveedor() {
        idProveedorCounter++;
        return "PROV" + idProveedorCounter;
    }

    public static String generarIdOrganizador() {
        idOrganizadorCounter++;
        return "ORG" + idOrganizadorCounter;
    }

    public static String generarIdContrato() {
        idContratoCounter++;
        return "CONT" + idContratoCounter;
    }

    // Método para recomendar proveedores
    public static void recomendarProveedores(Boda boda, List<Proveedor> proveedores) {
        System.out.println("\n=== PROVEEDORES RECOMENDADOS PARA LA BODA ===");
        System.out.println("Boda: " + boda.getNombreNovios());
        System.out.println("Presupuesto Comida: $" + String.format("%.2f", boda.getPresupuestoComida()));
        System.out.println("Presupuesto Salon: $" + String.format("%.2f", boda.getPresupuestoSalon()));
        System.out.println("Presupuesto Banda: $" + String.format("%.2f", boda.getPresupuestoBanda()));
        System.out.println("Invitados: " + boda.getNumeroInvitados());
        System.out.println("Duración: " + boda.getHorasDuracion() + " horas\n");

        boolean hayAptos = false;

        System.out.println("--- PROVEEDORES DE COMIDA APTOS ---");
        for (Proveedor p : proveedores) {
            if (p instanceof ProveedorComida && esProveedorApto(boda, p)) {
                ProveedorComida comida = (ProveedorComida) p;
                double costoTotal = calcularConIVA(comida.calcularCosto(boda.getNumeroInvitados()));
                System.out.println("✓ " + comida + " | Costo total (con IVA): $" + String.format("%.2f", costoTotal));
                hayAptos = true;
            }
        }

        System.out.println("\n--- PROVEEDORES DE SALÓN APTOS ---");
        for (Proveedor p : proveedores) {
            if (p instanceof ProveedorSalon && esProveedorApto(boda, p)) {
                ProveedorSalon salon = (ProveedorSalon) p;
                double costoTotal = calcularConIVA(salon.calcularCosto(boda.getHorasDuracion()));
                System.out.println("✓ " + salon + " | Costo total (con IVA): $" + String.format("%.2f", costoTotal));
                hayAptos = true;
            }
        }

        System.out.println("\n--- PROVEEDORES DE BANDA APTOS ---");
        for (Proveedor p : proveedores) {
            if (p instanceof ProveedorBanda && esProveedorApto(boda, p)) {
                ProveedorBanda banda = (ProveedorBanda) p;
                double costoTotal = calcularConIVA(banda.calcularCosto(boda.getHorasDuracion()));
                System.out.println("✓ " + banda + " | Costo total (con IVA): $" + String.format("%.2f", costoTotal));
                hayAptos = true;
            }
        }

        if (!hayAptos) {
            System.out.println("\n⚠ No hay proveedores aptos para esta boda.");
            System.out.println("Recomendaciones:");
            System.out.println("1. Aumentar el presupuesto");
            System.out.println("2. Reducir el número de invitados");
            System.out.println("3. Buscar otros proveedores");
        }
    }
}