package za.ac.cput.factory;

import za.ac.cput.domain.Address;
import za.ac.cput.util.Helper;

public class AddressFactory {
    public static Address buildAddress(String street, String suburb, String City, String postalCode){
        if(Helper.isStringNullorEmpty(street)||Helper.isStringNullorEmpty(suburb)||Helper.isStringNullorEmpty(City)||Helper.isInvalidPostalCode(postalCode)){
            return null;
        }
        return new Address.AddressBuilder()
                .setStreet(street)
                .setSuburb(suburb)
                .setCity(City)
                .setPostalCode(postalCode)
                .build();

    }
}