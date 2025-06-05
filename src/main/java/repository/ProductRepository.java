package repository;

import db.DbConnection;
import domain.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    public List<Product> findAll() throws SQLException {
        List<Product> products = new ArrayList<>();
        final var query = "SELECT id, name, value, cod, sku FROM Product";
        try (Connection conn = DbConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Product p = new Product(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getBigDecimal("value"),
                        rs.getString("cod"),
                        rs.getString("sku"));
                products.add(p);
            }
        }
        return products;
    }

    public void save(Product product) throws SQLException {
        final var query = "INSERT INTO Product(name, value, cod, sku) VALUES (?, ?, ?, ?)";
        try(Connection conn = DbConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, product.getName());
            stmt.setBigDecimal(2, product.getValue());
            stmt.setString(3, product.getCod());
            stmt.setString(4, product.getSku());

            stmt.executeUpdate();
        }
    }
}
