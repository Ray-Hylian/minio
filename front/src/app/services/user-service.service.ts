import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private endpoint: String = "http://localhost:8080";

  constructor(private http: HttpClient) { }

  /**
   * Creation of a user and bucket
   * @param userName need a user name  
   * @param userEmail need a user email
   */
  createUser(userName: string, userEmail: string) {
    return this.http.post<any>(this.endpoint + "/create-user", { name: userName, email: userEmail });
  }

  /**
   * Requests users that has name or email starting with the given parameter.
   * @param filter The string to compare with.
   */
  requestUsersStartsWith(filter: string) {
    return this.http.post<any>(this.endpoint + "/search-users", filter);
  }
}
