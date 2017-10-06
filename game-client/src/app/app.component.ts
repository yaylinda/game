import { Component } from '@angular/core';

@Component({
  selector: 'my-app',
  template: `
    <h1 id="title">{{title}}</h1>
    <hr>
    <router-outlet></router-outlet>
  `,
  styleUrls: ['./app.component.css']
})

export class AppComponent {
  title = 'Simple War';
}
