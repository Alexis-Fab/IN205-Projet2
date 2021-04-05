package com.ensta.librarymanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.dao.LivreDao;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.*;
import com.ensta.librarymanager.utils.*;

public class LivreDaoImpl implements LivreDao {
	private static LivreDaoImpl instance;
	private LivreDaoImpl() {};
	public static LivreDao getInstance() {
	if(instance == null) {
		instance = new LivreDaoImpl();
	}
	return instance;
	}

	private static final String SELECT_ALL = "SELECT id, titre, auteur, isbn FROM livre";
	private static final String SELECT_ONE_BY_ID = "SELECT id, titre, auteur, isbn FROM livre WHERE id = ?";
	private static final String CREATE = "INSERT INTO livre(titre, auteur, isbn) VALUES (?, ?, ?)";
	private static final String UPDATE = "UPDATE livre SET titre = ?, auteur = ?, isbn = ? WHERE id = ?";
	private static final String DELETE = "DELETE FROM livre WHERE id = ?";
	private static final String COUNT = "SELECT COUNT(id) AS count FROM livre";


	public List<Livre> getList() throws DaoException;
		List<Livre> livres = new ArrayList<>();
		try (Connection connection = EstablishConnection.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
			ResultSet res = preparedStatement.executeQuery(); )
			{
			while(res.next()) {
				Livre book = new Livre(res.getInt("id"), res.getString("titre"), res.getString("auteur"), res.getString("isbn"));
				livres.add(book);
			}
		}
		catch (SQLException e) {
			throw new DaoException("SELECT ALL failed in LivreDao", e);
		}
		return livres;
	};


	public Livre getById(int id) throws DaoException;
		Livre book = new Livre();
		try {
			Connection connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(SELECT_ONE_BY_ID);
			preparedStatement.setInt(1, id);
			ResultSet res = preparedStatement.executeQuery();
			if (res.next()) {
				book.setId(res.getInt("id"));
				book.setTitle(res.getString("titre"));
				book.setAuthor(res.getString("auteur"));
				book.setIsbn(res.getString("isbn"));
			}
		}
		catch (SQLException e) {
			throw new DaoException("Probl�me lors de la r�cup�ration du livre: id=" + id, e);
		}
		return book;
	};


	public int create(String titre, String auteur, String isbn) throws DaoException {
		int id = -1;
		try {
			Connection connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, titre);
			preparedStatement.setString(2, auteur);
			preparedStatement.setString(3, isbn);
			preparedStatement.executeUpdate();
			ResultSet res = preparedStatement.getGeneratedKeys();
			if (res.next()) {
				id = res.getInt(1);
			}
		}
		catch (SQLException e) {
			throw new DaoException("CREATE a new book failed", e);
		}
		return id;
	};


	public void update(Livre livre) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(UPDATE);
			preparedStatement.setString(1, livre.getTitle());
			preparedStatement.setString(2, livre.getAuthor());
			preparedStatement.setString(3, livre.getIsbn());
			preparedStatement.setInt(4, livre.getId());
			preparedStatement.executeUpdate();
		}
		catch (SQLException e) {
			throw new DaoException("Failed to UPDATE the book : " + livre, e);
		}
	};


	public void delete(int id) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(DELETE);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		}
		catch (SQLException e) {
			throw new DaoException("PFailed to DELETE the book : " + id, e);
		}
	};


	public int count() throws DaoException {
		try ( Connection connection = ConnectionManager.getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement(COUNT);
					ResultSet res = preparedStatement.executeQuery(); )
		{
		int counter = res.getInt(1);
		}
		catch (SQLException e) {
			throw new DaoException("COUNT in LivreDao failed", e);
		}
		return counter;
	};
}
