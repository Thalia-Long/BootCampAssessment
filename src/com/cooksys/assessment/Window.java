package com.cooksys.assessment;

import java.awt.EventQueue;
import java.awt.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.DefaultListModel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.ListSelectionModel;

import java.awt.Font;
import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.swing.JList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public class Window {

	private JFrame frmPcPartBuilder;
	//Create an instance of computerParts class to hold the data for right panel items
	protected computerParts part = new computerParts();
	// PC parts on the left panel, and selected parts that are chosen by
	// user on the right panel
	DefaultListModel<String> PC_Part, selected_Part;

	/**
	 * Launch the application. The main method is the entry point to a Java
	 * application. For this assessment, you shouldn't have to add anything to
	 * this.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
					window.frmPcPartBuilder.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application. This is the constructor for this Window class.
	 * All of the code here will be executed as soon as a Window object is made.
	 */
	public Window() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame. This is where Window Builder will
	 * generate its code.
	 */
	public void initialize() {
		
		PC_Part = new DefaultListModel<String>();
		selected_Part = new DefaultListModel();

		frmPcPartBuilder = new JFrame();
		frmPcPartBuilder.setFont(new Font("Dialog", Font.BOLD, 12));
		frmPcPartBuilder.setTitle("PC Part Builder");
		frmPcPartBuilder.setBounds(100, 100, 450, 300);
		frmPcPartBuilder.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frmPcPartBuilder.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		mnFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		menuBar.add(mnFile);

		// Load item from the File menu is used to unmarshalled the items in the
		// XML file and display them in the right panel
		JMenuItem mntmLoad = new JMenuItem("Load");
		mntmLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					load();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		mnFile.add(mntmLoad);

		// Save item from the File menu is used to marshal the list of computer
		// parts on the right panel to an XML file
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					save();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		mnFile.add(mntmSave);

		// Exit item from the File menu is used to exit the program
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);
		frmPcPartBuilder.getContentPane().setLayout(null);

		// Left JList contains all the PC parts for the user to select, and add
		// to the right JList
		final JList PC_list = new JList(PC_Part);
		PC_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		PC_list.setVisibleRowCount(7);
		PC_list.setBounds(10, 0, 130, 240);
		frmPcPartBuilder.getContentPane().add(PC_list);

		// Right JList on the right panel containing PC parts chosen by user
		final JList selectedList = new JList(selected_Part);
		PC_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		PC_list.setVisibleRowCount(7);
		selectedList.setBounds(293, 0, 131, 240);
		frmPcPartBuilder.getContentPane().add(selectedList);

		addParts();
		
		// Add Button ">>"
		JButton button = new JButton(">>");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int index = PC_list.getSelectedIndex();
				selected_Part.addElement(PC_Part.get(index));
				PC_Part.remove(index);
			}
		});
		button.setBounds(164, 88, 89, 23);
		frmPcPartBuilder.getContentPane().add(button);

		// Remove Button "<<"
		JButton button_1 = new JButton("<<");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectIndex = selectedList.getSelectedIndex();
				PC_Part.addElement(selected_Part.get(selectIndex));
				selected_Part.remove(selectIndex);
			}
		});
		button_1.setBounds(164, 122, 89, 23);
		frmPcPartBuilder.getContentPane().add(button_1);
	}

	// Converting the list of computer parts in the right panel to an XML file
	public void save() throws Exception {

		int size = selected_Part.size();
		for (int i = 0; i < size; i++) {
			part.getList().add((String) selected_Part.getElementAt(i));
		}
		try {
			File file = new File("HienFile.xml");
			JAXBContext jaxbContext = JAXBContext
					.newInstance(computerParts.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.marshal(part, file);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	// Converting the XML file to java object containing computer parts that
	// were saved from the right panel
	public void load() throws Exception {
		try {
			File file = new File(
					"C:\\Users\\hienl\\Downloads\\BootCampAssessment\\HienFile.xml");
			JAXBContext jaxbContext = JAXBContext
					.newInstance(computerParts.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			computerParts list = (computerParts) jaxbUnmarshaller
					.unmarshal(file);
			System.out.println(list.getList());
			selected_Part.removeAllElements();
			for (String item : list.getList()) {
				selected_Part.addElement(item);
			}
		} catch (JAXBException e3) {
			e3.printStackTrace();
		}
	}

	// Adding the computer parts to the JList on the left panel
	public void addParts() {

		String parts[] = { "Case", "Motherboard", "CPU", "GPU", "PSU", "RAM",
				"HDD" };

		for (int i = 0; i < parts.length; i++) {
			PC_Part.addElement(parts[i]);
		}
	}
}
