import {EventEmitter, Injectable, OnInit, Output} from '@angular/core';
import {Headers, Http} from '@angular/http';

import 'rxjs/add/operator/toPromise';

import {Observable} from 'rxjs/Rx';

import {Player} from "./dto/player";
import {GameSession} from "./dto/gamesession";
import {Card} from "./dto/card";
import {Cell} from "./dto/cell";
import {Move} from "./dto/move";
import {SessionToken} from './dto/sessiontoken';

@Injectable()
export class GameService {

  private headers = new Headers({
    'Content-Type': 'application/json',
    'accept': 'application/json'
  });
  private baseUrl = `http://${window.location.hostname}`;
  private basePort = '8080';
  private playerUrl = '/player';
  private loginUrl = '/login';

  private joinGameUrl = '/player/join';
  private startGameUrl = '/game/start';
  private cardUrl = '/game/card';
  private turnUrl = '/game/endTurn';
  private pollUrl = '/game/poll';
  // private getPlayerUrl = '/player';

  private selectedCard: Card;

  @Output() updateHandEE: EventEmitter<Card> = new EventEmitter();
  @Output() updatePowerEE: EventEmitter<number> = new EventEmitter();
  @Output() updateBoardEE: EventEmitter<Cell[][]> = new EventEmitter();
  @Output() updateGameSessionEE: EventEmitter<GameSession> = new EventEmitter();

  constructor(private http: Http) { }

  getPlayerFromSessionTokenCookie(sessionToken: string): Promise<Player> {
    const url = `${this.baseUrl}:${this.basePort}/${this.playerUrl}/${sessionToken}`;
    return this.http
      .get(url, {headers: this.headers})
      .toPromise()
      .then(response => response.json() as Player)
  }

  login(username: string, password: string): Promise<SessionToken> {
    const url = `${this.baseUrl}:${this.basePort}/${this.loginUrl}/${username}/${password}`;
    return this.http
      .get(url, {headers: this.headers})
      .toPromise()
      .then(response => response.json() as SessionToken)
  }

  /*
  OLD FUNCTIONS
   */

  joinGame(name: string): Promise<Player> {
    this.baseUrl = `http://${window.location.hostname}:${this.basePort}`;
    console.log('BASE URL: ' + this.baseUrl);

    const url = `${this.baseUrl}${this.joinGameUrl}/${name}`;
    return this.http
      .post(url, "{}", {headers: this.headers})
      .toPromise()
      .then(response => response.json() as Player)
      .catch(this.handleError);
  }

  startGame(player1: Player, player2: Player, id: string): Promise<GameSession> {
    console.log(`starting game: ${id}`);
    const url = `${this.baseUrl}${this.startGameUrl}/${id}`;
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
            return x.json().gameboard != null;
          })
          .take(1)
          .map(gameSession => {
            return gameSession.json() as GameSession
          })
          .timeout(600000)
      );
  }

  drawCard(id: string): Promise<Card> {
    console.log('drawing card...');
    const url = `${this.baseUrl}${this.cardUrl}/${id}`;
    return this.http
      .get(url, {headers: this.headers})
      .toPromise()
      .then(response => response.json() as Card)
      .catch(this.handleError);
  }

  sendCardPut(card: Card, row: number, col: number) : Promise<Cell[][]> {
    console.log('send card placement...');
    let cell: Cell = new Cell;
    cell.type = card.cardType;
    cell.might = card.might;
    cell.move = card.movement;
    cell.team = card.owningTeam;
    cell.state = 'OCCUPIED';

    let move: Move = new Move;
    move.row = row;
    move.col = col;
    move.cell = cell;

    move.playerId = card.owningPlayer;

    const url = `${this.baseUrl}${this.cardUrl}`;

    return this.http
      .put(url, move, {headers: this.headers})
      .toPromise()
      .then(response => response.json() as Cell[][])
      .catch(this.handleError);
  }

  endTurn(id: string, hand: Card[]) {
    console.log('ending turn...');
    const url = `${this.baseUrl}${this.turnUrl}/${id}`;
    return this.http
      .put(url, hand, {headers: this.headers})
      .toPromise()
      .then()
      .catch(this.handleError);
  }

  /************************************************************************************************
   * Private Helper Functions
   ************************************************************************************************/

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }

  /************************************************************************************************
   * Getters and Setters
   ************************************************************************************************/

  setClickedCard(card: Card) {
    this.selectedCard = card;
  }

  getClickedCard(): Card {
    return this.selectedCard;
  }

  updateHand(newCard: Card) {
    this.updateHandEE.emit(newCard);
  }

  getUpdatedHand(): EventEmitter<Card> {
    return this.updateHandEE;
  }

  updatePower(power: number) {
    this.updatePowerEE.emit(power);
  }

  getUpdatedPower(): EventEmitter<number> {
    return this.updatePowerEE;
  }

  updateBoard(board: Cell[][]) {
    this.updateBoardEE.emit(board);
  }

  getUpdatedBoard(): EventEmitter<Cell[][]> {
    return this.updateBoardEE;
  }

  updateGameSession(gameSession: GameSession) {
    this.updateGameSessionEE.emit(gameSession);
  }

  getUpdatedGameSession(): EventEmitter<GameSession> {
    return this.updateGameSessionEE;
  }
}

