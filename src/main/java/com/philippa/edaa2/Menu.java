package com.philippa.edaa2;

import domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.philippa.edaa2.FormatValidationUtils.*;
import static java.lang.Integer.parseInt;

public class Menu {

    //declaramos las propiedades
    private final Scanner input = new Scanner(System.in);
    private int customerIdCount = 1;                                    //incrementa cada vez que un cliente se añade
    private int orderIdCount = 1;                                       //incrementa cada vez que un cliente se añade
    private static final String dashedLnBreak = "--------------------------";
    private List<CD> cdArrayList = new ArrayList<>();
    private List<Vinyl> vinylArrayList = new ArrayList<>();
    private List<Order> orderArrayList = new ArrayList<>();
    private List<Customer> customerArrayList = new ArrayList<>();

    /**
     * Esta método arranca la aplicación y imprime el menú en la consola para el usuario
     */
    public void executeMenu() {
        boolean exit = false;
        do {
            System.out.println("###################################################################");
            System.out.println("\n                    Empire Records' Menu                         ");
            System.out.println("\n------------------------------------------------------------------");
            System.out.println("1. Mostrar todos los CDs disponibles");
            System.out.println("2. Mostrar todos los vinilos disponibles");
            System.out.println("3. Registrar un CD al sistema");
            System.out.println("4. Registrar un vinilo al sistema");
            System.out.println("5. Registrar un pedido");
            System.out.println("6. Mostrar pedidos");
            System.out.println("7. Mostrar clientes con pedidos");
            System.out.println("8. Comprobar si cliente existe en el sistema");
            System.out.println("X. Exit");
            System.out.println("--------------------------------------------------------------------");

            String option = input.nextLine().toUpperCase();

            switch (option) {
                case "1":
                    printCds();
                    break;
                case "2":
                    printVinyls();
                    break;
                case "3":
                    registerAlbumDetails(OBJECT_TYPE.CD);
                    break;
                case "4":
                    registerAlbumDetails(OBJECT_TYPE.VINYL);
                    break;
                case "5":
                    placeOrder();
                    break;
                case "6":
                    printOrders();
                    break;
                case "7":
                    printCustomers();
                    break;
                case "8":
                    checkForCustomer();
                    break;
                case "X":
                    exit = exit();
                    break;
                default:
                    System.out.println("Opción no reconocida.  Introduzca una opción del menú.");
            }
        } while (!exit);

    }

    //Case 1: imprimir los objetos CDs a la consola

    /**
     * Este método imprime todos los CDs del array list cdArrayList en la consola
     */
    public void printCds() {
        if (cdArrayList.isEmpty()){
            System.out.println("No CDs han sido registrados.");
        } else {
            for (CD cd : cdArrayList) {
                System.out.println(dashedLnBreak);
                System.out.println(cd);
            }
        }
    }

    //Case 2: imprimir los objetos vinilos a la consola

    /**
     * Este método imprime todos los vinilos de array list vinylArrayList en la consola
     */
    public void printVinyls() {
        if (vinylArrayList.isEmpty()){
            System.out.println("No vinilos han sido registrados.");
        } else {
            for (Vinyl vinyl : vinylArrayList) {
                System.out.println(dashedLnBreak);
                System.out.println(vinyl);
            }
        }
    }

    //Case 3 y 4: registrar un objeto CD (caso 3) o vinilo (caso 4)

    /**
     * Este método registra los detalles comunes del CD y vinilo de la clase padre (album) e invoca el método
     * que corresponde al tipo de objeto que el usuario quiera crear.
     * @param obj el tipo de objeto (CD o vinilo) para determinar cual método registerAlbumDetails() invoca
     */
    private void registerAlbumDetails(OBJECT_TYPE obj) {
        boolean exitLoop = false;

        while (!exitLoop) {
            System.out.println("Introduzca el nombre del artista o grupo: ");
            String artist = input.nextLine();
            System.out.println("Introduzca el nombre del album: ");
            String name = input.nextLine();
            System.out.println("¿Es el contenido explícito? Introduzca true (sí) o false (no): ");
            boolean isExplicit = validateBoolean();
            System.out.println("Introduzca el número de pistas: ");
            int tracks = validateInteger();
            System.out.println("Introduzca el precio: ");
            double price = validateDouble();

            if (obj == OBJECT_TYPE.CD)
                createCd(artist, name, isExplicit, tracks, price);
            else
                createVinyl(artist, name, isExplicit, tracks, price);

            System.out.println("¿Quieres añadir otro? Introduzca 0 (no) o 1 (si): ");
            int option = validateInteger();

            exitLoop = option != 1;

        }
    }

