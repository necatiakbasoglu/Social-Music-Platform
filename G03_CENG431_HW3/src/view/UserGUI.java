package view;

import java.awt.CardLayout;
import java.awt.Container;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.StringTokenizer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import model.Iztechify;
import model.Song;
import model.User;

import javax.swing.JTable;

import javax.swing.JTabbedPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.Color;

@SuppressWarnings("serial")
public class UserGUI extends JFrame implements ActionListener,Observer {

	private JPanel contentPane;
	private JTable mainSongTable,friendsTable,friendsSongsTable;
	private JPanel rightSideOfUserMainOperationsPane,rightSideOfFriendsPane;
	private CardLayout rightSideOfUserMainCardLayout,rightSideOfFriendCardLayout;
	private JButton buttonAddSong,
	buttonRemoveSong,buttonRemoveFriend,
	buttonAddFriend,buttonAddFriendInner,buttonReturnToFriend;
	private Container containerAddSong;
	private Container containerAddFriend;
	private JList<String> jListAllSongs;
	JTabbedPane tabbedPane;
	private JButton buttonSeeFriendsPlaylist;
	private JComboBox<String> comboBoxFriends;
	private JButton buttonAddSongInner;
	private JButton buttonReturnToUserMainOperation;
	private JList<String> jListAllUsers;
	private JPanel rightSideOfFriendsPlaylistPane;
	private User userModel;
	private Iztechify iztechify;
	private DefaultTableModel songTableModelOwn,songTableModelFriend,friendTableModel;
	private DefaultListModel<String> listModelUsers,listModelSongs;
	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserGUI frame = new UserGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	*/
	/**
	 *
	 * Create the frame.
	 */
	public UserGUI(User userModel,Iztechify iztechify) {
		
		this.userModel = userModel;
		this.iztechify = iztechify;
		this.iztechify.addObserver(this);
		this.userModel.addObserver(this);
		setTitle(userModel.getName()+" Screen");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 834, 366);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 818, 326);
		
		contentPane.add(tabbedPane);
		
		
		
		createMainTab();
		createFriendTab();
		createSeeFriendPlaylistTab();
		createListeners();
		fillOwnSongTable();
		fillFrienPlayListTable();
		fillFriendTable();
		fillUserList();
		fillAllSongList();
		
		String[] top3Musician = iztechify.getTop3Musician();
		String[] top3Song = iztechify.getTop3Musics();
		String top3s = "Top 3 Musicians : \n"+top3Musician[0]
				+"\n"+top3Musician[1]+"\n"+top3Musician[2]+"\nTop 3 Songs: "
				+"\n"+top3Song[0]+"\n"+top3Song[1]
				+"\n"+top3Song[2];
		JOptionPane.showMessageDialog(this,top3s , "TOP THREE", JOptionPane.INFORMATION_MESSAGE);
	}
	
	
	//main tab ýnýn  sag tarafýný yaratmak icin
	public void createRightSideOfUserMainScreenOperationPane() {
		rightSideOfUserMainOperationsPane =  new JPanel();
		rightSideOfUserMainCardLayout = new CardLayout();
		rightSideOfUserMainOperationsPane.setLayout(rightSideOfUserMainCardLayout);
		rightSideOfUserMainOperationsPane.add(createRightSideOfUserMainContainer());
		rightSideOfUserMainOperationsPane.setBounds(556, 0,556 , 326);
		createAddSongContainer();
		rightSideOfUserMainOperationsPane.add(containerAddSong, "Add Song Card");
	}
	
	//friend tabanýn sag tarafýný yaratmak ýcýn
	private void createRightSideOfFriendScreenPane() {
		// TODO Auto-generated method stub
		rightSideOfFriendsPane = new JPanel();
		rightSideOfFriendCardLayout = new CardLayout();
		rightSideOfFriendsPane.setLayout(rightSideOfFriendCardLayout);
		rightSideOfFriendsPane.add(createRightSideOfFriendContainer());
		rightSideOfFriendsPane.setBounds(556, 0,556 , 326);
		
		createAddFriendContainer();
		rightSideOfFriendsPane.add(containerAddFriend, "Add Friend Card");
		createRemoveFriendContainer();
	}
	
	private void createRightSideOfSeeFriendPlaylistPane() {
		rightSideOfFriendsPlaylistPane = new JPanel();
		rightSideOfFriendsPlaylistPane.setLayout(null);
		createRightSideOfFriendPlayListButtons();
		rightSideOfFriendsPlaylistPane.add(comboBoxFriends);
		rightSideOfFriendsPlaylistPane.add(buttonSeeFriendsPlaylist);
		//rightSideOfFriendsPlaylistPane.add(createRightSideOfFriendsPlayListContainer());
	}
	
	//friend tabýný yaratýyor her sey methodlara bolunmus durumda
	private void createFriendTab() {
		JPanel friendPanel = new JPanel();
		tabbedPane.addTab("Friends",friendPanel);
		friendsTable = new JTable();
		
		JScrollPane scroll = new JScrollPane(friendsTable);
		scroll.setBounds(friendsTable.getBounds());
		
		
		createRightSideOfFriendScreenPane();
		//group layout yan yana dursun diye table ile friend sag paneli
		GroupLayout gl_panelFriend = new GroupLayout(friendPanel);
		gl_panelFriend.setHorizontalGroup(
				gl_panelFriend.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelFriend.createSequentialGroup()
					.addComponent(scroll, GroupLayout.PREFERRED_SIZE, 518, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rightSideOfFriendsPane, GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panelFriend.setVerticalGroup(
				gl_panelFriend.createParallelGroup(Alignment.TRAILING)
				.addComponent(rightSideOfFriendsPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
				.addComponent(scroll, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
		);
		friendPanel.setLayout(gl_panelFriend);
	}
	
	private void createSeeFriendPlaylistTab() {
		JPanel seeFriendListPanel = new JPanel();
		tabbedPane.add("See Friends PlayList", seeFriendListPanel);
		friendsSongsTable = new JTable();
		
		JScrollPane scroll = new JScrollPane(friendsSongsTable);
		scroll.setBounds(friendsSongsTable.getBounds());
		
		createRightSideOfSeeFriendPlaylistPane();
		
		GroupLayout gl_panelFriendPlayList = new GroupLayout(seeFriendListPanel);
		gl_panelFriendPlayList.setHorizontalGroup(
				gl_panelFriendPlayList.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelFriendPlayList.createSequentialGroup()
					.addComponent(scroll, GroupLayout.PREFERRED_SIZE, 518, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rightSideOfFriendsPlaylistPane, GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panelFriendPlayList.setVerticalGroup(
				gl_panelFriendPlayList.createParallelGroup(Alignment.TRAILING)
				.addComponent(rightSideOfFriendsPlaylistPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
				.addComponent(scroll, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
		);
		seeFriendListPanel.setLayout(gl_panelFriendPlayList);
	}
	
	

	//friend tabýnýn ana ekranýndaký sag panel
	private Container createRightSideOfFriendContainer() {
		Container rightSideFriendContainer = new Container();
		rightSideFriendContainer.setLayout(null);
		
		createRightSideOfFriendButtons();
		
		rightSideFriendContainer.add(buttonAddFriend);
		rightSideFriendContainer.add(buttonRemoveFriend);
		return rightSideFriendContainer;
	}
	
	private void createRightSideOfFriendPlayListButtons() {
		
		buttonSeeFriendsPlaylist = new JButton("See");
		buttonSeeFriendsPlaylist.setBounds(82, 65, 123, 23);
		comboBoxFriends = new JComboBox<>();
		comboBoxFriends.setBounds(82, 30, 123, 23);
		
	}
	
	//friend tabýnda kullanýlan btonlarý yaratýyor
	private void createRightSideOfFriendButtons() {
		// TODO Auto-generated method stub
		buttonAddFriend = new JButton("Add Friend");
		buttonAddFriend.setBounds(82, 30, 123, 23);
		buttonRemoveFriend = new JButton("Remove Friend");
		buttonRemoveFriend.setBounds(82, 65, 123, 23);
		buttonReturnToFriend = new JButton("Return");
		buttonReturnToFriend.setBounds(165, 66, 89, 23);
	}
	
	//add friend butonuna týklayýnca acýlan cardLayout
	private void createAddFriendContainer() {
		containerAddFriend = new Container();
		containerAddFriend.setLayout(null);
		jListAllUsers = new JList<>();
		jListAllUsers.setBounds(0, 21, 145, 266);
		
		JScrollPane scroll = new JScrollPane(jListAllUsers);
		scroll.setBounds(jListAllUsers.getBounds());
		
		buttonAddFriendInner = new JButton("Add");
		buttonAddFriendInner.setBounds(165, 32, 89, 23);
		
		JLabel lblSelectFriend = new JLabel("Select User and click Add Song");
		lblSelectFriend.setForeground(Color.RED);
		lblSelectFriend.setBounds(10, 0, 244, 14);
		
		containerAddFriend.add(lblSelectFriend);
		containerAddFriend.add(scroll);
		containerAddFriend.add(buttonAddFriendInner);
	}
	
	//remove friend butonuna týklayýnca acýlan cardLayout
	private void createRemoveFriendContainer() {
	}
	
	//tablarý yaratmak ýcýn bu main tabý
	private void createMainTab() {
		JPanel panel = new JPanel();
		tabbedPane.addTab("Main", null, panel, null);
		mainSongTable = new JTable();
		
		JScrollPane scroll = new JScrollPane(mainSongTable);
		scroll.setBounds(mainSongTable.getBounds());
		
		createRightSideOfUserMainScreenOperationPane();
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(scroll, GroupLayout.PREFERRED_SIZE, 518, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rightSideOfUserMainOperationsPane, GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addComponent(rightSideOfUserMainOperationsPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
				.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
					.addComponent(scroll, GroupLayout.PREFERRED_SIZE, 299, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
	}
	
	private Container createRightSideOfUserMainContainer() {
		Container rightSideMainContainer = new Container();
		rightSideMainContainer.setLayout(null);
		
		createRightSideOfUserMainButtons();
		
		rightSideMainContainer.add(buttonAddSong);
		rightSideMainContainer.add(buttonRemoveSong);
		
		return rightSideMainContainer;
	}
	
	private void createRightSideOfUserMainButtons() {
		buttonAddSong = new JButton("Add Song");
		buttonAddSong.setBounds(66, 64, 123, 23);
		buttonRemoveSong = new JButton("Remove Song");
		buttonRemoveSong.setBounds(66, 98, 123, 23);
		buttonReturnToUserMainOperation = new JButton("Return");
		buttonReturnToUserMainOperation.setBounds(165, 66, 89, 23);
	}
	
	private void createAddSongContainer() {
		containerAddSong = new Container();
		containerAddSong.setLayout(null);
		
		
		jListAllSongs = new JList<>();
		//listModelSongs = new DefaultListModel<>();
		jListAllSongs.setBounds(0, 21, 145, 266);
		
		JScrollPane scroll = new JScrollPane(jListAllSongs);
		scroll.setBounds(jListAllSongs.getBounds());
		
		buttonAddSongInner = new JButton("Add");
		buttonAddSongInner.setBounds(165, 32, 89, 23);
		
		JLabel lblSelectSongsAnd = new JLabel("Select Songs and click Add Song");
		lblSelectSongsAnd.setForeground(Color.RED);
		lblSelectSongsAnd.setBounds(10, 0, 244, 14);
		
		containerAddSong.add(buttonAddSongInner);
		containerAddSong.add(lblSelectSongsAnd);
		containerAddSong.add(scroll);
		
		
	}
	

	private void createListeners() {
		buttonAddSong.addActionListener(this);
		buttonReturnToUserMainOperation.addActionListener(this);
		buttonRemoveSong.addActionListener(this);
		buttonAddFriend.addActionListener(this);
		buttonRemoveFriend.addActionListener(this);
		buttonReturnToFriend.addActionListener(this);
		buttonAddFriendInner.addActionListener(this);
		buttonAddSongInner.addActionListener(this);
		buttonSeeFriendsPlaylist.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==buttonReturnToFriend) {
			rightSideOfFriendCardLayout.first(rightSideOfFriendsPane);
		}
		
		if(e.getSource()==buttonAddFriend) {
			containerAddFriend.add(buttonReturnToFriend);
			rightSideOfFriendCardLayout.show(rightSideOfFriendsPane, "Add Friend Card");
		}
		
		if(e.getSource()==buttonRemoveFriend) {
			int row = friendsTable.getSelectedRow();
			if(row!=-1) {
				String selectedUser = (String) friendTableModel.getValueAt(row, 0);
				 userModel.removeFriend(selectedUser);
			}
			else
				JOptionPane.showMessageDialog(this,"Please select a row on table before press remove","OPPPS",
						JOptionPane.ERROR_MESSAGE);
		}
		
		if(e.getSource()==buttonRemoveSong) {
			int row = mainSongTable.getSelectedRow();
			if(row!=-1) {
				userModel.removeMusic(mainSongTable.getValueAt(row,2).toString());
			}else {
				JOptionPane.showMessageDialog(this,"Please select a row on table before press remove","OPPPS",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		if(e.getSource()==buttonReturnToUserMainOperation) {
			rightSideOfUserMainCardLayout.first(rightSideOfUserMainOperationsPane);
		}
		
		if(e.getSource()==buttonAddSong){
			containerAddSong.add(buttonReturnToUserMainOperation);
			rightSideOfUserMainCardLayout.show(rightSideOfUserMainOperationsPane, "Add Song Card");
			
		}
		if(e.getSource()== buttonAddFriendInner) {
				String selectedUser = jListAllUsers.getSelectedValue();
				if(selectedUser != null) {
					User u = iztechify.getUser(selectedUser);
					userModel.addFriend(u);
					rightSideOfFriendCardLayout.first(rightSideOfFriendsPane);
				}
				else
					JOptionPane.showMessageDialog(this,"Please select a row on list before press add","OPPPS",
						JOptionPane.ERROR_MESSAGE);
			
			
		}
		
		if(e.getSource()==buttonAddSongInner) {
			String selectedSong = jListAllSongs.getSelectedValue();
			StringTokenizer token  =new StringTokenizer(selectedSong, "-");
			if(selectedSong != null) {
				Song s = iztechify.getSong(token.nextToken().trim());
				System.out.println(s.getAlbumName()+" "+s.getName());
				userModel.addMusic(s);
				
			}
			else
				JOptionPane.showMessageDialog(this,"Please select a row on list before press add","OPPPS",
					JOptionPane.ERROR_MESSAGE);
		}
		
		if(e.getSource()==buttonSeeFriendsPlaylist) {
			fillFrienPlayListTable();
		}
	}
	
	private void fillOwnSongTable() {
	
		songTableModelOwn = new DefaultTableModel();
		
		songTableModelOwn.addColumn("Musician");
		songTableModelOwn.addColumn("Album");
		songTableModelOwn.addColumn("Name");
		songTableModelOwn.addColumn("Length");
		
		if(userModel.getAllSongAsList().size()>0) {
			for(Song s : userModel.getAllSongAsList()) {
					songTableModelOwn.addRow(new Object[] {s.getMusicianName(),s.getAlbumName(),
							s.getName(),s.getLength()});
			}
		}
		
		
		mainSongTable.setModel(songTableModelOwn);
	}
	
	private void fillFriendTable() {
		comboBoxFriends.removeAllItems();
		friendTableModel = new DefaultTableModel();
		
		friendTableModel.addColumn("Friend Name");
		
		for(User u : userModel.getAllFriendAsList()) {
			friendTableModel.addRow(new Object[] {u.getName()});
			comboBoxFriends.addItem(u.getName());
		}
		friendsTable.setModel(friendTableModel);	
	}
	
	
	
	private void fillFrienPlayListTable() {
		songTableModelFriend = new DefaultTableModel();
		String friendName = (String) comboBoxFriends.getSelectedItem();
		User friend = userModel.getFriend(friendName);
		songTableModelFriend.addColumn("Musician");
		songTableModelFriend.addColumn("Album");
		songTableModelFriend.addColumn("Name");
		songTableModelFriend.addColumn("Length");
		if(friend!=null) {
			for(Song s : friend.getAllSongAsList()) {
				songTableModelFriend.addRow(new Object[] {s.getMusicianName(),s.getAlbumName(),
					s.getName(),s.getLength()});
			}
		}
		friendsSongsTable.setModel(songTableModelFriend);
	}	
	
	private void fillUserList() {
		listModelUsers = new DefaultListModel<>();
		
		List<User> allUser = iztechify.getAllUser();
		if(allUser.size()>0) {
			for(User user : allUser) {
				if(user.getName()!=userModel.getName() && !userModel.isUserInFriendList(user.getName())) {
					listModelUsers.addElement(user.getName());
				}
			}
			jListAllUsers.setModel(listModelUsers);			
		}
		

	}
	
	private void fillAllSongList() {
		listModelSongs = new DefaultListModel<>();
		
		List<Song> allSong = iztechify.getAllSongAsList();
		
		for(Song s : allSong) {
			if(!userModel.isSongInPlayList(s))
				listModelSongs.addElement(s.getName()+" - "+s.getLength());
		}
		jListAllSongs.setModel(listModelSongs);
	}
	

	
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		fillOwnSongTable();
		fillFrienPlayListTable();
		fillFriendTable();
		fillUserList();
		fillAllSongList();
		
	}
}
