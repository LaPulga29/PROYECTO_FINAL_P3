package Interfaz;
import Util.Utilitario;
import Negocio.*;
import java.util.*;

public class MainEventos {
    public static void main(String[] args) {
        Utilitario utilitario = new Utilitario();
        EventoEjecutado gestorEventos = new EventoEjecutado();
        boolean salir = false;

        System.out.println("=== BIENVENIDO AL SISTEMA DE GESTIÓN DE BODAS ===");

        while (!salir) {
            utilitario.mostrarMenuPrincipal();
            int opcion = 0;
            boolean opcionValida = false;

            // MANEJO DE EXCEPCIÓN PARA ENTRADA INVÁLIDA EN LA SELECCIÓN DEL MENÚ
            while (!opcionValida) {
                try {
                    opcion = utilitario.leerEntero();
                    opcionValida = true;
                } catch (Util.Utilitario.EntradaInvalidaException e) {
                    System.out.println("❌ ERROR: " + e.getMessage() + ". Intente nuevamente.");
                    System.out.print("Seleccione una opción (1-6): ");
                }
            }

            switch (opcion) {
                case 1:
                    // MENÚ 1: GESTIÓN DE ORGANIZADORES
                    try {
                        utilitario.menuGestionarOrganizadores();
                    } catch (Exception e) {
                        System.out.println("❌ ERROR INESPERADO (Gestión Organizadores): " + e.getMessage());
                        e.printStackTrace();
                    }
                    break;

                case 2:
                    // MENÚ 2: GESTIÓN DE EVENTOS
                    try {
                        utilitario.menuGestionarEventos();
                    } catch (Exception e) {
                        System.out.println("❌ ERROR INESPERADO (Gestión Eventos): " + e.getMessage());
                        e.printStackTrace();
                    }
                    break;

                case 3:
                    // MENÚ 3: GESTIÓN DE PROVEEDORES
                    try {
                        utilitario.menuGestionarProveedores();
                    } catch (Exception e) {
                        System.out.println("❌ ERROR INESPERADO (Gestión Proveedores): " + e.getMessage());
                        e.printStackTrace();
                    }
                    break;

                case 4:
                    // MENÚ 4: GESTIÓN DE PRESUPUESTOS
                    try {
                        if (utilitario.getBodas().isEmpty()) {
                            System.out.println("\n✗ No hay bodas registradas.");
                            System.out.println("Use la opción 2 para crear una boda.");
                            break;
                        }
                        gestorEventos.menuGestionarPresupuestos(utilitario);
                    } catch (Exception e) {
                        System.out.println("❌ ERROR INESPERADO (Gestión Presupuestos): " + e.getMessage());
                        e.printStackTrace();
                    }
                    break;

                case 5:
                    // MENÚ 5: ANÁLISIS POST-EVENTO
                    try {
                        if (utilitario.getBodas().isEmpty()) {
                            System.out.println("\n✗ No hay bodas registradas.");
                            System.out.println("Use la opción 2 para crear una boda.");
                            break;
                        }
                        if (utilitario.getOrganizadores().isEmpty()) {
                            System.out.println("\n✗ No hay organizadores registrados.");
                            System.out.println("Use la opción 1 para agregar un organizador.");
                            break;
                        }
                        gestorEventos.menuAnalisisPostEvento(utilitario);
                    } catch (Exception e) {
                        System.out.println("❌ ERROR INESPERADO (Análisis Post-Evento): " + e.getMessage());
                        e.printStackTrace();
                    }
                    break;

                case 6:
                    salir = true;
                    System.out.println("¡Gracias por usar el sistema!");
                    break;

                default:
                    System.out.println("❌ Opción inválida. Ingrese un número del 1 al 6.");
            }
        }
    }
}