    /**
     * Este método crea una nueva instancia del CD y pasalo al array list de CDs.  Recibe como parámetros
     * los detalles comunes a un albúm (parent class) y se obtiene los parámetros específicos al objeto CD.
     * @param artist el artista o grupo del album
     * @param name  el nombre del album
     * @param isExplicit si el album contiene lenguaje explícito o no
     * @param tracks el número de pistas del album
     * @param price el precio del album
     */
    public void createCd(String artist, String name, boolean isExplicit, int tracks, double price) {
        System.out.println("¿Es la edición importada? Introduzca true (si) o false (no): ");
        boolean isImported = validateBoolean();
        CD cd = new CD(artist, name, isExplicit, tracks, price, isImported);

        boolean isDuplicate = isCdDuplicate(cd);

        if (!isDuplicate) { cdArrayList.add(cd); }
    }

    /**
     * Este método comprueba si el nuevo objeto está registrado en el sistema
     * @param cd el nuevo objeto creado por el usuario
     * @return boolean
     */
    public boolean isCdDuplicate(CD cd) {
        for (CD item : cdArrayList) {
            if (item.equals(cd)) {
                System.out.println("El CD está registrado en el sistema ya.\n");
                return true;
            }
        }
        return false;
    }

    /**
     * Este método crea una nueva instancia del vinilo y pasalo al array list de CDs.  Recibe como sus parámetros
     * los detalles comunes a un album (parent class) y se obtiene los parámetros específicos al objeto vinilo.
     * @param artist el artista o grupo del album
     * @param name el nombre del album
     * @param isExplicit si el album contiene lenguaje explícito o no
     * @param tracks el número de pistas del album
     * @param price el precio del album
     */
    public void createVinyl(String artist, String name, boolean isExplicit, int tracks, double price) {
        System.out.println("¿Es el vinilo de segunda mano? Introduzca true (sí) or false (no): ");
        boolean isSecondHand = validateBoolean();
        Vinyl vinyl = new Vinyl(artist, name, isExplicit, tracks, price, isSecondHand);

        boolean isDuplicate = isVinylDuplicate(vinyl);

        if (!isDuplicate) { vinylArrayList.add(vinyl); }
    }

    /**
     * Este método comprueba si el nuevo objeto está registrado en el sistema
     * @param vinyl el nuevo objeto creado por el usuario
     * @return boolean
     */
    public boolean isVinylDuplicate(Vinyl vinyl) {
        for (Vinyl item : vinylArrayList) {
            if (item.equals(vinyl)) {
                System.out.println("El vinilo está registrado en el sistema ya.");
                return true;
            }
        }
        return false;
    }

    //Case 5: registrar un objeto order (pedido)

    /**
     * Este método crea un objeto Order (pedido) y si el cliente no está registrado, para quien el pedido corresponde,
     * hay que añadir la ID del cliente al array list de pedidos, y si el cliente no está registrado, invoca el método
     * para crear un nuevo objeto
     * Customer (cliente)
     */
    public void placeOrder(){
        boolean exitLoop = false;

        while(!exitLoop){
            //crea la ID del pedido
            int orderId = orderIdCount;
            orderIdCount++;
            int customerId = customerIdCount;

            //comprueba si el cliente está registrado
            boolean isCustomer = isCustomer();
            if (isCustomer) {
                customerId = getId(orderId);                         //si está registrado, obtiene su ID
            } else {
                createCustomer(customerId, orderId);                //si no, crea un nuevo objeto Customer (cliente) usando el customer ID creado
                customerIdCount++;                                  //incrementa el valor para el próximo nuevo cliente
            }

            //obtiene los detalles del pedido
            OBJECT_TYPE orderType = setType();
            System.out.println("Introduzca el nombre del artista o grupo: ");
            String artist = input.nextLine();
            System.out.println("Introduzca el nombre del albúm: ");
            String name = input.nextLine();
            //añade el objeto al array list de pedidos
            Order order = new Order(orderId, orderType, artist, name, customerId);

            boolean isDuplicate = isOrderDuplicate(order);

            if (!isDuplicate) { orderArrayList.add(order); }

            //comprueba si el usuario quiere añadir otro pedido
            System.out.println("¿Quieres añadir otro? Introduzca 0 (no) o 1 (si): ");
            int option = validateInteger();

            exitLoop = option != 1;
        }
    }

    /**
     * Este método valida el tipo del pedido introducido y vuelve un integer representándolo al método placeOrder()
     */
    public OBJECT_TYPE setType() {
        int option = -1;

        do {
            try {
                System.out.println("Introduzca 1 para CD o 2 para vinilo.");
                option = parseInt(input.nextLine());
            } catch (NumberFormatException nfe) {
                System.out.println("La entrada es inválida.");
            }
        } while (option != 1 && option != 2);

        return (option == 1) ? OBJECT_TYPE.CD : OBJECT_TYPE.VINYL;
    }

