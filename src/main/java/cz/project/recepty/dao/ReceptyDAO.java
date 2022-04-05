package cz.project.recepty.dao;

import java.util.List;

import cz.project.recepty.beans.Recept;

public interface ReceptyDAO {

	//insert nebo update podle toho zda m�me id
	public long save(Recept recept);
	//z�skej objekt na z�klad� id
	public Recept getRecept(long id);
	//z�skej v�echny recepty
	public List<Recept> getRecepts();
        //odstrani recept podle id
        public void remove(long id);
}
