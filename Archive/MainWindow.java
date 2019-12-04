import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.math.BigInteger;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import java.util.Scanner;

public class MainWindow extends JFrame
{
	private static final long serialVersionUID = -3880026026104218593L;
	private Primes m_Primes;
	private JTextField tfPrimeFileName;
	private JTextField tfCrossFileName;
	private JLabel lblPrimeCount;
	private JLabel lblCrossCount;
	private JLabel lblLargestPrime;
	private JLabel lblLargestCross;
	private JLabel lblStatus;
	
	MainWindow(String name, Primes p)
	{
		m_Primes = p;
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setTitle(Config.APPLICATIONNAME);
		GridBagLayout gridLayout = new GridBagLayout();
		this.setSize(1000, 400);
		this.getContentPane().setBackground(new Color(52, 0, 0));
		this.getContentPane().setLayout(gridLayout);
		
		// Constraints
		GridBagConstraints gbcMainWindow = new GridBagConstraints();
		gbcMainWindow.fill = GridBagConstraints.HORIZONTAL;
		gbcMainWindow.anchor = GridBagConstraints.WEST;
		gbcMainWindow.ipady = 10;
		gbcMainWindow.weightx = .5;
		gbcMainWindow.insets = new Insets(1,2,0,0);
		gbcMainWindow.gridx = 0;
		gbcMainWindow.gridy = 0;
		
		GridBagConstraints gbcPanel = new GridBagConstraints();
		gbcPanel.fill = GridBagConstraints.HORIZONTAL;
		gbcPanel.anchor = GridBagConstraints.WEST;
		gbcPanel.weightx = 1;
		gbcPanel.insets = new Insets(1,2,0,0);
		gbcPanel.gridx = 0;
		gbcPanel.gridy = 0;
		
		// Primes panel
		JPanel primesPanel = new JPanel();
		primesPanel.setLayout(new GridBagLayout());
		
		tfPrimeFileName = new JTextField(Config.PRIMENAME);
		tfPrimeFileName.setColumns(40);
		gbcPanel.gridwidth = 2;
		gbcPanel.gridx = 0;
		gbcPanel.gridy = 0;
		primesPanel.add(tfPrimeFileName, gbcPanel);

		JLabel primesLabel = new JLabel("Primes File");
		primesLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		gbcPanel.gridwidth = 1;
		gbcPanel.gridx = 0;
		gbcPanel.gridy = 1;
		primesPanel.add(primesLabel, gbcPanel);
		
		lblPrimeCount = new JLabel("0", SwingConstants.CENTER);
		lblPrimeCount.setFont(new Font("Tahoma", Font.BOLD, 12));
		gbcMainWindow.anchor = GridBagConstraints.EAST;
		gbcPanel.gridx = 2;
		gbcPanel.gridy = 0;
		primesPanel.add(lblPrimeCount, gbcPanel);

		JButton primeLoad = new JButton("Load");
		primeLoad.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
	    	File file = new File(Config.PRIMENAME);
	    	BigInteger n = null;
	    	Primes tempP = new Primes();
	    	try {
	    		lblStatus.setText("Status: Prime list loaded successfully.");
	     		//m_Primes.clearPrimes();
		    	Scanner input = new Scanner(file);
		    	while(input.hasNext()) 
		    	{
		    		n = input.nextBigInteger();
//		    		System.out.println(n);
		    		tempP.addPrime(n);
		    	}
		    	m_Primes = tempP;
		    	updateStats();
		    	input.close();
	    	}
     		catch(Exception ex){
	    		lblStatus.setText(ex.toString());
     		}
	    }
	  });
		gbcPanel.anchor = GridBagConstraints.EAST;
		gbcPanel.gridx = 1;
		gbcPanel.gridy = 1;
		primesPanel.add(primeLoad, gbcPanel);		
		
		JButton primeSave = new JButton("Save");
		primeSave.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	FileWriter writer = null;
		    	try {
			    	lblStatus.setText("Status: Prime list saved successfully.");
			    	writer = new FileWriter(Config.DATAPATH + Config.PRIMENAME);
			    	for(BigInteger i:m_Primes.iteratePrimes()) {
//			    		System.out.println(""+i);
			    		writer.write(""+i);
			    		writer.write("\n");
			    	}
			    	writer.close();
			    	m_Primes.generateTwinPrimes();
			    	m_Primes.generateHexPrimes();
			    	updateStats();
		    	} 
		    	catch(Exception ex) {
		    		lblStatus.setText(ex.toString());
		    	}
		    }
		  });
		gbcPanel.anchor = GridBagConstraints.WEST;
		gbcPanel.gridx = 2;
		gbcPanel.gridy = 1;
		primesPanel.add(primeSave, gbcPanel);	
		
		this.add(primesPanel, gbcMainWindow);

		
		// Hex panel
		JPanel hexPanel = new JPanel();
		hexPanel.setLayout(new GridBagLayout());
		
		tfCrossFileName = new JTextField(Config.CROSSNAME);
		tfCrossFileName.setColumns(40);

		gbcPanel.gridwidth = 2;
		gbcPanel.gridx = 0;
		gbcPanel.gridy = 0;
		hexPanel.add(tfCrossFileName, gbcPanel);

		JLabel crossLabel = new JLabel("Hexagon Cross File");
		crossLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		gbcPanel.gridwidth = 1;
		gbcPanel.gridx = 0;
		gbcPanel.gridy = 1;
		hexPanel.add(crossLabel, gbcPanel);
		
		lblCrossCount = new JLabel("0", SwingConstants.CENTER);
		lblCrossCount.setFont(new Font("Tahoma", Font.BOLD, 12));
		gbcPanel.gridx = 2;
		gbcPanel.gridy = 0;
		hexPanel.add(lblCrossCount, gbcPanel);

		JButton hexLoad = new JButton("Load");
		hexLoad.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
     		File file = new File(Config.CROSSNAME);
	    	BigInteger n1 = null;
	    	BigInteger n2 = null;
	    	String line;
	    	String[] lineVector;
	    	try {
	    		lblStatus.setText("Status: Cross list loaded successfully.");
	     		m_Primes.clearPrimes();
		    	Scanner input = new Scanner(file);
		    	while(input.hasNextLine()) 
		    	{
		    		line = input.nextLine();
		    		lineVector = line.split(",");
		    		n1 = new BigInteger(lineVector[0]);
		    		n2 = new BigInteger(lineVector[1]);
//		    		System.out.println(n1 + "," + n2);
		    		Pair<BigInteger> temp = new Pair<BigInteger>(n1,n2);
		    		m_Primes.addCross(temp);
		    	}
		    	updateStats();
		    	input.close();
	    	}
     		catch(Exception ex){
	    		lblStatus.setText(ex.toString());
     		}
	    }
	  });
		gbcPanel.anchor = GridBagConstraints.EAST;
		gbcPanel.gridx = 1;
		gbcPanel.gridy = 1;
		hexPanel.add(hexLoad, gbcPanel);		
		
		JButton hexSave = new JButton("Save");
			hexSave.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	FileWriter writer = null;
		    	try 
		    	{
			    	lblStatus.setText("Status: Cross list saved successfully.");
			    	writer = new FileWriter(Config.DATAPATH + Config.CROSSNAME);
			    	for(Pair<BigInteger> i:m_Primes.iterateCrosses()) {
//			    		System.out.println(i.left() + ","+i.right());
			    		writer.write(i.left() + ","+i.right());
			    		writer.write("\n");
			    	}
			    	writer.close();
			    	updateStats();
		    	} 
		    	catch(Exception ex) {
		    		lblStatus.setText(ex.toString());		    
		    	}
		    }
		  });
			gbcPanel.anchor = GridBagConstraints.WEST;
		gbcPanel.gridx = 2;
		gbcPanel.gridy = 1;
		gbcMainWindow.gridy = 1;
		hexPanel.add(hexSave, gbcPanel);
		
		this.add(hexPanel, gbcMainWindow);
		
		
		// Generate panel
		JPanel pnlButtons = new JPanel();
		pnlButtons.setLayout(new GridBagLayout());
		
		// generate button
		JButton btnGeneratePrimes = new JButton("Generate Primes");
		gbcPanel.gridheight = 2;
		btnGeneratePrimes.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {	
      		popupGeneratePrimes();
      }
    });
		gbcPanel.gridx = 0;
		gbcPanel.gridy = 0;
		pnlButtons.add(btnGeneratePrimes, gbcPanel);
		
		// cancel button
		JButton btnGenerateCrosses = new JButton("Generate Crosses");
		gbcPanel.gridheight = 2;
		btnGenerateCrosses.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
      	m_Primes.generateTwinPrimes();
      	m_Primes.generateHexPrimes();
	    	lblStatus.setText("Status: Excited. Hex primes have been generated.");
      	updateStats();
      }
    });
		gbcPanel.anchor = GridBagConstraints.EAST;
		gbcPanel.gridx = 2;
		pnlButtons.add(btnGenerateCrosses, gbcPanel);		
		
		lblLargestPrime = new JLabel("The largest prime has 0 digits.", SwingConstants.CENTER);
		lblLargestPrime.setFont(new Font("Tahoma", Font.BOLD, 10));
		gbcPanel.gridheight = 1;
		gbcPanel.gridwidth = 1;
		gbcPanel.gridx = 1;
		gbcPanel.gridy = 0;
		pnlButtons.add(lblLargestPrime, gbcPanel);
		
		lblLargestCross = new JLabel("The largest cross has 0 and 0 digits.", SwingConstants.CENTER);
		lblLargestCross.setFont(new Font("Tahoma", Font.BOLD, 10));
		gbcPanel.gridheight = 1;
		gbcPanel.gridwidth = 1;
		gbcPanel.gridx = 1;
		gbcPanel.gridy = 1;
		pnlButtons.add(lblLargestCross, gbcPanel);
		gbcMainWindow.gridy = 2;
		this.add(pnlButtons, gbcMainWindow);	
		
		
		// status
		JPanel pnlStatus = new JPanel();
		pnlStatus.setLayout(new GridBagLayout());

		lblStatus = new JLabel("Status: Bored.", SwingConstants.LEFT);
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 12));
		gbcPanel.gridx = 0;
		pnlStatus.add(lblStatus, gbcPanel);
		gbcMainWindow.gridx = 0;
		gbcMainWindow.gridy = 3;
		this.add(pnlStatus, gbcMainWindow);
		
		
		this.pack();
		this.setVisible(true);
	}

	protected void popupGeneratePrimes()
	{
		JDialog dPrimes = new JDialog(this, "Prime Number Generation");
		GridBagLayout gridLayout = new GridBagLayout();
		dPrimes.getContentPane().setBackground(new Color(52, 0, 0));
		dPrimes.getContentPane().setLayout(gridLayout);
		
		GridBagConstraints gbcDialog = new GridBagConstraints();
		gbcDialog.fill = GridBagConstraints.HORIZONTAL;
		gbcDialog.anchor = GridBagConstraints.WEST;
		gbcDialog.ipady = 10;
		gbcDialog.weightx = .5;
		gbcDialog.insets = new Insets(1,2,0,0);
		gbcDialog.gridx = 0;
		gbcDialog.gridy = 0;
		
		GridBagConstraints gbcPanel = new GridBagConstraints();
		gbcPanel.anchor = GridBagConstraints.WEST;
		gbcPanel.weightx = .5;
		gbcPanel.insets = new Insets(1,2,0,0);
		gbcPanel.gridx = 0;
		gbcPanel.gridy = 0;
		
		JPanel pnlGenerate = new JPanel();
		pnlGenerate.setLayout(new GridBagLayout());
		
		JLabel lblCount = new JLabel("Number of Primes to Generate");
		lblCount.setFont(new Font("Tahoma", Font.PLAIN, 12));
		pnlGenerate.add(lblCount, gbcPanel);
		
		JTextField tfCount = new JTextField();
		lblCount.setLabelFor(tfCount);
		tfCount.setColumns(30);
		gbcPanel.gridx = 1;
		pnlGenerate.add(tfCount, gbcPanel);
		
		JLabel lblStart = new JLabel("Starting Number (does not have to be prime)");
		lblStart.setFont(new Font("Tahoma", Font.PLAIN, 12));
		gbcPanel.gridx = 0;
		gbcPanel.gridy = 1;
		pnlGenerate.add(lblStart, gbcPanel);
		
		JTextField tfStart = new JTextField();
		lblStart.setLabelFor(tfStart);
		tfStart.setColumns(30);
		gbcPanel.gridx = 1;
		pnlGenerate.add(tfStart, gbcPanel);
		
		dPrimes.add(pnlGenerate, gbcDialog);
		
		JPanel pnlButtons = new JPanel();
		pnlButtons.setLayout(new GridBagLayout());
		
		// generate button
		JButton btnGeneratePrimes = new JButton("Generate Primes");
		btnGeneratePrimes.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
      	try
      	{
      		BigInteger start = new BigInteger(tfStart.getText());
      		int count = Integer.parseInt(tfCount.getText());
       		m_Primes.generatePrimes(start, count);
       		lblStatus.setText("Status: Excited. Primes have been generated.");
       		updateStats();
      		dPrimes.dispose();
      	}
      	catch(NumberFormatException ex)
      	{
      		lblStatus.setText("You failed to type in an integer successfully. You are terrible at math. Shame.");
      		dPrimes.dispose();
      	}
      	
      }
    });
		gbcPanel.gridx = 0;
		gbcPanel.gridy = 0;
		pnlButtons.add(btnGeneratePrimes, gbcPanel);
		
		// cancel button
		JButton btnCancel = new JButton("Cancel Generation");
		btnCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
      	dPrimes.dispose();
      }
    });
		gbcPanel.anchor = GridBagConstraints.EAST;
		gbcPanel.gridx = 1;
		pnlButtons.add(btnCancel, gbcPanel);		
		
		gbcDialog.gridy = 1;
		dPrimes.add(pnlButtons, gbcDialog);
		
		JPanel pnlStatus = new JPanel();
		pnlStatus.setLayout(new GridBagLayout());

		gbcPanel.anchor = GridBagConstraints.SOUTHWEST;
		gbcPanel.weightx = .5;
		gbcPanel.insets = new Insets(1,2,0,0);
		gbcPanel.gridx = 0;
		gbcPanel.gridy = 1;

		JLabel lblNotice = new JLabel("Warning: This application is single threaded, and will freeze while generating primes.");
		lblNotice.setFont(new Font("Tahoma", Font.PLAIN, 12));
		pnlStatus.add(lblNotice, gbcPanel);
		
		gbcDialog.gridy = 2;
		dPrimes.add(pnlStatus, gbcDialog);
		
		dPrimes.setSize(200,200);
		dPrimes.pack(); // Knowing what this is and why it is needed is important. You should read the documentation on this function!
		dPrimes.setVisible(true);		
	}
	
	// This function updates all the GUI statistics. (# of primes, # of crosses, etc)
	private void updateStats()
	{
		lblPrimeCount.setText("" + m_Primes.primeCount());
		lblCrossCount.setText("" + m_Primes.crossesCount());

		lblLargestPrime.setText("The largest prime has " + m_Primes.sizeofLastPrime() + " digits.");
		lblLargestCross.setText("The largest cross has " + m_Primes.sizeofLastCross().left() + " and " + m_Primes.sizeofLastCross().right() + " digits.");
 	}

}
