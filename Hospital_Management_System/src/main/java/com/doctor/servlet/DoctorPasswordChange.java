package com.doctor.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.DoctorDao;
import com.dao.UserDao;
import com.db.DBConnect;

@WebServlet("/doctChangePassword")
public class DoctorPasswordChange extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int uid = Integer.parseInt( req.getParameter("uid").trim());
		String oldPassword = req.getParameter("oldPassword");
		String newPassword = req.getParameter("newPassword");
		
		DoctorDao dao = new DoctorDao(DBConnect.getConn());
		HttpSession session = req.getSession();
		
		if(dao.changePassword(uid, oldPassword)) {
			if(dao.changePassword(uid, newPassword)) {
				session.setAttribute("succMsg", "Password updated succesfully");
				resp.sendRedirect("doctor/edit_profile.jsp");
			}
			else
			{
				session.setAttribute("errorMsg", "Something wrong on the server");
				resp.sendRedirect("doctor/edit_profile.jsp");
			}
			
		}else
		{
			session.setAttribute("errorMsg", "Old password incorrect");
			resp.sendRedirect("doctor/edit_profile.jsp");
		}
	}
}
