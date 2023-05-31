import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { UserService } from 'src/app/services/user-service.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.css'],
  providers: []
})
export class CreateUserComponent implements OnInit {

  createUserForm: FormGroup = new FormGroup({
    name: new FormControl(""),
    email: new FormControl("")
  });

  constructor(private userService: UserService, readonly snackBar : MatSnackBar) { }

  ngOnInit(): void {
  }

  onSubmit() {
    var component = this;
    component.userService.createUser(this.createUserForm.value.name, this.createUserForm.value.email).subscribe(
      (response: any) => {
        if (response === 'OK') {
          this.snackBar.open('Utilisateur créé', '', {duration: 5000, panelClass: 'snackbar-success'});
          this.createUserForm.reset();
        } else {
          this.snackBar.open('La création de l\'utilisateur a échoué', '', {duration: 5000, panelClass: 'snackbar-error'});
        }
      }
    );
  }
}