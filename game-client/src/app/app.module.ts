import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule }   from '@angular/forms';
import { HttpModule }    from '@angular/http';
import { NgGridModule } from 'angular2-grid';

import { AppRoutingModule } from './app-routing.module';

import { AppComponent }         from './app.component';
import { DashboardComponent }   from './dashboard.component';
import { HeroesComponent }      from './heroes.component';
import { HeroDetailComponent }  from './hero-detail.component';
import { HeroService }          from './hero.service';
import { HeroSearchComponent }  from './hero-search.component';
import {HttpClientModule} from '@angular/common/http';
import {CardComponent} from "./card.component";
import {GameboardComponent} from "./gameboard.component";
import {DragulaModule} from "ng2-dragula";
import {HandComponent} from "./hand.component";

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AppRoutingModule,
    HttpClientModule,
    NgGridModule,
    DragulaModule,
  ],
  declarations: [
    AppComponent,
    DashboardComponent,
    CardComponent,
    HandComponent,
    GameboardComponent,
    HeroDetailComponent,
    HeroesComponent,
    HeroSearchComponent
  ],
  providers: [
    HeroService
  ],
  bootstrap: [
    AppComponent
  ]
})

export class AppModule { }
