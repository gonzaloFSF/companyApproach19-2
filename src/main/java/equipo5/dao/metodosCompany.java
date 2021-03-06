package equipo5.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import equipo4.model.AlmacenLotes;
import equipo4.model.AlmacenMMPP;
import equipo4.model.Lote;
import equipo5.model.Agricultor;
import equipo5.model.Cadena;
import equipo5.model.Cooperativa;
import equipo5.model.Fabrica;
import equipo5.model.Retailer;
import equipo6.model.Bloque;
import equipo6.model.Actor;
import equipo7.model.OrdenTrazabilidad;
import equipo7.model.Productos;
import equipo7.model.Transportista;
import equipo8.model.Sensor;

public class metodosCompany {

    private static Connection conn;

    private static String JDBC_DATABASE_URL="jdbc:postgresql://ec2-54-197-232-203.compute-1.amazonaws.com:5432/da8thb0c81jj6n?user=voamftsogizhrl&password=b92c40a06c23bf20ef80f4270ebf62bd464e9432d65e38458e047b7597bd5446&sslmode=require";

    static boolean primerusuario= true;


    public static void conectar(){
        try {
            conn= DriverManager.getConnection(JDBC_DATABASE_URL);
            //System.out.println("Conectado");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void crearBD()   throws Exception {
        PreparedStatement pst = conn.prepareStatement("DROP SCHEMA company CASCADE;");
        pst.executeUpdate();
        PreparedStatement pst100 = conn.prepareStatement("CREATE SCHEMA company;");
        pst100.executeUpdate();

        //creacion de las tablas
        PreparedStatement pst1 = conn.prepareStatement(
                "CREATE TABLE company.transportista (" +
                        "nombre_empresa VARCHAR(45) NOT NULL ," +
                        "empresa_transporte VARCHAR(45) NOT NULL, " +
                        "fecha_recogida VARCHAR(45) NOT NULL, " +
                        "fecha_entrega VARCHAR(45) NOT NULL, " +
                        "aceptaPedido BOOL NOT NULL, " +  
                        "firmadoRecogida BOOL NOT NULL, " + 
                        "firmadoEntrega BOOL NOT NULL, " + 
                        "PRIMARY KEY (nombre_empresa)) ;"
        );
        pst1.executeUpdate();

        PreparedStatement pst2 = conn.prepareStatement(
                "CREATE TABLE company.almacenMateriasPrimas (" +
                        "idAlmacenMateriasPrimas INT NOT NULL, " +
                        "capacidad FLOAT NULL, " +
                        "maltaPilsner FLOAT NULL, " +
                        "maltaCaramelo FLOAT NULL, " +
                        "maltaBasePalida FLOAT NULL, " +
                        "maltaMunich FLOAT NULL, " +
                        "maltaNegra FLOAT NULL, " +
                        "maltaCrystal FLOAT NULL, " +
                        "maltaChocolate FLOAT NULL, " +
                        "cebadaTostada FLOAT NULL, " +
                        "lupuloPerle FLOAT NULL, " +
                        "lupuloTettnanger FLOAT NULL, " +
                        "lupuloCentennial FLOAT NULL, " +
                        "levaduraAle FLOAT NULL, " +
                        "levaduraLager FLOAT NULL, " +
                        "maxCapacidad FLOAT NULL, " +
                        "maxMaltaPilsner FLOAT NULL, " +
                        "maxMaltaCaramelo FLOAT NULL, " +
                        "maxMaltaBasePalida FLOAT NULL, " +
                        "maxMaltaMunich FLOAT NULL, " +
                        "maxMaltaNegra FLOAT NULL, " +
                        "maxMaltaCrystal FLOAT NULL, " +
                        "maxMaltaChocolate FLOAT NULL, " +
                        "maxCebadaTostada FLOAT NULL, " +
                        "maxLupuloPerle FLOAT NULL, " +
                        "maxLupuloTettnanger FLOAT NULL, " +
                        "maxLupuloCentennial FLOAT NULL, " +
                        "maxLevaduraAle FLOAT NULL, " +
                        "maxLevaduraLager FLOAT NULL, " +
                        "PRIMARY KEY (idalmacenMateriasPrimas));"
        );
        pst2.executeUpdate();

        PreparedStatement pst3 = conn.prepareStatement(
                "CREATE TABLE company.lote (" +
                		"idBd INT GENERATED BY DEFAULT AS IDENTITY," +
                        "idLote INT NOT NULL ," +
                        "tipo VARCHAR(45) NOT NULL, " +
                        "pedido VARCHAR(45) NOT NULL ," +
                        "fecha_inicio TIMESTAMP NOT NULL ," +
                        "fecha_final TIMESTAMP NOT NULL, " +
                        "PRIMARY KEY (idBd));"
        );
        pst3.executeUpdate();

        PreparedStatement pst4 = conn.prepareStatement(
                "CREATE TABLE company.almacenLotes (" +
                		"idBd INT GENERATED BY DEFAULT AS IDENTITY," +
                        "idAlmacenLotes INT NOT NULL ," +
                		"idLote INT NOT NULL," +
                        "maxCapacidad INT NOT NULL ," +
                        "PRIMARY KEY (idBd));"
        );
        pst4.executeUpdate();

        PreparedStatement pst5 = conn.prepareStatement(
                "CREATE TABLE company.fabrica (" +
                        "fabrica_cif VARCHAR(45) NOT NULL ," +
                        "direccion VARCHAR(250) NOT NULL, " +
                        "idAlmacenMaterias INT NOT NULL, " +
                        "idAlmacenLotes INT NOT NULL, " +
                        "PRIMARY KEY (fabrica_cif), " +
                        "CONSTRAINT fk_fabrica_1 " +
                        "  FOREIGN KEY (idAlmacenMaterias) " +
                        "  REFERENCES company.almacenMateriasPrimas(idAlmacenMateriasPrimas) " +
                        "  ON DELETE NO ACTION " +
                        "  ON UPDATE NO ACTION, " +
                        "CONSTRAINT fk_fabrica_2 " +
                        "  FOREIGN KEY (idAlmacenMaterias) " +
                        "  REFERENCES company.almacenLotes(idBd) " +
                        "  ON DELETE NO ACTION " +
                        "  ON UPDATE NO ACTION " +
                        ");"
        );
        pst5.executeUpdate();

        PreparedStatement pst7 = conn.prepareStatement(
                "CREATE TABLE company.agricultor (" +
                        "cif VARCHAR(45) NOT NULL ," +
                        "nombre VARCHAR(45) NOT NULL, " +
                        "direccion VARCHAR(250) NOT NULL ," +
                        "cifCooperativa INT NOT NULL, " +
                        "PRIMARY KEY (cif));"
        );
        pst7.executeUpdate();

        PreparedStatement pst8 = conn.prepareStatement(
                "CREATE TABLE company.cooperativa (" +
                        "cif VARCHAR(45) NOT NULL ," +
                        "nombre VARCHAR(45) NOT NULL, " +
                        "direccion VARCHAR(250) NOT NULL ," +
                        "PRIMARY KEY (cif));"
        );
        pst8.executeUpdate();

        PreparedStatement pst9 = conn.prepareStatement(
                "CREATE TABLE company.retailer (" +
                        "cif VARCHAR(45) NOT NULL ," +
                        "nombre VARCHAR(45) NOT NULL, " +
                        "direccion VARCHAR(250) NOT NULL ," +
                        "PRIMARY KEY (cif));"
        );
        pst9.executeUpdate();

        PreparedStatement pst10 = conn.prepareStatement(
                "CREATE TABLE company.actor (" +
                		"idBd INT GENERATED BY DEFAULT AS IDENTITY," +
                        "nombreUsuario VARCHAR(45) NOT NULL ," +
                        "passwdPlana VARCHAR(45)  NOT NULL, " +
                        "passwdSalt VARCHAR(45) NOT NULL ," +
                        "email VARCHAR(250)  NOT NULL, " +
                        "usuarioPrevio INT, " +
                        "tipoActor INT  NOT NULL, " +
                        "PRIMARY KEY (nombreUsuario))"
        );
        pst10.executeUpdate();
        
        PreparedStatement pst11 = conn.prepareStatement(
                "CREATE TABLE company.cadena ( " +
                        "codLote INT NOT NULL, " +
                        "hashUltimoBloque VARCHAR(45) NULL, " +
                        "numBloques INT NOT NULL, " +
                        "PRIMARY KEY (codLote))"
        );
        pst11.executeUpdate();
 
                
                PreparedStatement pst12 = conn.prepareStatement(
                        "CREATE TABLE company.productos ( " +
                                "id INT NOT NULL, " +
                                "malta_palida INT NOT NULL, " +
                                "matla_munich INT NOT NULL, " +
                                "malta_negra INT NOT NULL, " +
                                "malta_crystal INT NOT NULL, " +
                                "malta_chocolate INT NOT NULL, " +
                                "malta_caramelo INT NOT NULL, " +
                                "cebada INT NOT NULL, " +
                                "cebada_tostada INT NOT NULL, " +
                                "lupulo_centenial INT NOT NULL, " +
                                "cajas_stout INT NOT NULL, " +
                                "cajas_bisner INT NOT NULL, " +
                                "PRIMARY KEY (id))"
                );
                pst12.executeUpdate();
                
                PreparedStatement pst13 = conn.prepareStatement(
                        "CREATE TABLE company.ordenTrazabilidad ( " +
                                "id INT NOT NULL, " +
                                "mensaje VARCHAR(45) NOT NULL, " +
                                "id_emisor VARCHAR(45) NOT NULL, " +
                                "id_receptor VARCHAR(45) NOT NULL, " +
                                "id_transportista VARCHAR(45) NOT NULL, " +
                                "id_productos INT NOT NULL, " +
                                "PRIMARY KEY (id))"
                );
                pst13.executeUpdate();
                
                PreparedStatement pst14 = conn.prepareStatement(
	              " CREATE TABLE company.sensor (" +
	               "id INT NOT NULL, " +
	                "anio VARCHAR(45), " +
	               "mes VARCHAR(45), " +
	               "dia VARCHAR(45), " +
	               "hora VARCHAR(45), " +
	               "min VARCHAR(45), " +
	               "sec VARCHAR(45), " +
	               "temperatura VARCHAR(45), " +
	               "PRIMARY KEY (id));"
               );
                pst14.executeUpdate();
                
                PreparedStatement pst15 = conn.prepareStatement(
                        " CREATE TABLE company.bloque (" +
                                "id VARCHAR(45) NOT NULL, " +
                                "hashPrevio VARCHAR(45), " +
                                "tipoBloque INT, " +
                                "numBloque INT, " +
                                "codLote INT, " +
                                "datosContainer INT, " +
                                "timeStamp FLOAT, " +
                                "PRIMARY KEY (id));"
                );
                pst15.executeUpdate();
                
   
        
        System.out.println("�Base de datos Creada!");
        
    }
    
    public static OrdenTrazabilidad getOrdenTrazabalidad(int id) throws SQLException, ClassNotFoundException {
    	conectar();
    	 String query = "SELECT * FROM company.ordenTrazabilidad WHERE id = " + id;
         Statement pst = conn.createStatement();
         ResultSet rs = pst.executeQuery(query);
         while(rs.next()) {
         //TODO 
           /*
            * Aclarar como es el objeto OrdenTrazabilidad, porque sus parámetros 3 y 4 no encajan bien
            * Ellos devuelven un ENUM, no un objeto de tipo ACTOR
            */
           
         OrdenTrazabilidad buscado = null; /*new OrdenTrazabilidad(id, rs.getString(2), 
             getActor(rs.getString(3)), getActor(rs.getString(4)),
        		 getTransportista(rs.getString(5)), getProductos(rs.getInt(6)));
        		 */
         pst.close();
         rs.close();
         conn.close();
         return buscado;
         }
         return null;	
    }
    public static void insertarOrdenTrazabilidad(OrdenTrazabilidad orden) throws SQLException, ClassNotFoundException {
        conectar();
        String query = "INSERT INTO company.ordenTrazabilidad (id, mensaje, id_emisor, id_receptor, id_transportista, id_productos)"
        		+ " VALUES (?, ?, ?, ?, ?, ?);";
        PreparedStatement pst = (PreparedStatement) conn.prepareStatement(query);
       pst.setInt(1, orden.getId());
       pst.setString(2, orden.getMensaje());
       //TODO 
       /*
        * Aclarar como es el objeto OrdenTrazabilidad, porque sus parámetros 3 y 4 no encajan bien
        * Ellos devuelven un ENUM, no un objeto de tipo ACTOR
        */
       //pst.setString(3, orden.getActorOrigen().getNombreUsuario());
       //pst.setString(4, orden.getDestinatario().getNombreUsuario());
       pst.setString(5, orden.getTransportista().getNombre());
       pst.setInt(6, orden.getProductos().getId());
        pst.executeUpdate();
        pst.close();
        conn.close();
    }
    
    public static Productos getProductos(int id) throws SQLException {
    	conectar();
    	 String query = "SELECT * FROM company.productos WHERE id =" + id;
         Statement pst = conn.createStatement();
         ResultSet rs = pst.executeQuery(query);
         while(rs.next()) {
         Productos buscado = new Productos(id, rs.getInt(2),  rs.getInt(3), rs.getInt(4),rs.getInt(5),
        		 rs.getInt(6),rs.getInt(7),rs.getInt(8), rs.getInt(9), rs.getInt(10), rs.getInt(11),rs.getInt(12));
         pst.close();
         rs.close();
         conn.close();
         return buscado;
         }
         return null;	
    }
    
    public static void insertarProductos(Productos producto) throws SQLException, ClassNotFoundException {
        conectar();
        String query = "INSERT INTO company.productos (id, malta_palida, matla_munich, malta_negra, malta_crystal, "
        		+ "malta_chocolate , malta_caramelo, cebada, cebada_tostada, lupulo_centenial, cajas_stout ,cajas_bisner)"
        		+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement pst = (PreparedStatement) conn.prepareStatement(query);
       pst.setInt(1, producto.getId());
       pst.setInt(2, producto.getCant_malta_palida());
       pst.setInt(3, producto.getCant_malta_munich());
       pst.setInt(4, producto.getCant_malta_negra());
       pst.setInt(5, producto.getCant_malta_crystal());
       pst.setInt(6, producto.getCant_malta_chocolate());
       pst.setInt(7, producto.getCant_malta_caramelo());
       pst.setInt(8, producto.getCant_cebada());
       pst.setInt(9, producto.getCant_cebada_tostada());
       pst.setInt(10, producto.getCant_lupulo_centenial());
       pst.setInt(11, producto.getCant_cajas_stout());
       pst.setInt(12, producto.getCant_cajas_bisner());
        pst.executeUpdate();
        pst.close();
        conn.close();
    }
     
    
    public static Cadena extraerCadena(int codLote) throws SQLException {
    	conectar();
        String query = "SELECT * FROM company.cadena WHERE cadena.codLote = ? ";
        PreparedStatement pst = (PreparedStatement) conn.prepareStatement(query);
        pst.setInt(1, codLote);
        ResultSet rs = pst.executeQuery(query);
        rs.next();
        Cadena buscado = new Cadena(codLote, rs.getString(2), rs.getInt(3));
        pst.close();
        rs.close();
        conn.close();
        return buscado;
    }

    public static void insertarCadena(Cadena cadena) throws SQLException {
    	conectar();
        int codLote = cadena.getCodLote();
        if(extraerCadena(codLote)!= null) {
            conectar();
            String query = "UPDATE company.cadena SET hashUltimoBloque = ? , numBloques = ?  WHERE codLote= ?";
            PreparedStatement pst = (PreparedStatement) conn.prepareStatement(query);
            pst.setString(1, cadena.getHashUltimoBloque());
            pst.setInt(2, cadena.getNumBloques());
            pst.setInt(3, codLote);
            pst.executeUpdate();
            pst.close();
            conn.close();
        }else {
            conectar();
            String query = "INSERT INTO company.cadena (codLote, hashUltimoBloque, numBloques) VALUES (?, ?, ?);";
            PreparedStatement pst = (PreparedStatement) conn.prepareStatement(query);
            pst.setInt(1, codLote);
            pst.setString(2, cadena.getHashUltimoBloque());
            pst.setInt(3, cadena.getNumBloques());
            pst.executeUpdate();
            pst.close();
            conn.close();
        }
    }

    public static void insertarRetailer(Retailer retailer) throws SQLException, ClassNotFoundException {
        conectar();
        String query = "INSERT INTO company.retailer (cif, nombre, direccion) VALUES (?, ?, ?);";
        PreparedStatement pst = (PreparedStatement) conn.prepareStatement(query);
        pst.setString(1, retailer.getCif());
        pst.setString(2, retailer.getNombre());
        pst.setString(3, retailer.getDireccion());
        pst.executeUpdate();
        pst.close();
        conn.close();
    }

    public static void insertarFabrica(Fabrica fabrica) throws SQLException, ClassNotFoundException {
        conectar();
        String query = "INSERT INTO company.fabrica (fabrica_cif, direccion, idAlmacenMaterias, idAlmacenLotes) VALUES (?, ?, ?, ?);";
        PreparedStatement pst = (PreparedStatement) conn.prepareStatement(query);
        pst.setString(1, fabrica.getCIF());
        pst.setString(2, fabrica.getDireccion());
        pst.setInt(3, fabrica.getIdAlmacenMaterias());
        pst.setInt(4, fabrica.getIdAlmacenLotes());
        pst.executeUpdate();
        pst.close();
        conn.close();
    }

    public static void insertarAlMMPP(AlmacenMMPP almacen) throws SQLException, ClassNotFoundException {
        conectar();
        String query = "INSERT INTO company.almacenMateriasPrimas (idAlmacenMateriasPrimas, capacidad, maltaPilsner, maltaCaramelo, "
                + "maltaBasePalida, maltaMunich, maltaNegra , maltaCrystal, maltaChocolate ,cebadaTostada , lupuloPerle, lupuloTettnanger,  lupuloCentennial,  "
                + "levaduraAle,  levaduraLager,  maxcapacidad, maxmaltaPilsner, maxmaltaCaramelo, maxmaltaBasePalida, "
                + "maxmaltaMunich, maxmaltaNegra, maxmaltaCrystal, maxmaltaChocolate, maxcebadaTostada, maxlupuloPerle, maxlupuloTettnanger,maxlupuloCentennial,"
                + " maxlevaduraAle, maxlevaduraLager ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        PreparedStatement pst = (PreparedStatement) conn.prepareStatement(query);

        pst.setInt(1, almacen.getId());
        pst.setInt(2, almacen.getCapacidad());
        pst.setDouble(3, almacen.getMaltaPilsner());
        pst.setDouble(4, almacen.getMaltaCaramelo());
        pst.setDouble(5, almacen.getMaltaBasePalida());
        pst.setDouble(6, almacen.getMaltaMunich());
        pst.setDouble(7, almacen.getMaltaNegra());
        pst.setDouble(8, almacen.getMaltaCrystal());
        pst.setDouble(9, almacen.getMaltaChocolate());
        pst.setDouble(10, almacen.getCebadaTostada());
        pst.setDouble(11, almacen.getLupuloPerle());
        pst.setDouble(12, almacen.getLupuloTettnanger());
        pst.setDouble(13, almacen.getLupuloCentennial());
        pst.setDouble(14, almacen.getLevaduraAle());
        pst.setDouble(15, almacen.getLevaduraLager());

        pst.setInt(16, almacen.getMaxcapacidad());
        pst.setDouble(17, almacen.getMaxmaltaPilsner());
        pst.setDouble(18, almacen.getMaxmaltaCaramelo());
        pst.setDouble(19, almacen.getMaxmaltaBasePalida());
        pst.setDouble(20, almacen.getMaxmaltaMunich());
        pst.setDouble(21, almacen.getMaxmaltaNegra());
        pst.setDouble(22, almacen.getMaxmaltaCrystal());
        pst.setDouble(23, almacen.getMaxmaltaChocolate());
        pst.setDouble(24, almacen.getMaxcebadaTostada());
        pst.setDouble(25, almacen.getMaxlupuloPerle());
        pst.setDouble(26, almacen.getMaxlupuloTettnanger());
        pst.setDouble(27, almacen.getMaxlupuloCentennial());
        pst.setDouble(28, almacen.getMaxlevaduraAle());
        pst.setDouble(29, almacen.getMaxlevaduraLager());

        pst.executeUpdate();
        pst.close();
        conn.close();
    }

    public static Retailer getRetailer(String CIF) throws SQLException {
    	conectar();
        String query = "SELECT * FROM company.retailer WHERE cif = '" + CIF + "'";
        Statement pst = conn.createStatement();
        ResultSet rs = pst.executeQuery(query);
        while(rs.next()) {
        Retailer buscado = new Retailer(rs.getString(1), rs.getString(2), rs.getString(3));
        pst.close();
        rs.close();
        conn.close();
        return buscado;
        }
        return null;
    }

    public static Fabrica getFabrica(String CIF) throws SQLException {
    	conectar();
        String query = "SELECT * FROM company.fabrica WHERE fabrica.fabrica_cif = '" + CIF + "'";
        Statement pst = conn.createStatement();
        ResultSet rs = pst.executeQuery(query);
        while(rs.next()) {
        Fabrica buscado = new Fabrica(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4));
        pst.close();
        rs.close();
        conn.close();
        return buscado;
        }
        return null;
    }

