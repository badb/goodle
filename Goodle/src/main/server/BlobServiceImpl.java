package main.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.client.BlobService;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;


@SuppressWarnings("serial")
public class BlobServiceImpl extends RemoteServiceServlet implements
		BlobService {

	// Start a GAE BlobstoreService session and Objectify session
	BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	//Objectify ofy = ObjectifyService.begin();
	//static {
	//	ObjectifyService.register(UploadedFile.class);
	//}

	// Generate a Blobstore Upload URL from the GAE BlobstoreService
	@Override
	public String getBlobStoreUploadUrl() {
		// Map the UploadURL to the uploadservice which will be called by
		// submitting the FormPanel
		
		//TODO: zakomentować przed uploadem na appengine
		String localName = System.getProperty("computername");
		return blobstoreService.createUploadUrl("/goodle/uploadservice").replace(localName, "127.0.0.1");
		
		//TODO: odkomentować przed uploadem na appengine
		//return blobstoreService.createUploadUrl("/goodle/uploadservice");
		
	}
	

	// Override doGet to serve blobs. This will be called automatically by the
	// File Widget
	// in the client
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
	{
		BlobInfoFactory blobInfoFactory = new BlobInfoFactory(DatastoreServiceFactory.getDatastoreService());

		try {
			BlobKey blobKey = new BlobKey(req.getParameter("blob-key"));
			BlobInfo blobInfo = blobInfoFactory.loadBlobInfo(blobKey);

			resp.setHeader("content-type", blobInfo.getContentType());
			resp.setHeader("content-disposition", "attachment;filename=" + blobInfo.getFilename());
			blobstoreService.serve(blobKey, resp);
		}
		catch (Exception e) {
			resp.sendError(400);
			blobstoreService.serve(null, resp);
			
		}
	}

}
