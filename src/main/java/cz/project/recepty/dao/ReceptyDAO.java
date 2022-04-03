package cz.project.recepty.dao;

import java.util.List;

import cz.project.recepty.beans.Recept;

public interface ReceptyDAO {

	//insert nebo update podle toho zda m�me id
	public boolean save(Recept recept);
	//z�skej objekt na z�klad� id
	public Recept getRecept(long id);
	//z�skej v�echny recepty
	public List<Recept> getRecepts();
}
