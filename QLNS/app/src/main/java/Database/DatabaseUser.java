package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class DatabaseUser extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ManagerStudent.db";
    private static final String TABLE_NAME = "User";
    private static final String ID = "ID";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private final Context context;
    SQLiteDatabase db;

    private static final String DATABASE_PATH = "/data/data/eventhandling.tensv.qlnv/databases";


    public DatabaseUser(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
        createDb();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE User(\"ID\" INTEGER NOT NULL, " +
                                        "\"username\" TEXT NOT NULL, " +
                                        "\"password\" TEXT NOT NULL, " +
                                        "PRIMARY KEY(\"ID\"AUTOINCREMENT))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_NAME);
        onCreate(db);
    }

    public void createDb(){
        boolean dbExist = checkDbExist();

        if(!dbExist){
            this.getReadableDatabase();
            copyDatabase();
        }
    }

    private boolean checkDbExist(){
        SQLiteDatabase sqLiteDatabase = null;
    // ni là tìm file DB của mi
        try{
            String path = DATABASE_PATH + DATABASE_NAME;
            sqLiteDatabase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        } catch (Exception ex){
        }

        if(sqLiteDatabase != null){
            sqLiteDatabase.close();
            return true;
        }

        return false;
    }

    private void copyDatabase(){
        // ni là kết nối
        try {
            InputStream inputStream = context.getAssets().open(DATABASE_NAME);

            String outFileName = DATABASE_PATH + DATABASE_NAME;

            OutputStream outputStream = new FileOutputStream(outFileName);

            byte[] b = new byte[1024];
            int length;

            while ((length = inputStream.read(b)) > 0){
                outputStream.write(b, 0, length);
            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private SQLiteDatabase openDatabase(){
        // đây cx kết nối
        String path = DATABASE_PATH + DATABASE_NAME;
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        return db;
    }

    public void close(){
        if(db != null){
            db.close();
        }
    }
    // Insert choi oi met lam biet ko
    public Boolean insertData(String username, String password){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues =new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = myDB.insert("User",null,contentValues);
        if(result == -1) {
            return false;

        }
        else {
            return true;

        }
    }
    // check ni ma thi xong xem phim
    public Boolean checkusername(String username){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from User where username =?", new String[]{username});
        if (cursor.getCount()>0){
            return true;
        }
        else{
            return false;
        }
    }
    //Kiem tra user va pass kiemtra tâttata
//    public Boolean checkusernamePassword(String username, String password){
//        SQLiteDatabase myDB = this.getWritableDatabase();
//        Cursor cursor = myDB.rawQuery("select * from User where username =? and password =?", new String[]{username});
//        if (cursor.getCount()>0){
//            return true;
//        }
//        else{
//            return false;
//        }
//    }
//    public Boolean kiemtraUserPass(String username, String password){
//        String[] columns = {"username"};
//        db = openDatabase();
//        String selection = "username=? and password = ?";
//        String[] selectionArgs = {username, password};
//        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
//        int count = cursor.getCount();
//        cursor.close();
//        close();
//        if(count > 0){
//            return true;
//        } else {
//            return false;
//        }
//    }
}



