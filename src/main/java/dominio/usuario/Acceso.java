
package dominio.usuario;

/***
 * 
 * Class created to store employee system login and password
 *
 */
public class Acceso {
	
	private String usuario;
	private String password;
	
	/**
	 * Acceso constructor
	 * @param usuario
	 * @param password
	 */
	public Acceso(String usuario, String password) {
		
		this.usuario = usuario;
		this.password = password;
	}

	/**
	 * Return the user name
	 * @return: usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * Set the user name
	 * @param usuario
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * Return the user password
	 * @return: password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Set the user password
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}

