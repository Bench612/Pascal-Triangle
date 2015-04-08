import java.awt.BorderLayout;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PascalFrame extends JFrame  implements ActionListener, ChangeListener, AdjustmentListener {

	JTextField primeField;
	JTextField maxRowsField;
	JTextField distinguishedPrimeField;
	JSlider brightness;
	JScrollBar upDownScroller;
	JScrollBar leftRightScroller;
	JSlider rowSlider;
	PascalPanel pascalPanel;
	public PascalFrame() {
		setBounds(100, 100, 900, 850);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pascalPanel = new PascalPanel(PascalPanel.MOD,2);
		setTitle("Pascal's Triangle Mod Primes");
		JPanel containerPanel = new JPanel(new BorderLayout());
		setContentPane(containerPanel);
		containerPanel.add(pascalPanel, BorderLayout.CENTER);
		
		upDownScroller = new JScrollBar(JScrollBar.VERTICAL, 0,Integer.MAX_VALUE / 15, 0, Integer.MAX_VALUE);
		containerPanel.add(upDownScroller, BorderLayout.EAST);
		upDownScroller.addAdjustmentListener(this);
		
		leftRightScroller = new JScrollBar(JScrollBar.HORIZONTAL, 0,Integer.MAX_VALUE / 15, 0, Integer.MAX_VALUE);
		containerPanel.add(leftRightScroller, BorderLayout.SOUTH);
		leftRightScroller.addAdjustmentListener(this);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(new JLabel("Prime: "));
		primeField = new JTextField(pascalPanel.triangle.getPrime() + "");
		menuBar.add(primeField);
		primeField.addActionListener(this);
		
		menuBar.add(new JLabel("White #: "));
		distinguishedPrimeField = new JTextField("" + pascalPanel.distinguishedRemainder);
		menuBar.add(distinguishedPrimeField);
		distinguishedPrimeField.addActionListener(this);
		
		menuBar.add(new JLabel("Brightness"));
		brightness = new JSlider(0,100, (int)(pascalPanel.brightness * 100));
		menuBar.add(brightness);
		brightness.addChangeListener(this);
		
		menuBar.add(new JLabel("\"Max\" Rows Visible:"));
		maxRowsField = new JTextField(pascalPanel.rowsOnScreen * 2 + "");
		menuBar.add(maxRowsField);
		maxRowsField.addActionListener(this);
		
		menuBar.add(new JLabel("Zoom"));
		rowSlider = new JSlider(0, Integer.MAX_VALUE);
		rowSlider.addChangeListener(this);
		menuBar.add(rowSlider);
		
		setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == primeField)
		{
			pascalPanel.reset(Integer.parseInt(primeField.getText()));
		}
		else if (arg0.getSource() == maxRowsField)
		{
			updateRowsOnScreen();
		}
		else if (arg0.getSource() == distinguishedPrimeField)
		{
			pascalPanel.distinguishedRemainder = Integer.parseInt(distinguishedPrimeField.getText());
		}
		pascalPanel.repaint();
	}

	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == rowSlider)
		{
			updateRowsOnScreen();
		}else if (e.getSource() == brightness)
		{
			pascalPanel.setBrightness((float)brightness.getValue() / brightness.getMaximum());
		}
		pascalPanel.repaint();
	}
	
	void updateRowsOnScreen()
	{
		long max = Long.parseLong(maxRowsField.getText());
		if (rowSlider.getValue() == rowSlider.getMaximum())
			pascalPanel.rowsOnScreen = max;
		else
		{
			pascalPanel.rowsOnScreen = ((max-2) * rowSlider.getValue()) / rowSlider.getMaximum() + 2;
		}
	}

	public void adjustmentValueChanged(AdjustmentEvent e) {
		if (e.getSource() == upDownScroller)
		{
			pascalPanel.topLeftY = (Long.parseLong(maxRowsField.getText()) * upDownScroller.getValue()) / upDownScroller.getMaximum();
		}
		else if (e.getSource() == leftRightScroller)
		{
			//@@@ this part is bad code only works if its square
			pascalPanel.topLeftX = ((Long.parseLong(maxRowsField.getText()) * leftRightScroller.getValue()) / leftRightScroller.getMaximum());
		}
		pascalPanel.repaint();
	}

}
