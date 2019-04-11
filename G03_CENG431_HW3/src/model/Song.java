package model;


public class Song {
	private String albumName, musicianName, name, length;
	
	
	public Song(String songName, String albumName, String musicianName, String length) {
		setAlbumName(albumName);
		setMusicianName(musicianName);
		setName(songName);
		setLength(length);
	}
	

	
	public String getAlbumName() {
		return albumName;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	public String getMusicianName() {
		return musicianName;
	}

	public void setMusicianName(String musicianName) {
		this.musicianName = musicianName;
	}

	public String getName() {
		return name;
	}

	public void setName(String musicName) {
		this.name = musicName;
	}



	public String getLength() {
		return length;
	}



	public void setLength(String length) {
		this.length = length;
	}

}
