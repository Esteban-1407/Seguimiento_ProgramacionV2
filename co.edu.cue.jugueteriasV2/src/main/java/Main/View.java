package Main;

import customExceptions.ToyStoreException;
import mapping.dtos.EmployeesDTO;
import mapping.dtos.ToyDTO;
import services.ToyStorelmpl;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class View {

    private static ToyStorelmpl toyStore;

    public static void main(String[] args) throws SQLException {
        try {
            toyStore = new ToyStorelmpl();
            Scanner scanner = new Scanner(System.in);
            int choice = -1;

            while (choice != 0) {
                System.out.println("Bienvenido a la tienda de juguetes");
                System.out.println("1. Mostrar lista de juguetes");
                System.out.println("2. Buscar juguete por ID");
                System.out.println("3. Agregar nuevo juguete");
                System.out.println("4. Actualizar stock de un juguete");
                System.out.println("5. Obtener total de stock");
                System.out.println("6. Obtener valor total de inventario");
                System.out.println("7. Obtener tipo con más juguetes");
                System.out.println("8. Obtener tipo con menos juguetes");
                System.out.println("9. Obtener juguetes con valor mayor a cierto monto");
                System.out.println("10. Ordenar juguetes por cantidad de stock");
                System.out.println("11.Mostrar lista de ventas y sus detalles");
                System.out.println("0. Salir");
                System.out.print("Ingrese su elección: ");

                try {
                    choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline character
                    switch (choice) {
                        case 1:
                            listToys();
                            break;
                        case 2:
                            searchToy();
                            break;
                        case 3:
                            System.out.println("Aun no");
                            break;
                        case 4:
                            updateStock(scanner);
                            break;
                        case 5:
                            getTotalStock();
                            break;
                        case 6:
                            getTotalValue();
                            break;
                        case 7:
                            getTypeWithMostToys();
                            break;
                        case 8:
                            getTypeWithLeastToys();
                            break;
                        case 9:
                            getToysWithValueGreaterThan(scanner);
                            break;
                        case 10:
                            orderByStockQuantity();
                            break;
                            case 11:
                                listEmployees();
                                break;

                        case 0:
                            System.out.println("Gracias por visitar la tienda de juguetes. ¡Hasta luego!");
                            break;
                        default:
                            System.out.println("Opción no válida. Inténtelo de nuevo.");
                            break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Por favor, ingrese un número válido.");
                    scanner.nextLine(); // Consume incorrect input
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void listToys() {
        List<ToyDTO> toys = toyStore.listToys();
        displayToys(toys);
    }

    private static void searchToy() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el ID del juguete a buscar: ");
        int id = scanner.nextInt();
        try {
            ToyDTO toy = toyStore.search(id);
            if (toy != null) {
                System.out.println("Juguete encontrado:");
                System.out.println(toy);
            } else {
                System.out.println("No se encontró ningún juguete con el ID proporcionado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    private static void addNewToy(Scanner scanner) {
//        ToyDTO toyDTO = new ToyDTO();
//        System.out.print("Ingrese el nombre del juguete: ");
//        toyDTO.setName(scanner.nextLine());
//        System.out.print("Ingrese el precio del juguete: ");
//        toyDTO.setPrice(scanner.nextDouble());
//        scanner.nextLine(); // Consume newline character
//        System.out.print("Ingrese la categoría del juguete: ");
//        toyDTO.setCategory(scanner.nextLine());
//        System.out.print("Ingrese la cantidad en stock del juguete: ");
//        toyDTO.setStock(scanner.nextInt());
//        toyStore.addToy(toyDTO);
//        System.out.println("Juguete agregado con éxito.");
//    }

    private static void updateStock(Scanner scanner) {
        System.out.print("Ingrese el ID del juguete para actualizar el stock: ");
        int toyId = scanner.nextInt();
        System.out.print("Ingrese la cantidad de cambio de stock (positivo para agregar, negativo para restar): ");
        int quantityChange = scanner.nextInt();
        toyStore.updateStock(toyId, quantityChange);
        System.out.println("Stock actualizado con éxito.");
    }

    private static void getTotalStock() {
        int totalStock = toyStore.getTotalStock();
        System.out.println("El total de stock en la tienda es: " + totalStock);
    }

    private static void getTotalValue() {
        double totalValue = toyStore.getTotalValue();
        System.out.println("El valor total del inventario en la tienda es: " + totalValue);
    }

    private static void getTypeWithMostToys() {
        String typeWithMostToys = toyStore.getTypeWithMostToys();
        System.out.println("El tipo con más juguetes es: " + typeWithMostToys);
    }

    private static void getTypeWithLeastToys() {
        String typeWithLeastToys = toyStore.getTypeWithLeastToys();
        System.out.println("El tipo con menos juguetes es: " + typeWithLeastToys);
    }

    private static void getToysWithValueGreaterThan(Scanner scanner) {
        System.out.print("Ingrese el valor mínimo para los juguetes que desea buscar: ");
        int value = scanner.nextInt();
        List<ToyDTO> toys = toyStore.getToysWithValueGreaterThan(value);
        displayToys(toys);
    }

    private static void orderByStockQuantity() {
        List<ToyDTO> toys = toyStore.orderByStockQuantity();
        displayToys(toys);
    }
    private static void listEmployees() {
        List<EmployeesDTO> employees = toyStore.listEmployees();
        displayEmployees(employees);
    }


    private static void displayToys(List<ToyDTO> toys) {
        if (toys.isEmpty()) {
            System.out.println("No hay juguetes disponibles en la tienda.");
        } else {
            System.out.println("Lista de juguetes:");
            for (ToyDTO toy : toys) {
                System.out.println(toy);
            }
        }
    }
    private static void displayEmployees(List<EmployeesDTO> employees) {
        if (employees.isEmpty()) {
            System.out.println("No hay empleados disponibles.");
        } else {
            System.out.println("Lista de empleados:");
            for (EmployeesDTO employ : employees) {
                System.out.println(employ);
            }
        }
    }
}
