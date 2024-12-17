package org.yearup.data;


import org.yearup.models.Profile;

public interface ProfileDao
{
    Profile create(Profile profile);

    Profile getByUserID(int userID);

    Profile updateProfile(Profile profile);
}
