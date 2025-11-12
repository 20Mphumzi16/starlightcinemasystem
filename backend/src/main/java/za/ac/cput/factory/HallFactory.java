package za.ac.cput.factory;

import za.ac.cput.domain.Branch;
import za.ac.cput.domain.Hall;
import za.ac.cput.util.Helper;

public class HallFactory {
    public static Hall createHall(String name, int capacity, Branch branch) {
        if(Helper.isStringNullorEmpty(name)||capacity<=0||Helper.isObjectNull(branch)){
            return null;
        }
        return new Hall.HallBuilder()
                .setName(name)
                .setCapacity(capacity)
                .setBranch(branch)
                .build();
    }
}
