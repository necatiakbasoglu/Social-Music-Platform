package model;

public class Admin {
	private Iztechify iztechify;
	
	public Admin(Iztechify iztechify) {
		this.iztechify = iztechify;
	}
	
	public void removeSong(String name) {
		iztechify.removeSong(name);
	}
	
	public void addSong(String musician,String album,String name,String length) {
		Song s = new Song(name, album, musician, length);
		iztechify.addSong(s);
	}
}
