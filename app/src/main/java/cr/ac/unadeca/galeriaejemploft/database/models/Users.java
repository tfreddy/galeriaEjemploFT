package cr.ac.unadeca.galeriaejemploft.database.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import cr.ac.unadeca.galeriaejemploft.database.GaleriaDB;

/**
 * Created by Freddy on 4/1/2018.
 */

@Table(database = GaleriaDB.class)
public class Users extends BaseModel {

    @Column
    @PrimaryKey(autoincrement = true)
    public long id;

    @Column
    public String username;

    @Column
    public String password;

    @Column
    public String nombre;

    @Column
    public String roll;
}
