package cz.project.recepty.dao;

import java.util.List;

import cz.project.recepty.beans.Obrazek;

public interface ObrazkyDAO {

	//insert nebo update podle toho zda m�me id
	public long save(Obrazek obr);
	//z�skej objekt na z�klad� id
	public Obrazek getPicture(long id);
	//z�skej v�echny recepty
	public List<Obrazek> getPictures();
}
