import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.css']
})
export class FileUploadComponent implements OnInit {

  randomUUID : string;

  @Input()
  minifiedText : boolean = false;

  @Input()
  disabled : boolean = false;

  @Output()
  uploadHandler : EventEmitter<FileUploadComponent> = new EventEmitter<FileUploadComponent>();

  files : File[] = [];

  constructor() {
    this.randomUUID = this.generateUniqSerial();
  }

  ngOnInit(): void {
  }

  openFileSelector(){
    var input = document.getElementById(this.randomUUID);
    if(input && input != null){
      input.click();
    }
  }

  inputFiles(event : any){
    this.addFiles(event.target.files);
  }

  addFiles(fileList : FileList){
    if(fileList != null){
      for(var i : number = 0; i < fileList.length; ++i){
        var f : File | null = fileList.item(i);
        if(f != null){
          this.files.push(f);
        }
      }
    }
  }

  handleUpload(){
    this.uploadHandler.emit(this);
  }

  removeFile(i : number){
    this.files.splice(i, 1);
  }

  public clear(){
    this.files = [];
  }

  public generateUniqSerial() {
    return 'xxxx-xxxx-xxx-xxxx'.replace(/[x]/g, function (c) {
      var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
      return v.toString(16);
    });
  }

}
