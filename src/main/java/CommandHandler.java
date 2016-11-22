import com.wso2.gayanw.axis2.xsd.Order;
import com.wso2.gayanw.axis2.xsd.Product;

import java.util.Scanner;

public class CommandHandler {

    private ServiceClient client;
    private Scanner scanner;

    CommandHandler(ServiceClient client) {
        this.client = client;
        scanner = new Scanner(System.in);
    }

    public void createOrder() {
        client.createOrder();
        System.out.println("Order successfully created with id: " + client.getLastOrderId());
    }

    public void invalidCommand() {
        System.out.println("Invalid Command");
    }

    public void getProduct() {
        System.out.println("GET PRODUCT >");
        System.out.print("id > ");
        int id = Integer.parseInt(scanner.nextLine());

        Product product = client.getProductById(id);
        if (product != null) {
            System.out.println(product);
        }
    }

    public void getOrder() {
        System.out.println("GET ORDER >");
        System.out.print("id > ");
        int id = Integer.parseInt(scanner.nextLine());

        Order order = client.getOrderById(id);
        if (order != null) {
            System.out.println(order);
        }
    }

    public void addProductToOrder() {
        System.out.println("ADD PRODUCT TO ORDER >");
        System.out.print("order id >");
        int orderId = Integer.parseInt(scanner.nextLine());
        System.out.print("product id >");
        int productId = Integer.parseInt(scanner.nextLine());
        System.out.print("count >");
        int count = Integer.parseInt(scanner.nextLine());

        client.addProductToOrder(orderId, productId, count);
    }

    public void createProduct() {
        System.out.println("CREATE PRODUCT >");
        System.out.print("name > ");
        String name = scanner.nextLine();
        System.out.print("value > ");
        float value = Float.valueOf(scanner.nextLine());

        client.createProduct(name, value);
        System.out.println("Product successfully created with id: " + client.getLastProductId());
    }
}
