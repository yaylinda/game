import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';

import {AppRoutingModule} from './app-routing.module';

import {AppComponent} from './app.component';
import {DashboardComponent} from './dashboard.component';
import {HeroService} from './hero.service';
import {HttpClientModule} from '@angular/common/http';
import {CardComponent} from "./card.component";
import {GameboardComponent} from "./gameboard.component";
import {HandComponent} from "./hand.component";

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AppRoutingModule,
    HttpClientModule
  ],
  declarations: [
    AppComponent,
    DashboardComponent,
    CardComponent,
    HandComponent,
    GameboardComponent,
  ],
  providers: [
    HeroService
  ],
  bootstrap: [
    AppComponent
  ]
})

export class AppModule { }
