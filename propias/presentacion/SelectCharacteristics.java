package propias.presentacion;

import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;

import propias.dominio.clases.CaracteristiquesPartida;

/** Vista que ens mostra i ens permet seleccionar un conjunt de caracteristiques
 * del sudoku. Permet seleccionar el tipus de partida, la mida del sudoku, el 
 * nombre minim de caselles, si es de la bd.
 * 
 * @author petrusqui
 *
 */
public class SelectCharacteristics extends SetView {

	/** La interficie per communicar-se amb els altres metodes. Quan s'apreta el
	 * boto save es crida el metode getParameters amb les caracteristiques seleccionades.
	 * 
	 * @author Petru Rares Sincraian
	 *
	 */
	public interface GetParametersListener {
		public void getParameters(CaracteristiquesPartida caracteristiques);
	}
		
	private final static String TITLE = "Select Characteristics";
	private final static String SAVE_BUTTON = "Guardar";

	private final static String MATCH_LABEL = "Partida";
	private final static String MATCH_TRAINING = "Entrenament";
	private final static String MATCH_COMPETITION = "Competició";

	private final static String DIFFICUlTY_LABEL = "Dificultat";
	private final static String DIFFICUlTY_EASY = "Fàcil";
	private final static String DIFFICUlTY_MEDIUM = "Mitjà";
	private final static String DIFFICUlTY_HARD = "Difícil";

	private final static String SIZE_LABEL = "Mida";
	private final static String SIZE_9 = "9x9";
	private final static String SIZE_16 = "16x16";

	private final static String GIVEN_NUMBERS_LABEL = "Numeros mínims donats";
	
	private final static String SUDOKU_BD_NEW_LABEL = "Nou o de la Base de Dades";
	private final static String SUDOKU_NEW = "Nou sudoku";
	private final static String SUDOKU_BD = "De la BBDD";

	private CaracteristiquesPartida characteristics;
	private JTextField hiddenNumbersField;
	private GetParametersListener listener;
	
	private JPanel panelMatch;
	private JPanel panelDifficulty;
	private JPanel panelSize;
	private JPanel panelGivenNuber;
	private JPanel panelSudokuType;

	/** La creadora per defecte. Es privada per inicialitzar un objecte fer 
	 * servir {@link #getInstance(Object)}
	 * 
	 * @param container L'objecte que implementa la interficie
	 */
	private SelectCharacteristics(Object container) {
		super();
		
		try {
			listener = (GetParametersListener) container;
		} catch (Exception e) {
			System.out.println("S'ha d'implementar la interface!");
			e.printStackTrace();
		}
		
		setTitle(TITLE);
		
		characteristics = new CaracteristiquesPartida();
		characteristics.setDificultat(1);
		characteristics.setTipusPartida(0);
		characteristics.setMida(9);
		characteristics.setNewSudoku(true);
		setVisible(true);
		createView();
	}
	
	/** Actualitza la vista a convidat. El que fa es que esborra per seleccionar
	 * competició del sudoku
	 * 
	 */
	public void updateToGuest() {
		panelMatch.remove(1);
	}