    /**
     * Este método comprueba si el nuevo objeto está registrado en el sistema
     * @param order el nuevo objeto creado por el usuario
     * @return boolean
     */
    public boolean isOrderDuplicate(Order order) {
        for (Order item : orderArrayList) {
            if (item.equals(order)) {
                System.out.println("El cliente ha puesto una reserva de este ítem anteriormente.\n");
                return true;
            }
        }
        return false;
    }

    /**
     * Este método confirma si el cliente está en el sistema o no
     * @return boolean
     */
    public boolean isCustomer() {
        System.out.println("¿El cliente tiene ID? Introduzca true (si) o false (no): ");
        return validateBoolean();
    }

    /**
     * Este método obtiene la ID del cliente si el cliente está registrado o se crea una ID si el cliente no está.
     * Si el cliente no existe, el método invoca createCustomer(customerId, orderId) para
     * crear un cliente; los parámetros pasados son requeridos para crear un nuevo objeto Customer (cliente).
     * @param orderId la ID del pedido
     * @return int
     */
    public int getId(int orderId){
        //obtiene la ID
        System.out.println("Introduzca la ID del cliente actual: ");
        int customerId = validateInteger();

        //valida la ID
        for (Customer customer : customerArrayList) {
            if (customer.getCustomerId() == customerId) {
                customer.setOrderNum(orderId);
                break;
            } else {
                customerId = customerIdCount;
                customerIdCount++;                                  //incrementa el valor para el próximo nuevo cliente
                createCustomer(customerId, orderId);
            }
        }
        return customerId;
    }

    /**
     * Este método crea un objeto Customer (cliente)
     * @param customerId la ID del cliente
     * @param orderId la ID del pedido
     */
    public void createCustomer(int customerId, int orderId) {
        System.out.println("Introduzca el nombre del cliente: ");
        String name = input.nextLine();
        System.out.println("Introduzca el/los apellido(s) del cliente:");
        String surname = input.nextLine();
        System.out.println("Introduzca el teléfono del cliente: ");
        String tel = input.nextLine();
        System.out.println("Introduzca el email del cliente: ");
        String email = input.nextLine();
        Customer customer = new Customer(customerId, name, surname, tel, email, orderId);

        customerArrayList.add(customer);
    }

    //Caso 6: imprimir los objetos order (pedido) a la consola

    /**
     * Este procedimiento imprime el objeto Order (pedido) en la consola
     */
    public void printOrders() {
        if (orderArrayList.isEmpty()) {
            System.out.println("No pedidos han sido registrados");
        } else {
            for (Order order : orderArrayList) {
                System.out.println(dashedLnBreak);
                System.out.println(order);
            }
        }
    }

    //Caso 7: imprimir los objetos customer (cliente) a la consola

    /**
     * Este método imprime el array list de clientes
     */
    public void printCustomers() {
        if (customerArrayList.isEmpty() ) {
            System.out.println("No clientes han sido registrados.");
        } else {
            for (Customer customer : customerArrayList) {
                System.out.println(dashedLnBreak);
                System.out.println(customer);
            }
        }
    }

    //Caso 8: comprobar si el cliente existe en el sistema

    /**
     * Este método obtiene los detalles del cliente para comprobar si esté registrado.  Crea un objeto Customer (cliente)
     * que pasa al método isCustomerDuplicate(Customer other)
     */
    public void checkForCustomer(){
        System.out.println("Introduzca la ID del cliente: ");
        int id = validateInteger();
        System.out.println("Introduzca el nombre: ");
        String name = input.nextLine();
        System.out.println("Introduzca el apellido: ");
        String surname = input.nextLine();
        System.out.println("Introduzca el número del teléfono: ");
        String tel = input.nextLine();
        System.out.println("Introduzca el email: ");
        String email = input.nextLine();
        Customer customer = new Customer(id, name, surname, tel, email);
        isCustomerDuplicate(customer);
    }

    /**
     * Este método comprueba si el cliente está registrado o no y notifica al usuario.
     * @param other el cliente que queremos buscar en el sistema
     */
    public void isCustomerDuplicate(Customer other){
        for (Customer customer : customerArrayList){
            if (customer.equals(other)) {
                System.out.println("El cliente existe en el sistema.");
                System.out.println(dashedLnBreak);
                System.out.println(customer.toString());
                System.out.println(dashedLnBreak);
            } else {
                System.out.println("El cliente NO existe en el sistema.");
            }
        }
    }

    //Caso X: salir de la aplicación

    /**
     * Este método apaga la aplicación y notifica al usuario
     */
    private boolean exit(){
        boolean exit = true;
        System.out.println("Apagando la aplicación.");

        return exit;
    }
}