    public static AlmacenMMPP getAlmacenMMPP(int id) throws SQLException, ClassNotFoundException {
        conectar();
        String query = "SELECT * FROM company.almacenMateriasPrimas WHERE almacenMateriasPrimas.idAlmacenMateriasPrimas = " + id;
        Statement pst = conn.createStatement();
        ResultSet rs = pst.executeQuery(query);
        while (rs.next()) {
            AlmacenMMPP buscado = new AlmacenMMPP(rs.getInt(1), rs.getInt(2), rs.getDouble(3), rs.getDouble(4),
                   rs.getDouble(5), rs.getDouble(6), rs.getDouble(7), rs.getDouble(8), rs.getDouble(9), rs.getDouble(10),
                    rs.getDouble(11), rs.getDouble(12), rs.getDouble(13), rs.getDouble(14), rs.getDouble(15));
            pst.close();
            rs.close();
            conn.close();
            return buscado;
        }
        return null;

    }
    
    public static void insertarLote(Lote lote) throws SQLException {
	    conectar();
	    LinkedList<String> list = lote.getPedidos();
	    
	    for(int i=0; i< list.size(); i++) {
	    
	    String ped = list.get(i);
	    
        String query = "INSERT INTO company.lote (idLote, tipo, pedido, fecha_inicio, fecha_final) VALUES (?, ?, ?, ?, ?);";
        PreparedStatement pst = (PreparedStatement) conn.prepareStatement(query);
        pst.setInt(1, lote.getCode());
        pst.setString(2, lote.getTipo());
        pst.setString(3, ped);
        pst.setDate(4, (Date) lote.getFecha_inicio());
        pst.setDate(5, (Date) lote.getFecha_final());
        pst.executeUpdate();
        pst.close();
        
	    }
	    conn.close();
    }
    
