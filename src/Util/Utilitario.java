package Util;
import Negocio.*;
import java.util.*;
import java.util.GregorianCalendar;

public class Utilitario {
    private static final double IVA = 0.15;
    private int idProveedorCounter = 2000;
    private int idOrganizadorCounter = 3000;

    private List<Boda> bodas = new ArrayList<>();
    private List<Proveedor> proveedores = new ArrayList<>();
    private List<Organizador> organizadores = new ArrayList<>();

    private Scanner scanner = new Scanner(System.in);

    // MÉTODO PARA MOSTRAR EL MENÚ PRINCIPAL
    public void mostrarMenuPrincipal() {
        System.out.println("\n=== SISTEMA DE GESTIÓN DE BODAS ===");
        System.out.println("1. Gestionar Organizadores");
        System.out.println("2. Gestionar Eventos");
        System.out.println("3. Gestionar Proveedores");
        System.out.println("4. Gestionar Presupuestos y Costos");
        System.out.println("5. Análisis Post-Evento");
        System.out.println("6. Salir");
        System.out.print("Seleccione una opción: ");
    }

    // MÉTODO PARA LEER ENTEROS
    public int leerEntero() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Ingrese un número válido: ");
            }
        }
    }

    // MENÚ 1: GESTIONAR ORGANIZADORES
    public void menuGestionarOrganizadores() {
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
                        System.out.println("\n No hay organizadores registrados.");
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

    private void agregarOrganizador() {
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
        String id = "ORG" + (++idOrganizadorCounter);
        Organizador org = new Organizador(id, cedula, nombre, especialidad, experiencia, contacto, token);
        organizadores.add(org);
        System.out.println("Organizador agregado exitosamente. ID: " + id);
    }

    public int leerEnteroPositivo() {
        int valor;
        do {
            valor = leerEntero();
            if (valor <= 0) {
                System.out.print("Ingrese un valor positivo: ");
            }
        } while (valor <= 0);
        return valor;
    }

    private void buscarOrganizadorPorNombre() {
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
                                " | Fecha: " + formatearFecha(boda.getFechaEvento()));
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

    private void mostrarTodosOrganizadores() {
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

    // MENÚ 2: GESTIONAR EVENTOS
    public void menuGestionarEventos() {
        if (organizadores.isEmpty()) {
            System.out.println("\nDebe registrar al menos un organizador primero.");
            System.out.println("Use la opción 1 para agregar un organizador.");
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
            System.out.println("Token inválido. Acceso denegado.");
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
                        System.out.println("\nNo hay bodas registradas.");
                    } else {
                        listarBodas();
                    }
                    break;
                case 3:
                    if (bodas.isEmpty()) {
                        System.out.println("\nNo hay bodas registradas para eliminar.");
                    } else {
                        eliminarEvento();
                    }
                    break;
                case 4:
                    if (bodas.isEmpty()) {
                        System.out.println("\nNo hay bodas registradas para buscar.");
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

    public void crearBoda(Organizador organizador) {
        System.out.println("\n=== CREAR NUEVA BODA ===");
        String id;
        boolean idValido = false;
        do {
            System.out.print("ID de la boda: ");
            id = scanner.nextLine();
            idValido = true;
            for (Boda b : bodas) {
                if (b.getId().equals(id)) {
                    System.out.println("Este ID ya existe. Ingrese otro.");
                    idValido = false;
                    break;
                }
            }
        } while (!idValido);
        System.out.println("\n--- FECHA DEL EVENTO ---");
        Calendar fechaEvento = leerFechaSoloDia();
        System.out.print("Horas de duración de la boda: ");
        int horas = leerEnteroPositivo();
        System.out.print("Lugares de preferencia: ");
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
        System.out.println("Boda creada exitosamente!");
    }

    private Calendar leerFechaSoloDia() {
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
        return crearFecha(dia, mes, año);
    }

    public double leerDoublePositivo() {
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

    private void listarBodas() {
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
                    mismasFechas(boda.getFechaEvento(), ahora)) {
                System.out.println(boda + " | Fecha: " + formatearFecha(boda.getFechaEvento()));
                hayProximas = true;
            }
        }
        if (!hayProximas) System.out.println("No hay bodas próximas.");
        System.out.println("\n--- BODAS PASADAS ---");
        boolean hayPasadas = false;
        for (Boda boda : bodas) {
            if (boda.getFechaEvento().before(ahora) &&
                    !mismasFechas(boda.getFechaEvento(), ahora)) {
                System.out.println(boda + " | Fecha: " + formatearFecha(boda.getFechaEvento()));
                hayPasadas = true;
            }
        }
        if (!hayPasadas) System.out.println("No hay bodas pasadas.");
    }
    public void eliminarEvento() {
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
            System.out.println("Boda eliminada exitosamente.");
        } else {
            System.out.println("Boda no encontrada.");
        }
    }

    private void buscarBodaPorFecha() {
        System.out.println("\nIngrese la fecha a buscar:");
        Calendar fecha = leerFechaSoloDia();
        List<Boda> resultado = filtrarBodasPorFecha(bodas, fecha);
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

    // MENÚ 3: GESTIONAR PROVEEDORES
    public void menuGestionarProveedores() {
        if (organizadores.isEmpty()) {
            System.out.println("\nDebe registrar al menos un organizador primero.");
            System.out.println("Use la opción 1 para agregar un organizador.");
            return;
        }
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
            System.out.println("Token inválido. Acceso denegado.");
            return;
        }
        System.out.println("Autenticación exitosa.");
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
                        System.out.println("\nNo hay proveedores registrados.");
                        System.out.println("Use la opción 1 para agregar un proveedor.");
                        break;
                    }
                    listarProveedores();
                    break;
                case 3:
                    if (proveedores.isEmpty()) {
                        System.out.println("\nNo hay proveedores registrados para eliminar.");
                        break;
                    }
                    eliminarProveedor();
                    break;
                case 4:
                    if (proveedores.isEmpty()) {
                        System.out.println("\nNo hay proveedores registrados para buscar.");
                        break;
                    }
                    buscarProveedoresPorCosto();
                    break;
                case 5:
                    if (proveedores.isEmpty()) {
                        System.out.println("\nNo hay proveedores registrados para buscar.");
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

    private void ingresarProveedor() {
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
        String id = "PROV" + (++idProveedorCounter);

        switch (tipo) {
            case 1: // Banda
                System.out.print("Costo por hora de espectáculo: $");
                double costoHoraBanda = leerDoublePositivo();
                System.out.print("Géneros que tocan (separados por coma): ");
                String generosStr = scanner.nextLine();
                String[] generos = generosStr.split(","); //HOLA
                ProveedorBanda banda = new ProveedorBanda(id, nombre, telefono,
                        costoHoraBanda, generos);
                proveedores.add(banda);
                System.out.println("Proveedor de banda agregado exitosamente.");
                break;

            case 2: // Comida
                System.out.print("Menú (Entrada, Plato fuerte y su bedida, Postre: ");
                String nombrePlato = scanner.nextLine();
                System.out.print("Costo por persona: $");
                double costoPersona = leerDoublePositivo();
                System.out.print("Nombre del catering: ");
                String catering = scanner.nextLine();
                ProveedorComida comida = new ProveedorComida(id, nombre, telefono,
                        nombrePlato, costoPersona, catering);
                proveedores.add(comida);
                System.out.println("Proveedor de comida agregado exitosamente.");
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
                System.out.println("Proveedor de salón agregado exitosamente.");
                break;
        }
    }

    private void listarProveedores() {
        System.out.println("\n=== LISTA DE PROVEEDORES ===");
        if (proveedores.isEmpty()) {
            System.out.println("No hay proveedores registrados.");
        } else {
            for (Proveedor p : proveedores) {
                System.out.println(p);
            }
        }
    }

    public void eliminarProveedor() {
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
            System.out.println("Proveedor eliminado exitosamente.");
        } else {
            System.out.println("Proveedor no encontrado.");
        }
    }

    public void buscarProveedoresPorCosto() {
        System.out.print("\nIngrese el costo máximo: $");
        double costoMaximo = leerDoublePositivo();
        List<Proveedor> resultado = filtrarProveedoresPorCosto(proveedores, costoMaximo);
        if (resultado.isEmpty()) {
            System.out.println("No hay proveedores con costo menor o igual a $" + costoMaximo);
        } else {
            System.out.println("\n=== PROVEEDORES ENCONTRADOS ===");
            for (Proveedor p : resultado) {
                System.out.println(p);
            }
        }
    }

    public void buscarProveedoresPorTipo() {
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

        List<Proveedor> resultado = filtrarProveedoresPorTipo(proveedores, tipoStr);
        if (resultado.isEmpty()) {
            System.out.println("No hay proveedores de tipo " + tipoStr);
        } else {
            System.out.println("\n=== PROVEEDORES DE " + tipoStr.toUpperCase() + " ===");
            for (Proveedor p : resultado) {
                System.out.println(p);
            }
        }
    }

    // MENÚ 4: GESTIONAR PRESUPUESTOS Y COSTOS
    public void menuGestionarPresupuestos() {
        if (organizadores.isEmpty()) {
            System.out.println("\nDebe registrar al menos un organizador primero.");
            System.out.println("Use la opción 1 para agregar un organizador.");
            return;
        }
        if (bodas.isEmpty()) {
            System.out.println("\nNo hay bodas registradas para analizar.");
            System.out.println("Use la opción 2 para crear una boda.");
            return;
        }
        boolean volver = false;
        while (!volver) {
            System.out.println("\n=== GESTIÓN DE PRESUPUESTOS Y COSTOS ===");
            System.out.println("1. Recomendar Proveedores (con disponibilidad)");
            System.out.println("2. Imprimir Proforma");
            System.out.println("3. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            int opcion = leerEntero();
            switch (opcion) {
                case 1:
                    if (proveedores.isEmpty()) {
                        System.out.println("\nNo hay proveedores registrados para comparar.");
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

    public void recomendarProveedoresParaBoda() {
        System.out.println("\n=== RECOMENDAR PROVEEDORES CON DISPONIBILIDAD ===");

        if (bodas.isEmpty()) {
            System.out.println("No hay bodas registradas.");
            return;
        }
        System.out.println("Seleccione una boda:");
        for (int i = 0; i < bodas.size(); i++) {
            Boda boda = bodas.get(i);
            System.out.println((i + 1) + ". " + boda.getNombreNovios() +
                    " - Fecha: " + formatearFecha(boda.getFechaEvento()));
        }
        System.out.print("Seleccione una boda (0 para volver): ");
        int opcion = leerEntero();
        if (opcion == 0 || opcion > bodas.size()) {
            return;
        }
        Boda bodaSeleccionada = bodas.get(opcion - 1);
        System.out.println("\n=== RECOMENDACIONES PARA: " + bodaSeleccionada.getNombreNovios() + " ===");
        System.out.println("Fecha: " + formatearFecha(bodaSeleccionada.getFechaEvento()));
        System.out.println("Invitados: " + bodaSeleccionada.getNumeroInvitados());
        System.out.println("Presupuesto total: $" + String.format("%.2f",
                (bodaSeleccionada.getPresupuestoComida() +
                        bodaSeleccionada.getPresupuestoSalon() +
                        bodaSeleccionada.getPresupuestoBanda())));

        System.out.println("----------------------------------------");
        System.out.println("\n📅 VERIFICACIÓN DE DISPONIBILIDAD POR FECHA:");
        List<Boda> bodasContratadasMismaFecha = new ArrayList<>();
        for (Boda otraBoda : bodas) {
            if (!otraBoda.getId().equals(bodaSeleccionada.getId()) &&
                    otraBoda.isProformaAceptada() &&
                    mismasFechas(otraBoda.getFechaEvento(), bodaSeleccionada.getFechaEvento())) {
                bodasContratadasMismaFecha.add(otraBoda);
            }
        }
        if (bodasContratadasMismaFecha.isEmpty()) {
            System.out.println("No hay eventos contratados para esta fecha.");
            System.out.println("Disponibilidad total de proveedores.");
        } else {
            System.out.println("Hay " + bodasContratadasMismaFecha.size() +
                    " evento(s) contratado(s) para la misma fecha.");
            System.out.println("  Algunos proveedores podrían no estar disponibles.");
        }
        System.out.println("\n--- PROVEEDORES DE COMIDA ---");
        System.out.println("Presupuesto: $" + String.format("%.2f", bodaSeleccionada.getPresupuestoComida()));
        System.out.println("Requerimiento: " + bodaSeleccionada.getNumeroInvitados() + " personas");
        List<Proveedor> proveedoresComida = filtrarProveedoresPorTipo(proveedores, "Comida");
        List<Proveedor> recomendadosComida = new ArrayList<>();
        for (Proveedor p : proveedoresComida) {
            if (p instanceof ProveedorComida) {
                ProveedorComida comida = (ProveedorComida) p;
                double costoTotal = comida.getCostoPorPersona() * bodaSeleccionada.getNumeroInvitados();
                boolean dentroPresupuesto = costoTotal <= bodaSeleccionada.getPresupuestoComida();
                boolean disponible = estaProveedorDisponible(comida, bodaSeleccionada.getFechaEvento());
                if (dentroPresupuesto && disponible) {
                    recomendadosComida.add(p);
                    System.out.println("✓ " + comida.getNombre() +
                            " - Plato: " + comida.getNombrePlato() +
                            " - Costo total: $" + String.format("%.2f", costoTotal) +
                            " - ✅ DISPONIBLE");
                } else if (dentroPresupuesto && !disponible) {
                    System.out.println("✗ " + comida.getNombre() +
                            " - Plato: " + comida.getNombrePlato() +
                            " - Costo total: $" + String.format("%.2f", costoTotal) +
                            " - ❌ NO DISPONIBLE (ya contratado)");
                } else if (!dentroPresupuesto) {
                    System.out.println("✗ " + comida.getNombre() +
                            " - Plato: " + comida.getNombrePlato() +
                            " - Costo total: $" + String.format("%.2f", costoTotal) +
                            " - 💰 FUERA DE PRESUPUESTO");
                }
            }
        }
        if (recomendadosComida.isEmpty()) {
            System.out.println("No hay proveedores de comida disponibles dentro del presupuesto.");
        }
        System.out.println("\n--- PROVEEDORES DE SALÓN ---");
        System.out.println("Presupuesto: $" + String.format("%.2f", bodaSeleccionada.getPresupuestoSalon()));
        System.out.println("Requerimiento: Capacidad para " + bodaSeleccionada.getNumeroInvitados() + " personas");
        List<Proveedor> proveedoresSalon = filtrarProveedoresPorTipo(proveedores, "Salon");
        List<Proveedor> recomendadosSalon = new ArrayList<>();
        for (Proveedor p : proveedoresSalon) {
            if (p instanceof ProveedorSalon) {
                ProveedorSalon salon = (ProveedorSalon) p;
                double costoTotal = salon.getCostoPorHora() * bodaSeleccionada.getHorasDuracion();
                boolean dentroPresupuesto = costoTotal <= bodaSeleccionada.getPresupuestoSalon();
                boolean capacidadSuficiente = salon.getCapacidadMaxima() >= bodaSeleccionada.getNumeroInvitados();
                boolean disponible = estaProveedorDisponible(salon, bodaSeleccionada.getFechaEvento());
                if (dentroPresupuesto && capacidadSuficiente && disponible) {
                    recomendadosSalon.add(p);
                    System.out.println("✓ " + salon.getNombre() +
                            " - Ubicación: " + salon.getUbicacion() +
                            " - Capacidad: " + salon.getCapacidadMaxima() +
                            " - Costo total: $" + String.format("%.2f", costoTotal) +
                            " - ✅ DISPONIBLE");
                } else if (dentroPresupuesto && capacidadSuficiente && !disponible) {
                    System.out.println("✗ " + salon.getNombre() +
                            " - Ubicación: " + salon.getUbicacion() +
                            " - Capacidad: " + salon.getCapacidadMaxima() +
                            " - Costo total: $" + String.format("%.2f", costoTotal) +
                            " - ❌ NO DISPONIBLE (ya reservado)");
                } else if (!capacidadSuficiente) {
                    System.out.println("✗ " + salon.getNombre() +
                            " - Ubicación: " + salon.getUbicacion() +
                            " - Capacidad: " + salon.getCapacidadMaxima() +
                            " - Costo total: $" + String.format("%.2f", costoTotal) +
                            " - 👥 CAPACIDAD INSUFICIENTE");
                } else if (!dentroPresupuesto) {
                    System.out.println("✗ " + salon.getNombre() +
                            " - Ubicación: " + salon.getUbicacion() +
                            " - Capacidad: " + salon.getCapacidadMaxima() +
                            " - Costo total: $" + String.format("%.2f", costoTotal) +
                            " - 💰 FUERA DE PRESUPUESTO");
                }
            }
        }
        if (recomendadosSalon.isEmpty()) {
            System.out.println("No hay salones disponibles dentro del presupuesto y con capacidad suficiente.");
        }
        System.out.println("\n--- PROVEEDORES DE BANDA ---");
        System.out.println("Presupuesto: $" + String.format("%.2f", bodaSeleccionada.getPresupuestoBanda()));
        List<Proveedor> proveedoresBanda = filtrarProveedoresPorTipo(proveedores, "Banda");
        List<Proveedor> recomendadosBanda = new ArrayList<>();
        for (Proveedor p : proveedoresBanda) {
            if (p instanceof ProveedorBanda) {
                ProveedorBanda banda = (ProveedorBanda) p;
                double costoTotal = banda.getCostoPorHora() * bodaSeleccionada.getHorasDuracion();
                boolean dentroPresupuesto = costoTotal <= bodaSeleccionada.getPresupuestoBanda();
                boolean disponible = estaProveedorDisponible(banda, bodaSeleccionada.getFechaEvento());
                if (dentroPresupuesto && disponible) {
                    recomendadosBanda.add(p);
                    System.out.println("✓ " + banda.getNombre() +
                            " - Costo total: $" + String.format("%.2f", costoTotal) +
                            " - ✅ DISPONIBLE");
                } else if (dentroPresupuesto && !disponible) {
                    System.out.println("✗ " + banda.getNombre() +
                            " - Costo total: $" + String.format("%.2f", costoTotal) +
                            " - ❌ NO DISPONIBLE (ya contratado)");
                } else if (!dentroPresupuesto) {
                    System.out.println("✗ " + banda.getNombre() +
                            " - Costo total: $" + String.format("%.2f", costoTotal) +
                            " - 💰 FUERA DE PRESUPUESTO");
                }
            }
        }
        if (recomendadosBanda.isEmpty()) {
            System.out.println("⚠ No hay bandas disponibles dentro del presupuesto.");
        }
        System.out.println("\n=== RESUMEN DE DISPONIBILIDAD ===");
        System.out.println("Comida: " + recomendadosComida.size() + " proveedor(es) recomendado(s)");
        System.out.println("Salón: " + recomendadosSalon.size() + " proveedor(es) recomendado(s)");
        System.out.println("Banda: " + recomendadosBanda.size() + " proveedor(es) recomendado(s)");

        int totalRecomendados = recomendadosComida.size() + recomendadosSalon.size() + recomendadosBanda.size();
        if (totalRecomendados >= 3) {
            System.out.println("\n✅ DISPONIBILIDAD ÓPTIMA");
            System.out.println("Puede proceder con la contratación de todos los servicios.");
        } else if (totalRecomendados >= 1) {
            System.out.println("\n⚠ DISPONIBILIDAD PARCIAL");
            System.out.println("Algunos servicios tienen disponibilidad limitada.");
        } else {
            System.out.println("\n❌ DISPONIBILIDAD INSUFICIENTE");
            System.out.println("Considere cambiar de fecha o ajustar presupuesto.");
        }
        System.out.println("\n💡 RECOMENDACIÓN: Use la opción 'Imprimir Proforma' para");
        System.out.println("seleccionar y contratar los proveedores disponibles.");
    }

    public boolean estaProveedorDisponible(Proveedor proveedor, Calendar fecha) {
        for (Boda boda : bodas) {
            if (boda.isProformaAceptada() && mismasFechas(boda.getFechaEvento(), fecha)) {
                for (Proveedor pContratado : boda.getProveedoresContratados()) {
                    if (pContratado.getId().equals(proveedor.getId())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void imprimirProforma() {
        System.out.println("\n=== IMPRIMIR PROFORMA ===");
        if (bodas.isEmpty()) {
            System.out.println("No hay bodas registradas.");
            return;
        }
        System.out.println("Seleccione una boda para generar proforma:");
        for (int i = 0; i < bodas.size(); i++) {
            Boda boda = bodas.get(i);
            System.out.println((i + 1) + ". " + boda.getNombreNovios() +
                    " - Fecha: " + formatearFecha(boda.getFechaEvento()));
        }
        System.out.print("Seleccione una boda (0 para volver): ");
        int opcion = leerEntero();
        if (opcion == 0 || opcion > bodas.size()) {
            return;
        }
        Boda bodaSeleccionada = bodas.get(opcion - 1);
        Organizador organizador = bodaSeleccionada.getOrganizador();
        List<Proveedor> proveedoresComida = buscarProveedoresDisponibles("Comida", bodaSeleccionada);
        List<Proveedor> proveedoresSalon = buscarProveedoresDisponibles("Salon", bodaSeleccionada);
        List<Proveedor> proveedoresBanda = buscarProveedoresDisponibles("Banda", bodaSeleccionada);
        ProveedorComida proveedorComidaSeleccionado = null;
        ProveedorSalon proveedorSalonSeleccionado = null;
        ProveedorBanda proveedorBandaSeleccionado = null;

        System.out.println("\n=== SELECCIÓN DE PROVEEDORES ===");
        if (!proveedoresComida.isEmpty()) {
            System.out.println("\n--- SELECCIONE PROVEEDOR DE COMIDA ---");
            for (int i = 0; i < proveedoresComida.size(); i++) {
                ProveedorComida comida = (ProveedorComida) proveedoresComida.get(i);
                double costoTotal = comida.getCostoPorPersona() * bodaSeleccionada.getNumeroInvitados();
                System.out.println((i + 1) + ". " + comida.getNombre() +
                        " - " + comida.getNombrePlato() +
                        " (Catering: " + comida.getNombreCatering() + ")" +
                        " - Costo total: $" + String.format("%.2f", costoTotal));
            }
            System.out.println("0. No seleccionar proveedor de comida");
            System.out.print("Seleccione una opción: ");

            int seleccionComida = leerEntero();
            if (seleccionComida > 0 && seleccionComida <= proveedoresComida.size()) {
                proveedorComidaSeleccionado = (ProveedorComida) proveedoresComida.get(seleccionComida - 1);
            }
        }
        if (!proveedoresSalon.isEmpty()) {
            System.out.println("\n--- SELECCIONE PROVEEDOR DE SALÓN ---");
            for (int i = 0; i < proveedoresSalon.size(); i++) {
                ProveedorSalon salon = (ProveedorSalon) proveedoresSalon.get(i);
                double costoTotal = salon.getCostoPorHora() * bodaSeleccionada.getHorasDuracion();
                System.out.println((i + 1) + ". " + salon.getNombre() +
                        " - Ubicación: " + salon.getUbicacion() +
                        " - Capacidad: " + salon.getCapacidadMaxima() +
                        " - Costo total: $" + String.format("%.2f", costoTotal));
            }
            System.out.println("0. No seleccionar proveedor de salón");
            System.out.print("Seleccione una opción: ");
            int seleccionSalon = leerEntero();
            if (seleccionSalon > 0 && seleccionSalon <= proveedoresSalon.size()) {
                proveedorSalonSeleccionado = (ProveedorSalon) proveedoresSalon.get(seleccionSalon - 1);
            }
        }
        if (!proveedoresBanda.isEmpty()) {
            System.out.println("\n--- SELECCIONE PROVEEDOR DE BANDA ---");
            for (int i = 0; i < proveedoresBanda.size(); i++) {
                ProveedorBanda banda = (ProveedorBanda) proveedoresBanda.get(i);
                double costoTotal = banda.getCostoPorHora() * bodaSeleccionada.getHorasDuracion();
                System.out.println((i + 1) + ". " + banda.getNombre() +
                        " - Costo total: $" + String.format("%.2f", costoTotal));
            }
            System.out.println("0. No seleccionar proveedor de banda");
            System.out.print("Seleccione una opción: ");
            int seleccionBanda = leerEntero();
            if (seleccionBanda > 0 && seleccionBanda <= proveedoresBanda.size()) {
                proveedorBandaSeleccionado = (ProveedorBanda) proveedoresBanda.get(seleccionBanda - 1);
            }
        }

        double costoRealComida = 0;
        double costoRealSalon = 0;
        double costoRealBanda = 0;
        if (proveedorComidaSeleccionado != null) {
            costoRealComida = proveedorComidaSeleccionado.getCostoPorPersona() * bodaSeleccionada.getNumeroInvitados();
        }
        if (proveedorSalonSeleccionado != null) {
            costoRealSalon = proveedorSalonSeleccionado.getCostoPorHora() * bodaSeleccionada.getHorasDuracion();
        }
        if (proveedorBandaSeleccionado != null) {
            costoRealBanda = proveedorBandaSeleccionado.getCostoPorHora() * bodaSeleccionada.getHorasDuracion();
        }
        double subtotal = costoRealComida + costoRealSalon + costoRealBanda;
        double iva = subtotal * IVA;
        double total = subtotal + iva;
        System.out.println("\n==================================================");
        System.out.println("              PROFORMA DE EVENTO");
        System.out.println("==================================================");
        System.out.println("\n1. DATOS PRINCIPALES DE LA BODA:");
        System.out.println("   ID Boda: " + bodaSeleccionada.getId());
        System.out.println("   Nombre Novios: " + bodaSeleccionada.getNombreNovios());
        System.out.println("   Fecha Evento: " + formatearFecha(bodaSeleccionada.getFechaEvento()));
        System.out.println("   Lugar: " + bodaSeleccionada.getLugar());
        System.out.println("   Tema de Color: " + bodaSeleccionada.getTemaColor());
        System.out.println("   Invitados: " + bodaSeleccionada.getNumeroInvitados());
        System.out.println("   Horas Duración: " + bodaSeleccionada.getHorasDuracion());
        System.out.println("\n2. DATOS SECUNDARIOS:");
        System.out.println("   Tipo Ceremonia: " + bodaSeleccionada.getTipoCeremonia());
        System.out.println("   Canción del Vals: " + bodaSeleccionada.getCancionVals());
        System.out.println("\n3. ORGANIZADOR A CARGO:");
        if (organizador != null) {
            System.out.println("   ID: " + organizador.getId());
            System.out.println("   Nombre: " + organizador.getNombre());
            System.out.println("   Especialidad: " + organizador.getEspecialidad());
            System.out.println("   Años Experiencia: " + organizador.getAñosExperiencia());
            System.out.println("   Contacto: " + organizador.getContacto());
        } else {
            System.out.println("   ⚠ NO ASIGNADO");
        }
        System.out.println("\n4. PRESUPUESTOS MÁXIMOS ASIGNADOS:");
        System.out.println("   Comida: $" + String.format("%.2f", bodaSeleccionada.getPresupuestoComida()));
        System.out.println("   Salón: $" + String.format("%.2f", bodaSeleccionada.getPresupuestoSalon()));
        System.out.println("   Banda/Música: $" + String.format("%.2f", bodaSeleccionada.getPresupuestoBanda()));
        System.out.println("\n5. PROVEEDORES SELECCIONADOS:");
        System.out.println("   ➤ COMIDA:");
        if (proveedorComidaSeleccionado != null) {
            System.out.println("      ✓ " + proveedorComidaSeleccionado.getNombre() +
                    " - " + proveedorComidaSeleccionado.getNombrePlato() +
                    " (Catering: " + proveedorComidaSeleccionado.getNombreCatering() + ")" +
                    " - Costo por persona: $" + String.format("%.2f", proveedorComidaSeleccionado.getCostoPorPersona()) +
                    " - Costo total: $" + String.format("%.2f", costoRealComida));

            if (costoRealComida > bodaSeleccionada.getPresupuestoComida()) {
                System.out.println("      ⚠ EXCEDE EL PRESUPUESTO por $" +
                        String.format("%.2f", (costoRealComida - bodaSeleccionada.getPresupuestoComida())));
            } else {
                System.out.println("      ✓ DENTRO DEL PRESUPUESTO (Ahorro: $" +
                        String.format("%.2f", (bodaSeleccionada.getPresupuestoComida() - costoRealComida)) + ")");
            }
        } else {
            System.out.println("      ⚠ NO SELECCIONADO");
            System.out.println("      Recomendación: Seleccione un proveedor o aumente el presupuesto.");
        }
        System.out.println("\n   ➤ SALÓN:");
        if (proveedorSalonSeleccionado != null) {
            System.out.println("      ✓ " + proveedorSalonSeleccionado.getNombre() +
                    " - Ubicación: " + proveedorSalonSeleccionado.getUbicacion() +
                    " - Capacidad: " + proveedorSalonSeleccionado.getCapacidadMaxima() +
                    " - Costo por hora: $" + String.format("%.2f", proveedorSalonSeleccionado.getCostoPorHora()) +
                    " - Costo total: $" + String.format("%.2f", costoRealSalon));
            if (costoRealSalon > bodaSeleccionada.getPresupuestoSalon()) {
                System.out.println("      ⚠ EXCEDE EL PRESUPUESTO por $" +
                        String.format("%.2f", (costoRealSalon - bodaSeleccionada.getPresupuestoSalon())));
            } else {
                System.out.println("      ✓ DENTRO DEL PRESUPUESTO (Ahorro: $" +
                        String.format("%.2f", (bodaSeleccionada.getPresupuestoSalon() - costoRealSalon)) + ")");
            }
        } else {
            System.out.println("      ⚠ NO SELECCIONADO");
            System.out.println("      Recomendación: Seleccione un proveedor o aumente el presupuesto.");
        }
        System.out.println("\n   ➤ BANDA:");
        if (proveedorBandaSeleccionado != null) {
            System.out.println("      ✓ " + proveedorBandaSeleccionado.getNombre() +
                    " - Costo por hora: $" + String.format("%.2f", proveedorBandaSeleccionado.getCostoPorHora()) +
                    " - Costo total: $" + String.format("%.2f", costoRealBanda));
            if (costoRealBanda > bodaSeleccionada.getPresupuestoBanda()) {
                System.out.println("      ⚠ EXCEDE EL PRESUPUESTO por $" +
                        String.format("%.2f", (costoRealBanda - bodaSeleccionada.getPresupuestoBanda())));
            } else {
                System.out.println("      ✓ DENTRO DEL PRESUPUESTO (Ahorro: $" +
                        String.format("%.2f", (bodaSeleccionada.getPresupuestoBanda() - costoRealBanda)) + ")");
            }
        } else {
            System.out.println("      ⚠ NO SELECCIONADO");
            System.out.println("      Recomendación: Seleccione un proveedor o aumente el presupuesto.");
        }
        System.out.println("\n6. RESUMEN FINANCIERO:");
        System.out.println("   --------------------------------------------------");
        System.out.printf("   %-30s $%,15.2f\n", "Comida:", costoRealComida);
        System.out.printf("   %-30s $%,15.2f\n", "Salón:", costoRealSalon);
        System.out.printf("   %-30s $%,15.2f\n", "Banda/Música:", costoRealBanda);
        System.out.println("   --------------------------------------------------");
        System.out.printf("   %-30s $%,15.2f\n", "Subtotal servicios:", subtotal);
        System.out.printf("   %-30s $%,15.2f\n", "IVA (15% sobre servicios):", iva);
        System.out.println("   --------------------------------------------------");
        System.out.printf("   %-30s $%,15.2f\n", "TOTAL A PAGAR:", total);
        System.out.println("   --------------------------------------------------");
        double presupuestoTotal = bodaSeleccionada.getPresupuestoComida() +
                bodaSeleccionada.getPresupuestoSalon() +
                bodaSeleccionada.getPresupuestoBanda();
        System.out.println("\n7. COMPARACIÓN CON PRESUPUESTO:");
        System.out.println("   Presupuesto total asignado: $" + String.format("%.2f", presupuestoTotal));
        System.out.println("   Costo total servicios: $" + String.format("%.2f", total));
        if (total > presupuestoTotal) {
            System.out.println("   ⚠ EXCEDE EL PRESUPUESTO por $" +
                    String.format("%.2f", (total - presupuestoTotal)));
        } else {
            System.out.println("   ✓ DENTRO DEL PRESUPUESTO (Ahorro: $" +
                    String.format("%.2f", (presupuestoTotal - total)) + ")");
        }
        System.out.println("\n==================================================");
        System.out.println("          ¡GRACIAS POR SU PREFERENCIA!");
        System.out.println("==================================================");
        System.out.println("\n\n=== ¿DESEA ASOCIAR AL CLIENTE CON ESTA PROFORMA? ===");
        System.out.println("Esta acción creará un contrato con los proveedores seleccionados.");
        System.out.println("Los proveedores quedarán reservados para la fecha del evento.");
        System.out.println("1. Sí, asociar cliente y crear contrato");
        System.out.println("2. No, solo imprimir proforma");
        System.out.println("3. Cancelar todo");
        System.out.print("Seleccione una opción: ");

        int decision = leerEntero();
        switch (decision) {
            case 1:
                if (proveedorComidaSeleccionado == null &&
                        proveedorSalonSeleccionado == null &&
                        proveedorBandaSeleccionado == null) {
                    System.out.println("\n No puede crear contrato sin proveedores seleccionados.");
                    System.out.println("Debe seleccionar al menos un proveedor para asociar al cliente.");
                    break;
                }
                // GUARDAR PROVEEDORES EN LA BODA
                if (proveedorComidaSeleccionado != null) {
                    bodaSeleccionada.contratarProveedor(proveedorComidaSeleccionado);
                }
                if (proveedorSalonSeleccionado != null) {
                    bodaSeleccionada.contratarProveedor(proveedorSalonSeleccionado);
                }
                if (proveedorBandaSeleccionado != null) {
                    bodaSeleccionada.contratarProveedor(proveedorBandaSeleccionado);
                }
                bodaSeleccionada.setProformaAceptada(true);
                System.out.println("\n✅ CONTRATO CREADO EXITOSAMENTE!");
                System.out.println("Cliente asociado: " + bodaSeleccionada.getNombreNovios());
                System.out.println("Fecha del evento: " + formatearFecha(bodaSeleccionada.getFechaEvento()));
                System.out.println("\nProveedores contratados:");
                if (proveedorComidaSeleccionado != null) {
                    System.out.println("• Comida: " + proveedorComidaSeleccionado.getNombre());
                }
                if (proveedorSalonSeleccionado != null) {
                    System.out.println("• Salón: " + proveedorSalonSeleccionado.getNombre());
                }
                if (proveedorBandaSeleccionado != null) {
                    System.out.println("• Banda: " + proveedorBandaSeleccionado.getNombre());
                }
                System.out.println("\n IMPORTANTE: Estos proveedores ahora están RESERVADOS");
                System.out.println("para la fecha " + formatearFecha(bodaSeleccionada.getFechaEvento()));
                System.out.println("y NO estarán disponibles para otros eventos el mismo día.");
                break;
            case 2:
                System.out.println("\nℹ Proforma impresa pero NO se creó contrato.");
                System.out.println("Los proveedores NO han sido reservados.");
                break;
            case 3:
                System.out.println("\n✗ Operación cancelada.");
                break;
            default:
                System.out.println("Opción inválida.");
        }
    }

    private List<Proveedor> buscarProveedoresDisponibles(String tipo, Boda boda) {
        List<Proveedor> resultado = new ArrayList<>();
        List<Proveedor> proveedoresTipo = filtrarProveedoresPorTipo(proveedores, tipo);
        for (Proveedor p : proveedoresTipo) {
            boolean disponible = false;
            if (p instanceof ProveedorComida && tipo.equals("Comida")) {
                ProveedorComida comida = (ProveedorComida) p;
                double costoTotal = comida.getCostoPorPersona() * boda.getNumeroInvitados();
                disponible = costoTotal <= boda.getPresupuestoComida();
            }
            else if (p instanceof ProveedorSalon && tipo.equals("Salon")) {
                ProveedorSalon salon = (ProveedorSalon) p;
                double costoTotal = salon.getCostoPorHora() * boda.getHorasDuracion();
                disponible = (costoTotal <= boda.getPresupuestoSalon()) &&
                        (salon.getCapacidadMaxima() >= boda.getNumeroInvitados());
            }
            else if (p instanceof ProveedorBanda && tipo.equals("Banda")) {
                ProveedorBanda banda = (ProveedorBanda) p;
                double costoTotal = banda.getCostoPorHora() * boda.getHorasDuracion();
                disponible = costoTotal <= boda.getPresupuestoBanda();
            }

            if (disponible) {
                resultado.add(p);
            }
        }
        return resultado;
    }
    // MÉTODO PRINCIPAL CORREGIDO: menuAnalisisPostEvento
    public void menuAnalisisPostEvento() {
        System.out.println("\n=== ANÁLISIS POST-EVENTO ===");
        if (organizadores.isEmpty()) {
            System.out.println("\n⚠ Debe registrar al menos un organizador primero.");
            System.out.println("Use la opción 1 para agregar un organizador.");
            return;
        }
        if (bodas.isEmpty()) {
            System.out.println("\n⚠ No hay bodas registradas para analizar.");
            System.out.println("Use la opción 2 para crear una boda.");
            return;
        }
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
        Calendar ahora = Calendar.getInstance();
        System.out.println("\nSeleccione una boda para análisis:");
        for (int i = 0; i < bodas.size(); i++) {
            Boda boda = bodas.get(i);
            String estado = "";
            if (boda.getFechaEvento().before(ahora) || mismasFechas(boda.getFechaEvento(), ahora)) {
                estado = " (PASADA/ACTUAL)";
            } else {
                estado = " (FUTURA)";
            }
            System.out.println((i + 1) + ". " + boda.getNombreNovios() +
                    " - Fecha: " + formatearFecha(boda.getFechaEvento()) +
                    (boda.isProformaAceptada() ? " ✓ CONTRATADA" : "") + estado);
        }
        System.out.print("Seleccione una boda (0 para volver): ");
        int opcion = leerEntero();
        if (opcion == 0 || opcion > bodas.size()) {
            return;
        }
        Boda bodaSeleccionada = bodas.get(opcion - 1);
        boolean esBodaFutura = bodaSeleccionada.getFechaEvento().after(ahora) &&
                !mismasFechas(bodaSeleccionada.getFechaEvento(), ahora);
        if (esBodaFutura) {
            System.out.println("\n⚠ ATENCIÓN: Esta boda aún no ha ocurrido.");
            System.out.println("Fecha programada: " + formatearFecha(bodaSeleccionada.getFechaEvento()));
            System.out.println("Se realizará un análisis PRELIMINAR basado en la planificación.");
            System.out.println("Para datos reales (asistencia, costos ejecutados), regrese después del evento.");
            System.out.print("\n¿Desea continuar con análisis preliminar? (1=Sí, 2=No): ");
            int continuar = leerEntero();
            if (continuar != 1) {
                return;
            }
        }
        boolean volver = false;
        while (!volver) {
            System.out.println("\n=== ANÁLISIS POST-EVENTO ===");
            System.out.println("Boda: " + bodaSeleccionada.getNombreNovios());
            System.out.println("Fecha: " + formatearFecha(bodaSeleccionada.getFechaEvento()));
            if (esBodaFutura) {
                System.out.println("  ANÁLISIS PRELIMINAR (EVENTO FUTURO)");
            } else {
                System.out.println("✅ ANÁLISIS POST-EVENTO (EVENTO REALIZADO)");
            }
            System.out.println("=================================");
            System.out.println("1. Análisis de Asistencia");
            System.out.println("2. Evaluación del Presupuesto");
            System.out.println("3. Análisis de Eficiencia");
            System.out.println("4. Generación de Conclusiones");
            System.out.println("5. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");

            int subOpcion = leerEntero();
            switch (subOpcion) {
                case 1:
                    analisisAsistenciaReal(bodaSeleccionada, esBodaFutura);
                    break;
                case 2:
                    evaluacionPresupuestoEjecutado(bodaSeleccionada, esBodaFutura);
                    break;
                case 3:
                    analisisEficienciaEvento(bodaSeleccionada, esBodaFutura);
                    break;
                case 4:
                    generarConclusiones(bodaSeleccionada, esBodaFutura);
                    break;
                case 5:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    // OPCIÓN 1: ANALISIS ASISTENCIA REAL - CON DETECCIÓN DE SOBRECUPO
    public void analisisAsistenciaReal(Boda boda, boolean esBodaFutura) {
        System.out.println("\n=== ANÁLISIS DE ASISTENCIA ===");
        System.out.println("Boda: " + boda.getNombreNovios());

        if (esBodaFutura) {
            System.out.println("⚠ ANÁLISIS PRELIMINAR (EVENTO FUTURO)");
        }

        int invitadosEstimados = boda.getNumeroInvitados();
        System.out.println("Invitados estimados: " + invitadosEstimados);

        if (boda.isProformaAceptada()) {
            System.out.println("\n📋 PROVEEDORES CONTRATADOS:");
            ProveedorComida comida = boda.getProveedorComidaContratado();
            ProveedorSalon salon = boda.getProveedorSalonContratado();
            ProveedorBanda banda = boda.getProveedorBandaContratado();

            if (comida != null) {
                System.out.println("• Comida: " + comida.getNombre() +
                        " (" + comida.getNombreCatering() + ")");
            }
            if (salon != null) {
                System.out.println("• Salón: " + salon.getNombre() +
                        " - Capacidad máxima: " + salon.getCapacidadMaxima());
            }
            if (banda != null) {
                System.out.println("• Banda: " + banda.getNombre());
            }
        }

        int asistentesReales;
        if (!esBodaFutura && boda.getAsistentesReales() > 0) {
            asistentesReales = boda.getAsistentesReales();
            System.out.println("\nAsistentes reales (ya registrados): " + asistentesReales);
        } else {
            if (esBodaFutura) {
                System.out.print("\nIngrese PROYECCIÓN de asistentes: ");
            } else {
                System.out.print("\nIngrese el número de asistentes reales: ");
            }
            asistentesReales = leerEnteroPositivo();
            boda.setAsistentesReales(asistentesReales);
        }

        System.out.println("Asistentes " + (esBodaFutura ? "proyectados" : "reales") + ": " + asistentesReales);

        // VERIFICAR SOBRECUPO CON EL SALÓN CONTRATADO
        ProveedorSalon salonContratado = boda.getProveedorSalonContratado();
        if (salonContratado != null) {
            int capacidadSalon = salonContratado.getCapacidadMaxima();

            System.out.println("\n🏨 VERIFICACIÓN DE CAPACIDAD DEL SALÓN:");
            System.out.println("Capacidad máxima del salón contratado: " + capacidadSalon + " personas");
            System.out.println("Asistentes " + (esBodaFutura ? "proyectados" : "reales") + ": " + asistentesReales);

            if (asistentesReales > capacidadSalon) {
                int personasExcedentes = asistentesReales - capacidadSalon;
                double porcentajeExceso = (personasExcedentes * 100.0) / capacidadSalon;

                System.out.println("\n🚨 ¡SOBRECUPO DETECTADO!");
                System.out.println("• Personas excedentes: " + personasExcedentes);
                System.out.printf("• Porcentaje de exceso: %.1f%%\n", porcentajeExceso);
                System.out.println("• Riesgos:");
                System.out.println("  - Problemas de seguridad y comodidad");
                System.out.println("  - Posibles multas por exceso de capacidad");
                System.out.println("  - Problemas con servicios (baños, sillas, etc.)");

                if (!esBodaFutura) {
                    System.out.println("\n⚠ RECOMENDACIONES INMEDIATAS:");
                    System.out.println("1. Notificar a los organizadores del riesgo");
                    System.out.println("2. Considerar espacio adicional");
                    System.out.println("3. Revisar permisos de ocupación");
                }
            } else if (asistentesReales == capacidadSalon) {
                System.out.println("\n⚠ CAPACIDAD AL LÍMITE");
                System.out.println("• El salón está en su capacidad máxima");
                System.out.println("• No hay margen para invitados adicionales");
            } else {
                int espacioDisponible = capacidadSalon - asistentesReales;
                System.out.println("\n✅ CAPACIDAD ADECUADA");
                System.out.println("• Espacio disponible: " + espacioDisponible + " personas");
                System.out.println("• Margen de seguridad: " +
                        String.format("%.1f", (espacioDisponible * 100.0 / capacidadSalon)) + "%");
            }
        }

        // Cálculo de porcentaje de asistencia
        if (invitadosEstimados > 0) {
            double porcentajeAsistencia = (asistentesReales * 100.0) / invitadosEstimados;
            System.out.printf("\n📊 Porcentaje de asistencia: %.2f%%\n", porcentajeAsistencia);

            int ausencias = invitadosEstimados - asistentesReales;
            System.out.println("Ausencias: " + Math.max(0, ausencias) + " personas");

            System.out.println("\n📈 ANÁLISIS DE ASISTENCIA:");
            if (porcentajeAsistencia >= 90) {
                System.out.println("✅ ASISTENCIA EXCELENTE");
                System.out.println("• Más del 90% de asistencia");
                System.out.println("• Evento muy atractivo para los invitados");
            } else if (porcentajeAsistencia >= 70) {
                System.out.println("⚠ ASISTENCIA REGULAR");
                System.out.println("• Entre 70-90% de asistencia");
                System.out.println("• Nivel de asistencia aceptable");
            } else {
                System.out.println("❌ ASISTENCIA BAJA");
                System.out.println("• Menos del 70% de asistencia");
                System.out.println("• Posibles problemas: fecha inconveniente, mala comunicación, etc.");
            }
        }

        // VERIFICAR SI HAY MÁS ASISTENTES DE LO ESTIMADO
        if (asistentesReales > invitadosEstimados) {
            int invitadosExtra = asistentesReales - invitadosEstimados;
            System.out.println("\n📢 ¡ATENCIÓN! HAY MÁS ASISTENTES DE LO ESTIMADO");
            System.out.println("• Invitados extra: " + invitadosExtra);
            System.out.println("• Impacto en servicios:");

            ProveedorComida comidaContratada = boda.getProveedorComidaContratado();
            if (comidaContratada != null) {
                double costoExtraComida = comidaContratada.getCostoPorPersona() * invitadosExtra;
                System.out.println("  - Comida extra necesaria: $" + String.format("%.2f", costoExtraComida));
            }

            System.out.println("• Posibles soluciones:");
            System.out.println("  1. Ordenar comida adicional");
            System.out.println("  2. Ajustar disposición de mesas");
            System.out.println("  3. Comunicar al personal de servicio");
        }
    }

    // OPCIÓN 2: EVALUACIÓN PRESUPUESTO - CON HORAS REALES
    private void evaluacionPresupuestoEjecutado(Boda boda, boolean esBodaFutura) {
        System.out.println("\n=== EVALUACIÓN DEL PRESUPUESTO ===");
        System.out.println("Boda: " + boda.getNombreNovios());

        if (esBodaFutura) {
            System.out.println("⚠ ANÁLISIS PRELIMINAR (EVENTO FUTURO)");
            System.out.println("Ingrese PROYECCIONES basadas en planificación");
        } else {
            System.out.println("✅ ANÁLISIS POST-EVENTO (EVENTO REALIZADO)");
            System.out.println("Ingrese datos REALES de ejecución");
        }

        List<Proveedor> proveedoresContratados = boda.getProveedoresContratados();

        if (proveedoresContratados.isEmpty() && !boda.isProformaAceptada()) {
            System.out.println("⚠ Esta boda no tiene proforma aceptada.");
            System.out.println("No hay proveedores contratados para evaluar.");
            return;
        }

        double presupuestoComidaPlan = boda.getPresupuestoComida();
        double presupuestoSalonPlan = boda.getPresupuestoSalon();
        double presupuestoBandaPlan = boda.getPresupuestoBanda();
        double presupuestoTotalPlan = presupuestoComidaPlan + presupuestoSalonPlan + presupuestoBandaPlan;

        System.out.println("\n📋 PRESUPUESTO PLANIFICADO:");
        System.out.printf("• Comida: $%,.2f\n", presupuestoComidaPlan);
        System.out.printf("• Salón: $%,.2f\n", presupuestoSalonPlan);
        System.out.printf("• Banda: $%,.2f\n", presupuestoBandaPlan);
        System.out.printf("• TOTAL: $%,.2f\n", presupuestoTotalPlan);

        System.out.println("\n💰 " + (esBodaFutura ? "PROYECCIÓN DE " : "") + "GASTOS:");

        double costoComidaReal = 0;
        double costoSalonReal = 0;
        double costoBandaReal = 0;

        ProveedorComida proveedorComida = boda.getProveedorComidaContratado();
        ProveedorSalon proveedorSalon = boda.getProveedorSalonContratado();
        ProveedorBanda proveedorBanda = boda.getProveedorBandaContratado();

        int horasPlanificadas = boda.getHorasDuracion();
        int horasRealesSalon;
        int horasRealesBanda;

        // **¡CORRECCIÓN! PREGUNTAR HORAS TANTO PARA BODAS FUTURAS COMO PASADAS**
        System.out.println("\n⏰ HORAS " + (esBodaFutura ? "PROYECTADAS" : "REALES") + " DE SERVICIO:");

        // **PREGUNTAR HORAS DEL SALÓN**
        if (esBodaFutura) {
            System.out.println("\n🏨 SALÓN (PROYECCIÓN):");
            System.out.print("¿Cuántas horas PROYECTA usar el salón? (Planificado: " + horasPlanificadas + " horas): ");
        } else {
            System.out.println("\n🏨 SALÓN (REAL):");
            System.out.print("¿Cuántas horas REALES se usó el salón? (Planificado: " + horasPlanificadas + " horas): ");
        }
        horasRealesSalon = leerEnteroPositivo();
        boda.setHorasRealesSalon(horasRealesSalon);

        // **PREGUNTAR HORAS DE LA BANDA**
        if (esBodaFutura) {
            System.out.println("\n🎵 BANDA (PROYECCIÓN):");
            System.out.print("¿Cuántas horas PROYECTA tocar la banda? (Planificado: " + horasPlanificadas + " horas): ");
        } else {
            System.out.println("\n🎵 BANDA (REAL):");
            System.out.print("¿Cuántas horas REALES tocó la banda? (Planificado: " + horasPlanificadas + " horas): ");
        }
        horasRealesBanda = leerEnteroPositivo();
        boda.setHorasRealesBanda(horasRealesBanda);

        // Mostrar resumen de horas
        System.out.println("\n📊 RESUMEN DE HORAS:");
        System.out.println("• Horas planificadas: " + horasPlanificadas + " horas");
        System.out.println("• Horas " + (esBodaFutura ? "proyectadas" : "reales") + " salón: " + horasRealesSalon + " horas");
        System.out.println("• Horas " + (esBodaFutura ? "proyectadas" : "reales") + " banda: " + horasRealesBanda + " horas");

        // Verificar diferencias
        if (horasRealesSalon != horasPlanificadas || horasRealesBanda != horasPlanificadas) {
            System.out.println("\n⚠ DIFERENCIAS " + (esBodaFutura ? "PROYECTADAS" : "DETECTADAS") + ":");
            if (horasRealesSalon != horasPlanificadas) {
                int diferenciaSalon = horasRealesSalon - horasPlanificadas;
                System.out.println("• Salón: " +
                        (diferenciaSalon > 0 ? "+" + diferenciaSalon + " horas " + (esBodaFutura ? "proyectadas" : "reales") +
                                " (extendido)" : diferenciaSalon + " horas " + (esBodaFutura ? "proyectadas" : "reales") + " (reducido)"));
            }
            if (horasRealesBanda != horasPlanificadas) {
                int diferenciaBanda = horasRealesBanda - horasPlanificadas;
                System.out.println("• Banda: " +
                        (diferenciaBanda > 0 ? "+" + diferenciaBanda + " horas " + (esBodaFutura ? "proyectadas" : "reales") +
                                " (extendido)" : diferenciaBanda + " horas " + (esBodaFutura ? "proyectadas" : "reales") + " (reducido)"));
            }
        }

        // **COMIDA: calcular basado en asistentes reales y proveedor**
        if (proveedorComida != null) {
            int asistentes = boda.getAsistentesReales() > 0 ? boda.getAsistentesReales() : boda.getNumeroInvitados();
            double costoPorPersona = proveedorComida.getCostoPorPersona();
            costoComidaReal = costoPorPersona * asistentes;

            System.out.println("\n🍽️  COMIDA (USANDO PROVEEDOR CONTRATADO):");
            System.out.println("Proveedor: " + proveedorComida.getNombre());
            System.out.printf("Costo por persona: $%,.2f\n", costoPorPersona);
            System.out.println("Asistentes: " + asistentes);
            System.out.printf("Costo " + (esBodaFutura ? "proyectado" : "real") + ": $%,.2f\n", costoComidaReal);

            if (asistentes > boda.getNumeroInvitados()) {
                int personasExtra = asistentes - boda.getNumeroInvitados();
                double costoExtra = personasExtra * costoPorPersona;
                System.out.println("⚠ Personas extra: " + personasExtra +
                        " (Costo adicional: $" + String.format("%.2f", costoExtra) + ")");
            }

            if (asistentes < boda.getNumeroInvitados()) {
                int platosSobrantes = boda.getNumeroInvitados() - asistentes;
                double costoPlatosSobrantes = platosSobrantes * costoPorPersona;
                System.out.printf("Platos sobrantes: %d (Costo desperdiciado: $%,.2f)\n",
                        platosSobrantes, costoPlatosSobrantes);
            }
        } else {
            System.out.print("\nCosto " + (esBodaFutura ? "proyectado" : "real") + " de comida: $");
            costoComidaReal = leerDoublePositivo();
        }

        // **SALÓN: calcular basado en horas ingresadas y proveedor**
        System.out.println("\n🏨  SALÓN:");
        if (proveedorSalon != null) {
            double costoPorHoraSalon = proveedorSalon.getCostoPorHora();
            costoSalonReal = costoPorHoraSalon * horasRealesSalon;

            System.out.println("Proveedor: " + proveedorSalon.getNombre());
            System.out.printf("Costo por hora: $%,.2f\n", costoPorHoraSalon);
            System.out.println("Horas " + (esBodaFutura ? "proyectadas" : "reales usadas") + ": " + horasRealesSalon);
            System.out.printf("Costo " + (esBodaFutura ? "proyectado" : "real") + ": $%,.2f\n", costoSalonReal);

            if (horasRealesSalon != horasPlanificadas) {
                int diferenciaHorasSalon = horasRealesSalon - horasPlanificadas;
                double costoDiferenciaSalon = diferenciaHorasSalon * costoPorHoraSalon;

                if (diferenciaHorasSalon > 0) {
                    System.out.println("⚠ " + (esBodaFutura ? "PROYECTA" : "Se usó") + " " +
                            Math.abs(diferenciaHorasSalon) + " horas MÁS de lo planificado");
                    System.out.println("Costo adicional por horas " +
                            (esBodaFutura ? "extra proyectadas" : "extra") + " del salón: $" +
                            String.format("%.2f", Math.abs(costoDiferenciaSalon)));
                } else {
                    System.out.println("✓ " + (esBodaFutura ? "PROYECTA" : "Se usó") + " " +
                            Math.abs(diferenciaHorasSalon) + " horas MENOS de lo planificado");
                    System.out.println("Ahorro por reducción de horas del salón: $" +
                            String.format("%.2f", Math.abs(costoDiferenciaSalon)));
                }
            }
        } else {
            System.out.print("Costo " + (esBodaFutura ? "proyectado" : "real") + " del salón: $");
            costoSalonReal = leerDoublePositivo();
        }

        // **BANDA: calcular basado en horas ingresadas y proveedor**
        System.out.println("\n🎵  BANDA:");
        if (proveedorBanda != null) {
            double costoPorHoraBanda = proveedorBanda.getCostoPorHora();
            costoBandaReal = costoPorHoraBanda * horasRealesBanda;

            System.out.println("Proveedor: " + proveedorBanda.getNombre());
            System.out.printf("Costo por hora: $%,.2f\n", costoPorHoraBanda);
            System.out.println("Horas " + (esBodaFutura ? "proyectadas" : "reales tocadas") + ": " + horasRealesBanda);
            System.out.printf("Costo " + (esBodaFutura ? "proyectado" : "real") + ": $%,.2f\n", costoBandaReal);

            if (horasRealesBanda != horasPlanificadas) {
                int diferenciaHorasBanda = horasRealesBanda - horasPlanificadas;
                double costoDiferenciaBanda = diferenciaHorasBanda * costoPorHoraBanda;

                if (diferenciaHorasBanda > 0) {
                    System.out.println("⚠ " + (esBodaFutura ? "PROYECTA" : "Tocó") + " " +
                            Math.abs(diferenciaHorasBanda) + " horas MÁS de lo planificado");
                    System.out.println("Costo adicional por horas " +
                            (esBodaFutura ? "extra proyectadas" : "extra") + " de la banda: $" +
                            String.format("%.2f", Math.abs(costoDiferenciaBanda)));
                } else {
                    System.out.println("✓ " + (esBodaFutura ? "PROYECTA" : "Tocó") + " " +
                            Math.abs(diferenciaHorasBanda) + " horas MENOS de lo planificado");
                    System.out.println("Ahorro por reducción de horas de la banda: $" +
                            String.format("%.2f", Math.abs(costoDiferenciaBanda)));
                }
            }
        } else {
            System.out.print("Costo " + (esBodaFutura ? "proyectado" : "real") + " de la banda: $");
            costoBandaReal = leerDoublePositivo();
        }

        // Guardar los gastos en la boda
        boda.setGastoRealComida(costoComidaReal);
        boda.setGastoRealSalon(costoSalonReal);
        boda.setGastoRealBanda(costoBandaReal);

        double gastoTotalReal = costoComidaReal + costoSalonReal + costoBandaReal;

        System.out.println("\n📊 RESUMEN DE GASTOS " + (esBodaFutura ? "PROYECTADOS" : "REALES") + ":");
        System.out.printf("• Comida: $%,.2f\n", costoComidaReal);
        System.out.printf("• Salón: $%,.2f\n", costoSalonReal);
        System.out.printf("• Banda: $%,.2f\n", costoBandaReal);
        System.out.printf("• TOTAL: $%,.2f\n", gastoTotalReal);

        System.out.println("\n📈 COMPARACIÓN PRESUPUESTO VS " + (esBodaFutura ? "PROYECCIÓN" : "REAL") + ":");

        // **COMIDA**
        double diferenciaComida = costoComidaReal - presupuestoComidaPlan;
        System.out.printf("Comida: Planificado $%,.2f - " + (esBodaFutura ? "Proyectado" : "Real") + " $%,.2f = ",
                presupuestoComidaPlan, costoComidaReal);
        if (diferenciaComida > 0) {
            System.out.printf("SOBRECOSTO $%,.2f\n", diferenciaComida);
            if (boda.getAsistentesReales() > boda.getNumeroInvitados()) {
                System.out.println("  Razón: Más asistentes de lo estimado (+" +
                        (boda.getAsistentesReales() - boda.getNumeroInvitados()) + " personas)");
            }
        } else {
            System.out.printf("AHORRO $%,.2f\n", -diferenciaComida);
        }

        // **SALÓN**
        double diferenciaSalon = costoSalonReal - presupuestoSalonPlan;
        System.out.printf("Salón: Planificado $%,.2f - " + (esBodaFutura ? "Proyectado" : "Real") + " $%,.2f = ",
                presupuestoSalonPlan, costoSalonReal);
        if (diferenciaSalon > 0) {
            System.out.printf("SOBRECOSTO $%,.2f\n", diferenciaSalon);
            if (horasRealesSalon > horasPlanificadas) {
                System.out.println("  Razón: Salón usado " + horasRealesSalon + " horas (planificado: " +
                        horasPlanificadas + " horas)");
                System.out.println("  Horas extra del salón: +" + (horasRealesSalon - horasPlanificadas) + " horas");
            }
        } else {
            System.out.printf("AHORRO $%,.2f\n", -diferenciaSalon);
            if (horasRealesSalon < horasPlanificadas) {
                System.out.println("  Razón: Salón usado " + horasRealesSalon + " horas (planificado: " +
                        horasPlanificadas + " horas)");
            }
        }

        // **BANDA**
        double diferenciaBanda = costoBandaReal - presupuestoBandaPlan;
        System.out.printf("Banda: Planificado $%,.2f - " + (esBodaFutura ? "Proyectado" : "Real") + " $%,.2f = ",
                presupuestoBandaPlan, costoBandaReal);
        if (diferenciaBanda > 0) {
            System.out.printf("SOBRECOSTO $%,.2f\n", diferenciaBanda);
            if (horasRealesBanda > horasPlanificadas) {
                System.out.println("  Razón: Banda tocó " + horasRealesBanda + " horas (planificado: " +
                        horasPlanificadas + " horas)");
                System.out.println("  Horas extra de la banda: +" + (horasRealesBanda - horasPlanificadas) + " horas");
            }
        } else {
            System.out.printf("AHORRO $%,.2f\n", -diferenciaBanda);
            if (horasRealesBanda < horasPlanificadas) {
                System.out.println("  Razón: Banda tocó " + horasRealesBanda + " horas (planificado: " +
                        horasPlanificadas + " horas)");
            }
        }

        // **TOTAL**
        double diferenciaTotal = gastoTotalReal - presupuestoTotalPlan;
        System.out.printf("\nTOTAL: Planificado $%,.2f - " + (esBodaFutura ? "Proyectado" : "Real") + " $%,.2f = ",
                presupuestoTotalPlan, gastoTotalReal);
        if (diferenciaTotal > 0) {
            System.out.printf("SOBRECOSTO TOTAL $%,.2f\n", diferenciaTotal);
            System.out.println("⚠ El evento " + (esBodaFutura ? "PROYECTA" : "EXCEDIÓ") + " el presupuesto");

            // Análisis detallado del sobrecosto
            System.out.println("\n🔍 ANÁLISIS DETALLADO DEL SOBRECOSTO:");

            double porcentajeComida = (diferenciaComida > 0 ? diferenciaComida : 0) * 100 / diferenciaTotal;
            double porcentajeSalon = (diferenciaSalon > 0 ? diferenciaSalon : 0) * 100 / diferenciaTotal;
            double porcentajeBanda = (diferenciaBanda > 0 ? diferenciaBanda : 0) * 100 / diferenciaTotal;

            System.out.println("Distribución del sobrecosto:");
            if (diferenciaComida > 0) {
                System.out.printf("• Comida: $%,.2f (%.1f%% del sobrecosto total)\n",
                        diferenciaComida, porcentajeComida);
            }
            if (diferenciaSalon > 0) {
                System.out.printf("• Salón: $%,.2f (%.1f%% del sobrecosto total)\n",
                        diferenciaSalon, porcentajeSalon);
            }
            if (diferenciaBanda > 0) {
                System.out.printf("• Banda: $%,.2f (%.1f%% del sobrecosto total)\n",
                        diferenciaBanda, porcentajeBanda);
            }

            System.out.println("\n💡 RECOMENDACIONES PARA FUTUROS EVENTOS:");
            if (horasRealesSalon > horasPlanificadas + 1) {
                System.out.println("• Negociar tarifa por horas adicionales del salón con anticipación");
                System.out.println("• Mejorar control del tiempo del evento");
            }
            if (horasRealesBanda > horasPlanificadas + 1) {
                System.out.println("• Establecer horario exacto para la banda");
                System.out.println("• Contratar banda con tarifa plana por evento completo");
            }
            if (boda.getAsistentesReales() > boda.getNumeroInvitados()) {
                System.out.println("• Implementar sistema de confirmación obligatoria");
                System.out.println("• Ordenar comida con 10% de margen adicional");
            }
        } else {
            System.out.printf("AHORRO TOTAL $%,.2f\n", -diferenciaTotal);
            System.out.println("✅ El evento se " + (esBodaFutura ? "PROYECTA" : "MANTUVO") + " dentro del presupuesto");

            // Análisis de ahorros
            if (diferenciaTotal < 0) {
                System.out.println("\n📉 FUENTES DE AHORRO:");
                if (diferenciaComida < 0) {
                    System.out.printf("• Comida: Ahorro de $%,.2f\n", -diferenciaComida);
                }
                if (diferenciaSalon < 0) {
                    System.out.printf("• Salón: Ahorro de $%,.2f\n", -diferenciaSalon);
                    if (horasRealesSalon < horasPlanificadas) {
                        System.out.println("  - Razón: " + (horasPlanificadas - horasRealesSalon) +
                                " horas menos de uso del salón");
                    }
                }
                if (diferenciaBanda < 0) {
                    System.out.printf("• Banda: Ahorro de $%,.2f\n", -diferenciaBanda);
                    if (horasRealesBanda < horasPlanificadas) {
                        System.out.println("  - Razón: " + (horasPlanificadas - horasRealesBanda) +
                                " horas menos de música");
                    }
                }
            }
        }

        double porcentajeVariacion = (diferenciaTotal * 100) / presupuestoTotalPlan;
        System.out.printf("\n📊 Variación total: %.2f%% %s del presupuesto\n",
                Math.abs(porcentajeVariacion),
                porcentajeVariacion > 0 ? "por encima" : "por debajo");

        // Clasificación del control presupuestario
        System.out.println("\n⭐ CLASIFICACIÓN DEL CONTROL PRESUPUESTARIO:");
        if (Math.abs(porcentajeVariacion) <= 5) {
            System.out.println("✅ EXCELENTE - Control presupuestario preciso");
        } else if (Math.abs(porcentajeVariacion) <= 15) {
            System.out.println("⚠ ADECUADO - Variación dentro de márgenes aceptables");
        } else if (Math.abs(porcentajeVariacion) <= 30) {
            System.out.println("🔶 REGULAR - Variación significativa, mejorar planificación");
        } else {
            System.out.println("❌ DEFICIENTE - Gran desviación del presupuesto");
        }
    }

    // OPCIÓN 3: ANALISIS EFICIENCIA - CON ANÁLISIS DETALLADO
    private void analisisEficienciaEvento(Boda boda, boolean esBodaFutura) {
        System.out.println("\n=== ANÁLISIS DE EFICIENCIA ===");
        System.out.println("Boda: " + boda.getNombreNovios());

        if (esBodaFutura) {
            System.out.println("⚠ ANÁLISIS PRELIMINAR (EVENTO FUTURO)");
        }

        int asistentesReales = boda.getAsistentesReales() > 0 ? boda.getAsistentesReales() : boda.getNumeroInvitados();
        int capacidadReal = 0;

        ProveedorSalon salonContratado = boda.getProveedorSalonContratado();
        if (salonContratado != null) {
            capacidadReal = salonContratado.getCapacidadMaxima();
            System.out.println("\n🏨 SALÓN CONTRATADO:");
            System.out.println("Proveedor: " + salonContratado.getNombre());
            System.out.println("Capacidad máxima: " + capacidadReal + " personas");
        } else {
            System.out.print("\nIngrese capacidad real del lugar (número máximo de personas): ");
            capacidadReal = leerEnteroPositivo();
        }

        int invitadosEstimados = boda.getNumeroInvitados();

        System.out.println("\n📊 DATOS:");
        System.out.println("• Capacidad del lugar: " + capacidadReal + " personas");
        System.out.println("• Invitados estimados: " + invitadosEstimados + " personas");
        System.out.println("• Asistentes " + (esBodaFutura ? "proyectados" : "reales") + ": " + asistentesReales + " personas");

        System.out.println("\n🏨 ANÁLISIS DE CAPACIDAD DEL SALÓN:");

        if (asistentesReales > capacidadReal) {
            int sobrecupo = asistentesReales - capacidadReal;
            double porcentajeSobrecupo = (sobrecupo * 100.0) / capacidadReal;

            System.out.println("🚨 ¡SOBRECUPO CRÍTICO!");
            System.out.println("• Personas excedentes: " + sobrecupo);
            System.out.printf("• Porcentaje de exceso: %.1f%%\n", porcentajeSobrecupo);
            System.out.println("• Nivel de riesgo: " +
                    (porcentajeSobrecupo > 20 ? "ALTO" :
                            porcentajeSobrecupo > 10 ? "MEDIO" : "MODERADO"));

            System.out.println("\n⚠ CONSECUENCIAS DEL SOBRECUPO:");
            System.out.println("1. Problemas de seguridad (salidas de emergencia bloqueadas)");
            System.out.println("2. Multas por exceso de capacidad");
            System.out.println("3. Mal servicio (baños, comida, bebidas)");
            System.out.println("4. Insatisfacción de invitados");

            if (!esBodaFutura) {
                System.out.println("\n💡 RECOMENDACIONES PARA FUTUROS EVENTOS:");
                System.out.println("• Contratar salón con 10-20% más capacidad");
                System.out.println("• Control estricto de lista de invitados");
                System.out.println("• Sistema de confirmación obligatoria");
            }
        } else {
            double usoCapacidad = (asistentesReales * 100.0) / capacidadReal;
            System.out.printf("Uso de capacidad: %.1f%%\n", usoCapacidad);

            if (usoCapacidad >= 90) {
                System.out.println("✅ USO ÓPTIMO DEL ESPACIO");
                System.out.println("• El salón estuvo en su máxima capacidad eficiente");
                System.out.println("• Buen aprovechamiento del espacio contratado");
            } else if (usoCapacidad >= 70) {
                System.out.println("⚠ USO ADECUADO DEL ESPACIO");
                System.out.println("• El salón tuvo ocupación aceptable");
                System.out.println("• Espacio bien distribuido, buen ambiente");
            } else if (usoCapacidad >= 40) {
                System.out.println("🔶 USO MODERADO DEL ESPACIO");
                System.out.println("• El salón tuvo ocupación media");
                System.out.println("• Posible desperdicio de espacio");
                System.out.println("• Considerar salón más pequeño para futuros eventos");
            } else {
                System.out.println("❌ USO INEFICIENTE DEL ESPACIO");
                System.out.println("• El salón estuvo semi-vacío");
                System.out.println("• Gran desperdicio de espacio y recursos");
                System.out.println("• Reconsiderar tamaño del lugar para futuros eventos");
            }

            int espacioDisponible = capacidadReal - asistentesReales;
            System.out.println("• Espacio disponible: " + espacioDisponible + " personas");
            System.out.println("• Margen de seguridad: " +
                    String.format("%.1f", (espacioDisponible * 100.0 / capacidadReal)) + "%");
        }

        System.out.println("\n🎯 ANÁLISIS DE PRECISIÓN EN ESTIMACIÓN:");
        double diferenciaEstimacion = Math.abs(asistentesReales - invitadosEstimados);
        double porcentajeError = (diferenciaEstimacion * 100.0) / invitadosEstimados;

        System.out.printf("Error de estimación: %.1f%%\n", porcentajeError);
        System.out.println("Diferencia: " + diferenciaEstimacion + " personas");

        if (porcentajeError <= 10) {
            System.out.println("✅ ESTIMACIÓN MUY PRECISA");
            System.out.println("• Planificación acertada");
            System.out.println("• Conocimiento adecuado del grupo de invitados");
        } else if (porcentajeError <= 20) {
            System.out.println("⚠ ESTIMACIÓN ACEPTABLE");
            System.out.println("• Margen de error dentro de lo esperado");
            System.out.println("• Posibles imprevistos normales");
        } else if (porcentajeError <= 35) {
            System.out.println("🔶 ESTIMACIÓN REGULAR");
            System.out.println("• Diferencia significativa entre planificación y realidad");
            System.out.println("• Mejorar métodos de confirmación de asistencia");
        } else {
            System.out.println("❌ ESTIMACIÓN POCO PRECISA");
            System.out.println("• Gran diferencia entre lo planificado y lo real");
            System.out.println("• Revisar completamente métodos de invitación y confirmación");
        }

        // ANÁLISIS DE HORAS REALES VS PLANIFICADAS
        if (!esBodaFutura) {
            System.out.println("\n⏰ ANÁLISIS DE DURACIÓN REAL:");
            int horasPlanificadas = boda.getHorasDuracion();
            int horasRealesSalon = boda.getHorasRealesSalon();
            int horasRealesBanda = boda.getHorasRealesBanda();

            System.out.println("Horas planificadas: " + horasPlanificadas);
            System.out.println("Horas reales salón: " + horasRealesSalon);
            System.out.println("Horas reales banda: " + horasRealesBanda);

            int diferenciaSalon = horasRealesSalon - horasPlanificadas;
            int diferenciaBanda = horasRealesBanda - horasPlanificadas;

            if (diferenciaSalon != 0) {
                System.out.println("Salón: " +
                        (diferenciaSalon > 0 ? "Extendido " + diferenciaSalon + " horas" :
                                "Reducido " + Math.abs(diferenciaSalon) + " horas"));
            }

            if (diferenciaBanda != 0) {
                System.out.println("Banda: " +
                        (diferenciaBanda > 0 ? "Extendido " + diferenciaBanda + " horas" :
                                "Reducido " + Math.abs(diferenciaBanda) + " horas"));
            }

            if (diferenciaSalon > 2 || diferenciaBanda > 2) {
                System.out.println("⚠ Evento significativamente más largo de lo planificado");
                System.out.println("Considerar mejor planificación de horarios para futuros eventos");
            }
        }
    }

    // OPCIÓN 4: CONCLUSIONES - MEJORADA
    private void generarConclusiones(Boda boda, boolean esBodaFutura) {
        System.out.println("\n=== CONCLUSIONES ===");
        System.out.println("Boda: " + boda.getNombreNovios());
        System.out.println("Fecha: " + formatearFecha(boda.getFechaEvento()));
        System.out.println("=================================");

        if (esBodaFutura) {
            System.out.println("⚠ CONCLUSIONES PRELIMINARES (EVENTO FUTURO)");
            System.out.println("Basadas en planificación y proyecciones");
        } else {
            System.out.println("✅ CONCLUSIONES FINALES (EVENTO REALIZADO)");
            System.out.println("Basadas en datos reales de ejecución");
        }

        System.out.println("\n📋 RESUMEN DE DATOS:");

        List<Proveedor> proveedoresContratados = boda.getProveedoresContratados();
        int proveedoresContratadosCount = proveedoresContratados.size();

        if (proveedoresContratadosCount == 3) {
            System.out.println("✅ PLANIFICACIÓN COMPLETA");
            System.out.println("• Se contrataron los 3 servicios principales");
        } else if (proveedoresContratadosCount >= 1) {
            System.out.println("⚠ PLANIFICACIÓN PARCIAL");
            System.out.println("• Solo " + proveedoresContratadosCount + " de 3 servicios contratados");
        } else {
            System.out.println("❌ PLANIFICACIÓN INSUFICIENTE");
            System.out.println("• No hay proveedores contratados formalmente");
        }

        double presupuestoTotal = boda.getPresupuestoTotal();
        double gastoTotalReal = boda.getGastoTotalReal();

        System.out.printf("\n💰 PRESUPUESTO: $%,.2f\n", presupuestoTotal);
        if (!esBodaFutura && gastoTotalReal > 0) {
            System.out.printf("💰 GASTO REAL: $%,.2f\n", gastoTotalReal);
            double diferencia = gastoTotalReal - presupuestoTotal;
            if (diferencia > 0) {
                System.out.printf("❌ EXCEDIDO POR: $%,.2f\n", diferencia);
            } else {
                System.out.printf("✅ AHORRO DE: $%,.2f\n", -diferencia);
            }
        }

        // VERIFICAR SOBRECUPO EN CONCLUSIONES
        if (!esBodaFutura && boda.getAsistentesReales() > 0) {
            ProveedorSalon salon = boda.getProveedorSalonContratado();
            if (salon != null && boda.getAsistentesReales() > salon.getCapacidadMaxima()) {
                int sobrecupo = boda.getAsistentesReales() - salon.getCapacidadMaxima();
                System.out.println("\n🚨 ¡SE DETECTÓ SOBRECUPO!");
                System.out.println("• Capacidad del salón: " + salon.getCapacidadMaxima());
                System.out.println("• Asistentes reales: " + boda.getAsistentesReales());
                System.out.println("• Personas excedentes: " + sobrecupo);
                System.out.println("• Riesgo de multas y problemas de seguridad");
            }
        }

        // VERIFICAR HORAS EXTRA EN CONCLUSIONES
        if (!esBodaFutura) {
            int horasPlanificadas = boda.getHorasDuracion();
            int horasRealesSalon = boda.getHorasRealesSalon();
            int horasRealesBanda = boda.getHorasRealesBanda();

            boolean horasExtra = horasRealesSalon > horasPlanificadas ||
                    horasRealesBanda > horasPlanificadas;

            if (horasExtra) {
                System.out.println("\n⏰ ¡HUBO HORAS EXTRA!");
                System.out.println("Horas planificadas: " + horasPlanificadas);
                if (horasRealesSalon > horasPlanificadas) {
                    System.out.println("• Salón: " + horasRealesSalon + " horas (+" +
                            (horasRealesSalon - horasPlanificadas) + ")");
                }
                if (horasRealesBanda > horasPlanificadas) {
                    System.out.println("• Banda: " + horasRealesBanda + " horas (+" +
                            (horasRealesBanda - horasPlanificadas) + ")");
                }
                System.out.println("Considerar mejor planificación de tiempos para futuros eventos");
            }
        }

        System.out.println("\n🎯 RECOMENDACIONES PARA FUTUROS EVENTOS:");

        System.out.println("1. CONFIRMACIÓN DE ASISTENCIA:");
        System.out.println("   • Sistema de confirmación 1 semana antes");
        System.out.println("   • Seguimiento personalizado a invitados clave");
        System.out.println("   • Planificar con 10-15% de margen para invitados sorpresa");

        System.out.println("\n2. CONTROL DE CAPACIDAD:");
        System.out.println("   • Contratar salón con 10-20% más capacidad que invitados estimados");
        System.out.println("   • Control estricto en entrada para evitar sobrecupo");
        System.out.println("   • Verificar permisos de ocupación máxima");

        System.out.println("\n3. CONTROL DE TIEMPOS:");
        System.out.println("   • Planificar horarios realistas");
        System.out.println("   • Incluir 30 min extra para imprevistos");
        System.out.println("   • Negociar tarifas por horas adicionales con proveedores");

        System.out.println("\n4. SELECCIÓN DE PROVEEDORES:");
        System.out.println("   • Contratar con al menos 2 meses de anticipación");
        System.out.println("   • Solicitar referencias y portafolios");
        System.out.println("   • Negociar cláusulas por horas extras");

        System.out.println("\n5. LOGÍSTICA DEL EVENTO:");
        System.out.println("   • Visitar el lugar con antelación");
        System.out.println("   • Plan B para condiciones climáticas");
        System.out.println("   • Coordinar tiempos entre proveedores");

        if (!esBodaFutura) {
            System.out.println("\n6. EVALUACIÓN POST-EVENTO:");
            System.out.println("   • Recopilar feedback de invitados");
            System.out.println("   • Documentar lecciones aprendidas");
            System.out.println("   • Actualizar base de datos de proveedores");
        }

        System.out.println("\n⭐ CLASIFICACIÓN DEL EVENTO:");
        if (proveedoresContratadosCount == 3 && presupuestoTotal > 5000) {
            System.out.println("• EVENTO PREMIUM - Alta inversión y planificación completa");
        } else if (proveedoresContratadosCount >= 2) {
            System.out.println("• EVENTO ESTÁNDAR - Planificación adecuada");
        } else {
            System.out.println("• EVENTO BÁSICO - Oportunidad de mejora en planificación");
        }

        // FACTOR DE RIESGO
        if (!esBodaFutura) {
            System.out.println("\n⚠ FACTOR DE RIESGO DETECTADO:");

            int factoresRiesgo = 0;
            String riesgos = "";

            ProveedorSalon salon = boda.getProveedorSalonContratado();
            if (salon != null && boda.getAsistentesReales() > salon.getCapacidadMaxima()) {
                factoresRiesgo++;
                riesgos += "• Sobrecupo en salón\n";
            }

            if (boda.getHorasRealesSalon() > boda.getHorasDuracion() + 1 ||
                    boda.getHorasRealesBanda() > boda.getHorasDuracion() + 1) {
                factoresRiesgo++;
                riesgos += "• Horas extras significativas\n";
            }

            if (boda.getGastoTotalReal() > boda.getPresupuestoTotal() * 1.1) {
                factoresRiesgo++;
                riesgos += "• Sobrecosto mayor al 10%\n";
            }

            if (factoresRiesgo == 0) {
                System.out.println("✅ RIESGO BAJO - Evento bien ejecutado");
            } else if (factoresRiesgo == 1) {
                System.out.println("⚠ RIESGO MODERADO - Un factor a mejorar");
                System.out.println(riesgos);
            } else {
                System.out.println("🚨 RIESGO ALTO - Múltiples factores críticos");
                System.out.println(riesgos);
                System.out.println("Revisar procesos de planificación y ejecución");
            }
        }

        if (esBodaFutura) {
            System.out.println("\n🔮 PRONÓSTICO:");
            System.out.println("• Evento " + (boda.isProformaAceptada() ? "CONTRATADO" : "PENDIENTE DE CONTRATACIÓN"));
            System.out.println("• Fecha programada: " + formatearFecha(boda.getFechaEvento()));
            System.out.println("• Nivel de planificación: " +
                    (proveedoresContratadosCount == 3 ? "COMPLETO" :
                            proveedoresContratadosCount >= 1 ? "PARCIAL" : "INICIAL"));

            ProveedorSalon salon = boda.getProveedorSalonContratado();
            if (salon != null && boda.getNumeroInvitados() > salon.getCapacidadMaxima() * 0.9) {
                System.out.println("⚠ RIESGO POTENCIAL: Posible sobrecupo si asisten más invitados de lo confirmado");
            }
        }
    }

    // MÉTODOS UTILITARIOS
    public static String formatearFecha(Calendar fecha) {
        if (fecha == null) return "No definida";

        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        int mes = fecha.get(Calendar.MONTH) + 1;
        int año = fecha.get(Calendar.YEAR);

        return String.format("%02d/%02d/%04d", dia, mes, año);
    }

    private Calendar crearFecha(int dia, int mes, int año) {
        return new GregorianCalendar(año, mes - 1, dia);
    }

    private boolean mismasFechas(Calendar fecha1, Calendar fecha2) {
        if (fecha1 == null || fecha2 == null) return false;

        return fecha1.get(Calendar.YEAR) == fecha2.get(Calendar.YEAR) &&
                fecha1.get(Calendar.MONTH) == fecha2.get(Calendar.MONTH) &&
                fecha1.get(Calendar.DAY_OF_MONTH) == fecha2.get(Calendar.DAY_OF_MONTH);
    }

    private List<Proveedor> filtrarProveedoresPorCosto(List<Proveedor> proveedores, double costoMaximo) {
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

    private List<Proveedor> filtrarProveedoresPorTipo(List<Proveedor> proveedores, String tipo) {
        List<Proveedor> resultado = new ArrayList<>();
        for (Proveedor p : proveedores) {
            if (p.getTipoServicio().equalsIgnoreCase(tipo)) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    private List<Boda> filtrarBodasPorFecha(List<Boda> bodas, Calendar fecha) {
        List<Boda> resultado = new ArrayList<>();
        for (Boda boda : bodas) {
            if (mismasFechas(boda.getFechaEvento(), fecha)) {
                resultado.add(boda);
            }
        }
        return resultado;
    }
}