import { Directive, HostListener, HostBinding, EventEmitter, Output, Input } from '@angular/core';

@Directive({
  selector: '[dragndrop]'
})
export class DragndropDirective {

  @Input()
  disabled : boolean = false;

  @Output()
  filesDropped : EventEmitter<FileList> = new EventEmitter<FileList>();

  constructor() { }

  @HostListener('dragover', ['$event'])
  public onDragOver(event : any){
    event.preventDefault();
    event.stopPropagation();
  }

  @HostListener('dragleave', ['$event'])
  public onDragLeave(event : any){
    event.preventDefault();
    event.stopPropagation();
  }

  @HostListener('drop', ['$event'])
  public onDrop(event : any){
    event.preventDefault();
    event.stopPropagation();

    if(!this.disabled){
      const files = event.dataTransfer.files;
      if(files.length > 0){
        this.filesDropped.emit(files);
      }
    }
  }

}
