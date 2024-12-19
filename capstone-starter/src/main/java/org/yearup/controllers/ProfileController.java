package org.yearup.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProfileDao;
import org.yearup.data.UserDao;
import org.yearup.models.Profile;
import org.yearup.models.User;

import java.security.Principal;

@RestController
@CrossOrigin
public class ProfileController {
    private final ProfileDao profileDao;
    private final UserDao userDao;
    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);


    @Autowired
    public ProfileController(ProfileDao profileDao, UserDao userDao) {
        this.profileDao = profileDao;
        this.userDao = userDao;
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Profile getProfile(Principal principal) {
        try {
            // get the currently logged-in username
            String userName = principal.getName();
            // find database user by userId
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            // use the profileDao to get all items in the cart and return the cart
            return this.profileDao.getByUserID(userId);

        } catch (Exception e) {
            logger.error("Error getting profile: ", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    @PutMapping("/profile")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Profile updateProfile(@RequestBody Profile profile) {
        try {
            return this.profileDao.updateProfile(profile);
        } catch (Exception e) {
            logger.error("Error updating profile: ", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }
}
