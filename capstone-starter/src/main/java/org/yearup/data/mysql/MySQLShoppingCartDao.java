package org.yearup.data.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MySQLShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao {

    private final ProductDao productDao;
    private final ShoppingCart shoppingCart = new ShoppingCart();

    @Autowired
    public MySQLShoppingCartDao(DataSource dataSource, ProductDao productDao) {
        super(dataSource);
        this.productDao = productDao;
    }

    @Override
    public ShoppingCart getByUserId(int userId) {

        //query handles getting shopping cart by user id in db
        String query = """
                SELECT * FROM shopping_cart
                WHERE user_id = ?
                """;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);

            preparedStatement.executeQuery();
            try (ResultSet results = preparedStatement.executeQuery()) {
                while (results.next()) {
                    ShoppingCartItem item = new ShoppingCartItem();

                    int productId = results.getInt("product_id");
                    int quantity = results.getInt("quantity");

                    item.setProduct(this.productDao.getById(productId));
                    item.setQuantity(quantity);

                    shoppingCart.add(item);

                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return shoppingCart;
    }

    @Override
    public ShoppingCart addToCart(int productID, int userID) {
        //query handles adding to shopping cart in db
        String query = """
                INSERT INTO shopping_cart
                Values(?, ?, ?)
                """;
        ShoppingCartItem item = new ShoppingCartItem();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            Product product = this.productDao.getById(productID);
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, product.getProductId());
            preparedStatement.setInt(3, item.getQuantity());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                // Retrieve the generated keys
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

                if (generatedKeys.next()) {
                    // Retrieve the auto-incremented ID
                    int cartItem = generatedKeys.getInt(1);

                    // get the newly inserted item in card
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return getByUserId(userID);
    }

    @Override
    public ShoppingCart updateShoppingCart(int productID, int userID, int quantity) {
        //query handles updating shopping cart in db
        String query = """
                UPDATE shopping_cart
                SET quantity = ?
                WHERE user_id = ? AND product_id = ?""";


        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, userID);
            preparedStatement.setInt(3, productID);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return getByUserId(userID);
    }

    @Override
    public ShoppingCart clearShoppingCart(int userID) {
        //query handles deleting/clearing shopping cart in db
        String query = """
                DELETE FROM shopping_cart
                WHERE user_id = ?
                """;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userID);

            preparedStatement.executeUpdate();
            shoppingCart.getItems().clear();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return shoppingCart;
    }
}