	/** Crea la vista
	 * 
	 */
	private void createView() {
		panelMatch = new JPanel();
		panelDifficulty = new JPanel();
		panelSize = new JPanel();
		panelGivenNuber = new JPanel();
		panelSudokuType = new JPanel();
		createMatchGroup(panelMatch);
		createDifficultyGroup(panelDifficulty);
		createSizeGroup(panelSize);
		createGivenNumbersPanel(panelGivenNuber);
		createSudokuSelectionGroup(panelSudokuType);

		JButton saveButton = new JButton(SAVE_BUTTON);
		saveButton.addActionListener(new SaveActionListener());

		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(
		layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
					.addComponent(panelSize, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(panelMatch, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(panelSudokuType, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(panelGivenNuber, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(saveButton, GroupLayout.Alignment.TRAILING)
					.addComponent(panelDifficulty, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			.addContainerGap())
		);
		layout.setVerticalGroup(
		layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
			.addComponent(panelMatch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
			.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
			.addComponent(panelSudokuType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
			.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
			.addComponent(panelDifficulty, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
			.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
			.addComponent(panelSize, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
			.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
			.addComponent(panelGivenNuber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
			.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
			.addComponent(saveButton)
			.addGap(12, 12, 12))
		);
		
		modifyPanelProperties();
	}

	/** Modifica les propietats del panell
	 * 
	 */
	private void modifyPanelProperties() {
		setBorder(null);
        setMinimumSize(getPreferredSize());
		setMaximumSize(getMinimumSize());
		setName(TITLE);
	}
	
	/** Crea el group del panell
	 * 
	 * @param panel
	 */
	private void createSudokuSelectionGroup(JPanel panel) {
		JRadioButton newSudoku = new JRadioButton(SUDOKU_NEW);
		newSudoku.setActionCommand(SUDOKU_NEW);
		newSudoku.setSelected(true);

		JRadioButton bdSudoku = new JRadioButton(SUDOKU_BD);
		bdSudoku.setActionCommand(SUDOKU_BD);
		
		ButtonGroup typeSudoku = new ButtonGroup();
		typeSudoku.add(newSudoku);
		typeSudoku.add(bdSudoku);

		RadiousActionListener listener = new RadiousActionListener();
		newSudoku.addActionListener(listener);
		bdSudoku.addActionListener(listener);

		panel.setBorder(BorderFactory.createTitledBorder(SUDOKU_BD_NEW_LABEL));
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);

		layout.setHorizontalGroup(
			layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
				.addComponent(newSudoku)
				.addGap(18, 18, 18)
				.addComponent(bdSudoku)
				.addGap(0, 0, Short.MAX_VALUE))
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(newSudoku)
				.addComponent(bdSudoku))
		);
	}

	/** Crea l'apartat Match
	 * 
	 * @param panel
	 */
	private void createMatchGroup(JPanel panel) {
		JRadioButton matchTraining = new JRadioButton(MATCH_TRAINING);
		matchTraining.setActionCommand(MATCH_TRAINING);
		matchTraining.setSelected(true);

		JRadioButton bdSudoku = new JRadioButton(MATCH_COMPETITION);
		bdSudoku.setActionCommand(MATCH_COMPETITION);

		ButtonGroup match = new ButtonGroup();
		match.add(matchTraining);
		match.add(bdSudoku);

		RadiousActionListener listener = new RadiousActionListener();
		matchTraining.addActionListener(listener);
		bdSudoku.addActionListener(listener);

		panel.setBorder(BorderFactory.createTitledBorder(MATCH_LABEL));
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);

		layout.setHorizontalGroup(
			layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
				.addComponent(matchTraining)
				.addGap(18, 18, 18)
				.addComponent(bdSudoku)
				.addGap(0, 0, Short.MAX_VALUE))
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(matchTraining)
				.addComponent(bdSudoku))
		);
	}

	/** Crea el panell de la dificultat
	 * 
	 * @param panel
	 */
	private void createDifficultyGroup(JPanel panel) {
		JRadioButton difficultyEasy = new JRadioButton(DIFFICUlTY_EASY);
		difficultyEasy.setActionCommand(DIFFICUlTY_EASY);

		JRadioButton difficultyMedium = new JRadioButton(DIFFICUlTY_MEDIUM);
		difficultyMedium.setActionCommand(DIFFICUlTY_MEDIUM);
		difficultyMedium.setSelected(true);

		JRadioButton difficultyHard = new JRadioButton(DIFFICUlTY_HARD);
		difficultyHard.setActionCommand(DIFFICUlTY_HARD);

		ButtonGroup difficulty = new ButtonGroup();
		difficulty.add(difficultyEasy);
		difficulty.add(difficultyMedium);
		difficulty.add(difficultyHard);

		RadiousActionListener listener = new RadiousActionListener();
		difficultyEasy.addActionListener(listener);
		difficultyMedium.addActionListener(listener);
		difficultyHard.addActionListener(listener);

		panel.setBorder(BorderFactory.createTitledBorder(DIFFICUlTY_LABEL));
		panel.setMinimumSize(panel.getPreferredSize());
		GroupLayout layout = new GroupLayout(panel);
        	panel.setLayout(layout);

		layout.setHorizontalGroup(
		layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
				.addComponent(difficultyEasy)
				.addGap(18, 18, 18)
				.addComponent(difficultyMedium)
				.addGap(18, 18, 18)
				.addComponent(difficultyHard))
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(difficultyEasy)
				.addComponent(difficultyMedium)
				.addComponent(difficultyHard))
		);
	}

