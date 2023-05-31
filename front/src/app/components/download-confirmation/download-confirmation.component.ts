import { Component, OnInit } from '@angular/core';
import * as Forge from 'node-forge'
import { saveAs } from 'file-saver';
import { publicKey } from 'src/app/public-key';
import { ActivatedRoute } from '@angular/router';
import { HttpEventType } from '@angular/common/http';
import { FileService } from 'src/app/services/file-service.service';


@Component({
  selector: 'app-download-confirmation',
  templateUrl: './download-confirmation.component.html',
  styleUrls: ['./download-confirmation.component.css']
})
export class DownloadConfirmationComponent implements OnInit {

  loadingMessage? : string;

  progress : number = 0;

  verified? : boolean;

  constructor(private fileService: FileService, private route: ActivatedRoute) {
    this.route.params.subscribe((params: any) => {
      var uuid : string = params.uuid;

      fileService.getFileInfo(uuid).subscribe((fileInfo : {name : string; contentType : string; size : number}) => {
        
        this.loadingMessage = "Téléchargement de " + fileInfo.name;

        fileService.downloadFile(uuid).subscribe((event : any) => {
          if(event.type == HttpEventType.DownloadProgress){
            this.progress = Math.round(100*event.loaded/(fileInfo.size+256));
            if(this.progress == 100){
              this.loadingMessage = "Vérification du fichier";
            }
          } else if(event.type == HttpEventType.Response){
            var signedFile : ArrayBuffer = event.body;

            const rsa = Forge.pki.publicKeyFromPem(publicKey);
            let sha256 = Forge.md.sha256.create();

            const sliceSize : number = 2048;
            const parts = Math.floor(fileInfo.size/sliceSize);

            // Updates hash sequently to avoid memory overconsumption.
            for(var i = 0; i < parts; i++){
              sha256.update(new Forge.util.ByteStringBuffer(signedFile.slice(i*sliceSize, (i+1)*sliceSize)).getBytes());
            }
            sha256.update(new Forge.util.ByteStringBuffer(signedFile.slice(parts*sliceSize, fileInfo.size)).getBytes());

            var signature = new Forge.util.ByteStringBuffer(signedFile.slice(fileInfo.size)).getBytes();

            this.verified = rsa.verify(sha256.digest().getBytes(), signature);

            if(this.verified){
              this.loadingMessage = "Téléchargement terminé.";
              saveAs(new Blob([signedFile.slice(0, fileInfo.size)], {type: fileInfo.contentType}), fileInfo.name);
            } else {
              this.loadingMessage = "La vérification du fichier a échouée";
            }
          }
        });
      });

    });
  }

  ngOnInit(): void {
    
  }
}
