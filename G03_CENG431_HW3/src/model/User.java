package model;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class User extends Observable {
	private String name;
	private String password;
	private List<Song> playlist;
	private List<User> friendList;
	
	public User(String name,String password) {
		playlist = new ArrayList<Song>();
		friendList = new ArrayList<User>();
		setName(name);
		setPassword(password);
	}

	public void addMusic(Song song) {
		if(!playlist.contains(song)) {
			playlist.add(song);
			notifyInit();
		}
	}
	
	public void removeMusic(Song song) {
		if(playlist.contains(song)) {
			playlist.remove(song);
			notifyInit();
		}
			
	}
	
	public void removeMusic(String songName) {
		for(Song music : playlist) 
			if(music.getName().equals(songName)) {
				playlist.remove(music);
				notifyInit();
				break;
			}
	}
	
	private void notifyInit() {
		//all notify functions are called here
		setChanged();
		notifyObservers();
		notifyAllFriend();
	}
	
	//when one change has occur then all friends notify their views
	private void notifyAllFriend() {
		for(User u:friendList) {
			u.setChanged();
			u.notifyObservers();
		}
	}
	
//----------------------- friend adding removing operations --------------------		
	public void addFriend(User user) {
		if(!friendList.contains(user)) {
			friendList.add(user);
			user.addFriend(this);
			notifyInit();
		}
	}
	
	public void removeFriend(User user) {
		if(friendList.contains(user)){
			friendList.remove(user);
			notifyInit();		
		}

	}
		
	public boolean removeFriend(String userName) {
		for(User user : friendList) 
			if(user.getName().equals(userName)) {
				friendList.remove(user);
				user.removeFriend(this);	
				notifyInit();
				return true;
			}
		return false;
	}
	
	
	public boolean isSongInPlayList(Song song){	
		return playlist.contains(song);	
	}
	
	public boolean isUserInFriendList(String name){	
		for(User u : friendList) {
			if(u.getName()==name) {
				return true;
			}
		}
		return false;			
	}
	
	public List<Song> getAllSongAsList(){
		return new ArrayList<Song>(playlist);
	}
	
	
	public List<User> getAllFriendAsList(){
		return new ArrayList<User>(friendList);
	}
	
	public User getFriend(String name) {
		for(User u : friendList) {
			if(u.getName()==name)
				return u;
		}
		return null;
	}
	
	public String getName() {return name;}

	public void setName(String name) {this.name = name;}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
