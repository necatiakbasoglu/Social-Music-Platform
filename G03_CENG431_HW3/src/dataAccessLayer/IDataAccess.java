package dataAccessLayer;
import java.util.ArrayList;
import java.util.List;

import model.Song;
import model.User;
import model.Iztechify;

public interface IDataAccess {
	public ArrayList<Song> getMusicData(String path);	
	public void getUserData(Iztechify iztechify,String path);
	public void writeMusicData(List<Song> musics, String path);
	public void writeUserData(List<User> users,String path);
}