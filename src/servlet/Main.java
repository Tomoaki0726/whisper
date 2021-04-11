package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.GetMutterListLogic ;
import model.Mutter;
import model.PostMutterLogic;
import model.User;


//@WebServlet("/Main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	//つぶやきリストをアプリケーションスコープから取得 DB連携に伴い1/6に削除
	//つぶやきリスト取得して、リクエストスコープに保存
		GetMutterListLogic getMutterListLogic =
				new GetMutterListLogic();
		List<Mutter> mutterList = getMutterListLogic.execute();
		//スコープにリクエストスコープをセット
		request.setAttribute("mutterList", mutterList);


		//ログインしているか確認するため
		//セッションスコープからユーザー情報を取得
		HttpSession session =request.getSession();
		User loginUser =(User) session.getAttribute("loginUser");

	if(loginUser == null) {//ログインしていない場合
		//リダイレクト
		response.sendRedirect("/whisper");
	}else {//ログイン済みの場合
		//フォワード
		RequestDispatcher dispatcher =
				request.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
		dispatcher.forward(request, response);

		}
	}




	//投稿機能の為の追加メソッド
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		String text = request.getParameter("text");

		//入力値チェック（入力されているか？文字数は０以外か？）
		if(text != null && text.length() !=0) {
			//アプリケーションスコープに保存されたつぶやきリストを取得（DB連携のため1/6に削除）
			//セッションスコープに保存されたユーザー情報を取得
			HttpSession session = request.getSession();
			User loginUser = (User) session.getAttribute("loginUser");

			//つぶやきをつぶやきリストに追加
			Mutter mutter =new Mutter(loginUser.getName(),text);
			PostMutterLogic postMutterLogic = new PostMutterLogic();
			postMutterLogic.execute(mutter);

			//アプリケーションスコープにつぶやきリストを保存
				//削除
			}else {
				//エラーメッセージをリクエストスコープに保存
				request.setAttribute("errorMsg", "ささやきが入力されていません");
			}
			//つぶやきを取得して、リクエストスコープに保存
			GetMutterListLogic getMutterListLogic =
					new GetMutterListLogic();
			List<Mutter> mutterList = getMutterListLogic.execute();
			request.setAttribute("mutterList", mutterList);

			//メイン画面にフォワード
		RequestDispatcher dispatcher =
				request.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
		dispatcher.forward(request, response);

	}

}
