import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { UserService } from 'src/app/services/user-service.service';
import { FileService } from 'src/app/services/file-service.service';
import { User, BucketFile } from 'src/app/data-object';
import { Router } from '@angular/router';

@Component({
  selector: 'app-download-form',
  templateUrl: './download-form.component.html',
  styleUrls: ['./download-form.component.css'],
  providers: []
})

export class DownloadFormComponent implements OnInit {

  files: BucketFile[] = [];
  users: User[] = [];
  selectedUser!: User; //= {uuid : "dbd7778a-340e-11ed-a261-0242ac120002", name : "Melissa Ren", email : "melissa.ren@infotel.com" };
  displayedColumns: string[] = ['Nom du fichier'];

  userFormControl = new FormControl('');
  getUuidForm: FormGroup = new FormGroup({
    bucketName: new FormControl(""),
    path: new FormControl("")
  });

  constructor(private userService: UserService, private fileService: FileService, private router:Router) { }

  ngOnInit(): void {
  }

  updateOptions() {
    var filter = this.userFormControl.value;
    if (filter != null && filter != '') {
      this.userService.requestUsersStartsWith(filter).subscribe((response: any) => {
        this.users = response;
      });
    } else {
      this.users = [];
    }
  }

  displayFn(user : User){
    return user && user.name ? user.name : "";
  }

  selectUser(event : any){
    this.selectedUser = event.option.value;
    this.updateFiles();
  }

  updateFiles() {
    this.fileService.getFileList(this.selectedUser.uuid).subscribe((response: any) => {
      this.files = response;
    });
  }

  requestDownload(event:any, path : string){
    this.fileService.getUuidFromDlRequest(this.selectedUser.uuid, path).subscribe((response: any) => {
      
      window.open('/download/'+response.uuid, "_blank");
      
    });
  }
}
