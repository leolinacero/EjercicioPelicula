package Views;

import Model.PeliCopia;
import Model.Pelicula;
import Model.User;

import java.util.List;

public class Session {


        public static User userSelected = null;

        public static PeliCopia copySelected = null;

        public static List<PeliCopia> copyDTO = null;

        public static Pelicula peliDTO = null;


        public static void paramsnotnull(){
            userSelected = null;
            copySelected = null;
            copyDTO = null;
            peliDTO = null;
        }



}
