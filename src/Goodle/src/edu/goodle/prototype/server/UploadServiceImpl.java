package edu.goodle.prototype.server;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

import edu.goodle.prototype.db.UploadedFile;

//The FormPanel must submit to a servlet that extends HttpServlet  
//RemoteServiceServlet cannot be used
@SuppressWarnings("serial")
public class UploadServiceImpl extends HttpServlet {

  //Start Blobstore and Objectify Sessions
  BlobstoreService blobstoreService = BlobstoreServiceFactory
      .getBlobstoreService();
  Objectify ofy = ObjectifyService.begin();

  //.static {
  //  ObjectifyService.register(UploadedFile.class);
  //}

  //Override the doPost method to store the Blob's meta-data
  public void doPost(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {

    Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
    List<BlobKey> blobKeys = blobs.get("upload");

    //Get the paramters from the request to populate the UploadedFile object
    //TODO umieszczanie danych
    UploadedFile uploadedFile = new UploadedFile("Name", null, "/goodle/blobservice?blob-key=" + blobKeys.get(0).getKeyString());

    ofy.put(uploadedFile);

    //Redirect recursively to this servlet (calls doGet)
    res.sendRedirect("/goodle/uploadservice?id=" + uploadedFile.getKey());
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
