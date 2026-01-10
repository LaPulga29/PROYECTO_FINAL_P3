package Util;
import Negocio.*;
import java.util.*;
import java.util.GregorianCalendar;

public class Utilitario {
    private static final double IVA = 0.15;
    private int idProveedorCounter = 2000;
    private int idOrganizadorCounter = 3000;

    private List<Organizador> organizadores;
    private List<Boda> bodas;
    private List<Proveedor> proveedores;

    public Utilitario() {
        this.organizadores = new ArrayList<>();
        this.bodas = new ArrayList<>();
        this.proveedores = new ArrayList<>();
    }


    public List<Organizador> getOrganizadores() {
        return this.organizadores;
    }

    public List<Boda> getBodas() {
        return this.bodas;
    }

    public List<Proveedor> getProveedores() {
        return this.proveedores;
    }

    private Scanner scanner = new Scanner(System.in);

    // =============================================
    // MÉTODOS DE VALIDACIÓN (AGREGAR ESTOS)
    // =============================================

    // Validar email (debe contener @)
    private boolean validarEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return email.contains("@") && email.indexOf("@") > 0
                && email.indexOf("@") < email.length() - 1;
    }

    // Validar teléfono (10 dígitos, solo números)
    private boolean validarTelefonoOrg(String telefono) {
        if (telefono == null || telefono.isEmpty()) {
            return false;
        }

        // Validar que tenga exactamente 10 caracteres
        if (telefono.length() != 10) {
            return false;
        }

        try {
            Long.parseLong(telefono);
            // Verificar que no sea negativo
            if (telefono.startsWith("-")) {
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Verificar si un año es bisiesto
    private boolean esBisiesto(int año) {
        return (año % 4 == 0 && año % 100 != 0) || (año % 400 == 0);
    }

    // Validar que una fecha sea válida
    private boolean validarFecha(int año, int mes, int dia) {
        // Validar año (no aceptar años anteriores a 2000)
        if (año < 2026 || año > 2100) {
            return false;
        }

        // Validar mes
        if (mes < 1 || mes > 12) {
            return false;
        }

        // Validar día según el mes
        int[] diasPorMes = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        // Ajustar febrero para años bisiestos
        if (mes == 2 && esBisiesto(año)) {
            diasPorMes[1] = 29;
        }

        // Validar día
        if (dia < 1 || dia > diasPorMes[mes - 1]) {
            return false;
        }

        return true;
    }

    // Validar que una fecha no sea pasada
    private boolean validarFechaNoPasada(Calendar fecha) {
        if (fecha == null) {
            return false;
        }

        Calendar hoy = Calendar.getInstance();
        hoy.set(Calendar.HOUR_OF_DAY, 0);
        hoy.set(Calendar.MINUTE, 0);
        hoy.set(Calendar.SECOND, 0);
        hoy.set(Calendar.MILLISECOND, 0);

        fecha.set(Calendar.HOUR_OF_DAY, 0);
        fecha.set(Calendar.MINUTE, 0);
        fecha.set(Calendar.SECOND, 0);
        fecha.set(Calendar.MILLISECOND, 0);

        return !fecha.before(hoy);
    }

    // Método para leer fecha con validación
    private Calendar leerFechaConValidacion() {
        int dia, mes, año;

        // Leer año
        while (true) {
            System.out.print("Año (ej: 2026): ");
            try {
                año = Integer.parseInt(scanner.nextLine().trim());
                if (año < 2026) {
                    System.out.println("✗ Año inválido. Ingrese año 2026 o mayor.");
                    continue;
                }
                if (año > 2100) {
                    System.out.println("✗ Año muy lejano. Ingrese un año razonable.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("✗ Debe ingresar un número válido.");
            }
        }

        // Leer mes
        while (true) {
            System.out.print("Mes (1-12): ");
            try {
                mes = Integer.parseInt(scanner.nextLine().trim());
                if (mes < 1 || mes > 12) {
                    System.out.println("✗ Mes inválido. Ingrese un valor entre 1 y 12.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("✗ Debe ingresar un número válido.");
            }
        }

        // Leer día
        while (true) {
            System.out.print("Día (1-31): ");
            try {
                dia = Integer.parseInt(scanner.nextLine().trim());
                if (dia < 1 || dia > 31) {
                    System.out.println("✗ Día inválido. Ingrese un valor entre 1 y 31.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("✗ Debe ingresar un número válido.");
            }
        }

        // Validar combinación día/mes/año
        while (!validarFecha(año, mes, dia)) {
            System.out.println("\n✗ Fecha inválida.");

            // Mostrar mensaje específico para febrero
            if (mes == 2) {
                int maxDias = esBisiesto(año) ? 29 : 28;
                System.out.println("Febrero tiene " + maxDias + " días en el año " + año + ".");
            }

            System.out.println("Ingrese la fecha nuevamente:");

            // Volver a leer año
            while (true) {
                System.out.print("Año: ");
                try {
                    año = Integer.parseInt(scanner.nextLine().trim());
                    if (año >= 2000 && año <= 2100) break;
                } catch (NumberFormatException e) {
                    System.out.println("✗ Debe ingresar un número válido.");
                }
            }

            // Volver a leer mes
            while (true) {
                System.out.print("Mes (1-12): ");
                try {
                    mes = Integer.parseInt(scanner.nextLine().trim());
                    if (mes >= 1 && mes <= 12) break;
                } catch (NumberFormatException e) {
                    System.out.println("✗ Debe ingresar un número válido.");
                }
            }

            // Volver a leer día
            while (true) {
                System.out.print("Día: ");
                try {
                    dia = Integer.parseInt(scanner.nextLine().trim());
                    if (dia >= 1 && dia <= 31) break;
                } catch (NumberFormatException e) {
                    System.out.println("✗ Debe ingresar un número válido.");
                }
            }
        }

        return crearFecha(dia, mes, año);
    }

    // Validar cédula (10 dígitos, solo números)
    private boolean validarCedula(String cedula) {
        if (cedula == null || cedula.isEmpty()) {
            return false;
        }

        // Validar que tenga exactamente 10 caracteres
        if (cedula.length() != 10) {
            return false;
        }

        try {
            Long.parseLong(cedula);
            // Verificar que no sea negativo
            if (cedula.startsWith("-")) {
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

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

        // VALIDACIÓN DE CÉDULA
        String cedula;
        while (true) {
            System.out.print("Cédula (10 dígitos): ");
            cedula = scanner.nextLine().trim();
            if (validarCedula(cedula)) {
                break;
            }
            System.out.println("✗ Cédula inválida. Debe tener exactamente 10 dígitos numéricos.");
        }

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Especialidad: ");
        String especialidad = scanner.nextLine();

        // VALIDACIÓN DE AÑOS DE EXPERIENCIA
        int experiencia;
        while (true) {
            System.out.print("Años de experiencia: ");
            try {
                experiencia = Integer.parseInt(scanner.nextLine().trim());
                if (experiencia < 0) {
                    System.out.println("✗ Los años de experiencia no pueden ser negativos.");
                    continue;
                }
                if (experiencia > 50) {
                    System.out.println("✗ Años de experiencia no realistas.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("✗ Debe ingresar solo números.");
            }
        }

        // VALIDACIÓN DE EMAIL
        String email;
        while (true) {
            System.out.print("Email: ");
            email = scanner.nextLine().trim();
            if (validarEmail(email)) {
                break;
            }
            System.out.println("✗ Email inválido. Debe contener @ y tener formato válido (ejemplo@dominio.com).");
        }

        // VALIDACIÓN DE TELÉFONO
        String telefono;
        while (true) {
            System.out.print("Teléfono (10 dígitos): ");
            telefono = scanner.nextLine().trim();
            if (validarTelefono(telefono)) {
                break;
            }
            System.out.println("✗ Teléfono inválido. Debe tener exactamente 10 dígitos numéricos.");
        }

        System.out.print("Token de seguridad: ");
        String token = scanner.nextLine();
        String id = "ORG" + (++idOrganizadorCounter);

        // Crear organizador con campos separados (email y teléfono)
        Organizador org = new Organizador(id, cedula, nombre, especialidad,
                experiencia, email, telefono, token);
        organizadores.add(org);
        System.out.println("✅ Organizador agregado exitosamente. ID: " + id);
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
                System.out.println("Email: " + org.getEmail());  // Email separado
                System.out.println("Teléfono: " + org.getTelefono());  // Teléfono separado
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
        // USAR EL NUEVO MÉTODO CON VALIDACIÓN
        Calendar fechaEvento = leerFechaConValidacion();

        // VALIDAR QUE LA FECHA NO SEA PASADA
        while (!validarFechaNoPasada(fechaEvento)) {
            System.out.println("✗ No se puede crear un evento con fecha pasada.");
            System.out.println("Ingrese una fecha futura:");
            fechaEvento = leerFechaConValidacion();
        }

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
        System.out.println("✅ Boda creada exitosamente!");
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

        System.out.println("\n--- BODAS PASADAS ---");
        boolean hayPasadas = false;
        for (Boda boda : bodas) {
            if (boda.getFechaEvento().before(ahora)) {
                System.out.println("✗ " + boda + " | Fecha: " + formatearFecha(boda.getFechaEvento()) + " (PASADA)");
                hayPasadas = true;
            }
        }
        if (!hayPasadas) System.out.println("No hay bodas pasadas.");

        System.out.println("\n--- BODAS FUTURAS ---");
        boolean hayFuturas = false;
        for (Boda boda : bodas) {
            if (boda.getFechaEvento().after(ahora)) {
                System.out.println("✓ " + boda + " | Fecha: " + formatearFecha(boda.getFechaEvento()) + " (FUTURA)");
                hayFuturas = true;
            }
        }
        if (!hayFuturas) System.out.println("No hay bodas futuras.");

        System.out.println("\n--- BODAS HOY ---");
        boolean hayHoy = false;
        for (Boda boda : bodas) {
            if (mismasFechas(boda.getFechaEvento(), ahora)) {
                System.out.println("● " + boda + " | Fecha: " + formatearFecha(boda.getFechaEvento()) + " (HOY)");
                hayHoy = true;
            }
        }
        if (!hayHoy) System.out.println("No hay bodas programadas para hoy.");
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
        Calendar fecha = leerFechaConValidacion();  // Usar el nuevo método con validación
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

    // MENÚ 3: GESTIONAR PROVEEDORES (VERSIÓN COMPLETA CON 5 OPCIONES)
    public void menuGestionarProveedores() {
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
            System.out.println("\n=== GESTIÓN DE PROVEEDORES ===");
            System.out.println("1. Ingresar Proveedor (elige tipo)");
            System.out.println("2. Listar Proveedores");
            System.out.println("3. Eliminar Proveedor");
            System.out.println("4. Buscar Proveedores por Costo");
            System.out.println("5. Buscar Proveedores por Tipo");
            System.out.println("6. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");

            int opcion = leerEntero();
            switch (opcion) {
                case 1:
                    ingresarProveedor();  // Este método ya lo tienes y permite elegir tipo
                    break;
                case 2:
                    listarProveedores();  // Este método ya lo tienes
                    break;
                case 3:
                    if (proveedores.isEmpty()) {
                        System.out.println("\nNo hay proveedores registrados para eliminar.");
                    } else {
                        eliminarProveedor();  // Este método ya lo tienes
                    }
                    break;
                case 4:
                    if (proveedores.isEmpty()) {
                        System.out.println("\nNo hay proveedores registrados para buscar.");
                    } else {
                        buscarProveedoresPorCosto();  // Este método ya lo tienes
                    }
                    break;
                case 5:
                    if (proveedores.isEmpty()) {
                        System.out.println("\nNo hay proveedores registrados para buscar.");
                    } else {
                        buscarProveedoresPorTipo();  // Este método ya lo tienes
                    }
                    break;
                case 6:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private String leerTelefonoValido() {
        Scanner scanner = new Scanner(System.in);
        String telefono;

        while (true) {
            System.out.print("Teléfono (10 dígitos numéricos): ");
            telefono = scanner.nextLine().trim();

            if (validarTelefono(telefono)) {
                return telefono;
            } else {
                System.out.println("✗ Teléfono inválido. Debe tener 10 dígitos numéricos.");
            }
        }
    }

    private boolean validarTelefono(String telefono) {
        if (telefono == null || telefono.length() != 10) {
            return false;
        }

        // Verificar que sean solo números
        for (int i = 0; i < telefono.length(); i++) {
            if (!Character.isDigit(telefono.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    private void agregarProveedorComida() {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("\n=== AGREGAR PROVEEDOR DE COMIDA ===");

            System.out.print("ID del proveedor: ");
            String id = scanner.nextLine();

            System.out.print("Nombre del proveedor: ");
            String nombre = scanner.nextLine();

            // Usar método validado para teléfono
            String telefono = leerTelefonoValido();

            System.out.print("Nombre del plato principal: ");
            String nombrePlato = scanner.nextLine();

            System.out.print("Costo por persona: ");
            double costoPorPersona = leerDoublePositivo();

            System.out.print("Nombre del catering: ");
            String nombreCatering = scanner.nextLine();

            ProveedorComida proveedor = new ProveedorComida(id, nombre, telefono,
                    nombrePlato, costoPorPersona, nombreCatering);
            proveedores.add(proveedor);
            System.out.println("✓ Proveedor de comida agregado exitosamente.");

        } catch (Exception e) {
            System.out.println("✗ Error al agregar proveedor: " + e.getMessage());
        }
    }

    private void agregarProveedorSalon() {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("\n=== AGREGAR PROVEEDOR DE SALÓN ===");

            System.out.print("ID del proveedor: ");
            String id = scanner.nextLine();

            System.out.print("Nombre del salón: ");
            String nombre = scanner.nextLine();

            // Usar método validado para teléfono
            String telefono = leerTelefonoValido();

            System.out.print("Ubicación: ");
            String ubicacion = scanner.nextLine();

            System.out.print("Capacidad máxima: ");
            int capacidad = leerEnteroPositivo();

            System.out.print("Costo por hora: ");
            double costoPorHora = leerDoublePositivo();

            ProveedorSalon proveedor = new ProveedorSalon(id, nombre, telefono,
                    ubicacion, capacidad, costoPorHora);
            proveedores.add(proveedor);
            System.out.println("✓ Proveedor de salón agregado exitosamente.");

        } catch (Exception e) {
            System.out.println("✗ Error al agregar proveedor: " + e.getMessage());
        }
    }

    private void agregarProveedorBanda() {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("\n=== AGREGAR PROVEEDOR DE BANDA ===");

            System.out.print("ID del proveedor: ");
            String id = scanner.nextLine();

            System.out.print("Nombre de la banda: ");
            String nombre = scanner.nextLine();

            // Usar método validado para teléfono
            String telefono = leerTelefonoValido();

            System.out.print("Costo por hora: ");
            double costoPorHora = leerDoublePositivo();

            System.out.print("Ingrese géneros musicales (separados por coma): ");
            String generosStr = scanner.nextLine();
            String[] generos = generosStr.split(",");
            // Limpiar espacios
            for (int i = 0; i < generos.length; i++) {
                generos[i] = generos[i].trim();
            }

            ProveedorBanda proveedor = new ProveedorBanda(id, nombre, telefono,
                    costoPorHora, generos);
            proveedores.add(proveedor);
            System.out.println("✓ Proveedor de banda agregado exitosamente.");

        } catch (Exception e) {
            System.out.println("✗ Error al agregar proveedor: " + e.getMessage());
        }
    }

    private void listarProveedores() {
        System.out.println("\n=== LISTA DE PROVEEDORES ===");

        if (proveedores.isEmpty()) {
            System.out.println("No hay proveedores registrados.");
            return;
        }

        System.out.println("\n--- PROVEEDORES DE COMIDA ---");
        for (Proveedor p : proveedores) {
            if (p instanceof ProveedorComida) {
                System.out.println(p);
            }
        }

        System.out.println("\n--- PROVEEDORES DE SALÓN ---");
        for (Proveedor p : proveedores) {
            if (p instanceof ProveedorSalon) {
                System.out.println(p);
            }
        }

        System.out.println("\n--- PROVEEDORES DE BANDA ---");
        for (Proveedor p : proveedores) {
            if (p instanceof ProveedorBanda) {
                System.out.println(p);
            }
        }
    }

    private void ingresarProveedor() {
        System.out.println("\n=== INGRESAR NUEVO PROVEEDOR ===");

        // Validar ID único
        String id;
        boolean idValido = false;
        do {
            System.out.print("ID del proveedor: ");
            id = scanner.nextLine();

            // Verificar si el ID ya existe
            idValido = true;
            for (Proveedor p : proveedores) {
                if (p.getId().equalsIgnoreCase(id)) {
                    System.out.println("✗ Este ID ya está registrado. Ingrese un ID diferente.");
                    idValido = false;
                    break;
                }
            }

            if (id.isEmpty()) {
                System.out.println("✗ El ID no puede estar vacío.");
                idValido = false;
            }
        } while (!idValido);

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

        // Validar teléfono (10 dígitos, solo números)
        String telefono;
        while (true) {
            System.out.print("Teléfono (10 dígitos): ");
            telefono = scanner.nextLine().trim();

            if (validarTelefono(telefono)) {
                break;
            }
            System.out.println("✗ Teléfono inválido. Debe tener exactamente 10 dígitos numéricos.");
        }

        // Ya no usar idProveedorCounter autoincremental, usar el ID ingresado por usuario
        // String id = "PROV" + (++idProveedorCounter); // Eliminar esta línea

        switch (tipo) {
            case 1: // Banda
                System.out.print("Costo por hora de espectáculo: $");
                double costoHoraBanda = leerDoublePositivo();
                System.out.print("Géneros que tocan (separados por coma): ");
                String generosStr = scanner.nextLine();
                String[] generos = generosStr.split(",");
                for (int i = 0; i < generos.length; i++) {
                    generos[i] = generos[i].trim();
                }
                ProveedorBanda banda = new ProveedorBanda(id, nombre, telefono,
                        costoHoraBanda, generos);
                proveedores.add(banda);
                System.out.println("✅ Proveedor de banda agregado exitosamente. ID: " + id);
                break;

            case 2: // Comida
                System.out.print("Menú (Entrada, Plato fuerte y su bebida, Postre): ");
                String nombrePlato = scanner.nextLine();
                System.out.print("Costo por persona: $");
                double costoPersona = leerDoublePositivo();
                System.out.print("Nombre del catering: ");
                String catering = scanner.nextLine();
                ProveedorComida comida = new ProveedorComida(id, nombre, telefono,
                        nombrePlato, costoPersona, catering);
                proveedores.add(comida);
                System.out.println("✅ Proveedor de comida agregado exitosamente. ID: " + id);
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
                System.out.println("✅ Proveedor de salón agregado exitosamente. ID: " + id);
                break;
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
        if (proveedores.isEmpty()) {
            System.out.println("\nNo hay proveedores registrados.");
            return;
        }

        System.out.println("\n=== BUSCAR PROVEEDORES POR COSTO ===");
        System.out.println("Seleccione el tipo de proveedor:");
        System.out.println("1. Banda (buscar por costo por hora)");
        System.out.println("2. Comida (buscar por costo por persona)");
        System.out.println("3. Salón (buscar por costo por hora)");
        System.out.print("Opción: ");
        int tipo = leerEntero();

        if (tipo < 1 || tipo > 3) {
            System.out.println("Opción inválida.");
            return;
        }

        String tipoStr = "";
        String unidadCosto = "";

        switch (tipo) {
            case 1:
                tipoStr = "Banda";
                unidadCosto = "por hora";
                break;
            case 2:
                tipoStr = "Comida";
                unidadCosto = "por persona";
                break;
            case 3:
                tipoStr = "Salon";
                unidadCosto = "por hora";
                break;
        }

        System.out.printf("\nIngrese el costo máximo %s: $", unidadCosto);
        double costoMaximo = leerDoublePositivo();

        List<Proveedor> resultado = new ArrayList<>();
        for (Proveedor p : proveedores) {
            if (p.getTipoServicio().equalsIgnoreCase(tipoStr)) {
                double costoBase = 0;
                boolean cumple = false;

                if (p instanceof ProveedorBanda && tipo == 1) {
                    ProveedorBanda banda = (ProveedorBanda) p;
                    costoBase = banda.getCostoPorHora();
                    cumple = costoBase <= costoMaximo;
                } else if (p instanceof ProveedorComida && tipo == 2) {
                    ProveedorComida comida = (ProveedorComida) p;
                    costoBase = comida.getCostoPorPersona();
                    cumple = costoBase <= costoMaximo;
                } else if (p instanceof ProveedorSalon && tipo == 3) {
                    ProveedorSalon salon = (ProveedorSalon) p;
                    costoBase = salon.getCostoPorHora();
                    cumple = costoBase <= costoMaximo;
                }

                if (cumple) {
                    resultado.add(p);
                }
            }
        }

        if (resultado.isEmpty()) {
            System.out.println("\n✗ No hay proveedores de " +
                    (tipo == 1 ? "banda" : tipo == 2 ? "comida" : "salón") +
                    " con costo menor o igual a $" + String.format("%.2f", costoMaximo));
        } else {
            System.out.println("\n=== PROVEEDORES ENCONTRADOS ===");
            System.out.println("Tipo: " + (tipo == 1 ? "Banda" : tipo == 2 ? "Comida" : "Salón"));
            System.out.println("Costo máximo: $" + String.format("%.2f", costoMaximo) + " " + unidadCosto);
            System.out.println("Resultados: " + resultado.size() + " proveedor(es)");
            System.out.println("---------------------------------");

            for (int i = 0; i < resultado.size(); i++) {
                Proveedor p = resultado.get(i);
                System.out.println((i + 1) + ". " + p);
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


    private List<Proveedor> buscarProveedoresDisponibles(String tipo, Boda boda) {
        List<Proveedor> resultado = new ArrayList<>();
        List<Proveedor> proveedoresTipo = filtrarProveedoresPorTipo(proveedores, tipo);

        for (Proveedor p : proveedoresTipo) {
            boolean disponible = false;
            boolean dentroPresupuesto = false;
            boolean capacidadSuficiente = false;
            boolean fechaDisponible = false;

            if (p instanceof ProveedorComida && tipo.equals("Comida")) {
                ProveedorComida comida = (ProveedorComida) p;
                double costoTotal = comida.getCostoPorPersona() * boda.getNumeroInvitados();
                dentroPresupuesto = costoTotal <= boda.getPresupuestoComida();
                fechaDisponible = estaProveedorDisponible(comida, boda.getFechaEvento());
                disponible = dentroPresupuesto && fechaDisponible;
            }
            else if (p instanceof ProveedorSalon && tipo.equals("Salon")) {
                ProveedorSalon salon = (ProveedorSalon) p;
                double costoTotal = salon.getCostoPorHora() * boda.getHorasDuracion();
                dentroPresupuesto = costoTotal <= boda.getPresupuestoSalon();
                capacidadSuficiente = salon.getCapacidadMaxima() >= boda.getNumeroInvitados();
                fechaDisponible = estaProveedorDisponible(salon, boda.getFechaEvento());
                disponible = dentroPresupuesto && capacidadSuficiente && fechaDisponible;
            }
            else if (p instanceof ProveedorBanda && tipo.equals("Banda")) {
                ProveedorBanda banda = (ProveedorBanda) p;
                double costoTotal = banda.getCostoPorHora() * boda.getHorasDuracion();
                dentroPresupuesto = costoTotal <= boda.getPresupuestoBanda();
                fechaDisponible = estaProveedorDisponible(banda, boda.getFechaEvento());
                disponible = dentroPresupuesto && fechaDisponible;
            }

            if (disponible) {
                resultado.add(p);
            }
        }
        return resultado;
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