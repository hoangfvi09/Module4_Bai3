package service;

import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductService implements IService<Product>{
    private String jdbcURL = "jdbc:mysql://localhost:3306/productManagement?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "123456";
    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }
    @Override
    public void save(Product product) {
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("insert into products (name, price, quantity, color, description, categoryId) values (?,?,?,?,?,?);")) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2,product.getPrice());
            preparedStatement.setInt(3,product.getQuantity());
            preparedStatement.setString(4,product.getColor());
            preparedStatement.setString(5,product.getDescription());
            preparedStatement.setInt(6,product.getCategoryId());
            preparedStatement.executeUpdate();
        } catch (SQLException ignored) {
        }
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall("select * from products;");) {
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                String productName = rs.getString("name");
                String description = rs.getString("description");
                int categoryId = rs.getInt("categoryId");
                String color = rs.getString("color");
                int price = rs.getInt("price");
                int quantity = rs.getInt("quantity");
                int id = rs.getInt("id");
                products.add(new Product(id, productName,price, quantity,color,description,categoryId));
            }

        } catch (SQLException e) {
        }
        return products;
    }

    @Override
    public Product findById(int id) {
        Product product = null;
        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall("select * from products where id = ?;");) {
            callableStatement.setInt(1,id);
            ResultSet rs = callableStatement.executeQuery();
            rs.next();
            String productName = rs.getString("name");
            String description = rs.getString("description");
            int categoryId = rs.getInt("categoryId");
            String color = rs.getString("color");
            int price = rs.getInt("price");
            int quantity = rs.getInt("quantity");
            product = new Product(id, productName,price, quantity,color,description,categoryId);

        } catch (SQLException e) {
        }
        return product;
    }

    @Override
    public void update(int id, Product product) {
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("update products set name= ?, price= ?, quantity= ?, color= ?, description = ?, categoryId = ? where id =?;");) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2,product.getPrice());
            preparedStatement.setInt(3,product.getQuantity());
            preparedStatement.setString(4,product.getColor());
            preparedStatement.setString(5,product.getDescription());
            preparedStatement.setInt(6,product.getCategoryId());
            preparedStatement.setInt(7,product.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ignored) {
        }

    }

    @Override
    public void delete(int id) {
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("delete from products where id = ?");) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException ignored) {
        }
    }
    public List <Product> findByName(String name){
        List<Product> products = new ArrayList<>();
        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall("select * from products where name like ?;");) {
            callableStatement.setString(1,name);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                String productName = rs.getString("name");
                String description = rs.getString("description");
                int categoryId = rs.getInt("categoryId");
                String color = rs.getString("color");
                int price = rs.getInt("price");
                int quantity = rs.getInt("quantity");
                int id = rs.getInt("id");
                products.add(new Product(id, productName,price, quantity,color,description,categoryId));
            }

        } catch (SQLException e) {
        }
        return products;
    }
}
