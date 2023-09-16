
package dominio.bbdd;

import dominio.dao.acceso.UsuarioAccesoImpl;
import dominio.dao.acceso.ArticuloAcceso;
import dominio.dao.acceso.WikiAccesoImpl;
import dominio.dao.acceso.WikiAcceso;
import dominio.dao.acceso.ArticuloAccesoImpl;
import dominio.dao.acceso.UsuarioAcceso;
import dominio.articulo.*;
import dominio.usuario.*;
import dominio.wiki.Wiki;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


/**
 * This class works by initializing the database that contains nine tables. This is an embedded database. 
 * Usuario: contains user fields.
 * Acceso: works with Usuario. Contains the user login.
 * Articulo: contains article fields.
 * Contenido: works with Articulo. 
 * UsuarioArticulo: work with Usuario and Articulo. Contains the users who work with the article. 
 * ColaboradorEstado: contains the user (Colaborador) record who modified the article at some point. 
 * UsuarioWiki: works with Usuario and Wiki. Contains the users who work with the wiki. 
 * Wiki: contains the wiki fields. 
 * WikiArticulo: contains the articles that belong to the Wiki. 
 * Contains the SQL statements to create the embedded database
 * @author Toni
 */
public class BBDD {

    private static final String Tabla_Usuario = "CREATE TABLE Usuario\n"
            + "(\n"
            + "	ID_Usuario INTEGER NOT NULL PRIMARY KEY,\n"
            + "	Nombre TEXT NOT NULL COLLATE NOCASE,\n"
            + "	Apellido TEXT NOT NULL COLLATE NOCASE,\n"
            + "	ApellidoDos TEXT NULL COLLATE NOCASE,\n"
            + "	Direccion TEXT NULL COLLATE NOCASE,\n"
            + "	Telefono TEXT NULL COLLATE NOCASE,\n"
            + "	Tipo TEXT NOT NULL COLLATE NOCASE,\n"
            + "	Solicitud TEXT NULL COLLATE NOCASE\n"
            + ")\n"
            + ";\n";
    private static final String Tabla_Acceso = "CREATE TABLE Acceso\n"
            + "(\n"
            + "	ID_Usuario INTEGER NULL,\n"
            + "	NombreUsuario TEXT NOT NULL COLLATE NOCASE,\n"
            + "	Password TEXT NOT NULL COLLATE NOCASE,\n"
            + "	CONSTRAINT PK_Acceso PRIMARY KEY (ID_Usuario,NombreUsuario),\n"
            + "	CONSTRAINT FK_Acceso_Usuario FOREIGN KEY (ID_Usuario) REFERENCES Usuario (ID_Usuario) ON DELETE CASCADE ON UPDATE CASCADE\n"
            + ")\n"
            + ";\n";
    private static final String Tabla_Articulo = "CREATE TABLE Articulo\n"
            + "(\n"
            + "	ID_Articulo INTEGER NOT NULL PRIMARY KEY,\n"
            + "	Titulo TEXT NULL COLLATE NOCASE,\n"
            + "	Descripcion TEXT NULL COLLATE NOCASE,\n"
            + "	Borrador INTEGER NULL,\n"
            + "	Archivo TEXT NULL COLLATE NOCASE,\n"
            + "	CONSTRAINT FK_Articulo_Contenido FOREIGN KEY (ID_Articulo) REFERENCES Contenido (ID_Articulo) ON DELETE No Action ON UPDATE CASCADE,\n"
            + "	CONSTRAINT FK_Articulo_UsuarioArticulo FOREIGN KEY (ID_Articulo) REFERENCES UsuarioArticulo (ID_Articulo) ON DELETE No Action ON UPDATE No Action\n"
            + ")\n"
            + ";\n";

