package Interfaz;
import Util.Utilitario;
import Negocio.*;
import java.util.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
//

public class MainEventos {
    private static List<Boda> bodas = new ArrayList<>();
    private static List<Proveedor> proveedores = new ArrayList<>();
    private static List<Organizador> organizadores = new ArrayList<>();
    private static List<Contrato> contratos = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n=== SISTEMA DE GESTIÓN DE BODAS ===");
            System.out.println("1. Gestionar Organizadores");
            System.out.println("2. Gestionar Eventos");
            System.out.println("3. Gestionar Proveedores");
            System.out.println("4. Gestionar Presupuestos y Costos");
            System.out.println("5. Agenda y Disponibilidad");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = leerEntero();

            switch (opcion) {
                case 1:
                    menuGestionarOrganizadores();
                    break;
                case 2:
                    if (organizadores.isEmpty()) {
                        System.out.println("\n⚠ Debe registrar al menos un organizador primero.");
                        System.out.println("Use la opción 1 para agregar un organizador.");
                        break;
                    }
                    menuGestionarEventos();
                    break;
                case 3:
                    if (organizadores.isEmpty()) {
                        System.out.println("\n⚠ Debe registrar al menos un organizador primero.");
                        System.out.println("Use la opción 1 para agregar un organizador.");
                        break;
                    }
                    menuGestionarProveedores();
                    break;
                case 4:
                    if (organizadores.isEmpty()) {
                        System.out.println("\n⚠ Debe registrar al menos un organizador primero.");
                        System.out.println("Use la opción 1 para agregar un organizador.");
                        break;
                    }
                    if (bodas.isEmpty()) {
                        System.out.println("\n⚠ No hay bodas registradas para analizar.");
                        System.out.println("Use la opción 2 para crear una boda.");
                        break;
                    }
                    menuGestionarPresupuestos();
                    break;
                case 5:
                    if (organizadores.isEmpty()) {
                        System.out.println("\n⚠ Debe registrar al menos un organizador primero.");
                        System.out.println("Use la opción 1 para agregar un organizador.");
                        break;
                    }
                    menuAgendaDisponibilidad();
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

    // Métodos auxiliares para lectura
    private static int leerEntero() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Ingrese un número válido: ");
            }
        }
    }

    private static int leerEnteroPositivo() {
        int valor;
        do {
            valor = leerEntero();
            if (valor <= 0) {
                System.out.print("Ingrese un valor positivo: ");
            }
        } while (valor <= 0);
        return valor;
    }

    private static double leerDoublePositivo() {
        double valor;
        do {
            try {
                valor = Double.parseDouble(scanner.nextLine());
                if (valor <= 0) {
                    System.out.print("Ingrese un valor positivo: ");
                } else {
                    return valor;
                }
            } catch (NumberFormatException e) {
                System.out.print("Ingrese un número válido: ");
            }
        } while (true);
    }

    private static Calendar leerFechaSoloDia() {
        System.out.print("Día (1-31): ");
        int dia = leerEntero();
        while (dia < 1 || dia > 31) {
            System.out.print("Día inválido. Ingrese día (1-31): ");
            dia = leerEntero();
        }

        System.out.print("Mes (1-12): ");
        int mes = leerEntero();
        while (mes < 1 || mes > 12) {
            System.out.print("Mes inválido. Ingrese mes (1-12): ");
            mes = leerEntero();
        }

        System.out.print("Año (ej: 2024): ");
        int año = leerEntero();
        while (año < 2023) {
            System.out.print("Año inválido. Ingrese año (2023 o mayor): ");
            año = leerEntero();
        }

        return Utilitario.crearFecha(dia, mes, año);
    }

    // MENÚ 1: GESTIONAR ORGANIZADORES (se mantiene igual)
    private static void menuGestionarOrganizadores() {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n=== GESTIÓN DE ORGANIZADORES ===");
            System.out.println("1. Agregar Organizador");
            System.out.println("2. Buscar Organizador por Nombre");
            System.out.println("3. Mostrar Todos los Organizadores");
            System.out.println("4. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");

            int opcion = leerEntero();

            switch (opcion) {
                case 1:
                    agregarOrganizador();
                    break;
                case 2:
                    if (organizadores.isEmpty()) {
                        System.out.println("\n⚠ No hay organizadores registrados.");
                        System.out.println("Use la opción 1 para agregar un organizador.");
                        break;
                    }
                    buscarOrganizadorPorNombre();
                    break;
                case 3:
                    if (organizadores.isEmpty()) {
                        System.out.println("\n⚠ No hay organizadores registrados.");
                        System.out.println("Use la opción 1 para agregar un organizador.");
                        break;
                    }
                    mostrarTodosOrganizadores();
                    break;
                case 4:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private static void agregarOrganizador() {
        System.out.println("\n=== AGREGAR ORGANIZADOR ===");

        System.out.print("Cédula: ");
        String cedula = scanner.nextLine();

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Especialidad: ");
        String especialidad = scanner.nextLine();

        System.out.print("Años de experiencia: ");
        int experiencia = leerEnteroPositivo();

        System.out.print("Contacto (email/teléfono): ");
        String contacto = scanner.nextLine();

        System.out.print("Token de seguridad: ");
        String token = scanner.nextLine();

        String id = Utilitario.generarIdOrganizador();
        Organizador org = new Organizador(id, cedula, nombre, especialidad, experiencia, contacto, token);
        organizadores.add(org);

        System.out.println("✓ Organizador agregado exitosamente. ID: " + id);
    }

    private static void buscarOrganizadorPorNombre() {
        System.out.print("\nIngrese el nombre del organizador a buscar: ");
        String nombre = scanner.nextLine();

        boolean encontrado = false;
        for (Organizador org : organizadores) {
            if (org.getNombre().equalsIgnoreCase(nombre)) {
                System.out.println("\n=== INFORMACIÓN DEL ORGANIZADOR ===");
                System.out.println("ID: " + org.getId());
                System.out.println("Cédula: " + org.getCedula());
                System.out.println("Nombre: " + org.getNombre());
                System.out.println("Especialidad: " + org.getEspecialidad());
                System.out.println("Años experiencia: " + org.getAñosExperiencia());
                System.out.println("Contacto: " + org.getContacto());

                System.out.println("\n=== EVENTOS ASOCIADOS ===");
                if (org.getEventosAsociados().isEmpty()) {
                    System.out.println("No tiene eventos asignados.");
                } else {
                    for (Boda boda : org.getEventosAsociados()) {
                        System.out.println("- " + boda.getNombreNovios() +
                                " | Fecha: " + Utilitario.formatearFecha(boda.getFechaEvento()));
                    }
                }
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            System.out.println("✗ Organizador no encontrado.");
        }
    }

    private static void mostrarTodosOrganizadores() {
        System.out.println("\n=== LISTA DE ORGANIZADORES ===");
        if (organizadores.isEmpty()) {
            System.out.println("No hay organizadores registrados.");
        } else {
            for (Organizador org : organizadores) {
                System.out.println(org.toStringSinToken());
                System.out.println("------------------------");
            }
        }
    }

    // MENÚ 2: GESTIONAR EVENTOS (se mantiene igual)
    private static void menuGestionarEventos() {
        if (organizadores.isEmpty()) {
            System.out.println("\n⚠ Debe registrar al menos un organizador primero.");
            return;
        }

        System.out.print("\nIngrese su token de autenticación: ");
        String token = scanner.nextLine();

        Organizador orgAutenticado = null;
        for (Organizador org : organizadores) {
            if (org.getTokenSeguridad().equals(token)) {
                orgAutenticado = org;
                break;
            }
        }

        if (orgAutenticado == null) {
            System.out.println("✗ Token inválido. Acceso denegado.");
            return;
        }

        System.out.println("✓ Autenticado como: " + orgAutenticado.getNombre());

        boolean volver = false;
        while (!volver) {
            System.out.println("\n=== GESTIÓN DE EVENTOS ===");
            System.out.println("Organizador: " + orgAutenticado.getNombre());
            System.out.println("1. Crear Boda");
            System.out.println("2. Listar Todas las Bodas");
            System.out.println("3. Eliminar Evento");
            System.out.println("4. Buscar Boda por Fecha");
            System.out.println("5. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");

            int opcion = leerEntero();

            switch (opcion) {
                case 1:
                    crearBoda(orgAutenticado);
                    break;
                case 2:
                    if (bodas.isEmpty()) {
                        System.out.println("\n⚠ No hay bodas registradas.");
                    } else {
                        listarBodas();
                    }
                    break;
                case 3:
                    if (bodas.isEmpty()) {
                        System.out.println("\n⚠ No hay bodas registradas para eliminar.");
                    } else {
                        eliminarEvento();
                    }
                    break;
                case 4:
                    if (bodas.isEmpty()) {
                        System.out.println("\n⚠ No hay bodas registradas para buscar.");
                    } else {
                        buscarBodaPorFecha();
                    }
                    break;
                case 5:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private static void crearBoda(Organizador organizador) {
        System.out.println("\n=== CREAR NUEVA BODA ===");

        String id;
        boolean idValido = false;
        do {
            System.out.print("ID de la boda: ");
            id = scanner.nextLine();
            idValido = true;
            for (Boda b : bodas) {
                if (b.getId().equals(id)) {
                    System.out.println("✗ Este ID ya existe. Ingrese otro.");
                    idValido = false;
                    break;
                }
            }
        } while (!idValido);

        System.out.println("\n--- FECHA DEL EVENTO ---");
        Calendar fechaEvento = leerFechaSoloDia();

        System.out.print("Horas de duración de la boda: ");
        int horas = leerEnteroPositivo();

        System.out.print("Lugar: ");
        String lugar = scanner.nextLine();

        System.out.print("Tema de color: ");
        String temaColor = scanner.nextLine();

        System.out.print("Número de invitados: ");
        int invitados = leerEnteroPositivo();

        System.out.print("Presupuesto para comida: $");
        double presupuestoComida = leerDoublePositivo();

        System.out.print("Presupuesto para salón: $");
        double presupuestoSalon = leerDoublePositivo();

        System.out.print("Presupuesto para banda: $");
        double presupuestoBanda = leerDoublePositivo();

        System.out.print("Tipo de ceremonia: ");
        String tipoCeremonia = scanner.nextLine();

        System.out.print("Canción del vals: ");
        String cancionVals = scanner.nextLine();

        System.out.print("Nombres de los novios: ");
        String nombreNovios = scanner.nextLine();

        Boda boda = new Boda(id, fechaEvento, horas, lugar, temaColor,
                invitados, presupuestoComida, presupuestoSalon, presupuestoBanda,
                tipoCeremonia, cancionVals, nombreNovios);
        boda.setOrganizador(organizador);
        organizador.agregarEvento(boda);
        bodas.add(boda);

        System.out.println("✓ Boda creada exitosamente!");
    }

    private static void listarBodas() {
        System.out.println("\n=== LISTA DE BODAS ===");
        if (bodas.isEmpty()) {
            System.out.println("No hay bodas registradas.");
            return;
        }

        Calendar ahora = Calendar.getInstance();
        System.out.println("\n--- BODAS PRÓXIMAS ---");
        boolean hayProximas = false;
        for (Boda boda : bodas) {
            if (boda.getFechaEvento().after(ahora) ||
                    Utilitario.mismasFechas(boda.getFechaEvento(), ahora)) {
                System.out.println(boda + " | Fecha: " + Utilitario.formatearFecha(boda.getFechaEvento()));
                hayProximas = true;
            }
        }
        if (!hayProximas) System.out.println("No hay bodas próximas.");

        System.out.println("\n--- BODAS PASADAS ---");
        boolean hayPasadas = false;
        for (Boda boda : bodas) {
            if (boda.getFechaEvento().before(ahora) &&
                    !Utilitario.mismasFechas(boda.getFechaEvento(), ahora)) {
                System.out.println(boda + " | Fecha: " + Utilitario.formatearFecha(boda.getFechaEvento()));
                hayPasadas = true;
            }
        }
        if (!hayPasadas) System.out.println("No hay bodas pasadas.");
    }

    private static void eliminarEvento() {
        System.out.print("\nIngrese el ID de la boda a eliminar: ");
        String id = scanner.nextLine();

        Boda bodaEliminar = null;
        for (Boda boda : bodas) {
            if (boda.getId().equals(id)) {
                bodaEliminar = boda;
                break;
            }
        }

        if (bodaEliminar != null) {
            bodas.remove(bodaEliminar);
            System.out.println("✓ Boda eliminada exitosamente.");
        } else {
            System.out.println("✗ Boda no encontrada.");
        }
    }

    private static void buscarBodaPorFecha() {
        System.out.println("\nIngrese la fecha a buscar:");
        Calendar fecha = leerFechaSoloDia();

        List<Boda> resultado = Utilitario.filtrarBodasPorFecha(bodas, fecha);

        if (resultado.isEmpty()) {
            System.out.println("No hay bodas en esa fecha.");
        } else {
            System.out.println("\n=== BODAS ENCONTRADAS ===");
            for (Boda boda : resultado) {
                System.out.println("ID: " + boda.getId());
                System.out.println("Novios: " + boda.getNombreNovios());
                System.out.println("Lugar: " + boda.getLugar());
                System.out.println("Organizador: " + boda.getOrganizador().getNombre());
                System.out.println("------------------------");
            }
        }
    }

    // MENÚ 3: GESTIONAR PROVEEDORES (actualizado con validaciones)
    private static void menuGestionarProveedores() {
        System.out.print("\nIngrese su token de autenticación: ");
        String token = scanner.nextLine();

        boolean tokenValido = false;
        for (Organizador org : organizadores) {
            if (org.getTokenSeguridad().equals(token)) {
                tokenValido = true;
                break;
            }
        }

        if (!tokenValido) {
            System.out.println("✗ Token inválido. Acceso denegado.");
            return;
        }

        System.out.println("✓ Autenticación exitosa.");

        boolean volver = false;
        while (!volver) {
            System.out.println("\n=== GESTIÓN DE PROVEEDORES ===");
            System.out.println("1. Ingresar Proveedor");
            System.out.println("2. Listar Proveedores");
            System.out.println("3. Eliminar Proveedor");
            System.out.println("4. Buscar Proveedores por Costo");
            System.out.println("5. Buscar Proveedores por Tipo");
            System.out.println("6. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");

            int opcion = leerEntero();

            switch (opcion) {
                case 1:
                    ingresarProveedor();
                    break;
                case 2:
                    if (proveedores.isEmpty()) {
                        System.out.println("\n⚠ No hay proveedores registrados.");
                        System.out.println("Use la opción 1 para agregar un proveedor.");
                        break;
                    }
                    listarProveedores();
                    break;
                case 3:
                    if (proveedores.isEmpty()) {
                        System.out.println("\n⚠ No hay proveedores registrados para eliminar.");
                        break;
                    }
                    eliminarProveedor();
                    break;
                case 4:
                    if (proveedores.isEmpty()) {
                        System.out.println("\n⚠ No hay proveedores registrados para buscar.");
                        break;
                    }
                    buscarProveedoresPorCosto();
                    break;
                case 5:
                    if (proveedores.isEmpty()) {
                        System.out.println("\n⚠ No hay proveedores registrados para buscar.");
                        break;
                    }
                    buscarProveedoresPorTipo();
                    break;
                case 6:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private static void ingresarProveedor() {
        System.out.println("\n=== INGRESAR NUEVO PROVEEDOR ===");
        System.out.println("Seleccione el tipo de proveedor:");
        System.out.println("1. Banda");
        System.out.println("2. Comida");
        System.out.println("3. Salón");
        System.out.print("Opción: ");

        int tipo = leerEntero();
        while (tipo < 1 || tipo > 3) {
            System.out.print("Opción inválida. Ingrese 1, 2 o 3: ");
            tipo = leerEntero();
        }

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();

        String id = Utilitario.generarIdProveedor();

        switch (tipo) {
            case 1: // Banda
                System.out.print("Costo por hora de espectáculo: $");
                double costoHoraBanda = leerDoublePositivo();

                System.out.print("Géneros que tocan (separados por coma): ");
                String generosStr = scanner.nextLine();
                String[] generos = generosStr.split(",");

                ProveedorBanda banda = new ProveedorBanda(id, nombre, telefono,
                        costoHoraBanda, generos);
                proveedores.add(banda);
                System.out.println("✓ Proveedor de banda agregado exitosamente.");
                break;

            case 2: // Comida
                System.out.print("Nombre del plato: ");
                String nombrePlato = scanner.nextLine();

                System.out.print("Costo por persona: $");
                double costoPersona = leerDoublePositivo();

                System.out.print("Nombre del catering: ");
                String catering = scanner.nextLine();

                ProveedorComida comida = new ProveedorComida(id, nombre, telefono,
                        nombrePlato, costoPersona, catering);
                proveedores.add(comida);
                System.out.println("✓ Proveedor de comida agregado exitosamente.");
                break;

            case 3: // Salón
                System.out.print("Ubicación: ");
                String ubicacion = scanner.nextLine();

                System.out.print("Capacidad máxima de invitados: ");
                int capacidad = leerEnteroPositivo();

                System.out.print("Costo por hora de uso: $");
                double costoHoraSalon = leerDoublePositivo();

                ProveedorSalon salon = new ProveedorSalon(id, nombre, telefono,
                        ubicacion, capacidad, costoHoraSalon);
                proveedores.add(salon);
                System.out.println("✓ Proveedor de salón agregado exitosamente.");
                break;
        }
    }

    private static void listarProveedores() {
        System.out.println("\n=== LISTA DE PROVEEDORES ===");
        if (proveedores.isEmpty()) {
            System.out.println("No hay proveedores registrados.");
        } else {
            for (Proveedor p : proveedores) {
                System.out.println(p);
            }
        }
    }

    private static void eliminarProveedor() {
        System.out.print("\nIngrese el ID del proveedor a eliminar: ");
        String id = scanner.nextLine();

        Proveedor proveedorEliminar = null;
        for (Proveedor p : proveedores) {
            if (p.getId().equals(id)) {
                proveedorEliminar = p;
                break;
            }
        }

        if (proveedorEliminar != null) {
            proveedores.remove(proveedorEliminar);
            System.out.println("✓ Proveedor eliminado exitosamente.");
        } else {
            System.out.println("✗ Proveedor no encontrado.");
        }
    }

    private static void buscarProveedoresPorCosto() {
        System.out.print("\nIngrese el costo máximo: $");
        double costoMaximo = leerDoublePositivo();

        List<Proveedor> resultado = Utilitario.filtrarProveedoresPorCosto(proveedores, costoMaximo);

        if (resultado.isEmpty()) {
            System.out.println("No hay proveedores con costo menor o igual a $" + costoMaximo);
        } else {
            System.out.println("\n=== PROVEEDORES ENCONTRADOS ===");
            for (Proveedor p : resultado) {
                System.out.println(p);
            }
        }
    }

    private static void buscarProveedoresPorTipo() {
        System.out.println("\nSeleccione el tipo de proveedor:");
        System.out.println("1. Banda");
        System.out.println("2. Comida");
        System.out.println("3. Salón");
        System.out.print("Opción: ");

        int tipo = leerEntero();
        String tipoStr = "";

        switch (tipo) {
            case 1: tipoStr = "Banda"; break;
            case 2: tipoStr = "Comida"; break;
            case 3: tipoStr = "Salon"; break;
            default:
                System.out.println("Opción inválida.");
                return;
        }

        List<Proveedor> resultado = Utilitario.filtrarProveedoresPorTipo(proveedores, tipoStr);

        if (resultado.isEmpty()) {
            System.out.println("No hay proveedores de tipo " + tipoStr);
        } else {
            System.out.println("\n=== PROVEEDORES DE " + tipoStr.toUpperCase() + " ===");
            for (Proveedor p : resultado) {
                System.out.println(p);
            }
        }
    }

    // MENÚ 4: GESTIONAR PRESUPUESTOS Y COSTOS (actualizado con opción imprimir proforma)
    private static void menuGestionarPresupuestos() {
        if (bodas.isEmpty()) {
            System.out.println("\n⚠ No hay bodas registradas para analizar.");
            return;
        }

        boolean volver = false;
        while (!volver) {
            System.out.println("\n=== GESTIÓN DE PRESUPUESTOS Y COSTOS ===");
            System.out.println("1. Recomendar Proveedores");
            System.out.println("2. Imprimir Proforma");
            System.out.println("3. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");

            int opcion = leerEntero();

            switch (opcion) {
                case 1:
                    if (proveedores.isEmpty()) {
                        System.out.println("\n⚠ No hay proveedores registrados para comparar.");
                        break;
                    }
                    recomendarProveedoresParaBoda();
                    break;
                case 2:
                    imprimirProforma();
                    break;
                case 3:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private static void recomendarProveedoresParaBoda() {
        System.out.println("\nSeleccione una boda para analizar:");

        for (int i = 0; i < bodas.size(); i++) {
            System.out.println((i + 1) + ". " + bodas.get(i).getNombreNovios() +
                    " - " + Utilitario.formatearFecha(bodas.get(i).getFechaEvento()));
        }

        System.out.print("Seleccione una boda (0 para volver): ");
        int opcion = leerEntero();

        if (opcion == 0) return;

        if (opcion < 1 || opcion > bodas.size()) {
            System.out.println("Opción inválida.");
            return;
        }

        Boda bodaSeleccionada = bodas.get(opcion - 1);
        Utilitario.recomendarProveedores(bodaSeleccionada, proveedores);
    }

    private static void imprimirProforma() {
        System.out.println("\n=== IMPRIMIR PROFORMA ===");
        System.out.println("Seleccione una boda para generar proforma:");

        for (int i = 0; i < bodas.size(); i++) {
            System.out.println((i + 1) + ". " + bodas.get(i).getNombreNovios() +
                    " - " + Utilitario.formatearFecha(bodas.get(i).getFechaEvento()));
        }

        System.out.print("Seleccione una boda (0 para volver): ");
        int opcion = leerEntero();

        if (opcion == 0) return;

        if (opcion < 1 || opcion > bodas.size()) {
            System.out.println("Opción inválida.");
            return;
        }

        Boda boda = bodas.get(opcion - 1);
        generarProforma(boda);
    }

    private static void generarProforma(Boda boda) {
        System.out.println("\n==========================================");
        System.out.println("           PROFORMA DE BODA");
        System.out.println("==========================================");

        // Datos principales de la boda
        System.out.println("\n--- DATOS PRINCIPALES ---");
        System.out.println("ID de la boda: " + boda.getId());
        System.out.println("Novios: " + boda.getNombreNovios());
        System.out.println("Fecha: " + Utilitario.formatearFecha(boda.getFechaEvento()));
        System.out.println("Lugar: " + boda.getLugar());
        System.out.println("Duración: " + boda.getHorasDuracion() + " horas");
        System.out.println("Invitados: " + boda.getNumeroInvitados());

        // Datos secundarios
        System.out.println("\n--- DATOS SECUNDARIOS ---");
        System.out.println("Tema de color: " + boda.getTemaColor());
        System.out.println("Tipo de ceremonia: " + boda.getTipoCeremonia());
        System.out.println("Canción del vals: " + boda.getCancionVals());

        // Organizador a cargo
        System.out.println("\n--- ORGANIZADOR A CARGO ---");
        if (boda.getOrganizador() != null) {
            Organizador org = boda.getOrganizador();
            System.out.println("Nombre: " + org.getNombre());
            System.out.println("Especialidad: " + org.getEspecialidad());
            System.out.println("Experiencia: " + org.getAñosExperiencia() + " años");
            System.out.println("Contacto: " + org.getContacto());
        } else {
            System.out.println("No asignado");
        }

        // Presupuestos
        System.out.println("\n--- PRESUPUESTOS ASIGNADOS ---");
        System.out.println("Presupuesto para comida: $" + String.format("%.2f", boda.getPresupuestoComida()));
        System.out.println("Presupuesto para salón: $" + String.format("%.2f", boda.getPresupuestoSalon()));
        System.out.println("Presupuesto para banda: $" + String.format("%.2f", boda.getPresupuestoBanda()));

        // Opciones disponibles
        System.out.println("\n--- OPCIONES DISPONIBLES ---");
        List<ProveedorComida> comidasAptas = new ArrayList<>();
        List<ProveedorSalon> salonesAptos = new ArrayList<>();
        List<ProveedorBanda> bandasAptas = new ArrayList<>();

        for (Proveedor p : proveedores) {
            if (Utilitario.esProveedorApto(boda, p)) {
                if (p instanceof ProveedorComida) {
                    comidasAptas.add((ProveedorComida) p);
                } else if (p instanceof ProveedorSalon) {
                    salonesAptos.add((ProveedorSalon) p);
                } else if (p instanceof ProveedorBanda) {
                    bandasAptas.add((ProveedorBanda) p);
                }
            }
        }

        System.out.println("\n1. OPCIONES DE COMIDA:");
        if (comidasAptas.isEmpty()) {
            System.out.println("   ✗ No hay opciones de comida dentro del presupuesto.");
            System.out.println("   Recomendación: Aumentar presupuesto o buscar otro proveedor.");
        } else {
            for (int i = 0; i < comidasAptas.size(); i++) {
                ProveedorComida comida = comidasAptas.get(i);
                double costoTotal = Utilitario.calcularConIVA(comida.calcularCosto(boda.getNumeroInvitados()));
                System.out.println("   " + (i + 1) + ". " + comida.getNombreCatering() +
                        " - " + comida.getNombrePlato() +
                        " - $" + comida.getCostoPorPersona() + "/persona" +
                        " - Total: $" + String.format("%.2f", costoTotal) + " (con IVA)");
            }
        }

        System.out.println("\n2. OPCIONES DE SALÓN:");
        if (salonesAptos.isEmpty()) {
            System.out.println("   ✗ No hay opciones de salón dentro del presupuesto.");
            System.out.println("   Recomendación: Aumentar presupuesto o buscar otro proveedor.");
        } else {
            for (int i = 0; i < salonesAptos.size(); i++) {
                ProveedorSalon salon = salonesAptos.get(i);
                double costoTotal = Utilitario.calcularConIVA(salon.calcularCosto(boda.getHorasDuracion()));
                System.out.println("   " + (i + 1) + ". " + salon.getNombre() +
                        " - " + salon.getUbicacion() +
                        " - Capacidad: " + salon.getCapacidadMaxima() +
                        " - $" + salon.getCostoPorHora() + "/hora" +
                        " - Total: $" + String.format("%.2f", costoTotal) + " (con IVA)");
            }
        }

        System.out.println("\n3. OPCIONES DE BANDA:");
        if (bandasAptas.isEmpty()) {
            System.out.println("   ✗ No hay opciones de banda dentro del presupuesto.");
            System.out.println("   Recomendación: Aumentar presupuesto o buscar otro proveedor.");
        } else {
            for (int i = 0; i < bandasAptas.size(); i++) {
                ProveedorBanda banda = bandasAptas.get(i);
                double costoTotal = Utilitario.calcularConIVA(banda.calcularCosto(boda.getHorasDuracion()));
                System.out.println("   " + (i + 1) + ". " + banda.getNombre() +
                        " - $" + banda.getCostoPorHora() + "/hora" +
                        " - Total: $" + String.format("%.2f", costoTotal) + " (con IVA)");
            }
        }

        // Calcular total estimado
        System.out.println("\n--- ESTIMADO TOTAL A PAGAR ---");
        double totalMinimo = calcularTotalMinimo(boda, comidasAptas, salonesAptos, bandasAptas);
        double totalMaximo = calcularTotalMaximo(boda, comidasAptas, salonesAptos, bandasAptas);

        System.out.println("Rango estimado: $" + String.format("%.2f", totalMinimo) +
                " - $" + String.format("%.2f", totalMaximo) + " (incluye IVA)");

        System.out.println("\n==========================================");
        System.out.println("       FIN DE LA PROFORMA");
        System.out.println("==========================================");
    }

    private static double calcularTotalMinimo(Boda boda, List<ProveedorComida> comidas,
                                              List<ProveedorSalon> salones, List<ProveedorBanda> bandas) {
        double minComida = comidas.isEmpty() ? 0 :
                Utilitario.calcularConIVA(Collections.min(comidas,
                                Comparator.comparingDouble(ProveedorComida::getCostoPorPersona))
                        .calcularCosto(boda.getNumeroInvitados()));

        double minSalon = salones.isEmpty() ? 0 :
                Utilitario.calcularConIVA(Collections.min(salones,
                                Comparator.comparingDouble(ProveedorSalon::getCostoPorHora))
                        .calcularCosto(boda.getHorasDuracion()));

        double minBanda = bandas.isEmpty() ? 0 :
                Utilitario.calcularConIVA(Collections.min(bandas,
                                Comparator.comparingDouble(ProveedorBanda::getCostoPorHora))
                        .calcularCosto(boda.getHorasDuracion()));

        return minComida + minSalon + minBanda;
    }

    private static double calcularTotalMaximo(Boda boda, List<ProveedorComida> comidas,
                                              List<ProveedorSalon> salones, List<ProveedorBanda> bandas) {
        double maxComida = comidas.isEmpty() ? boda.getPresupuestoComida() :
                Utilitario.calcularConIVA(Collections.max(comidas,
                                Comparator.comparingDouble(ProveedorComida::getCostoPorPersona))
                        .calcularCosto(boda.getNumeroInvitados()));

        double maxSalon = salones.isEmpty() ? boda.getPresupuestoSalon() :
                Utilitario.calcularConIVA(Collections.max(salones,
                                Comparator.comparingDouble(ProveedorSalon::getCostoPorHora))
                        .calcularCosto(boda.getHorasDuracion()));

        double maxBanda = bandas.isEmpty() ? boda.getPresupuestoBanda() :
                Utilitario.calcularConIVA(Collections.max(bandas,
                                Comparator.comparingDouble(ProveedorBanda::getCostoPorHora))
                        .calcularCosto(boda.getHorasDuracion()));

        return maxComida + maxSalon + maxBanda;
    }

    // MENÚ 5: AGENDA Y DISPONIBILIDAD (actualizado)
    private static void menuAgendaDisponibilidad() {
        if (bodas.isEmpty()) {
            System.out.println("\n⚠ No hay eventos registrados para verificar disponibilidad.");
            return;
        }

        if (proveedores.isEmpty()) {
            System.out.println("\n⚠ No hay proveedores registrados.");
            return;
        }

        System.out.println("\n=== AGENDA Y DISPONIBILIDAD ===");
        System.out.println("Seleccione un evento para verificar disponibilidad de salón:");

        for (int i = 0; i < bodas.size(); i++) {
            Boda boda = bodas.get(i);
            System.out.println((i + 1) + ". " + boda.getNombreNovios() +
                    " - Fecha: " + Utilitario.formatearFecha(boda.getFechaEvento()) +
                    " - Lugar: " + boda.getLugar());
        }

        System.out.print("Seleccione un evento (0 para volver): ");
        int opcion = leerEntero();

        if (opcion == 0) return;

        if (opcion < 1 || opcion > bodas.size()) {
            System.out.println("Opción inválida.");
            return;
        }

        Boda bodaSeleccionada = bodas.get(opcion - 1);
        verificarDisponibilidadConPresupuesto(bodaSeleccionada);
    }

    private static void verificarDisponibilidadConPresupuesto(Boda boda) {
        System.out.println("\n=== VERIFICANDO DISPONIBILIDAD PARA ===");
        System.out.println("Evento: " + boda.getNombreNovios());
        System.out.println("Fecha: " + Utilitario.formatearFecha(boda.getFechaEvento()));
        System.out.println("Duración: " + boda.getHorasDuracion() + " horas");
        System.out.println("Lugar: " + boda.getLugar());
        System.out.println("Invitados: " + boda.getNumeroInvitados());
        System.out.println("Presupuesto salón: $" + String.format("%.2f", boda.getPresupuestoSalon()));

        // Filtrar salones que se ajustan al presupuesto
        List<ProveedorSalon> salonesAptos = new ArrayList<>();
        for (Proveedor p : proveedores) {
            if (p instanceof ProveedorSalon && Utilitario.esProveedorApto(boda, p)) {
                salonesAptos.add((ProveedorSalon) p);
            }
        }

        if (salonesAptos.isEmpty()) {
            System.out.println("\n✗ No hay salones que se ajusten al presupuesto.");
            return;
        }

        // Mostrar salones aptos
        System.out.println("\n=== SALONES QUE SE AJUSTAN AL PRESUPUESTO ===");
        for (int i = 0; i < salonesAptos.size(); i++) {
            ProveedorSalon salon = salonesAptos.get(i);
            double costoTotal = Utilitario.calcularConIVA(salon.calcularCosto(boda.getHorasDuracion()));
            System.out.println((i + 1) + ". " + salon.getNombre() +
                    " - Ubicación: " + salon.getUbicacion() +
                    " - Capacidad: " + salon.getCapacidadMaxima() +
                    " - Costo/hora: $" + salon.getCostoPorHora() +
                    " - Total: $" + String.format("%.2f", costoTotal) + " (con IVA)");
        }

        System.out.print("\nSeleccione un salón para verificar disponibilidad (0 para volver): ");
        int opcionSalon = leerEntero();

        if (opcionSalon == 0) return;

        if (opcionSalon < 1 || opcionSalon > salonesAptos.size()) {
            System.out.println("Opción inválida.");
            return;
        }

        ProveedorSalon salonSeleccionado = salonesAptos.get(opcionSalon - 1);
        verificarDisponibilidadSalon(salonSeleccionado, boda);
    }

    private static void verificarDisponibilidadSalon(ProveedorSalon salon, Boda boda) {
        boolean disponible = Utilitario.validarDisponibilidadSalon(salon, boda.getFechaEvento(),
                boda.getHorasDuracion(), contratos);

        System.out.println("\n=== VERIFICACIÓN DE DISPONIBILIDAD ===");
        System.out.println("Salón: " + salon.getNombre());
        System.out.println("Ubicación: " + salon.getUbicacion());
        System.out.println("Fecha del evento: " + Utilitario.formatearFecha(boda.getFechaEvento()));

        if (disponible) {
            System.out.println("\n✓ El salón ESTÁ DISPONIBLE para esa fecha.");

            // Calcular costo
            double costoBase = salon.calcularCosto(boda.getHorasDuracion());
            double costoConIVA = Utilitario.calcularConIVA(costoBase);

            System.out.println("\nDetalles de costo:");
            System.out.println("Horas: " + boda.getHorasDuracion() + " horas");
            System.out.println("Costo por hora: $" + salon.getCostoPorHora());
            System.out.println("Costo base: $" + String.format("%.2f", costoBase));
            System.out.println("IVA (12%): $" + String.format("%.2f", costoBase * 0.12));
            System.out.println("TOTAL: $" + String.format("%.2f", costoConIVA));

            // Verificar si está dentro del presupuesto
            if (costoConIVA <= boda.getPresupuestoSalon()) {
                System.out.println("\n✓ Este salón está DENTRO del presupuesto asignado.");

                // Preguntar si quiere reservar
                System.out.print("\n¿Desea reservar este salón para el evento? (S/N): ");
                String respuesta = scanner.nextLine();

                if (respuesta.equalsIgnoreCase("S")) {
                    reservarSalon(salon, boda, costoConIVA);
                }
            } else {
                System.out.println("\n⚠ Este salón EXCEDE el presupuesto asignado.");
                System.out.println("Presupuesto asignado: $" + String.format("%.2f", boda.getPresupuestoSalon()));
                System.out.println("Costo total: $" + String.format("%.2f", costoConIVA));
                System.out.println("Diferencia: $" + String.format("%.2f", (costoConIVA - boda.getPresupuestoSalon())));
            }
        } else {
            System.out.println("\n✗ El salón NO ESTÁ DISPONIBLE para esa fecha.");
            System.out.println("Este salón ya tiene una reserva para esa fecha.");

            // Mostrar eventos que usan este salón en la misma fecha
            System.out.println("\nEventos que usan este salón:");
            boolean hayEventos = false;
            for (Contrato contrato : contratos) {
                if (contrato.getProveedor().equals(salon) &&
                        Utilitario.mismasFechas(contrato.getBoda().getFechaEvento(), boda.getFechaEvento())) {
                    System.out.println("- " + contrato.getBoda().getNombreNovios() +
                            " | Fecha: " + Utilitario.formatearFecha(contrato.getBoda().getFechaEvento()));
                    hayEventos = true;
                }
            }
            if (!hayEventos) {
                System.out.println("(No se encontraron eventos específicos)");
            }
        }
    }

    private static void reservarSalon(ProveedorSalon salon, Boda boda, double costoTotal) {
        String idContrato = Utilitario.generarIdContrato();
        Contrato contrato = new Contrato(idContrato, boda, salon, costoTotal);
        contratos.add(contrato);

        System.out.println("\n✓ ¡RESERVA EXITOSA!");
        System.out.println("Contrato ID: " + idContrato);
        System.out.println("Salón: " + salon.getNombre());
        System.out.println("Evento: " + boda.getNombreNovios());
        System.out.println("Fecha: " + Utilitario.formatearFecha(boda.getFechaEvento()));
        System.out.println("Total a pagar: $" + String.format("%.2f", costoTotal));
        System.out.println("\nRecuerde que debe confirmar la reserva con el pago correspondiente.");
    }
}