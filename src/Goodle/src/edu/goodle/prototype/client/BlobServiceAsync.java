package edu.goodle.prototype.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.goodle.prototype.db.UploadedFile;


public interface BlobServiceAsync {

    void getBlobStoreUploadUrl(AsyncCallback<String> callback);
    void getUploadedFile(String id, AsyncCallback<ClientFile> callback);

}
