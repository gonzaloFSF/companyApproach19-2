package equipo6.otros;

import java.util.List;

import equipo5.model.Cadena;
import equipo6.model.Bloque;
import equipo6.model.DatosContainer;
import equipo7.model.OrdenTrazabilidad;


//Esta es la clase a la que van a llamar el resto de grupos para hacer sus
//gestiones con respecto a añadir cosas al blockchain
public class BlockchainServices{
	public BlockchainServices() {}
	public boolean checkConsistencia(int codLote) {
		try {
			return equipo5.dao.metodosCompany.extraerCadena(codLote).checkConsistencia();
		}catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

    //Aun tienen que definir los otros grupos cual va a ser la clase
    //que nos van a pasar de con la informacion del traspaso. No se si sera
    //este nombre u otro, pero en cualquier caso podemos tratarlo como si fuera lo mismo.
    //Recibe un objeto contenedor con la informacion del traspaso, y tenemos que encapsularlo
    //en DatosContainer, y guardarlo en la cadena con los metodos de la clase Cadena correspondientes
    //TODO alejandro
    public void guardarOrden(OrdenTrazabilidad traspaso){
        //encapsularlo, sin tener los datos de la clase Traspaso no podemos encapsularlo
        DatosContainer dc = traspaso;
        int codLote = traspaso.getId();
        try {
        	Cadena cadena = equipo5.dao.metodosCompany.extraerCadena(codLote);
        	cadena.incorporarBloque(dc, 0); //Cambiar cuando asignemos cada entero a cada tipo de bloque
        }catch (Exception ex) {
        	ex.printStackTrace();
        }

          
    }


    //Funcion que devuelve la informacion de un traspaso. 
    //Aun por concretar como se va a identificar al traspaso, si por un id_traspaso o como. 
    //en cualquier caso, nos pasan siempre el codLote, nosotros cogemos la cadena asociada
    //a ese lote y a partir de ahi vamos tirando. 

    //Obtiene bloque adecuado utilizando los metodos de clase Cadena, y una vez lo tiene 
    //extrae la informacion del traspaso y la devuelve.
    //TODO anton
    public OrdenTrazabilidad getTraspaso(int codLote){
//    	Cadena cadena = BBDD.getCadena(codLote);
//    	List<Bloque> bloques = cadena.getCadena();
//    	int i =0;
//    	while(i<bloques.size()){
//    		if(bloques.get(i).getTipoBloque() == 0){
//    			return (Traspaso) bloques.get(i).getDatos();
//    		}
//    		i++;
//    	}
//    	return null;
    	
    	
    	try {
			Cadena cadena = equipo5.dao.metodosCompany.extraerCadena(codLote);
			List<Bloque> bloques = cadena.getBloque(0);
			if (!bloques.isEmpty()) {
				return (OrdenTrazabilidad) bloques.get(bloques.size() - 1).getDatos();
			}
    	}catch(Exception ex) {
    		ex.printStackTrace();
    		return null;
    	}
      return null;    	
    } 
}
