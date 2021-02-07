package model;
//ログインに関する処理をするモデル


public class LoginLogic {
	public boolean execute(User user) {
		if(user.getPass().equals("1234")) { return true; }
		return false;
	}

}
