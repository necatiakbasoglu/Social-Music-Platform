package iztechifyAPP;

import model.Iztechify;
import view.Login;

public class IztechifyAPP {
	
	public static void main(String args[]) {
		Iztechify iztechify = new Iztechify();
		Login login = new Login(iztechify);
		login.setVisible(true);
	}
}
