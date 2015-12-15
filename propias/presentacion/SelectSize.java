package propias.presentacion;

import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;

import propias.dominio.clases.CaracteristiquesPartida;

public class SelectSize extends SetView {

	public interface GetParametersListener {
		public void getSize(int size);
	}
	
	private static final long serialVersionUID = -170046201841094501L;
	
	private final static String TITLE = "Seleciona la Mida";
	private final static String SAVE_BUTTON = "Guardar";

	private final static String SIZE_LABEL = "Mida";
	private final static String SIZE_9 = "9x9";
	private final static String SIZE_16 = "16x16";

	private int size;
	private GetParametersListener listener;

	private SelectSize(Object container) {
		super();
		
		try {
			listener = (GetParametersListener) container;
		} catch (Exception e) {
			System.out.println("S'ha d'implementar el listener!!!");
			e.printStackTrace();
		}
		
		size = 9;
		createView();
	}

	private void createView() {		
		JPanel panelSize = new JPanel();
		createSizeGroup(panelSize);

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
					.addComponent(saveButton, GroupLayout.Alignment.TRAILING))
			.addContainerGap())
		);
		layout.setVerticalGroup(
		layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
			.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
			.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
			.addComponent(panelSize, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
			.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
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

	private class RadiousActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
				case SIZE_9:
					size = 9;
					break;
				case SIZE_16:
					size = 16;
					break;
				default: break;
			}
		}
	}
	
	private class SaveActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			listener.getSize(size);
		}
		
	}
	
	public static SelectSize getInstance(Object object) {
		if (instance != null) {
			try {
				instance.finalize();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		instance = new SelectSize(object);
		return instance;
	}
	
	private static SelectSize instance;
}