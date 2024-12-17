package org.yearup.data;

import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

public interface ShoppingCartDao
{
    ShoppingCart getByUserId(int userId);
    // add additional method signatures here

    ShoppingCart addToCart(int productID, int userID);

    ShoppingCart updateShoppingCart(int productID, int userID, int quantity);

    ShoppingCart clearShoppingCart(int userID);
}
