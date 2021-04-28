package br.com.controlecolesterol.dao;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import br.com.controlecolesterol.R;
import br.com.controlecolesterol.model.Alimento;
import br.com.controlecolesterol.model.Categoria;
import br.com.controlecolesterol.model.Medicamento;
import br.com.controlecolesterol.model.Usuario;

@androidx.room.Database(entities = {Alimento.class, Medicamento.class, Usuario.class, Categoria.class},
        version = 5)
public abstract class Database extends RoomDatabase {

    public static final int QUANTIDADE_LIMITE = 5;

    public abstract AlimentoDao alimentoDao();
    public abstract CategoriaDao categoriaDao();
    public abstract MedicamentoDao medicamentoDao();

    public static Database instance;

    public Database(){}

    public static Database getDatabase(Context context){

        if(instance == null){

            synchronized (Database.class){

                if(instance == null){

                    instance = Room.databaseBuilder(context, Database.class, "colesterol.db")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }

        return instance;

    }

    public static void carregaAlimentos(Context context) {

        if(instance.alimentoDao().findAll().isEmpty()){
            int[] id = context.getResources().getIntArray(R.array.id_alim);
            String[] nome = context.getResources().getStringArray(R.array.nome_alim);
            String[] descricao = context.getResources().getStringArray(R.array.descricao);
            String[] consumo = context.getResources().getStringArray(R.array.consumo_diario);
            int[] categoriaId = context.getResources().getIntArray(R.array.categoriasId);

            for (int i = 0; i < QUANTIDADE_LIMITE ; i++){
                Alimento alimento = new Alimento(id[i], nome[i], descricao[i], consumo[i], true, categoriaId[i]);
                instance.alimentoDao().insert(alimento);
            }
        }

    }

    public static void carregaCategoriaPadrao(Context context) {

        if(instance.categoriaDao().findAll().isEmpty()){
            String [] categorias = context.getResources().getStringArray(R.array.categorias);

            for(String c: categorias){
                Categoria categoria = new Categoria(c);
                instance.categoriaDao().insert(categoria);
            }
        }

    }

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }
}
