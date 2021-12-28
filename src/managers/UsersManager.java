package managers;

import common.interfaces.IUsersManager;
import models.UserModel;

import java.sql.SQLException;
import java.util.ArrayList;

class UsersManager extends Manager implements IUsersManager {

    public UsersManager(DBManager db) {
        super(db);
    }

    @Override
    public ArrayList<UserModel> getUsers() throws SQLException {
        var result = db.getStatement().executeQuery("select id 'ID',first_name 'First name',last_name 'Last name',email 'Email' from users");
        var users = new ArrayList<UserModel>();
        while(result.next()){
            users.add(new UserModel(result.getInt(1)
                    ,result.getString(2),
                    result.getString(3),
                    result.getString(4)));
        }
        return users;
    }

    @Override
    public boolean addUser(UserModel user) throws SQLException {
        try {
                var prs = db.getConnection().prepareStatement("insert into users(first_Name,last_Name,email) values (?,?,?)");
                prs.setString(1, user.getFirstName());
                prs.setString(2, user.getLastName());
                prs.setString(3, user.getEmail());

                int row = prs.executeUpdate();

                if (row == 0){
                    return false;
            }

            return true;
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
            return false;
        }

    }

    @Override
    public boolean updateUser(UserModel user) {
        try {
            var prs = db.getConnection().prepareStatement("Update users set first_Name = ?, last_Name = ? , email = ? where id = ?");
            prs.setString(1,user.getFirstName() );
            prs.setString(2, user.getLastName());
            prs.setString(3, user.getEmail());
            prs.setInt(4, user.getId());
            int row = prs.executeUpdate();


            return true;
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteUser(UserModel user) {
        try {

            var prs = db.getConnection().prepareStatement("delete from users where id = ?");
            prs.setInt(1,user.getId());
            int row = prs.executeUpdate();

           if (row != 1){
               return false;
           }
           return true;
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteUser(int id) {
        try{
        var prs = db.getConnection().prepareStatement("delete from users where id = ?");
        prs.setInt(1,id);
        int row = prs.executeUpdate();

        if (row != 1){
            return false;
        }
        return true;
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }

    @Override
    public UserModel getUserByEmail(String email) {
        try {

            var prs = db.getConnection().prepareStatement("SELECT * FROM `users` WHERE email = ?");
            prs.setString(1,email);
            var rs = prs.executeQuery();
            //exception
            return new UserModel(rs.getInt(1), rs.getString(2) , rs.getString(3), rs.getString(4));
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
            return null;
        }    }

    @Override
    public ArrayList<UserModel> getUsersByName(String name) throws SQLException {
        try {
            var prs = db.getConnection().prepareStatement("select * from users where first_name like ? ");
            prs.setString(1,"%"+name+"%");
            var rs = prs.executeQuery();
            var Ausers = new ArrayList<UserModel>();
            while(rs.next()){
                Ausers.add(new UserModel(rs.getInt(1),rs.getString(2), rs.getString(3),rs.getString(4)));
            }
            return Ausers;
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
            return null;
        }

    }
}
