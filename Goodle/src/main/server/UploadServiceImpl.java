package main.server;


import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.client.ClientFactory;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;


//The FormPanel must submit to a servlet that extends HttpServlet  
//RemoteServiceServlet cannot be used
@SuppressWarnings("serial")
public class UploadServiceImpl extends HttpServlet 
{
	ClientFactory clientFactory;
	public void setClientFactory(ClientFactory clientFactory) { this.clientFactory = clientFactory; }

	//Start Blobstore and Objectify Sessions
	BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	//Objectify ofy = ObjectifyService.begin();

	//static { ObjectifyService.register(UploadedFile.class); }

	/*
	 * Override the doPost method to store the Blob's meta-data(non-Javadoc)
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException 
	{
		Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
		List<BlobKey> blobKeys = blobs.get("file");
	
		//Redirect recursively to this servlet (calls doGet)
		if (blobKeys.get(0) == null) 
		{ 
	        res.sendRedirect("/"); 
		} 
		else 
		{ 
			//res.sendRedirect("http://127.0.0.1:8888/goodle/uploadservice?id=" + blobKeys.get(0).getKeyString());
			res.setHeader("Content-Type", "text/html");
			res.getWriter().write(blobKeys.get(0).getKeyString());
		} 
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
	{
		//Send the meta-data id back to the client in the HttpServletResponse response
		String id = req.getParameter("id");
		resp.setHeader("Content-Type", "text/html");
		resp.getWriter().println(id);
	}



}
