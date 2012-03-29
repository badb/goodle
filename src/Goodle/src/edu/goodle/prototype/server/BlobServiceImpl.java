package edu.goodle.prototype.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

import edu.goodle.prototype.client.BlobService;
import edu.goodle.prototype.client.ClientFile;
import edu.goodle.prototype.db.UploadedFile;

@SuppressWarnings("serial")
public class BlobServiceImpl extends RemoteServiceServlet implements
		BlobService {

	// Start a GAE BlobstoreService session and Objectify session
	BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();
	Objectify ofy = ObjectifyService.begin();
	//static {
	//	ObjectifyService.register(UploadedFile.class);
	//}

	// Generate a Blobstore Upload URL from the GAE BlobstoreService
	@Override
	public String getBlobStoreUploadUrl() {

		// Map the UploadURL to the uploadservice which will be called by
		// submitting the FormPanel
		return blobstoreService.createUploadUrl("/goodle/uploadservice").replace("julia-laptop", "127.0.0.1");
	}

	public ClientFile getUploadedFile(String id) {

		long l = Long.parseLong(id);
		UploadedFile file = ofy.get(UploadedFile.class, l);
		ClientFile sfile = new ClientFile();
		sfile.setName(file.getName());
		sfile.setUrl(file.getUrl());
		return sfile;

	}

	// Override doGet to serve blobs. This will be called automatically by the
	// File Widget
	// in the client
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		BlobKey blobKey = new BlobKey(req.getParameter("blob-key"));
		blobstoreService.serve(blobKey, resp);

	}

}
