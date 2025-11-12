package za.ac.cput.service.ShowService;

import za.ac.cput.domain.Show;
import za.ac.cput.service.IService;

import java.util.List;

public interface IShowService extends IService <Show,Long>{
    List<Show>getAllShows();
}
