package dataAccessLayer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.*;
import org.json.simple.parser.*;

import model.Song;
import model.User;
import model.Iztechify;

public class DataAccessJSON implements IDataAccess{
	
	public DataAccessJSON() {
		
	}
	
	public ArrayList<Song> getMusicData(String path) {
		ArrayList<Song> musics = new ArrayList<Song>();
		JSONParser parser = new JSONParser();		
		try {
			JSONArray arr =  (JSONArray) parser.parse(new FileReader(path));
			
			for(Object obj : arr) {
				
				JSONObject musician = (JSONObject) obj;
				
				String musicianName = (String) musician.get("name");
				
				JSONArray albums = (JSONArray) musician.get("albums");
						
				
				for(Object album : albums) {
					JSONObject eachAlbum = (JSONObject) album;
					String albumTitle = (String) eachAlbum.get("title");					
					JSONArray songs = (JSONArray) eachAlbum.get("songs");
					
					for(Object song : songs) {
						JSONObject songObject = (JSONObject) song;
						String songTitle = (String) songObject.get("title");
						String songDuration  = (String) songObject.get("length");
						
						musics.add(new Song(songTitle, albumTitle, musicianName, songDuration));												
					}
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return musics;
	}

	
	@SuppressWarnings("unchecked")
	public void writeMusicData(List<Song> musics, String path) {
		List<String> allMusician = new ArrayList<String>();
		List<String> allAlbums = new ArrayList<String>();
		List<String> allMusics = new ArrayList<String>();
		
		for(Song song : musics) {
			if(!allMusician.contains(song.getMusicianName()))
				allMusician.add(song.getMusicianName());
			if(!allAlbums.contains(song.getAlbumName()))
				allAlbums.add(song.getAlbumName());
			if(!allMusics.contains(song.getName()))
				allMusics.add(song.getName());
		}
		
		JSONArray musicianArrayJson = new JSONArray();
		for(String musicians :allMusician) {
			JSONObject eachMusicianObject = new JSONObject();
			eachMusicianObject.put("name", musicians);
			JSONArray albumsArrayJson = new JSONArray();
			for(String albums : allAlbums) {
				JSONObject eachAlbumsObject = new JSONObject();
				eachAlbumsObject.put("title", albums);
				JSONArray musicsArray = new JSONArray();
				for(String musicsA : allMusics) {
					JSONObject musicObjectJson = new JSONObject();
					for(Song song: musics) {
						if(song.getMusicianName().equals(musicians)
								&& song.getAlbumName().equals(albums)
								&& song.getName().equals(musicsA) && albums!=null) {
							musicObjectJson.put("title", musicsA);
							musicObjectJson.put("length",song.getLength());
							musicsArray.add(musicObjectJson);
						}
					}
				}
				eachAlbumsObject.put("songs",musicsArray);
				albumsArrayJson.add(eachAlbumsObject);
			}
			eachMusicianObject.put("albums", albumsArrayJson);
			musicianArrayJson.add(eachMusicianObject);
		}
		
		FileWriter file;
		try {
			file = new FileWriter(path);
			file.write(musicianArrayJson.toJSONString());
			file.flush();
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getUserData(Iztechify iztechify,String path){
		
		JSONParser parser = new JSONParser();
		try {
			JSONArray userArray =  (JSONArray) parser.parse(new FileReader(path));
			for(Object user: userArray) {
				JSONObject eachUser = (JSONObject) user;
				String name = (String) eachUser.get("username");
				String pass = (String) eachUser.get("password");
				
				User userCreate = new User(name, pass);
				
				JSONArray playList = (JSONArray) eachUser.get("playlist");
				for(Object s : playList) {
					JSONObject songNames= (JSONObject) s;
					String songName = (String) songNames.get("music");
					
					Song song  = iztechify.getSong(songName);
					
					if(song!=null) {
						userCreate.addMusic(song);
					}
						
				}	
				JSONArray friendArray  = (JSONArray) eachUser.get("friendList");
				for(Object u: friendArray ) {
					JSONObject f = (JSONObject) u;
					String friendName = (String) f.get("friend");
					User us = iztechify.getUser(friendName);
					if(us!=null) {
						userCreate.addFriend(us);
					}
				}
				iztechify.addUser(userCreate);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}
	
	@SuppressWarnings("unchecked")
	public void writeUserData(List<User> users,String path) {
		JSONArray userArray = new JSONArray();
		
		for(User u:users) {
			JSONObject user = new JSONObject();
			user.put("username", u.getName());
			user.put("password", u.getPassword());
			JSONArray playlist = new JSONArray();
			for(Song s : u.getAllSongAsList()) {
				JSONObject music = new JSONObject();
				music.put("music", s.getName());
				playlist.add(music);
			}
			
			JSONArray friendList = new JSONArray();
			for(User friend : u.getAllFriendAsList()) {
				JSONObject myFriend = new JSONObject();
				myFriend.put("friend", friend.getName());
				friendList.add(myFriend);
			}
			user.put("playlist", playlist);
			user.put("friendList", friendList);
			userArray.add(user);
		}
		
		FileWriter file;
		try {
			file = new FileWriter(path);
			file.write(userArray.toJSONString());
			file.flush();
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
