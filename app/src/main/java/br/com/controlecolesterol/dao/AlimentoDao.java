package br.com.controlecolesterol.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.controlecolesterol.model.Alimento;

@Dao
public interface AlimentoDao {

    @Insert
    long insert(Alimento alimento);

    @Delete
    void delete(Alimento alimento);

    @Update
    void update(Alimento alimento);

    @Query("SELECT * FROM Alimento WHERE id = :id")
    Alimento findById(long id);

    @Query("SELECT * FROM Alimento ORDER BY nome ASC")
    List<Alimento> findAll();

    @Query("SELECT count(*) FROM Alimento WHERE categoriaId = :idCategoria ORDER BY nome ASC")
    int countAllByCategoriaId(int idCategoria);
}
