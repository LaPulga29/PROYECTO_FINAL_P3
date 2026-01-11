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

    // =============================================
    // EXCEPCIONES PERSONALIZADAS
    // =============================================
    public static class ValidacionException extends Exception {
        public ValidacionException(String mensaje) { super(mensaje); }
    }

    public static class FechaInvalidaException extends ValidacionException {
        public FechaInvalidaException(String mensaje) { super(mensaje); }
    }

    public static class NumeroInvalidoException extends ValidacionException {
        public NumeroInvalidoException(String mensaje) { super(mensaje); }
    }

    public static class TextoVacioException extends ValidacionException {
        public TextoVacioException(String mensaje) { super(mensaje); }
    }

    public static class CedulaInvalidaException extends ValidacionException {
        public CedulaInvalidaException(String mensaje) { super(mensaje); }
    }

    public static class EmailInvalidoException extends ValidacionException {
        public EmailInvalidoException(String mensaje) { super(mensaje); }
    }

    public static class TelefonoInvalidoException extends ValidacionException {
        public TelefonoInvalidoException(String mensaje) { super(mensaje); }
    }

    public static class TokenInvalidoException extends ValidacionException {
        public TokenInvalidoException(String mensaje) { super(mensaje); }
    }

    public static class IdDuplicadoException extends ValidacionException {
        public IdDuplicadoException(String mensaje) { super(mensaje); }
    }


    public static class EntradaInvalidaException extends ValidacionException {
        public EntradaInvalidaException(String mensaje) { super(mensaje); }
    }

    public static class OpcionInvalidaException extends ValidacionException {
        public OpcionInvalidaException(String mensaje) { super(mensaje); }
    }

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
    // MÉTODOS DE VALIDACIÓN CON EXCEPCIONES
    // =============================================
    private void validarEmailConExcepcion(String email) throws EmailInvalidoException {
        if (email == null || email.trim().isEmpty()) {
            throw new EmailInvalidoException("El email no puede estar vacío");
        }
        if (!email.contains("@") || email.indexOf("@") <= 0 ||
                email.indexOf("@") >= email.length() - 1 || !email.contains(".")) {
            throw new EmailInvalidoException("Email inválido. Formato: usuario@dominio.com");
        }
    }

    private void validarTelefonoConExcepcion(String telefono) throws TelefonoInvalidoException {
        if (telefono == null || telefono.trim().isEmpty()) {
            throw new TelefonoInvalidoException("El teléfono no puede estar vacío");
        }
        if (telefono.length() != 10) {
            throw new TelefonoInvalidoException("El teléfono debe tener 10 dígitos");
        }
        try {
            Long.parseLong(telefono);
        } catch (NumberFormatException e) {
            throw new TelefonoInvalidoException("El teléfono solo debe contener números");
        }
    }

    private void validarCedulaConExcepcion(String cedula, boolean verificarDuplicado) throws CedulaInvalidaException {
        if (cedula == null || cedula.trim().isEmpty()) {
            throw new CedulaInvalidaException("La cédula no puede estar vacía");
        }

        // Quitar espacios y guiones
        cedula = cedula.trim().replaceAll("[-\\s]", "");

        if (cedula.length() != 10) {
            throw new CedulaInvalidaException("La cédula debe tener exactamente 10 dígitos");
        }

        // Verificar que solo contenga dígitos
        if (!cedula.matches("\\d+")) {
            throw new CedulaInvalidaException("La cédula solo debe contener números positivos (0-9)");
        }

        try {
            long valor = Long.parseLong(cedula);

            if (valor <= 0) {
                throw new CedulaInvalidaException("La cédula no puede ser negativa o cero");
            }

            // Verificar duplicado si se solicita
            if (verificarDuplicado && cedulaYaExiste(cedula)) {
                throw new CedulaInvalidaException("Ya existe un organizador con esta cédula: " + cedula);
            }

        } catch (NumberFormatException e) {
            throw new CedulaInvalidaException("Formato de cédula inválido");
        }
    }

    // Sobrecarga para mantener compatibilidad
    private void validarCedulaConExcepcion(String cedula) throws CedulaInvalidaException {
        validarCedulaConExcepcion(cedula, true); // Por defecto verifica duplicado
    }

    private void validarTextoConExcepcion(String texto, String campo) throws TextoVacioException {
        if (texto == null || texto.trim().isEmpty()) {
            throw new TextoVacioException("El campo '" + campo + "' no puede estar vacío");
        }
    }

    private void validarNumeroPositivoConExcepcion(int numero, String campo) throws NumeroInvalidoException {
        if (numero <= 0) {
            throw new NumeroInvalidoException(campo + " debe ser mayor a 0");
        }
    }

    private void validarNumeroNoNegativoConExcepcion(double numero, String campo) throws NumeroInvalidoException {
        if (numero < 0) {
            throw new NumeroInvalidoException(campo + " no puede ser negativo");
        }
    }

    private void validarIdUnicoConExcepcion(String id, String tipo) throws IdDuplicadoException {
        if (tipo.equalsIgnoreCase("boda")) {
            for (Boda b : bodas) {
                if (b.getId().equals(id)) {
                    throw new IdDuplicadoException("Ya existe una boda con el ID: " + id);
                }
            }
        } else if (tipo.equalsIgnoreCase("proveedor")) {
            for (Proveedor p : proveedores) {
                if (p.getId().equals(id)) {
                    throw new IdDuplicadoException("Ya existe un proveedor con el ID: " + id);
                }
            }
        }
    }

    private void validarTokenConExcepcion(String token) throws TokenInvalidoException {
        boolean tokenValido = false;
        for (Organizador org : organizadores) {
            if (org.getTokenSeguridad().equals(token)) {
                tokenValido = true;
                break;
            }
        }
        if (!tokenValido) {
            throw new TokenInvalidoException("Token de autenticación inválido");
        }
    }


    private boolean esBisiesto(int año) {
        return (año % 4 == 0 && año % 100 != 0) || (año % 400 == 0);
    }

    private boolean validarFecha(int año, int mes, int dia) {
        if (año < 2026 || año > 2100) {
            return false;
        }
        if (mes < 1 || mes > 12) {
            return false;
        }
        int[] diasPorMes = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (mes == 2 && esBisiesto(año)) {
            diasPorMes[1] = 29;
        }
        if (dia < 1 || dia > diasPorMes[mes - 1]) {
            return false;
        }
        return true;
    }

    public boolean estaProveedorDisponible(Proveedor proveedor, Calendar fecha) {
        if (proveedor == null || fecha == null) {
            return false;
        }

        // Verificar en todas las bodas con proforma aceptada para la misma fecha
        for (Boda boda : bodas) {
            if (boda.isProformaAceptada() && mismasFechas(boda.getFechaEvento(), fecha)) {
                // Si el proveedor está en la lista de contratados, NO está disponible
                if (boda.getProveedoresContratados().contains(proveedor)) {
                    return false;
                }
            }
        }

        return true; // Proveedor disponible
    }

    private void validarFechaNoPasadaConExcepcion(Calendar fecha) throws FechaInvalidaException {
        if (fecha == null) {
            throw new FechaInvalidaException("La fecha no puede ser nula");
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
        if (fecha.before(hoy)) {
            throw new FechaInvalidaException("No se puede crear un evento con fecha pasada");
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

    // MÉTODO PARA LEER ENTEROS CON MANEJO DE ERRORES
    public int leerEntero() throws EntradaInvalidaException {
        try {
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                throw new EntradaInvalidaException("La entrada no puede estar vacía");
            }
            // Validar que solo sean dígitos
            if (!input.matches("-?\\d+")) {
                throw new EntradaInvalidaException("Debe ingresar solo números enteros");
            }
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new EntradaInvalidaException("Formato de número inválido");
        }
    }

    // MENÚ 1: GESTIONAR ORGANIZADORES
    public void menuGestionarOrganizadores() throws EntradaInvalidaException {
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
                        break;
                    }
                    buscarOrganizadorPorNombre();
                    break;
                case 3:
                    if (organizadores.isEmpty()) {
                        System.out.println("\n⚠ No hay organizadores registrados.");
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

        try {
            // VALIDACIÓN DE CÉDULA
            String cedula;
            while (true) {
                System.out.print("Cédula (10 dígitos): ");
                cedula = scanner.nextLine().trim();
                try {
                    validarCedulaConExcepcion(cedula, true);
                    break;
                } catch (CedulaInvalidaException e) {
                    System.out.println("❌ " + e.getMessage() + " Intente nuevamente.");
                }
            }

            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();
            validarTextoConExcepcion(nombre, "Nombre");

            System.out.print("Especialidad: ");
            String especialidad = scanner.nextLine();
            validarTextoConExcepcion(especialidad, "Especialidad");

            // AÑOS DE EXPERIENCIA - CON VALIDACIÓN
            int experiencia = 0;
            boolean entradaValida = false;
            while (!entradaValida) {
                try {
                    System.out.print("Años de experiencia: ");
                    experiencia = leerEntero(); // Cambié de leerSoloNumero a leerEntero
                    if (experiencia < 0) {
                        throw new NumeroInvalidoException("Los años de experiencia no pueden ser negativos");
                    }
                    if (experiencia > 50) {
                        throw new NumeroInvalidoException("Años de experiencia no realistas (máximo 50)");
                    }
                    entradaValida = true;
                } catch (ValidacionException e) {
                    System.out.println("❌ ERROR: " + e.getMessage() + ". Intente nuevamente.");
                }
            }

            // VALIDACIÓN DE EMAIL
            String email;
            while (true) {
                System.out.print("Email: ");
                email = scanner.nextLine().trim();
                try {
                    validarEmailConExcepcion(email);
                    break;
                } catch (EmailInvalidoException e) {
                    System.out.println("❌ " + e.getMessage() + " Intente nuevamente.");
                }
            }

            // VALIDACIÓN DE TELÉFONO
            String telefono;
            while (true) {
                System.out.print("Teléfono (10 dígitos): ");
                telefono = scanner.nextLine().trim();
                try {
                    validarTelefonoConExcepcion(telefono);
                    break;
                } catch (TelefonoInvalidoException e) {
                    System.out.println("❌ " + e.getMessage() + " Intente nuevamente.");
                }
            }

            System.out.print("Token de seguridad: ");
            String token = scanner.nextLine();
            validarTextoConExcepcion(token, "Token de seguridad");

            String id = "ORG" + (++idOrganizadorCounter);

            // Crear organizador
            Organizador org = new Organizador(id, cedula, nombre, especialidad,
                    experiencia, email, telefono, token);
            organizadores.add(org);
            System.out.println("✅ Organizador agregado exitosamente. ID: " + id);

        } catch (ValidacionException e) {
            System.out.println("❌ ERROR DE VALIDACIÓN: " + e.getMessage());
            System.out.println("El organizador no se pudo agregar.");
        } catch (Exception e) {
            System.out.println("❌ ERROR INESPERADO: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void buscarOrganizadorPorNombre() {
        System.out.print("\nIngrese el nombre del organizador a buscar: ");
        String nombre = scanner.nextLine();

        try {
            validarTextoConExcepcion(nombre, "Nombre para búsqueda");

            boolean encontrado = false;
            for (Organizador org : organizadores) {
                if (org.getNombre().equalsIgnoreCase(nombre)) {
                    System.out.println("\n=== INFORMACIÓN DEL ORGANIZADOR ===");
                    System.out.println("ID: " + org.getId());
                    System.out.println("Cédula: " + org.getCedula());
                    System.out.println("Nombre: " + org.getNombre());
                    System.out.println("Especialidad: " + org.getEspecialidad());
                    System.out.println("Años experiencia: " + org.getAñosExperiencia());
                    System.out.println("Email: " + org.getEmail());
                    System.out.println("Teléfono: " + org.getTelefono());
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
        } catch (TextoVacioException e) {
            System.out.println("❌ ERROR: " + e.getMessage());
        }
    }

    private void mostrarTodosOrganizadores() {
        System.out.println("\n=== LISTA DE ORGANIZADORES ===");
        for (Organizador org : organizadores) {
            System.out.println(org.toStringSinToken());
            System.out.println("------------------------");
        }
    }

    // MENÚ 2: GESTIONAR EVENTOS
    public void menuGestionarEventos() {
        if (organizadores.isEmpty()) {
            System.out.println("\nDebe registrar al menos un organizador primero.");
            System.out.println("Use la opción 1 para agregar un organizador.");
            return;
        }

        try {
            System.out.print("\nIngrese su token de autenticación: ");
            String token = scanner.nextLine();
            validarTokenConExcepcion(token);

            Organizador orgAutenticado = null;
            for (Organizador org : organizadores) {
                if (org.getTokenSeguridad().equals(token)) {
                    orgAutenticado = org;
                    break;
                }
            }

            if (orgAutenticado == null) {
                throw new TokenInvalidoException("Token no asociado a ningún organizador");
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
        } catch (TokenInvalidoException e) {
            System.out.println("❌ ERROR DE AUTENTICACIÓN: " + e.getMessage());
        } catch (ValidacionException e) {
            System.out.println("❌ ERROR: " + e.getMessage());
        }
    }
    public void crearBoda(Organizador organizador) {
        System.out.println("\n=== CREAR NUEVA BODA ===");

        try {
            String id;
            System.out.print("ID de la boda: ");
            id = scanner.nextLine();
            validarTextoConExcepcion(id, "ID de boda");
            validarIdUnicoConExcepcion(id, "boda");

            System.out.println("\n--- FECHA DEL EVENTO ---");
            Calendar fechaEvento = leerFechaConValidacion();
            validarFechaNoPasadaConExcepcion(fechaEvento);

            // HORAS DE DURACIÓN - CON VALIDACIÓN
            int horas = 0;
            boolean entradaValida = false;
            while (!entradaValida) {
                try {
                    System.out.print("Horas de duración de la boda: ");
                    horas = leerEnteroPositivo();
                    validarNumeroPositivoConExcepcion(horas, "Horas de duración");
                    entradaValida = true;
                } catch (ValidacionException e) {
                    System.out.println("❌ ERROR: " + e.getMessage() + ". Intente nuevamente.");
                }
            }

            System.out.print("Lugares de preferencia: ");
            String lugar = scanner.nextLine();
            validarTextoConExcepcion(lugar, "Lugar");

            System.out.print("Tema de color: ");
            String temaColor = scanner.nextLine();
            validarTextoConExcepcion(temaColor, "Tema de color");

            // NÚMERO DE INVITADOS - CON VALIDACIÓN
            int invitados = 0;
            entradaValida = false;
            while (!entradaValida) {
                try {
                    System.out.print("Número de invitados: ");
                    invitados = leerEnteroPositivo();
                    validarNumeroPositivoConExcepcion(invitados, "Número de invitados");
                    entradaValida = true;
                } catch (ValidacionException e) {
                    System.out.println("❌ ERROR: " + e.getMessage() + ". Intente nuevamente.");
                }
            }

            // PRESUPUESTO COMIDA - CON VALIDACIÓN
            double presupuestoComida = 0;
            entradaValida = false;
            while (!entradaValida) {
                try {
                    System.out.print("Presupuesto para comida: $");
                    presupuestoComida = leerDoublePositivo();
                    validarNumeroNoNegativoConExcepcion(presupuestoComida, "Presupuesto comida");
                    entradaValida = true;
                } catch (ValidacionException e) {
                    System.out.println("❌ ERROR: " + e.getMessage() + ". Intente nuevamente.");
                }
            }

            // PRESUPUESTO SALÓN - CON VALIDACIÓN
            double presupuestoSalon = 0;
            entradaValida = false;
            while (!entradaValida) {
                try {
                    System.out.print("Presupuesto para salón: $");
                    presupuestoSalon = leerDoublePositivo();
                    validarNumeroNoNegativoConExcepcion(presupuestoSalon, "Presupuesto salón");
                    entradaValida = true;
                } catch (ValidacionException e) {
                    System.out.println("❌ ERROR: " + e.getMessage() + ". Intente nuevamente.");
                }
            }

            // PRESUPUESTO BANDA - CON VALIDACIÓN
            double presupuestoBanda = 0;
            entradaValida = false;
            while (!entradaValida) {
                try {
                    System.out.print("Presupuesto para banda: $");
                    presupuestoBanda = leerDoublePositivo();
                    validarNumeroNoNegativoConExcepcion(presupuestoBanda, "Presupuesto banda");
                    entradaValida = true;
                } catch (ValidacionException e) {
                    System.out.println("❌ ERROR: " + e.getMessage() + ". Intente nuevamente.");
                }
            }

            System.out.print("Tipo de ceremonia: ");
            String tipoCeremonia = scanner.nextLine();
            validarTextoConExcepcion(tipoCeremonia, "Tipo de ceremonia");

            System.out.print("Canción del vals: ");
            String cancionVals = scanner.nextLine();

            System.out.print("Nombres de los novios: ");
            String nombreNovios = scanner.nextLine();
            validarTextoConExcepcion(nombreNovios, "Nombres de novios");

            // Crear boda con manejo de excepciones
            Boda boda = new Boda(id, fechaEvento, horas, lugar, temaColor,
                    invitados, presupuestoComida, presupuestoSalon, presupuestoBanda,
                    tipoCeremonia, cancionVals, nombreNovios);

            boda.setOrganizador(organizador);
            organizador.agregarEvento(boda);
            bodas.add(boda);
            System.out.println("✅ Boda creada exitosamente!");

        } catch (ValidacionException e) {
            System.out.println("❌ ERROR DE VALIDACIÓN: " + e.getMessage());
            System.out.println("La boda no se pudo crear. Intente nuevamente.");
        } catch (Exception e) {
            System.out.println("❌ ERROR INESPERADO: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public double leerDoublePositivo() throws EntradaInvalidaException, NumeroInvalidoException {
        try {
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                throw new EntradaInvalidaException("La entrada no puede estar vacía");
            }
            // Validar formato decimal
            if (!input.matches("-?\\d+(\\.\\d+)?")) {
                throw new EntradaInvalidaException("Formato de número decimal inválido");
            }
            double valor = Double.parseDouble(input);
            if (valor < 0) {
                throw new NumeroInvalidoException("No puede ingresar valores negativos");
            }
            return valor;
        } catch (NumberFormatException e) {
            throw new EntradaInvalidaException("Número inválido");
        }
    }

    private Calendar leerFechaConValidacion() {
        int dia = 0, mes = 0, año = 0;

        // Leer año
        while (true) {
            try {
                System.out.print("Año (ej: 2026): ");
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    throw new TextoVacioException("El año no puede estar vacío");
                }
                año = Integer.parseInt(input);
                if (año < 2026) {
                    throw new NumeroInvalidoException("Año inválido. Ingrese año 2026 o mayor");
                }
                if (año > 2100) {
                    throw new NumeroInvalidoException("Año muy lejano. Ingrese un año razonable");
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("✗ Debe ingresar un número válido");
            } catch (ValidacionException e) {
                System.out.println("✗ " + e.getMessage());
            }
        }

        // Leer mes
        while (true) {
            try {
                System.out.print("Mes (1-12): ");
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    throw new TextoVacioException("El mes no puede estar vacío");
                }
                mes = Integer.parseInt(input);
                if (mes < 1 || mes > 12) {
                    throw new NumeroInvalidoException("Mes inválido. Ingrese un valor entre 1 y 12");
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("✗ Debe ingresar un número válido");
            } catch (ValidacionException e) {
                System.out.println("✗ " + e.getMessage());
            }
        }

        // Leer día
        while (true) {
            try {
                System.out.print("Día (1-31): ");
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    throw new TextoVacioException("El día no puede estar vacío");
                }
                dia = Integer.parseInt(input);
                if (dia < 1 || dia > 31) {
                    throw new NumeroInvalidoException("Día inválido. Ingrese un valor entre 1 y 31");
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("✗ Debe ingresar un número válido");
            } catch (ValidacionException e) {
                System.out.println("✗ " + e.getMessage());
            }
        }

        // Validar combinación día/mes/año
        while (!validarFecha(año, mes, dia)) {
            System.out.println("\n✗ Fecha inválida.");

            if (mes == 2) {
                int maxDias = esBisiesto(año) ? 29 : 28;
                System.out.println("Febrero tiene " + maxDias + " días en el año " + año + ".");
            }

            System.out.println("Ingrese la fecha nuevamente:");

            // Volver a leer año
            while (true) {
                try {
                    System.out.print("Año: ");
                    String input = scanner.nextLine().trim();
                    if (input.isEmpty()) {
                        throw new TextoVacioException("El año no puede estar vacío");
                    }
                    año = Integer.parseInt(input);
                    if (año < 2026 || año > 2100) {
                        throw new NumeroInvalidoException("Año fuera de rango válido");
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("✗ Debe ingresar un número válido");
                } catch (ValidacionException e) {
                    System.out.println("✗ " + e.getMessage());
                }
            }

            // Volver a leer mes
            while (true) {
                try {
                    System.out.print("Mes (1-12): ");
                    String input = scanner.nextLine().trim();
                    if (input.isEmpty()) {
                        throw new TextoVacioException("El mes no puede estar vacío");
                    }
                    mes = Integer.parseInt(input);
                    if (mes < 1 || mes > 12) {
                        throw new NumeroInvalidoException("Mes inválido");
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("✗ Debe ingresar un número válido");
                } catch (ValidacionException e) {
                    System.out.println("✗ " + e.getMessage());
                }
            }

            // Volver a leer día
            while (true) {
                try {
                    System.out.print("Día: ");
                    String input = scanner.nextLine().trim();
                    if (input.isEmpty()) {
                        throw new TextoVacioException("El día no puede estar vacío");
                    }
                    dia = Integer.parseInt(input);
                    if (dia < 1 || dia > 31) {
                        throw new NumeroInvalidoException("Día inválido");
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("✗ Debe ingresar un número válido");
                } catch (ValidacionException e) {
                    System.out.println("✗ " + e.getMessage());
                }
            }
        }

        return crearFecha(dia, mes, año);
    }

    private void listarBodas() {
        System.out.println("\n=== LISTA DE BODAS ===");
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
                System.out.println("✓ " + boda);
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
        try {
            System.out.print("\nIngrese el ID de la boda a eliminar: ");
            String id = scanner.nextLine();
            validarTextoConExcepcion(id, "ID de boda");

            Boda bodaEliminar = null;
            for (Boda boda : bodas) {
                if (boda.getId().equals(id)) {
                    bodaEliminar = boda;
                    break;
                }
            }
            if (bodaEliminar != null) {
                bodas.remove(bodaEliminar);
                System.out.println("✅ Boda eliminada exitosamente.");
            } else {
                System.out.println("✗ Boda no encontrada.");
            }
        } catch (TextoVacioException e) {
            System.out.println("❌ ERROR: " + e.getMessage());
        }
    }

    private void buscarBodaPorFecha() {
        try {
            System.out.println("\nIngrese la fecha a buscar:");
            Calendar fecha = leerFechaConValidacion();
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
        } catch (Exception e) {
            System.out.println("❌ ERROR: " + e.getMessage());
        }
    }

    // MENÚ 3: GESTIONAR PROVEEDORES
    public void menuGestionarProveedores() {
        if (organizadores.isEmpty()) {
            System.out.println("\nDebe registrar al menos un organizador primero.");
            System.out.println("Use la opción 1 para agregar un organizador.");
            return;
        }

        try {
            System.out.print("\nIngrese su token de autenticación: ");
            String token = scanner.nextLine();
            validarTokenConExcepcion(token);

            Organizador orgAutenticado = null;
            for (Organizador org : organizadores) {
                if (org.getTokenSeguridad().equals(token)) {
                    orgAutenticado = org;
                    break;
                }
            }

            if (orgAutenticado == null) {
                throw new TokenInvalidoException("Token no asociado a ningún organizador");
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
                        ingresarProveedor();
                        break;
                    case 2:
                        listarProveedores();
                        break;
                    case 3:
                        if (proveedores.isEmpty()) {
                            System.out.println("\nNo hay proveedores registrados para eliminar.");
                        } else {
                            eliminarProveedor();
                        }
                        break;
                    case 4:
                        if (proveedores.isEmpty()) {
                            System.out.println("\nNo hay proveedores registrados para buscar.");
                        } else {
                            buscarProveedoresPorCosto();
                        }
                        break;
                    case 5:
                        if (proveedores.isEmpty()) {
                            System.out.println("\nNo hay proveedores registrados para buscar.");
                        } else {
                            buscarProveedoresPorTipo();
                        }
                        break;
                    case 6:
                        volver = true;
                        break;
                    default:
                        System.out.println("Opción inválida.");
                }
            }
        } catch (TokenInvalidoException e) {
            System.out.println("❌ ERROR DE AUTENTICACIÓN: " + e.getMessage());
        } catch (ValidacionException e) {
            System.out.println("❌ ERROR: " + e.getMessage());
        }
    }

    private String leerTelefonoValido() {
        String telefono;
        while (true) {
            try {
                System.out.print("Teléfono (10 dígitos numéricos): ");
                telefono = scanner.nextLine().trim();
                validarTelefonoConExcepcion(telefono);
                return telefono;
            } catch (TelefonoInvalidoException e) {
                System.out.println("❌ " + e.getMessage());
            }
        }
    }
    private void ingresarProveedor() {
        System.out.println("\n=== INGRESAR NUEVO PROVEEDOR ===");

        try {
            // Validar ID único
            String id;
            System.out.print("ID del proveedor: ");
            id = scanner.nextLine();
            validarTextoConExcepcion(id, "ID del proveedor");
            validarIdUnicoConExcepcion(id, "proveedor");

            System.out.println("Seleccione el tipo de proveedor:");
            System.out.println("1. Banda");
            System.out.println("2. Comida");
            System.out.println("3. Salón");

            int tipo = 0;
            boolean entradaValida = false;
            while (!entradaValida) {
                try {
                    System.out.print("Opción: ");
                    tipo = leerEntero();
                    if (tipo < 1 || tipo > 3) {
                        throw new NumeroInvalidoException("Opción inválida. Ingrese 1, 2 o 3");
                    }
                    entradaValida = true;
                } catch (ValidacionException e) {
                    System.out.println("❌ ERROR: " + e.getMessage() + ". Intente nuevamente.");
                }
            }

            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();
            validarTextoConExcepcion(nombre, "Nombre");

            // Validar teléfono
            String telefono = leerTelefonoValido();

            switch (tipo) {
                case 1: // Banda
                    // COSTO POR HORA - CON VALIDACIÓN
                    double costoHoraBanda = 0;
                    entradaValida = false;
                    while (!entradaValida) {
                        try {
                            System.out.print("Costo por hora de espectáculo: $");
                            costoHoraBanda = leerDoublePositivo();
                            validarNumeroPositivoConExcepcion((int)costoHoraBanda, "Costo por hora");
                            entradaValida = true;
                        } catch (ValidacionException e) {
                            System.out.println("❌ ERROR: " + e.getMessage() + ". Intente nuevamente.");
                        }
                    }

                    System.out.print("Géneros que tocan (separados por coma): ");
                    String generosStr = scanner.nextLine();
                    validarTextoConExcepcion(generosStr, "Géneros musicales");

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
                    validarTextoConExcepcion(nombrePlato, "Menú");

                    // COSTO POR PERSONA - CON VALIDACIÓN
                    double costoPersona = 0;
                    entradaValida = false;
                    while (!entradaValida) {
                        try {
                            System.out.print("Costo por persona: $");
                            costoPersona = leerDoublePositivo();
                            validarNumeroPositivoConExcepcion((int)costoPersona, "Costo por persona");
                            entradaValida = true;
                        } catch (ValidacionException e) {
                            System.out.println("❌ ERROR: " + e.getMessage() + ". Intente nuevamente.");
                        }
                    }

                    System.out.print("Nombre del catering: ");
                    String catering = scanner.nextLine();
                    validarTextoConExcepcion(catering, "Nombre del catering");

                    ProveedorComida comida = new ProveedorComida(id, nombre, telefono,
                            nombrePlato, costoPersona, catering);
                    proveedores.add(comida);
                    System.out.println("✅ Proveedor de comida agregado exitosamente. ID: " + id);
                    break;

                case 3: // Salón
                    System.out.print("Ubicación: ");
                    String ubicacion = scanner.nextLine();
                    validarTextoConExcepcion(ubicacion, "Ubicación");

                    // CAPACIDAD - CON VALIDACIÓN
                    int capacidad = 0;
                    entradaValida = false;
                    while (!entradaValida) {
                        try {
                            System.out.print("Capacidad máxima de invitados: ");
                            capacidad = leerEnteroPositivo();
                            validarNumeroPositivoConExcepcion(capacidad, "Capacidad máxima");
                            entradaValida = true;
                        } catch (ValidacionException e) {
                            System.out.println("❌ ERROR: " + e.getMessage() + ". Intente nuevamente.");
                        }
                    }

                    // COSTO POR HORA - CON VALIDACIÓN
                    double costoHoraSalon = 0;
                    entradaValida = false;
                    while (!entradaValida) {
                        try {
                            System.out.print("Costo por hora de uso: $");
                            costoHoraSalon = leerDoublePositivo();
                            validarNumeroPositivoConExcepcion((int)costoHoraSalon, "Costo por hora");
                            entradaValida = true;
                        } catch (ValidacionException e) {
                            System.out.println("❌ ERROR: " + e.getMessage() + ". Intente nuevamente.");
                        }
                    }

                    ProveedorSalon salon = new ProveedorSalon(id, nombre, telefono,
                            ubicacion, capacidad, costoHoraSalon);
                    proveedores.add(salon);
                    System.out.println("✅ Proveedor de salón agregado exitosamente. ID: " + id);
                    break;
            }

        } catch (ValidacionException e) {
            System.out.println("❌ ERROR DE VALIDACIÓN: " + e.getMessage());
            System.out.println("El proveedor no se pudo agregar.");
        } catch (Exception e) {
            System.out.println("❌ ERROR INESPERADO: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void listarProveedores() {
        System.out.println("\n=== LISTA DE PROVEEDORES ===");

        if (proveedores.isEmpty()) {
            System.out.println("No hay proveedores registrados.");
            return;
        }

        System.out.println("\n--- PROVEEDORES DE COMIDA ---");
        boolean hayComida = false;
        for (Proveedor p : proveedores) {
            if (p instanceof ProveedorComida) {
                System.out.println(p);
                hayComida = true;
            }
        }
        if (!hayComida) System.out.println("No hay proveedores de comida.");

        System.out.println("\n--- PROVEEDORES DE SALÓN ---");
        boolean haySalon = false;
        for (Proveedor p : proveedores) {
            if (p instanceof ProveedorSalon) {
                System.out.println(p);
                haySalon = true;
            }
        }
        if (!haySalon) System.out.println("No hay proveedores de salón.");

        System.out.println("\n--- PROVEEDORES DE BANDA ---");
        boolean hayBanda = false;
        for (Proveedor p : proveedores) {
            if (p instanceof ProveedorBanda) {
                System.out.println(p);
                hayBanda = true;
            }
        }
        if (!hayBanda) System.out.println("No hay proveedores de banda.");
    }

    public void eliminarProveedor() {
        try {
            System.out.print("\nIngrese el ID del proveedor a eliminar: ");
            String id = scanner.nextLine();
            validarTextoConExcepcion(id, "ID del proveedor");

            Proveedor proveedorEliminar = null;
            for (Proveedor p : proveedores) {
                if (p.getId().equals(id)) {
                    proveedorEliminar = p;
                    break;
                }
            }
            if (proveedorEliminar != null) {
                proveedores.remove(proveedorEliminar);
                System.out.println("✅ Proveedor eliminado exitosamente.");
            } else {
                System.out.println("✗ Proveedor no encontrado.");
            }
        } catch (TextoVacioException e) {
            System.out.println("❌ ERROR: " + e.getMessage());
        }
    }

    public void buscarProveedoresPorCosto() {
        if (proveedores.isEmpty()) {
            System.out.println("\nNo hay proveedores registrados.");
            return;
        }

        try {
            System.out.println("\n=== BUSCAR PROVEEDORES POR COSTO ===");
            System.out.println("Seleccione el tipo de proveedor:");
            System.out.println("1. Banda (buscar por costo por hora)");
            System.out.println("2. Comida (buscar por costo por persona)");
            System.out.println("3. Salón (buscar por costo por hora)");
            System.out.print("Opción: ");
            int tipo = leerEntero();

            if (tipo < 1 || tipo > 3) {
                throw new NumeroInvalidoException("Opción inválida. Ingrese 1, 2 o 3");
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
            validarNumeroPositivoConExcepcion((int)costoMaximo, "Costo máximo");

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
        } catch (ValidacionException e) {
            System.out.println("❌ ERROR: " + e.getMessage());
        }
    }

    public void buscarProveedoresPorTipo() {
        try {
            System.out.println("\nSeleccione el tipo de proveedor:");
            System.out.println("1. Banda");
            System.out.println("2. Comida");
            System.out.println("3. Salón");
            System.out.print("Opción: ");
            int tipo = leerEntero();

            if (tipo < 1 || tipo > 3) {
                throw new NumeroInvalidoException("Opción inválida. Ingrese 1, 2 o 3");
            }

            String tipoStr = "";
            switch (tipo) {
                case 1: tipoStr = "Banda"; break;
                case 2: tipoStr = "Comida"; break;
                case 3: tipoStr = "Salon"; break;
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
        } catch (ValidacionException e) {
            System.out.println("❌ ERROR: " + e.getMessage());
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

    public int leerEnteroPositivo() throws EntradaInvalidaException, NumeroInvalidoException {
        int valor = leerEntero();
        if (valor <= 0) {
            throw new NumeroInvalidoException("Debe ingresar un número positivo mayor a 0");
        }
        return valor;
    }

    private boolean cedulaYaExiste(String cedula) {
        // Quitar espacios y guiones para comparar
        cedula = cedula.trim().replaceAll("[-\\s]", "");

        for (Organizador org : organizadores) {
            String cedulaOrg = org.getCedula().trim().replaceAll("[-\\s]", "");
            if (cedulaOrg.equals(cedula)) {
                return true;
            }
        }
        return false;
    }



}
