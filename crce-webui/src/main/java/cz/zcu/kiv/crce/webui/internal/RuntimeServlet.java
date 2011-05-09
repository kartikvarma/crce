package cz.zcu.kiv.crce.webui.internal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cz.zcu.kiv.crce.metadata.Resource;
import cz.zcu.kiv.crce.plugin.Plugin;
import cz.zcu.kiv.crce.repository.plugins.Executable;



public class RuntimeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message = null;
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		if(storeBufferAction(req)) {
			if(setSessionForForm(req)) req.getRequestDispatcher("jsp/forms/testForm.jsp").forward(req, resp);
			else{
				if (message==null) message="No bundles selected";
				ResourceServlet.setError(req.getSession(), false, message);
				req.getRequestDispatcher("resource?link="+req.getSession().getAttribute("source"));
			}
		}
		
	}
	
	private boolean setSessionForForm(HttpServletRequest req){
		Resource[] toTest = parseParams(req);
		if(toTest==null) return false;
		else{
			HttpSession session = req.getSession();			
			ResourceServlet.cleanSession(session);
			session.setAttribute("resources", toTest);
			Plugin[] testPlugins = Activator.instance().getPluginManager().getPlugins(Executable.class);
			session.setAttribute("tests", testPlugins);
			return true;
		}
	}
	
	private Resource[] fetchRightArray(String source, HttpServletRequest req){
		Activator a = Activator.instance();
		if(source.equals("store")) return a.getStore().getRepository().getResources();
		else return a.getBuffer(req).getRepository().getResources();
	}
	
	private Resource[] parseParams(HttpServletRequest req){
		
		
		String[] uris = (String [])req.getParameterValues("check");
		Resource[] array = fetchRightArray((String)req.getSession().getAttribute("source"),req);
		Resource[] toTest = new Resource[uris.length];
		for(int i=0;i<uris.length;i++){		
		try {
			toTest[i] = EditServlet.findResource(new URI(uris[i]), array);
		} catch (FileNotFoundException e) {
			message = "File not found! Please try again!";
			return null;
		} catch (URISyntaxException e) {
			message = "Malformed URI cant make URI from param!";
			return null;
		}
		
		}
		return toTest;
	}
	
	private boolean storeBufferAction(HttpServletRequest req){
		String source = (String) req.getSession().getAttribute("source");
		System.out.println(source);
		if(source!=null && (source.equals("buffer") || source.equals("store"))) return true;
		else return false;
	}

}
