/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examenparte1;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static VehiculoDAO vehiculoDAO;

    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            vehiculoDAO = new VehiculoDAO(connection);
            Scanner scanner = new Scanner(System.in);
            int option;

            do {
                System.out.println("Seleccione una opción:");
                System.out.println("1. Consultar todos los vehículos");
                System.out.println("2. Consultar un vehículo por ID");
                System.out.println("3. Insertar un nuevo vehículo");
                System.out.println("4. Eliminar un vehículo por ID");
                System.out.println("5. Actualizar un vehículo");
                System.out.println("6. Obtener la potencia media según el tipo de vehículo");
                System.out.println("7. Obtener la potencia máxima según el tipo de vehículo");
                System.out.println("0. Salir");

                option = scanner.nextInt();
                scanner.nextLine();  // Consumir el salto de línea

                switch (option) {
                    case 1:
                        consultarTodos();
                        break;
                    case 2:
                        consultarPorId(scanner);
                        break;
                    case 3:
                        insertarVehiculo(scanner);
                        break;
                    case 4:
                        eliminarVehiculo(scanner);
                        break;
                    case 5:
                        actualizarVehiculo(scanner);
                        break;
                    case 6:
                        obtenerPotenciaMedia(scanner);
                        break;
                    case 7:
                        obtenerPotenciaMaxima(scanner);
                        break;
                    case 0:
                        System.out.println("Saliendo...");
                        break;
                    default:
                        System.out.println("Opción no válida.");
                        break;
                }
            } while (option != 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void consultarTodos() throws SQLException {
        List<Vehiculo> vehiculos = vehiculoDAO.consultarTodos();
        for (Vehiculo vehiculo : vehiculos) {
            System.out.println(vehiculo);
        }
    }

    private static void consultarPorId(Scanner scanner) throws SQLException {
        System.out.println("Ingrese el ID del vehículo:");
        int id = scanner.nextInt();
        Vehiculo vehiculo = vehiculoDAO.consultarPorId(id);
        if (vehiculo != null) {
            System.out.println(vehiculo);
        } else {
            System.out.println("Vehículo no encontrado.");
        }
    }

    private static void insertarVehiculo(Scanner scanner) throws SQLException {
        System.out.println("Ingrese el tipo del vehículo:");
        String tipo = scanner.nextLine();
        System.out.println("Ingrese la marca del vehículo:");
        String marca = scanner.nextLine();
        System.out.println("Ingrese la potencia del vehículo:");
        int potencia = scanner.nextInt();
        System.out.println("Ingrese la fecha de compra (yyyy-mm-dd):");
        String fecha = scanner.next();

        Vehiculo vehiculo = new Vehiculo(0, tipo, marca, potencia, java.sql.Date.valueOf(fecha));
        vehiculoDAO.insertarVehiculo(vehiculo);
        System.out.println("Vehículo insertado correctamente.");
    }

    private static void eliminarVehiculo(Scanner scanner) throws SQLException {
        System.out.println("Ingrese el ID del vehículo a eliminar:");
        int id = scanner.nextInt();
        vehiculoDAO.eliminarVehiculo(id);
        System.out.println("Vehículo eliminado correctamente.");
    }

    private static void actualizarVehiculo(Scanner scanner) throws SQLException {
        System.out.println("Ingrese el ID del vehículo a actualizar:");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consumir el salto de línea

        System.out.println("Ingrese el nuevo tipo del vehículo:");
        String tipo = scanner.nextLine();
        System.out.println("Ingrese la nueva marca del vehículo:");
        String marca = scanner.nextLine();
        System.out.println("Ingrese la nueva potencia del vehículo:");
        int potencia = scanner.nextInt();
        System.out.println("Ingrese la nueva fecha de compra (yyyy-mm-dd):");
        String fecha = scanner.next();

        Vehiculo vehiculo = new Vehiculo(id, tipo, marca, potencia, java.sql.Date.valueOf(fecha));
        vehiculoDAO.actualizarVehiculo(vehiculo);
        System.out.println("Vehículo actualizado correctamente.");
    }

    private static void obtenerPotenciaMedia(Scanner scanner) throws SQLException {
        System.out.println("Ingrese el tipo de vehículo:");
        String tipo = scanner.nextLine();
        double potenciaMedia = vehiculoDAO.obtenerPotenciaMedia(tipo);
        System.out.println("La potencia media de los vehículos tipo " + tipo + " es: " + potenciaMedia);
    }

    private static void obtenerPotenciaMaxima(Scanner scanner) throws SQLException {
        System.out.println("Ingrese el tipo de vehículo:");
        String tipo = scanner.nextLine();
        int potenciaMaxima = vehiculoDAO.obtenerPotenciaMaxima(tipo);
        System.out.println("La potencia máxima de los vehículos tipo " + tipo + " es: " + potenciaMaxima);
    }
}

}
