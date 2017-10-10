import {Component, Input, OnInit} from "@angular/core";
import {Card} from "./card";
import {HeroService} from "./hero.service";
import {Cell} from "./cell";
import {GameSession} from "./gamesession";

@Component({
  selector: 'hand',
  template: `
    <div id="hand">
      <div id="game-stats">
        <p *ngIf="gameSession.myTurn">My Turn</p>
        <p *ngIf="!gameSession.myTurn">Opponent's Turn</p>
        <p>Power: {{gameSession.player.power}}</p>
      </div>
      <card *ngFor="let card of gameSession.player.hand;"
            (click)="processClickedCard(card)"
            [card]="card">
      </card>
      <button id="end-turn-btn" (click)="endTurn()">End Turn</button>
    </div>
  `,
  styleUrls: [ './hand.component.css' ]
})

export class HandComponent implements OnInit {
  private _gameSession: GameSession;
  private _lastSelectedCard: Card;

  constructor(private heroService: HeroService) { }

  ngOnInit() {
    this.heroService.getUpdatedHand()
      .subscribe((newCard: Card) => {
        const index = this._gameSession.player.hand.indexOf(this._lastSelectedCard);
        this._gameSession.player.hand.splice(index, 1, newCard);
      });
    this.heroService.getUpdatedPower()
      .subscribe((power: number) => {
        this._gameSession.player.power = power;
      });
    this.heroService.getUpdatedBoard()
      .subscribe((board: Cell[][]) => {
        this._gameSession.gameboard = board;
      });
  }

  processClickedCard(card: Card): void {
    if (this._gameSession.myTurn) {
      for (let otherCard of this._gameSession.player.hand) {
        otherCard.clicked = false;
      }
      card.clicked = true;
      this._lastSelectedCard = card;
      this.heroService.setClickedCard(card);
    }
  }

  endTurn(): void {
    this._gameSession.myTurn = false;
    this.heroService.endTurn(this._gameSession);
    this.heroService.pollForGame(this._gameSession.player.id).subscribe(updatedGameSession => {
      this.heroService.updateGameSessionEE.emit(updatedGameSession);
    });
  }

  @Input()
  set gameSession(gameSession: GameSession) {
    this._gameSession = gameSession;
  }

  get gameSession(): GameSession {
    return this._gameSession;
  }
}
