package com.ensta.librarymanager.dao;

import java.time.LocalDate;
import java.util.List;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.*;
import com.ensta.librarymanager.utils.*;
import com.ensta.librarymanager.dao.*;
import com.ensta.librarymanager.persistence.ConnectionManager;


public class EmpruntDaoImpl implements EmpruntDao {
	private static EmpruntDaoImpl instance;
	private EmpruntDaoImpl() {};
	public static EmpruntDao getInstance() {
		if(instance == null) {
			instance = new EmpruntDaoImpl();
		}
		return instance;
	};

	private static final String SELECT_ALL = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre ORDER BY dateRetour DESC";
	private static final String SELECT_CURRENT = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL";
	private static final String SELECT_CURRENT_BY_MEMBER = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL AND membre.id = ?";
  private static final String SELECT_CURRENT_BY_BOOK = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL AND livre.id = ?";
	private static final String SELECT_BY_ID = "SELECT e.id AS idEmprunt, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE e.id = ?";
	private static final String CREATE = "INSERT INTO emprunt(idMembre, idLivre, dateEmprunt) VALUES (?, ?, ?)";
	private static final String UPDATE = "UPDATE emprunt SET idMembre = ?, idLivre = ?, dateEmprunt = ?, dateRetour = ? WHERE id = ?";
  private static final String COUNT = "SELECT COUNT(id) AS count FROM emprunt";

	MembreDao membreImpl = MembreDaoImpl.getInstance();
	LivreDao livreImpl = LivreDaoImpl.getInstance();

	public List<Emprunt> getList() throws DaoException {
		List<Emprunt> emprunts = new ArrayList<>();
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
			 ResultSet res = preparedStatement.executeQuery(); )
			{
				while(res.next()) {
					Emprunt emprunt = new Emprunt(res.getInt("id"), membreImpl.getById(res.getInt("idMembre")),
																				livreImpl.getById(res.getInt("idLivre")), res.getDate("dateEmprunt").toLocalDate(),
																				res.getDate("dateRetour").toLocalDate() );
					emprunts.add(emprunt);
				}
			}
		catch (SQLException e) {
			throw new DaoException("SELECT ALL failed", e);
		}
		return emprunts;
	};

	public List<Emprunt> getListCurrent() throws DaoException {
    List<Emprunt> emprunts = new ArrayList<>();
		try (Connection connection = ConnectionManager.getConnection();
				 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CURRENT);
				 ResultSet res = preparedStatement.executeQuery(); )
				{
					while(res.next()) {
						Emprunt emprunt = new Emprunt(res.getInt("id"), membreImpl.getById(res.getInt("idMembre")),
																					livreImpl.getById(res.getInt("idLivre")), res.getDate("dateEmprunt").toLocalDate(),
																					res.getDate("dateRetour").toLocalDate());
						emprunts.add(emprunt);
					}
			}
		catch (SQLException e) {
			throw new DaoException("SELECT CURRENT failed", e);
		}
		return emprunts;
	};

	public List<Emprunt> getListCurrentByMembre(int idMember) throws DaoException {
		List<Emprunt> emprunts = new ArrayList<>();
		try {
		 	Connection connection = ConnectionManager.getConnection();
		 	PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CURRENT_BY_MEMBER);
		 	preparedStatement.setInt(1, idMember);
		 	ResultSet res = preparedStatement.executeQuery();
		 	while(res.next()) {
			 	Emprunt emprunt = new Emprunt(res.getInt("id"), membreImpl.getById(res.getInt("idMembre")),
																			livreImpl.getById(res.getInt("idLivre")), res.getDate("dateEmprunt").toLocalDate(), null );
			 	emprunts.add(emprunt);
			}
		}
		catch (SQLException e) {
			throw new DaoException("SELECT CURRENT BY MEMBER failed", e);
		}
		return emprunts;
	};


	public List<Emprunt> getListCurrentByLivre(int idBook) throws DaoException {
		List<Emprunt> emprunts = new ArrayList<>();
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CURRENT_BY_BOOK);
			preparedStatement.setInt(1, idBook);
			ResultSet res = preparedStatement.executeQuery();
			while(res.next()) {
				Emprunt emprunt = new Emprunt(res.getInt("id"), membreImpl.getById(res.getInt("idMembre")),
																			livreImpl.getById(res.getInt("idLivre")), res.getDate("dateEmprunt").toLocalDate(), null);
				emprunts.add(emprunt);
			}
		}
		catch (SQLException e) {
			throw new DaoException("SELECT CURRENT BY BOOK failed", e);
		}
		return emprunts;
	};

	public Emprunt getById(int id) throws DaoException {
		Emprunt emprunt = new Emprunt();
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID);
			preparedStatement.setInt(1, id);
			ResultSet res = preparedStatement.executeQuery();
			if(res.next()) {
				Livre book = new Livre(res.getInt("idLivre"), res.getString("titre"), res.getString("auteur"), res.getString("isbn"));
				Membre member = new Membre(res.getInt("idMembre"), res.getString("nom"), res.getString("prenom"), res.getString("adresse"), res.getString("email"), res.getString("telephone"), Abonnement.valueOf(res.getString("abonnement")));
				emprunt.setId(id);
				emprunt.setMember(member);
				emprunt.setBook(book);
				emprunt.setBorrowDate(res.getDate("dateEmprunt").toLocalDate() );
				Date returnDate = res.getDate("dateRetour");
				if (returnDate != null){
					emprunt.setReturnDate(returnDate.toLocalDate());
				}
			}
		}
		catch (SQLException e) {
			throw new DaoException("SELECT BY ID failed with id = " + id, e);
		}
		return emprunt;
  };


	public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, idMembre);
			preparedStatement.setInt(2, idLivre);
			preparedStatement.setDate(3, Date.valueOf(dateEmprunt) );
			preparedStatement.executeUpdate();
		}
		catch (SQLException e) {
			throw new DaoException("CREATE a new loan failed", e);
		}
	};


	public void update(Emprunt emprunt) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
			preparedStatement.setInt(1, emprunt.getMember().getId());
			preparedStatement.setInt(2, emprunt.getBook().getId());
			preparedStatement.setDate(3, Date.valueOf(emprunt.getBorrowDate()));
			LocalDate returnDate = emprunt.getReturnDate();
			if(returnDate != null) {
				preparedStatement.setDate(4, Date.valueOf(returnDate));
			}
			else {
				preparedStatement.setDate(4, null);
			}
			preparedStatement.setInt(5, emprunt.getId());
			preparedStatement.executeUpdate();

		}
		catch (SQLException e) {
			throw new DaoException("Failed to UPDATE the loan : " + emprunt, e);
		}
	};


	public int count() throws DaoException {
		int counter = 0;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(COUNT);
			ResultSet res = preparedStatement.executeQuery();
			if(res.next()) {
				counter = res.getInt("count");
			}
		}
		catch (SQLException e) {
			throw new DaoException("COUNT in EmpruntDao failed", e);
		}
		return counter;
	};
}
