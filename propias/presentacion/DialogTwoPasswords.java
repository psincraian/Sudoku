package propias.presentacion;

import javax.swing.JDialog;

public class DialogTwoPasswords extends JDialog {

	public interface twoPasswordsInterface {
		public void save(String password1, String password2);
		public void cancel();
	}
	
    private DialogTwoPasswords(Object object) {
    	super();
    	listener = (twoPasswordsInterface) object;
        initComponents();
        setModal(true);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        panePassword1 = new javax.swing.JScrollPane();
        password1 = new HintTextField("Introduce password");
        panePassword2 = new javax.swing.JScrollPane();
        password2 = new HintTextField("Repeat password");
        Save = new javax.swing.JButton();
        Cancel = new javax.swing.JButton();

        password1.setToolTipText("Introduce the password");
        panePassword1.setViewportView(password1);

        password2.setToolTipText("Repeat the password");
        panePassword2.setViewportView(password2);

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

        javax.swing.GroupLayout jDialog6Layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(jDialog6Layout);
        jDialog6Layout.setHorizontalGroup(
            jDialog6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDialog6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panePassword1)
                    .addComponent(panePassword2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDialog6Layout.createSequentialGroup()
                        .addComponent(Cancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(Save, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jDialog6Layout.setVerticalGroup(
            jDialog6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panePassword1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panePassword2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDialog6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Save)
                    .addComponent(Cancel))
                .addContainerGap(197, Short.MAX_VALUE))
        );

       

        pack();
    }                     

    private void CancelActionPerformed(java.awt.event.ActionEvent evt) {  
    	listener.cancel();
    }                                      

    private void SaveActionPerformed(java.awt.event.ActionEvent evt) {                                     
    	listener.save(password1.getText(), password2.getText());
    }    
    
    public static DialogTwoPasswords getInstance(Object object) {
    	if (instance != null)
			try {
				instance.finalize();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	instance = new DialogTwoPasswords(object);
    	return instance;
    }

    private javax.swing.JButton Cancel;
    private javax.swing.JButton Save;
    private javax.swing.JScrollPane panePassword1;
    private javax.swing.JScrollPane panePassword2;
    private HintTextField password1;
    private HintTextField password2;
    private twoPasswordsInterface listener;
    private static DialogTwoPasswords instance;
}
