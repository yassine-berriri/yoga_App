import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { expect } from '@jest/globals';
import { SessionService } from 'src/app/services/session.service';

import { ListComponent } from './list.component';
import { of } from 'rxjs';
import { SessionApiService } from '../../services/session-api.service';
import { By } from '@angular/platform-browser';
import { MatButtonModule } from '@angular/material/button';
import { RouterModule } from '@angular/router';



describe('ListComponent', () => {
  let component: ListComponent;
  let fixture: ComponentFixture<ListComponent>;

  const mockSessionService = {
    sessionInformation: {
      admin: true
    }
  }

  const mockSessionApiService = {
    all: jest.fn().mockReturnValue(
      of([
        { id: 1, name: 'Yoga Basics', date: '2024-01-01', description: 'Learn the basics of Yoga.' },
        { id: 2, name: 'Advanced Yoga', date: '2024-01-15', description: 'Push your limits with advanced poses.' },
      ])
    ),
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ListComponent],
      imports: [HttpClientModule, MatCardModule, MatIconModule, MatButtonModule, RouterModule.forRoot([])],
      providers: [{ provide: SessionService, useValue: mockSessionService },
        { provide: SessionApiService, useValue: mockSessionApiService },

      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  // test unitaire  Affichage de la liste des sessions
  it('should display a list of sessions', () => {
    const sessionCards = fixture.debugElement.queryAll(By.css('mat-card.item'));
    expect(sessionCards.length).toBe(2); // Vérifiez que 2 sessions sont affichées
    expect(sessionCards[0].nativeElement.textContent).toContain('Yoga Basics');
    expect(sessionCards[1].nativeElement.textContent).toContain('Advanced Yoga');
  }); 

  // test unitaire L’apparition des boutons Detail si l’utilisateur connecté est un admin
  it('should display the Detail button if the user is an admin', () => {
    const detailButtons = fixture.debugElement.queryAll(By.css('span.ml1')).filter(span => span.nativeElement.textContent === 'Detail');
    expect(detailButtons.length).toBe(2); // les buttons detail doit etre affiché 2 fois
  });

   // test unitaire L’apparition des boutons Detail si l’utilisateur connecté est un admin
   it('should display the create button if the user is an admin', () => {
    const detailButtons = fixture.debugElement.queryAll(By.css('span.ml1')).filter(span => span.nativeElement.textContent === 'Create');
    expect(detailButtons.length).toBe(1); // les buttons create doit etre affiché 1 fois
  });

  

});
