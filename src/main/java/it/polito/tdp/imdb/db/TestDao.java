package it.polito.tdp.imdb.db;

public class TestDao {

	public static void main(String[] args) {
		TestDao testDao = new TestDao();
		testDao.run();
	}
	
	public void run() {
		ImdbDAO dao = new ImdbDAO();
		System.out.println("Actors:");
		//System.out.println(dao.listAllActors());
		System.out.println("Movies:");
		System.out.println(dao.listAllMovies());
		System.out.println("Directors:");
		System.out.println(dao.listAllDirectors());
	}

}
