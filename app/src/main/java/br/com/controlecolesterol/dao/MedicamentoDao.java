package br.com.controlecolesterol.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.controlecolesterol.model.Medicamento;

@Dao
public interface MedicamentoDao {

    @Insert
    long insert(Medicamento medicamento);

    @Delete
    void delete(Medicamento medicamento);

    @Update
    void update(Medicamento medicamento);

    @Query("SELECT * FROM Medicamento WHERE id = :id")
    Medicamento findById(long id);

    @Query("SELECT * FROM Medicamento WHERE nome = :nome")
    Medicamento findByNome(String nome);

    @Query("SELECT * FROM Medicamento ORDER BY nome ASC")
    List<Medicamento> findAll();

    @Query("SELECT COUNT(*) FROM Medicamento")
    int total();
}
