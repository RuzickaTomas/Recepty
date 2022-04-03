package cz.project.recepty.dao;

import java.util.List;

import cz.project.recepty.beans.Recept;

public interface ReceptyDAO {

	//insert nebo update podle toho zda máme id
	public boolean save(Recept recept);
	//získej objekt na základì id
	public Recept getRecept(long id);
	//získej všechny recepty
	public List<Recept> getRecepts();
}
