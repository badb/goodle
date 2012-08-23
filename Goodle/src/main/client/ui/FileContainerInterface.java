package main.client.ui;

import main.shared.proxy.UploadedFileProxy;

public interface FileContainerInterface {
	public void addFile(String url, String title);
	public void removeFile(UploadedFileProxy file);
}
