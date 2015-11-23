package propias.dominio.clases;

/** Un usuari convidat. Es un tipus especial d'usuari. Aquest usuari té com a nom
 * convidat i no té contrasenya.
 * 
 * @author Petru Rares Sincraian
 *
 */
public class UsuariConvidat extends UsuariGeneral {

	/** La constructora per defecte
	 * 
	 */
	public UsuariConvidat() {
		super("Convidat");
	}
}
