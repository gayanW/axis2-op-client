import com.wso2.gayanw.axis2.xsd.Order;
import com.wso2.gayanw.axis2.xsd.Product;
import org.apache.axis2.AxisFault;

import java.util.Scanner;

public class App {

    private Scanner scanner;
    private ServiceClient serviceClient;

    public App() {
        scanner = new Scanner(System.in);
        try {
            serviceClient = new ServiceClient();
        } catch (AxisFault axisFault) {
            axisFault.printStackTrace();
        }
    }

    public static void main(String[] args) {
        App app = new App();

        Command command;
        do {
            printUsage();
            command = app.getCommand();
            app.handleCommand(command);

        } while (command != Command.EXIT);
    }

    private Command getCommand() {
        String commandStr = scanner.nextLine();
        Command command;
        switch (commandStr) {
            case "0":
                command = Command.CREATE_ORDER;
                break;
            case "1":
                command = Command.CREATE_PRODUCT;
                break;
            case "2":
                command = Command.ADD_PRODUCT_TO_ORDER;
                break;
            case "3":
                command = Command.GET_ORDER;
                break;
            case "4":
                command = Command.GET_PRODUCT;
                break;
            case "5":
                command = Command.EXIT;
                break;
            default:
                command = Command.INVALID_COMMAND;
                break;
        }
        return command;
    }

    private enum  Command {
        CREATE_ORDER, CREATE_PRODUCT, ADD_PRODUCT_TO_ORDER, GET_ORDER, GET_PRODUCT, EXIT,
        INVALID_COMMAND
    }


    private void handleCommand(Command command) {
        switch (command) {
            case CREATE_ORDER:
                handleCreateOrderCommand();
                break;
            case CREATE_PRODUCT:
                handleCreateProductCommand();
                break;
            case ADD_PRODUCT_TO_ORDER:
                handleAddProductToOrderCommand();
                break;
            case GET_ORDER:
                handleGetOrderCommand();
                break;
            case GET_PRODUCT:
                handleGetProductCommand();
                break;
            case INVALID_COMMAND:
                handleInvalidCommand();
                break;
        }
    }

    private void handleCreateOrderCommand() {
        serviceClient.createOrder();
        System.out.println("Order successfully created with id: " + serviceClient.getLastOrderId());
    }

    private void handleInvalidCommand() {
        System.out.println("Invalid Command");

    }

    private void handleGetProductCommand() {
        System.out.println("GET PRODUCT >");
        System.out.print("id > ");
        int id = Integer.parseInt(scanner.nextLine());

        Product product = serviceClient.getProductById(id);
        if (product != null) {
            System.out.println(product);
        }
    }

    private void handleGetOrderCommand() {
        System.out.println("GET ORDER >");
        System.out.print("id > ");
        int id = Integer.parseInt(scanner.nextLine());

        Order order = serviceClient.getOrderById(id);
        if (order != null) {
            System.out.println(order);
        }
    }

    private void handleAddProductToOrderCommand() {
        System.out.println("ADD PRODUCT TO ORDER >");
        System.out.print("order id >");
        int orderId = Integer.parseInt(scanner.nextLine());
        System.out.print("product id >");
        int productId = Integer.parseInt(scanner.nextLine());
        System.out.print("count >");
        int count = Integer.parseInt(scanner.nextLine());

        serviceClient.addProductToOrder(orderId, productId, count);
    }

    private void handleCreateProductCommand() {
        System.out.println("CREATE PRODUCT >");
        System.out.print("name > ");
        String name = scanner.nextLine();
        System.out.print("value > ");
        float value = Float.valueOf(scanner.nextLine());

        serviceClient.createProduct(name, value);
        System.out.println("Product successfully created with id: " + serviceClient.getLastProductId());
    }

    private static void printUsage() {
        System.out.println("\n$ CREATE_ORDER [0], CREATE_PRODUCT [1], ADD_PRODUCT_TO_ORDER [2], " +
                "GET_ORDER [3], GET_PRODUCT [4], EXIT [5] >");
    }

}
