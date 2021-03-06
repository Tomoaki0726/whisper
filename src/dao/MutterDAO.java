package dao;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Mutter;

public class MutterDAO {

    private static Connection getConnection() throws URISyntaxException, SQLException {
            // heroku configに設定されている値を取得。
        URI dbUri = new URI(System.getenv("CLEARDB_DATABASE_URL"));
        // :をデリミタとして必要な情報を抜き取る。
        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        // JDBC用のURLを生成。
        String dbUrl = "jdbc:mysql://" + dbUri.getHost() + dbUri.getPath()+"?useUnicode=true&characterEncoding=utf8";

        return DriverManager.getConnection(dbUrl, username, password);
    }



  //レコードを取得するメソッド
    public List<Mutter> findAll(){
    	Connection conn=null;
    	List<Mutter> mutterList= new ArrayList<Mutter>();
    	try{
    		Class.forName("com.mysql.cj.jdbc.Driver");
    		conn = getConnection();
    	//	SELECT文の準備
    		String sql =
    		"SELECT ID,NAME,TEXT FROM mutter ORDER BY ID DESC";
    		//SQL文をDBに届ける(SQLの送信)
    		PreparedStatement pStmt = conn.prepareStatement(sql);

    		//SELECTを実行(結果の受け取り)
    		ResultSet rs = pStmt.executeQuery();

    		//SELECT文の結果をArrayListに格納
    		while(rs.next()) {
    			int id=rs.getInt("ID");
    			String userName = rs.getString("NAME");
    			String text = rs.getString("TEXT");
    			Mutter mutter = new Mutter(id,userName,text);
    			mutterList.add(mutter);
    		}

		 } catch (URISyntaxException e) {
	         e.printStackTrace();
	         return null;
    	}catch (SQLException e) {
    		e.printStackTrace();
    		return null;
    	}catch (ClassNotFoundException e) {
    		e.printStackTrace();
    		return null;
    	}finally {
    		//データベース切断
    		if(conn != null) {
    			try {
    				conn.close();
    			}catch (SQLException e) {
    				e.printStackTrace();
    				return null;
    			}
    		}
    	}
    	//はてな？↓どういう意味？mutterListを取得
    	return mutterList;
    }

    //createメソッドでレコードを追加
    public boolean create(Mutter mutter) {
    	Connection conn =null;
    	try {
    		//データベースへ接続
    		conn = getConnection();

    		//INSERT文の準備(idは自動連番なので指定しなくてよい)
    		String sql ="INSERT INTO mutter(NAME,TEXT) VALUES(?, ?)";
    		PreparedStatement pStmt = conn.prepareStatement(sql);
    		//INSERT文の「?」に使用する値を設定してSQLを完成
    		pStmt.setString(1, mutter.getUserName());
    		pStmt.setString(2, mutter.getText());

    		//INSERT文を実行(前処理済みのSQLを実行し，更新行数を返す)
    		int result =pStmt.executeUpdate();

    		if(result !=1) {
    			return false;
    		}
    	} catch (URISyntaxException e) {
            e.printStackTrace();
            return false;
    	}catch (SQLException e) {
    		e.printStackTrace();
    		return false;
    	}finally {
    		//データベース切断
    		if(conn != null) {
    			try {
    				conn.close();
    			}catch (SQLException e) {
    				e.printStackTrace();
    			}
    		}
    	}
    				return true;
    	}
    }

