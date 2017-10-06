import { Injectable }    from '@angular/core';
import { Headers, Http } from '@angular/http';
import {HttpClient, HttpClientModule} from '@angular/common/http';

import 'rxjs/add/operator/toPromise';

import { Hero } from './hero';
import {Player} from "./player";
import {toPromise} from "rxjs/operator/toPromise";
import {Observable} from "rxjs/Observable";

@Injectable()
export class HeroService {

  private headers = new Headers({'Content-Type': 'application/json', 'accept': 'application/json'});
  private heroesUrl = 'api/heroes';  // URL to web api

  private baseUrl = 'localhost:8080';
  private joinGameUrl = '/player/join';

  constructor(private http: Http, private httpClient: HttpClient) { }

  joinGame(name: string): Observable<Player> {
    const url = `${this.baseUrl}${this.joinGameUrl}/${name}`;
    return this.httpClient.post(url, "{}");
  }

  // joinGame(name: string): void {
  //   const url = `${this.baseUrl}${this.joinGameUrl}/${name}`;
  //   this.http
  //     .post(url, "{}", {headers: this.headers})
  //     .toPromise()
  //     .then(response => response.json().data as Player)
  //     .catch(this.handleError);
  // }

  getHeroes(): Promise<Hero[]> {
    return this.http.get(this.heroesUrl)
               .toPromise()
               .then(response => response.json().data as Hero[])
               .catch(this.handleError);
  }


  getHero(id: number): Promise<Hero> {
    const url = `${this.heroesUrl}/${id}`;
    return this.http.get(url)
      .toPromise()
      .then(response => response.json().data as Hero)
      .catch(this.handleError);
  }

  delete(id: number): Promise<void> {
    const url = `${this.heroesUrl}/${id}`;
    return this.http.delete(url, {headers: this.headers})
      .toPromise()
      .then(() => null)
      .catch(this.handleError);
  }

  create(name: string): Promise<Hero> {
    return this.http
      .post(this.heroesUrl, JSON.stringify({name: name}), {headers: this.headers})
      .toPromise()
      .then(res => res.json().data as Hero)
      .catch(this.handleError);
  }

  update(hero: Hero): Promise<Hero> {
    const url = `${this.heroesUrl}/${hero.id}`;
    return this.http
      .put(url, JSON.stringify(hero), {headers: this.headers})
      .toPromise()
      .then(() => hero)
      .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }
}

