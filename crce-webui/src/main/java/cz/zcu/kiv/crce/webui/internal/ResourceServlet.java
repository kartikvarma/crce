package cz.zcu.kiv.crce.webui.internal;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cz.zcu.kiv.crce.metadata.Resource;
import cz.zcu.kiv.crce.plugin.Plugin;

public class ResourceServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String regex = req.getParameter("search");

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();
		String link = null;
		
		if (req.getParameter("link") != null && req.getParameter("link") instanceof String) 
		{
			link = req.getParameter("link");
			
		}
		try {
			if(fillSession(session,link,req))
			{
				resp.sendRedirect("jsp/"+link+".jsp");
				//req.getRequestDispatcher("jsp/"+link+".jsp").forward(req, resp);
			}
			else
			{
				req.getRequestDispatcher("resource?link=buffer").forward(req, resp);
			}

		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private boolean fillSession(HttpSession session, String link, HttpServletRequest req){
		if(link==null) return false;
		if(link.equals("buffer"))
		{
			Resource[] resources = Activator.instance().getBuffer(req).getRepository().getResources();
			session.setAttribute("resources", resources);
			return true;
		}
		
		else if(link.equals("plugins"))
		{
				Plugin[] plugins = Activator.instance().getPluginManager().getPlugins();
				session.setAttribute("plugins", plugins);
				return true;
		}
		
		else if(link.equals("store"))
		{
			Resource[] store = Activator.instance().getStore().getRepository().getResources();
			session.setAttribute("store", store);
			return true;
		}
		
		else
		{
			link=null;
			return false;
		}
	}
}
