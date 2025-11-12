package za.ac.cput.factory;

import za.ac.cput.domain.Address;
import za.ac.cput.domain.Branch;
import za.ac.cput.util.Helper;

public class BranchFactory {
    public static Branch createBranch(String name, Address address,String contactNumber) {
        if(Helper.isStringNullorEmpty(name)||Helper.isNumber10Digits(contactNumber)||Helper.isObjectNull(address))
        {
            return null;
        }
        return new Branch.BranchBuilder()
                .setName(name)
                .setAddress(address)
                .setContactNumber(contactNumber).build();

    }
}
