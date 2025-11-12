package za.ac.cput.service.HallService;

import za.ac.cput.domain.Hall;
import za.ac.cput.service.IService;

import java.util.List;

public interface IHallService extends IService <Hall,Long>{
    List<Hall> getAllHalls();

}
