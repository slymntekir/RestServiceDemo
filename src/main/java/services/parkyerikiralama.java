package services;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/parkyeri")
public class parkyerikiralama extends Application {
    
    private Set<Object> singletons = new HashSet<Object>();

    public parkyerikiralama() {
        singletons.add(new resteasyone());
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
