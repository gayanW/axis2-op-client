import com.wso2.gayanw.axis2.AddProductToOrderDocument;
import com.wso2.gayanw.axis2.CreateOrderDocument;
import com.wso2.gayanw.axis2.CreateProductDocument;
import com.wso2.gayanw.axis2.GetLastOrderIdDocument;
import com.wso2.gayanw.axis2.GetLastOrderIdResponseDocument;
import com.wso2.gayanw.axis2.GetLastProductIdDocument;
import com.wso2.gayanw.axis2.GetLastProductIdResponseDocument;
import com.wso2.gayanw.axis2.GetOrderByIdDocument;
import com.wso2.gayanw.axis2.GetOrderByIdResponseDocument;
import com.wso2.gayanw.axis2.GetProductByIdDocument;
import com.wso2.gayanw.axis2.GetProductByIdResponseDocument;
import com.wso2.gayanw.axis2.OrderProcessingServiceStub;
import com.wso2.gayanw.axis2.xsd.Order;
import com.wso2.gayanw.axis2.xsd.Product;
import org.apache.axis2.AxisFault;

import java.rmi.RemoteException;

public class ServiceClient {

    private OrderProcessingServiceStub stub;

    public ServiceClient() throws AxisFault {
        stub = new OrderProcessingServiceStub();
    }

    public void createOrder() {
        CreateOrderDocument createOrderDoc = CreateOrderDocument.Factory.newInstance();
        createOrderDoc.addNewCreateOrder();
        try {
            stub.createOrder(createOrderDoc);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void createProduct(String name, float value) {
        CreateProductDocument createProductDoc = CreateProductDocument.Factory.newInstance();
        CreateProductDocument.CreateProduct createProduct = createProductDoc.addNewCreateProduct();
        createProduct.setName(name);
        createProduct.setValue(value);
        try {
            stub.createProduct(createProductDoc);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void addProductToOrder(int orderId, int productId, int count) {
        AddProductToOrderDocument addProductToOrderDoc = AddProductToOrderDocument.Factory.newInstance();
        AddProductToOrderDocument.AddProductToOrder addProductToOrder = addProductToOrderDoc.addNewAddProductToOrder();
        addProductToOrder.setOrderId(orderId);
        addProductToOrder.setProductId(productId);
        addProductToOrder.setCount(count);

        try {
            stub.addProductToOrder(addProductToOrderDoc);
        } catch (RemoteException e) {
            handleAxisFault((AxisFault) e);
        }
    }

    public Order getOrderById(int id) {
        GetOrderByIdDocument getOrderByIdDoc = GetOrderByIdDocument.Factory.newInstance();
        GetOrderByIdDocument.GetOrderById getOrderById = getOrderByIdDoc.addNewGetOrderById();
        getOrderById.setId(id);

        Order order = null;
        GetOrderByIdResponseDocument getOrderByIdResponseDoc;
        try {
            getOrderByIdResponseDoc = stub.getOrderById(getOrderByIdDoc);
            order = getOrderByIdResponseDoc.getGetOrderByIdResponse().getReturn();
        } catch (RemoteException e) {
            handleAxisFault((AxisFault) e);
        }
        return order;
    }

    public Product getProductById(int id) {
        GetProductByIdDocument getProductByIdDoc = GetProductByIdDocument.Factory.newInstance();
        GetProductByIdDocument.GetProductById getProductById = getProductByIdDoc.addNewGetProductById();
        getProductById.setId(id);

        Product product = null;
        GetProductByIdResponseDocument getProductByIdResponseDoc;
        try {
            getProductByIdResponseDoc = stub.getProductById(getProductByIdDoc);
            product = getProductByIdResponseDoc.getGetProductByIdResponse().getReturn();
        } catch (RemoteException e) {
            handleAxisFault((AxisFault) e);
        }
        return product;
    }

    public int getLastOrderId() {
        GetLastOrderIdDocument getLastOrderIdDoc = GetLastOrderIdDocument.Factory.newInstance();
        getLastOrderIdDoc.addNewGetLastOrderId();
        int lastOrderId = -1;
        try {
            GetLastOrderIdResponseDocument getLastOrderIdResponseDoc = stub.getLastOrderId(getLastOrderIdDoc);
            lastOrderId = getLastOrderIdResponseDoc.getGetLastOrderIdResponse().getReturn();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return lastOrderId;
    }

    public int getLastProductId() {
        GetLastProductIdDocument getLastProductIdDoc = GetLastProductIdDocument.Factory.newInstance();
        getLastProductIdDoc.addNewGetLastProductId();
        int lastProductId = -1;
        try {
            GetLastProductIdResponseDocument getLastProductIdResponseDoc = stub.getLastProductId(getLastProductIdDoc);
            lastProductId = getLastProductIdResponseDoc.getGetLastProductIdResponse().getReturn();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return lastProductId;
    }

    private static void handleAxisFault(AxisFault axisFault) {
        String faultCode = axisFault.getFaultCode().getLocalPart();
        switch (faultCode) {
            case "NoSuchProductFault":
                System.out.println("No such product");
                break;
            case "NoSuchOrderFault":
                System.out.println("No such order");
                break;
            default:
                axisFault.printStackTrace();
                break;
        }
    }

}
