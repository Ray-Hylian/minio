import { Component } from '@angular/core';
import { Router, NavigationStart } from '@angular/router';
  
@Component({
  selector: 'my-app',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  navLinks!: {label : string, icon? : any, routerLink : string}[];
  hideMenu!: boolean;

  constructor(router: Router){
    router.events.forEach((event: any) => {
      if(event instanceof NavigationStart){
        this.hideMenu = event.url.startsWith("/download/");
      }
    });
  }
  
  ngOnInit() {
    this.navLinks = [
      {label: 'Création d\'utilisateur', icon: 'person_add', routerLink: '/create-user'},
      {label: 'Téléversement de fichiers', icon: 'file_upload', routerLink: '/upload-file'},
      {label: 'Téléchargement de fichiers', icon: 'file_download', routerLink: '/download-file'}
    ];
  }
}