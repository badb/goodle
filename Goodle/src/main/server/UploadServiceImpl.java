package main.server;


import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.client.ClientFactory;
import main.server.domain.UploadedFile;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.sun.xml.internal.ws.api.server.Module;


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

	//Override the doPost method to store the Blob's meta-data
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
		Logger logger = Logger.getLogger("Goodle.Log");
		logger.log(Level.SEVERE, blobs.toString());
		List<BlobKey> blobKeys = blobs.get("file");
		logger.log(Level.SEVERE, blobKeys.get(0).getKeyString());

		/*UploadedFileProxy uploadedFile = new UploadedFile();
		uploadedFile.setName(req.getParameter("title"));
		uploadedFile.setAuthor(clientFactory.getCurrentUser().getId());
		uploadedFile.setUrl("/goodle/blobservice?blob-key=" + blobKeys.get(0).getKeyString());
		*/
		
		//Redirect recursively to this servlet (calls doGet)
		//res.sendRedirect("/goodle/uploadservice?id=" + uploadedFile.getId());
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		//Send the meta-data id back to the client in the HttpServletResponse response
		String id = req.getParameter("id");
		resp.setHeader("Content-Type", "text/html");
		resp.getWriter().println(id);

	}



}
