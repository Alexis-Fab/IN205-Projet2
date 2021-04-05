package com.ensta.librarymanager.dao;

import java.util.List;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.Livre;

public class LivreDaoImpl implements LivreDao {
	private static LivreDaoImpl instance;
	private LivreDaoImpl() {}
	public static LivreDao getInstance() {
	if(instance == null) {
		instance = new LivreDaoImpl();
	}
	return instance;
	}

	private static final String CREATE_QUERY = "INSERT INTO livre(titre, auteur, isbn) VALUES (?, ?, ?)";
	private static final String SELECT_ONE_QUERY = "SELECT id, titre, auteur, isbn FROM livre WHERE id = ?";
	private static final String SELECT_ALL_QUERY = "SELECT id, titre, auteur, isbn FROM livre";
	private static final String UPDATE_QUERY = "UPDATE livre SET titre = ?, auteur = ?, isbn = ? WHERE id = ?";
	private static final String DELETE_QUERY = "DELETE FROM livre WHERE id = ?";
	private static final String COUNT_QUERY = "SELECT COUNT(id) AS count FROM livre";


	public List<Livre> getList() throws DaoException;
	public Livre getById(int id) throws DaoException;
	public int create(String titre, String auteur, String isbn) throws DaoException;
	public void update(Livre livre) throws DaoException;
	public void delete(int id) throws DaoException;
	public int count() throws DaoException;
}
