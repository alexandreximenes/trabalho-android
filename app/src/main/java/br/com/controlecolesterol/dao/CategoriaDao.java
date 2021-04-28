package br.com.controlecolesterol.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.controlecolesterol.model.Categoria;

@Dao
public interface CategoriaDao {

    @Insert
    long insert(Categoria categoria);

    @Delete
    void delete(Categoria categoria);

    @Update
    void update(Categoria categoria);

    @Query("SELECT * FROM Categoria WHERE id = :id")
    Categoria findById(long id);

    @Query("SELECT * FROM Categoria WHERE descricao = :descricao")
    Categoria findByDescricao(String descricao);

    @Query("SELECT * FROM Categoria ORDER BY descricao ASC")
    List<Categoria> findAll();

    @Query("SELECT COUNT(*) FROM Categoria")
    int total();
}
