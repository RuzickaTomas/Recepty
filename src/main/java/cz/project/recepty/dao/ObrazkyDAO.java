package cz.project.recepty.dao;

import java.util.List;

import cz.project.recepty.beans.Obrazek;
import javax.ejb.Local;

@Local
public interface ObrazkyDAO {

	//insert nebo update podle toho zda m�me id
	public long save(Obrazek obr);
	//z�skej objekt na z�klad� id
	public Obrazek getPicture(long id);
        //ziskej obrazek podle id receptu
        public Obrazek getPictureByReceptId(long id);
	//z�skej v�echny recepty
	public List<Obrazek> getPictures();
        //odstrani obrazek podle id
        public void remove(long id);
}
