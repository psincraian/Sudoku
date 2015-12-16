package propias.presentacion;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

/** Aquesta classe remplaca JTextField. Basicament el que fem es afegir un hint,
 * que ens permet indicar informació adicional en el mateix quadre d'entrada. 
 * Quan l'usuari clica aquesta informació desapareix i permet introduir la seva
 * propia informació.
 * 
 * @author petrusqui
 *
 */
class HintTextField extends JTextField implements FocusListener {

	  private final String hint;
	  private boolean showingHint;

	  /** La constructora per defecte. 
	   * 
	   * @param hint Es el missatge d'informació a mostrar
	   */
	  public HintTextField(final String hint) {
	    super(hint);
	    this.hint = hint;
	    this.showingHint = true;
	    super.addFocusListener(this);
	  }

	  /** Quan es l'objecte seleccionat
	   * 
	   */
	  @Override
	  public void focusGained(FocusEvent e) {
	    if(this.getText().isEmpty()) {
	      super.setText("");
	      showingHint = false;
	    }
	  }
	  
	  /** Quan no es el objecte seleccionat
	   * 
	   */
	  @Override
	  public void focusLost(FocusEvent e) {
	    if(this.getText().isEmpty()) {
	      super.setText(hint);
	      showingHint = true;
	    }
	  }
	  
	  /** Obtenim el text
	   * 
	   */
	  @Override
	  public String getText() {
	    return showingHint ? "" : super.getText();
	  }
	}