    public static Lote getLote(int idLote) throws SQLException {
    	conectar();
        String query = "SELECT * FROM company.lote WHERE idLote =" + idLote;
        Statement pst = conn.createStatement();
        ResultSet rs = pst.executeQuery(query);
        LinkedList<String> lista = new LinkedList<String>();
        int idB = 0;
        while(rs.next()) {
        String ped = rs.getString(4);
        lista.add(ped);
        idB= rs.getInt(1);
        }
        pst.close();
        rs.close();
        
        String query2 = "SELECT * FROM company.lote WHERE idBd = " + idB;
        Statement pst2 = conn.createStatement();
        ResultSet rs2 = pst2.executeQuery(query);
        while(rs2.next()) {
        Lote buscado = new Lote(idB, rs2.getInt(2), rs2.getString(3), lista, rs2.getDate(5), rs2.getDate(6));
        pst2.close();
        rs2.close();
        conn.close();
        return buscado;
        }
        return null;
    }

   public static void insertarAlmacenLotes(AlmacenLotes almacen) throws SQLException {
	    conectar();
	    LinkedList<Lote> list = almacen.getLista();
	    
	    for(int i=0; i< list.size(); i++) {
	    Lote lot = list.get(i);
	    int lotId=lot.getCode();
        String query = "INSERT INTO company.almacenLotes (idAlmacenLotes, idLote, maxCapacidad) VALUES (?, ?, ?);";
        PreparedStatement pst = (PreparedStatement) conn.prepareStatement(query);
        pst.setInt(1, almacen.getId());
        pst.setInt(2, lotId);
        pst.setInt(3, 7);
        pst.executeUpdate();
        pst.close();
	    }
	    conn.close();
    }
   
