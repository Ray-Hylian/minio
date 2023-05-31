import { Component, OnInit, ViewChild, TemplateRef } from '@angular/core';
import { HttpEventType, HttpUploadProgressEvent, HttpStatusCode } from '@angular/common/http';

import { FileService } from 'src/app/services/file-service.service';
import { UserService } from 'src/app/services/user-service.service';
import { User } from 'src/app/data-object';

import { takeUntil } from 'rxjs/operators'
import { Subject } from 'rxjs';
import { FormGroup, FormArray, FormControl } from '@angular/forms';
import { FileUploadComponent } from '../file-upload/file-upload.component';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-upload-form',
  templateUrl: './upload-form.component.html',
  styleUrls: ['./upload-form.component.css'],
  providers: []
})
export class UploadFormComponent implements OnInit {

  userFormControl = new FormControl('');

  users : User[] = [];
  selectedUser! : User;

  cancelUploadSubject = new Subject();
  uploading: boolean = false;
  progress: number = 0;

  metadataArray : FormArray = new FormArray([new FormGroup({key: new FormControl(), value : new FormControl()})]);
  metadataForm : FormGroup = new FormGroup({metadataArray : this.metadataArray});

  @ViewChild("uploadDialog", {read: TemplateRef})
  uploadDialogTemplate? : TemplateRef<any>;

  constructor(private fileService: FileService, private userService: UserService, private dialog: MatDialog, readonly snackBar : MatSnackBar) { }

  ngOnInit(): void {

  }

  upload(event: any, fileUpload: FileUploadComponent) {
    this.uploading = true;
    var formData = new FormData();
    
    formData.append('bucket', this.selectedUser.uuid);
    for (let file of event.files) {
      formData.append('file', file);
    }
    if(this.metadataArray.value){
      this.metadataArray.value.forEach((formGroup : any) => {
        if(formGroup.key != null && formGroup.value != null && formGroup.key.value != '' && formGroup.value.value != ''){
          formData.append('metadata', JSON.stringify(formGroup));
        }
      });
    }
    if(this.uploadDialogTemplate){
      this.dialog.open(this.uploadDialogTemplate);
    }
    this.fileService.uploadFiles(formData)
      .pipe(takeUntil(this.cancelUploadSubject))
      .subscribe((response: any) => {
        if (response.type === HttpEventType.UploadProgress) {
          this.updateProgress(response);
        } else if (response.type === HttpEventType.Response) {
          this.updateResponse(response, fileUpload);
        }
      });
  }

  updateProgress(uploadEvent: HttpUploadProgressEvent) {
    if (typeof uploadEvent.total != 'undefined') {
      this.progress = Math.round(100 * uploadEvent.loaded / uploadEvent.total);
    }
  }

  updateResponse(response: any, fileUpload: FileUploadComponent) {
    this.uploading = false;
    this.progress = 0;

    if (response.status === HttpStatusCode.Ok) {
      // this.messageService.add({ severity: 'success', summary: 'Fichier téléversé' });
      this.snackBar.open('Fichier téléversé', '', {duration: 5000, panelClass: 'snackbar-success'});
      fileUpload.clear();
      this.dialog.closeAll();
    } else {
      this.snackBar.open('Une erreur est survenue', 'La requête n\'a pas pue être traitée par le serveur.', {duration: 5000, panelClass: 'snackbar-error'});
    }
  }

  cancelUpload() {
    this.uploading = false;
    this.cancelUploadSubject.next(0);
    this.dialog.closeAll();
    this.progress = 0;
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
  }

  addMetadataField(){
    this.metadataArray.push(new FormGroup({key: new FormControl(), value : new FormControl()}));
  }
}
