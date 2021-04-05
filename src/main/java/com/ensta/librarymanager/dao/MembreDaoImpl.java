package com.ensta.librarymanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.dao.MembreDaoImpl;
import com.ensta.librarymanager.model.*;
import com.ensta.librarymanager.utils.*;
import com.ensta.librarymanager.exception.DaoException;

public class MembreDaoImpl implements MembreDao {
	private static MembreDao instance;
	private MembreDaoImpl() {};
	public static MembreDao getInstance() {
		if (instance == null) {
			instance = new MembreDaoImpl();
		}
		return instance;
	}

	private static final String CREATE = "INSERT INTO Membre (nom, prenom, adresse, email, telephone, abonnement) VALUES (?, ?, ?, ?, ?, ?);";
	private static final String SELECT_ONE_BY_ID = "SELECT * FROM Membre WHERE id=?;";
	private static final String SELECT_ALL = "SELECT * FROM Membre;";
	private static final String UPDATE = "UPDATE Membre SET nom=?, prenom=?, adresse=?, email=?, telephone=?, abonnement=? WHERE id=?;";
	private static final String DELETE = "DELETE FROM Membre WHERE id=?;";
	private static final String COUNT = "SELECT COUNT(id) AS count FROM Membre;";


	public List<Membre> getList() throws DaoException {
		List<Membre> membres = new ArrayList<>();
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
			 ResultSet res = preparedStatement.executeQuery(); )
			{
			while(res.next()) {
				Membre member = new Membre(res.getInt("id"), res.getString("nom"), res.getString("prenom"), res.getString("adresse"), res.getString("email"), res.getString("telephone"));
				membres.add(member);
			}
		}
		catch (SQLException e) {
			throw new DaoException("SELECT ALL in MembreDao failed", e);
		}
		return membres;
	};


	public Membre getById(int id) throws DaoException {
		Membre member = new Membre();
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(SELECT_ONE_BY_ID);
			preparedStatement.setInt(1, id);
			ResultSet res = preparedStatement.executeQuery();
			if(res.next()) {
				member.setId(res.getInt("id"));
				member.setSurname(res.getString("nom"));
				member.setForename(res.getString("prenom"));
				member.setMail(res.getString("email"));
				member.setAdress(res.getString("adresse"));
				member.setPhone(res.getString("telephone"));
				member.setSubscription(Abonnement.valueOf(res.getString("abonnement")));
			}
		}
		catch (SQLException e) {
			throw new DaoException("SELECT BY ID in MembreDao failed with id : " + id, e);
		}
		return membre;
	};


	public int create(String nom, String prenom, String adresse, String email, String telephone) throws DaoException {
		int id = -1;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, membre.getSurname());
			preparedStatement.setString(2, membre.getForename());
			preparedStatement.setString(3, membre.getAdress());
			preparedStatement.setString(4, membre.getMail());
			preparedStatement.setString(5, membre.getPhone());
			preparedStatement.setString(6, Abonnement.BASIC.toString());
			preparedStatement.executeUpdate();
			ResultSet res preparedStatement.getGeneratedKeys();
			if (res.next()) {
				id = res.getInt(1);
			}
		}
		catch (SQLException e) {
			throw new DaoException("CREATE a new member failed", e);
		}
		return id;
	};


	public void update(Membre membre) throws DaoException {
		try (
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(UPDATE); )
		{
			preparedStatement.setString(1, membre.getSurname());
			preparedStatement.setString(2, membre.getForename());
			preparedStatement.setString(3, membre.getMail());
			preparedStatement.setString(4, membre.getAdress());
			preparedStatement.setString(5, membre.getPhone());
			preparedStatement.setString(6, membre.getSubscription().toString());
			preparedStatement.setInt(7, membre.getId());
			preparedStatement.executeUpdate();
		}
		catch (SQLException e) {
			throw new DaoException("Failed to UPDATE the member : " + membre, e);
		}
	};


	public void delete(int id) throws DaoException {
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(DELETE);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		}
		catch (SQLException e) {
			throw new DaoException("Failed to DELETE membre with id : " + id, e);
		}
	};

	public int count() throws DaoException {
		try (
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(COUNT);
			ResultSet res = preparedStatement.executeQuery(); )
			{
		int counter = res.getInt(1);
		}
		catch (SQLException e) {
			throw new DaoException("COUNT in MembreDao failed", e);
		}
		return counter;
	};
}
