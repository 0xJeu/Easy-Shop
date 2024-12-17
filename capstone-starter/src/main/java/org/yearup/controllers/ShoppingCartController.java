package org.yearup.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.User;

import java.security.Principal;

// convert this class to a REST controller
// only logged-in users should have access to these actions
@RestController
public class ShoppingCartController {
    // a shopping cart requires
    private ShoppingCartDao shoppingCartDao;
    private UserDao userDao;
    private ProductDao productDao;
    private static final Logger logger = LoggerFactory.getLogger(ShoppingCartController.class);

    @Autowired
    public ShoppingCartController(ShoppingCartDao shoppingCartDao, UserDao userDao, ProductDao productDao) {
        this.shoppingCartDao = shoppingCartDao;
        this.userDao = userDao;
        this.productDao = productDao;
    }

    // each method in this controller requires a Principal object as a parameter
    @GetMapping("/cart")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ShoppingCart getCart(Principal principal) {
        try {
            // get the currently logged in username
            String userName = principal.getName();
            // find database user by userId
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            // use the shoppingcartDao to get all items in the cart and return the cart
            return this.shoppingCartDao.getByUserId(userId);
        } catch (Exception e) {
            logger.error("Error getting cart: ", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    // add a POST method to add a product to the cart - the url should be
    // https://localhost:8080/cart/products/15 (15 is the productId to be added)
    @PostMapping("/cart/products/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ShoppingCart addToCart(@PathVariable int id, Principal principal) {

        try {
            // get the currently logged-in username
            String userName = principal.getName();
            // find database user by userId
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            return this.shoppingCartDao.addToCart(id, userId);

        } catch (Exception e) {
            logger.error("Error adding to cart: ", e);
            throw new RuntimeException(e);
        }
    }


    // add a PUT method to update an existing product in the cart - the url should be
    // https://localhost:8080/cart/products/15 (15 is the productId to be updated)
    // the BODY should be a ShoppingCartItem - quantity is the only value that will be updated
    @PutMapping("cart/products/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ShoppingCart updateShoppingCart(@PathVariable int id, @RequestBody ShoppingCartItem shoppingCartItem, Principal principal) {

        try {
            // get the currently logged-in username
            String userName = principal.getName();
            // find database user by userId
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

//            logger.info("Product ID: {}, ShoppingCartItem: {}, Principal: {}", id, shoppingCartItem, principal);
//            logger.info("Updating cart for User ID: {}, Product ID: {}, New Quantity: {}", userId, id, shoppingCartItem.getQuantity());

            // use the shoppingcartDao to update items in the cart and return the cart
            return this.shoppingCartDao.updateShoppingCart(id, userId, shoppingCartItem.getQuantity());

        } catch (Exception e) {
            logger.error("Error adding to cart: ", e);
            throw new RuntimeException(e);
        }
    }



    // add a DELETE method to clear all products from the current users cart
    // https://localhost:8080/cart
    @DeleteMapping("/cart")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ShoppingCart clearCart(Principal principal) {
        try {
            // get the currently logged-in username
            String userName = principal.getName();
            // find database user by userId
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            // use the shoppingcartDao to clear all items in the cart and return the cart
            return this.shoppingCartDao.clearShoppingCart(userId);
        } catch (Exception e) {
            logger.error("Error clearing cart: ", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

}