   public static AlmacenLotes getAlmacenLotes(int idAlm) throws SQLException {
	   conectar();
       String query = "SELECT * FROM company.almacenLotes WHERE idAlmacenLotes =" + idAlm;
       Statement pst = conn.createStatement();
       ResultSet rs = pst.executeQuery(query);
       LinkedList<Lote> lista = new LinkedList<Lote>();
       int idB = 0;
       
       while(rs.next()) {
       int idLote = rs.getInt(3);
       Lote lote = getLote(idLote);
       lista.add(lote);
       idB=rs.getInt(1);
       }
       pst.close();
       rs.close();
       conectar();
       String query2 = "SELECT * FROM company.almacenLotes WHERE idBd = " + idB;
       Statement pst2 = conn.createStatement();
       ResultSet rs2 = pst2.executeQuery(query);
       while(rs2.next()) {
       AlmacenLotes buscado = new AlmacenLotes(idB, rs2.getInt(2), lista);
       pst2.close();
       rs2.close();
       conn.close();
       return buscado;
       }
       return null;
   }
   
   public static void insercionAgricultor (Agricultor agricultor) throws SQLException, ClassNotFoundException{
		conectar();
	    String query = "INSERT INTO company.agricultor (cif, nombre, direccion, cifCooperativa) VALUES (?, ?, ?, ?);"; 
	    PreparedStatement pst = (PreparedStatement) conn.prepareStatement(query);
	    pst.setString(1, agricultor.getCif());
	    pst.setString(2, agricultor.getNombre());
	    pst.setString(3, agricultor.getDireccion());
	    pst.setInt(4, agricultor.getCifCooperativa());
	    pst.executeUpdate();
	    pst.close();
	    conn.close();
   }
 
