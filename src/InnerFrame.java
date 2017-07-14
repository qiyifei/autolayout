

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class InnerFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;


	private NorthPanel northPanel;
	
	
	private JPanel southPanel;
	private JButton enterButton;
	

	public InnerFrame() throws HeadlessException {
		super();
		initComponent();
	}

	private void initComponent() {
		
		enterButton = new JButton("refresh");
		southPanel = new JPanel();
		southPanel.add(enterButton);
		
		this.add(southPanel, BorderLayout.SOUTH);
		
		northPanel = new NorthPanel();
		this.add(northPanel, BorderLayout.CENTER);
		
		enterButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				northPanel.getNodes();
			}
		});
	}
	

	public static void main(String[] args) {
		InnerFrame innerFrame = new InnerFrame();
		innerFrame.setVisible(true);
		innerFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		innerFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

}
