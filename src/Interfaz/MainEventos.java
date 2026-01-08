package Interfaz;
import Util.Utilitario;
import Negocio.*;
import java.util.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
//
public class MainEventos {
    public static void main(String[] args) {
        Utilitario utilitario = new Utilitario();
        boolean salir = false;

        System.out.println("=== BIENVENIDO AL SISTEMA DE GESTIÓN DE BODAS ===");

        while (!salir) {
            utilitario.mostrarMenuPrincipal();
            int opcion = utilitario.leerEntero();

            switch (opcion) {
                case 1: utilitario.menuGestionarOrganizadores(); break;
                case 2: utilitario.menuGestionarEventos(); break;
                case 3: utilitario.menuGestionarProveedores(); break;
                case 4: utilitario.menuGestionarPresupuestos(); break;  // Ahora con disponibilidad
                case 5: utilitario.menuAnalisisPostEvento(); break;     // Nuevo: Análisis Post-Evento
                case 6:
                    salir = true;
                    System.out.println("¡Gracias por usar el sistema!");
                    break;
                default: System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }
}