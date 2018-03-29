import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-profilepage',
  templateUrl: './profilepage.component.html',
  styleUrls: ['./profilepage.component.css']
})
export class ProfilepageComponent implements OnInit {

  private urlPath: string;

  constructor(private title: Title) { }

  ngOnInit() {
    this.title.setTitle("Profile page");

    this.urlPath = window.location.pathname;
  }
}
