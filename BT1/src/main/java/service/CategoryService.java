package service;

import model.Category;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryService implements IService<Category>{
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
    public void save(Category category) {

    }

    @Override
    public List<Category> findAll() {
        return null;
    }

    @Override
    public Category findById(int id) {
        Category category= null;
        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall("select * from category c where c.id = ?;");) {
            callableStatement.setInt(1,id);
            ResultSet rs = callableStatement.executeQuery();
            rs.next();
            String name = rs.getString("name");
            category = new Category(id, name);

        } catch (SQLException e) {
        }
        return category;
    }

    @Override
    public void update(int id, Category category) {

    }

    @Override
    public void delete(int id) {

    }

    public List <Category> findByProductList(List<Product> products){
        List<Category> categoryList = new ArrayList<>();
        for (Product product:products
        ) {
            categoryList.add(findById(product.getCategoryId()));
        }
        return categoryList;
    }


}
