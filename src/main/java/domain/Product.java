package domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private Long id;
    private String name;
    private BigDecimal value;
    private String cod;
    private String sku;

    public Product() {
    }

    public Product(Long id, String name, BigDecimal value, String cod, String sku) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.cod = cod;
        this.sku = sku;
    }

    public Product(String name, BigDecimal value, String cod, String sku) {
        this.name = name;
        this.value = value;
        this.cod = cod;
        this.sku = sku;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(value, product.value) && Objects.equals(cod, product.cod) && Objects.equals(sku, product.sku);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, value, cod, sku);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", cod='" + cod + '\'' +
                ", sku='" + sku + '\'' +
                '}';
    }
}