    private static final String Tabla_Contenido = "CREATE TABLE Contenido\n"
            + "(\n"
            + "	ID_Articulo INTEGER NOT NULL,\n"
            + " ID_Contenido INTEGER NOT NULL, \n"
            + "	Subtitulo TEXT NOT NULL COLLATE NOCASE,\n"
            + "	Frases TEXT NULL COLLATE NOCASE,\n"
            + "	CONSTRAINT PK_Contenido PRIMARY KEY (ID_Articulo, ID_Contenido),\n"
            + "	CONSTRAINT FK_Contenido_Articulo FOREIGN KEY (ID_Articulo) REFERENCES Articulo (ID_Articulo) ON DELETE CASCADE ON UPDATE CASCADE\n"
            + ")\n"
            + ";\n";
    private static final String Tabla_UsuarioArticulo = "CREATE TABLE UsuarioArticulo\n"
            + "(\n"
            + "	ID_Articulo INTEGER NOT NULL,\n"
            + "	ID_Usuario INTEGER NOT NULL,\n"
            + "	CONSTRAINT PK_UsuarioArticulo PRIMARY KEY (ID_Articulo,ID_Usuario),\n"
            + "	CONSTRAINT FK_UsuarioArticulo_Articulo FOREIGN KEY (ID_Articulo) REFERENCES Articulo (ID_Articulo) ON DELETE CASCADE ON UPDATE CASCADE,\n"
            + "	CONSTRAINT FK_UsuarioArticulo_Usuario FOREIGN KEY (ID_Usuario) REFERENCES Usuario (ID_Usuario) ON DELETE CASCADE ON UPDATE CASCADE\n"
            + ")\n"
            + ";\n";
    private static final String Tabla_ColaboradorEstado = "CREATE TABLE ColaboradorEstado\n"
            + "(\n"            
            + "	ID_Articulo INTEGER NOT NULL,\n"
            + "	ID_Usuario INTEGER NOT NULL,\n"
            + " Estado TEXT NULL COLLATE NOCASE,\n"
            + "	CONSTRAINT PK_ColaboradorEstado PRIMARY KEY (ID_Articulo,ID_Usuario),\n"
            + "	CONSTRAINT FK_ColaboradorEstado_Articulo FOREIGN KEY (ID_Articulo) REFERENCES Articulo (ID_Articulo) ON DELETE CASCADE ON UPDATE CASCADE,\n"
            + "	CONSTRAINT FK_ColaboradorEstado_Usuario FOREIGN KEY (ID_Usuario) REFERENCES Usuario (ID_Usuario) ON DELETE CASCADE ON UPDATE CASCADE\n"
            + ")\n"
            + ";\n";
    private static final String Tabla_UsuarioWiki = "CREATE TABLE UsuarioWiki\n"
            + "(\n"
            + "	ID_Wiki INTEGER NOT NULL,\n"
            + "	ID_Usuario INTEGER NOT NULL,\n"
            + "	CONSTRAINT PK_UsuarioWiki PRIMARY KEY (ID_Usuario,ID_Wiki),\n"
            + "	CONSTRAINT FK_UsuarioWiki_Usuario FOREIGN KEY (ID_Usuario) REFERENCES Usuario (ID_Usuario) ON DELETE CASCADE ON UPDATE CASCADE,\n"
            + "	CONSTRAINT FK_UsuarioWiki_Wiki FOREIGN KEY (ID_Wiki) REFERENCES Wiki (ID_Wiki) ON DELETE CASCADE ON UPDATE CASCADE\n"
            + ")\n"
            + ";\n";
    private static final String Tabla_Wiki = "CREATE TABLE Wiki\n"
            + "(\n"
            + "	ID_Wiki INTEGER NOT NULL PRIMARY KEY,\n"
            + "	Titulo TEXT NULL COLLATE NOCASE,\n"
            + "	Tematica TEXT NULL COLLATE NOCASE\n"
            + ")\n"
            + ";\n";
    private static final String Tabla_WikiArticulo = "CREATE TABLE WikiArticulo\n"
            + "(\n"
            + "	ID_Wiki INTEGER NOT NULL,\n"
            + "	ID_Articulo INTEGER NOT NULL,\n"
            + "	CONSTRAINT PK_WikiArticulo PRIMARY KEY (ID_Wiki,ID_Articulo),\n"
            + "	CONSTRAINT FK_WikiArticulo_Articulo FOREIGN KEY (ID_Articulo) REFERENCES Articulo (ID_Articulo) ON DELETE CASCADE ON UPDATE CASCADE,\n"
            + "	CONSTRAINT FK_WikiArticulo_Wiki FOREIGN KEY (ID_Wiki) REFERENCES Wiki (ID_Wiki) ON DELETE CASCADE ON UPDATE CASCADE\n"
            + ")\n"
            + ";\n";
    private static final String Index_AU = "CREATE INDEX IXFK_Acceso_Usuario\n"
            + " ON Acceso (ID_Usuario ASC)\n"
            + ";\n";
    private static final String Index_AC = "CREATE INDEX IXFK_Articulo_Contenido\n"
            + " ON Articulo (ID_Articulo ASC)\n"
            + ";\n";
    private static final String Index_UA = "CREATE INDEX IXFK_Articulo_UsuarioArticulo\n"
            + " ON Articulo (ID_Articulo ASC)\n"
            + ";\n";
    private static final String Index_CA = "CREATE INDEX IXFK_Contenido_Articulo\n"
            + " ON Contenido (ID_Articulo ASC)\n"
            + ";\n";
    private static final String Index_UAA = "CREATE INDEX IXFK_UsuarioArticulo_Articulo\n"
            + " ON UsuarioArticulo (ID_Articulo ASC)\n"
            + ";\n";
    private static final String Index_UAU = "CREATE INDEX IXFK_UsuarioArticulo_Usuario\n"
            + " ON UsuarioArticulo (ID_Usuario ASC)\n"
            + ";\n";
    private static final String Index_UWU = "CREATE INDEX IXFK_UsuarioWiki_Usuario\n"
            + " ON UsuarioWiki (ID_Usuario ASC)\n"
            + ";\n";
    private static final String Index_UWW = "CREATE INDEX IXFK_UsuarioWiki_Wiki\n"
            + " ON UsuarioWiki (ID_Wiki ASC)\n"
            + ";\n";
    private static final String Index_WAA = "CREATE INDEX IXFK_WikiArticulo_Articulo\n"
            + " ON WikiArticulo (ID_Articulo ASC)\n"
            + ";\n";
    private static final String Index_WAW = "CREATE INDEX IXFK_WikiArticulo_Wiki\n"
            + " ON WikiArticulo (ID_Wiki ASC)\n"
            + ";";
    private static final String Index_CEU = "CREATE INDEX IXFK_ColaboradorEstado_Usuario\n"
            + " ON ColaboradorEstado (ID_Usuario ASC)\n"
            + ";\n";
    private static final String Index_CEA = "CREATE INDEX IXFK_ColaboradorEstado_Articulo\n"
            + " ON ColaboradorEstado (ID_Articulo ASC)\n"
            + ";\n";
    
    
    /**
     * Constructor that initializes the database with its tables
     * @throws ClassNotFoundException 
     */
    public BBDD() throws ClassNotFoundException {
        Statement stmt = null;
        try {
            Connection conn = Conexion.getConnexion();
            stmt = conn.createStatement();
            stmt.execute(Tabla_Usuario);
            stmt.close();
            stmt = conn.createStatement();
            stmt.execute(Tabla_Acceso);
            stmt.close();
            stmt = conn.createStatement();
            stmt.execute(Tabla_Articulo);
            stmt.close();
            stmt = conn.createStatement();
            stmt.execute(Tabla_Contenido);
            stmt.close();
            stmt = conn.createStatement();
            stmt.execute(Tabla_UsuarioArticulo);
            stmt.close();
            stmt = conn.createStatement();
            stmt.execute(Tabla_UsuarioWiki);
            stmt.close();
            stmt = conn.createStatement();
            stmt.execute(Tabla_Wiki);
            stmt.close();
            stmt = conn.createStatement();
            stmt.execute(Tabla_WikiArticulo);
            stmt.close();
            stmt = conn.createStatement();
            stmt.execute(Tabla_ColaboradorEstado);
           
            //Indices
            stmt.close();
            stmt = conn.createStatement();
            stmt.execute(Index_AU);
            stmt.close();
            stmt = conn.createStatement();
            stmt.execute(Index_AC);
            stmt.close();
            stmt = conn.createStatement();
            stmt.execute(Index_UA);
            stmt.close();
            stmt = conn.createStatement();
            stmt.execute(Index_CA);
            stmt.close();
            stmt = conn.createStatement();
            stmt.execute(Index_UAA);
            stmt.close();
            stmt = conn.createStatement();
            stmt.execute(Index_UAU);
            stmt.close();
            stmt = conn.createStatement();
            stmt.execute(Index_UWU);
            stmt.close();
            stmt = conn.createStatement();
            stmt.execute(Index_UWW);
            stmt.close();
            stmt = conn.createStatement();
            stmt.execute(Index_WAA);
            stmt.close();
            stmt = conn.createStatement();
            stmt.execute(Index_WAW);
            stmt.close();
            stmt = conn.createStatement();
            stmt.execute(Index_CEU);
            stmt.close();
            stmt = conn.createStatement();
            stmt.execute(Index_CEA);
            stmt.close();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        crearContenido();
    }

    /**
     * Create the test objects to be used by the
     * application to run properly and show a working
     * correct. Both Wikis, Articles, and their elements and users will
     * will be added after execution.
     *
     * @throws ClassNotFoundException
     */
    public void crearContenido() throws ClassNotFoundException {

        /* Creando usuarios */
        Usuario gestor = new Gestor("Pedro", "Perez", "Suarez", "Calle Alberca, 59", "602311489");
        Usuario coordinador = new Coordinador("José", "Hernandez", "Hernandez", "Calle Frutos, 211", "602348989");
        Usuario coordinadorDos = new Coordinador("Laura", "Marcos", "Hernandez", "Avenida de Córdoba, 1", "604731548");
        Usuario supervisor = new Supervisor("Cristina", "Campos", "Requena", "Avenida del Páramo, 500", "604731222");
        Usuario supervisorDos = new Supervisor("Carlos", "Sainz", "Sainz", "Avenida de la fórmula, 32", "604115522");
        Usuario supervisorTres = new Supervisor("Julieta", "Venegas", "Velasquez", "Calle Juán XX, 500", "604478956");
        Usuario colaborador = new Colaborador("Julio", "Verne", "Gabriel", "Calle Francia, 24", "647802354");
        Usuario colaboradorDos = new Colaborador("Victor", "Martinez", "Coslada", "Calle Terual, 24", "698745123");

        /* Articulo uno */
        Articulo articulo = new Articulo("Las fobias", "Miedos irracionales del ser humano.");
        articulo.addContenido(new Contenido("Primer capitulo", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed bibendum ipsum a sagittis condimentum. Duis viverra, nibh sagittis efficitur luctus, mauris massa posuere quam, sit amet aliquet eros massa nec enim. Phasellus et enim eget ex sollicitudin pellentesque ut vel sapien. Nulla sed augue risus. Nunc auctor, ante in volutpat gravida, dui velit finibus ligula, aliquam feugiat augue massa nec nulla. Nullam euismod nulla sit amet purus scelerisque, sit amet condimentum nisi auctor. "));
        articulo.addContenido(new Contenido("Los miedos", "Morbi quis libero risus. Integer auctor magna nec nibh dapibus luctus. Donec in gravida dolor, quis tristique erat. Nulla consectetur justo sit amet enim commodo, at commodo ligula imperdiet. Etiam odio sapien, blandit feugiat turpis vitae, sodales posuere turpis. "));

        /* Articulo dos*/
        Articulo articuloDos = new Articulo("Las mascotas", "Definición de lo que se puede considerar como mascota");
        articuloDos.addContenido(new Contenido("Duis molestie", "Duis molestie metus vitae velit dictum consequat. Fusce finibus augue nisl, sed faucibus enim viverra vitae. Vivamus eu libero sit amet ex fermentum lacinia eu vitae elit."));
        articuloDos.addContenido(new Contenido("Ut feugiat", "nec mi a efficitur. Vivamus sed risus aliquet sem convallis venenatis. Praesent ultrices quam eros, id vestibulum dolor auctor id. Nulla facilisi. Ut sollicitudin venenatis aliquet. Integer posuere massa in enim bibendum, ac interdum velit tempor. Quisque ex elit, hendrerit eu aliquet in, rutrum id lacus. Mauris at nulla euismod diam porttitor porta. Aenean convallis erat quis quam sagittis tincidunt."));

        /* Articulo tres */
        Articulo articuloTres = new Articulo("Noches de luna llena", "Se Prende resolver la existencia de los licántropos.");
        articuloTres.addContenido(new Contenido("Phaselius venenatis", "Nulla iaculis lacinia felis, eu eleifend ex fringilla nec. Fusce et augue nulla. Suspendisse mollis ac dui in commodo. Donec eu mollis nisl. Fusce massa lectus, tincidunt quis lobortis in, congue et nisi. Pellentesque facilisis mattis ante, sagittis faucibus magna."));
        articuloTres.addContenido(new Contenido("Nunc facilisis", "Dignissim neque. Duis lobortis mauris vitae odio sagittis, sed aliquam leo molestie. Nullam vel elementum libero. Donec imperdiet sodales ipsum ut volutpat. Sed porttitor molestie condimentum."));
        articuloTres.addContenido(new Contenido("Suspendisse at pulvinar augue", "Aenean eu scelerisque velit. Etiam consectetur ante mi, consequat sagittis sapien hendrerit non. Nullam suscipit ipsum sem. Sed metus ante, pulvinar sit amet sodales ut, varius id velit. Duis bibendum nisi vitae pretium convallis. Praesent diam sem, imperdiet id erat sed, aliquet pretium eros."));

        /* Wiki Uno */
        Wiki wiki = new Wiki("Los animales", "Formas en la que los animales se relacionan con el entorno y los humanos.");
        Wiki wikiDos = new Wiki("Miedo", "Descubriendo el porqué las personas sienten angustia ante determinados estímulos.");
        Wiki wikiTres = new Wiki("La Luna", "Resolviendo las dudas más llamativas que envuelven a éste pequeño satélite terrestre.");

        /* Declarando variables para almacenar los eleemntos */
        ArticuloAcceso articuloAccess = new ArticuloAccesoImpl();
        UsuarioAcceso usuarioAccess = new UsuarioAccesoImpl();
        WikiAcceso wikiAccess = new WikiAccesoImpl();

        /* Guardando usuarios en el sistema */
        usuarioAccess.insertUser(gestor);
        usuarioAccess.insertUser(coordinador);
        usuarioAccess.insertUser(coordinadorDos);
        usuarioAccess.insertUser(supervisor);
        usuarioAccess.insertUser(supervisorDos);
        usuarioAccess.insertUser(supervisorTres);
        usuarioAccess.insertUser(colaborador);
        usuarioAccess.insertUser(colaboradorDos);

        /* Save Wikis  */
        wikiAccess.insertWiki(wiki);
        wikiAccess.insertWiki(wikiDos);
        wikiAccess.insertWiki(wikiTres);

        /* Save Articulos */
        articuloAccess.insertArticle(articulo);
        this.insertarContenido(articulo, articulo.getContenido());
        articuloAccess.insertArticle(articuloDos);
        this.insertarContenido(articuloDos, articuloDos.getContenido());
        articuloAccess.insertArticle(articuloTres);
        this.insertarContenido(articuloTres, articuloTres.getContenido());

        /* Add users into the articles*/
        articuloAccess.insertUser(articulo, supervisor);
        articuloAccess.insertUser(articulo, supervisorDos);
        articuloAccess.insertUser(articulo, coordinador);
        

        articuloAccess.insertUser(articuloDos, supervisor);      
        articuloAccess.insertUser(articuloDos, coordinadorDos);

        articuloAccess.insertUser(articuloTres, supervisorDos);
        articuloAccess.insertUser(articuloTres, supervisorTres);
        articuloAccess.insertUser(articuloTres, coordinadorDos);
        articuloAccess.insertUser(articuloTres, coordinador);

        /* Add articles into the Wikis */
        wikiAccess.insertContent(wikiDos, articulo);
        wikiAccess.insertContent(wikiDos, articuloTres);
        wikiAccess.insertContent(wiki, articuloDos);
        wikiAccess.insertContent(wikiTres, articuloDos);

        /* Add users into the Wikis */
        wikiAccess.insertUser(wiki, supervisor);
        wikiAccess.insertUser(wiki, supervisorDos);
        wikiAccess.insertUser(wikiDos, coordinador);
        wikiAccess.insertUser(wikiDos, supervisorDos);
        wikiAccess.insertUser(wikiDos, supervisorTres);
        wikiAccess.insertUser(wikiTres, coordinadorDos);
        wikiAccess.insertUser(wikiTres, coordinador);
        wikiAccess.insertUser(wikiTres, supervisorDos);
        wikiAccess.insertUser(wikiTres, supervisor);
    }
    
    /**
     * Insert the content into the article
     * @param articulo
     * @param contenido 
     */
    private void insertarContenido(Articulo articulo, List<Contenido> contenido) {
        ArticuloAcceso articuloAccess = new ArticuloAccesoImpl();
        for (Contenido content : contenido) {
            articuloAccess.insertContent(articulo, content);
        }
    }

    

}
