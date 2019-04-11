package view;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import model.Iztechify;
import model.Song;

@SuppressWarnings("serial")
public class AdminGUI extends JFrame implements ActionListener,Observer {

	private JPanel contentPane;
	private JPanel rightSideOfMainOperationsPane,mainPanel;
	private CardLayout rightSideOfMainCardLayout;
	private JButton btnAddNewSongMain,btnRemoveSongMain,btnEditSongMain;
	private JTable jTableSongs;
	private JLabel labelAlbumName,labelSongName,labelMusicianName,labelSongDuration;
	private JButton btnEditSongInner,btnReturn;
	private Container containerAddNewSong,containerEditSong;
	private JTextField textFieldEditAlbumName,
	textFieldEditMusicianName,textFieldEditSongName,textFieldAddAlbumName
	,textFieldAddMusicianName,textFieldAddSongName,textFieldAddSongDuration;
	private JButton btnAddSongInner;
	private Iztechify iztechifyModel;
	private DefaultTableModel tableModelSongs;
	private String tempEditName;
	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public AdminGUI(Iztechify iztechify) {
		iztechifyModel = iztechify;
		iztechifyModel.addObserver(this);
		
		setTitle("ADMIN SCREEN");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 602, 354);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		createMainPane();
		contentPane.add(mainPanel);
		contentPane.setVisible(true);
		fillTable();
		
	}
	
	private void createMainPane() {
		jTableSongs = new JTable();
		
		JScrollPane scroll  = new JScrollPane(jTableSongs);
		scroll.setBounds(jTableSongs.getBounds());
		
		mainPanel = new JPanel();
		createRightSideOfMainScreenOperationPane();
		
		GroupLayout gl_panelFriend = new GroupLayout(mainPanel);
		gl_panelFriend.setHorizontalGroup(
			gl_panelFriend.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelFriend.createSequentialGroup()
					.addComponent(scroll, GroupLayout.PREFERRED_SIZE, 382, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rightSideOfMainOperationsPane, GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panelFriend.setVerticalGroup(
			gl_panelFriend.createParallelGroup(Alignment.TRAILING)
				.addComponent(rightSideOfMainOperationsPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
				.addComponent(scroll, GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
		);
		mainPanel.setLayout(gl_panelFriend);
	}
	
	//ana ekranýn panelini ve card layout unu oluþturuyor
	//sonra bunlarý ekliyor üstüne her card layoutun içi de ekleniyor
	//diðer methodlar sayesinde
	private void createRightSideOfMainScreenOperationPane() {
		rightSideOfMainOperationsPane = new JPanel();
		rightSideOfMainCardLayout =  new CardLayout();
		rightSideOfMainOperationsPane.setLayout(rightSideOfMainCardLayout);
		rightSideOfMainOperationsPane.add(createRightSideOfMainContainer());
		rightSideOfMainOperationsPane.setBounds(556, 0, 556, 326);
		
		createEditSongContainer();
		rightSideOfMainOperationsPane.add(containerEditSong, "Edit Song Pane");
		
		createAddNewSongContainer();
		rightSideOfMainOperationsPane.add(containerAddNewSong,"Add Song Pane");
	}
	
	//ana ekranýn sað tarafýndaki ekranýn containerý
	private Container createRightSideOfMainContainer() {
		Container container = new Container();
		createRightSideOfMainButton(container);
		return container;
	}
	
	
	private void createEditSongContainer() {
		containerEditSong = new Container();
		containerEditSong.setLayout(null);
		createEditSongElements(containerEditSong);
	}
	
	private void createAddNewSongContainer() {
		containerAddNewSong = new Container();
		containerAddNewSong.setLayout(null);
		createAddNewSongElements(containerAddNewSong);
		
	}
	
	private void createAddNewSongElements(Container con) {
		labelAlbumName = new JLabel("Album :");
		labelAlbumName.setBounds(27, 11, 123, 23);
		textFieldAddAlbumName = new JTextField(10);
		textFieldAddAlbumName.setBounds(27, 30, 123, 23);
		
		labelMusicianName = new JLabel("Musician :");
		labelMusicianName.setBounds(27, 56, 123, 23);
		textFieldAddMusicianName = new JTextField(10);
		textFieldAddMusicianName.setBounds(27, 77, 123, 23);
		
		labelSongName = new JLabel("Song :");
		labelSongName.setBounds(27, 102, 123, 23);
		textFieldAddSongName = new JTextField(10);
		textFieldAddSongName.setBounds(27, 125, 123, 23);
		
		labelSongDuration = new JLabel("Song Duration:");
		labelSongDuration.setBounds(27, 151, 123, 23);
		
		textFieldAddSongDuration = new JTextField(10);
		textFieldAddSongDuration.setBounds(27, 174, 123, 23);
		
		btnAddSongInner = new JButton("Add Song");
		btnAddSongInner.setBounds(27, 208, 123, 23);
		
		btnReturn = new JButton("Return Main");
		
		
		btnAddSongInner.addActionListener(this);
		btnReturn.addActionListener(this);
		
		con.add(labelAlbumName);
		con.add(textFieldAddAlbumName);
		
		con.add(labelMusicianName);
		con.add(textFieldAddMusicianName);
		
		con.add(labelSongName);
		con.add(textFieldAddSongName);
		
		con.add(labelSongDuration);
		con.add(textFieldAddSongDuration);
		
		con.add(btnAddSongInner);
	}
	
	private void createEditSongElements(Container con) {
		labelAlbumName = new JLabel("Album :");
		labelAlbumName.setBounds(27, 11, 123, 23);
		textFieldEditAlbumName = new JTextField(10);
		textFieldEditAlbumName.setBounds(27, 30, 123, 23);
		
		labelMusicianName = new JLabel("Musician :");
		labelMusicianName.setBounds(27, 56, 123, 23);
		textFieldEditMusicianName = new JTextField(10);
		textFieldEditMusicianName.setBounds(27, 77, 123, 23);
		
		labelSongName = new JLabel("Song :");
		labelSongName.setBounds(27, 102, 123, 23);
		textFieldEditSongName = new JTextField(10);
		textFieldEditSongName.setBounds(27, 125, 123, 23);
		
		btnEditSongInner = new JButton("Edit");
		btnEditSongInner.setBounds(27, 159, 123, 23);
		
		btnReturn = new JButton("Return Main");
		
		
		btnEditSongInner.addActionListener(this);
		btnReturn.addActionListener(this);
		
		con.add(labelAlbumName);
		con.add(textFieldEditAlbumName);
		
		con.add(labelMusicianName);
		con.add(textFieldEditMusicianName);
		
		con.add(labelSongName);
		con.add(textFieldEditSongName);
		
		con.add(btnEditSongInner);
		
	}
	
	
	//ana ekranýn butonlarý
	private void createRightSideOfMainButton(Container con) {
		btnAddNewSongMain = new JButton("Add New Song");

		btnAddNewSongMain.setBounds(10, 42, 123, 23);
		btnEditSongMain = new JButton("Edit Song");
		btnEditSongMain.setBounds(10,76,123,23);
		btnRemoveSongMain = new JButton("Remove Song");
		btnRemoveSongMain.setBounds(10, 112, 123, 23);
		btnEditSongMain.addActionListener(this);
		btnRemoveSongMain.addActionListener(this);
		btnAddNewSongMain.addActionListener(this);
		con.add(btnAddNewSongMain);
		con.add(btnEditSongMain);
		con.add(btnRemoveSongMain);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnAddNewSongMain) {
			btnReturn.setBounds(27, 240, 123, 23);
			containerAddNewSong.add(btnReturn);
			rightSideOfMainCardLayout.show(rightSideOfMainOperationsPane, "Add Song Pane");
		}
		if(e.getSource() == btnEditSongMain) {
			int row = jTableSongs.getSelectedRow();
			if(row!=-1) {
				btnReturn.setBounds(27, 193, 123, 23);
				containerEditSong.add(btnReturn);
				tempEditName = jTableSongs.getValueAt(row, 2).toString();
				//musician album song duration
				textFieldEditAlbumName.setText(jTableSongs.getValueAt(row, 1).toString());
				textFieldEditMusicianName.setText(jTableSongs.getValueAt(row, 0).toString());
				textFieldEditSongName.setText(jTableSongs.getValueAt(row, 2).toString());
				rightSideOfMainCardLayout.show(rightSideOfMainOperationsPane, "Edit Song Pane");
			}else {

				JOptionPane.showMessageDialog(this,"Select a song to edit","OPPPS",
						JOptionPane.ERROR_MESSAGE);
			}
			
		}
		if(e.getSource() == btnReturn) {
			rightSideOfMainCardLayout.first(rightSideOfMainOperationsPane);
		}
		
		if(e.getSource() == btnEditSongInner) {
			if(validateEditSongField()) {
				String songName = textFieldEditSongName.getText();
				String albumName = textFieldEditAlbumName.getText();
				String musicianName = textFieldEditMusicianName.getText();
				Song s = iztechifyModel.getSong(tempEditName);
				s.setAlbumName(albumName);
				s.setMusicianName(musicianName);
				s.setName(songName);
		
				iztechifyModel.notifyAllOfThem();
				rightSideOfMainCardLayout.first(rightSideOfMainOperationsPane);
			}else {
			
				JOptionPane.showMessageDialog(this,"Please select a song from table","OPPPS",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		
		if(e.getSource()==btnAddSongInner) {
			if(validateAddSongField()) {	
				String songName = textFieldAddSongName.getText();
				String albumName = textFieldAddAlbumName.getText();
				String musicianName = textFieldAddMusicianName.getText();
				String length = textFieldAddSongDuration.getText();
				Song s = new Song(songName, albumName, musicianName, length);
				iztechifyModel.addSong(s);
				textFieldAddSongName.setText("");
				textFieldAddAlbumName.setText("");
				textFieldAddMusicianName.setText("");
				textFieldAddSongDuration.setText("");
				rightSideOfMainCardLayout.first(rightSideOfMainOperationsPane);
			}else {
			
				JOptionPane.showMessageDialog(this,"All Fields must be filled","OPPPS",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		
		if(e.getSource()==btnRemoveSongMain) {
			int row = jTableSongs.getSelectedRow();
			if(row!=-1) {
				String songName = tableModelSongs.getValueAt(row, 2).toString();
				iztechifyModel.removeSong(songName);
			}else {
				JOptionPane.showMessageDialog(this,"Please select a song from table","OPPPS",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	

	private boolean validateEditSongField() {
		if(!textFieldEditAlbumName.getText().equals("") && !textFieldEditMusicianName.getText().equals("")
				&& !textFieldEditSongName.getText().equals(""))
			return true;
		return false;
	}
	
	private boolean validateAddSongField() {
	
		if(!textFieldAddAlbumName.getText().equals("")
				&& !textFieldAddMusicianName.getText().equals("") && !textFieldAddSongName.getText().equals("")
				&& !textFieldAddSongDuration.getText().equals(""))
			return true;
		return false;
	}
	
	
	private void fillTable() {
		tableModelSongs = new DefaultTableModel();
		tableModelSongs.addColumn("Musician");
		tableModelSongs.addColumn("Album");
		tableModelSongs.addColumn("Song");
		tableModelSongs.addColumn("Length");
		
		for(Song s: iztechifyModel.getAllSongAsList()) {
			tableModelSongs.addRow(new Object[] {s.getMusicianName(),
					s.getAlbumName(),s.getName(),s.getLength()});
		}
		
		jTableSongs.setModel(tableModelSongs);
		
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		fillTable();
	}

}
