import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

/**
 * Service to transmit data back and forth between the front and back-end.
 */
@Injectable({
  providedIn: 'root'
})
export class FileService {

  private endpoint: String = "http://localhost:8080";

  constructor(private http: HttpClient) { }

  /**
   * Uploads the given FormData to the rest-api and reporting
   * upload progress.
   * 
   * @param formData Data to be sent over to the REST-API.
   */
  uploadFiles(formData: FormData): any {
    return this.http.post<any>(this.endpoint + "/upload", formData, {
      reportProgress: true,
      observe: 'events'
    });
  }

  /**
   * Get file(s) from bucket.
   * @param bucketName Name of the selected bucket.
   */
  getFileList(bucketName: string) {
    return this.http.get(this.endpoint + "/list-files?bucket=" + bucketName);
  }

  /**
   * Post request to give bucket and path in order to get an uuid request download.
   * @param bucketName name of the bucket.
   * @param path filepath of the file.
   */
  getUuidFromDlRequest(bucketName: string, path: string) {
    return this.http.post<any>(this.endpoint + "/request-file", { bucket: bucketName, path: path } );
  }

  getFileInfo(uuid : string){
    return this.http.get<any>(this.endpoint + "/file-info/" + uuid);
  }

  downloadFile(uuid : string){
    return this.http.get<any>(this.endpoint + "/download/" + uuid, {responseType : 'arraybuffer' as 'json', reportProgress: true, observe: 'events'});
  }

}