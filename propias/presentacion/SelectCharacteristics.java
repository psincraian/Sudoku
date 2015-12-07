package propias.presentacion;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import propias.dominio.clases.CaracteristiquesPartida;

public class SelectCharacteristics extends JPanel {

	public interface GetParametersListener {
		public void getParameters(CaracteristiquesPartida caracteristiques);
	}
	
	private static final long serialVersionUID = -170046201841094501L;
	
	private final static String TITLE = "Select Characteristics";
	private final static String SAVE_BUTTON = "Save";

	private final static String MATCH_LABEL = "Match";
	private final static String MATCH_TRAINING = "Training";
	private final static String MATCH_COMPETITION = "Competition";

	private final static String DIFFICUlTY_LABEL = "Difficulty";
	private final static String DIFFICUlTY_EASY = "Easy";
	private final static String DIFFICUlTY_MEDIUM = "Medium";
	private final static String DIFFICUlTY_HARD = "Hard";

	private final static String SIZE_LABEL = "Size";
	private final static String SIZE_9 = "9x9";
	private final static String SIZE_16 = "16x16";

	private final static String GIVEN_NUMBERS_LABEL = "Given numbers";

	private CaracteristiquesPartida characteristics;
	private JTextField hiddenNumbersField;
	private GetParametersListener listener;

	public SelectCharacteristics(Object container) {
		super();
		
		try {
			listener = (GetParametersListener) container;
		} catch (Exception e) {
			System.out.println("S'ha d'implementar la interface!");
			e.printStackTrace();
		}
		
		characteristics = new CaracteristiquesPartida();
		characteristics.setDificultat(1);
		characteristics.setTipusPartida(0);
		characteristics.setMida(9);
		createView();
	}

	private void createView() {		
		JPanel panelMatch = new JPanel();
		JPanel panelDifficulty = new JPanel();
		JPanel panelSize = new JPanel();
		JPanel panelGivenNuber = new JPanel();

		createMatchGroup(panelMatch);
		createDifficultyGroup(panelDifficulty);
		createSizeGroup(panelSize);
		createGivenNumbersPanel(panelGivenNuber);

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

	private void modifyPanelProperties() {
		setBorder(null);
        setMinimumSize(getPreferredSize());
		setMaximumSize(getMinimumSize());
		setName(TITLE);
	}

	private void createMatchGroup(JPanel panel) {
		JRadioButton matchTraining = new JRadioButton(MATCH_TRAINING);
		matchTraining.setActionCommand(MATCH_TRAINING);
		matchTraining.setSelected(true);

		JRadioButton matchCompetition = new JRadioButton(MATCH_COMPETITION);
		matchCompetition.setActionCommand(MATCH_COMPETITION);

		ButtonGroup match = new ButtonGroup();
		match.add(matchTraining);
		match.add(matchCompetition);

		RadiousActionListener listener = new RadiousActionListener();
		matchTraining.addActionListener(listener);
		matchCompetition.addActionListener(listener);

		panel.setBorder(BorderFactory.createTitledBorder(MATCH_LABEL));
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);

		layout.setHorizontalGroup(
			layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
				.addComponent(matchTraining)
				.addGap(18, 18, 18)
				.addComponent(matchCompetition)
				.addGap(0, 0, Short.MAX_VALUE))
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(matchTraining)
				.addComponent(matchCompetition))
		);
	}

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
				default: break;
			}

			System.out.println(e.getActionCommand());
		}
	}
	
	private class SaveActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			characteristics.setGivenNumbers(Integer.parseInt((hiddenNumbersField.getText())));
			listener.getParameters(characteristics);
		}
		
	}
}