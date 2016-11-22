import org.apache.axis2.AxisFault;

import java.util.Scanner;

public class App {

    private Scanner scanner;
    private CommandHandler commandHandler;

    public static void main(String[] args) {
        App app = new App();

        Command command;
        do {
            printUsage();
            command = app.getCommand();
            app.handleCommand(command);

        } while (command != Command.EXIT);
    }

    private App() {
        scanner = new Scanner(System.in);
        try {
            ServiceClient client = new ServiceClient();
            commandHandler = new CommandHandler(client);
        } catch (AxisFault axisFault) {
            axisFault.printStackTrace();
        }
    }

    private enum  Command {
        CREATE_ORDER, CREATE_PRODUCT, ADD_PRODUCT_TO_ORDER, GET_ORDER, GET_PRODUCT, EXIT,
        INVALID_COMMAND
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

    private void handleCommand(Command command) {
        switch (command) {
            case CREATE_ORDER:
                commandHandler.createOrder();
                break;
            case CREATE_PRODUCT:
                commandHandler.createProduct();
                break;
            case ADD_PRODUCT_TO_ORDER:
                commandHandler.addProductToOrder();
                break;
            case GET_ORDER:
                commandHandler.getOrder();
                break;
            case GET_PRODUCT:
                commandHandler.getProduct();
                break;
            case INVALID_COMMAND:
                commandHandler.invalidCommand();
                break;
        }
    }

    private static void printUsage() {
        System.out.println("\n$ CREATE_ORDER [0], CREATE_PRODUCT [1], ADD_PRODUCT_TO_ORDER [2], " +
                "GET_ORDER [3], GET_PRODUCT [4], EXIT [5] >");
    }

}
