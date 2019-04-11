package view;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dataAccessLayer.DataAccessJSON;
import dataAccessLayer.IDataAccess;
import model.Admin;
import model.Iztechify;
import model.Song;
import model.User;

import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

@SuppressWarnings("serial")
public class Login extends JFrame implements ActionListener {

	/**
	 * 
	 */
	
	private JPanel contentPane,loginPane,registerPane;
	private JTextField textFieldUserNameLogin;
	private JPasswordField passwordFieldLogin;
	private CardLayout cardLayout;
	private JTextField textFieldNameRegister;
	private JTextField textFieldPasswordRegister;
	private JLabel lblName;
	private JLabel lblPassword_1;
	private JButton btnRegster_1;
	private JButton btnReturnLoginPage;
	private JButton btnLogn;
	private Iztechify iztechify;
	private IDataAccess dataAccess;
	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public Login(Iztechify iztechify) {
		this.iztechify = iztechify;
		dataAccess = new DataAccessJSON();
		
		List<Song> musics = new ArrayList<Song>();
		
		musics.addAll(dataAccess.getMusicData("music.json"));	
		//users.addAll(dal.getUserData("users.json"));
		for(Song song:musics) {
			iztechify.addSong(song);
		}
		
		dataAccess.getUserData(iztechify,"users.json");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				dataAccess.writeUserData(iztechify.getAllUser(), "users.json");
				dataAccess.writeMusicData(iztechify.getAllSongAsList(), "music.json");
			}
		});
		
		setBounds(100, 100, 363, 344);
		contentPane = new JPanel();
		loginPane = new JPanel();
		registerPane = new JPanel();
		cardLayout = new CardLayout();
		contentPane.setLayout(cardLayout);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		createLoginPane();
		createRegisterPane();
		contentPane.add(loginPane,"Login Page");
		contentPane.add(registerPane,"Register Page");
		
		cardLayout.show(contentPane, "Login Page");
		
		
	}
	
	public void createRegisterPane() {
		registerPane.setLayout(null);
		
		textFieldNameRegister = new JTextField();
		textFieldNameRegister.setBounds(139, 61, 129, 20);
		registerPane.add(textFieldNameRegister);
		textFieldNameRegister.setColumns(10);
		
		textFieldPasswordRegister = new JTextField();
		textFieldPasswordRegister.setBounds(139, 105, 129, 20);
		registerPane.add(textFieldPasswordRegister);
		textFieldPasswordRegister.setColumns(10);
		
		lblName = new JLabel("Name:");
		lblName.setBounds(23, 67, 68, 14);
		registerPane.add(lblName);
		
		lblPassword_1 = new JLabel("Password:");
		lblPassword_1.setBounds(23, 108, 68, 14);
		registerPane.add(lblPassword_1);
		
		btnRegster_1 = new JButton("REGISTER");
		btnRegster_1.setBounds(139, 164, 119, 23);
		registerPane.add(btnRegster_1);
		btnRegster_1.addActionListener(this);
		
		btnReturnLoginPage = new JButton("Return Login Page");
		btnReturnLoginPage.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cardLayout.show(contentPane, "Login Page");
			}
		});
		btnReturnLoginPage.setBounds(105, 198, 172, 23);
		registerPane.add(btnReturnLoginPage);
	}
	
	public void createLoginPane() {
		loginPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		loginPane.setLayout(null);
		JLabel lblUserName = new JLabel("User Name:");
		lblUserName.setBounds(42, 47, 100, 24);
		loginPane.add(lblUserName);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(42, 82, 79, 25);
		loginPane.add(lblPassword);
		
		btnLogn = new JButton("LOGIN");
		btnLogn.addActionListener(this);
		btnLogn.setBounds(81, 125, 89, 23);
		loginPane.add(btnLogn);
		
		JButton btnRegster = new JButton("REGISTER");
		btnRegster.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cardLayout.show(contentPane, "Register Page");
			}
		});
		btnRegster.setBounds(186, 125, 115, 23);
		loginPane.add(btnRegster);
		
		
		textFieldUserNameLogin = new JTextField();
		textFieldUserNameLogin.setBounds(131, 49, 144, 20);
		loginPane.add(textFieldUserNameLogin);
		textFieldUserNameLogin.setColumns(10);
		
		passwordFieldLogin = new JPasswordField();
		passwordFieldLogin.setBounds(131, 84, 144, 20);
		loginPane.add(passwordFieldLogin);
		
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnLogn) {
			String userName = textFieldUserNameLogin.getText();
			char[] passChar = passwordFieldLogin.getPassword();
			String password = new String(passChar);
			password.trim();
			if(userName.equals("admin") && password.equals("admin")) {
				new Admin(iztechify);
				AdminGUI adminGUI = new AdminGUI(iztechify);
				adminGUI.setVisible(true);
			}
			else {
				User u = iztechify.verifyUser(userName, password);
				if(u!=null) {
					UserGUI uGUI = new UserGUI(u, iztechify);
					uGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					uGUI.setVisible(true);
				}else {
					JOptionPane.showMessageDialog(this, "User name or Password is wrong!!!!", "Unknown User!!!!!", JOptionPane.ERROR_MESSAGE);
				}
			}
			textFieldUserNameLogin.setText("");
			passwordFieldLogin.setText("");
		}
		
		if(e.getSource()==btnRegster_1) {
			String name = textFieldNameRegister.getText();
			String pass = textFieldPasswordRegister.getText();
			if(!name.equals("") && !pass.equals("")) {
				User u = new User(name, pass);
				iztechify.addUser(u);
				textFieldNameRegister.setText("");
				textFieldPasswordRegister.setText("");
				cardLayout.show(contentPane, "Login Page");
			}else
				JOptionPane.showMessageDialog(this, "Fill all fields", "Null Fields", JOptionPane.ERROR_MESSAGE);
		}
	}
}
