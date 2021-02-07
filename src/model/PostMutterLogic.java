package model;

import dao.MutterDAO;

public class PostMutterLogic {
	public void execute(Mutter mutter) {
		//変数を一つに変更
		MutterDAO dao = new MutterDAO();
		//daoのクリエイトメソッドにmutterをもたせる
		dao.create(mutter);
	}

}
