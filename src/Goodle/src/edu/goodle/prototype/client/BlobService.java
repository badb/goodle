package edu.goodle.prototype.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath("blobservice")
public interface BlobService extends RemoteService {

  String getBlobStoreUploadUrl();

  ClientFile getUploadedFile(String id);

}