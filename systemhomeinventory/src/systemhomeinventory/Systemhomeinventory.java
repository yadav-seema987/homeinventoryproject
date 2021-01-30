package systemhomeinventory;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Date;
import java.beans.PropertyChangeEvent;

public class Systemhomeinventory extends JFrame {

	private JPanel contentPane;
	private JButton photobutton;
	static JTextArea phototextarea;
	private JTextField itemtextfield;
	private JTextField serialtextfield;
	private JTextField pricetextfield;
	private JTextField storetextfield;
	private JTextField notetextfield;
	private JComboBox locationcombobox;
	private JPanel searchpanel;
	private Photopanelclass photopanel=new Photopanelclass();
	private JCheckBox checkbox;
	private JLabel itemlabel,locationlabel,seriallabel,pricelabel,storelabel,notelabel,photolabel,datelabel;
	private JToolBar inventory=new JToolBar();
	private JButton deletebutton;
	private JButton savebutton;
	private JButton previousbutton;
	private JButton nextbutton;
	private JButton exitbutton;
	private JButton printbutton;
	private JButton[] searchbutton=new JButton[26];
	private JButton newbutton;
	private JDateChooser dateChooser;
	private ImageIcon newicon=new ImageIcon("new.png");
	private ImageIcon deleteicon=new ImageIcon("delete.png");
	private ImageIcon saveicon=new ImageIcon("save.png");
	private ImageIcon previousicon=new ImageIcon("previous.png");
	private ImageIcon nexticon=new ImageIcon("next.png");
	private ImageIcon printicon=new ImageIcon("print.png");
	static final int maximumentries=300;
	static Inventoryitem[] myinventory=new Inventoryitem[maximumentries];
	static int currententry;
	static int numberenteries;
	static final int enteriesperpage=2;
	static int lastpage;
	private JTextField textField;
	private JButton btnNewButton;
	private JLabel locationaddlabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Systemhomeinventory frame = new Systemhomeinventory();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Systemhomeinventory() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 558, 339);
		setResizable(false);
		setTitle("Home Inventory System");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		
		inventory.setFloatable(false);
		inventory.setBackground(Color.BLUE);
		inventory.setOrientation(SwingConstants.VERTICAL);
		GridBagConstraints gridconstraint = new GridBagConstraints();
		gridconstraint.insets = new Insets(0, 0, 0, 5);
		gridconstraint.gridx = 0;
		gridconstraint.gridy = 0;
		gridconstraint.gridheight=8;
		gridconstraint.fill=GridBagConstraints.VERTICAL;
		contentPane.add(inventory, gridconstraint);
		
		
		inventory.addSeparator();
		Dimension size=new Dimension(70,50);
		//inventory.addSeparator();
		newbutton = new JButton("New",newicon);
		newbutton.setToolTipText("Add New Item");
		buttonsize(newbutton,size);
		newbutton.setHorizontalTextPosition(SwingConstants.CENTER);
		newbutton.setVerticalTextPosition(SwingConstants.BOTTOM);
		
		newbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newevent();
			}

			private void newevent() {
				// TODO Auto-generated method stub
				checksave();
				blankvalues();
			}

			
					});
		inventory.add(newbutton);

		
		deletebutton = new JButton("Delete",deleteicon);
		deletebutton.setToolTipText("Delete Current Item");
		buttonsize(deletebutton,size);
		deletebutton.setHorizontalTextPosition(SwingConstants.CENTER);
		deletebutton.setVerticalTextPosition(SwingConstants.BOTTOM);
		
		deletebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteevent();
			}

			private void deleteevent() {
				// TODO Auto-generated method stub
				if(JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this item?","Delete Inventory Item",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.NO_OPTION)
				{
					return;
				}
				deleteentry(currententry);
				if(numberenteries==0)
				{
					currententry=0;
					blankvalues();
				}
				else
				{
					currententry--;
					if(currententry==0)
					{
						currententry=1;
					}
					showentry(currententry);
				}
				
			}

		});
		
		inventory.add(deletebutton);
		
		savebutton = new JButton("Save",saveicon);
		buttonsize(savebutton,size);
		savebutton.setToolTipText("Save Current Item");
		savebutton.setHorizontalTextPosition(SwingConstants.CENTER);
		savebutton.setVerticalTextPosition(SwingConstants.BOTTOM);
		
		savebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveevent();
			}

			private void saveevent() {
				// TODO Auto-generated method stub
				itemtextfield.setText(itemtextfield.getText().trim());
				if(itemtextfield.getText().equals(""))
				{
					JOptionPane.showConfirmDialog(null,"Must have item description.","Error",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE);
					itemtextfield.requestFocus();
					return;
				}
				if(newbutton.isEnabled())
				{
					deleteentry(currententry);
				}
				//capitilize first letter
				String s=itemtextfield.getText();
				itemtextfield.setText(s.substring(0,1).toUpperCase()+s.substring(1));
				numberenteries++;
				currententry=1;
				if(numberenteries!=1)
				{
					do {
						if(itemtextfield.getText().compareTo(myinventory[currententry-1].description)<0)
						{
							break;
							
						}	
						currententry++;
						
					}
					while(currententry<numberenteries);	
				}
				if(currententry!=numberenteries)
				{
					for(int i=numberenteries; i>=currententry+1;i--)
					{
						myinventory[i-1]=myinventory[i-2];
						myinventory[i-2]=new Inventoryitem();
					}
				}
				myinventory[currententry-1]=new Inventoryitem();
				myinventory[currententry-1].description=itemtextfield.getText();
				myinventory[currententry-1].location=locationcombobox.getSelectedItem().toString();
				myinventory[currententry-1].marked=checkbox.isSelected();
				myinventory[currententry-1].serialnumber=serialtextfield.getText();
				myinventory[currententry-1].purchaseprice=pricetextfield.getText();
				myinventory[currententry-1].purchasedate=(datetostring(dateChooser.getDate()));
				myinventory[currententry-1].purchaselocation=storetextfield.getText();
				myinventory[currententry-1].photofile=phototextarea.getText();
				myinventory[currententry-1].note=notetextfield.getText();
				showentry(currententry);
				if(numberenteries<maximumentries)
				{
					newbutton.setEnabled(true);
					
				}
				else
				{
					newbutton.setEnabled(false);
					deletebutton.setEnabled(true);
					printbutton.setEnabled(true);
				}
					
			}


		});
		inventory.add(savebutton);
		inventory.addSeparator();
		
		previousbutton = new JButton("Pevious",previousicon);
		buttonsize(previousbutton,size);
		previousbutton.setToolTipText("Display Previous  Item");
		previousbutton.setHorizontalTextPosition(SwingConstants.CENTER);
		previousbutton.setVerticalTextPosition(SwingConstants.BOTTOM);
		
		previousbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				previousevent();
			}

			private void previousevent() {
				// TODO Auto-generated method stub
				checksave();
				currententry--;
				showentry(currententry);
			}
		});
		inventory.add(previousbutton);
		
		nextbutton = new JButton("Next",nexticon);
		buttonsize(nextbutton,size);
		nextbutton.setToolTipText("Display Next Item");
		nextbutton.setHorizontalTextPosition(SwingConstants.CENTER);
		nextbutton.setVerticalTextPosition(SwingConstants.BOTTOM);
		
		nextbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nextevent();
			}

			private void nextevent() {
				// TODO Auto-generated method stub
				checksave();
				currententry++;
				showentry(currententry);
			}
		});
		inventory.add(nextbutton);
		inventory.addSeparator();
		
		printbutton = new JButton("Print",printicon);
		buttonsize(printbutton,size);
		printbutton.setToolTipText("Print Inventory List");
		printbutton.setHorizontalTextPosition(SwingConstants.CENTER);
		printbutton.setVerticalTextPosition(SwingConstants.BOTTOM);
		
		printbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printevent();
			}

			private void printevent() {
				// TODO Auto-generated method stub
				lastpage=(int)(1+(numberenteries-1)/enteriesperpage);
				PrinterJob printerjob=PrinterJob.getPrinterJob();
				printerjob.setPrintable(new Document());
				if(printerjob.printDialog())
				{
					try
					{
						printerjob.print();
					}
					catch(PrinterException ex)
					{
						JOptionPane.showConfirmDialog(null, ex.getMessage(),"Print Error",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE);
					}
				}
				
			}
		});
		inventory.add(printbutton);
		
		exitbutton = new JButton("Exit");
		buttonsize(exitbutton,size);
		exitbutton.setToolTipText("Exit Program");
		exitbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exitevent();
			}

			private void exitevent() {
				// TODO Auto-generated method stub
				exitForm(null);
			}

			private void exitForm(WindowEvent e) {
				// TODO Auto-generated method stub
				if(JOptionPane.showConfirmDialog(null, "Any unused changes will be lost.\nAre you sure to exit?","Exit program",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.NO_OPTION)
				{
					return;
				}
				try {
					PrintWriter outputfile=new PrintWriter(new BufferedWriter(new FileWriter("D:\\Seema\\systemhomeinventory\\inventory.txt")));
					outputfile.println(numberenteries);
					if(numberenteries!=0)
					{
						for(int i=0; i<numberenteries; i++)
						{
							outputfile.println(myinventory[i].description);
							outputfile.println(myinventory[i].location);
							outputfile.println(myinventory[i].serialnumber);
							outputfile.println(myinventory[i].marked);
							outputfile.println(myinventory[i].purchaseprice);
							outputfile.println(myinventory[i].purchasedate);
							outputfile.println(myinventory[i].purchaselocation);
							outputfile.println(myinventory[i].note);
							outputfile.println(myinventory[i].photofile);
						}
					}
					//write combobox entry
					outputfile.println(locationcombobox.getItemCount());
					if(locationcombobox.getItemCount()!=0)
					{
						for(int i=0; i<locationcombobox.getItemCount(); i++)
						{
							outputfile.println(locationcombobox.getItemAt(i));
						}
					}
					outputfile.close();
				}
				catch(Exception ex)
				{
					
				}
			}
		});
		inventory.add(exitbutton);

		
		itemlabel = new JLabel("Inventory Item");
		itemlabel.setFont(new Font("Arial", Font.BOLD, 13));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 0;
		contentPane.add(itemlabel, gbc_lblNewLabel);
		
		itemtextfield = new JTextField();
		itemtextfield.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				itemtextevent();
			}

			private void itemtextevent() {
				// TODO Auto-generated method stub
				locationcombobox.requestFocus();
			}
		});
		itemtextfield.setPreferredSize(new Dimension(400,25));
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.anchor = GridBagConstraints.WEST;
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 0;
		gbc_textField.gridwidth=4;
		contentPane.add(itemtextfield, gbc_textField);
		itemtextfield.setColumns(10);
		
		locationaddlabel = new JLabel("Add Item Location");
		locationaddlabel.setFont(new Font("Arial", Font.BOLD, 13));
		GridBagConstraints gbc_lblNewLabel3 = new GridBagConstraints();
		gbc_lblNewLabel3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel3.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel3.gridx = 6;
		gbc_lblNewLabel3.gridy = 0;
		contentPane.add(locationaddlabel, gbc_lblNewLabel3);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField1 = new GridBagConstraints();
		gbc_textField1.insets = new Insets(0, 0, 5, 0);
		gbc_textField1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField1.gridx = 7;
		gbc_textField1.gridy = 0;
		contentPane.add(textField, gbc_textField1);
		textField.setColumns(10);
		
		locationlabel = new JLabel("Location");
		locationlabel.setFont(new Font("Arial", Font.BOLD, 13));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 1;
		contentPane.add(locationlabel, gbc_lblNewLabel_1);
		
		locationcombobox = new JComboBox();
		locationcombobox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eventlocation();
			}

			private void eventlocation() {
				// TODO Auto-generated method stub
				if(locationcombobox.getItemCount()!=0)
				{
					for(int i=0; i<locationcombobox.getItemCount(); i++)
					{
						if(locationcombobox.getSelectedItem().toString().equals(locationcombobox.getItemAt(i).toString()))
						{
							serialtextfield.requestFocus();
							return;
						}
					}
				}
				
				locationcombobox.addItem(locationcombobox.getSelectedItem());
				
					serialtextfield.requestFocus();
				//	locationcombobox.setEditable(true);
				//locationcombobox.requestFocus();
				
			}
		});
		locationcombobox.setFont(new Font("Arial", Font.PLAIN, 13));
		locationcombobox.setPreferredSize(new Dimension(270,25));
		locationcombobox.setBackground(Color.WHITE);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.anchor=GridBagConstraints.WEST;
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 1;
		gbc_comboBox.gridwidth=3;
		contentPane.add(locationcombobox, gbc_comboBox);
		
		checkbox = new JCheckBox("Marked");
		checkbox.setFont(new Font("Arial", Font.BOLD, 13));
		GridBagConstraints gbc_chckbxNewCheckBox = new GridBagConstraints();
		gbc_chckbxNewCheckBox.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxNewCheckBox.anchor=GridBagConstraints.WEST;
		gbc_chckbxNewCheckBox.gridx = 6;
		gbc_chckbxNewCheckBox.gridy = 1;
		contentPane.add(checkbox, gbc_chckbxNewCheckBox);
		
	btnNewButton = new JButton("Add Loaction");
	btnNewButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			buttonevent();
		}

		private void buttonevent() {
			// TODO Auto-generated method stub
			locationcombobox.addItem(textField.getText());
		}
	});
	GridBagConstraints gbc_btnNewButton2 = new GridBagConstraints();
	gbc_btnNewButton2.insets = new Insets(0, 0, 5, 0);
	gbc_btnNewButton2.gridx = 7;
	gbc_btnNewButton2.gridy = 1;
	contentPane.add(btnNewButton, gbc_btnNewButton2);  
		
		seriallabel  = new JLabel("Serial Number");
		seriallabel.setFont(new Font("Arial", Font.BOLD, 13));
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 1;
		gbc_lblNewLabel_2.gridy = 2;
		contentPane.add(seriallabel, gbc_lblNewLabel_2);
		
		serialtextfield = new JTextField();
		serialtextfield.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				serialevent();
			}

			private void serialevent() {
				// TODO Auto-generated method stub
				pricetextfield.requestFocus();
			}
		});
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.anchor=GridBagConstraints.WEST;
		gbc_textField_1.gridx = 2;
		gbc_textField_1.gridy = 2;
		gbc_textField_1.gridwidth=3;
		contentPane.add(serialtextfield, gbc_textField_1);
		serialtextfield.setColumns(10);
		
		pricelabel = new JLabel("Purchase Price");
		pricelabel.setFont(new Font("Arial", Font.BOLD, 13));
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 1;
		gbc_lblNewLabel_3.gridy = 3;
		contentPane.add(pricelabel, gbc_lblNewLabel_3);
		
		pricetextfield = new JTextField();
		pricetextfield.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				priceevent();
			}

			private void priceevent() {
				// TODO Auto-generated method stub
				dateChooser.requestFocus();
			}
		});
		pricetextfield.setPreferredSize(new Dimension(160,25));
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(0, 0, 5, 5);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.anchor=GridBagConstraints.WEST;
		gbc_textField_2.gridx = 2;
		gbc_textField_2.gridy = 3;
		gbc_textField_2.gridwidth=2;
		contentPane.add(pricetextfield, gbc_textField_2);
		pricetextfield.setColumns(10);
		
		datelabel = new JLabel("Date Purchased");
		datelabel.setFont(new Font("Arial", Font.BOLD, 13));
		GridBagConstraints gbc_lblNewLabel_7 = new GridBagConstraints();
		gbc_lblNewLabel_7.anchor=GridBagConstraints.WEST;
		gbc_lblNewLabel_7.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_7.gridx = 4;
		gbc_lblNewLabel_7.gridy = 3;
		contentPane.add(datelabel, gbc_lblNewLabel_7);
		
		dateChooser = new JDateChooser();
		dateChooser.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				dateevent();
			}

			private void dateevent() {
				// TODO Auto-generated method stub
				//storetextfield.requestFocus();
			}
		});
		dateChooser.setPreferredSize(new Dimension(120,25));
		GridBagConstraints gbc_dateChooser = new GridBagConstraints();
		gbc_dateChooser.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooser.anchor=GridBagConstraints.WEST;
		gbc_dateChooser.fill = GridBagConstraints.BOTH;
		gbc_dateChooser.gridx = 5;
		gbc_dateChooser.gridy = 3;
		gbc_dateChooser.gridwidth=2;
		contentPane.add(dateChooser, gbc_dateChooser);
		
		storelabel = new JLabel("Store/Website");
		storelabel.setFont(new Font("Arial", Font.BOLD, 13));
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 1;
		gbc_lblNewLabel_4.gridy = 4;
		contentPane.add(storelabel, gbc_lblNewLabel_4);
		
		storetextfield = new JTextField();
		storetextfield.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				storeevent();
			}

			private void storeevent() {
				// TODO Auto-generated method stub
				notetextfield.requestFocus();
			}
		});
		storetextfield.setPreferredSize(new Dimension(400,25));
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.insets = new Insets(0, 0, 5, 5);
		gbc_textField_3.anchor=GridBagConstraints.WEST;
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.gridx = 2;
		gbc_textField_3.gridy = 4;
		gbc_textField_3.gridwidth=5;
		contentPane.add(storetextfield, gbc_textField_3);
		storetextfield.setColumns(10);
		
		notelabel = new JLabel("Note");
		notelabel.setFont(new Font("Arial", Font.BOLD, 13));
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5.gridx = 1;
		gbc_lblNewLabel_5.gridy = 5;
		contentPane.add(notelabel, gbc_lblNewLabel_5);
		
		notetextfield = new JTextField();
		notetextfield.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				noteevent();
			}

			private void noteevent() {
				// TODO Auto-generated method stub
				photobutton.requestFocus();
			}
		});
		notetextfield.setPreferredSize(new Dimension(400,25));
		GridBagConstraints gbc_textField_4 = new GridBagConstraints();
		gbc_textField_4.anchor=GridBagConstraints.WEST;
		gbc_textField_4.insets = new Insets(0, 0, 5, 5);
		gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_4.gridx = 2;
		gbc_textField_4.gridy = 5;
		gbc_textField_4.gridwidth=5;
		contentPane.add(notetextfield, gbc_textField_4);
		notetextfield.setColumns(10);
		
		photolabel  = new JLabel("Photo");
		photolabel.setFont(new Font("Arial", Font.BOLD, 13));
		GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
		gbc_lblNewLabel_6.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_6.gridx = 1;
		gbc_lblNewLabel_6.gridy = 6;
		gbc_lblNewLabel_6.anchor=GridBagConstraints.EAST;
		contentPane.add(photolabel, gbc_lblNewLabel_6);
		
		photobutton = new JButton("Choose Photo");
		photobutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				photoevent();
			}

			private void photoevent() {
				// TODO Auto-generated method stub
				JFileChooser openchooser=new JFileChooser();
				openchooser.setDialogType(JFileChooser.OPEN_DIALOG);
				openchooser.setDialogTitle("Open Photo File");
				openchooser.addChoosableFileFilter(new FileNameExtensionFilter("Photo Files","png"));
			if(openchooser.showOpenDialog(openchooser)==JFileChooser.APPROVE_OPTION)
				{
					showphoto(openchooser.getSelectedFile().toString());
				}
				photobutton.requestFocus();
			}
		});
		
		phototextarea = new JTextArea();
		phototextarea.setPreferredSize(new Dimension(350,35));
		phototextarea.setEditable(false);
		phototextarea.setLineWrap(true);
		phototextarea.setBackground(new Color(255,255,192));
		phototextarea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.insets = new Insets(0, 0, 5, 5);
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 2;
		gbc_textArea.gridy = 6;
		gbc_textArea.gridwidth=4;
		contentPane.add(phototextarea, gbc_textArea);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor=GridBagConstraints.WEST;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 7;
		gbc_btnNewButton.gridy = 6;	
		contentPane.add(photobutton, gbc_btnNewButton); 
		
		searchpanel = new JPanel();
		searchpanel.setPreferredSize(new Dimension(240,160));
		searchpanel.setBorder(BorderFactory.createTitledBorder("Item Search"));
		searchpanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.anchor=GridBagConstraints.CENTER;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 7;
		gbc_panel.gridwidth=3;
		contentPane.add(searchpanel, gbc_panel);
		int x=0,y=0;
		for(int i=0; i<26; i++)
		{
			searchbutton[i]=new JButton();
			searchbutton[i].setText(String.valueOf((char)(65+i)));
			searchbutton[i].setFont(new Font("Arial",Font.BOLD,12));
			searchbutton[i].setMargin(new Insets(-10, -10, -10, -10));
			buttonsize(searchbutton[i],new Dimension(37,27));
			searchbutton[i].setBackground(Color.YELLOW);
			GridBagConstraints grid = new GridBagConstraints();
			grid.gridx=x;
			grid.gridy=y;
			searchpanel.add(searchbutton[i],grid);
			searchbutton[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					searchpanelevent(e);
				}

				private void searchpanelevent(ActionEvent e) {
					// TODO Auto-generated method stub
					int i=0;
					if(numberenteries==0)
					{
						return;
					}
					
					//search item by clicking letter
					String letterclick=e.getActionCommand();
					do
					{
						if(myinventory[i].description.substring(0, 1).equals(letterclick))
						{
							currententry=i+1;
							showentry(currententry);
							return;
							
						}
						i++;
					}
				while(i<numberenteries);
					{
						JOptionPane.showConfirmDialog(null, "No "+ letterclick+" inventory items.","None Found",JOptionPane.INFORMATION_MESSAGE);
					}
					
				}				
			});

			x++;
			if(x%6==0)
			{
				x=0;
				y++;
			}
			
		}
		
		photopanel.setPreferredSize(new Dimension(240,160));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 0, 5);
		gbc_panel_1.anchor=GridBagConstraints.CENTER;
		gbc_panel_1.gridx = 4;
		gbc_panel_1.gridy = 7;
		gbc_panel_1.gridwidth=3;
		contentPane.add(photopanel, gbc_panel_1);
		
		
		pack();
		Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((int)(0.5*(screen.width-getWidth())),(int)(0.5*(screen.height-getHeight())),getWidth(),getHeight());
		
		
		int n;
		//open file for enteries
		try
		{
			BufferedReader inputfile=new BufferedReader(new FileReader("D:\\Seema\\systemhomeinventory\\inventory.txt") );
			numberenteries=Integer.valueOf(inputfile.readLine()).intValue();
			if(numberenteries!=0)
			{
				for(int i=0;i<=numberenteries; i++)
				{
					myinventory[i]=new Inventoryitem();
					myinventory[i].description=inputfile.readLine();
					myinventory[i].location=inputfile.readLine();
					myinventory[i].serialnumber=inputfile.readLine();
					myinventory[i].marked=Boolean.valueOf(inputfile.readLine()).booleanValue();    //Boolean.valueOf(   .booleanValue();
					myinventory[i].purchaseprice=inputfile.readLine();
					myinventory[i].purchasedate=inputfile.readLine();
					myinventory[i].purchaselocation=inputfile.readLine();
					myinventory[i].note=inputfile.readLine();
					myinventory[i].photofile=inputfile.readLine();
				}
			}
			
			//read  combobox element
			n=Integer.valueOf(inputfile.readLine()).intValue();
			if(n!=0)
			{
				for(int i=0; i<n; i++)
				{
					locationcombobox.addItem(inputfile.readLine());			
				}
			}
			inputfile.close();
			currententry=1;
			showentry(currententry);
		}
		catch(Exception e)
		{
			numberenteries=0;
			currententry=0;
		}
		if(numberenteries==0)
		{
			newbutton.setEnabled(false);
			deletebutton.setEnabled(false);
			nextbutton.setEnabled(false);
			previousbutton.setEnabled(false);
			printbutton.setEnabled(false);
		}
		
		
	}

	private void buttonsize(JButton jb, Dimension dim) {
		jb.setPreferredSize(dim);
		jb.setMinimumSize(dim);
		jb.setMaximumSize(dim);
	}
	
	private void showentry(int j)
	{
		//display all entery
		itemtextfield.setText(myinventory[j-1].description);
		locationcombobox.setSelectedItem(myinventory[j-1].location);
		checkbox.setSelected(myinventory[j-1].marked);
		serialtextfield.setText(myinventory[j-1].serialnumber);
		pricetextfield.setText(myinventory[j-1].purchaseprice);
		dateChooser.setDate(stringtodate(myinventory[j-1].purchasedate));
		storetextfield.setText(myinventory[j-1].purchaselocation);
		notetextfield.setText(myinventory[j-1].note);
		showphoto(myinventory[j-1].photofile);
		nextbutton.setEnabled(true);
		previousbutton.setEnabled(true);
		if(j==1)
		{
			previousbutton.setEnabled(false);
		}
		if(j==numberenteries)
		{
			nextbutton.setEnabled(false);
		}
		itemtextfield.requestFocus();
		
	}
	
	private void blankvalues() {
		// TODO Auto-generated method stub
		newbutton.setEnabled(false);
		deletebutton.setEnabled(false);
		savebutton.setEnabled(true);
		previousbutton.setEnabled(false);
		nextbutton.setEnabled(false);
		printbutton.setEnabled(false);
		itemtextfield.setText("");
		locationcombobox.setSelectedItem("");
		checkbox.setSelected(false);
		serialtextfield.setText("");
		pricetextfield.setText("");
		dateChooser.setDate(new Date());
		storetextfield.setText("");
		notetextfield.setText("");
		phototextarea.setText("");
		photopanel.repaint();
		itemtextfield.requestFocus();
	}

	private void deleteentry(int j) {
		// TODO Auto-generated method stub
		if(j!=numberenteries)
		{
			for(int i=j; i<numberenteries; i++)
			{
				myinventory[i-1]=new Inventoryitem();
				myinventory[i-1]=myinventory[i];
			}
		}
		numberenteries--;
	}


	private void showphoto(String photofile) {
		// TODO Auto-generated method stub
		if(!photofile.equals(""))
		{
			try {
				phototextarea.setText(photofile);
			}
			catch(Exception e)
			{
				phototextarea.setText("");
			}
		}
		else
		{
			phototextarea.setText("");
		}
		photopanel.repaint();
	}
	
	private void checksave() {
		// TODO Auto-generated method stub
		boolean editable=false;
		if(!myinventory[currententry-1].description.equals(itemtextfield.getText()))
		{
			editable=true;
			
		}
		else if(!myinventory[currententry-1].location.equals(locationcombobox.getSelectedItem()))
		{
			editable=true;
		}
		else if(myinventory[currententry-1].marked!=checkbox.isSelected())
		{
			editable=true;
		}
		else if(!myinventory[currententry-1].serialnumber.equals(serialtextfield.getText()))
		{
			editable=true;
		}
		else if(!myinventory[currententry-1].purchaseprice.equals(pricetextfield.getText()))
		{
			editable=true;
		}
		else if(!myinventory[currententry-1].purchasedate.equals(dateChooser.getDate()))
		{
			editable=true;
		}
		else if(!myinventory[currententry-1].note.equals(notetextfield.getText()))
		{
			editable=true;
		}
		else if(!myinventory[currententry-1].photofile.equals(phototextarea.getText()))
		{
			editable=true;
		}
		
		if(editable)
		{
			if(JOptionPane.showConfirmDialog(null, "You have editaed this item.Do you want this changes?","Save item",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION)
			{
				savebutton.doClick();
			}
		}
		

	}


	   private Date stringtodate(String s) {
		// TODO Auto-generated method stub
		int m=Integer.valueOf(s.substring(0,2)).intValue()-1;
		int d=Integer.valueOf(s.substring(3,5)).intValue();
		int y=Integer.valueOf(s.substring(6)).intValue()-1900;
		return(new Date());
	}
	    private String datetostring(Date date) {
			// TODO Auto-generated method stub
	    	String ystring=String.valueOf(date.getYear()+1900);
	    	int m=date.getMonth()+1;
	    	String mstring=new DecimalFormat("00").format(m);
	    	int d=date.getDate();
	    	String dstring=new DecimalFormat("00").format(d);
			
			return null;
		}
	    
}
 class  Photopanelclass extends JPanel
{
	public void paintcomponet(Graphics g) {
		Graphics2D graphics=(Graphics2D)g;
		super.paint(graphics);
		//add rectangle border to photopanel
		graphics.setPaint(Color.BLACK);
		graphics.draw(new Rectangle2D.Double(0,0,getWidth()-1,getHeight()-1));
		//show photo
		Image photoimg=new ImageIcon(Systemhomeinventory.phototextarea.getText()).getImage();
		int w=getWidth();
		int h=getHeight();
		Double rheight=(double)getHeight()/(double)photoimg.getHeight(null);
		Double rwidth=(double)getWidth()/(double)photoimg.getWidth(null);
		if(rwidth>rheight)
		{
			w=(int)(photoimg.getWidth(null)*rheight);
		}
		else
		{
			h=(int)(photoimg.getHeight(null)*rwidth);
		}
		graphics.drawImage(photoimg,(int)(0.5*(getWidth()-w)),(int)(0.5*(getHeight()-h)),w,h,null);
		graphics.dispose();
			
	}
	
}
 
