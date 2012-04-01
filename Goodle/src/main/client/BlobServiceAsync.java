package main.client;

import main.shared.models.UploadedFile;

import com.google.gwt.user.client.rpc.AsyncCallback;



public interface BlobServiceAsync {

    void getBlobStoreUploadUrl(AsyncCallback<String> callback);
    void getUploadedFile(String id, AsyncCallback<ClientFile> callback);

}