   public static void insercionCooperativa (Cooperativa cooperativa) throws SQLException, ClassNotFoundException{
   	conectar();
	    String query = "INSERT INTO company.cooperativa (cif, nombre, direccion) VALUES (?, ?, ?);"; 
	    PreparedStatement pst = (PreparedStatement) conn.prepareStatement(query);
	    pst.setString(1, cooperativa.getCif());
	    pst.setString(2, cooperativa.getNombre());
	    pst.setString(3, cooperativa.getDireccion());
	    pst.executeUpdate();
	    pst.close();
	    conn.close();
   }
   
	public static Agricultor getAgricultor(String cif) throws SQLException, ClassNotFoundException{
   	conectar();  
   	String query = "SELECT * FROM company.agricultor WHERE agricultor.cif = '" + cif + "'";
   	Statement pst = conn.createStatement();
   	ResultSet rs = pst.executeQuery(query);
   	while(rs.next()){
   		Agricultor buscado = new Agricultor(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4));
       	pst.close();
       	rs.close();
       	return buscado;
   	}
   	return null;
   }
 
	public static Cooperativa getCooperativa(String cif) throws SQLException, ClassNotFoundException{
    	conectar();
    	String query = "SELECT * FROM company.cooperativa WHERE cooperativa.cif = '" + cif + "'";
    	Statement pst = conn.createStatement();
    	ResultSet rs = pst.executeQuery(query);
    	while(rs.next()){
        	Cooperativa buscado = new Cooperativa(rs.getString(1), rs.getString(2), rs.getString(3));
        	pst.close();
        	rs.close();
        	return buscado;
    	}
    	return null;
    }
	public static void insertarTransportista (Transportista transportista) throws SQLException {
	      conectar();
	      String query = "INSERT INTO company.transportista(nombre_empresa, empresa_transporte, fecha_recogida, fecha_entrega, "
	              + "aceptaPedido, firmadoRecogida, firmadoEntrega) VALUES (?, ?, ?, ?, ?, ?, ?);";
	      PreparedStatement pst = (PreparedStatement) conn.prepareStatement(query);
	      pst.setString(1, transportista.getNombre());
	      pst.setString(2, transportista.getEmpresa());
	      pst.setString(3, transportista.getFecha_recogida());
	      pst.setString(4, transportista.getFecha_entrega());
	      pst.setBoolean(5, transportista.getaceptaPedido());
	      pst.setBoolean(6, transportista.getFirmadoRecogida());
	      pst.setBoolean(7, transportista.getFirmadoEntrega());
	      pst.executeUpdate();
	      pst.close();
	      conn.close();
	   }



	  public static Transportista getTransportista(String nombre_empresa) throws SQLException {
	       conectar();
	       String query = "SELECT * FROM company.transportista WHERE transportista.nombre_empresa = '" + nombre_empresa + "'";
	       Statement pst = conn.createStatement();
	           ResultSet rs = pst.executeQuery(query);
	           while(rs.next()){
	               Transportista buscado = new Transportista(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
	               if(rs.getBoolean(5) == true) {
	                   buscado.aceptarPedido();
	               }
	               else {
	                   buscado.rechazarPedido();
	               }
	               buscado.setFirmadoRecogida(rs.getBoolean(6));
	               buscado.setFirmadoEntrega(rs.getBoolean(7));
	            pst.close();
	            rs.close();
	            conn.close();
	            return buscado;
	           }
	           return null;
	  }
	  public static void insertarSensor (Sensor sensor) throws SQLException{
		  conectar();
		  String query = "INSERT INTO company.sensor(id, anio, mes, dia, hora, min, sec, temperatura) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
		  PreparedStatement pst = (PreparedStatement) conn.prepareStatement(query);
		  pst.setInt(1, sensor.getID());
		  pst.setString(2, sensor.getAnio());
		  pst.setString(3, sensor.getMes());
		  pst.setString(4, sensor.getDia());
		  pst.setString(5, sensor.getHora());
		  pst.setString(6, sensor.getMin());
		  pst.setString(7, sensor.getSec());
		  pst.setString(8, sensor.getTemperatura());
		  pst.executeUpdate();
		  pst.close();
		  conn.close();
		}


	  public static Sensor getSensor (int id) throws SQLException {
	  conectar();
	  String query = "SELECT * FROM company.sensor WHERE sensor.id = " +  id;
	  Statement pst = conn.createStatement();
	  ResultSet rs = pst.executeQuery(query);
	  while(rs.next()){
	     Sensor buscado = new Sensor();
	     buscado.setID(rs.getInt(1));
	     buscado.setAnio(rs.getString(2));
	     buscado.setMes(rs.getString(3));
	     buscado.setDia(rs.getString(4));
	     buscado.setHora(rs.getString(5));
	     buscado.setMin(rs.getString(6));
	     buscado.setSec(rs.getString(7));
	     buscado.setTemperatura(rs.getString(8));
	     pst.close();
	     rs.close();
	     conn.close();
	     return buscado;
	  }
	  return null;
	  }
		
		 public static Actor getActor(String nombreUsuario) throws SQLException, ClassNotFoundException {
			 if(nombreUsuario!= null) {
		            conectar();
		            Actor actor = new Actor();
		            String query = "SELECT * FROM company.actor WHERE actor.nombreUsuario = '" + nombreUsuario + "'";
		            Statement pst = conn.createStatement();
		            ResultSet rs = pst.executeQuery(query);
		            rs.next();
		            Actor buscado = new Actor(rs.getInt(1), rs.getString(2), rs.getString(4), rs.getString(5),
		            		rs.getInt(6));
		            pst.close();
		            rs.close();
		            return buscado; 
		        }
		        else return null;
		    }
		 
		public static void insercionActor(Actor actor) throws SQLException, ClassNotFoundException {
		        conectar();
		        String query = "INSERT INTO company.actor (nombreUsuario, passwdPlana, passwdSalt, email, tipoActor) VALUES (?, ?, ?, ?, ?);";
		        PreparedStatement pst = (PreparedStatement) conn.prepareStatement(query);
		        pst.setString(1, actor.getNombreUsuario());
		        pst.setString(2, actor.getpasswordPlana());
		        pst.setString(3, actor.getpasswordSalt());
		        pst.setString(4, actor.getEmail());
		        pst.setInt(5, actor.getTipoActor());
		        pst.executeUpdate();
		        pst.close();
		    }

		    
		   public static Bloque getBloque(String hashBloquePedido) throws SQLException, ClassNotFoundException {
                /*conectar();
                Bloque devolver =null;
                String query = "SELECT * FROM company.bloque WHERE id = '" + hashBloquePedido + "'" ;
                Statement pst = conn.createStatement();
                ResultSet rs = pst.executeQuery(query);
                while(rs.next()) {
                    int tipoBloque = rs.getInt(3);
                    String hashPrevio = rs.getString(2);
                    int numBloque = rs.getInt(4);
                    int codLote = rs.getInt(5);
                    switch (tipoBloque) {
                        case 0:
                            Bloque buscado = new Bloque(hashPrevio, tipoBloque, numBloque, codLote, getOrdenTrazabalidad(rs.getInt(6)));
                            devolver = buscado;
                            break;
                        case 2:
                            Bloque buscado2 = new Bloque(hashPrevio, tipoBloque, numBloque, codLote, getSensor(rs.getInt(6)));
                            devolver = buscado2;
                            break;
                    }
                }
                pst.close();
                rs.close();
                conn.close();
                return devolver;*/
			   return null;
            }

            public static void insertarBloque(Bloque bloqAinsertar) throws SQLException, ClassNotFoundException {
                /*int data=0;
                switch (bloqAinsertar.getTipoBloque()) {
                    case 0:
                        OrdenTrazabilidad aInsertar = (OrdenTrazabilidad) bloqAinsertar.getDatos();
                        data = aInsertar.getId();
                        insertarOrdenTrazabilidad(aInsertar);
                        break;
                    case 2:
                        break;
                }
                conectar();
                String query = "INSERT INTO company.bloque (id, hashPrevio, tipoBloque, numBloque, codLote, datosContainer, timeStamp) VALUES (?, ?, ?, ?, ?, ?, ?);";
                PreparedStatement pst = (PreparedStatement) conn.prepareStatement(query);
                pst.setString(1, bloqAinsertar.getHashCode());
                pst.setString(2, bloqAinsertar.getHashPrevio());
                pst.setInt(3,bloqAinsertar.getTipoBloque());
                pst.setInt(4,bloqAinsertar.getNumBloque());
                pst.setInt(5,bloqAinsertar.getCodLote());
                pst.setInt(6, data);
                pst.setFloat(7, bloqAinsertar.getTimeStamp());
                pst.executeUpdate();
                pst.close();*/
            	

            }



       
}