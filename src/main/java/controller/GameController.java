package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.AdminDAO;
import dao.CarCrashDAO;
import dao.DinoGameDAO;
import dao.JumpkingDAO;
import dao.RaiseDragonDAO;
import dao.RoadOfSamuraiDAO;
import dao.SkeletonSurvivorDAO;



@WebServlet("*.game")
public class GameController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public GameController() { super(); }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf8");
		AdminDAO adao = AdminDAO.getInstance();
		String cmd = request.getRequestURI();
		System.out.println(cmd);
		try {
			if (cmd.equals("/CarCrashStart.game")) {
			    String loginID = request.getParameter("loginID");
			    boolean isTrue = adao.isBan(loginID);
				if(isTrue) {
					PrintWriter check = response.getWriter();
					check.append("true");
				}
				else {
				String gameURL = "/game/CarCrash/carcrash.jsp";
			    PrintWriter pw = response.getWriter();
			    pw.append(gameURL);
				}
			}else if(cmd.equals("/CarCrashGameOver.game")) {
				Gson gson = new Gson();
				CarCrashDAO dao = CarCrashDAO.getInstance();
				String loginID = request.getParameter("loginID");
				int score = Integer.parseInt(request.getParameter("score"));
				int result = dao.writeGameResult(loginID,score);
				PrintWriter pw = response.getWriter();
				pw.append(gson.toJson(result));
			}else if(cmd.equals("/SkeletonSurvivorStart.game")) {
				String loginID = request.getParameter("loginID");
				boolean isTrue = adao.isBan(loginID);
				if(isTrue) {
					PrintWriter check = response.getWriter();
					check.append("true");
				}
				else {
				String gameURL = "/game/SkeletonSurvivor/skeletonsurvivor.jsp";
			    PrintWriter pw = response.getWriter();
			    pw.append(gameURL);
				}
			}else if(cmd.equals("/SkeletonSurvivorGameOver.game")) {
				Gson gson = new Gson();
				SkeletonSurvivorDAO dao = SkeletonSurvivorDAO.getInstance();
				String loginID = request.getParameter("loginID");
				int score = Integer.parseInt(request.getParameter("score"));
				int result = dao.writeGameResult(loginID,score);
				PrintWriter pw = response.getWriter();
				pw.append(gson.toJson(result));
			}else if(cmd.equals("/DinoGameStart.game")) {
				String loginID = request.getParameter("loginID");
				boolean isTrue = adao.isBan(loginID);
				if(isTrue) {
					PrintWriter check = response.getWriter();
					check.append("true");
				}
				else {
				String gameURL = "/game/DinoGame/dinogame.jsp";
			    PrintWriter pw = response.getWriter();
			    pw.append(gameURL);
				}
			}else if(cmd.equals("/DinoGameOver.game")) {
				Gson gson = new Gson();
				DinoGameDAO dao = DinoGameDAO.getInstance();
				String loginID = request.getParameter("loginID");
				int score = Integer.parseInt(request.getParameter("score"));
				int result = dao.writeGameResult(loginID,score);
				PrintWriter pw = response.getWriter();
				pw.append(gson.toJson(result));
			}else if(cmd.equals("/JumpkingStart.game")) {
				String loginID = request.getParameter("loginID");
				boolean isTrue = adao.isBan(loginID);
				if(isTrue) {
					PrintWriter check = response.getWriter();
					check.append("true");
				}
				else {
				String gameURL = "/game/JumpKing/jumpking.jsp";
			    PrintWriter pw = response.getWriter();
			    pw.append(gameURL);
				}
			    
			}else if(cmd.equals("/JumpkingGameOver.game")) {
				Gson gson = new Gson();
				JumpkingDAO dao = JumpkingDAO.getInstance();
				String loginID = request.getParameter("loginID");
				int score = (int)Double.parseDouble(request.getParameter("score"));
				int result = dao.writeGameResult(loginID,score);
				PrintWriter pw = response.getWriter();
				pw.append(gson.toJson(result));
			}else if(cmd.equals("/RoadOfSamuraiStart.game")) {
				String loginID = request.getParameter("loginID");
				boolean isTrue = adao.isBan(loginID);
				if(isTrue) {
					PrintWriter check = response.getWriter();
					check.append("true");
				}
				else {
				String gameURL = "/game/RoadOfSamurai/roadofsamurai.jsp";
			    PrintWriter pw = response.getWriter();
			    pw.append(gameURL);
				}
			}else if(cmd.equals("/RoadOfSamuraiGameOver.game")) {
				Gson gson = new Gson();
				RoadOfSamuraiDAO dao = RoadOfSamuraiDAO.getInstance();
				String loginID = request.getParameter("loginID");
				int score = Integer.parseInt(request.getParameter("score"));
				int result = dao.writeGameResult(loginID,score);
				PrintWriter pw = response.getWriter();
				pw.append(gson.toJson(result));
			}else if(cmd.equals("/RaiseDragonStart.game")) {
				String loginID = request.getParameter("loginID");
				boolean isTrue = adao.isBan(loginID);
				if(isTrue) {
					PrintWriter check = response.getWriter();
					check.append("true");
				}
				else {
				String gameURL = "/game/RaiseDragon/raisedragon.jsp";
			    PrintWriter pw = response.getWriter();
			    pw.append(gameURL);
				}
			}else if(cmd.equals("/RaiseDragonGameOver.game")) {
				Gson gson = new Gson();
				RaiseDragonDAO dao = RaiseDragonDAO.getInstance();
				String loginID = request.getParameter("loginID");
				int score = Integer.parseInt(request.getParameter("score"));
				int result = dao.writeGameResult(loginID,score);
				PrintWriter pw = response.getWriter();
				pw.append(gson.toJson(result));
			}
			else if(cmd.equals("/JumpKingTop10.game")) {
				Gson gson = new Gson();
				JumpkingDAO dao = JumpkingDAO.getInstance();
				String json = gson.toJson(dao.top10Rank());
				PrintWriter pw = response.getWriter();
				pw.append(json);
			}else if(cmd.equals("/CarCrashTop10.game")) {
				Gson gson = new Gson();
				CarCrashDAO dao = CarCrashDAO.getInstance();
				String json = gson.toJson(dao.top10Rank());
				PrintWriter pw = response.getWriter();
				pw.append(json);
			}else if(cmd.equals("/DinoGameTop10.game")) {
				Gson gson = new Gson();
				DinoGameDAO dao = DinoGameDAO.getInstance();
				String json = gson.toJson(dao.top10Rank());
				PrintWriter pw = response.getWriter();
				pw.append(json);
			}else if(cmd.equals("/RoadOfSamuraiTop10.game")) {
				Gson gson = new Gson();
				RoadOfSamuraiDAO dao = RoadOfSamuraiDAO.getInstance();
				String json = gson.toJson(dao.top10Rank());
				PrintWriter pw = response.getWriter();
				pw.append(json);
			}else if(cmd.equals("/RaiseDragonTop10.game")) {
				Gson gson = new Gson();
				RaiseDragonDAO dao = RaiseDragonDAO.getInstance();
				String json = gson.toJson(dao.top10Rank());
				PrintWriter pw = response.getWriter();
				pw.append(json);
			}else if(cmd.equals("/SkeletonSurvivorTop10.game")) {
				Gson gson = new Gson();
				SkeletonSurvivorDAO dao = SkeletonSurvivorDAO.getInstance();
				String json = gson.toJson(dao.top10Rank());
				PrintWriter pw = response.getWriter();
				pw.append(json);
			}
		}catch(Exception e) {
			response.sendRedirect("/error.html");
			e.printStackTrace();
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
