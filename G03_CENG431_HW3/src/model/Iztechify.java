package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

public class Iztechify extends Observable{
	private ArrayList<Song> musics;
	private ArrayList<User> users;
	private Admin admin;
	
	public Iztechify() {
		musics = new ArrayList<Song>();	
		users = new ArrayList<User>();
	}
	
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	
	public Admin getAdmin() {
		return admin;
	}
	
	public String[] getTop3Musics(){
		String[] topThree = {" "," "," "};
		Map<Song,Integer> amountsOfMusics = new HashMap<Song,Integer>();
		Integer[] amounts = new Integer[musics.size()];
		int amount=0;
		boolean isThereMusic = false;
		for(int i=0; i<musics.size();i++) { 
			amount=0;
			for(User user : users) {
				if(user.isSongInPlayList(musics.get(i))) {
					amount += 1;
					isThereMusic = true;
				}
			}
			amountsOfMusics.put(musics.get(i), amount);
			amounts[i]=amount;
		}
		
		if(isThereMusic) {
			Arrays.sort(amounts);
			for(int i=0; i<3;i++) 
				for(Song s : amountsOfMusics.keySet()) 
					if(amounts[musics.size()-i-1]>0)
						if(amountsOfMusics.get(s)==amounts[amounts.length-i-1])
						{
							topThree[i]=s.getName()+" - "+s.getLength();
							amountsOfMusics.put(s, 0);
							break;
						}
										
		}

		return topThree;
	}
	
	public String[] getTop3Musician() {
		String[] topThree = {" "," "," "};
		Map<String, Integer> musicians = new HashMap<String,Integer>();
	
		for(Song s: musics) {
			if(!musicians.containsKey(s.getMusicianName()))
				musicians.put(s.getMusicianName(),0);
		}
		Integer amount=new Integer(0);
		for(Song s : musics) {
			for(User user : users) {
				if(user.isSongInPlayList(s)) {
					amount = musicians.get(s.getMusicianName());
					musicians.put(s.getMusicianName(),++amount);
				}	
			}
		}
		
		List<Integer> mapValuesForSorting = new ArrayList<>(musicians.values());
		Collections.sort(mapValuesForSorting);
		Integer[] num = new Integer[musicians.size()];
		
		for(int i=0; i<3;i++) 
			for(String s : musicians.keySet()) 
				if(mapValuesForSorting.get(musicians.size()-i-1)>0)
					if(musicians.get(s)==mapValuesForSorting.get(num.length-i-1))
					{
						topThree[i]=s;
						musicians.put(s, 0);
						break;
					}
									
	

	
		
	
		return topThree;
	}

	
	
	public Song getSong(String name) {
		for(Song music : musics) 
			if(music.getName().equals(name))
				return music;				
		return null;
	}
	
	
	public void notifyAllOfThem() {
		setChanged();
		notifyObservers();
	}
	public void addSong(Song song) {
		if(!musics.contains(song)) {
			musics.add(song);
			notifyAllOfThem();
		} 
			 
	}
	
	
	public User getUser(String userName) {
		for(User user : users) 
			if(user.getName().equals(userName))
				return user;
		return null;
	}
	

	
	public List<User> getAllUser(){
		return new ArrayList<User>(users);
	}
	
	public User verifyUser(String userName,String password) {
		for(User u : users) {
			if(u.getName().equals(userName) && u.getPassword().equals(password)) {
				return u;
			}
		}
		return null;
	}
	
	public List<Song> getAllSongAsList(){
		return new ArrayList<Song>(musics);
	}
	
	public void removeSong(String songName) {
		boolean flag = false;
		for(Song s :musics) {
			if(s.getName() == songName) {
				for(User u: users) {				
					u.removeMusic(s);
					musics.remove(s);
					notifyAllOfThem();
					flag =true;
				}
			}
			if(flag) {
				break;
			}
			
		}
	}
	
	public void addUser(User user) {
		if(!users.contains(user)) {
			users.add(user);
			notifyAllOfThem();
		}
	}
}
