package propias.presentacion;

import javax.swing.JDialog;

/**
 * 
 * @author Petru Rares Sincraian
 * 
 * Mostra un Dialog amb una entrada de text i dos butons. Serveix per demanar el nou
 * nom del usuari.
 *
 */
public class DialogChangeUsername extends JDialog {
	
	/** Creadora per defecte pero privada. Si es vol instanciar la classe utilitzar
	 * {@link #getInstance(Object)}
	 * 
	 * @param object L'objecte que implementa la interficie
	 */
    private DialogChangeUsername(Object object) {
    	super();
    	listener = (changeUsername) object;
        initComponents();
        setModal(true);
    }
    
    /** La interficie per comunicar-se amb l'usuari. Quan s'apreti el boto guardar
     * es cridara a save(username) on username conté el text escrit en el textedit.
     * Quan es premi cancelar es cridarà al metode cancelName()
     * 
     * @author Petru Rares Sincraian 
     *
     */
    public interface changeUsername {
    	public void save(String username);
    	public void cancelName();
    }
    
    /** Crea la vista
     * 
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {

        usernamePane = new javax.swing.JScrollPane();
        username = new HintTextField("New username");
        Save = new javax.swing.JButton();
        Cancel = new javax.swing.JButton();

        usernamePane.setViewportView(username);

        Save.setText("Save");
        Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveActionPerformed(evt);
            }
        });

        Cancel.setText("Cancel");
        Cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(getContentPane());
        setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(usernamePane, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(Cancel, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Save, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(usernamePane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Cancel)
                    .addComponent(Save))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }                      

    /** Accio que es duu a terme quan es prem Cancel
     * 
     * @param evt
     */
    private void CancelActionPerformed(java.awt.event.ActionEvent evt) {       
    	listener.cancelName();
    }                                      

    /** Accio que es duu a terme quan es prem Save
     * 
     * @param evt
     */
    private void SaveActionPerformed(java.awt.event.ActionEvent evt) {   
    	listener.save(username.getText());
    }
    
    /** El metode getInstance es la constructora. Aquesta classe es singleton
     * per tant en un moment donat només podrem tenir un objecte. Quan es crida
     * l'objecte anterior es borra.
     * 
     * @param object
     * @return Una referencia al objecte
     */
    public static DialogChangeUsername getInstance(Object object) {
    	if (instance != null)
			try {
				instance.finalize();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
    	instance = new DialogChangeUsername(object);
    	return instance;
    }

    private javax.swing.JButton Cancel;
    private javax.swing.JButton Save;
    private HintTextField username;
    private javax.swing.JScrollPane usernamePane;
    private changeUsername listener;
    private static DialogChangeUsername instance;
}
