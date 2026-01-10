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
            int opcion = utilitario.leerEntero();

            switch (opcion) {
                case 1:
                    utilitario.menuGestionarOrganizadores();
                    break;
                case 2:
                    utilitario.menuGestionarEventos();
                    break;
                case 3:
                    utilitario.menuGestionarProveedores();
                    break;
                case 4:
                    // MENÚ 4: GESTIÓN DE PRESUPUESTOS
                    if (utilitario.getBodas().isEmpty()) {
                        System.out.println("\n✗ No hay bodas registradas.");
                        System.out.println("Use la opción 2 para crear una boda.");
                        break;
                    }
                    gestorEventos.menuGestionarPresupuestos(utilitario);
                    break;
                case 5:
                    // MENÚ 5: ANÁLISIS POST-EVENTO
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
                    break;
                case 6:
                    salir = true;
                    System.out.println("¡Gracias por usar el sistema!");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }
}