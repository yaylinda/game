import { Injectable }    from '@angular/core';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

// import 'rxjs/Rx';
import {Observable} from 'rxjs/Rx';

import {Player} from "./player";
import {GameSession} from "./gamesession";

@Injectable()
export class HeroService {

  private headers = new Headers({
    'Content-Type': 'application/json',
    'accept': 'application/json'
  });

  private baseUrl = 'http://localhost:8080';
  private playerUrl = '/player';
  private joinGameUrl = '/player/join';
  private startGameUrl = '/game/start';
  private cardUrl = '/game/card';
  private boardUrl = '/game/board';
  private pollUrl = '/game/poll';

  constructor(private http: Http) { }

  joinGame(name: string): Promise<Player> {
    const url = `${this.baseUrl}${this.joinGameUrl}/${name}`;
    return this.http
      .post(url, "{}", {headers: this.headers})
      .toPromise()
      .then(response => response.json() as Player)
      .catch(this.handleError);
  }

  startGame(player1: Player, player2: Player): Promise<GameSession> {
    const url = `${this.baseUrl}${this.startGameUrl}`;
    return this.http
      .post(url, [player1, player2], {headers: this.headers})
      .toPromise()
      .then(response => response.json() as GameSession)
      .catch(this.handleError);
  }

  getPlayerById(id: string): Promise<Player> {
    console.log(`getting player by id: ${id}`);
    const url = `${this.baseUrl}${this.playerUrl}/${id}`;
    return this.http
      .get(url, {headers: this.headers})
      .toPromise()
      .then(response => response.json() as Player)
      .catch(this.handleError);
  }

  pollForGame(id: string): Observable<GameSession> {
    console.log('polling for game...');
    const url = `${this.baseUrl}${this.pollUrl}/${id}`;
    return Observable
      .fromPromise(this.http
        .get(url, {headers: this.headers})
        .toPromise())
      .flatMap(jobQueueData =>
        Observable.interval(1000)
          .flatMap(() => this.http
            .get(url, {headers: this.headers})
            .toPromise())
          .filter(x => {
            return x.json().gameBoard != null;
          })
          .take(1)
          .map(gameSession => {
            return gameSession.json() as GameSession
          })
          .timeout(60000)
      );
  }

  // TODO update board

  // TODO draw cards

  // TODO place card

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }
}

