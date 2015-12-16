package propias.presentacion;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import propias.dominio.clases.OptionsMenu;

/**
 * 
 * @author Petru Rares Sincraian
 *
 */
public class VistaMenu extends SetView {
	
	/** getOption es crida quan es fa clic en algun botó. En option s'indica la opcio
	 * que s'ha seleccionat.
	 * 
	 * @author Petru Rares Sincraian
	 *
	 */
	public interface MenuButtonClicked {
		public void getOption(OptionsMenu option);
	}
	
	private static final String NEW_MATCH_ACTION = "New game";
	private static final String LOAD_GAME_ACTION = "Load game";
	private static final String CREATE_SUDOKU_ACTION = "Create sudoku";
	private static final String DELETE_USER_ACTION = "Eliminar Usuari";
	private static final String VIEW_RANKING_ACTION = "Ranking";
	private static final String VIEW_PROFILE_ACTION = "View profile";
	private static final String EXIT_ACTION = "exit";
	
	private JButton newMatch;
	private JButton loadGame;
	private JButton createSudoku;
	private JButton deleteUser;
	private JButton viewRanking;
	private JButton viewProfile;
	private JButton exit;
	private MenuButtonClicked menuButtonClicked;

	
	/** La creadora per defecte. Es privada per inicialitzar un objecte fer 
	 * servir {@link #getInstance(Object)}
	 * 
	 * @param container L'objecte que implementa la interficie
	 */
	private VistaMenu(Object object) {
		super();
		
		try {
			menuButtonClicked = (MenuButtonClicked) object;
		} catch (Exception e) {
			System.out.println("S'ha d'implementar la interface!");
			e.printStackTrace();
		}
		
		createUserView();
	}
	
	/** Crea la vista del usuari
	 * 
	 */
	private void createUserView() {
        setButtonsText();
        setMinimumSize(new Dimension(172, 210));
        
        GroupLayout thisLayout = new GroupLayout(this);
        this.setLayout(thisLayout);
        thisLayout.setHorizontalGroup(
            thisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thisLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(thisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(newMatch, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(loadGame, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(createSudoku, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(deleteUser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(thisLayout.createSequentialGroup()
                        .addGroup(thisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(exit, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(thisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(viewProfile, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                                .addComponent(viewRanking, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        thisLayout.setVerticalGroup(
            thisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thisLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(newMatch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(loadGame)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(createSudoku)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deleteUser)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(viewRanking)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(viewProfile)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exit))
        );
	}
	
	/** Afegeix els texts a tots els butons
	 * 
	 */
	private void setButtonsText() {
        newMatch = new JButton(OptionsMenu.PartidaRapida.toString());
        loadGame = new JButton(OptionsMenu.CargarPartida.toString());
        createSudoku = new JButton(OptionsMenu.CrearSudoku.toString());
        deleteUser = new JButton(OptionsMenu.EliminarUsuari.toString());
        viewRanking = new JButton(OptionsMenu.Ranking.toString());
        viewProfile = new JButton(OptionsMenu.Perfil.toString());
        exit = new JButton(OptionsMenu.Sortir.toString());
        
        ButtonListener listener = new ButtonListener();
        newMatch.addActionListener(listener);
        loadGame.addActionListener(listener);
        createSudoku.addActionListener(listener);
        deleteUser.addActionListener(listener);
        viewRanking.addActionListener(listener);
        viewProfile.addActionListener(listener);
        exit.addActionListener(listener);
        
        newMatch.setActionCommand(NEW_MATCH_ACTION);
        loadGame.setActionCommand(LOAD_GAME_ACTION);
        createSudoku.setActionCommand(CREATE_SUDOKU_ACTION);
        deleteUser.setActionCommand(DELETE_USER_ACTION);
        viewRanking.setActionCommand(VIEW_RANKING_ACTION);
        viewProfile.setActionCommand(VIEW_PROFILE_ACTION);
        exit.setActionCommand(EXIT_ACTION);
	}
	
	/** Modifica la vista al mode convidat. Això el que fa es deshabilitat
	 * els botons de "Carregar partida", "Crear sudoku", "Esborrar usuari"
	 * "Veure ranking" i "Veure perfil"
	 * 
	 */
	public void updateToGuestView() {
		loadGame.setEnabled(false);
		createSudoku.setEnabled(false);
		deleteUser.setEnabled(false);
		viewRanking.setEnabled(false);
		viewProfile.setEnabled(false);
	}
	
	/** El listener de cada boto. Quan s'apreta un boto venim aqui.
	 * 
	 * @author Petru Rares Sincraian
	 *
	 */
	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			OptionsMenu option = OptionsMenu.PartidaRapida;
			
			switch (e.getActionCommand()) {
				case NEW_MATCH_ACTION:
					option = OptionsMenu.PartidaRapida;
					break;
				case LOAD_GAME_ACTION:
					option = OptionsMenu.CargarPartida;
					break;
				case CREATE_SUDOKU_ACTION:
					option = OptionsMenu.CrearSudoku;
					break;
				case DELETE_USER_ACTION:
					option = OptionsMenu.EliminarUsuari;
					break;
				case VIEW_RANKING_ACTION:
					option = OptionsMenu.Ranking;
					break;
				case VIEW_PROFILE_ACTION:
					option = OptionsMenu.Perfil;
					break;
				case EXIT_ACTION:
					option = OptionsMenu.Sortir;
					break;
				default: break;
			}
			
			menuButtonClicked.getOption(option);
		}
	}
	
    /** El metode getInstance es la constructora. Aquesta classe es singleton
     * per tant en un moment donat només podrem tenir un objecte. Quan es crida
     * l'objecte anterior es borra.
     * 
     * @param object
     * @return Una referencia al objecte
     */
	public static VistaMenu getInstance(Object object) {
		if (instance != null) {
			try {
				instance.finalize();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		
		instance = new VistaMenu(object);
		return instance;
	}
	
	private static VistaMenu instance;
}
