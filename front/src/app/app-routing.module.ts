import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UploadFormComponent } from './components/upload-form/upload-form.component';
import { CreateUserComponent } from './create-user/create-user.component';
import { DownloadFormComponent } from './components/download-form/download-form.component';
import { DownloadConfirmationComponent } from './components/download-confirmation/download-confirmation.component';

const routes: Routes = [
  { path: '', redirectTo: 'create-user', pathMatch:'full' },
  { path: 'create-user', component: CreateUserComponent },
  { path: 'upload-file', component: UploadFormComponent },
  { path: 'download-file', component: DownloadFormComponent },
  { path: 'download/:uuid', component: DownloadConfirmationComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