	/** Crea el panell de la mida
	 * 
	 * @param panel
	 */
	private void createSizeGroup(JPanel panel) {
		JRadioButton size9 = new JRadioButton(SIZE_9);
		size9.setActionCommand(SIZE_9);
		size9.setSelected(true);

		JRadioButton size16 = new JRadioButton(SIZE_16);
		size16.setActionCommand(SIZE_16);

		ButtonGroup size = new ButtonGroup();
		size.add(size9);
		size.add(size16);

		RadiousActionListener listener = new RadiousActionListener();
		size9.addActionListener(listener);
		size16.addActionListener(listener);

		panel.setBorder(BorderFactory.createTitledBorder(SIZE_LABEL));
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);
		layout.setHorizontalGroup(
		layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
				.addComponent(size9)
				.addGap(18, 18, 18)
				.addComponent(size16)
				.addGap(0, 0, Short.MAX_VALUE))
		);
		layout.setVerticalGroup(
		layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(size9)
				.addComponent(size16))
		);
	}

	/** Crea el panell del nombre minim de nombres
	 * 
	 * @param panel
	 */
	private void createGivenNumbersPanel(JPanel panel) {
		hiddenNumbersField = new JTextField("0");

		panel.setBorder(BorderFactory.createTitledBorder(GIVEN_NUMBERS_LABEL));
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addComponent(hiddenNumbersField))
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
					.addGap(0, 0, Short.MAX_VALUE)
					.addComponent(hiddenNumbersField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
	}


	/** El listener de cada vistas
	 * 
	 * @author petrusqui
	 *
	 */
	private class RadiousActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
				case MATCH_TRAINING:
					characteristics.setTipusPartida(0);
					break;
				case MATCH_COMPETITION:
					characteristics.setTipusPartida(1);
					break;
				case DIFFICUlTY_EASY:
					characteristics.setDificultat(0);
					break;
				case DIFFICUlTY_MEDIUM:
					characteristics.setDificultat(1);
					break;
				case DIFFICUlTY_HARD:
					characteristics.setDificultat(2);
					break;
				case SIZE_9:
					characteristics.setMida(9);
					break;
				case SIZE_16:
					characteristics.setMida(16);
					break;
				case SUDOKU_NEW:
					characteristics.setNewSudoku(true);
					break;
				case SUDOKU_BD:
					characteristics.setNewSudoku(false);
					break;
				default: break;
			}
		}
	}
	
	/** Quan s'apreta el boto save venim aqui
	 * 
	 * @author petrusqui
	 *
	 */
	private class SaveActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			characteristics.setGivenNumbers(Integer.parseInt((hiddenNumbersField.getText())));
			listener.getParameters(characteristics);
		}
		
	}
	
    /** El metode getInstance es la constructora. Aquesta classe es singleton
     * per tant en un moment donat només podrem tenir un objecte. Quan es crida
     * l'objecte anterior es borra.
     * 
     * @param object
     * @return Una referencia al objecte
     */
	public static SelectCharacteristics getInstance(Object object) {
		if (instance != null) {
			try {
				instance.finalize();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		
		instance = new SelectCharacteristics(object);
		return instance;
	}
	
	private static SelectCharacteristics instance;
	
}