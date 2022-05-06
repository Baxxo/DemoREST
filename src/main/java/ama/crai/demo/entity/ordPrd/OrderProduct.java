package ama.crai.demo.entity.ordPrd;

import ama.crai.demo.entity.Order;
import ama.crai.demo.entity.Product;

import javax.persistence.*;

@Entity
@Table(name = "ord_prd")
public class OrderProduct {

    @EmbeddedId
    private OrdPrdKey id;

    @ManyToOne
    @MapsId("idOrd")
    @JoinColumn(name = "id_ord")
    private Order order;

    @ManyToOne
    @MapsId("namePrd")
    @JoinColumn(name = "name_prd")
    private Product product;

    @Column(name = "qta")
    private int quantity;

    public OrderProduct() {
    }

    public OrderProduct(Order order, Product product, int quantity) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.id = new OrdPrdKey(order.getId(), product.getName());
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public OrdPrdKey getId() {
        return id;
    }

    public void setId(OrdPrdKey id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "OrderProduct{" +
                "id=" + id +
                ", order=" + order +
                ", product=" + product +
                ", quantity=" + quantity +
                '}';
    }


}
