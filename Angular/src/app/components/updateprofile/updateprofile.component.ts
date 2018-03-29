import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Title } from '@angular/platform-browser';
import { User } from '../../models/User';
import { CurrentUserService } from '../../services/current-user.service';

@Component({
  selector: 'app-updateprofile',
  templateUrl: './updateprofile.component.html',
  styleUrls: ['./updateprofile.component.css']
})
export class UpdateprofileComponent implements OnInit {

  private user: User;

  constructor(private client: HttpClient, private router: Router, private currUser: CurrentUserService, private title: Title) { }

  ngOnInit() {
    this.title.setTitle('Update Info');
    
    this.user = this.currUser.getCurrentUser();
    
    document.getElementById("firstname").setAttribute("value", this.user.firstName);
    document.getElementById("lastname").setAttribute("value", this.user.lastName);
    document.getElementById("email").setAttribute("value", this.user.email);
  }

}