class Document implements Printable
{

	@Override
	public int print(Graphics g, PageFormat pf, int pageIndex) {
		// TODO Auto-generated method stub
		Graphics2D graphics=(Graphics2D)g;
		if((pageIndex+1)>Systemhomeinventory.lastpage)
		{
			return NO_SUCH_PAGE;
		}
		int i,iend;
		graphics.setFont(new Font("Arial",Font.BOLD,14));
		graphics.drawString("Homa Inventory Items-Page"+String.valueOf(pageIndex+1),(int)pf.getImageableX(),(int)(pf.getImageableY()+25));
		int dy=(int)graphics.getFont().getStringBounds("s", graphics.getFontRenderContext()).getHeight();
		int y=(int)(pf.getImageableY()+4*dy);
		iend=Systemhomeinventory.enteriesperpage*(pageIndex+1);
		if(iend>Systemhomeinventory.numberenteries)
		{
			iend=Systemhomeinventory.numberenteries;
		}
		for(i=0+Systemhomeinventory.enteriesperpage*pageIndex; i<iend; i++)
		{
			Line2D.Double divideline=new Line2D.Double(pf.getImageableX(),y,pf.getImageableX()+pf.getImageableWidth(),y);
			graphics.draw(divideline);
			y+=dy;
			graphics.setFont(new Font("Arial",Font.BOLD,12));
			graphics.drawString(Systemhomeinventory.myinventory[i].description,(int)pf.getImageableX(),y);
			
			y+=dy;
			graphics.setFont(new Font("Arial",Font.BOLD,12));
			graphics.drawString("Location"+Systemhomeinventory.myinventory[i].location,(int)(pf.getImageableX()+25),y);
			
			y+=dy;
			if(Systemhomeinventory.myinventory[i].marked)
			{
				graphics.drawString("Item is marked with identifying information",(int)(pf.getImageableX()+25),y);
			}
			else
			{
				graphics.drawString("Item is Not marked with identifying information",(int)(pf.getImageableX()+25),y);
			}	
			
			y+=dy;
			graphics.drawString("Serial Number:"+Systemhomeinventory.myinventory[i].serialnumber,(int)(pf.getImageableX()+25),y);
			
			y+=dy;
			graphics.drawString("Price:$"+Systemhomeinventory.myinventory[i].purchaseprice+",Purchased on:"+Systemhomeinventory.myinventory[i].purchasedate,(int)(pf.getImageableX()+25),y);
			
			y+=dy;
			graphics.drawString("Purchased at:"+Systemhomeinventory.myinventory[i].purchaselocation,(int)(pf.getImageableX()+25),y);
			
			y+=dy;
			graphics.drawString("Note:"+Systemhomeinventory.myinventory[i].note,(int)(pf.getImageableX()+25),y);
			y+=dy;
			try
			{
				Image inventoryimg=new ImageIcon(Systemhomeinventory.myinventory[i].photofile).getImage();
				double ratio=(double)(inventoryimg.getWidth(null))/(double)inventoryimg.getHeight(null);
				graphics.drawImage(inventoryimg,(int)(pf.getImageableX()+25),y,(int)(100*ratio),100,null);
			}
			catch(Exception ex)
			{
				
			}
			
		}
		return PAGE_EXISTS;
	}
	
}

