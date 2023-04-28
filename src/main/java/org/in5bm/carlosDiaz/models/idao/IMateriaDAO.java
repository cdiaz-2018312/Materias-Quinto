package org.in5bm.carlosDiaz.models.idao;

import java.util.List;
import org.in5bm.carlosDiaz.domain.Materia;


public interface IMateriaDAO {
    public List<Materia> getAll();
    
    public int add(Materia materia);
    
    public int update(Materia materia);
    
    public int delete(Materia materia);
}
