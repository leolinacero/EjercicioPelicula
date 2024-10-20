package DAO;

import java.util.List;

public interface DAO <T>{
    /**El método devuelve una lista de todos los objetos*/
    public List<T> findAll();
    /**
     * El método devuelve un objeto por el id que se le pasa por parámetro
     * @param id del objeto
     * */
    public T findById(Integer id);
    /**
     * El método persiste el nuevo objeto en la base de datos
     * @param t el nuevo objeto pasado por parámetro
     * */
    public void save(T t);
    /**
     * El método modifica los cambios de un objeto pasado por parámetro
     * @param t el objeto modificado
     * */
    public void update (T t);
    /**
     * El método elimina un objeto de la base de datos
     * @param t el objeto eliminado
     * */
    public void delete (T t);


}
